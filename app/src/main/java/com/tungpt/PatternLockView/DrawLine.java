package com.tungpt.PatternLockView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawLine extends View implements ISendDataService {
    private final float RADIUS = getResources().getDimension(R.dimen._5sdp);
    private Line current;
    private Locations locations;
    private Paint paint = new Paint();
    private Paint pCircle = new Paint();
    private List<Locations> listCircle = new ArrayList<>();
    private ArrayList<Line> lines = new ArrayList<>();
    private boolean indexes = true;
    private int joinX = -1;
    private int joinY = -1;
    private int check = 0;
    private static ISendDataService sISendDataService;
    private int mCount = 0;
    private int copyX = -1;
    private int copyY = -1;
    private int copyX1 = -1;
    private int copyY1 = -1;
    private int mRadiusPoint;
    private String keyOnMove;
    private int imageOnMove;
    private int idOnMove;
    private String hintOnMove;

    public DrawLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        PatternLockView.setISendDataService(this);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircles(canvas);
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

    public float getDistance(float x, float y, int x1, int y1) {
        return (int) Math.sqrt(Math.pow((x1 - x), 2) + Math.pow((y1 - y), 2));
    }

    public int getS(int P, int a, int b, int c) {
        return (int) Math.sqrt(P * (P - a) * (P - b) * (P - c));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onActionDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                onActionMove(event);
                break;
            case MotionEvent.ACTION_UP:
                onActionUp();
                break;
        }
        return true;
    }

    public void onActionDown(MotionEvent event) {
        if (mCount > 4) {
            sISendDataService.sendData(-2, "", 0, "", 0);
            mCount = 10;
        } else {
            paint.setColor(Color.WHITE);
            indexes = true;
            mCount = 0;
            copyX = 0;
            copyY = -10000;
            if (lines.size() > 0) {
                lines.clear();
            }
            if (listCircle.size() > 0) {
                listCircle.clear();
            }
            sISendDataService.sendData(-1, "", 0, "", 0);
            for (int i = 0; i < PatternLockView.getListPoint().size(); i++) {
                Locations locations = PatternLockView.getListPoint().get(i);
                int xCenter = locations.getX();
                int yCenter = locations.getY();
                mRadiusPoint = (int) locations.getRadius();
                String keyOnDown = locations.getKey();
                int imageOnDown = locations.getImage();
                int idOnDown = locations.getId();
                String hintOnDown = locations.getHint();
                if (getDistance(event.getX(), event.getY(), xCenter, yCenter) <= mRadiusPoint / 2) {
                    joinX = xCenter;
                    joinY = yCenter;
                    sISendDataService.sendData(mCount, keyOnDown, imageOnDown, hintOnDown, i);
                    mCount++;
                    lines.add(new Line(xCenter, yCenter));
                    listCircle.add(new Locations(xCenter, yCenter, RADIUS, keyOnDown, imageOnDown, hintOnDown, idOnDown));
                }
            }
            invalidate();
        }
    }

    public void onActionMove(MotionEvent event) {
        if (lines.size() > 0 && mCount < 10 && indexes) {
//            String keyOnMove;
//            int imageOnMove;
//            int idOnMove;
//            String hintOnMove;
            check = 0;
            current = lines.get(lines.size() - 1);
            current.stopX = (int) event.getX();
            current.stopY = (int) event.getY();

            //Browse array to draw line
            for (int i = 0; i < PatternLockView.getListPoint().size(); i++) {
                locations = PatternLockView.getListPoint().get(i);
                int xCenter = locations.getX();
                int yCenter = locations.getY();
                mRadiusPoint = (int) locations.getRadius();
                if (getDistance(current.stopX, current.stopY, xCenter, yCenter) <= mRadiusPoint / 2) {
                    current.stopX = xCenter;
                    current.stopY = yCenter;
                    copyX1 = xCenter;
                    copyY1 = yCenter;
                    check = 1;
                    lines.add(new Line(current.stopX, current.stopY));
                }
            }
            //Draw Points
            if (copyX1 != copyX || copyY1 != copyY) {
                if (copyX <= copyX1 && copyY <= copyY1) {
                    drawFromStart();
//                    for (int i = 0; i < PatternLockView.getListPoint().size(); i++) {
//                        Locations locations = PatternLockView.getListPoint().get(i);
//                        keyOnMove = locations.getKey();
//                        imageOnMove = locations.getImage();
//                        idOnMove = locations.getId();
//                        hintOnMove = locations.getHint();
//                        int xCenter = locations.getX();
//                        int yCenter = locations.getY();
//                        int a = (int) getDistance(copyX1, copyY1, copyX, copyY);
//                        int b = (int) getDistance(copyX1, copyY1, xCenter, yCenter);
//                        int c = (int) getDistance(copyX, copyY, xCenter, yCenter);
//                        int P = (a + b + c) / 2;
//                        int S = getS(P, a, b, c);
//                        if (S == 0 && b + c <= a && joinX != xCenter && joinY != yCenter &&
//                                (xCenter != copyX || yCenter != copyY)) {
//                            sISendDataService.sendData(mCount, keyOnMove, imageOnMove, hintOnMove, i);
//                            mCount++;
//                            if (mCount <= 10) {
//                                if (mCount == 10) {
//                                    current.stopX = xCenter;
//                                    current.stopY = yCenter;
//                                }
//                                listCircle.add(new Locations(xCenter, yCenter, RADIUS, keyOnMove, imageOnMove, hintOnMove, idOnMove));
//                            }
//                        }
//                    }
                } else if (copyY > copyY1) {
                    drawReverse();
//                    for (int i = PatternLockView.getListPoint().size() - 1; i >= 0; i--) {
//                        Locations locations = PatternLockView.getListPoint().get(i);
//                        keyOnMove = locations.getKey();
//                        imageOnMove = locations.getImage();
//                        idOnMove = locations.getId();
//                        hintOnMove = locations.getHint();
//                        int xCenter = locations.getX();
//                        int yCenter = locations.getY();
//                        int a = (int) getDistance(copyX1, copyY1, copyX, copyY);
//                        int b = (int) getDistance(copyX1, copyY1, xCenter, yCenter);
//                        int c = (int) getDistance(copyX, copyY, xCenter, yCenter);
//                        int P = (a + b + c) / 2;
//                        int S = getS(P, a, b, c);
//                        if (S == 0 && b + c <= a && joinX != xCenter && joinY != yCenter &&
//                                (xCenter != copyX || yCenter != copyY)) {
//                            sISendDataService.sendData(mCount, keyOnMove, imageOnMove, hintOnMove, i);
//                            mCount++;
//                            if (mCount <= 10) {
//                                if (mCount == 10) {
//                                    current.stopX = xCenter;
//                                    current.stopY = yCenter;
//                                }
//                                listCircle.add(new Locations(xCenter, yCenter, RADIUS, keyOnMove, imageOnMove, hintOnMove, idOnMove));
//                            }
//                        }
//                    }
                } else if (copyY < copyY1) {
//                    for (int i = 0; i < PatternLockView.getListPoint().size(); i++) {
//                        Locations locations = PatternLockView.getListPoint().get(i);
//                        keyOnMove = locations.getKey();
//                        imageOnMove = locations.getImage();
//                        idOnMove = locations.getId();
//                        hintOnMove = locations.getHint();
//                        int xCenter = locations.getX();
//                        int yCenter = locations.getY();
//                        int a = (int) getDistance(copyX1, copyY1, copyX, copyY);
//                        int b = (int) getDistance(copyX1, copyY1, xCenter, yCenter);
//                        int c = (int) getDistance(copyX, copyY, xCenter, yCenter);
//                        int P = (a + b + c) / 2;
//                        int S = getS(P, a, b, c);
//                        if (S == 0 && b + c <= a && joinX != xCenter && joinY != yCenter &&
//                                (xCenter != copyX || yCenter != copyY)) {
//                            sISendDataService.sendData(mCount, keyOnMove, imageOnMove, hintOnMove, i);
//                            mCount++;
//                            if (mCount <= 10) {
//                                if (mCount == 10) {
//                                    current.stopX = xCenter;
//                                    current.stopY = yCenter;
//                                }
//                                listCircle.add(new Locations(xCenter, yCenter, RADIUS, keyOnMove, imageOnMove, hintOnMove, idOnMove));
//                            }
//                        }
//                    }
                    drawFromStart();
                } else {
                    drawReverse();
//                    for (int i = PatternLockView.getListPoint().size() - 1; i >= 0; i--) {
//                        Locations locations = PatternLockView.getListPoint().get(i);
//                        keyOnMove = locations.getKey();
//                        imageOnMove = locations.getImage();
//                        idOnMove = locations.getId();
//                        hintOnMove = locations.getHint();
//                        int xCenter = locations.getX();
//                        int yCenter = locations.getY();
//                        int a = (int) getDistance(copyX1, copyY1, copyX, copyY);
//                        int b = (int) getDistance(copyX1, copyY1, xCenter, yCenter);
//                        int c = (int) getDistance(copyX, copyY, xCenter, yCenter);
//                        int P = (a + b + c) / 2;
//                        int S = getS(P, a, b, c);
//                        if (S == 0 && b + c <= a && joinX != xCenter && joinY != yCenter &&
//                                (xCenter != copyX || yCenter != copyY)) {
//                            sISendDataService.sendData(mCount, keyOnMove, imageOnMove, hintOnMove, i);
//                            mCount++;
//                            if (mCount <= 10) {
//                                if (mCount == 10) {
//                                    current.stopX = xCenter;
//                                    current.stopY = yCenter;
//                                }
//                                listCircle.add(new Locations(xCenter, yCenter, RADIUS, keyOnMove, imageOnMove, hintOnMove, idOnMove));
//                            }
//                        }
//                    }
                }
                copyX = copyX1;
                copyY = copyY1;
            }
            joinX = (int) event.getX();
            joinY = (int) event.getY();
            invalidate();
        }
    }

    public void onActionUp() {
        indexes = false;
        if (lines.size() > 0 && mCount != 10) {
            if (check == 0) {
                Line lastLine = lines.remove(lines.size() - 1);
                lastLine.stopX = joinX;
                lastLine.stopY = joinY;
            } else {
                check = 0;
            }
        }
        invalidate();
    }

    public void drawCircles(Canvas canvas) {
        for (int i = 0; i < PatternLockView.getListPoint().size(); i++) {
            locations = PatternLockView.getListPoint().get(i);
            int xCenter = locations.getX();
            int yCenter = locations.getY();
            switch (i) {
                case 0:
                case 4:
                case 15:
                case 19:
                    pCircle.setColor(Color.WHITE);
                    canvas.drawCircle(xCenter, yCenter, RADIUS, pCircle);
                    break;
                case 1:
                case 2:
                case 3:
                case 5:
                case 6:
                case 7:
                case 9:
                case 11:
                    pCircle.setColor(getResources().getColor(R.color.color_red_flag));
                    canvas.drawCircle(xCenter, yCenter, RADIUS, pCircle);
                    break;
                case 8:
                case 10:
                case 12:
                case 13:
                case 14:
                case 16:
                case 17:
                case 18:
                    pCircle.setColor(getResources().getColor(R.color.color_blue_flag));
                    canvas.drawCircle(xCenter, yCenter, RADIUS, pCircle);
                    break;
                default:
                    break;
            }
        }
    }

    public void drawFromStart(){
        for (int i = 0; i < PatternLockView.getListPoint().size(); i++) {
            Locations locations = PatternLockView.getListPoint().get(i);
            keyOnMove = locations.getKey();
            imageOnMove = locations.getImage();
            idOnMove = locations.getId();
            hintOnMove = locations.getHint();
            int xCenter = locations.getX();
            int yCenter = locations.getY();
            int a = (int) getDistance(copyX1, copyY1, copyX, copyY);
            int b = (int) getDistance(copyX1, copyY1, xCenter, yCenter);
            int c = (int) getDistance(copyX, copyY, xCenter, yCenter);
            int P = (a + b + c) / 2;
            int S = getS(P, a, b, c);
            if (S == 0 && b + c <= a && joinX != xCenter && joinY != yCenter &&
                    (xCenter != copyX || yCenter != copyY)) {
                sISendDataService.sendData(mCount, keyOnMove, imageOnMove, hintOnMove, i);
                mCount++;
                if (mCount <= 10) {
                    if (mCount == 10) {
                        current.stopX = xCenter;
                        current.stopY = yCenter;
                    }
                    listCircle.add(new Locations(xCenter, yCenter, RADIUS, keyOnMove, imageOnMove, hintOnMove, idOnMove));
                }
            }
        }
    }

    public void drawReverse(){
        for (int i = PatternLockView.getListPoint().size() - 1; i >= 0; i--) {
            Locations locations = PatternLockView.getListPoint().get(i);
            keyOnMove = locations.getKey();
            imageOnMove = locations.getImage();
            idOnMove = locations.getId();
            hintOnMove = locations.getHint();
            int xCenter = locations.getX();
            int yCenter = locations.getY();
            int a = (int) getDistance(copyX1, copyY1, copyX, copyY);
            int b = (int) getDistance(copyX1, copyY1, xCenter, yCenter);
            int c = (int) getDistance(copyX, copyY, xCenter, yCenter);
            int P = (a + b + c) / 2;
            int S = getS(P, a, b, c);
            if (S == 0 && b + c <= a && joinX != xCenter && joinY != yCenter &&
                    (xCenter != copyX || yCenter != copyY)) {
                sISendDataService.sendData(mCount, keyOnMove, imageOnMove, hintOnMove, i);
                mCount++;
                if (mCount <= 10) {
                    if (mCount == 10) {
                        current.stopX = xCenter;
                        current.stopY = yCenter;
                    }
                    listCircle.add(new Locations(xCenter, yCenter, RADIUS, keyOnMove, imageOnMove, hintOnMove, idOnMove));
                }
            }
        }
    }

    public static void setISendDataService(ISendDataService iSendDataService) {
        DrawLine.sISendDataService = iSendDataService;
    }

    @Override
    public void sendData(int position, String key, int image, String hint, int index) {

    }

    @Override
    public void ResetRequest() {
        mCount = 0;
        paint.setColor(Color.WHITE);
        indexes = true;
        lines.clear();
        listCircle.clear();
        invalidate();
    }
}
