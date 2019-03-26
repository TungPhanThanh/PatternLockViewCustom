package com.tungpt.PatternLockView;

public class Locations {
    private int x;
    private int y;
    private float radius;
    private String key;
    private int hinhAnh;
    private int mId;

    public Locations() {

    }

    public Locations(int x, int y, float radius,String key,int hinhAnh, int mId) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.key=key;
        this.hinhAnh=hinhAnh;
        this.mId = mId;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
