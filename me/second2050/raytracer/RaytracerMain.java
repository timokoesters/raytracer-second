package me.second2050.raytracer;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.text.NumberFormatter;

class RaytracerMain {
    // global variables
    static final double IMAGE_ASPECT_RATIO = 16.0/9.0; // without decimal place it will be 1
    static final int IMAGE_WIDTH = 320;
    static final int IMAGE_HEIGHT = (int)(IMAGE_WIDTH / IMAGE_ASPECT_RATIO);
    static final String OUTPUT_FILE_NAME = "output.ppm";
    static final int SAMPLES_PER_PIXEL = 50;
    static final double CAMERA_FOCAL_LENGTH = 1.0;
    static final int CAMERA_FIELD_OF_VIEW = 45;
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
        Material materialCenter2= new MetalMaterial(new Color(1.0, 0.0, 0.5), 0.25);
        Material materialLeft   = new LambertianMaterial(new Color(0.0, 0.5, 1.0));
        Material materialRight  = new MetalMaterial(new Color(0.8, 0.6, 0.2), 0.25);

        world.add(new Sphere(new Vector(0.0, -100.5, -1.0), 100.0, materialGround));
        world.add(new Sphere(new Vector(0.0, 0.0, -1.0), 0.485, materialCenter));
        world.add(new Sphere(new Vector(0.0, 0.0, -1.0), -0.475, materialCenter));
        world.add(new Sphere(new Vector(0.0, 0.0, -1.0), 0.25, materialCenter2));
        world.add(new Sphere(new Vector(-1.0, 0.0, -1.0), 0.5, materialLeft));
        world.add(new Sphere(new Vector(1.0, 0.0, -1.0), 0.5, materialRight));

        // setup camera
        Camera cam = new Camera(new Vector(-2, 2, 1), new Vector(0, 0, -1), new Vector(0, 1, 0), IMAGE_ASPECT_RATIO, 2.0, CAMERA_FIELD_OF_VIEW, CAMERA_FOCAL_LENGTH);

        // setup rng for antialiasing
        Random rand = new Random();

        // start rendering timer
        long startTime = System.currentTimeMillis();

        // render image
        output.printf("P3\n%d %d\n255\n", IMAGE_WIDTH, IMAGE_HEIGHT); // write file header
        System.out.printf("\n");
        Renderer[] renderers = new Renderer[2];
        for (int i = 0; i < renderers.length; i++) {
            renderers[i] = new Renderer(100, 120);
            renderers[i].start();
        }
        for (int i = 0; i < renderers.length; i++) {
            try {
                renderers[i].join();
            } catch (Exception e) {
                e.printStacktrace();
                System.exit(100);
            }
            output.printf("%s", renderers[i].getResult());
        }


        // output runtime to user
        long endTime = System.currentTimeMillis();
        System.out.printf("Rendering time: %f seconds", (endTime - startTime) / 1000d);

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
