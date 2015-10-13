package com.dookoonu.math.fractal;

import com.dookoonu.math.graph2d.GraphPaper;
import com.dookoonu.math.graph2d.ImgData;
import java.awt.*;
import java.awt.image.*;


public class MultiFractalImageGenerator extends GraphPaper {

    Image img;
    int alpha = 255 << 24;
    int pixels[];
    int colors[];
    int rx, ry;
    MemoryImageSource fimg;

    public MultiFractalImageGenerator() {
        super();
        setSize(256, 256);
        finishConstruct();
    }

    public MultiFractalImageGenerator(int w, int h) {
        super();
        setSize(w, h);
        finishConstruct();
    }

    public MultiFractalImageGenerator(int w, int h, String title) {
        super(title);
        setSize(w, h);
        finishConstruct();
        pixels = new int[viewHlength * viewVlength];
        fimg = new MemoryImageSource(viewHlength, viewVlength, pixels, 0, viewHlength);

    }

    public void setupcolors(int max) {
        colors = new int[max + 1];
        for (int i = 0; i < colors.length; i++) {
            int r = (i * 13) % 255;
            int g = (r * 2) % 255;
            int b = (g * 16) % 255;

            colors[i] = alpha | (r << 16) | (g << 8) | b;
        }
    }

    private void finishConstruct() {
        //setTitle("Testing the Julia Set");
        setLocation(100, 100);
        setupView();
        //imgData = new ImgData();
        //setVisible(true);
    }

    public void setRange(double xmn, double xmx, double ymn, double ymx) {
        imgData.setRange(xmn, xmx, ymn, ymx);
    }

    public void init() {

        set_xinc();
        set_yinc();



        int screenX, screenY;

        //System.out.println("centreX: " + centreX +" " + "centreY: " + centreY);
        // System.out.println("scaleX: " + scaleX +" " + "scaleY: " + scaleY);
        // System.out.println("xinc: " + xinc +" " + "yinc: " + yinc);
        // System.out.println(viewHmin + " "+ viewVmax);

        Graphics g = getGraphics();


        for (double[] coor : imgData.data) {
            screenX = (int) ((coor[0] - imgData.grange[0]) * xinc + viewHmin);
            screenY = (int) (viewVmax - (coor[1] - imgData.grange[2]) * yinc);

            //g.setColor(new Color(colors[(int)coor[2]]));
            //g.drawLine((int)screenX, (int)screenY, (int)screenX, (int)screenY);

            int scrnY = screenY < 1 ? 0 : screenY - 1;
            int pos = scrnY * viewHlength + screenX;

            if (pos >= 0 && pos < pixels.length) {
                pixels[pos] = colors[(int) coor[2]];
            } else { //this should'nt happen so we have a problem.
                System.out.println("Bad Index: " + pos + " " + "x: " + coor[0] + " " + "y: " + coor[1]);
                System.out.println("sx " + screenX + " " + "sy " + screenY);
            }
        }
        img = createImage(fimg);
        g.drawImage(img, 0, 0, this);
    }

    public void setmouseXY(int mx, int my) {
        rx = mx;
        ry = my;

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (img != null) {
            g.drawImage(img, 0, 0, this);
        }
        //g.setXORMode(Color.red);
        // if (rx!=0 && ry!=0) g.drawRect(ry-10, rx-10, rx+10, ry+10 );
        // g.setPaintMode();
        // rx=ry=0;
    }

    public void generateSet(JMSet js) {
        js.generateSet(super.imgData);
    }

    public double[] getXY(int sx, int sy) {
        double[] xy = new double[2];

        xy[0] = (sx - viewHmin) / xinc + imgData.grange[0];
        xy[1] = imgData.grange[2] - ((sy - viewVmax) / yinc);

        return xy;
    }

    public int getviewHmin() {
        return viewHmin;
    }

    public double getxinc() {
        return xinc;
    }

    public int getviewVmax() {
        return viewVmax;
    }

    public int getviewHlength() {
        return viewHlength;
    }

    public int getviewVlength() {
        return viewVlength;
    }

    public double getyinc() {
        return yinc;
    }

    public ImgData getImgData() {
        return imgData;
    }
}
