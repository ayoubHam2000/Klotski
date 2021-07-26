package com.example.klotski.Classes;

import android.widget.ImageView;

import com.example.klotski.Services.MatrixBox;

public class Box{

    public ImageView theBox;
    public ImageView testBox;

    public float unitWidth;
    public float unitHeight;

    public float[] x;
    public float[] y;
    private float width;
    private float height;
    public float centerX;
    public float centerY;
    public boolean isNotWall;
    private float unit;

    private int reduceAmount;

    //private float[] oldMx = new float[2];


    public Box(ImageView theBox, boolean isNotWall,ImageView testBox, float unitWidth, float unitHeight){
        this.theBox = theBox;
        this.isNotWall = isNotWall;
        this.unitWidth = unitWidth;
        this.unitHeight = unitHeight;
        this.testBox = testBox;

        init();
    }

    //-----------------------------------------------------------
    //initialize

    public void init(){
        reduceAmount = MatrixBox.reduceAmount;
        unit = MatrixBox.unit;
        imageSetSize();

        width = unitWidth * unit - reduceAmount;
        height = unitHeight * unit - reduceAmount;

        x = new float[2];
        y = new float[2];
    }

    private void imageSetSize(){
        theBox.requestLayout();
        theBox.setScaleType(ImageView.ScaleType.FIT_XY);
        theBox.getLayoutParams().width = (int)(unit * unitWidth - reduceAmount);
        theBox.getLayoutParams().height = (int)(unit * unitHeight - reduceAmount);
    }


    public void initMove(float mx, float my){
        int reduce = 15;
        testBox.requestLayout();

        testBox.setScaleType(ImageView.ScaleType.FIT_XY);
        testBox.getLayoutParams().width = (int)width - reduce;
        testBox.getLayoutParams().height = (int)height - reduce;

        testBox.setX(theBox.getX() + reduce / 2f);
        testBox.setY(theBox.getY() + reduce / 2f);

        //System.out.println("--->" + theBox.getWidth());

    }

    public void move(float mx, float my){
        float diffX = mx - centerX;
        float diffY = my - centerY;
        //System.out.println("X : " + diffX + "Y : " + diffY);
        if(isNotWall){
            if(my > y[1] && abs(diffY) > abs(diffX) ){
                setPosition(testBox, centerX, centerY + unit);
            }else if(my < y[0] && abs(diffY) > abs(diffX) ){
                setPosition(testBox, centerX, centerY - unit);
            }else if(mx > x[1]){
                setPosition(testBox , centerX + unit, centerY);
            }else if(mx < x[0]){
                setPosition(testBox, centerX - unit, centerY);
            }
        }
    }

    //if the box is clicked it return true
    public boolean isClicked(float mx, float my){
        if(mx > x[0] && mx < x[1] && isNotWall){
            return my > y[0] && my < y[1];
        }
        return false;
    }

    public void endMove(){
        if(isNotWall){
            setPosition(theBox, getCenterX(testBox) , getCenterY(testBox));
            synchronizePos();
        }
    }


    private float getCenterX(ImageView image){
        return image.getX() + image.getWidth() / 2f;
    }
    private float getCenterY(ImageView image){
        return image.getY() + image.getHeight() / 2f;
    }

    private void setPosition(ImageView image, float x, float y){
        image.setX(x - image.getWidth() / (float)2);
        image.setY(y - image.getHeight() / (float)2);
    }

    public void synchronizePos(){
        x[0] = theBox.getX();
        x[1] = x[0] + width;
        y[0] = theBox.getY();
        y[1] = y[0] + height;
        centerX = x[0] + width / 2f;
        centerY = y[0] + height / 2f;
        //System.out.println("//----> " + x[0] + " //----> " + x[1] + " //----> " + y[0] + " //----> " + y[1] );
    }

    private float abs(float x){
        if(x < 0)
            return -x;
        return x;
    }




}
