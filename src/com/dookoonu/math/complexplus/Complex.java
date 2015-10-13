package com.dookoonu.math.complexplus;

public class Complex {

    public double x, y;
    double zeroLimit = 5.0e-10;

    public Complex() {
        x = 0;
        y = 0;
    }

    public Complex(double a, double b) {
        x = a;
        y = b;
    }

    public Complex(int a, int b) {
        x = a;
        y = b;
    }

    public void setxy(double r, double i) {
        x = r;
        y = i;
    }

    public void conjugate() {
        y = -y;
    }

    public void negate() {
        x = -x;
        y = -y;
    }

    public double norm() {
        return (x * x + y * y);
    }

    public double abs() {
        return Math.sqrt(x * x + y * y);
    }

    public double Arg() {
        return Math.atan(y / x);
    }

    public void normalize() {
        double n;

        n = Math.sqrt(norm());
        x = x / n;
        y = y / n;

    }

    public double[] parts() {
        double[] temp = {x, y};

        return temp;
    }

    public Complex square() {
        return new Complex(x * x - y * y, 2 * x * y);
    }

    public void squareInPlace(){
        double x1=x * x - y * y, y1=2 * x * y;
        x = x1; y=y1;
    }
    
    public Complex inverse() {
        Complex tmp = new Complex();
        double denom = norm();

        tmp.x = x / denom;
        tmp.y = -y / denom;

        return tmp;
    }

    public Complex add(Complex z) {
        Complex tmp = new Complex();

        tmp.x = x + z.x;
        tmp.y = y + z.y;

        return tmp;
    }

    public void addInPlace(Complex z){
        x += z.x;
        y += z.y;
    }
    
    public Complex sub(Complex z) {
        Complex tmp = new Complex();

        tmp.x = x - z.x;
        tmp.y = y - z.y;
        return tmp;
    }

    public Complex multiply(Complex z) {
        Complex tmp = new Complex();

        tmp.x = x * z.x - y * z.y;
        tmp.y = y * z.x + x * z.y;

        return tmp;
    }

    public Complex multiply(double k) {
        Complex tmp = new Complex();
        tmp.x = k * tmp.x;
        tmp.y = k * tmp.y;
        return tmp;
    }

    public void Multiply(double k) {
        x *= k;
        y *= k;
    }

    public void setZeroLimit(double zl) {
        zeroLimit = zl;
    }

    public void testZero() {
        x = x < zeroLimit ? 0 : x;
        y = y < zeroLimit ? 0 : y;
    }

    public static Complex[] roots(Complex z, int root) {
        double invroot = 1.0 / root;
        Complex[] theRoots = new Complex[root];
        double absroot = Math.pow(z.abs(), invroot);
        double arg = z.Arg();
        for (int k = 0; k < root; k++) {
            theRoots[k] = new Complex(absroot * (Math.cos(invroot * (arg + 2 * k * Math.PI))),
                    absroot * Math.sin(invroot * (arg + 2 * k * Math.PI)));
        }
        return theRoots;
    }

    @Override
    public String toString() {
        return Double.toString(x) + " + " + Double.toString(y) + "i";
    }
}
