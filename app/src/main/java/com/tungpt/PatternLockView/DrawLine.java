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

public class DrawLine extends View {
    Paint paint = new Paint();
    ArrayList<Line> lines = new ArrayList<Line>();
    int joinX, joinY = -1;
    int check = 0;
    List<Locations> listCircle = new ArrayList<>();
    private static IInterSendata iInterSendata;
    int dem = 0;
    int copyX = -1;
    int copyY = -1;
    int copyX1 = -1;
    int copyY1 = -1;
    float EPSILON = 0.001f;
    int count = 0;

    public DrawLine(Context context, AttributeSet attrs) {
        super(context, attrs);
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
                copyX = -1;
                copyY = -1;
                if (lines.size() > 0) {
                    lines.clear();
                }
                if (listCircle.size() > 0) {
                    listCircle.clear();
                }
                iInterSendata.sendata(-1, "", 0);
                for (int i = 0; i < GridViewPasscon.getListLocations().size(); i++) {
                    Locations locations = GridViewPasscon.getListLocations().get(i);
                    int xCenter = locations.getX();
                    int yCenter = locations.getY();
                    float radius = locations.getRadius();
                    if (getDistance(event.getX(), event.getY(), xCenter, yCenter) <= radius) {
                        joinX = xCenter;
                        joinY = yCenter;
                        lines.add(new Line(xCenter, yCenter));
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:

                if (lines.size() > 0 && dem < 6) {
                    Line current = lines.get(lines.size() - 1);
                    current.stopX = (int) event.getX();
                    current.stopY = (int) event.getY();
                    check = 0;
                    String key = "";
                    int hinhAnh = 0;
                    int id = 0;
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
                                hinhAnh = locations.getHinhAnh();
                                id = locations.getId();
                                int xCenter = locations.getX();
                                int yCenter = locations.getY();
                                Log.d("Position ", "onTouchEvent: " + xCenter + "/" + yCenter);
                                float a = getDistance(copyX1, copyY1, copyX, copyY);
                                float b = getDistance(copyX1, copyY1, xCenter, yCenter);
                                float c = getDistance(copyX, copyY, xCenter, yCenter);
                                float P = (a + b + c) / 2;
                                float S = getS(P, a, b, c);
                                if (S == 0 && b + c <= a && joinX != xCenter && joinY != yCenter &&
                                        (xCenter != copyX || yCenter != copyY)) {
                                    listCircle.add(new Locations(xCenter, yCenter, 7, key, hinhAnh, id));
                                    iInterSendata.sendata(dem, key, hinhAnh);
                                    dem++;
                                    Log.d("aaaa","ACTION_MOVE");
                                    Log.d("aaaa", "X:Y : " + xCenter + "/" + yCenter);
                                    Log.d("FIRST", "first touch: ");
                                }
                            }
                        } else {
                            for (int i = 0; i < GridViewPasscon.getListLocations().size(); i++) {
                                Locations locations = GridViewPasscon.getListLocations().get(i);
                                key = locations.getKey();
                                hinhAnh = locations.getHinhAnh();
                                id = locations.getId();
                                int xCenter = locations.getX();
                                int yCenter = locations.getY();
                                float a = getDistance(copyX1, copyY1, copyX, copyY);
                                float b = getDistance(copyX1, copyY1, xCenter, yCenter);
                                float c = getDistance(copyX, copyY, xCenter, yCenter);
                                float P = (a + b + c) / 2;
                                float S = getS(P, a, b, c);
                                if (S == 0 && b + c <= a && joinX != xCenter && joinY != yCenter && (xCenter != copyX || yCenter != copyY)) {

                                    listCircle.add(new Locations(xCenter, yCenter, 7, key, hinhAnh, id));
                                    iInterSendata.sendata(dem, key, hinhAnh);
                                    dem++;
                                    Log.d("aaaa","ACTION_MOVE1");
                                    Log.d("aaaa", "X:Y : " + xCenter + "/" + yCenter);
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
                if (dem == 0) {
                    listCircle.clear();
                }
                if (lines.size() > 0) {
                    if (check == 0) {
                        Line current1 = lines.remove(lines.size() - 1);
                        current1.stopX = joinX;
                        current1.stopY = joinY;
                    } else {
                        check = 0;
                    }
                    invalidate();
                }
                break;
        }
        return true;
    }


    public static void setIIterface(IInterSendata iInterSendata) {
        DrawLine.iInterSendata = iInterSendata;
    }

    public interface IInterSendata {
        void sendata(int position, String key, int hinhAnh);
    }
}