package me.second2050.raytracer;

import java.util.Random;

public class Vector {
    private double x, y, z;

    // Constructor
    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Getter Functions
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    // Basic Operations
    public Vector add(Vector that) {
        return new Vector(x + that.getX(), y + that.getY(), z + that.getZ());
    }
    public Vector add(double that) {
        return new Vector(x + that, y + that, z + that);
    }
    public Vector subtract(Vector that) {
        return new Vector(x - that.getX(), y - that.getY(), z - that.getZ());
    }
    public Vector subtract(double that) {
        return new Vector(x - that, y - that, z - that);
    }
    public Vector multiply(Vector that) {
        return new Vector(x * that.getX(), y * that.getY(), z * that.getZ());
    }
    public Vector multiply(double that) {
        return new Vector(x * that, y * that, z * that);
    }
    public Vector multiply(int that) {
        return new Vector(x * that, y * that, z * that);
    }
    public Vector divide(Vector that) {
        return new Vector(x / that.getX(), y / that.getY(), z / that.getZ());
    }
    public Vector divide(double that) {
        return new Vector(x / that, y / that, z / that);
    }
    public Vector divide(int that) {
        return new Vector(x / that, y / that, z / that);
    }
    public double dot(Vector that) {
        return this.x * that.getX() + 
               this.y * that.getY() + 
               this.z * that.getZ();
    }
    public Vector cross(Vector that) {
        double newX = this.y * that.getZ() - this.z * that.getY();
        double newY = this.z * that.getX() - this.x * that.getZ();
        double newZ = this.x * that.getY() - this.y * that.getX();
        return new Vector(newX, newY, newZ);
    }
    public double length() {
        return Math.sqrt(this.dot(this));
    }
    public Vector reflect(Vector that) {
        return this.subtract(that.multiply(2 * this.dot(that)));
    }
    public Vector refract(Vector that, double etaiOverEtat) {
        double cosTheta = Math.min(this.multiply(-1).dot(that), 1.0);
        Vector rOutPerp = this.add(that.multiply(cosTheta)).multiply(etaiOverEtat);
        Vector rOutParallel = that.multiply(-Math.sqrt(Math.abs(1.0 - (rOutPerp.length() * rOutPerp.length()))));
        return rOutPerp.add(rOutParallel);
    }

    // Functions
    public Color toColor() {
        return new Color(x, y, z);
    }
    public boolean nearZero() {
        double s = 1e-8;
        return (Math.abs(this.x) < s) && (Math.abs(this.y) < s) && (Math.abs(this.z) < s);
    }

    // Utility Functions
    public static Vector getNew(double x, double y, double z) {
        return new Vector(x, y, z);
    }
    public Vector getUnitVector() {
        return this.divide(this.length());
    }
    public static Vector getRandom(Random rand) {
        return new Vector(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
    }
    public static Vector getRandom(double min, double max, Random rand) {
        return new Vector(rand.nextDouble(min, max), rand.nextDouble(min, max), rand.nextDouble(min, max));
    }
    public static Vector getRandomInUnitSphere(Random rand) {
        while (true) {
            Vector p = Vector.getRandom(-1, 1, rand);
            if (p.length() * p.length() >= 1) { continue; }
            return p;
        }
    }
    public static Vector getRandomUnitVector(Random rand) {
        return Vector.getRandomInUnitSphere(rand).getUnitVector();
    }
    public static Vector getRandomInHemisphere(Vector normal, Random rand) {
        Vector inUnitSphere = Vector.getRandomInUnitSphere(rand);
        if (inUnitSphere.dot(normal) > 0.0) { return inUnitSphere; }
        else { return inUnitSphere.multiply(-1); }
    }
}
