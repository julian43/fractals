/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dookoonu.math.fractal;

import java.util.concurrent.ExecutorService;
import javax.swing.JDesktopPane;

/**
 *
 * @author Julian G. Cowell
 */
public abstract class DoMultiFractal extends DoFractal implements Runnable{
    
    protected MultiJMSet mfracSet;
    
    public DoMultiFractal(JDesktopPane fma, ExecutorService es, String title) {
        super(fma, es, title);         
    }

    @Override
    public void showWin() {
        fracImgGen.finishConstruct();
        mfracSet = new MultiJMSet();
        fracImgGen.setDf(this);
        //mImgGen.repaint();
    }
 
}
