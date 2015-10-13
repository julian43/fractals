/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dookoonu.math.graph2d;

import com.dookoonu.interfrac.FracDesktopMgr;
import com.dookoonu.math.fractal.DoFractal;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class InternalGraphPaper extends javax.swing.JInternalFrame {

    protected DoFractal df;
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
    protected double pointerInc = 0.1;
    private String xmin, xmax, ymin, ymax;
    protected int alphaconst = 255;
    protected int alpha;
    protected int rmul = 13, gmul = 2, bmul = 7;
    private Point currentCenter;
    static private Point lastloc = null;
    private int incx = 10, incy = 10, leftlim = 0;
    FracDesktopMgr fdm;
    //protected FracPanel fractalPanel;   

    /**
     * Creates new form InternalGraphPaper
     */
    public InternalGraphPaper() {
        initComponents();
    }

    public InternalGraphPaper(String name) {
        super(name);
        initComponents();
        setClosable(true);
        setIconifiable(true);
        alphaVals();
        //fractalPanel = new FracPanel();
        //add(fractalPanel);
    }

    public void setDf(DoFractal df) {
        this.df = df;
    }

    public ImgData getImgData() {
        return imgData;
    }

    public void setFracDesktopMgr(FracDesktopMgr f) {
        fdm = f;       
    }

    public void setupView() {
        Rectangle rec = getBounds();
        //fractalPanel.setSize((int)(rec.width*0.5), rec.height);
         if (lastloc == null) {
            lastloc = getLocation();
            lastloc.setLocation(leftlim, 0);
            setLocation(lastloc);
        } else {
            int x = lastloc.x;
            int y = lastloc.y;

            lastloc.setLocation(x + incx, y + incy);
            setLocation(lastloc);
        }
        Rectangle d = fractalPanel.getBounds();
        viewHmax = d.width - h_rOffset - h_lOffset;
        viewVmax = d.height - v_bOffset - v_tOffset;
        viewHmin = h_lOffset;
        viewVmin = v_tOffset;
        viewHlength = viewHmax - viewHmin;
        viewVlength = viewVmax - viewVmin;

        centreX = viewHlength / 2;
        centreY = viewVlength / 2;
        fractalPanel.setPaper(this);
        //selectRangeSlider.setValue(1);
        pointerRangeTextField.setText(String.valueOf(selectRangeSlider.getValue() / (selectRangeSlider.getMaximum() * 1.0)));
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

    public JPanel getFracPanel() {
        return fractalPanel;
    }

    public JPanel getSmallPanel() {
        return smallFracPanel;
    }

    public void setImage(BufferedImage img) {
        fractalPanel.setImage(img);
    }

    public void setSmallImage(BufferedImage img) {
        smallFracPanel.setImage(img);
    }

    public Point getCenterPoint() {
        return currentCenter;
    }

    public int getInc() {
        return (int) (pointerInc * 100);
    }

    public String getXmaxField() {
        return xmaxField.getText();
    }

    public void setXmaxField(String xmax) {
        xmaxField.setText(xmax);
    }

    public String getXminField() {
        return xminField.getText();
    }

    public void setXminField(String xmin) {
        xminField.setText(xmin);
    }

    public String getYmaxField() {
        return ymaxField.getText();
    }

    public void setYmaxField(String ymax) {
        ymaxField.setText(ymax);
    }

    public String getYminField() {
        return yminField.getText();
    }

    public void setYminField(String ymin) {
        yminField.setText(ymin);
    }

    public String getCxField() {
        return cxField.getText();
    }

    public void setCxField(String cx) {
        cxField.setText(cx);
    }

    public String getCyField() {
        return cyField.getText();
    }

    public void setCyField(String cy) {
        cyField.setText(cy);
    }

    public String getIterField() {
        return iterField.getText();
    }

    public void setIterField(String iter) {
        iterField.setText(iter);
    }

    public String getXdivField() {
        return xdivField.getText();
    }

    public void setXdivField(String xdiv) {
        xdivField.setText(xdiv);
    }

    public String getYdivField() {
        return ydivField.getText();
    }

    public void setYdivField(String ydiv) {
        ydivField.setText(ydiv);
    }

    public double getPointerInc() {
        return pointerInc;
    }

    public void setPointerInc(double pointerInc) {
        this.pointerInc = pointerInc;
    }

    public int getAlphaconst() {
        return alphaconst;
    }

    public void setAlphaconst(int alphaconst) {
        this.alphaconst = alphaconst;
    }

    public int getRmul() {
        return rmul;
    }

    public void setRmul(int rmul) {
        this.rmul = rmul;
    }

    public int getGmul() {
        return gmul;
    }

    public void setGmul(int gmul) {
        this.gmul = gmul;
    }

    public int getBmul() {
        return bmul;
    }

    public void setBmul(int bmul) {
        this.bmul = bmul;
    }

    public void clear() {
        Graphics g = fractalPanel.getGraphics();
        g.clearRect(0, 0, viewHlength, viewVlength);
    }

    private double fromScreenX(int sx) {
        return (sx - viewHmin) / xinc + imgData.xmin;
    }

    private double fromScreenY(int sy) {
        return (sy - viewVmax) / (-yinc) + imgData.ymin;
    }

    private void saveRange() {
        xmin = getXminField();
        xmax = getXmaxField();
        ymin = getYminField();
        ymax = getYmaxField();
    }

    public void restoreRange() {
        setXminField(xmin);
        setXmaxField(xmax);
        setYminField(ymin);
        setYmaxField(ymax);
        if (mouseToggleBtn.isSelected()) {
            startFracBtnActionPerformed(null);
        }
    }

    public void updateRanges(Point centerPoint) {
        //System.out.println("x=" + fromScreenX(centerPoint.x) + " y=" + fromScreenY(centerPoint.y));        
        currentCenter = centerPoint;
        double cx = fromScreenX(centerPoint.x);
        double cy = fromScreenY(centerPoint.y);
        setXminField(Double.toString(cx - pointerInc));
        setXmaxField(Double.toString(cx + pointerInc));
        setYminField(Double.toString(cy - pointerInc));
        setYmaxField(Double.toString(cy + pointerInc));
        if (mouseToggleBtn.isSelected()) {
            startDraw();
        }

    }

    public String getbField() {
        return bField.getText();
    }

    public void setbField(String b) {
        bField.setText(b);
    }

    public String getgField() {
        return gField.getText();
    }

    public void setgField(String g) {
        gField.setText(g);
    }

    public String getrField() {
        return rField.getText();
    }

    public void setrField(String r) {
        rField.setText(r);
    }

    public int getAlphaConst() {
        return (Integer) alphaBox.getSelectedItem();
    }

    public void setAlphaConst(int a) {
        alphaBox.setSelectedItem(a);
    }

    private void alphaVals() {

        for (int i = 0; i < 256; i++) {
            alphaBox.addItem(i);
        }

        alphaBox.setSelectedItem(255);
    }

    private void startDraw() {
        setImage(null);
        clear();
        rmul = Integer.parseInt(getrField());
        gmul = Integer.parseInt(getgField());
        bmul = Integer.parseInt(getbField());
        alphaconst = (Integer) alphaBox.getSelectedItem();
        df.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jfc = new javax.swing.JFileChooser();
        fractalPanel = new com.dookoonu.math.graph2d.FracPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        startFracBtn = new javax.swing.JButton();
        xminField = new javax.swing.JTextField();
        xmaxField = new javax.swing.JTextField();
        yminField = new javax.swing.JTextField();
        ymaxField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cxField = new javax.swing.JTextField();
        cyField = new javax.swing.JTextField();
        xdivField = new javax.swing.JTextField();
        ydivField = new javax.swing.JTextField();
        iterField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        saveBtn = new javax.swing.JButton();
        selectRangeSlider = new javax.swing.JSlider();
        pointerRangeTextField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        smallFracPanel = new com.dookoonu.math.graph2d.FracPanel();
        jPanel2 = new javax.swing.JPanel();
        mouseToggleBtn = new javax.swing.JToggleButton();
        rField = new javax.swing.JTextField();
        gField = new javax.swing.JTextField();
        bField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        alphaBox = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();

        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameDeactivated(evt);
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        javax.swing.GroupLayout fractalPanelLayout = new javax.swing.GroupLayout(fractalPanel);
        fractalPanel.setLayout(fractalPanelLayout);
        fractalPanelLayout.setHorizontalGroup(
            fractalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 493, Short.MAX_VALUE)
        );
        fractalPanelLayout.setVerticalGroup(
            fractalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane1.setToolTipText("Set Conditions for Fractal Generation");
        jTabbedPane1.setName(""); // NOI18N

        startFracBtn.setText("Start");
        startFracBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startFracBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("xmin");

        jLabel2.setText("xmax");

        jLabel3.setText("ymin");

        jLabel4.setText("ymax");

        xdivField.setText("800");

        ydivField.setText("800");
        ydivField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ydivFieldActionPerformed(evt);
            }
        });

        jLabel5.setText("Iterations");

        jLabel6.setText("XDivisons");

        jLabel7.setText("YDivisions");

        jLabel8.setText("c.x");

        jLabel9.setText("c.y");

        saveBtn.setText("Save Image");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        selectRangeSlider.setMajorTickSpacing(10);
        selectRangeSlider.setMinimum(1);
        selectRangeSlider.setMinorTickSpacing(1);
        selectRangeSlider.setPaintTicks(true);
        selectRangeSlider.setSnapToTicks(true);
        selectRangeSlider.setValue(10);
        selectRangeSlider.setExtent(1);
        selectRangeSlider.setFocusCycleRoot(true);
        selectRangeSlider.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                selectRangeSliderMouseWheelMoved(evt);
            }
        });
        selectRangeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                selectRangeSliderStateChanged(evt);
            }
        });

        pointerRangeTextField.setEditable(false);
        pointerRangeTextField.setToolTipText("");

        jLabel15.setText("Pointer Range");

        javax.swing.GroupLayout smallFracPanelLayout = new javax.swing.GroupLayout(smallFracPanel);
        smallFracPanel.setLayout(smallFracPanelLayout);
        smallFracPanelLayout.setHorizontalGroup(
            smallFracPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 179, Short.MAX_VALUE)
        );
        smallFracPanelLayout.setVerticalGroup(
            smallFracPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 137, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(startFracBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveBtn))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ydivField, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(xdivField)
                                    .addComponent(cxField))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(cyField, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pointerRangeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(selectRangeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(iterField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(61, 61, 61)
                                        .addComponent(jLabel9)
                                        .addGap(53, 53, 53))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(xminField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(yminField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGap(18, 18, 18)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(ymaxField, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(xmaxField, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4)))
                            .addComponent(smallFracPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xmaxField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xminField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yminField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ymaxField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cxField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel15)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(iterField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pointerRangeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(xdivField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ydivField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(selectRangeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(smallFracPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startFracBtn, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(saveBtn, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Fractal Setup", jPanel1);

        mouseToggleBtn.setText("Mouse Start Off");
        mouseToggleBtn.setActionCommand("");
        mouseToggleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mouseToggleBtnActionPerformed(evt);
            }
        });

        jLabel10.setText("R");

        jLabel11.setText("G");

        jLabel12.setText("B");

        jLabel13.setText("Multipliers");

        jLabel14.setText("Alpha");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mouseToggleBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(119, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rField, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel14))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gField, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(bField, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(alphaBox, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(alphaBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 263, Short.MAX_VALUE)
                .addComponent(mouseToggleBtn)
                .addGap(55, 55, 55))
        );

        jTabbedPane1.addTab("Control Panel", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fractalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fractalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startFracBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startFracBtnActionPerformed

        saveRange();
        startDraw();
    }//GEN-LAST:event_startFracBtnActionPerformed

    private void ydivFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ydivFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ydivFieldActionPerformed

    private void mouseToggleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mouseToggleBtnActionPerformed
        if (mouseToggleBtn.isSelected()) {
            mouseToggleBtn.setText("Mouse Start On");
        } else {
            mouseToggleBtn.setText("Mouse Start Off");
        }

    }//GEN-LAST:event_mouseToggleBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        int res = jfc.showSaveDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            fractalPanel.saveImage(selectedFile);
        }

    }//GEN-LAST:event_saveBtnActionPerformed
    int totalRot = 0;
    private void selectRangeSliderMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_selectRangeSliderMouseWheelMoved

        totalRot += evt.getPreciseWheelRotation();
        if (totalRot < 0) {
            totalRot = 0;
        } else if (totalRot > 100) {
            totalRot = 100;
        }

        selectRangeSlider.setValue(totalRot);


    }//GEN-LAST:event_selectRangeSliderMouseWheelMoved

    private void selectRangeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_selectRangeSliderStateChanged
        totalRot = selectRangeSlider.getValue();
        pointerInc = totalRot / (selectRangeSlider.getMaximum() * 1.0);
        pointerRangeTextField.setText(String.valueOf(selectRangeSlider.getValue() / (selectRangeSlider.getMaximum() * 1.0)));
    }//GEN-LAST:event_selectRangeSliderStateChanged

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        fdm.setTopFrame(this);
    }//GEN-LAST:event_formFocusGained

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
         fdm.setTopFrame(this);
    }//GEN-LAST:event_formInternalFrameActivated

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        fdm.setTopFrame(null);
    }//GEN-LAST:event_formInternalFrameDeactivated

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox alphaBox;
    private javax.swing.JTextField bField;
    private javax.swing.JTextField cxField;
    private javax.swing.JTextField cyField;
    private com.dookoonu.math.graph2d.FracPanel fractalPanel;
    private javax.swing.JTextField gField;
    private javax.swing.JTextField iterField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JFileChooser jfc;
    private javax.swing.JToggleButton mouseToggleBtn;
    private javax.swing.JTextField pointerRangeTextField;
    private javax.swing.JTextField rField;
    private javax.swing.JButton saveBtn;
    private javax.swing.JSlider selectRangeSlider;
    private com.dookoonu.math.graph2d.FracPanel smallFracPanel;
    private javax.swing.JButton startFracBtn;
    private javax.swing.JTextField xdivField;
    private javax.swing.JTextField xmaxField;
    private javax.swing.JTextField xminField;
    private javax.swing.JTextField ydivField;
    private javax.swing.JTextField ymaxField;
    private javax.swing.JTextField yminField;
    // End of variables declaration//GEN-END:variables
}
