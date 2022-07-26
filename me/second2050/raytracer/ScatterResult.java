package me.second2050.raytracer;

public class ScatterResult {
    boolean result;
    Ray scattered;
    Color attenuation;

    ScatterResult(boolean result, Ray scattered, Color attenuation) {
        this.result = result;
        this.scattered = scattered;
        this.attenuation = attenuation;
    }
}
