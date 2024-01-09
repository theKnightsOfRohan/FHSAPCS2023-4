package ProblemSets.W13;

import java.util.Arrays;

public class D1 {
    public static void main(String[] args) {
        System.out.println(getDiagonalSumTest());
    }

    public static boolean createArrTest() {
        int[][] arr = createArr(3, 3, 0);
        int[][] expected = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };

        String[][] arr2 = createArr(3, 3, "a");
        String[][] expected2 = { { "a", "a", "a" }, { "a", "a", "a" }, { "a", "a", "a" } };

        return Arrays.deepEquals(arr, expected) && Arrays.deepEquals(arr2, expected2);
    }

    public static boolean createBorderArrTest() {
        int[][] arr = createBorderArr(3, 3, 3);
        int[][] expected = { { 3, 3, 3 }, { 3, 0, 3 }, { 3, 3, 3 } };

        String[][] arr2 = createBorderArr(3, 3, "a");
        String[][] expected2 = { { "a", "a", "a" }, { "a", null, "a" }, { "a", "a", "a" } };

        return Arrays.deepEquals(arr, expected) && Arrays.deepEquals(arr2, expected2);
    }

    public static boolean minValueInArrayTest() {
        int[][] arr = { { 1, 2, 3, 4 }, { 4, 5, 6, 8 }, { 7, 8, 9, 10 } };

        return minValueInArray(arr) == 1 && getMinValColumn(arr) == 0 && getMinInteriorValue(arr) == 5;
    }

    public static boolean getDiagonalSumTest() {
        int[][] arr = { { 1, 2, 3, 4 }, { 4, 5, 6, 8 }, { 7, 8, 9, 10 }, { 1, 2, 3, 4 } };

        int[][] arr2 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, };

        int[][] arr3 = { { 1 }, { 2 }, { 3 } };

        return getDiagonalSum(arr) == 38 && getDiagonalSum(arr2) == 25 && getDiagonalSum(arr3) == 0;
    }

    public static int[][] createArr(int rows, int cols, int initialValue) {
        int[][] arr = new int[rows][cols];

        for (int[] row : arr) {
            Arrays.fill(row, initialValue);
        }

        return arr;
    }

    public static String[][] createArr(int rows, int cols, String initialValue) {
        String[][] arr = new String[rows][cols];

        for (String[] row : arr) {
            Arrays.fill(row, initialValue);
        }

        return arr;
    }

    public static int[][] createBorderArr(int rows, int cols, int borderValue) {
        int[][] arr = new int[rows][cols];

        Arrays.fill(arr[0], borderValue);
        Arrays.fill(arr[arr.length - 1], borderValue);

        for (int i = 1; i < arr[i].length - 1; i++) {
            arr[i][0] = borderValue;
            arr[i][arr[i].length - 1] = borderValue;
        }

        return arr;
    }

    private static String[][] createBorderArr(int rows, int cols, String string) {
        String[][] arr = new String[rows][cols];

        Arrays.fill(arr[0], string);
        Arrays.fill(arr[arr.length - 1], string);

        for (int i = 1; i < arr[i].length - 1; i++) {
            arr[i][0] = string;
            arr[i][arr[i].length - 1] = string;
        }

        return arr;
    }

    public static int minValueInArray(int[][] arr) {
        int min = Integer.MAX_VALUE;

        for (int[] row : arr) {
            for (int val : row) {
                if (val < min) {
                    min = val;
                }
            }
        }

        return min;
    }

    public static int getMinInteriorValue(int[][] arr) {
        int min = Integer.MAX_VALUE;

        for (int i = 1; i < arr.length - 1; i++) {
            for (int j = 1; j < arr[i].length - 1; j++) {
                if (arr[i][j] < min) {
                    min = arr[i][j];
                }
            }
        }

        return min;
    }

    public static int getMinValColumn(int[][] arr) {
        int min = Integer.MAX_VALUE;
        int minCol = 0;

        for (int[] row : arr) {
            for (int i = 0; i < arr.length; i++) {
                if (row[i] < min) {
                    min = row[i];
                    minCol = i;
                }
            }
        }

        return minCol;
    }

    public static int getDiagonalSum(int[][] arr) {
        if (arr.length != arr[0].length) {
            return 0;
        }

        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            sum += arr[i][i];
            if (i != arr.length - 1 - i) {
                sum += arr[i][arr.length - 1 - i];
            }
        }

        return sum;
    }

    public static void printArr(int[][] grid) {
        for (int[] row : grid) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}
