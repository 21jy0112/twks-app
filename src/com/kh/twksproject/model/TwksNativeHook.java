package com.kh.twksproject.model;

import java.io.IOException;

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

	public void nativeKeyPressed(NativeKeyEvent e) {
        KeyClick++;
    }

    
    public void nativeMousePressed(NativeMouseEvent e) { 
        MouseClick++;
    }


    public void nativeMouseMoved(NativeMouseEvent e) {
        MouseMove++;
    }

    
    public void nativeMouseDragged(NativeMouseEvent e) {
    	MouseMove++;
    }

    
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
    	MouseMove++;
    }

    public void appendDisplayKeyClick(final String output) {
    	try {
    		TwksUtility.doCreatTxtFile("KeyClick"+"20221220");
    		TwksUtility.doWriteTxtFile(output);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }
    
    public void appendDisplayMouseClick(final String output) {
    	try {
    		TwksUtility.doCreatTxtFile("MouseClick"+"20221220");
    		TwksUtility.doWriteTxtFile(output);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }
    
    public void appendDisplayMouseMove(final String output) {
    	try {
    		TwksUtility.doCreatTxtFile("MouseMove"+"20221220");
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