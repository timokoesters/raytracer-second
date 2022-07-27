package me.second2050.raytracer;

import java.io.*;
import java.util.Random;

class RaytracerMain {
    // global variables
    static final double IMAGE_ASPECT_RATIO = 16.0/9.0; // without decimal place it will be 1
    static final int IMAGE_WIDTH = 960;
    static final int IMAGE_HEIGHT = (int)(IMAGE_WIDTH / IMAGE_ASPECT_RATIO);
    static final String OUTPUT_FILE_NAME = "output.ppm";
    static final int SAMPLES_PER_PIXEL = 50;
    static final double CAMERA_FOCAL_LENGTH = 1.0;
    static final int MAX_DEPTH = 50;
    public static final Random RAND = new Random();

    public static void main(String[] args) {
        // open file
        File file;
        try {
            file = new File("./" + OUTPUT_FILE_NAME);
        } catch (Exception e) {
            System.out.printf("ERROR: Couldn't open output file: %s\n", OUTPUT_FILE_NAME);
            e.printStackTrace();
            System.exit(255);
            return;
        }
        
        // create file
        PrintWriter output;
        try {
            // delete file if it exists already
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            output = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file), 1024000), false);
        } catch (Exception e) {
            System.out.printf("ERROR: Couldn't create new output file: %s\n", OUTPUT_FILE_NAME);
            e.printStackTrace();
            System.exit(255);
            return;
        }

        // print image information
        System.out.printf("Image Dimensions: %dx%d (Aspect Ratio: %f)\n", IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_ASPECT_RATIO);
        System.out.printf("Output file: %s\n", OUTPUT_FILE_NAME);

        // setup world
        Hittables world = new Hittables();

        Material materialGround = new LambertianMaterial(new Color(0.8, 0.8, 0.0));
        Material materialCenter = new DielectricMaterial(1.5);
        Material materialLeft   = new LambertianMaterial(new Color(0.0, 0.5, 1.0));
        Material materialRight  = new MetalMaterial(new Color(0.8, 0.6, 0.2), 1.0);

        world.add(new Sphere(new Vector(0.0, -100.5, -1.0), 100.0, materialGround));
        world.add(new Sphere(new Vector(0.0, 0.0, -1.0), 0.485, materialCenter));
        world.add(new Sphere(new Vector(0.0, 0.0, -1.0), -0.475, materialCenter));
        world.add(new Sphere(new Vector(-1.0, 0.0, -1.0), 0.5, materialLeft));
        world.add(new Sphere(new Vector(1.0, 0.0, -1.0), 0.5, materialRight));

        // setup camera
        Camera cam = new Camera(IMAGE_ASPECT_RATIO, 2.0, CAMERA_FOCAL_LENGTH);

        // setup rng for antialiasing
        Random rand = new Random();

        // render image
        output.printf("P3\n%d %d\n255\n", IMAGE_WIDTH, IMAGE_HEIGHT); // write file header
        System.out.printf("\n");
        for (int i = IMAGE_HEIGHT-1; i >= 0; i--) {
            System.out.printf("\033[1F\033[1G\033[2K"); // go up 1 line and clear it
            System.out.printf("Scanlines remaining: %d\n", i);
            for (int j = 0; j < IMAGE_WIDTH; j++) {
                Color pixelColor = new Color(0, 0, 0);
                for (int k = 0; k < SAMPLES_PER_PIXEL; k++) {
                    double u = (j + rand.nextDouble()) / (IMAGE_WIDTH-1);
                    double v = (i + rand.nextDouble()) / (IMAGE_HEIGHT-1);
                    Ray r = cam.getRay(u, v);
                    pixelColor = pixelColor.add(getRayColor(r, world, MAX_DEPTH)).toColor();
                }
                output.printf("%s\n", pixelColor.getPpmColor(SAMPLES_PER_PIXEL)); // write rendered pixel to file
            }
        }
        output.flush();
        output.close(); // close file to ensure correct writing to storage
        return;
    }

    private static Color getRayColor(Ray r, Hittable object, int depth) {
        HitRecord rec = object.hit(r, 0.001, Utility.INFINITY);

        if (depth <= 0) { return new Color(0, 0, 0); }

        if (rec.gotHit()) {
            Ray scattered = null;
            Color attenuation = new Color(1, 1, 1);
            ScatterResult scatterResult = rec.getMaterial().scatter(r, rec, attenuation, scattered);
            if (scatterResult.result) {
                scattered = scatterResult.scattered;
                attenuation = scatterResult.attenuation;
                return attenuation.multiply(getRayColor(scattered, object, depth-1)).toColor();
            }
            return new Color(0, 0, 0);
        }

        Vector direction = r.getDirection();
        double t = 0.5 * (direction.getY() + 1.0);
        Vector result = (Vector.getNew(1.0,1.0,1.0).multiply(1.0 - t)).add((Vector.getNew(0.5, 0.7, 1.0)).multiply(t));
        return result.toColor();
    }
}
