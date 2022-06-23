package me.second2050.raytracer;

public class Color {
    double r, g, b;

    //Constructor
    public Color(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    // functions
    public String getPpmColor() {
        return new String(String.format("%d %d %d", r, g, b));
    }
    public Vector toVector() {
        return new Vector(r, g, b);
    }

    // utility functions
    public Color getRayColor(Ray r) {
        Vector direction = r.getDirection();
        double t = 0.5 * (direction.getY() + 1.0);
        Vector result = (Vector.getNew(1,1,1).multiply(1.0 - t)).add((Vector.getNew(0.5, 0.7, 1.0)).multiply(t));
        return result.toColor();
    }
}
