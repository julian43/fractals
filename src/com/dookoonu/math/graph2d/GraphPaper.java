package com.dookoonu.math.graph2d;

import java.awt.*;
import javax.swing.*;

public class GraphPaper extends JFrame {

    protected int viewHmax, viewVmax, viewHmin, viewVmin, viewHlength, viewVlength;
    protected int h_rOffset = 0;
    protected int h_lOffset = 0;
    protected int v_bOffset = 0;
    protected int v_tOffset = 0;
    protected double scaleX = 1.0;
    protected double scaleY = 1.0;
    protected double xinc, yinc;
    protected int centreX, centreY;
    protected int zeroPntX = 0;
    protected int zeroPntY = 0;
    protected ImgData imgData;

    public GraphPaper() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public GraphPaper(String t) {
        super(t);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setupView() {
        Dimension d = getSize();
        viewHmax = d.width - h_rOffset - h_lOffset;
        viewVmax = d.height - v_bOffset - v_tOffset;
        viewHmin = h_lOffset;
        viewVmin = v_tOffset;
        viewHlength = viewHmax - viewHmin;
        viewVlength = viewVmax - viewVmin;

        centreX = viewHlength / 2;
        centreY = viewVlength / 2;
    }

    public void setImgData(ImgData data) {
        imgData = data;
    }

    public void set_xinc() {
        xinc = (viewHlength / (imgData.xmax - imgData.xmin)) / scaleX;
    }

    public void set_yinc() {
        yinc = (viewVlength / (imgData.ymax - imgData.ymin)) / scaleY;
    }
}
