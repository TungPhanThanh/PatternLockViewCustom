package com.tungpt.PatternLockView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawLine extends View implements IInterfaceChecked{
    Paint paint = new Paint();
    ArrayList<Line> lines = new ArrayList<Line>();
    int joinX, joinY = -1;
    int check = 0;
    List<Locations> listCircle = new ArrayList<>();
    private static IInterSendData iInterSendData;
    int dem = 0;
    int copyX = -1;
    int copyY = -1;
    int copyX1 = -1;
    int copyY1 = -1;
    float EPSILON = 0.001f;
    int count = 0;

    public DrawLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        GridViewPasscon.setInterfaceChecked(this);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (Line l : lines) {
            canvas.drawLine(l.startX, l.startY, l.stopX, l.stopY, paint);
        }
        if (listCircle.size() > 0) {
            for (int i = 0; i < listCircle.size(); i++) {
                Locations locations = listCircle.get(i);
                canvas.drawCircle(locations.getX(), locations.getY(), locations.getRadius(), paint);
            }
        }
    }

    public float getDistance(float x, float y, float x1, float y1) {
        return (float) Math.sqrt(Math.pow((x1 - x), 2) + Math.pow((y1 - y), 2));
    }

    public float getS(float P, float a, float b, float c) {
        return (float) Math.sqrt(P * (P - a) * (P - b) * (P - c));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dem = 0;
                count = 0;
                copyX = -2;
                copyY = -4;
                if (lines.size() > 0) {
                    lines.clear();
                }
                if (listCircle.size() > 0) {
                    listCircle.clear();
                }
                iInterSendData.sendData(-1, "", 0,"",0);

                for (int i = 0; i < GridViewPasscon.getListLocations().size(); i++) {
                    Locations locations = GridViewPasscon.getListLocations().get(i);
                    int xCenter = locations.getX();
                    int yCenter = locations.getY();
                    float radius = locations.getRadius();
                    String key = locations.getKey();
                    int hinhAnh = locations.getImage();
                    int id = locations.getId();
                    String hint = locations.getHint();
                    if (getDistance(event.getX(), event.getY(), xCenter, yCenter) <= radius) {
                        joinX = xCenter;
                        joinY = yCenter;
                        Log.d("hihi", "Key :" + key);
                        iInterSendData.sendData(dem, key, hinhAnh,hint,i);
                        dem++;
                        lines.add(new Line(xCenter, yCenter));
                        listCircle.add(new Locations(xCenter, yCenter, 7, key, hinhAnh,hint, id));
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (lines.size() > 0 && dem < 10) {
                    Line current = lines.get(lines.size() - 1);
                    current.stopX = (int) event.getX();
                    current.stopY = (int) event.getY();
                    check = 0;
                    String key = "";
                    int hinhAnh = 0;
                    int id = 0;
                    String hint = "";
                    //duyet mnag locatin de ve duong ke
                    for (int i = 0; i < GridViewPasscon.getListLocations().size(); i++) {
                        Locations locations = GridViewPasscon.getListLocations().get(i);
                        int xCenter = locations.getX();
                        int yCenter = locations.getY();
                        float radius = locations.getRadius();
                        if (getDistance(current.stopX, current.stopY, xCenter, yCenter) <= radius) {
                            current.stopX = xCenter;
                            current.stopY = yCenter;
                            copyX1 = xCenter;
                            copyY1 = yCenter;
                            check = 1;
                            lines.add(new Line(current.stopX, current.stopY));
                        }
                    }
                    //dung de ve cac cham tron
                    if (copyX1 != copyX || copyY1 != copyY) {

                        if (copyX1 < copyX || copyY1 < copyY) {
                            for (int i = GridViewPasscon.getListLocations().size() - 1; i >= 0; i--) {
                                Locations locations = GridViewPasscon.getListLocations().get(i);
                                key = locations.getKey();
                                hinhAnh = locations.getImage();
                                id = locations.getId();
                                hint = locations.getHint();
                                int xCenter = locations.getX();
                                int yCenter = locations.getY();
                                float a = getDistance(copyX1, copyY1, copyX, copyY);
                                float b = getDistance(copyX1, copyY1, xCenter, yCenter);
                                float c = getDistance(copyX, copyY, xCenter, yCenter);
                                float P = (a + b + c) / 2;
                                float S = getS(P, a, b, c);
                                if (S == 0 && b + c <= a && joinX != xCenter && joinY != yCenter &&
                                        (xCenter != copyX || yCenter != copyY)) {

                                    iInterSendData.sendData(dem, key, hinhAnh,hint,i);
                                    dem++;
                                    if (dem <= 10) {
                                        if (dem == 10)
                                        {
                                            current.stopX = xCenter;
                                            current.stopY = yCenter;
                                        }
                                        listCircle.add(new Locations(xCenter, yCenter, 7, key, hinhAnh,hint, id));
                                    }
                                }
                            }
                        } else {
                            for (int i = 0; i < GridViewPasscon.getListLocations().size(); i++) {
                                Locations locations = GridViewPasscon.getListLocations().get(i);
                                key = locations.getKey();
                                hinhAnh = locations.getImage();
                                id = locations.getId();
                                hint = locations.getHint();
                                int xCenter = locations.getX();
                                int yCenter = locations.getY();
                                float a = getDistance(copyX1, copyY1, copyX, copyY);
                                float b = getDistance(copyX1, copyY1, xCenter, yCenter);
                                float c = getDistance(copyX, copyY, xCenter, yCenter);
                                float P = (a + b + c) / 2;
                                float S = getS(P, a, b, c);
                                if (S == 0 && b + c <= a && joinX != xCenter && joinY != yCenter && (xCenter != copyX || yCenter != copyY)) {
                                    iInterSendData.sendData(dem, key, hinhAnh,hint,i);
                                    dem++;
                                    if (dem <= 10) {
                                        if (dem == 10)
                                        {
                                            current.stopX = xCenter;
                                            current.stopY = yCenter;
                                        }
                                        listCircle.add(new Locations(xCenter, yCenter, 7, key, hinhAnh,hint, id));
                                    }
                                }
                            }
                        }
                        copyX = copyX1;
                        copyY = copyY1;
                    }
                    joinX = (int) event.getX();
                    joinY = (int) event.getY();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (lines.size() > 0) {
                    if (check == 0) {
                        Line current1 = lines.remove(lines.size() - 1);
                        current1.stopX = joinX;
                        current1.stopY = joinY;
                    } else {
                        check = 0;
                    }
                }
                invalidate();
                break;
        }
        return true;
    }


    public static void setIInterface(IInterSendData iInterSendData) {
        DrawLine.iInterSendData = iInterSendData;
    }

    @Override
    public void onTabSelected() {
        lines.clear();
        listCircle.clear();
        invalidate();
    }

    public interface IInterSendData {
        void sendData(int position, String key, int hinhAnh, String hint,int index);
    }
}