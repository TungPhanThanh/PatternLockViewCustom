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
import android.widget.RelativeLayout;

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
    private RelativeLayout relativeLayout;
    private Button imOne, imTwo, imThree, imFour, im5, im6, im7;
    private Button btn_reset;
    private Button[] mImageViews;
    private Button mButtonTap, mButtonSwipe;
    private View mViewDrawLine;
    private ImageView mImageViewContent;
    private int dem = 0;
    private int check = 0;
    private static List<Locations> listLocations;
    private static List<Locations> listLocationA;
    private static List<Locations> listLocationB;
    private List<Locations> locationsListC;

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

    }

    public void initView() {
        listLocations = new ArrayList<>();
        mGridView = (GridView) findViewById(R.id.grid_view);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout_main);
        mViewDrawLine = findViewById(R.id.view_draw_line);
        mImageViewContent = findViewById(R.id.img_content);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        imOne = (Button) findViewById(R.id.img_1);
        imTwo = (Button) findViewById(R.id.img_2);
        imThree = (Button) findViewById(R.id.img_3);
        imFour = (Button) findViewById(R.id.img_4);
        im5 = (Button) findViewById(R.id.img_5);
        im6 = (Button) findViewById(R.id.img_6);
        im7 = (Button) findViewById(R.id.img_7);
        mButtonTap = findViewById(R.id.test1_button);
        mButtonSwipe = findViewById(R.id.test2_button);
        mButtonTap.setOnClickListener(this);
        mButtonSwipe.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        mButtonSwipe.performClick();
        mImageViews = new Button[7];
        mImageViews[0] = imOne;
        mImageViews[1] = imTwo;
        mImageViews[2] = imThree;
        mImageViews[3] = imFour;
        mImageViews[4] = im5;
        mImageViews[5] = im6;
        mImageViews[6] = im7;

        listLocationA = new ArrayList<>();
        listLocationB = new ArrayList<>();
        locationsListC = new ArrayList<>();
        mIconPasscons = new ArrayList<>();
        mIconPasscons = DatabaseHelp.getListIcon();

        Collections.shuffle(mIconPasscons);
        for (int i = 0; i < mIconPasscons.size(); i++) {
            Locations locations = mIconPasscons.get(i);
            listLocations.add(new Locations(0, 0, 0, locations.getKey(), locations.getHinhAnh(), locations.getHint(), i));
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
    public void sendData(int position, String key, int hinhAnh, String hint, int index) {
        if (position == -1) {
            listLocationA.clear();
            for (int i = 0; i < mImageViews.length; i++) {
                mImageViews[i].setBackgroundResource(R.color.color_crystal_clear);
                mImageViews[i].setText("");
            }
        } else {
            if (position < 7) {
                mAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.item_animation_right_side);
                listLocationA.add(new Locations(0, 0, 0, key, hinhAnh, hint, index));
                if ((hinhAnh + "").length() > 1) {
                    mImageViews[position].startAnimation(mAnimation);
                    mImageViews[position].setBackgroundResource(hinhAnh);
                } else {
                    mImageViews[position].startAnimation(mAnimation);
                    mImageViews[position].setText(hinhAnh + "");
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
                        listLocationB.add(new Locations(0, 0, 0, location.getKey(), location.getHinhAnh(), location.getHint(), i));
                    }
                }
                //random B
                Collections.shuffle(listLocationB);
                for (int i = 0; i < listLocationB.size(); i++) {
                    Locations location = listLocationB.get(i);
                    locationsListC.add(new Locations(0, 0, 0, location.getKey(), location.getHinhAnh(), location.getHint(), i));
                }
                for (int i = 0; i < listLocations.size(); i++) {
                    int dem = 0;
                    Locations location=null;
                    if (locationsListC.size() !=0)
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
                    if (dem!=1)
                    {
                        locationsListC.remove(0);
                    }
                }
                mIconPasscons = listLocations;
                mAdapterGridViewSelectIcon = new AdapterGridViewSelectIcon(this, mIconPasscons, this);
                mGridView.setAdapter(mAdapterGridViewSelectIcon);
                break;
            default:
                break;

        }
    }

    public static void setInterfaceChecked(IInterfaceChecked sInterfaceChecked){
        GridViewPasscon.sInterfaceChecked = sInterfaceChecked;
    }
}
