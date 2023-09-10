package ProblemSets.W3.Extras;

import GeneralHelpers.Annotations.HelperMethod;
import GeneralHelpers.Annotations.RunnableMethod;

public class NthDimensionalDistance {
    public static void main(String[] args) {
        makeDataTable(10000, 300);
    }

    /*
     * 7. Higher dimensional geometry. Higher dimensional spaces are very important
     * in machine learning and data science. However, the geometry of higher
     * dimensions often doesn’t behave the way you expect based on your intuition
     * from 2nd and 3rd dimensional spaces.
     * Generate two random points from a d-dimensional space with each coordinate
     * between 0 and 1. Calculate the average distance between them over many
     * trials.
     * 
     * Output a table showing the average distance between two random points with
     * the dimension going from 2 to 30.
     * 
     * Your table should show that as the dimension increases, most of the volume of
     * the hypercube lies near the surface. (Think about why your numbers might
     * imply this). (Whereas our intuition of a square or a cube is that most of the
     * volume lies near the middle (not the perimeter)).
     */

    @RunnableMethod
    public static void makeDataTable(int i, int j) {
        for (int k = 2; k <= j; k++) {
            System.out.println(calcAverageDistance(i, k));
        }
    }

    @RunnableMethod
    public static double calcAverageDistance(int trials, int dimension) {
        double totalDistance = 0;

        for (int i = 0; i < trials; i++) {
            totalDistance += randomDistance(dimension);
        }

        return totalDistance / trials;
    }

    @HelperMethod
    private static double randomDistance(int dimension) {
        double[] point1 = new double[dimension];
        double[] point2 = new double[dimension];

        for (int i = 0; i < dimension; i++) {
            point1[i] = Math.random();
            point2[i] = Math.random();
        }

        return distance(point1, point2);
    }

    @HelperMethod
    private static double distance(double[] point1, double[] point2) {
        double sum = 0;

        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }

        return Math.sqrt(sum);
    }
}
