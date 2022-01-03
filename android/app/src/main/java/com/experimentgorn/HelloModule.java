package com.experimentgorn;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;

import gotorndemo.Gotorndemo;
import gotorndemo.Observer;

public class HelloModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;

    HelloModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    private void sendEvent(String eventName, WritableMap params) {
        reactContext
                .getJSModule(RCTNativeAppEventEmitter.class)
                .emit(eventName, params);
    }

    public class SysObserver implements Observer {
        public void onSeek(byte[] byteChunk, long currentFileChunkIndex, long fileSize) {
            WritableMap data = Arguments.createMap();
            WritableArray byteChunkArr = Arguments.createArray();

            for (byte byteItem: byteChunk) {
                byteChunkArr.pushInt(byteItem);
            }
            data.putArray("bytes", byteChunkArr);
            data.putDouble("currentFileChunkIndex", (int)currentFileChunkIndex);
            data.putInt("fileSize", (int) fileSize);

            sendEvent("ReadChange", data);
        }
    }

    @ReactMethod
    public void observeFile(String dir, Promise promise) {
        Uri dirUri = Uri.parse(dir);
        String path = FileUtil.getFullPathFromTreeUri(dirUri, reactContext);

        Gotorndemo.observeFile(path, new SysObserver());
    }

    @ReactMethod
    public void goUp() {
        Gotorndemo.goUp();
    }

    @ReactMethod
    public void goDown() {
        Gotorndemo.goDown();
    }

    @ReactMethod
    public void stopObservingFile() {
        Gotorndemo.stopObservingFile();
    }

    @NonNull
    public String getName() {
        return "HelloModule";
    }
}
