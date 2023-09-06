package ProblemSets.W3;

import java.util.HashSet;
import GeneralHelpers.Annotations.HelperMethod;
import GeneralHelpers.Annotations.RunnableMethod;

public class D2 {
    public static void main(String[] args) {
        averageOfDisplacement3DDataTable(10000, 100);
    }

    @RunnableMethod
    public static void averageOfDisplacement2DDataTable(int trials, int steps) {
        System.out.println("Steps\tAverage Displacement");
        for (int i = 1; i <= steps; i++) {
            System.out.println(i + ":\t" + averageOfDisplacement2D(trials, i));
        }
    }

    @HelperMethod
    private static double averageOfDisplacement2D(int trials, int steps) {
        double avg = 0;
        for (int i = 0; i < trials; i++) {
            avg += displacementTrial2D(steps) / trials;
        }

        return avg;
    }

    @HelperMethod
    private static double displacementTrial2D(int steps) {
        int x = 0, y = 0;
        for (int i = 0; i < steps; i++) {
            int dir = (int) (Math.random() * 4);
            switch (dir) {
                case 0:
                    x++;
                    break;
                case 1:
                    x--;
                    break;
                case 2:
                    y++;
                    break;
                case 3:
                    y--;
                    break;
            }
        }

        return (Math.sqrt(x * x + y * y));
    }

    @RunnableMethod
    public static void averageOfNonIntersectingTrials2DDataTable(int trials, int steps) {
        System.out.println("Steps\tAverage Non-Intersecting Paths");
        for (int i = 1; i <= steps; i++) {
            System.out.println(i + ":\t" + averageOfNonIntersectingTrials2D(trials, i));
        }
    }

    @HelperMethod
    private static double averageOfNonIntersectingTrials2D(int trials, int steps) {
        double avg = 0;
        for (int i = 0; i < trials; i++) {
            avg += nonIntersectingPath2D(steps) / trials;
        }

        return avg;
    }

    @HelperMethod
    private static double nonIntersectingPath2D(int steps) {
        int x = 0, y = 0;
        HashSet<String> visited = new HashSet<>();
        visited.add("0,0");
        for (int i = 0; i < steps; i++) {
            int dir = (int) (Math.random() * 4);
            switch (dir) {
                case 0 -> x++;
                case 1 -> x--;
                case 2 -> y++;
                case 3 -> y--;
            }

            if (!visited.add(x + "," + y))
                return 0;
        }

        return 1;
    }

    @RunnableMethod
    public static void averageOfNonIntersectingTrials3DDataTable() {
        System.out.println("Steps\tAverage 3D Non-Intersecting Paths");
        for (int i = 1; i <= 100; i++) {
            System.out.println(i + ":\t" + averageOfNonIntersectingTrials3D(100, i));
        }
    }

    @HelperMethod
    private static double averageOfNonIntersectingTrials3D(int trials, int steps) {
        double avg = 0;
        for (int i = 0; i < trials; i++) {
            avg += nonIntersectingPath3D(steps) / trials;
        }

        return avg;
    }

    @HelperMethod
    private static double nonIntersectingPath3D(int steps) {
        int x = 0, y = 0, z = 0;
        HashSet<String> visited = new HashSet<>();
        visited.add("0,0,0");
        for (int i = 0; i < steps; i++) {
            int dir = (int) (Math.random() * 6);
            switch (dir) {
                case 0 -> x++;
                case 1 -> x--;
                case 2 -> y++;
                case 3 -> y--;
                case 4 -> z++;
                case 5 -> z--;
            }

            if (!visited.add(x + "," + y + "," + z))
                return 0;
        }

        return 1;
    }

    @RunnableMethod
    public static void averageOfDisplacement3DDataTable(int trials, int steps) {
        System.out.println("Steps\tAverage Displacement");
        for (int i = 1; i <= steps; i++) {
            System.out.println(averageOfDisplacement3D(trials, i));
        }
    }

    @HelperMethod
    private static double averageOfDisplacement3D(int trials, int steps) {
        double avg = 0;
        for (int i = 0; i < trials; i++) {
            avg += displacementTrial3D(steps) / trials;
        }

        return avg;
    }

    @HelperMethod
    private static double displacementTrial3D(int steps) {
        double x = 0, y = 0, z = 0;
        for (int i = 0; i < steps; i++) {
            double theta1 = Math.random() * 2 * Math.PI;
            double theta2 = Math.random() * 2 * Math.PI;

            x += Math.sin(theta1) * Math.cos(theta2);
            y += Math.sin(theta1) * Math.sin(theta2);
            z += Math.cos(theta2);
        }

        return (Math.sqrt(x * x + y * y + z * z));
    }
}
