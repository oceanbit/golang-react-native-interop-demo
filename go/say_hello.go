package gotorndemo

import (
	"os"
	"path/filepath"
)

type ValueMaybe struct {
	Error string
	Value string
}

func WriteFile(dir string) *ValueMaybe {
	val := []byte("Hello, world!\n")
	err := os.WriteFile(filepath.Join(dir, "test.txt"), val, 0644)
	if err != nil {
		return &ValueMaybe{Error: err.Error(), Value: ""}
	}
	return &ValueMaybe{Error: "", Value: ""}
}

func ReadFile(dir string) *ValueMaybe {
	val, err := os.ReadFile(filepath.Join(dir, "test.txt"))
	if err != nil {
		return &ValueMaybe{Error: err.Error(), Value: ""}
	}
	return &ValueMaybe{Error: "", Value: string(val)}
}
