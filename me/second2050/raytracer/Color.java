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
        int newR = (int)(255.999 * r);
        int newG = (int)(255.999 * g);
        int newB = (int)(255.999 * b);
        return new String(String.format("%d %d %d", newR, newG, newB));
    }
    public Vector toVector() {
        return new Vector(r, g, b);
    }
}
