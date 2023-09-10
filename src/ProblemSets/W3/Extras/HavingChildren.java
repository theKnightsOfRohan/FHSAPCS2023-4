package ProblemSets.W3.Extras;

import GeneralHelpers.Annotations.HelperMethod;
import GeneralHelpers.Annotations.RunnableMethod;

public class HavingChildren {
    public static void main(String[] args) {
        makeDataTable(.05, .95);
    }

    /*
     * A couple beginning a family decide to keep having children until they have at
     * least one boy and at least one girl (this seems like a terrible idea to me).
     * Estimate the average number of children they will have.
     */

    /*
     * a). Write a method to return this value. Your method should take two
     * parameters: p, the probability of having a girl, and n, the total number of
     * trials to use in calculating the result.
     */

    @RunnableMethod
    public static double calcAverageNumChildren(int trials, double genderProbability) {
        double totalNumOfChildren = 0;

        for (int i = 1; i <= trials; i++) {
            totalNumOfChildren += numOfChildren(genderProbability);
        }

        return totalNumOfChildren / trials;
    }

    @HelperMethod
    private static double numOfChildren(double genderProbability) {
        boolean gender1 = Math.random() < genderProbability;
        double numOfChildren = 1;

        boolean gender2;
        do {
            gender2 = Math.random() < genderProbability;
            numOfChildren++;
        } while (gender1 == gender2);

        return numOfChildren;
    }

    /*
     * b). Display a data table for the average number of children for all values of
     * p starting at 0.05 and increasing to 0.95 in increments of 0.05.
     */

    @RunnableMethod
    public static void makeDataTable(double lowerProb, double upperProb) {
        for (double i = lowerProb; i <= upperProb; i += .05) {
            System.out.println(i + ": " + calcAverageNumChildren(10000, i));
        }
    }
}