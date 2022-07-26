package me.second2050.raytracer;

public class MetalMaterial extends Material {
    Color albedo;
    double fuzz;

    MetalMaterial(Color a) {
        this.albedo = a;
        this.fuzz = 0.0;
    }
    MetalMaterial(Color a, double fuzz) {
        this.albedo = a;
        this.fuzz = fuzz;
    }

    @Override
    public ScatterResult scatter(Ray in, HitRecord rec, Color attenuation, Ray scattered) {
        fuzz = fuzz < 1 ? fuzz : 1; //set to 1 if bigger
        Vector reflected = in.getDirection().getUnitVector().reflect(rec.getNormal());
        scattered = new Ray(rec.getPos(), Vector.getRandomInUnitSphere().multiply(fuzz).add(reflected));
        attenuation = albedo;
        return new ScatterResult(scattered.getDirection().dot(rec.getNormal()) > 0, scattered, attenuation);
    }
    
}
