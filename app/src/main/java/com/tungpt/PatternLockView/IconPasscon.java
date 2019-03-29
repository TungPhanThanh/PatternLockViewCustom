package com.tungpt.PatternLockView;

import java.io.Serializable;

public class IconPasscon implements Serializable {

    private String key;
    private int hinhAnh;
    private String hint;
    private int position;

    public IconPasscon() {

    }

    public IconPasscon(String key, int hinhAnh, String hint, int postion) {
        this.key = key;
        this.hinhAnh = hinhAnh;
        this.hint = hint;
        this.position = postion;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
