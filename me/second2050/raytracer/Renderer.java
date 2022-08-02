package me.second2050.raytracer;

import java.util.Random;

class Renderer extends Thread {
    private String result;
    private int start;
    private int end;
    private Camera cam;
    private Hittables world;

    private final Random RAND = new Random();

    public Renderer(int start, int end, Camera cam, Hittables world) {
        this.start = start;
        this.end = end;
        this.cam = cam;
        this.world = world;
    }

    public void run() {
        System.out.printf("I am here!\n  %d→%d\n", start, end);

        StringBuilder sb = new StringBuilder();

        for ( int i = start; i >= end; i-- ) {
            for ( int j = 0; j < RaytracerMain.IMAGE_WIDTH; j++ ) {
                Color pixelColor = new Color(0, 0, 0);
                for ( int k = 0; k < RaytracerMain.SAMPLES_PER_PIXEL; k++ ) {
                     double u = (j + RAND.nextDouble()) / (RaytracerMain.IMAGE_WIDTH-1);
                     double v = (i + RAND.nextDouble()) / (RaytracerMain.IMAGE_HEIGHT-1);
                     Ray r = cam.getRay(u, v);
                     pixelColor = pixelColor.add(getRayColor(r, world, RaytracerMain.MAX_DEPTH, RAND)).toColor();
                }
                sb.append(String.format("%s\n", pixelColor.getPpmColor(RaytracerMain.SAMPLES_PER_PIXEL)));
            }
        }

        System.out.printf("Almost done!\n  %d→%d\n", start, end);
        result = sb.toString();
        System.out.printf("I am done!\n  %d→%d\n", start, end);
        return;
    }
    public String getResult() {
        return this.result;
    }

    private static Color getRayColor(Ray r, Hittable object, int depth, Random rand) {
        HitRecord rec = object.hit(r, 0.001, Utility.INFINITY);

        if (depth <= 0) { return new Color(0, 0, 0); }

        if (rec.gotHit()) {
            Ray scattered = null;
            Color attenuation = new Color(1, 1, 1);
            ScatterResult scatterResult = rec.getMaterial().scatter(r, rec, attenuation, scattered, rand);
            if (scatterResult.result) {
                scattered = scatterResult.scattered;
                attenuation = scatterResult.attenuation;
                return attenuation.multiply(getRayColor(scattered, object, depth-1, rand)).toColor();
            }
            return new Color(0, 0, 0);
        }

        Vector direction = r.getDirection();
        double t = 0.5 * (direction.getY() + 1.0);
        Vector result = (Vector.getNew(1.0,1.0,1.0).multiply(1.0 - t)).add((Vector.getNew(0.5, 0.7, 1.0)).multiply(t));
        return result.toColor();
    }
}
