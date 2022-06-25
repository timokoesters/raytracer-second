package me.second2050.raytracer;

import java.util.ArrayList;

public class Hittables extends Hittable {
    ArrayList<Hittable> objects;

    // Constructor
    public Hittables() {
        objects = new ArrayList<>();
    }

    // Functions
    public void add(Hittable object) {
        objects.add(object);
    }
    public void clear() {
        objects.clear();
    }

    @Override
    HitRecord hit(Ray r, double tMin, double tMax) {
        boolean hitAnything = false;
        double closest = tMax;
        HitRecord rec = new HitRecord();

        for (Hittable object : objects) {
            HitRecord tempRec = object.hit(r, tMin, closest);
            if (tempRec.gotHit()) {
                hitAnything = true;
                closest = tempRec.getT();
                rec = tempRec;
            }
        }
        return rec;
    }
}
