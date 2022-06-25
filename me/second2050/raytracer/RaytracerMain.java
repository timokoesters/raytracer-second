package me.second2050.raytracer;

import java.io.*;

class RaytracerMain {
    // global variables
    static final double IMAGE_ASPECT_RATIO = 16.0/9.0; // without decimal place it will be 1
    static final int IMAGE_WIDTH = 480;
    static final int IMAGE_HEIGHT = (int)(IMAGE_WIDTH / IMAGE_ASPECT_RATIO);
    static final String OUTPUT_FILE_NAME = "output.ppm";

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
            output = new PrintWriter(file);
        } catch (Exception e) {
            System.out.printf("ERROR: Couldn't create new output file: %s\n", OUTPUT_FILE_NAME);
            e.printStackTrace();
            System.exit(255);
            return;
        }

        // print image information
        System.out.printf("Image Dimensions: %dx%d (Aspect Ratio: %f)\n", IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_ASPECT_RATIO);
        System.out.printf("Output file: %s\n", OUTPUT_FILE_NAME);

        // setup camera
        double viewportHeight = 2.0;
        double viewportWidth = IMAGE_ASPECT_RATIO * viewportHeight;
        double focalLength = 1.0;

        Vector origin = new Vector(0, 0, 0);
        Vector hor = new Vector(viewportWidth, 0, 0);
        Vector vert = new Vector(0, viewportHeight, 0);
        // lowerLeftCorner = origin - hor/2 - vert/2 - Vector(0, 0, focalLength);
        Vector lowerLeftCorner = origin.subtract(hor.divide(2)).subtract(vert.divide(2)).subtract(new Vector(0, 0, focalLength));

        // render image
        output.printf("P3\n%d %d\n255\n", IMAGE_WIDTH, IMAGE_HEIGHT); // write file header
        System.out.printf("\n");
        for (int i = IMAGE_HEIGHT-1; i >= 0; i--) {
            System.out.printf("\033[1F\033[1G\033[2K"); // go up 1 line and clear it
            System.out.printf("Scanlines remaining: %d\n", i);
            for (int j = 0; j < IMAGE_WIDTH; j++) {
                double u = (double)j / (IMAGE_WIDTH - 1);
                double v = (double)i / (IMAGE_HEIGHT - 1);
                Ray r = new Ray(origin, lowerLeftCorner.add(hor.multiply(u)).add(vert.multiply(v)).subtract(origin));
                Color pixelColor = getRayColor(r); // calculate pixel color from ray target
                output.printf("%s\n", pixelColor.getPpmColor()); // write rendered pixel to file
            }
        }
        output.close(); // close file to ensure correct writing to storage
        return;
    }

    private static Color getRayColor(Ray r) {
        // hard coded sphere
        double t = hitSphere(new Vector(0, 0, -1), 0.5, r);
        if (t > 0.0) { // if hitting sphere use it to color the pixel
            Vector n = r.getTarget(t).subtract(new Vector(0, 0, -1)).getUnitVector();
            return new Color(n.getX()+1, n.getY()+1, n.getZ()+1).multiply(0.5).toColor();
        }

        Vector direction = r.getDirection();
        t = 0.5 * (direction.getY() + 1.0);
        Vector result = (Vector.getNew(1.0,1.0,1.0).multiply(1.0 - t)).add((Vector.getNew(0.5, 0.7, 1.0)).multiply(t));
        return result.toColor();
    }
    private static double hitSphere(Vector sphereCentre, double radius, Ray r) {
        Vector originCentre = r.getOrigin().subtract(sphereCentre);
        double a = r.getDirection().dot(r.getDirection());
        double halfB = originCentre.dot(r.getDirection());
        double c = originCentre.dot(originCentre) - radius*radius;
        double discriminant = halfB*halfB - a*c;
        if (discriminant < 0) {
            return -1.0;
        } else {
            return ( -halfB - Math.sqrt(discriminant) ) / a;
        }
    }
}
