package ProblemSets.W5.Plinko;

import GeneralHelpers.Annotations.HelperMethod;
import GeneralHelpers.Annotations.RunnableMethod;

public class PlinkoSimulation {
    // Basic constants
    final static int TRIALS = 100000;
    final static int WIDTH = 11;
    final static double HALF_WIDTH = WIDTH / 2;

    public static void main(String[] args) {
        displayFullTable(1, 20, TRIALS);
    }

    // Create a table of both the average location of the puck and the average money
    @RunnableMethod
    public static void displayFullTable(int start, int end, int trials) {
        System.out.println("Pegs\tLoc\tMoney");
        for (int i = start; i <= end; i++) {
            System.out.println(i + "\t" + getAverageLocsAndMoney(i, trials));
        }
    }

    // Gets the average location of the puck and the average money won for a given
    // simulation
    @HelperMethod
    private static String getAverageLocsAndMoney(int pegs, int trials) {
        double[] sums = new double[2];

        for (int i = 0; i < trials; i++) {
            int[] results = runSimulationLocsAndMoney(pegs);
            sums[0] += results[0];
            sums[1] += results[1];
        }

        return sums[0] / trials + "\t" + sums[1] / trials;
    }

    // Runs a simulation of the puck falling through that number of pegs, then
    // calculates the money they won and the location of the puck
    @HelperMethod
    private static int[] runSimulationLocsAndMoney(int pegs) {
        int loc = 0;
        int money = 0;

        for (int i = 0; i < pegs; i++) {
            if (Math.random() < 0.5)
                loc++;
            else
                loc--;

            if (loc > HALF_WIDTH)
                loc = (int) HALF_WIDTH;
            if (loc < -HALF_WIDTH)
                loc = (int) -HALF_WIDTH;
        }

        switch (Math.abs(loc)) {
            case 0:
                money = 10;
                break;
            case 1:
                money = 0;
                break;
            case 2:
                money = 5;
                break;
            case 3:
                money = 3;
                break;
            case 4:
                money = 2;
                break;
            case 5:
                money = 1;
                break;
            default:
                throw new RuntimeException("Invalid location: " + loc);
        }

        return new int[] { Math.abs(loc), money };
    }

    // Create a table of the average location of the puck for each number of pegs
    @RunnableMethod
    public static void displayTableLocs(int start, int end, int trials) {
        for (int i = start; i <= end; i++) {
            System.out.println(i + "\t" + getAverageValue(i, trials));
        }
    }

    // Gets the average location of the puck for a given number of pegs over 10000
    // trials
    @HelperMethod
    private static double getAverageValue(int pegs, int trials) {
        double sum = 0;
        for (int i = 0; i < trials; i++) {
            sum += runSimulation(pegs);
        }

        return sum / trials;
    }

    // Runs a simulation of the puck falling through that number of pegs
    @HelperMethod
    private static int runSimulation(int pegs) {
        int loc = 0;
        for (int i = 0; i < pegs; i++) {
            if (Math.random() < 0.5)
                if (loc < HALF_WIDTH)
                    loc++;
                else if (loc > -HALF_WIDTH)
                    loc--;
        }

        return Math.abs(loc);
    }

    // Create a table of the average money won for each number of pegs
    @RunnableMethod
    public static void displayTableMoney(int start, int end, int trials) {
        for (int i = start; i <= end; i++) {
            System.out.println(i + "\t" + getAverageMoney(i, trials));
        }
    }

    // Gets the average money won for a given number of pegs over 10000 trials
    @HelperMethod
    private static double getAverageMoney(int i, int trials) {
        double sum = 0;
        for (int j = 0; j < trials; j++) {
            sum += runSimulationMoney(i);
        }

        return sum / trials;
    }

    // Runs a simulation of the puck falling through that number of pegs, then
    // calculates the money they won
    @HelperMethod
    private static double runSimulationMoney(int i) {
        int loc = 0;

        for (int j = 0; j < i; j++) {
            if (Math.random() < 0.5)
                if (loc < HALF_WIDTH)
                    loc++;
                else if (loc > -HALF_WIDTH)
                    loc--;
        }

        switch (Math.abs(loc)) {
            case 0:
                return 10;
            case 1:
                return 0;
            case 2:
                return 5;
            case 3:
                return 3;
            case 4:
                return 2;
            case 5:
                return 1;
            default:
                throw new RuntimeException("Invalid location: " + loc);
        }
    }
}
