/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dookoonu.interfrac;

import com.dookoonu.math.fractal.DoFractal;
import java.util.concurrent.ExecutorService;
import javax.swing.JDesktopPane;

/**
 *
 * @author Julian G. Cowell
 */
public class DoMandelBrot extends DoFractal {

    public DoMandelBrot(JDesktopPane fma, ExecutorService es, String title) {
        super(fma, es, title);
        fracImgGen.setXmaxField("2.0");//("-0.72");
        fracImgGen.setXminField("-2.0");//("-0.77");
        fracImgGen.setYmaxField("2.0");//("-0.15");
        fracImgGen.setYminField("-2.0");//("-0.2");

        fracImgGen.setrField("13");
        fracImgGen.setgField("2");
        fracImgGen.setbField("7");               
        
        fracImgGen.setXdivField("800");
        fracImgGen.setYdivField("800");
        fracImgGen.setIterField("5000");
    }

    @Override
    public void run() {

        fracImgGen.repaint();
        //fracImgGen.setRange(-0.77, -0.72, -0.2, -0.15);//(-2.0, 2.0, -2.0, 2.0);(-0.73,-0.7,-0.28,-0.25);
        fracImgGen.setRange();
        //mandelbrotSet.setc(-0.8,0.15);
        fracSet.setMandel(true);
        fracSet.setdivisions_X(Integer.parseInt(fracImgGen.getXdivField()));
        fracSet.setdivisions_Y(Integer.parseInt(fracImgGen.getYdivField()));
        fracSet.setn(Integer.parseInt(fracImgGen.getIterField()));

        fracImgGen.generateSet(fracSet); //generate Mandelbrot set

        fracImgGen.init();
        //mImgGen.repaint();
    }
}
