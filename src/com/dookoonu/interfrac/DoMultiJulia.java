/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dookoonu.interfrac;

import com.dookoonu.math.fractal.DoMultiFractal;
import java.util.concurrent.ExecutorService;
import javax.swing.JDesktopPane;

/**
 *
 * @author Julian G. Cowell
 */
public class DoMultiJulia extends DoMultiFractal {

    public DoMultiJulia(JDesktopPane fma, ExecutorService es, String title) {
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
        fracImgGen.setRange();
        
        mfracSet.setc(Double.parseDouble(fracImgGen.getCxField()),
                Double.parseDouble(fracImgGen.getCyField()));        
        mfracSet.setdivisions_X(Integer.parseInt(fracImgGen.getXdivField()));
        mfracSet.setdivisions_Y(Integer.parseInt(fracImgGen.getYdivField()));
        mfracSet.setn(Integer.parseInt(fracImgGen.getIterField()));

        fracImgGen.generateMuliSet(mfracSet, getES(), 4, 4);
    }
}
