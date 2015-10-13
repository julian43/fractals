package com.dookoonu.maths.util;

public class Range2d {

    double x1, x2, y1, y2;
    int subdx = 1;
    int subdy = 1;
    double[][] subrangex;
    double[][] subrangey;

    public Range2d() {
        subdx = 1;
        subdy = 1;
    }

    public Range2d(double a, double b, double c, double d, int sx, int sy) {
        x1 = a;
        x2 = b;
        y1 = c;
        y2 = d;
        subdx = sx;
        subdy = sy;
    }

    public Range2d(double[] a, int[] s) {
        x1 = a[0];
        x2 = a[1];
        y1 = a[2];
        y2 = a[3];
        subdx = s[0];
        subdy = s[1];
    }

    public void setSubdivisions(int sx, int sy) {
        subdx = sx;
        subdy = sy;
    }

    public void setRange(double a, double b, double c, double d) {
        x1 = a;
        x2 = b;
        y1 = c;
        y2 = d;
    }

    public double getx1() {
        return x1;
    }

    public double getx2() {
        return x2;
    }

    public double gety1() {
        return y1;
    }

    public double gety2() {
        return y2;
    }

    public void setx1(double val) {
        x1 = val;
    }

    public void setx2(double val) {
        x2 = val;
    }

    public void sety1(double val) {
        y1 = val;
    }

    public void sety2(double val) {
        y2 = val;
    }

    public void subdivide() {
        double xinc = (x2 - x1) / subdx;
        double yinc = (y2 - y1) / subdy;

        subrangex = new double[subdx][2];
        subrangey = new double[subdy][2];

        subrangex[0][0] = x1;
        subrangey[0][0] = y1;
        subrangex[0][1] = x1 + xinc;
        subrangey[0][1] = y1 + yinc;
        for (int i = 1; i < subdx; i++) {
            subrangex[i][0] = subrangex[i - 1][1];
            subrangex[i][1] = subrangex[i][0] + xinc;
        }
        for (int i = 1; i < subdy; i++) {
            subrangey[i][0] = subrangey[i - 1][1];
            subrangey[i][1] = subrangey[i][0] + yinc;
        }

    }

    public double[][] getsubrangex() {
        return subrangex;
    }

    public double[][] getsubrangey() {
        return subrangey;
    }
}
