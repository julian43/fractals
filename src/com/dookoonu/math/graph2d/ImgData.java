package com.dookoonu.math.graph2d;

import java.io.*;

public class ImgData implements Serializable{

    public double[][] data;
    private int colorMax;
    public double xmin, xmax, ymin, ymax, xinc, yinc;
    public int[] divXY;
    public double[] c;
    public int iterations;
    public double[] grange;

    public ImgData() {
    }

    public ImgData(double[] range, int[] divs, double[] tc, int iter) {
        xmin = range[0];
        xmax = range[1];
        ymin = range[2];
        ymax = range[3];
        divXY = divs;
        c = tc;
        iterations = iter;
    }

    public ImgData(Double[] range, Integer[] divs, Double[] tc, Integer iter) {
        xmin = range[0];
        xmax = range[1];
        ymin = range[2];
        ymax = range[3];
        divXY = new int[2];
        c = new double[2];
        divXY[0] = divs[0];
        divXY[1] = divs[1];
        c[0] = tc[0];
        c[1] = tc[1];
        iterations = iter;
    }

    public void setRange(double xmn, double xmx, double ymn, double ymx) {
        xmin = xmn;
        xmax = xmx;
        ymin = ymn;
        ymax = ymx;
    }

    public void set_xmin(double xmn) {
        xmin = xmn;
    }

    public void set_xmax(double xmx) {
        xmax = xmx;
    }

    public void set_ymin(double ymn) {
        ymin = ymn;
    }

    public void set_ymax(double ymx) {
        ymax = ymx;
    }

    public void setdata(double[][] d) {
        data = d;
    }

    public void set_colorMax(int cm) {
        colorMax = cm;
    }

    public double[][] getdata() {
        return data;
    }

    public int get_colorMax() {
        return colorMax;
    }
}
