package com.tungpt.PatternLockView;

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

public class AdapterGridViewSelectIcon extends BaseAdapter{
    private Context context;
    private List<Locations> listIcon;
    private int dem=-1;
    private static  List<Locations> list;
    private IILocations iiLocations;

    public static List<Locations> getList() {
        return list;
    }

    public static void setList(List<Locations> list) {
        AdapterGridViewSelectIcon.list = list;
    }

    public AdapterGridViewSelectIcon(Context context, List<Locations> listIcon,IILocations iiLocations) {
        list=new ArrayList<>();
        this.context = context;
        this.listIcon = listIcon;
        this.iiLocations=iiLocations;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_gridview_selecticon, null);
        }
        final Locations iconPasscon = listIcon.get(position);
        final ImageView imIcon = (ImageView) convertView.findViewById(R.id.im_icon);
        TextView txtSelectIcon = (TextView) convertView.findViewById(R.id.txt_select_icon);
        final FrameLayout frameLayout=(FrameLayout) convertView.findViewById(R.id.layout_item_gridview);
        String flagHinhAnh = iconPasscon.getHinhAnh() + "";
        if (flagHinhAnh.length() > 1) {
            imIcon.setImageResource(iconPasscon.getHinhAnh());
        } else {
            imIcon.setVisibility(View.GONE);
            txtSelectIcon.setVisibility(View.VISIBLE);
            txtSelectIcon.setText(iconPasscon.getHinhAnh() + "");
            txtSelectIcon.setTextColor(context.getResources().getColor(R.color.white));
        }

        ViewTreeObserver vto = frameLayout.getViewTreeObserver();
        final View finalConvertView = convertView;
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                frameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if(position!=dem){
                    list.add(getLocationPoint(finalConvertView,iconPasscon.getKey(),iconPasscon.getHinhAnh(),position,iconPasscon.getHint()));
                    dem=position;
                }
                if(position==19){
                    iiLocations.sendListLocations(list);
                }
            }
        });

        return convertView;
    }
    private Locations getLocationPoint(View view,String key,int hinhAnh,int id,String hint)
    {
        int x = (int) (view.getX() + view.getWidth()/2);
        int y = (int) (view.getY() +view.getHeight()/2);
        return new Locations(x,y,view.getWidth()/2,key,hinhAnh,hint,id);
    }
    public interface IILocations{
        void sendListLocations(List<Locations> list);
    }
}
