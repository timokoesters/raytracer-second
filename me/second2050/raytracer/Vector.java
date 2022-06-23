package me.second2050.raytracer;

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

    // Functions
    public Color toColor() {
        return new Color(x, y, z);
    }

    // Utility Functions
    public static Vector getNew(double x, double y, double z) {
        return new Vector(x, y, z);
    }
}
