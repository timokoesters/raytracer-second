package me.second2050.raytracer;

public class Ray {
    private Vector origin, direction;

    // Constructor
    public Ray(Vector origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    // Getter functions
    public Vector getOrigin() {
        return origin;
    }
    public Vector getDirection() {
        return direction;
    }
    public Vector getTarget() {
        return origin.add(direction);
    }
    public Vector getTarget(double t) {
        return origin.add(direction.multiply(t));
    }
}
