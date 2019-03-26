package com.tungpt.PatternLockView;

public class Model {
    private String mTitle;
    private String mWebsite;
    private String mId;
    private String mPassword;

    public Model(String title, String website, String id, String password) {
        mTitle = title;
        mWebsite = website;
        mId = id;
        mPassword = password;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
