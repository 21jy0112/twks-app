package com.kh.twksproject.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelListener;

public class TwksNativeHook implements NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener {

    private long KeyClick ;
    private long MouseClick;
    private long MouseMove;

    private SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
    private String day = dayFormat.format(new Date());

    public TwksNativeHook() {
        KeyClick = 0;
        MouseClick = 0;
        MouseMove = 0;
    }


    public long getKeyClick() {
        return KeyClick;
    }


    public void setKeyClick(long keyClick) {
        KeyClick = keyClick;
    }


    public long getMouseClick() {
        return MouseClick;
    }


    public void setMouseClick(long mouseClick) {
        MouseClick = mouseClick;
    }


    public long getMouseMove() {
        return MouseMove;
    }


    public void setMouseMove(long mouseMove) {
        MouseMove = mouseMove;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        KeyClick++;
    }


    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        MouseClick++;
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        MouseMove++;
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        MouseMove++;
    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        MouseMove++;
    }

    public void appendDisplayKeyClick(final String output) {
        try {
            TwksUtility.doCreatTxtFile("KeyClick"+day);
            TwksUtility.doWriteTxtFile(output);
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    public void appendDisplayMouseClick(final String output) {
        try {
            TwksUtility.doCreatTxtFile("MouseClick"+day);
            TwksUtility.doWriteTxtFile(output);
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    public void appendDisplayMouseMove(final String output) {
        try {
            TwksUtility.doCreatTxtFile("MouseMove"+day);
            TwksUtility.doWriteTxtFile(output);
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    public void doRecord() {
        appendDisplayMouseMove(String.valueOf(getMouseMove()));
        appendDisplayMouseClick(String.valueOf(getMouseClick()));
        appendDisplayKeyClick(String.valueOf(getKeyClick()));
    }

    public void recordReturnToZero() {
        setMouseMove(0);
        setMouseClick(0);
        setKeyClick(0);
    }

}