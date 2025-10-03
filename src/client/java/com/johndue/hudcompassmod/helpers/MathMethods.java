package com.johndue.hudcompassmod.helpers;

public class MathMethods {
    private static double wrapAngle(double x) {
        double shifted = (x + 1) % 2;

        if (shifted < 0) {
            shifted += 2;
        }

        return shifted - 1;
    }

    public static int getAlphaFromOffset(int offset, int compass_width, int text_width) {
        double normalized = (1.0 - (double)Math.abs(offset) / ((compass_width - text_width/2) / 2));
        normalized = Math.sqrt(normalized);
        int alpha = (int)(normalized * 255);

        return alpha;
    }

    public static int getCompassOffset(double camera_angle, double direction_angle, int compass_width, int compass_fov) {
        double offset = wrapAngle(camera_angle-direction_angle) * compass_width;
    
        return (int)(offset/compass_fov * 180);
    }
}
