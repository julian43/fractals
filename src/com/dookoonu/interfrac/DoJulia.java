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
public class DoJulia extends DoFractal {

    public DoJulia(JDesktopPane fma, ExecutorService es, String title) {
        super(fma, es, title);
        fracImgGen.setXmaxField("2.0");//("0.125");
        fracImgGen.setXminField("-2.0");//("0.0");
        fracImgGen.setYmaxField("2.0");//("-0.375");
        fracImgGen.setYminField("-2.0");//("-0.5");

        fracImgGen.setrField("13");
        fracImgGen.setgField("2");
        fracImgGen.setbField("7");

        fracImgGen.setXdivField("800");
        fracImgGen.setYdivField("800");
        fracImgGen.setIterField("5000");

        fracImgGen.setCxField("-0.8");
        fracImgGen.setCyField("0.15");

    }

    @Override
    public void run() {
        fracImgGen.repaint();
        //fracImgGen.setRange(0, 0.125, -0.5, -0.375);//(-2.0, 2.0, -2.0, 2.0);
        fracImgGen.setRange();
        fracSet.setc(Double.parseDouble(fracImgGen.getCxField()),
                Double.parseDouble(fracImgGen.getCyField()));
        //juliaSet.setMandel(true);
        fracSet.setdivisions_X(Integer.parseInt(fracImgGen.getXdivField()));
        fracSet.setdivisions_Y(Integer.parseInt(fracImgGen.getYdivField()));
        fracSet.setn(Integer.parseInt(fracImgGen.getIterField()));

        fracImgGen.generateSet(fracSet); //generate Julia set        
        fracImgGen.init();
        
    }
}
