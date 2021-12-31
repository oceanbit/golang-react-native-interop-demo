package com.experimentgorn;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

import gotorndemo.Gotorndemo;

public class HelloModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;

    HelloModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @ReactMethod
    public void sayHello(Promise promise) {
        promise.resolve(Gotorndemo.sayHello());
    }

    @NonNull
    public String getName() {
        return "HelloModule";
    }
}
