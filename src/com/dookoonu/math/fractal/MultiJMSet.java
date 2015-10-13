package com.dookoonu.math.fractal;

import com.dookoonu.math.complexplus.Complex;
import com.dookoonu.math.graph2d.ImgData;
import java.util.concurrent.CountDownLatch;

public class MultiJMSet implements Runnable {

    Complex c = new Complex();
    int n = 200;
    //double inc = 0.1;
    int x_div = 100;
    int y_div = 100;
    int maxNum = 0;
    double x_inc;
    double y_inc;
    boolean mandel = false;
    Complex z = new Complex();//(x,y);
    ImgData id;
    CountDownLatch cdl;
    
    public MultiJMSet(){
       
    }
    
    public MultiJMSet(MultiJMSet source){
        y_div = source.y_div;
        x_div = source.x_div;
        n = source.n;
        c.x = source.c.x;
        c.y = source.c.y;
        mandel = source.mandel;       
    }
 
    public double testM() {

        double x = z.x, y = z.y, x1, y1;

        int num = 0;

        while ((x * x + y * y < 4.0) && (num < n)) {
            //z = (z.square()).add(c);
            x1 = x * x - y * y + c.x;
            y1 = 2 * x * y + c.y;
            x = x1;
            y = y1;
            num++;
        }
        if (num > maxNum) {
            maxNum = num;
        }
        return num;

    }

    public double testComplexM(Complex w) {
        int num = 0;

        while (w.norm() < 4.0 && num < n) {
            w = w.square().add(c);
            num++;
        }
        if (num > maxNum) {
            maxNum = num;
        }
        return num;
    }

    public double testJ() {
        //Complex z = new Complex(x,y);
        double x = z.x, y = z.y, x1, y1;

        int num = 0;

        while ((x * x + y * y < 4.0) && (num < n)) {
            //z = (z.square()).add(c);
            x1 = x * x - y * y + c.x;
            y1 = 2 * x * y + c.y;
            x = x1;
            y = y1;
            num++;
        }
        if (num > maxNum) {
            maxNum = num;
        }
        return num;
    }

    public double testComplexJ(Complex w) {
        int num = 0;

        while (w.norm() < 4.0 && num < n) {
            //w.squareInPlace();
            //w.addInPlace(c);
            w = w.square().add(c);
            num++;
        }
        if (num > maxNum) {
            maxNum = num;
        }
        return num;
    }

    public double testComplexJ() {
        int num = 0;

        while (z.norm() < 4.0 && num < n) {
            z = ((z.square()).square()).add(c);
            num++;
        }
        if (num > maxNum) {
            maxNum = num;
        }
        return num;
    }

    int getSize(double x1, double x2, double y1, double y2) {
        x_inc = (x2 - x1) / x_div;
        y_inc = (y2 - y1) / y_div;
        return (int) (((x2 - x1) / x_inc) * ((y2 - y1) / y_inc));
    }

    public double[][] testRange(double x1, double x2, double y1, double y2) {
        int limit = getSize(x1, x2, y1, y2) + 1;
        double[][] retList = new double[limit][3];
        double y;
        double x = x1;
        int count = 0;

        if (mandel == true) {
            while (x <= x2) {
                y = y1;
                z.x = x;
                while (y <= y2 && count < limit) {
                    retList[count][0] = x;
                    retList[count][1] = y;
                    z.y = y;
                    setc(x, y);
                    retList[count][2] = testM();//testComplexM(z);
                    y += y_inc;
                    count++;

                }
                x += x_inc;
            }

        } else {
            while (x <= x2) {
                y = y1;
                z.x = x;
                while (y <= y2 && count < limit) {
                    retList[count][0] = x;
                    retList[count][1] = y;
                    z.y = y;
                    retList[count][2] = testJ();//testComplexJ(z);
                    y += y_inc;
                    count++;

                }
                x += x_inc;
            }
        }
        return retList;
    }

    public void setId(ImgData id) {
        this.id = id;
    }    
    
    public void setc(double x, double y) {
        c.x = x;
        c.y = y;
    }

    public void setn(int num) {
        n = num;
    }

    
    /**
     * Set the divisions of the x-axis
     * @param div 
     */
    public void setdivisions_X(int div) {
        x_div = div;
    }

    public void setdivisions_Y(int div) {
        y_div = div;
    }

    public void setMandel(boolean flg) {
        mandel = flg;
    }

    public void setCdl(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    
    
    @Override
    public void run() {       
        maxNum = 0;          
        id.data = testRange(id.xmin, id.xmax, id.ymin, id.ymax);
        id.set_colorMax(maxNum);
        cdl.countDown();
    }
}
