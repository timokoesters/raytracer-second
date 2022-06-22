package me.second2050.raytracer;

import java.io.*;

class RaytracerMain {
    // global variables
    static final int IMAGE_WIDTH = 256;
    static final int IMAGE_HEIGHT = 256;
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

        // render test image
        output.printf("P3\n%d %d\n255\n", IMAGE_WIDTH, IMAGE_HEIGHT); // write file header
        System.out.printf("\n");
        for (int i = IMAGE_HEIGHT-1; i >= 0; i--) {
            System.out.printf("\033[1F\033[1G\033[2K"); // go up 1 line and clear it
            System.out.printf("Scanlines remaining: %d\n", i);
            for (int j = 0; j < IMAGE_WIDTH; j++) {
                double r = (double)j / (IMAGE_WIDTH-1);
                double g = (double)i / (IMAGE_HEIGHT-1);
                double b = 0.25;

                int ir = (int)(255.999 * r);
                int ig = (int)(255.999 * g);
                int ib = (int)(255.999 * b);

                // System.out.printf("   i, j: %d %d\n", i, j);
                // System.out.printf("r, g, b: %f %f %f\n", r, g, b);
                output.printf("%d %d %d\n", ir, ig, ib); // write rendered pixel to file
            }
        }
        output.close(); // close file to ensure correct writing to storage
        return;
    }
}
