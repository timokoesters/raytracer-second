package me.second2050.raytracer;

public class LambertianMaterial extends Material {
    Color albedo;

    LambertianMaterial(Color a) {
        this.albedo = a;
    }

    // Getter Functions
    public Color getAlbedo() {
        return albedo;
    }

    @Override
    public ScatterResult scatter(Ray in, HitRecord rec, Color attenuation, Ray scattered) {
        Vector scatterDirection = rec.getNormal().add(Vector.getRandomUnitVector());

        // catch degenerate scatter direction
        if (scatterDirection.nearZero()) { scatterDirection = rec.getNormal(); }

        scattered = new Ray(rec.getPos(), scatterDirection);
        attenuation = albedo;
        return new ScatterResult(true, scattered, attenuation);
    }
    
}
