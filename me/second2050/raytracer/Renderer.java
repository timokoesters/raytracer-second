package me.second2050.raytracer;

class Renderer implements Runnable {
    private String result;
    private int start;
    private int end;

    Public Renderer(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public void run() {
        StringBuilder sb = new StringBuilder();

        for ( int i = start; i >= end; i-- ) {
            for ( int j = 0; j < IMAGE_WIDTH; j++ ) {
                Color pixelColor = new Color(0, 0, 0);
                for ( int k = 0; k < SAMPLES_PER_PIXEL; k++ ) {
                     double u = (j + rand.nextDouble()) / (IMAGE_WIDTH-1);
                     double v = (i + rand.nextDouble()) / (IMAGE_HEIGHT-1);
                     Ray r = cam.getRay(u, v);
                     pixelColor = pixelColor.add(getRayColor(r, world, MAX_DEPTH)).toColor();
                }
                sb.append(String.format("%s\n", pixelColor.getPpmColor(SAMPLES_PER_PIXEL)));
            }
        }

        result = sb.toString;
        return;
    }
    public String getResult() {
        return this.result;
    }
}
