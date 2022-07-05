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
    public String getPpmColor(int samplesPerPixel) {
        double scale = 1.0 / samplesPerPixel;
        r = Math.sqrt(scale * r);
        g = Math.sqrt(scale * g);
        b = Math.sqrt(scale * b);

        int newR = (int)(256 * Utility.clamp(r, 0.0, 0.999));
        int newG = (int)(256 * Utility.clamp(g, 0.0, 0.999));
        int newB = (int)(256 * Utility.clamp(b, 0.0, 0.999));
        return new String(String.format("%d %d %d", newR, newG, newB));
    }
}
