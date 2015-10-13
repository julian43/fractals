package com.dookoonu.interfrac;

//SampleDesktopMgr.java
//
import java.awt.*;
import javax.swing.*;

//A DesktopManger that keeps its frames inside the desktop
public class FracDesktopMgr extends DefaultDesktopManager {
    JInternalFrame topFrame;
    JFrame parent;
    int leftlim;
    //This is called whenever a frame is moved. This implementation keeps the frame from 
    //leaving the desktop.    
    
    @Override
    public void dragFrame(JComponent f, int x, int y) {
        if (f instanceof JInternalFrame) { //Deal only with internal frames.
            JInternalFrame frame = (JInternalFrame) f;
            JDesktopPane desk = frame.getDesktopPane();
            Dimension d = desk.getSize();

            //Nothing all that fancy below, just figuring how to adjust 
            //to keep the frame on the desktop.
            if (x < leftlim) {	//too far left?
                x = leftlim;	//Flush against the left side.
            } else {
                if (x + frame.getWidth() > d.width) {	//Too far right?
                    x = d.width - frame.getWidth();		//Flush against right side.
                }
            }
            if (y < 0) {		//Too high?
                y = 0;		//Flush against the top.
            } else {
                if (y + frame.getHeight() > d.height) {		//Too low?
                    y = d.height - frame.getHeight();	//Flush against the bottom.
                }
            }
        }
        //Pass along the (possibly cropped) values to the normal drag handler.
        super.dragFrame(f, x, y);
    }
    
    public void setTopFrame(JInternalFrame tf){
        topFrame = tf;
        
    }
    
    public JInternalFrame getTopFrame(){
        return topFrame;
    }

    public JFrame getParent() {
        return parent;
    }

    public void setParent(JFrame parent) {
        this.parent = parent;
    }

    public void setLeftlim(int leftlim) {
        this.leftlim = leftlim;
    }
    
    
    
}