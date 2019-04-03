package com.tungpt.PatternLockView;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatternLockView extends AppCompatActivity implements ILocations,
        ISendDataService, View.OnClickListener {

    private static List<Locations> sListPoint;
    private static List<Locations> sListPointOnMove;
    private static List<Locations> sListPointNoAction;
    private static ImageView[] mImageViews;
    private static TextView[] mTextViews;
    protected static Button mButtonReset;
    private static ISendDataService sInterfaceChecked;
    private List<Locations> mIconPasscons;
    private Animation mAnimation;
    private AdapterItemPatternLockView mAdapterGridViewSelectIcon;
    private GridView mGridView;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private ImageView img6;
    private ImageView img7;
    private ImageView img8;
    private ImageView img9;
    private ImageView img10;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text6;
    private TextView text7;
    private TextView text8;
    private TextView text9;
    private TextView text10;
    private ImageView mImageViewContent;
    private ImageView mImageViewBack;

    public static List<Locations> getListPoint() {
        return sListPoint;
    }

    public static void setListPoint(List<Locations> listPoint) {
        PatternLockView.sListPoint = listPoint;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_lock_view);
        DrawLine.setISendDataService(this);
        initView();
        mButtonReset.setEnabled(true);
        ScaleAnimation fade_in = new ScaleAnimation(
                0f,
                1f,
                0f,
                1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(600);
        fade_in.setFillAfter(true);
        mImageViewContent.startAnimation(fade_in);
    }

    public void initView() {
        sListPoint = new ArrayList<>();
        sListPointOnMove = new ArrayList<>();
        sListPointNoAction = new ArrayList<>();
        mIconPasscons = new ArrayList<>();
        mGridView = findViewById(R.id.grid_view);
        mImageViewContent = findViewById(R.id.img_content);
        mImageViewBack = findViewById(R.id.img_back_pattern_lock);
        mButtonReset = findViewById(R.id.btn_reset);

        img1 = findViewById(R.id.img_1);
        img2 = findViewById(R.id.img_2);
        img3 = findViewById(R.id.img_3);
        img4 = findViewById(R.id.img_4);
        img5 = findViewById(R.id.img_5);
        img6 = findViewById(R.id.img_6);
        img7 = findViewById(R.id.img_7);
        img8 = findViewById(R.id.img_8);
        img9 = findViewById(R.id.img_9);
        img10 = findViewById(R.id.img_10);

        text1 = findViewById(R.id.text_result1);
        text2 = findViewById(R.id.text_result2);
        text3 = findViewById(R.id.text_result3);
        text4 = findViewById(R.id.text_result4);
        text5 = findViewById(R.id.text_result5);
        text6 = findViewById(R.id.text_result6);
        text7 = findViewById(R.id.text_result7);
        text8 = findViewById(R.id.text_result8);
        text9 = findViewById(R.id.text_result9);
        text10 = findViewById(R.id.text_result10);

        mButtonReset.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);

        mImageViews = new ImageView[10];
        mImageViews[0] = img1;
        mImageViews[1] = img2;
        mImageViews[2] = img3;
        mImageViews[3] = img4;
        mImageViews[4] = img5;
        mImageViews[5] = img6;
        mImageViews[6] = img7;
        mImageViews[7] = img8;
        mImageViews[8] = img9;
        mImageViews[9] = img10;

        mTextViews = new TextView[10];
        mTextViews[0] = text1;
        mTextViews[1] = text2;
        mTextViews[2] = text3;
        mTextViews[3] = text4;
        mTextViews[4] = text5;
        mTextViews[5] = text6;
        mTextViews[6] = text7;
        mTextViews[7] = text8;
        mTextViews[8] = text9;
        mTextViews[9] = text10;

        mIconPasscons = PatternLockUtils.getListIcon();
        Collections.shuffle(mIconPasscons);
        for (int i = 0; i < mIconPasscons.size(); i++) {
            Locations locations = mIconPasscons.get(i);
            sListPoint.add(new Locations(0,
                    0,
                    0,
                    locations.getKey(),
                    locations.getImage(),
                    locations.getHint(),
                    i));
        }
        mIconPasscons = sListPoint;
        mAdapterGridViewSelectIcon = new AdapterItemPatternLockView(this, mIconPasscons, this);
        mGridView.setAdapter(mAdapterGridViewSelectIcon);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void sendListLocations(List<Locations> list) {
        setListPoint(list);
    }

    @Override
    public void sendData(int position, String key, int image, String hint, int index) {
        if (position == -1) {
            sListPointOnMove.clear();
            for (int i = 0; i < mImageViews.length; i++) {
                mImageViews[i].setVisibility(View.GONE);
                mTextViews[i].setVisibility(View.GONE);
            }

        } else if (position == -2) {

        } else {
            if (position < 10) {
                mAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.item_animation_right_side);
                sListPointOnMove.add(new Locations(0, 0, 0, key, image, hint, index));
                if ((image + "").length() > 1) {
                    mImageViews[position].setVisibility(View.VISIBLE);
                    mImageViews[position].startAnimation(mAnimation);
                    mImageViews[position].setImageResource(image);
                } else {
                    mTextViews[position].setVisibility(View.VISIBLE);
                    mTextViews[position].startAnimation(mAnimation);
                    mTextViews[position].setText(image + "");
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back_pattern_lock:
                finish();
                break;
            case R.id.btn_reset:
                sInterfaceChecked.ResetRequest();
                sListPointOnMove.clear();
                for (int i = 0; i < mImageViews.length; i++) {
                    mImageViews[i].setVisibility(View.GONE);
                    mTextViews[i].setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void ResetRequest() {

    }
    public static void setISendDataService(ISendDataService iSendDataService) {
        PatternLockView.sInterfaceChecked = iSendDataService;
    }
}
