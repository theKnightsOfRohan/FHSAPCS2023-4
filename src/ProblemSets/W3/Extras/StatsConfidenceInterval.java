package ProblemSets.W3.Extras;

import GeneralHelpers.Annotations.RunnableMethod;

public class StatsConfidenceInterval {
    public static void main(String[] args) {
        calcConfidenceInterval(10000, .00001);
    }

    /*
     * How many trials should you run in approximating your result? One principled
     * way to answer this question is using the idea of a “confidence interval” from
     * statistics. Intuitively you know that 10 trials would be too few because of
     * the random variation for each trial. And intuitively you know that the more
     * trials you have, the more confident you can be that your long-term results
     * are accurate. In this problem you’ll learn how to construct a 95% “confidence
     * interval”. If you have few trials your interval will be wide, but as you
     * increase the number of trials, your interval will become narrower. When your
     * interval is more narrow than your desired precision, you stop performing
     * experiments.
     * Idea of a confidence interval: Let’s say I run an experiment 6 times and I
     * get outcomes of 55, 56, 55, 56, 57, and 57. The mean value is 56, and because
     * there’s very little variation from the mean, I should be able to be confident
     * that the true expected outcome is between 55 and 57.
     * But what if I run an experiment 6 times and the outcomes are 2, 109, 45, 23,
     * 89, and 56. The mean is 54, but there’s so much variability that I’m not very
     * confident the real mean is between 53 and 55; it could easily be something
     * else.
     * If you construct a 95% confidence interval, you’re sure that 95 out of 100
     * times you do this your interval will contain the true mean. (This is slightly
     * different than saying there’s a 95% chance that the interval you DID
     * construct contains the true mean).
     * Here’s the math: If you run T trials, you can calculate the mean and the
     * variance this way:
     * 
     * represents the mean and 2represents the variance. (The variance is a measure
     * of how much the data is “spread out” around the mean.
     * For a 95% confidence interval, the low and high values are given by:
     * As you can see, if T gets very large then the fractional term will get very
     * small and the overall interval will narrow. Note that you will need to run a
     * minimum number of trials before you start calculating confidence intervals.
     * For your simulations in this problem set, let’s call that number 100.
     * You can see this white paper from a financial investment group which explains
     * the same idea above.
     * Programming task: For one (or more) of your problems, have the user specify
     * acceptable error (a double, say “0.01”). Then, as you perform more trials,
     * calculate the mean, standard deviation and bounds for a 95% confidence
     * interval. When the width of the confidence interval is smaller than your
     * acceptable error, you can stop running experiments!
     */

    @RunnableMethod
    public static void calcConfidenceInterval(int dataSetSize, double acceptableError) {
        double mean = 0;
        double variance = 0;
        double low = 0;
        double high = 0;

        for (int i = 0; i < dataSetSize; i++) {
            double result = Math.random();
            mean += result;
            variance += result * result;
        }

        mean /= dataSetSize;
        variance /= dataSetSize;
        variance -= mean * mean;

        low = mean - 2 * Math.sqrt(variance / dataSetSize);
        high = mean + 2 * Math.sqrt(variance / dataSetSize);

        int trials = 0;

        do {
            trials++;
            double result = Math.random();
            mean += result;
            variance += result * result;

            mean /= trials;
            variance /= trials;
            variance -= mean * mean;
            variance = Math.abs(variance);

            low = mean - 2 * Math.sqrt(variance / trials);
            high = mean + 2 * Math.sqrt(variance / trials);
        } while (high - low > acceptableError);

        System.out.println("Mean: " + mean);
        System.out.println("Variance: " + variance);
        System.out.println("Low: " + low);
        System.out.println("High: " + high);
        System.out.println("Trials: " + trials);
    }
}
