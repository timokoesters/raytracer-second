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

    public Camera(Vector lookFrom, Vector lookAt, Vector vUp,  double aspectRatio, double viewportHeight, double verticalFOV, double focalLength) {
        double theta = Utility.degToRad(verticalFOV);
        double h = Math.tan(theta / 2);
        this.viewportHeight = viewportHeight * h;
        this.viewportWidth = aspectRatio * this.viewportHeight;
        this.focalLength = focalLength;

        Vector w = lookFrom.subtract(lookAt).getUnitVector();
        Vector u = vUp.cross(w).getUnitVector();
        Vector v = w.cross(u);

        origin = lookFrom;
        hor = u.multiply(this.viewportWidth);
        vert = v.multiply(this.viewportHeight);
        lowerLeftCorner = origin.subtract(hor.divide(2)).subtract(vert.divide(2)).subtract(w);
    }

    public Ray getRay(double u, double v) {
        return new Ray(origin, lowerLeftCorner.add(hor.multiply(u)).add(vert.multiply(v)).subtract(origin));
    }
}
