package com.design.apparel.appareldesigner;

/*******************************************************************************
 * Created by VasukiSairam on 6/22/2015.
 * This code is the class that contains pattern details as an Arraylist
 ******************************************************************************/

public class PatternText {

    private String text;
    private String r;
    private String angle;
    private int x;
    private int y;

    public PatternText(String str, int _x, int _y) {
        this.text=str;
        this.x=_x;
        this.y=_y;
    }

    public PatternText() {
        this.r=null;
        this.angle=null;
        this.x=0;
        this.y=0;
    }

    public PatternText(String _r, String _a, int _x, int _y) {
        this.r=_r;
        this.angle=_a;
        this.x=_x;
        this.y=_y;
    }

    public void setValues(String _r,String _a,int _x,int _y)
    {
        this.r=_r;
        this.angle=_a;
        this.x=_x;
        this.y=_y;
    }

    public void setText(String str) {
        this.text = str;
    }

    public String getText() {
        return text;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public int getX() {
        return x;
    }

    public void setX(int _x) {
        x=_x;
    }

    public int getY() {
        return y;
    }

    public void setY(int _y) {
        y=_y;
    }

    public void setPos(int _x,int _y)
    {
        x=_x;
        y=_y;
    }

  }
