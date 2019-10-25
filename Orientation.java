package com.example.indoor1;

public class Orientation {
    private String xOri, yOri, zOri;


    public Orientation(){
        this.xOri = "0";
        this.yOri = "0";
        this.zOri = "0";

    }

    public Orientation(String xOri, String yOri, String zOri) {
        this.xOri = xOri;
        this.yOri = yOri;
        this.zOri = zOri;

    }

    public String getxOri() {
        return xOri;
    }

    public void setxOri(String xOri) {
        this.xOri = xOri;
    }

    public String getyOri() {
        return yOri;
    }

    public void setyOri(String yOri) {
        this.yOri = yOri;
    }

    public String getzOri() {
        return zOri;
    }

    public void setzOri(String zOri) {
        this.zOri = zOri;
    }




}
