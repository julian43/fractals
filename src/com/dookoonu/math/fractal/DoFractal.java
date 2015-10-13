/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dookoonu.math.fractal;

import com.dookoonu.interfrac.FracDesktopMgr;
import java.util.concurrent.ExecutorService;
import javax.swing.JDesktopPane;

/**
 *
 * @author Julian G. Cowell
 */
public abstract class DoFractal implements Runnable{

    protected FractalImageGenerator fracImgGen;
    protected JMSet fracSet;
    ExecutorService fracES;
    FracDesktopMgr dm;
   
    
    public DoFractal(JDesktopPane fma, ExecutorService es, String title) {
        fracImgGen = new FractalImageGenerator(title);
        fma.add(fracImgGen);       
        fracES = es;       
     
    }

    public void showWin() {
        fracImgGen.finishConstruct();
        fracSet = new JMSet();
        fracImgGen.setDf(this);
        //mImgGen.repaint();
    }
    
    public void start(){
        fracES.submit(this);
    }
    
    public ExecutorService getES(){
        return fracES;
    }
    
    public void setDesktopMgr(FracDesktopMgr fdm){
        dm = fdm;
        fracImgGen.setFracDesktopMgr(fdm);
        
    }
}
