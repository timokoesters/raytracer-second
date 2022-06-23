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
        return new String(String.format("%d %d %d", (int)r, (int)g, (int)b));
    }
    public Vector toVector() {
        return new Vector(r, g, b);
    }
}
