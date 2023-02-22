package com.kh.twksproject.model;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

public class TwksNativeHookUtility {
    private long KeyClick;
    private long MouseClick;
    private long MouseMove;

    public static void startListening(TwksNativeHook a) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(a);
        GlobalScreen.addNativeMouseListener(a);
        GlobalScreen.addNativeMouseMotionListener(a);
        GlobalScreen.addNativeMouseWheelListener(a);
    }

    public static void stopListening(TwksNativeHook a) {
        GlobalScreen.removeNativeKeyListener(a);
        GlobalScreen.removeNativeMouseListener(a);
        GlobalScreen.removeNativeMouseMotionListener(a);
        GlobalScreen.removeNativeMouseWheelListener(a);
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }



}
