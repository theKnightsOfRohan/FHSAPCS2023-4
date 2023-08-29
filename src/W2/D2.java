package W2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class D2 {
    public static void main(String[] args) {
        System.out.println(queenThreatenConfig());
    }

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
            for (int j = i; j < 10; j++) {
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
            for (int j = i; j < 10; j++) {
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
        int counter = 2;
        int placeholder;
        do {
            placeholder = lastTwo[1];
            lastTwo[1] = (lastTwo[0] + lastTwo[1]) % 10;
            lastTwo[0] = placeholder;
            counter++;
        } while (!Arrays.equals(firstTwo, lastTwo));

        return counter - 2;
    }

    /*
     * Given a chessboard, there is a configuration of 8 queens in which each queen
     * does not threaten any other queen.
     * Using a brute force method, find that configuration.
     * Return the configuration as an array of 8 integers,
     * where each value represents the column (0-7)
     * and each index represents the row (0-7).
     */

    public static ArrayList<Integer> queenThreatenConfig() {
        ArrayList<Integer> config = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            config.add(i);
        }

        boolean isValid = false;
        do {
            addRandomPositions(config);
            for (int i = 0; i < 7; i++) {
                isValid = true;
                for (int j = i + 1; j < 8; j++) {
                    if (queenDoesThreatenSquare(i, config.get(i), j, config.get(j))) {
                        isValid = false;
                        break;
                    }
                }
                if (!isValid)
                    break;
            }
        } while (!isValid);

        return config;
    }

    public static boolean queenDoesThreatenSquare(int row, int col, int queenRow, int queenCol) {
        if (row == queenRow || col == queenCol)
            return true;
        if (Math.abs(row - queenRow) == Math.abs(col - queenCol))
            return true;
        return false;
    }

    public static void addRandomPositions(ArrayList<Integer> arr) {
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
