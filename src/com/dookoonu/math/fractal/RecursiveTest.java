/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dookoonu.math.fractal;

import com.dookoonu.math.complexplus.Complex;
import com.dookoonu.math.graph2d.ImgData;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author Julian G. Cowell
 */
public class RecursiveTest extends RecursiveAction {

    private double xmin, xmax, ymin, ymax;
    private double x_inc, y_inc, x_div, y_div;
    private int maxNum = 0, n = 200;
    private ImgData id;
    private boolean mandel;
    private Complex c = new Complex();
    private Complex z = new Complex();
    private static ArrayList<ImgData> al = new ArrayList<>();
    
    public RecursiveTest(ImgData id, boolean m) {
        this.id = id;
        n = id.iterations;
        mandel = m;
        al.add(id);
    }

    int getSize() {
        x_inc = (id.xmax - id.xmin) / id.divXY[0];
        y_inc = (id.ymax - id.ymin) / id.divXY[1];
        return (int) (((id.xmax - id.xmin) / x_inc) * ((id.ymax - id.ymin) / y_inc));
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

    @Override
    protected void compute() {
        double[][] retList;
        double y;
        double x = id.xmin;
        int count = 0;
        int limit = getSize() + 1;
        retList = new double[limit][3];
        if (id.divXY[0] <= 100 && id.divXY[1]<=100) {
            if (mandel == true) {
                while (x <= id.xmax) {
                    y = id.ymin;
                    z.x = x;
                    while (y <= id.ymax && count < limit) {
                        retList[count][0] = x;
                        retList[count][1] = y;
                        z.y = y;
                        c.x = x;
                        c.y = y;
                        retList[count][2] = testM();
                        y += y_inc;
                        count++;
                    }
                    x += x_inc;
                }
            } else {
                while (x <= id.xmax) {
                    y = id.ymin;
                    z.x = x;
                    while (y <= id.xmax && count < limit) {
                        retList[count][0] = x;
                        retList[count][1] = y;
                        z.y = y;
                        retList[count][2] = testJ();
                        y += y_inc;
                        count++;
                    }
                    x += x_inc;
                }
            }
            id.set_colorMax(maxNum);
            id.data = retList;
           
        } else {//range is too big for a single thread.
            double midx = (id.xmax - id.xmin)/2;
            double midy = (id.ymax - id.ymin)/2;
            ImgData nid1 = new ImgData(); nid1.divXY[0] = id.divXY[0]/4; nid1.divXY[1] = id.divXY[1]/4;
            ImgData nid2 = new ImgData(); nid2.divXY[0] = id.divXY[0]/4; nid2.divXY[1] = id.divXY[1]/4;
            ImgData nid3 = new ImgData(); nid3.divXY[0] = id.divXY[0]/4; nid3.divXY[1] = id.divXY[1]/4;nid3.divXY = id.divXY.clone();
            ImgData nid4 = new ImgData(); nid4.divXY[0] = id.divXY[0]/4; nid4.divXY[1] = id.divXY[1]/4;
            nid2.setRange(xmin, xmin+midx, ymin+midy, ymax);
            nid1.setRange(xmin+midx, xmax, ymin+midx, ymax);
            nid3.setRange(xmin, xmin+midx, ymin, ymin+midx);
            nid4.setRange(xmin+midx, xmax, ymin, ymin+midy);
            RecursiveTest firstQuater = new RecursiveTest(nid1, mandel);            
            RecursiveTest secondQuater = new RecursiveTest(nid2, mandel);
            RecursiveTest thirdQuater = new RecursiveTest(nid3, mandel);
            RecursiveTest fourthQuater = new RecursiveTest(nid4, mandel);
            firstQuater.fork();
            secondQuater.fork();
            thirdQuater.fork();
            fourthQuater.compute();
            firstQuater.join();
            secondQuater.join();
            thirdQuater.join();
            //figure out how to combine these arrays.
        }                
    }
    
    public ArrayList<double[][]> getList(){
        return null;
        //return al;
    }
}