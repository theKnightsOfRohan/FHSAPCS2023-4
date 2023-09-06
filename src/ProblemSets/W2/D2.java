package ProblemSets.W2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import GeneralHelpers.Annotations.HelperMethod;
import GeneralHelpers.Annotations.RunnableMethod;

public class D2 {
    public static void main(String[] args) {
        findAllConfigsOptimally();
    }

    /*
     * Given a pair of numbers, an addition cycle is the process of adding the two
     * numbers together, and then adding the ones digit of the result to the end of
     * the list,
     * and then repeating the process with the last two digits, and so on, until the
     * first two digits are reached again.
     * This is treated as one cycle, whose length is the number of times the process
     * is repeated.
     */

    public static int longestAdditionCycle() {
        int longestCycle = 0;
        int[] firstTwo = new int[2];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int length = lengthOfAdditionCycle(new int[] { i, j });
                System.out.println("[" + i + ", " + j + "]: " + length);
                if (length > longestCycle) {
                    longestCycle = length;
                    firstTwo[0] = i;
                    firstTwo[1] = j;
                }
            }
        }

        System.out.println("Longest is: [" + firstTwo[0] + ", " + firstTwo[1] + "]: " + longestCycle);
        return longestCycle;
    }

    public static int shortestAdditionCycle() {
        int shortestCycle = 100;
        int[] firstTwo = new int[2];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int length = lengthOfAdditionCycle(new int[] { i, j });
                System.out.println("[" + i + ", " + j + "]: " + length);
                if (length < shortestCycle) {
                    shortestCycle = length;
                    firstTwo[0] = i;
                    firstTwo[1] = j;
                }
            }
        }

        System.out.println("Shortest is: [" + firstTwo[0] + ", " + firstTwo[1] + "]: " + shortestCycle);
        return shortestCycle;
    }

    public static int numOfDifferentLengths() {
        // Key = length, Value = number of pairs with that length
        HashMap<Integer, Integer> lengths = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int length = lengthOfAdditionCycle(new int[] { i, j });
                if (!lengths.containsKey(length))
                    lengths.put(length, 1);
                else
                    lengths.put(length, lengths.get(length) + 1);
            }
        }

        System.out.println(lengths);
        return lengths.size();
    }

    public static int lengthOfAdditionCycle(int[] firstTwo) {
        int[] lastTwo = Arrays.copyOf(firstTwo, 2);
        int counter = 0;
        int placeholder;
        do {
            placeholder = lastTwo[1];
            lastTwo[1] = (lastTwo[0] + lastTwo[1]) % 10;
            lastTwo[0] = placeholder;
            counter++;
        } while (!Arrays.equals(firstTwo, lastTwo));

        return counter;
    }

    /*
     * Given a chessboard, there is a configuration of 8 queens in which each queen
     * does not threaten any other queen.
     * Using a brute force method, find that configuration.
     * Return the configuration as an array of 8 integers,
     * where each value represents the column (0-7)
     * and each index represents the row (0-7).
     */

    @RunnableMethod(description = "Find all configurations of 8 queens in which each queen does not threaten any other queen.")
    public static void findAllConfigsOptimally() {
        ArrayList<ArrayList<Integer>> configs = new ArrayList<>();
        ArrayList<Integer> config = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            config.add(0);

        addToConfig(0, config, configs);
        for (ArrayList<Integer> configuration : configs) {
            printAsChessboard(configuration);
            System.out.println(configuration);
            System.out.println();
        }
    }

    // Public methods are runnable, private methods are helpers
    @RunnableMethod(description = "Find all configuration of 8 queens in which each queen does not threaten any other queen.")
    public static void findAllConfigs() {
        int[] config = new int[8];
        generateConfigs(config, 0);
    }

    @RunnableMethod
    public static void generateConfigs(int[] config, int depth) {
        if (depth == 8) {
            ArrayList<Integer> configList = new ArrayList<>();
            for (int i : config) {
                configList.add(i);
            }
            if (checkConfigValidity(configList)) {
                printAsChessboard(configList);
                System.out.println(configList);
                System.out.println();
            }
        } else {
            for (int i = 0; i < 8; i++) {
                config[depth] = i;
                generateConfigs(config, depth + 1);
            }
        }
    }

    @RunnableMethod
    public static void findManyConfigs() {
        HashSet<ArrayList<Integer>> configs = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            configs.add(randomQueenThreatenConfig());
        }
    }

    @RunnableMethod
    public static ArrayList<Integer> randomQueenThreatenConfig() {
        ArrayList<Integer> config = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            config.add(i);
        }

        boolean isValid;
        do {
            addRandomPositions(config);
            isValid = checkConfigValidity(config);
        } while (!isValid);

        printAsChessboard(config);
        System.out.println(config);
        System.out.println();
        return config;
    }

    @HelperMethod
    private static void addToConfig(int depth, ArrayList<Integer> config, ArrayList<ArrayList<Integer>> configs) {
        if (depth == 8) {
            configs.add(config);
            printAsChessboard(config);
        } else {
            for (int i = 0; i < 8; i++) {
                config.add(depth, i);
                for (int j = 0; j < depth; j++) {
                    if (queenThreatensSquare(depth, config.get(depth), j, config.get(j)))
                        break;
                    else
                        addToConfig(depth + 1, config, configs);
                }
            }
        }
    }

    @HelperMethod
    private static boolean checkConfigValidity(ArrayList<Integer> config) {
        boolean isValid = true;
        for (int i = 0; i < 7; i++) {
            for (int j = i + 1; j < 8; j++) {
                if (queenThreatensSquare(i, config.get(i), j, config.get(j))) {
                    isValid = false;
                    break;
                }
            }
            if (!isValid)
                break;
        }
        return isValid;
    }

    @HelperMethod
    private static void printAsChessboard(ArrayList<Integer> config) {
        for (int i = 0; i < 8; i++) {
            System.out.print("|");
            for (int j = 0; j < 8; j++) {
                if (config.get(i) == j)
                    System.out.print("*|");
                else
                    System.out.print("_|");
            }
            System.out.println();
        }
    }

    @HelperMethod
    private static boolean queenThreatensSquare(int row, int col, int queenRow, int queenCol) {
        if (row == queenRow || col == queenCol || Math.abs(row - queenRow) == Math.abs(col - queenCol))
            return true;
        return false;
    }

    @HelperMethod
    private static void addRandomPositions(ArrayList<Integer> arr) {
        arr.clear();
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            nums.add(i);
        }

        for (int i = 0; i < 8; i++) {
            int randIndex = (int) (Math.random() * nums.size());
            arr.add(nums.get(randIndex));
            nums.remove(randIndex);
        }
    }
}
