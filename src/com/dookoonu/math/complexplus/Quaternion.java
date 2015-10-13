package com.dookoonu.math.complexplus;

import java.lang.Math;

public class Quaternion {

    double zeroLimit = 5.0e-10;
    public double q0, q1, q2, q3;

    public Quaternion() {
        q0 = 0;
        q1 = 0;
        q2 = 0;
        q3 = 0;
    }

    public Quaternion(double a, double b, double c, double d) {
        q0 = a;
        q1 = b;
        q2 = c;
        q3 = d;
    }

    public Quaternion(int a, int b, int c, int d) {
        q0 = a;
        q1 = b;
        q2 = c;
        q3 = d;
    }

    public void setRIJK(double a, double b, double c, double d) {
        q0 = a;
        q1 = b;
        q2 = c;
        q3 = d;
    }

    public void conjugate() {
        q1 = -q1;
        q2 = -q2;
        q3 = -q3;
    }

    public double norm() {
        return (q0 * q0 + q1 * q1 + q2 * q2 + q3 * q3);
    }

    public void normalize() {
        double n;

        n = Math.sqrt(norm());
        q0 = q0 / n;
        q1 = q1 / n;
        q2 = q2 / n;
        q3 = q3 / n;
    }

    public double[] parts() {
        double[] temp = {q0, q1, q2, q3};

        return temp;
    }

    public Quaternion multiply(Quaternion a) {
        Quaternion result = new Quaternion();

        result.q0 = a.q0 * q0 - a.q1 * q1 - a.q2 * q2 - a.q3 * q3;
        result.q1 = a.q0 * q1 + a.q1 * q0 + a.q2 * q3 - a.q3 * q2;
        result.q2 = a.q0 * q2 + a.q2 * q0 + a.q3 * q1 - a.q1 * q3;
        result.q3 = a.q0 * q3 + a.q3 * q0 + a.q1 * q2 - a.q2 * q1;

        return result;
    }

    public Quaternion multiply(double a) {

        return new Quaternion(q0 * a, q1 * a, q2 * a, q3 * a);
    }

    public Quaternion add(Quaternion a) {
        return new Quaternion(q0 + a.q0, q1 + a.q1, q2 + a.q2, q3 + a.q3);
    }

    public Quaternion add(double a) {
        return new Quaternion(q0 + a, q1, q2, q3);
    }

    public Quaternion add(int a) {
        return new Quaternion(q0 + a, q1, q2, q3);
    }

    public Quaternion subtract(Quaternion a) {
        return new Quaternion(q0 - a.q0, q1 - a.q1, q2 - a.q2, q3 - a.q3);
    }

    public Quaternion negate(Quaternion a) {
        return new Quaternion(-a.q0, -a.q1, -a.q2, -a.q3);
    }

    public Quaternion inverse() {
        double n;

        n = norm();
        return new Quaternion(q0 / n, -q1 / n, -q2 / n, -q3 / n);
    }

    public Quaternion inner(Quaternion x) {
        // This is the inner automorphism, where this quaternion is a.
        Quaternion temp = new Quaternion();
        Quaternion result = new Quaternion();

        Quaternion temp2 = inverse();

        //temp= a*x
        temp.q0 = q0 * x.q0 - q1 * x.q1 - q2 * x.q2 - q3 * x.q3;
        temp.q1 = q0 * x.q1 + q1 * x.q0 + q2 * x.q3 - q3 * x.q2;
        temp.q2 = q0 * x.q2 + q2 * x.q0 + q3 * x.q1 - q1 * x.q3;
        temp.q3 = q0 * x.q3 + q3 * x.q0 + q1 * x.q2 - q2 * x.q1;

        //result = temp * a^-1
        result.q0 = temp.q0 * temp2.q0 - temp.q1 * temp2.q1 - temp.q2 * temp2.q2 - temp.q3 * temp2.q3;
        result.q1 = temp.q0 * temp2.q1 + temp.q1 * temp2.q0 + temp.q2 * temp2.q3 - temp.q3 * temp2.q2;
        result.q2 = temp.q0 * temp2.q2 + temp.q2 * temp2.q0 + temp.q3 * temp2.q1 - temp.q1 * temp2.q3;
        result.q3 = temp.q0 * temp2.q3 + temp.q3 * temp2.q0 + temp.q1 * temp2.q2 - temp.q2 * temp2.q1;

        return result;

    }

    public void setZeroLimit(double zl) {
        zeroLimit = zl;
    }

    public void testZero() {
        q0 = q0 < zeroLimit ? 0 : q0;
        q1 = q1 < zeroLimit ? 0 : q1;
        q2 = q2 < zeroLimit ? 0 : q2;
        q3 = q3 < zeroLimit ? 0 : q3;
    }

    public String toString() {
        return Double.toString(q0) + " + " + Double.toString(q1) + "i" + " + " + Double.toString(q2) + "j" + " + "
                + Double.toString(q3) + "k";
    }
}
