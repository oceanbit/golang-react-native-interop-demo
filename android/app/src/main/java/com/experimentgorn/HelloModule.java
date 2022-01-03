package com.experimentgorn;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

import gotorndemo.Gotorndemo;
import gotorndemo.ValueMaybe;

public class HelloModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;

    HelloModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @ReactMethod
    public void readFile(String path, Promise promise) {
        ValueMaybe fileRead = Gotorndemo.readFile(path);
        String err = fileRead.getError();
        String val = fileRead.getValue();
        if (!err.isEmpty()) {
            promise.reject(new Exception(err));
        } else {
            promise.resolve(val);
        }
    }

    @ReactMethod
    public void writeFile(String path, Promise promise) {
        ValueMaybe fileWrite  = Gotorndemo.writeFile(path);
        String err = fileWrite.getError();
        String val = fileWrite.getValue();
        if (!err.isEmpty()) {
            promise.reject(new Exception(err));
        } else {
            promise.resolve(val);
        }
    }

    @NonNull
    public String getName() {
        return "HelloModule";
    }
}
