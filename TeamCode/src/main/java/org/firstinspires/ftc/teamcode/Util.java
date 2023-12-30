package org.firstinspires.ftc.teamcode;

public class Util {
    public static boolean closeEnough(double a, double b, int threshold) {
        double difference = Math.abs(a - b);
        return difference <= threshold;
    }
}
