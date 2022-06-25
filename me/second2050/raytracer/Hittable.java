package me.second2050.raytracer;

abstract class Hittable {
    abstract HitRecord hit(Ray r, double tMin, double tMax);
}
