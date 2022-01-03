package gotorndemo

import (
	"os"
)

type ValueMaybe struct {
	Error string
	Value string
}

func WriteFile(path string) *ValueMaybe {
	val := []byte("Hello, world!\n")
	err := os.WriteFile(path, val, 0644)
	if err != nil {
		return &ValueMaybe{Error: err.Error(), Value: ""}
	}
	return &ValueMaybe{Error: "", Value: ""}
}

func ReadFile(path string) *ValueMaybe {
	val, err := os.ReadFile(path)
	if err != nil {
		return &ValueMaybe{Error: err.Error(), Value: ""}
	}
	return &ValueMaybe{Error: "", Value: string(val)}
}
