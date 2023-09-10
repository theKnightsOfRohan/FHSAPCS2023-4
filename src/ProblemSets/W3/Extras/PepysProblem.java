package ProblemSets.W3.Extras;

import GeneralHelpers.Annotations.HelperMethod;
import GeneralHelpers.Annotations.RunnableMethod;

public class PepysProblem {
    public static void main(String[] args) {
        System.out.println(calcProbabilityOfNOnesInMDice(10000, 1, 6));
        System.out.println(calcProbabilityOfNOnesInMDice(10000, 2, 12));
    }

    /*
     * In 1693, Samuel Pepys asked Isaac Newton (co-inventor of
     * the Calculus) which was more likely: getting at least one 1 when rolling a
     * fair die 6 times or getting at least two 1's when rolling a fair die 12
     * times.
     * You will write a program to answer this general kind of question. Your method
     * should use 10000 trials to approximate the probability of getting at least n
     * 1’s when rolling a die m times (and, of course, n can’t be larger than m ).
     * Your method will look like this:
     */

    @RunnableMethod
    public static double calcProbabilityOfNOnesInMDice(int trials, int n, int m) {
        double totalNumOfNOnes = 0;

        for (int i = 0; i < trials; i++) {
            if (numOfNOnes(n, m))
                totalNumOfNOnes++;
        }

        return totalNumOfNOnes / trials;
    }

    @HelperMethod
    private static boolean numOfNOnes(int n, int m) {
        int numOfOnes = 0;

        for (int i = 0; i < m; i++) {
            if (Math.random() < 1.0 / 6) {
                numOfOnes++;
            }
        }

        return numOfOnes >= n;
    }
}
