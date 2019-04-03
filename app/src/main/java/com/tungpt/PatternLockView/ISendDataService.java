package com.tungpt.PatternLockView;

public interface ISendDataService {
    void sendData(int position, String key, int image, String hint, int index);

    void ResetRequest();
}
