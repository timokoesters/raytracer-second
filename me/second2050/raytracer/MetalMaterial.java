package me.second2050.raytracer;

public class MetalMaterial extends Material {
    Color albedo;

    MetalMaterial(Color a) {
        this.albedo = a;
    }

    @Override
    public ScatterResult scatter(Ray in, HitRecord rec, Color attenuation, Ray scattered) {
        Vector reflected = in.getDirection().getUnitVector().reflect(rec.getNormal());
        scattered = new Ray(rec.getPos(), reflected);
        attenuation = albedo;
        return new ScatterResult(scattered.getDirection().dot(rec.getNormal()) > 0, scattered, attenuation);
    }
    
}
