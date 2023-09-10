package ProblemSets.W3.Extras;

import GeneralHelpers.Annotations.HelperMethod;
import GeneralHelpers.Annotations.RunnableMethod;

public class BasketballShotStreak {
    public static void main(String[] args) {
        makeDataTable(.2, .8);
    }

    /*
     * In many sports, players, coaches and fans believe in a “hot hand” phenomenon
     * in which a player is more likely to hit a shot after one or more hits than
     * one or more misses. In this problem you’ll investigate the probability of
     * seeing a long streak of hits assuming there is no such thing. If it turns out
     * that long streaks of hits aren’t unlikely to happen just by chance, you might
     * conclude that belief in the “hot hand” is based on misperception.
     * Assume a basketball team has 10 players, each of which takes 15 shots during
     * the course of the game. Each shot has a probability of 50% of going in.
     */

    /*
     * (a) Write a program to calculate the probability that within a single game
     * you’ll see a “streak” where one player gets at least 10 shots in a row.
     */

    @RunnableMethod
    public static double calcProbOfStreakInOneGame(int trials, int players, int shotsPerPlayer, int streakLength,
            double probPerPlayer) {
        int numOfStreaks = 0;

        for (int i = 1; i <= trials; i++) {
            if (streakInOneGame(shotsPerPlayer, players, streakLength, probPerPlayer))
                numOfStreaks++;
        }

        return (double) numOfStreaks / trials;
    }

    @HelperMethod
    private static boolean streakInOneGame(int shotsPerPlayer, int totalPlayers, int streakLength,
            double probPerPlayer) {
        for (int i = 0; i < totalPlayers; i++) {
            if (streak(shotsPerPlayer, streakLength, probPerPlayer))
                return true;
        }

        return false;
    }

    @HelperMethod
    private static boolean streak(int totalShots, int streakLength, double probPerShot) {
        int numOfShots = 0;

        for (int i = 0; i < totalShots; i++) {
            if (shot(probPerShot))
                numOfShots++;
            else
                numOfShots = 0;

            if (numOfShots >= streakLength)
                return true;
        }

        return false;
    }

    @HelperMethod
    private static boolean shot(double probOfShot) {
        return Math.random() < probOfShot;
    }

    /*
     * (c) Make a data table for 10 players taking 15 shots each per game, but let
     * the probability of the players making their shots vary from 20% to 80% in
     * intervals of 1%. (Remember, each row of the table should be the result of
     * many (10,000?) trials).
     */

    @RunnableMethod
    public static void makeDataTable(double lowerProb, double upperProb) {
        for (double i = lowerProb; i <= upperProb; i += .01) {
            System.out.println(i + ": " + calcProbOfStreakInOneGame(10000, 10, 15, 10, i));
        }
    }
}
