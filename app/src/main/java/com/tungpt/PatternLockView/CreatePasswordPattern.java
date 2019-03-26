package com.tungpt.PatternLockView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import io.paperdb.Paper;

public class CreatePasswordPattern extends AppCompatActivity {
    private String mSavePatternKey = "pattern_code";
    private String mSavePattern;
    private String mPatternFinal;
    private PatternLockView mPatternLockView;
    private Button mButtonSet, mButtonDel;

    public static Intent getIntent(Context context) {
        return new Intent(context, CreatePasswordPattern.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(this);
        mSavePattern = Paper.book().read(mSavePatternKey);
        if (mSavePattern != null && !mSavePattern.equals("null")) {
            setContentView(R.layout.create_password_pattern);
            mButtonDel = findViewById(R.id.delete_password_button);
            mButtonSet = findViewById(R.id.set_password_button);
            mPatternLockView = findViewById(R.id.pattern_view_create);
            mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    mPatternFinal = PatternLockUtils.patternToString(mPatternLockView, pattern);
                    if (mPatternFinal.equals(mSavePattern)) {
                        startActivity(DetailActivity.getIntent(CreatePasswordPattern.this));
                        Toast.makeText(CreatePasswordPattern.this, "Password Correct !", Toast.LENGTH_SHORT).show();
                        Log.d("aaa", "onComplete: " + mPatternFinal + "," + mSavePattern + "," + mSavePatternKey);
                    } else {
                        Toast.makeText(CreatePasswordPattern.this, "Password incorrect !", Toast.LENGTH_SHORT).show();
                        Log.d("aaa", "onComplete: " + mPatternFinal + "," + mSavePattern + "," + mSavePatternKey);
                    }
                }

                @Override
                public void onCleared() {

                }
            });
            mButtonSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paper.book().write(mSavePatternKey, mPatternFinal);
                    Toast.makeText(CreatePasswordPattern.this, "Set Success", Toast.LENGTH_SHORT).show();
                }
            });
            mButtonDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paper.book().destroy();
                }
            });

        } else {
            setContentView(R.layout.create_password_pattern);
            mButtonSet = findViewById(R.id.set_password_button);
            mButtonDel = findViewById(R.id.delete_password_button);
            mPatternLockView = findViewById(R.id.pattern_view_create);
            mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    mPatternFinal = PatternLockUtils.patternToString(mPatternLockView, pattern);
                }

                @Override
                public void onCleared() {

                }
            });
            mButtonSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paper.book().write(mSavePatternKey, mPatternFinal);
                    Toast.makeText(CreatePasswordPattern.this, "Set Success", Toast.LENGTH_SHORT).show();
                }
            });
            mButtonDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paper.book().destroy();
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
