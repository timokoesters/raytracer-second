package me.second2050.raytracer;

public class HitRecord {
    private boolean hit;
    private Vector pos;
    private Vector normal;
    private double t;
    private boolean frontFace;
    private Material material;

    // Constructor
    public HitRecord(Vector pos, Vector normal, double t, Material material) {
        this.hit = true;
        this.pos = pos;
        this.normal = normal;
        this.t = t;
        this.material = material;
    }
    public HitRecord() {
        this.hit = false;
    }

    // Getter Functions
    public boolean gotHit() {
        return hit;
    }
    public Vector getPos() {
        return pos;
    }
    public Vector getNormal() {
        return normal;
    }
    public double getT() {
        return t;
    }
    public Material getMaterial() {
        return material;
    }
    public boolean getFrontFace() {
        return frontFace;
    }

    // Other Functions
    public void setFaceNormal(Ray r, Vector outwardNormal) {
        frontFace = r.getDirection().dot(outwardNormal) < 0;
        // normal = frontFace ? outwardNormal : -outwardNormal // get negative outwardNormal
        normal = frontFace ? outwardNormal : outwardNormal.subtract(outwardNormal.multiply(2));
    }
}
