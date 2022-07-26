package me.second2050.raytracer;

public abstract class Material {
    abstract public ScatterResult scatter(Ray in, HitRecord rec, Color attenuation, Ray scattered);
}
