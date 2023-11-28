package ProblemSets.W3.Extras;

import java.util.Arrays;

import Utils.Annotations.HelperMethod;
import Utils.Annotations.RunnableMethod;

public class ProbabilityOfTriangle {
    public static void main(String[] args) {
        for (int i = 1; i < 100; i++) {
            System.out.println(probabilityOfPolygon(10000, i));
        }
    }

    /*
     * Suppose I have a stick of length 1. I select two places at random to break
     * it, giving me three pieces. What is the probability those pieces can be put
     * together to make a triangle? Approximate this probability using 10,000 random
     * experiments in which you break a stick and then check if the pieces can make
     * a triangle...
     */

    @RunnableMethod
    public static double probabilityOfTriangle(int trials) {
        int numOfTriangles = 0;

        for (int i = 1; i <= trials; i++) {
            if (triangle())
                numOfTriangles++;
        }

        return (double) numOfTriangles / trials;
    }

    @HelperMethod
    private static boolean triangle() {
        double[] pieces = new double[3];
        pieces[0] = 1 - Math.random();

        for (int i = 1; i < 3; i++) {
            pieces[i] = pieces[i - 1] - (Math.random() * pieces[i - 1]);
        }

        Arrays.sort(pieces);

        return (pieces[0] + pieces[1]) > pieces[2];
    }

    /*
     * Can you generalize this? What if I have a stick of length 1 and break it in k
     * places. What's the probability the resulting k+1 pieces will form a
     * (k+1)-gon?
     */

    @RunnableMethod
    public static double probabilityOfPolygon(int trials, int k) {
        int numOfPolygons = 0;

        for (int i = 1; i <= trials; i++) {
            if (polygon(k))
                numOfPolygons++;
        }

        return (double) numOfPolygons / trials;
    }

    @HelperMethod
    private static boolean polygon(int k) {
        double[] pieces = new double[k];
        pieces[0] = 1 - Math.random();

        for (int i = 1; i < k; i++) {
            pieces[i] = pieces[i - 1] - (Math.random() * pieces[i - 1]);
        }

        Arrays.sort(pieces);

        double sum = 0;
        for (int i = 0; i < pieces.length - 1; i++) {
            sum += pieces[i];
        }

        return sum > pieces[pieces.length - 1];
    }
}
