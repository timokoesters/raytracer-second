package me.second2050.raytracer;

public class DielectricMaterial extends Material {
    double refractionIndex;

    DielectricMaterial(double refractionIndex) {
        this.refractionIndex = refractionIndex;
    }

    @Override
    public ScatterResult scatter(Ray in, HitRecord rec, Color attenuation, Ray scattered) {
        attenuation = new Color(1, 1, 1);
        double refractionRatio = rec.getFrontFace() ? (1.0/refractionIndex) : refractionIndex;

        Vector unitDirection = in.getDirection().getUnitVector();
        Vector refracted = unitDirection.refract(rec.getNormal(), refractionRatio);

        scattered = new Ray(rec.getPos(), refracted);
        return new ScatterResult(true, scattered, attenuation);
    }
    
}
