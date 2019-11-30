package com.example.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class Drawline {
    private Context mContext;
    private Resources mResources;
    private ArrayList<String> newData2 = new ArrayList<>();
    private  ArrayList<Integer> path = new ArrayList<>();
    private ArrayList<Double> xRatio = new ArrayList<Double>();
    private ArrayList<Double> yRatio = new ArrayList<Double>();

    private ArrayList<Double> xDG = new ArrayList<Double>();
    private ArrayList<Double> yDG = new ArrayList<Double>();
    private ArrayList<String> directAr = new ArrayList<>();
    private ArrayList<String> directAr2 = new ArrayList<>();
    private ArrayList<Double> distancefordirect = new ArrayList<>();
    double x1R, x2R, y1R, y2R, x1R2, y1R2;
    private List<Integer> newList;

    private Bitmap bitmap;

    private ArrayList<Integer> idofListSpin = new ArrayList<Integer>();

    public void drawline(int k) {

        if (getNewData2() != null) {
            //draw
            // Initialize a new Bitmap object
            setBitmap(BitmapFactory.decodeResource(getmResources(), R.drawable.mapforuse));
            setBitmap(getBitmap().copy(Bitmap.Config.ARGB_8888, true));
            Bitmap bitmapC = BitmapFactory.decodeResource(mResources,R.drawable.currentnew);
//
           Bitmap bitmapM = BitmapFactory.decodeResource(mResources,R.drawable.marknew);
            // Initialize a new Canvas instance
            Canvas canvas = new Canvas(getBitmap());

            // Draw a solid color on the canvas as background
            // canvas.drawColor(Color.LTGRAY);

            // Initialize a new Paint instance to draw the line
            Paint paint = new Paint();
            // Line color
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);
            // Line width in pixels
            paint.setStrokeWidth(8);
            paint.setAntiAlias(true);

//            int p,o;
//            double xxxxxx,yyyyyyyy;
//            //room
//            for (int i = 0; i< getIdofListSpin().size(); i++)
//            {
//                p = getIdofListSpin().get(i);
//                System.out.println("ppp"+p);
//
//                xxxxxx = xRatio.get(p);
//                yyyyyyyy = yRatio.get(p);
//                System.out.println("xxxxxx"+xxxxxx);
//
////                double x = canvas.getWidth();
////                double y = canvas.getHeight();
////
////                double markx = x*(xxxxxx-0.0219);
////                double marky =y*(yyyyyyyy-0.0585);
////
////
////
////                //cur
////                int markxx = (int) markx;
////                int markyy =(int) marky;
////
////
////                canvas.drawBitmap(bitmapM, markxx, markyy, paint);
//            }
            int startline, endline, here;

            System.out.println("newData2" + getNewData2());

            System.out.println("d Ratio" + getxRatio());


            for (int i = 0; i < getNewData2().size(); i = i + 2) {
                startline = Integer.parseInt(getNewData2().get(i));
                endline = Integer.parseInt(getNewData2().get(i + 1));
                here = getPath().get(k);

                for (int j = 0; j < xRatio.size(); j++) {
                    x1R = xRatio.get(startline);
                    x2R = xRatio.get(endline);
                    y1R = yRatio.get(startline);
                    y2R = yRatio.get(endline);

                    //here
                    x1R2 = xRatio.get(here);
                    y1R2 = yRatio.get(here);

                    double x = canvas.getWidth();
                    double y = canvas.getHeight();
//                System.out.println("x " + x + " y" + y);
                    double x1 = x1R * x;
                    double x2 = x2R * x;
                    double y1 = y1R * y;
                    double y2 = y2R * y;

                    //here
                    double x11 = x1R2 * x;
                    double y22 = y1R2 * y;
                    double markx = x*(x2R-0.0219);
                    double marky =y*(y2R-0.0585);

                    double curx =  (x*(x1R2-0.028846));
                    double cury =(y*(y1R2-0.043537));
//                System.out.println("x1 " + x1 + " y1" + y1);
                    int xint1 = (int) x1;
                    int xint2 = (int) x2;
                    int yint1 = (int) y1;
                    int yint2 = (int) y2;



                    //cur
                    int markxx = (int) markx;
                    int markyy =(int) marky;

                    int curxx = (int) (curx);
                    int curyy =(int) (cury);
                    // Draw a line on canvas at the center position

                    canvas.drawLine(
                            xint1, // startX
                            yint1, // startY
                            xint2, // stopX
                            yint2, // stopY
                            paint // Paint
                    );
                    Paint p1 = new Paint();
                    // Line color
                    p1.setColor(Color.RED);
                    p1.setStyle(Paint.Style.STROKE);
                    // Line width in pixels
                    p1.setStrokeWidth(25);
                    p1.setAntiAlias(true);
                    System.out.println("check i indraw line" + i);
                //mark destination
                    if (i == newData2.size() - 2) {
//                        canvas.drawPoint(xint2, yint2, p1);

                        canvas.drawBitmap(bitmapM, markxx, markyy, paint);
                    }

                    Paint p2 = new Paint();
                    // Line color
                    p2.setColor(Color.BLUE);
                    p2.setStyle(Paint.Style.STROKE);
                    // Line width in pixels
                    p2.setStrokeWidth(25);
                    p2.setAntiAlias(true);

//                        canvas.drawPoint(xint11, xint22, p2);

                    canvas.drawBitmap(bitmapC, curxx, curyy, paint);

                }
            }
           // Display the newly created bitmap on app interface
//            mImageView.setImageBitmap(bitmap);
            if(k==0) {
                System.out.println("path " + getPath());
                calDirection();
                System.out.println("directar " + getDirectAr());
                System.out.println("distancefordirect " + getDistancefordirect());
                addDirectToArr();
                System.out.println("ar2Dist = " + getDirectAr2());
            }
        }
        else{

        }
    }




    double   xd1, xd2, yd1, yd2;

    public void calDirection() {
        System.out.println("drawline" +getPath());
        System.out.println("xDG" +getxDG());

        for (int i = 0; i < getPath().size() - 1; i++) {


            xd1 = getxDG().get(getPath().get(i));

            xd2 = getxDG().get(getPath().get(i + 1));
            yd1 = getyDG().get(getPath().get(i));
            yd2 = getyDG().get(getPath().get(i + 1));
            System.out.println("xd1=" + xd1 + "xd2=" + xd2);
            addDirectionToArr(xd1, xd2, yd1, yd2);


        }
    }

    public void addDirectionToArr(double x1, double x2, double y1, double y2) {

        String d = "";
        double dis=0;
        if (x1 > x2) {
            d = "l";
            dis=x1-x2;
        } else if (x1 < x2) {
            d = "r";
            dis=x2-x1;
        } else if (y1 < y2) {
            d = "u";
            dis=y2-y1;
        } else if (y1 > y2) {
            d = "d";
            dis=y1-y2;
        }
        getDirectAr().add(d);
        getDistancefordirect().add(dis);

    }

    public void addDirectToArr() {
        for (int i = 0; i < getDirectAr().size(); i++) {
            if (i == 0) {
                getDirectAr2().add("ตรง");

            } else {
                calDirectionFinal( getDirectAr().get(i - 1), getDirectAr().get(i));


            }

        }
        deletePointnotChangeDirection();

    }

    public void deletePointnotChangeDirection(){
        setNewList(new ArrayList<>(getPath()));

        for(int i = 1; i< getDirectAr2().size(); i++){

            if(getDirectAr2().get(i).equals("ตรง")){
                getDirectAr2().remove(i);
                getNewList().remove(i);
                getDistancefordirect().set(i-1, getDistancefordirect().get(i)+ getDistancefordirect().get(i-1));
                getDistancefordirect().remove(i);
                i=0;
            }
        }

    }
    public void calDirectionFinal(String s1,String s2){
        if( (s1.equals("r") && s2.equals("r"))|(s1.equals("l") && s2.equals("l"))|(s1.equals("u") && s2.equals("u"))|(s1.equals("d") && s2.equals("d"))){
            getDirectAr2().add("ตรง");

        }
        else if( (s1.equals("d") && s2.equals("r"))|(s1.equals("r") && s2.equals("u"))|(s1.equals("l") && s2.equals("d"))|(s1.equals("u") && s2.equals("l"))){
            getDirectAr2().add("เลี้ยวซ้าย");
        }
        else if( (s1.equals("d") && s2.equals("l"))|(s1.equals("u") && s2.equals("r"))|(s1.equals("r") && s2.equals("d"))|(s1.equals("l") && s2.equals("u"))){
            getDirectAr2().add("เลี้ยวขวา");
        }


    }




    public ArrayList<String> getNewData2() {
        return newData2;
    }

    public void setNewData2(ArrayList<String> newData2) {
        this.newData2 = newData2;
    }

    public ArrayList<Double> getxRatio() {
        return xRatio;
    }

    public void setxRatio(ArrayList<Double> xRatio) {
        this.xRatio = xRatio;
    }

    public ArrayList<Double> getyRatio() {
        return yRatio;
    }

    public void setyRatio(ArrayList<Double> yRatio) {
        this.yRatio = yRatio;
    }

    public List<Integer> getNewList() {
        return newList;
    }

    public void setNewList(List<Integer> newList) {
        this.newList = newList;
    }

    public ArrayList<Double> getxDG() {
        return xDG;
    }

    public void setxDG(ArrayList<Double> xDG) {
        this.xDG = xDG;
    }

    public ArrayList<Double> getyDG() {
        return yDG;
    }

    public void setyDG(ArrayList<Double> yDG) {
        this.yDG = yDG;
    }

    public Resources getmResources() {
        return mResources;
    }

    public void setmResources(Resources mResources) {
        this.mResources = mResources;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    public void setPath(ArrayList<Integer> path) {
        this.path = path;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public ArrayList<String> getDirectAr() {
        return directAr;
    }

    public void setDirectAr(ArrayList<String> directAr) {
        this.directAr = directAr;
    }

    public ArrayList<String> getDirectAr2() {
        return directAr2;
    }

    public void setDirectAr2(ArrayList<String> directAr2) {
        this.directAr2 = directAr2;
    }

    public ArrayList<Double> getDistancefordirect() {
        return distancefordirect;
    }

    public void setDistancefordirect(ArrayList<Double> distancefordirect) {
        this.distancefordirect = distancefordirect;
    }

    public ArrayList<Integer> getIdofListSpin() {
        return idofListSpin;
    }

    public void setIdofListSpin(ArrayList<Integer> idofListSpin) {
        this.idofListSpin = idofListSpin;
    }
}
