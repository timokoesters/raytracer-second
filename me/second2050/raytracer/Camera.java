package me.second2050.raytracer;

public class Camera {
    public double viewportWidth;
    public double viewportHeight;
    public double aspectRatio;
    public double focalLength;

    private Vector origin;
    private Vector hor;
    private Vector vert;
    private Vector lowerLeftCorner;

    public Camera(double aspectRatio, double viewportHeight, double focalLength) {
        this.viewportHeight = viewportHeight;
        this.viewportWidth = aspectRatio * viewportHeight;
        this.focalLength = focalLength;

        this.origin = new Vector(0, 0, 0);
        this.hor = new Vector(viewportWidth, 0, 0);
        this.vert = new Vector(0, viewportHeight, 0);
        // lowerLeftCorner = origin - hor/2 - vert/2 - Vector(0, 0, focalLength);
        this.lowerLeftCorner = origin.subtract(hor.divide(2)).subtract(vert.divide(2)).subtract(new Vector(0, 0, focalLength));
    }

    public Ray getRay(double u, double v) {
        return new Ray(origin, lowerLeftCorner.add(hor.multiply(u)).add(vert.multiply(v)).subtract(origin));
    }
}
