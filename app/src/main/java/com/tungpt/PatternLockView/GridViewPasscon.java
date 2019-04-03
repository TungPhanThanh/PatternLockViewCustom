package com.tungpt.PatternLockView;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class GridViewPasscon extends AppCompatActivity implements AdapterGridViewSelectIcon.IILocations,
        DrawLine.IInterSendData, View.OnClickListener {
    private static IInterfaceChecked sInterfaceChecked;
    private List<Locations> mIconPasscons;
    private Animation mAnimation;
    private AdapterGridViewSelectIcon mAdapterGridViewSelectIcon;
    private GridView mGridView;
    private ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10;
    private TextView text1, text2, text3, text4, text5, text6, text7, text8, text9, text10;
    private Button mButtonReset, mButtonConfirm;
    private ImageView[] mImageViews;
    private TextView[] mTextViews;
    private Button mButtonTap, mButtonSwipe;
    private View mViewDrawLine;
    private ImageView mImageViewContent;
    private static List<Locations> listLocations;
    private static List<Locations> listLocationA;
    private static List<Locations> listLocationB;
    private List<Locations> locationsListC;
    private List<Integer> arrayPassword;
    private List<Integer> arrayConfirm;
    private int count;


    public static List<Locations> getListLocations() {
        return listLocations;
    }

    public static void setListLocations(List<Locations> listLocations) {
        GridViewPasscon.listLocations = listLocations;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_passcon);
        DrawLine.setIInterface(this);
        initView();
        mButtonReset.setEnabled(true);

    }

    public void initView() {
        listLocations = new ArrayList<>();
        mGridView = findViewById(R.id.grid_view);
        mViewDrawLine = findViewById(R.id.view_draw_line);
        mImageViewContent = findViewById(R.id.img_content);
        mButtonReset = findViewById(R.id.btn_reset);
        mButtonConfirm = findViewById(R.id.btn_confirm);

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

        mButtonTap = findViewById(R.id.test1_button);
        mButtonSwipe = findViewById(R.id.test2_button);

        mButtonTap.setOnClickListener(this);
        mButtonSwipe.setOnClickListener(this);
        mButtonReset.setOnClickListener(this);
        mButtonConfirm.setOnClickListener(this);
        mButtonSwipe.performClick();

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

        listLocationA = new ArrayList<>();
        listLocationB = new ArrayList<>();
        locationsListC = new ArrayList<>();
        mIconPasscons = new ArrayList<>();
        mIconPasscons = DatabaseHelp.getListIcon();

        arrayPassword = new ArrayList<Integer>();
        arrayConfirm = new ArrayList<Integer>();

        Collections.shuffle(mIconPasscons);
        for (int i = 0; i < mIconPasscons.size(); i++) {
            Locations locations = mIconPasscons.get(i);
            listLocations.add(new Locations(0,
                    0,
                    0,
                    locations.getKey(),
                    locations.getImage(),
                    locations.getHint(),
                    i));
        }
        mIconPasscons = listLocations;
        mAdapterGridViewSelectIcon = new AdapterGridViewSelectIcon(this, mIconPasscons, this);
        mGridView.setAdapter(mAdapterGridViewSelectIcon);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void sendListLocations(List<Locations> list) {
        setListLocations(list);
    }

    @Override
    public void sendData(int position, String key, int image, String hint, int index) {
        if (position == -1) {
            listLocationA.clear();
            arrayConfirm.clear();
            for (int i = 0; i < mImageViews.length; i++) {
                mImageViews[i].setVisibility(View.GONE);
                mTextViews[i].setVisibility(View.GONE);

            }

        } else {
            if (position < 10) {
                mAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.item_animation_right_side);
                listLocationA.add(new Locations(0, 0, 0, key, image, hint, index));
                Log.d("array", "sendData: " + index);
                if ((image + "").length() > 1) {
                    mImageViews[position].setVisibility(View.VISIBLE);
                    mImageViews[position].startAnimation(mAnimation);
                    mImageViews[position].setImageResource(image);
                } else {
                    mTextViews[position].setVisibility(View.VISIBLE);
                    mTextViews[position].startAnimation(mAnimation);
                    mTextViews[position].setText(image + "");
                }

                if (count == 1) {
                    arrayConfirm.add(index);
                }
                if ((position + 1) == arrayPassword.size() && compareArray() == true) {
                    startActivity(MainActivity.getIntent(this));
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test1_button:
                mButtonTap.setSelected(true);
                mButtonSwipe.setSelected(false);
                mButtonTap.setClickable(false);
                mButtonSwipe.setClickable(true);
                mViewDrawLine.setVisibility(View.GONE);
                ScaleAnimation fade_out = new ScaleAnimation(
                        1f,
                        0f,
                        1f,
                        0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                fade_out.setDuration(10);
                fade_out.setFillAfter(true);
                mImageViewContent.startAnimation(fade_out);
//                mImageViewContent.clearAnimation();
                break;
            case R.id.test2_button:
                mButtonTap.setSelected(false);
                mButtonSwipe.setSelected(true);
                mButtonTap.setClickable(true);
                mButtonSwipe.setClickable(false);
                mViewDrawLine.setVisibility(View.VISIBLE);
                ScaleAnimation fade_in = new ScaleAnimation(
                        0f,
                        1f,
                        0f,
                        1f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                fade_in.setDuration(150);
                fade_in.setFillAfter(true);
                mImageViewContent.startAnimation(fade_in);
                sInterfaceChecked.onTabSelected();
                break;
            case R.id.btn_reset:
                sInterfaceChecked.onTabSelected();
                listLocationA.clear();
                for (int i = 0; i < mImageViews.length; i++) {
                    mImageViews[i].setVisibility(View.GONE);
                    mTextViews[i].setVisibility(View.GONE);
                }
//                randomPattern();
                break;
            case R.id.btn_confirm:
                mButtonReset.setEnabled(false);
                if (count == 0) {
                    for (int i = 0; i < listLocationA.size(); i++) {
                        arrayPassword.add(listLocationA.get(i).getmId());
                    }
                    count = 1;
                    arrayConfirm.clear();
                    sInterfaceChecked.onTabSelected();
                    listLocationA.clear();
                    for (int i = 0; i < mImageViews.length; i++) {
                        mImageViews[i].setVisibility(View.GONE);
                        mTextViews[i].setVisibility(View.GONE);
                    }
                }
            default:
                break;

        }
    }

    public void randomPattern() {
        listLocationB.clear();
        locationsListC.clear();
        for (int i = 0; i < mIconPasscons.size(); i++) {
            int dem = 0;
            Locations location = mIconPasscons.get(i);
            for (int j = 0; j < listLocationA.size(); j++) {
                Locations locations = listLocationA.get(j);
                if (i == locations.getmId()) {
                    dem++;
                }
            }
            if (dem == 0) {
                listLocationB.add(new Locations(0,
                        0,
                        0,
                        location.getKey(),
                        location.getImage(),
                        location.getHint(), i));
            }
        }
        //random B
        Collections.shuffle(listLocationB);
        for (int i = 0; i < listLocationB.size(); i++) {
            Locations location = listLocationB.get(i);
            locationsListC.add(new Locations(0,
                    0,
                    0,
                    location.getKey(),
                    location.getImage(),
                    location.getHint(), i));
        }
        for (int i = 0; i < listLocations.size(); i++) {
            int dem = 0;
            Locations location = null;
            if (locationsListC.size() != 0)
                location = locationsListC.get(0);
            for (int j = 0; j < listLocationA.size(); j++) {
                Locations locations = listLocationA.get(j);
                if (i == locations.getmId()) {
                    dem = 1;
                    location = locations;
                }
            }
            location.setmId(i);
            listLocations.set(i, location);
            if (dem != 1) {
                locationsListC.remove(0);
            }
        }
        mIconPasscons = listLocations;
        mAdapterGridViewSelectIcon = new AdapterGridViewSelectIcon(this, mIconPasscons, this);
        mGridView.setAdapter(mAdapterGridViewSelectIcon);
    }

    public boolean compareArray() {
        int check = 0;
        if (arrayConfirm.size() == arrayPassword.size()) {
            for (int i = 0; i < arrayPassword.size(); i++) {
                if (arrayPassword.get(i) == arrayConfirm.get(i)) {
                    check++;
                }
            }
            if (check == arrayConfirm.size())
                return true;
        }
        return false;
    }

    public static void setInterfaceChecked(IInterfaceChecked sInterfaceChecked) {
        GridViewPasscon.sInterfaceChecked = sInterfaceChecked;
    }
}
