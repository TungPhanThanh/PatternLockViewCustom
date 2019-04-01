package com.tungpt.PatternLockView;

public class Locations {
    private int x;
    private int y;
    private float radius;
    private String key;
    private int image;
    private String hint;
    private int mId;

    public Locations() {

    }

    public Locations(int x, int y, float radius, String key, int image, String hint, int mId) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.key = key;
        this.image = image;
        this.mId = mId;
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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

    @Override
    public String toString() {
        return "Locations{" +
                "x=" + x +
                ", y=" + y +
                ", radius=" + radius +
                ", key='" + key + '\'' +
                ", image=" + image +
                ", hint='" + hint + '\'' +
                ", mId=" + mId +
                '}';
    }

}
