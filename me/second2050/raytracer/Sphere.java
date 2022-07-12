package me.second2050.raytracer;

public class Sphere extends Hittable {
    private Vector center;
    private double radius;
    private Material material;

    // Constructor
    public Sphere(Vector center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    // Getter Functions
    public Vector getCenter() {
        return center;
    }
    public double getRadius() {
        return radius;
    }
    public Material getMaterial() {
        return material;
    }

    @Override
    HitRecord hit(Ray r, double tMin, double tMax) {
        Vector originCentre = r.getOrigin().subtract(center);
        double a = r.getDirection().dot(r.getDirection());
        double halfB = originCentre.dot(r.getDirection());
        double c = originCentre.dot(originCentre) - radius*radius;

        double discriminant = halfB*halfB - a*c;
        if (discriminant < 0) { return new HitRecord(); } // no hit
        double sqrtd = Math.sqrt(discriminant);

        // find the nearest root that is in the acceptable range: t_min < t < t_max
        double root = ( -halfB - sqrtd ) / a;
        if (root < tMin || tMax < root) {
            root = (-halfB + sqrtd) / a;
            if (root < tMin || tMax < root) {
                return new HitRecord(); // no hit
            }
        }
        double recT = root;
        Vector recP = r.getTarget(recT);
        Vector recN = recP.subtract(center).divide(radius);
        HitRecord result = new HitRecord(recP, recN, recT, material);
        result.setFaceNormal(r, recN);

        return result;
    }
}
