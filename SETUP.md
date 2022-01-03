# macOS

- Install Android Studio
- Install SDK, add to `ANDROID_HOME`
- Install NDK, add to `ANDROID_NDK_HOME` path
- `brew install go`
- `brew install libgit2`
- `go install golang.org/x/mobile/cmd/gomobile@latest`
- Add `~/go/bin` to PATH
- `cd go && gomobile init` 

----------------------------------

# Windows (wrong)

- Install Visual Studio Build Tools

- [Download + install `pkg-config`](https://stackoverflow.com/a/1711338) 
 
- Add to path

- [Download CMake](https://cmake.org/download/), add to path


```
git clone https://github.com/libgit2/libgit2/
cd libgit2
git checkout v1.3.0
mkdir build
cd build
```

```
cmake .. -DBUILD_SHARED_LIBS=OFF -DBUILD_CLAR=OFF -DTHREADSAFE=ON
cmake --build .
```

In elevated shell:

```
cmake --build . --target install
```

- Copy files to `C:\Tools`, then modify `libgit2.pc`

- Set env variable `PKG_CONFIG_PATH` to `C:\tools\libgit2\lib\pkgconfig`

- [Install GCC](https://jmeubank.github.io/tdm-gcc/)

- Add GCC to path

- Clone this project

```
cd project-repo-path
cd go
go get github.com/libgit2/git2go/v33
```

https://bvisness.me/libgit2/#windows-2

https://github.com/odewahn/git2go-test