package com.example.klotski.Services;

import android.widget.ImageView;

public class MatrixBox {

    public static float unit = 100;
    public static int reduceAmount = 15;
    public static float mainX = -1;
    public static float mainY = -1;



    public static boolean isIntersect(float[] x1, float[] y1, float[] x2, float[] y2){
        if(isIntersectInX(x1, x2) && isIntersectInY(y1, y2)){
            return true;
        }
        return false;
    }

    private static boolean isIntersectInX(float[] x1, float[] x2){
        if(x1[0] >= x2[0])
        {
            if(x1[0] <= x2[1])
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            if(x2[0] <= x1[1])
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    private static boolean isIntersectInY(float[] y1, float[] y2){
        if (y1[0] >= y2[0])
        {
            if (y1[0] <= y2[1])
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            if (y2[0] <= y1[1])
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }



}
