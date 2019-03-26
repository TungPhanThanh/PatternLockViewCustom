package com.tungpt.PatternLockView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class CustomDrawLine extends AppCompatActivity implements View.OnClickListener {
    private PointF mPointA, mPointC;
    private PointF mPointB, mPointD;
    private ImageView mImageView1, mImageView2, mImageView3, mImageView4;
    private Paint mPaint;
    private float x1, y1, x2, y2;
    private Canvas canvas;
    private DrawLine mDrawLine;
    private Button mButton;
    private ConstraintLayout mConstraintLayout;
    private static List<Locations> mLocationsList;

    public static List<Locations> getmLocationsList() {
        return mLocationsList;
    }

    public static void setmLocationsList(List<Locations> mLocationsList) {
        CustomDrawLine.mLocationsList = mLocationsList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_line);
        setupLocation();
        ViewTreeObserver vto = mConstraintLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mConstraintLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                mLocationsList.add(getLocationPoint(mImageView1));
//                mLocationsList.add(getLocationPoint(mImageView2));
//                mLocationsList.add(getLocationPoint(mImageView3));
//                mLocationsList.add(getLocationPoint(mImageView4));
            }
        });


    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x1 = event.getX();
        y1 = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Log.d("event", "X: " + x1);
                Log.d("event", "Y: " + y1);
                break;
        }
        return true;
    }

//    private Locations getLocationPoint(View view)
//    {
//        float x = view.getX() + view.getWidth()/2;
//        float y = view.getY() +view.getHeight()/2;
//        return new Locations(x,y,view.getWidth()/2);
//    }

    public void setupLocation() {
        mImageView1 = findViewById(R.id.image_view_one);
        mImageView2 = findViewById(R.id.image_view_two);
        mImageView3 = findViewById(R.id.image_view_three);
        mImageView4 = findViewById(R.id.image_view_four);
        mConstraintLayout=(ConstraintLayout) findViewById(R.id.constraintlayout);
        mLocationsList=new ArrayList<>();
        mImageView1.setOnClickListener(this);
        mImageView2.setOnClickListener(this);
        mImageView3.setOnClickListener(this);
        mImageView4.setOnClickListener(this);
        x2 = mImageView1.getX();
        y2 = mImageView2.getY();
        Log.d("position", "setupLocation: " + x2 + "/" + y2);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_one:
                break;
            case R.id.image_view_two:
                break;
            case R.id.image_view_three:
                break;
            case R.id.image_view_four:
                break;
        }
    }
}