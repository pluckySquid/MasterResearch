package com.example.indoor1;

import android.widget.TextView;

public class Accelerations {
    private String xAcc, yAcc, zAcc;


    public Accelerations(){

    }

    public Accelerations(String xAcc, String yAcc, String zAcc) {
        this.xAcc = xAcc;
        this.yAcc = yAcc;
        this.zAcc = zAcc;

    }

    public String getxAcc() {
        return xAcc;
    }

    public void setxAcc(String xAcc) {
        this.xAcc = xAcc;
    }

    public String getyAcc() {
        return yAcc;
    }

    public void setyAcc(String yAcc) {
        this.yAcc = yAcc;
    }

    public String getzAcc() {
        return zAcc;
    }

    public void setzAcc(String zAcc) {
        this.zAcc = zAcc;
    }




}
