package me.second2050.raytracer;

public class Color extends Vector {
    private double r = super.getX();
    private double g = super.getY();
    private double b = super.getZ();

    //Constructor
    public Color(double r, double g, double b) {
        super(r, g, b);
    }

    // functions
    public String getPpmColor() {
        int newR = (int)(255.999 * r);
        int newG = (int)(255.999 * g);
        int newB = (int)(255.999 * b);
        return new String(String.format("%d %d %d", newR, newG, newB));
    }
}
