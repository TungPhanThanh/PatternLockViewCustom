package com.tungpt.PatternLockView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterItemPatternLockView extends BaseAdapter {
    private Context context;
    private List<Locations> listIcon;
    private static List<Locations> listPoints;
    private ILocations iLocations;
    private int count = -1;

    public static List<Locations> getListPoints() {
        return listPoints;
    }

    public static void setListPoints(List<Locations> listPoints) {
        AdapterItemPatternLockView.listPoints = listPoints;
    }

    public AdapterItemPatternLockView(Context context, List<Locations> listIcon, ILocations iLocations) {
        listPoints = new ArrayList<>();
        this.context = context;
        this.listIcon = listIcon;
        this.iLocations = iLocations;
    }

    @Override
    public int getCount() {
        return listIcon.size();
    }

    @Override
    public Object getItem(int position) {
        return listIcon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.item_gridview_selecticon, null);
        }
        final Locations iconPasscon = listIcon.get(position);
        final ImageView imIcon = convertView.findViewById(R.id.im_icon);
        TextView txtSelectIcon = convertView.findViewById(R.id.txt_select_icon);
        final FrameLayout frameLayout = convertView.findViewById(R.id.layout_item_gridview);
        String imgFlag = iconPasscon.getImage() + "";
        if (imgFlag.length() > 1) {
            imIcon.setImageResource(iconPasscon.getImage());
        } else {
            imIcon.setVisibility(View.GONE);
            txtSelectIcon.setVisibility(View.VISIBLE);
            txtSelectIcon.setText(iconPasscon.getImage() + "");
            txtSelectIcon.setTextColor(context.getResources().getColor(R.color.white));
        }

        ViewTreeObserver vto = frameLayout.getViewTreeObserver();
        final View finalConvertView = convertView;
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                frameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (position != count) {
                    listPoints.add(getLocationPoint(finalConvertView, iconPasscon.getKey(), iconPasscon.getImage(), position, iconPasscon.getHint()));
                    count = position;
                }
                if (position == 19) {
                    iLocations.sendListLocations(listPoints);
                }
            }
        });
        return convertView;
    }
    private Locations getLocationPoint(View view, String key, int image, int id, String hint) {
        int x = (int) (view.getX() + view.getWidth() / 2);
        int y = (int) (view.getY() + view.getHeight() / 2);
        return new Locations(x, y, view.getWidth() / 2, key, image, hint, id);
    }
}
