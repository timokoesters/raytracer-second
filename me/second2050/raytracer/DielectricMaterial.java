package me.second2050.raytracer;

import java.util.Random;

public class DielectricMaterial extends Material {
    double refractionIndex;

    DielectricMaterial(double refractionIndex) {
        this.refractionIndex = refractionIndex;
    }

    @Override
    public ScatterResult scatter(Ray in, HitRecord rec, Color attenuation, Ray scattered, Random rand) {
        attenuation = new Color(1, 1, 1);
        double refractionRatio = rec.getFrontFace() ? (1.0/refractionIndex) : refractionIndex;

        Vector unitDirection = in.getDirection().getUnitVector();
        double cosTheta = Math.min(unitDirection.multiply(-1).dot(rec.getNormal()), 1.0);
        double sinTheta = Math.sqrt(1.0 - cosTheta*cosTheta);

        boolean cannotRefract = refractionRatio * sinTheta > 1.0;
        Vector direction;

        if (cannotRefract || reflectance(cosTheta, refractionRatio) > Math.random()) {
            direction = unitDirection.reflect(rec.getNormal());
        } else {
            direction = unitDirection.refract(rec.getNormal(), refractionRatio);
        }

        scattered = new Ray(rec.getPos(), direction);
        return new ScatterResult(true, scattered, attenuation);
    }
    
    private static double reflectance(double cosine, double refractionIndex) {
        // Schlick approx.
        double r0 = (1-refractionIndex) / (1+refractionIndex);
        r0 = r0*r0;
        return r0 + (1-r0) * Math.pow((1 - cosine), 5);
    }
}
