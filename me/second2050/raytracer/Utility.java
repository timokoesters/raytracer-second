package me.second2050.raytracer;

public class Utility {
    // Constants
    public static final double INFINITY = Double.MAX_VALUE;
    public static final double PI = Math.PI;

    // Utility Functions
    public static double degToRad(double degrees) {
        return degrees * PI / 180.0;
    }
    public static double clamp(double x, double min, double max) {
        if (x < min) { return min; }
        if (x > max) { return max; }
        return x;
    }
}
