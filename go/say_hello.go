package gotorndemo

import (
	"github.com/reactivex/rxgo/v2"
	"io"
	"os"
	"path/filepath"
)

func check(e error) {
	if e == io.EOF {
		return
	}
	if e != nil {
		panic(e)
	}
}

type OnChangeFn func([]byte, int64, int64)

var fileSeekChannel *chan rxgo.Item

func readAndObserveFile(
	fileName string,
	bytesPerRow int64,
	numberOfRows int,
	onChange OnChangeFn,
) (chan rxgo.Item, *os.File) {
	fileSeekChannelLocal := make(chan rxgo.Item)

	fileSeekObservable := rxgo.FromChannel(fileSeekChannelLocal)

	var currentFileChunkIndex int64 = 0

	// TODO: Replace this with file selector
	activeFile, openErr := os.Open(fileName)
	check(openErr)

	byteChunk := make([]byte, bytesPerRow*int64(numberOfRows))
	byteChunkSize := 0

	readFileUpdateChunk := func(offset int64) {
		var err error
		byteChunkSize, err = activeFile.ReadAt(byteChunk, offset)
		check(err)
	}

	readFileUpdateChunk(0)

	// Get file size
	fileStat, statE := activeFile.Stat()
	fileSize := fileStat.Size()
	check(statE)

	// Eager initialize UI
	onChange(
		byteChunk,
		currentFileChunkIndex,
		fileSize,
	)

	// Update UI on observable update
	fileSeekObservable.DoOnNext(func(i interface{}) {
		if i == "up" {
			currentFileChunkIndex -= 1
			if currentFileChunkIndex < 0 {
				currentFileChunkIndex = 0
			}
		} else if i == "down" {
			currentFileChunkIndex += 1
			maxRowIndex := fileSize / bytesPerRow
			if currentFileChunkIndex >= maxRowIndex {
				currentFileChunkIndex = maxRowIndex
			}
		}
		readFileUpdateChunk(currentFileChunkIndex * bytesPerRow)

		onChange(
			byteChunk,
			currentFileChunkIndex,
			fileSize,
		)
	})

	return fileSeekChannelLocal, activeFile
}

type Observer interface {
	OnSeek(byteChunk []byte, currentFileChunkIndex int64, fileSize int64)
}

func ObserveFile(path string, observer Observer) {
	var bytesPerRow int64 = 120
	numberOfRows := 3

	var localPath = filepath.Join(path, "pure.ts")

	localFileSeekChannel, _ := readAndObserveFile(
		localPath,
		bytesPerRow,
		numberOfRows,
		observer.OnSeek,
	)

	if fileSeekChannel != nil {
		close(*fileSeekChannel)
	}

	fileSeekChannel = &localFileSeekChannel
}

func GoUp() {
	*fileSeekChannel <- rxgo.Of("up")
}

func GoDown() {
	*fileSeekChannel <- rxgo.Of("down")
}

func StopObservingFile() {
	close(*fileSeekChannel)
	fileSeekChannel = nil
}
