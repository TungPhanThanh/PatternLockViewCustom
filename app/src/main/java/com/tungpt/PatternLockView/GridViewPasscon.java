package com.tungpt.PatternLockView;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GridViewPasscon extends AppCompatActivity implements AdapterGridViewSelectIcon.IILocations, DrawLine.IInterSendata {
    private List<IconPasscon> mIconPasscons;
    private AdapterGridViewSelectIcon mAdapterGridViewSelectIcon;
    private GridView mGridView;
    private RelativeLayout relativeLayout;
    private String mSavePattern;
    private String mPatternFinal;
    private Button imOne, imTwo, imThree, imFor,im5,im6,im7;
    private Button[] mImageViews;
    private int dem = 0;
    private int check = 0;
    private static  List<Locations> listLocations;

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
        DrawLine.setIIterface(this);
        mGridView = (GridView) findViewById(R.id.grid_view);
        imOne = (Button) findViewById(R.id.img_1);
        imTwo = (Button) findViewById(R.id.img_2);
        imThree = (Button) findViewById(R.id.img_3);
        imFor = (Button) findViewById(R.id.img_4);
        im5 = (Button) findViewById(R.id.img_5);
        im6 = (Button) findViewById(R.id.img_6);
        im7 = (Button) findViewById(R.id.img_7);
        relativeLayout=(RelativeLayout) findViewById(R.id.layout_main);
        listLocations=new ArrayList<>();
        mImageViews = new Button[7];
        mImageViews[0] = imOne;
        mImageViews[1] = imTwo;
        mImageViews[2] = imThree;
        mImageViews[3] = imFor;
        mImageViews[4] = im5;
        mImageViews[5] = im6;
        mImageViews[6] = im7;
        mIconPasscons = new ArrayList<>();
        mIconPasscons = DatabaseHelp.getListIcon();
        Collections.shuffle(mIconPasscons);
        for (int i = 0; i < mIconPasscons.size(); i++) {
            Log.d("value", "onCreate: " + mIconPasscons.get(i).getKey() + "/" + mIconPasscons.get(i).getHinhAnh() + "/" + mIconPasscons.get(i).getHint());
        }
        mAdapterGridViewSelectIcon = new AdapterGridViewSelectIcon(this, mIconPasscons,this);
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
    public void sendata(int position, String key,int hinhAnh) {
        Log.d("position", "sendData: " + position);
        if (position == -1) {
            for (int i = 0; i < mImageViews.length; i++) {
                mImageViews[i].setBackgroundResource(R.color.color_crystal_clear);
                mImageViews[i].setText("");
            }
        } else {
            if (position < 7) {
                if ((hinhAnh + "").length() > 1) {
                    mImageViews[position].setBackgroundResource(hinhAnh);
                } else {
                    mImageViews[position].setText(hinhAnh + "");
                }
            }
        }
    }
}
