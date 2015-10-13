package com.dookoonu.math.fractal;

import com.dookoonu.image.CompatibleImage;
import com.dookoonu.image.GraphicsUtilities;
import com.dookoonu.interfrac.FracDesktopMgr;
import com.dookoonu.math.graph2d.ImgData;
import com.dookoonu.math.graph2d.InternalGraphPaper;
import com.dookoonu.maths.util.Range2d;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class FractalImageGenerator extends InternalGraphPaper {

    BufferedImage img;
    
    
    public FractalImageGenerator() {
        super();
        //setSize(256, 256);
        //finishConstruct();
    }

    public FractalImageGenerator(String title) {
        super(title);

        //finishConstruct();
    }

    public FractalImageGenerator(int w, int h) {
        super();
        setSize(w, h);
        //finishConstruct();
    }

    public FractalImageGenerator(int w, int h, String title) {
        super(title);
        setSize(w, h);
        //finishConstruct();
    }

    public void finishConstruct() {
        //setTitle("Testing the Julia Set");
        setLocation(100, 100);
        setVisible(true);
        // v_tOffset = 40;
        //h_lOffset = 10; 
        //h_rOffset = 10;
        //v_bOffset = 40; 
        setupView();
        imgData = new ImgData();
    }   
    
    public void setRange(double xmn, double xmx, double ymn, double ymx) {
        imgData.setRange(xmn, xmx, ymn, ymx);
    }

    public void setRange() {
        double xmn = Double.parseDouble(getXminField());
        double xmx = Double.parseDouble(getXmaxField());
        double ymn = Double.parseDouble(getYminField());
        double ymx = Double.parseDouble(getYmaxField());

        imgData.setRange(xmn, xmx, ymn, ymx);
    }

    public void init() {
        JPanel panel = getFracPanel();
        int pixels[] = new int[viewHlength * viewVlength];
        int colors[] = new int[imgData.get_colorMax() + 1];
        set_xinc();
        set_yinc();
        alpha = alphaconst << 24;
        for (int i = 0; i < colors.length; i++) {
            int r = (i * rmul) % 255;
            int g = (r * gmul) % 255;
            int b = (g * bmul) % 255;

            colors[i] = alpha | (r << 16) | (g << 8) | b;
        }

        int screenX, screenY;

        //System.out.println("centreX: " + centreX +" " + "centreY: " + centreY);
        //System.out.println("scaleX: " + scaleX +" " + "scaleY: " + scaleY);
        //System.out.println("xinc: " + xinc +" " + "yinc: " + yinc);

        Graphics g = panel.getGraphics();

        for (double[] coor : imgData.data) {
            screenX = (int) ((coor[0] - imgData.xmin) * xinc + viewHmin);
            screenY = (int) (viewVmax - (coor[1] - imgData.ymin) * yinc);

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
        img = CompatibleImage.createCompatibleImage(viewHlength, viewVlength);
        GraphicsUtilities.setPixels(img, 0, 0, viewHlength, viewVlength, pixels);
        //createImage(new MemoryImageSource(viewHlength, viewVlength, pixels, 0, viewHlength));
        setImage(img);
        g.drawImage(img, 0, 0, this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        /* if (img != null) {
         JPanel panel = getFracPanel();
         Graphics pg = panel.getGraphics();
         pg.drawImage(img, 0, 0, null);
         }*/
    }

    public void generateSet(JMSet js) {
        js.generateSet(super.imgData);
    }

    private int maxColor(ArrayList<ImgData> al) {
        int max = 0;
        for (ImgData id : al) {
            if (id.get_colorMax() > max) {
                max = id.get_colorMax();
            }
        }

        return max;
    }

    BufferedImage oldImg;
    public void generateMuliSet(MultiJMSet js, ExecutorService es, int sx, int sy) {
        JPanel panel = getFracPanel();
        JPanel past = getSmallPanel();
        Range2d r2d = new Range2d();
        r2d.setRange(imgData.xmin, imgData.xmax, imgData.ymin, imgData.ymax);
        r2d.setSubdivisions(sx, sy);
        r2d.subdivide();
        double[][] subrangex = r2d.getsubrangex();
        double[][] subrangey = r2d.getsubrangey();
        ArrayList<ImgData> al = new ArrayList<>();
        int xdiv = js.x_div / sx;
        int ydiv = js.y_div / sy;
        double xmin, xmax, ymin, ymax;
        CountDownLatch cdl = new CountDownLatch(sx * sy);
        for (int i = 0; i < sx; i++) {
            xmin = subrangex[i][0];
            xmax = subrangex[i][1];
            for (int j = 0; j < sy; j++) {
                ymin = subrangey[j][0];
                ymax = subrangey[j][1];
                ImgData id = new ImgData();
                id.setRange(xmin, xmax, ymin, ymax);
                al.add(id);
                MultiJMSet jms = new MultiJMSet(js);
                jms.setdivisions_X(xdiv);
                jms.setdivisions_Y(ydiv);
                jms.setId(id);
                jms.setCdl(cdl);
                es.submit(jms);
            }
        }
        try {

            cdl.await();
            int pixels[] = new int[viewHlength * viewVlength];
            int colors[] = new int[maxColor(al) + 1];
            set_xinc();
            set_yinc();
            alpha = alphaconst << 24;
            for (int i = 0; i < colors.length; i++) {
                int r = (i * rmul) % 255;
                int g = (r * gmul) % 255;
                int b = (g * bmul) % 255;

                colors[i] = alpha | (r << 16) | (g << 8) | b;
            }
            int screenX, screenY, pl = pixels.length;

            Graphics2D g = (Graphics2D) panel.getGraphics();
            for (ImgData id : al) {
                for (double[] coor : id.data) {
                    screenX = (int) ((coor[0] - imgData.xmin) * xinc + viewHmin);
                    screenY = (int) (viewVmax - (coor[1] - imgData.ymin) * yinc);

                    int scrnY = screenY < 1 ? 0 : screenY - 1;
                    int pos = scrnY * viewHlength + screenX;

                    if (pos >= 0 && pos < pl) {
                        pixels[pos] = colors[(int) coor[2]];
                    } else { //this should'nt happen so we have a problem.
                        System.out.println("Bad Index: " + pos + " " + "x: " + coor[0] + " " + "y: " + coor[1]);
                        System.out.println("sx " + screenX + " " + "sy " + screenY);
                    }
                }
            }


            BufferedImage bimg = CompatibleImage.createCompatibleImage(viewHlength, viewVlength);
            BufferedImage smallImage = new BufferedImage(past.getWidth(), past.getHeight(), Transparency.OPAQUE);
            double rx = past.getWidth()/(viewHlength*1.0), ry = past.getHeight()/(viewVlength*1.0);
            Graphics2D g2d = smallImage.createGraphics();
            GraphicsUtilities.setPixels(bimg, 0, 0, viewHlength, viewVlength, pixels);
            setImage(bimg);
            g.drawImage(bimg, 0, 0, this);                        
            
            Point cp = getCenterPoint();
            if (cp != null && oldImg!=null) {                                
                int wh = getInc();
                      
                //g2d_bimg.drawRect(cp.x, cp.y, 50, 50);
                g2d.drawImage(oldImg, 0, 0, smallImage.getWidth(), smallImage.getHeight(), this);
                //smallImage = GraphicsUtilities.GrayScale(smallImage);
                GraphicsUtilities.GrayScale(smallImage);
                Graphics2D g2d_bimg = smallImage.createGraphics();
                int w_2 = (int)(getInc()*5*rx)/2;
                int h_2 = (int)(getInc()*5*ry)/2; 
                g2d_bimg.setColor(Color.red);
                g2d_bimg.drawRect((int)(cp.x*rx)-w_2, (int)(cp.y*ry)-h_2, (int)(getInc()*5*rx), (int)(getInc()*5*ry));   
                Graphics2D g2 = (Graphics2D) past.getGraphics();
                GraphicsUtilities.changeBrightness(smallImage, new float[]{3.5f,3.5f,3.5f}, 
                        new float[]{0.0f,0.0f,0.0f});
                setSmallImage(smallImage);
                g2.drawImage(smallImage, 0, 0, past);
            }
            
            oldImg = bimg;
        } catch (InterruptedException ex) {
            Logger.getLogger(FractalImageGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}