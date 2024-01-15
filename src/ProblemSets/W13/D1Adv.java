package ProblemSets.W13;

import java.util.Arrays;

public class D1Adv {
    public static void main(String[] args) {
        int[][] arr = { { 1, 2, 3 }, { 1, 3, 4 }, { 1, 4, 5 } };
        System.out.println(Arrays.deepToString(arr));

        System.out.println(detectHomogeneousBlocks(arr, 3, 1));

        System.out.println(perimeterSum(arr) + " = 21");

        // 0 0 0 0 0 0 0 0
        // 0 6 6 6 6 6 6 0
        // 1 0 0 0 0 2 2 2
        // 1 0 0 0 0 2 2 2
        // 1 0 0 0 0 2 2 2
        // 1 0 0 0 0 0 7 7
        // 0 0 4 4 0 0 7 7
        // 0 0 4 4 0 0 7 7

        int[][] grid = { { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 6, 6, 6, 6, 6, 6, 0 },
                { 1, 0, 0, 0, 0, 2, 2, 2 },
                { 1, 0, 0, 0, 0, 2, 2, 2 },
                { 1, 0, 0, 0, 0, 2, 2, 2 },
                { 1, 0, 0, 0, 0, 0, 7, 7 },
                { 0, 0, 4, 4, 0, 0, 7, 7 },
                { 0, 0, 4, 4, 0, 0, 7, 7 } };

        System.out.println(detectLowerRight(grid, 3, 0).row + "," + detectLowerRight(grid, 3, 0).col);
        System.out.println(detectLowerRight(grid, 6, 6).row + "," + detectLowerRight(grid, 6, 6).col);

        String[][] arr2 = { { "a", "b", "c" }, { "d", "e", "f" }, { "g", "h", "i" } };

        System.out.println(Arrays.deepToString(transpose(arr2)));
    }

    public static boolean detectHomogeneousBlocks(int[][] arr, int rowSize, int colSize) {
        if (arr.length < rowSize && arr[0].length < colSize) {
            return false;
        }

        int[][] window = new int[rowSize][colSize];

        for (int i = 0; i < arr.length - rowSize + 1; i++) {
            for (int j = 0; j < arr[i].length - colSize + 1; j++) {
                for (int k = 0; k < window.length; k++) {
                    for (int l = 0; l < window[k].length; l++) {
                        window[k][l] = arr[i + k][j + l];
                    }
                }

                if (isHomogeneous(window)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isHomogeneous(int[][] arr) {
        System.out.println(Arrays.deepToString(arr));

        int val = arr[0][0];

        for (int[] row : arr) {
            for (int i : row) {
                if (i != val) {
                    return false;
                }
            }
        }

        return true;
    }

    public static int perimeterSum(int[][] arr) {
        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            sum += arr[i][0];
            sum += arr[i][arr[i].length - 1];
        }

        for (int i = 1; i < arr[0].length - 1; i++) {
            sum += arr[0][i];
            sum += arr[arr.length - 1][i];
        }

        return sum;
    }

    static class Location {
        int row;
        int col;

        public Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static Location detectLowerRight(int[][] arr, int row, int col) {
        if (arr.length < row && arr[0].length < col) {
            return null;
        }

        int val = arr[row][col];

        if (val == 0)
            return null;

        int bottomRow = getBottomRow(arr, row, col, val);
        int rightCol = getRightCol(arr, row, col, val);

        return new Location(bottomRow, rightCol);
    }

    public static int getBottomRow(int[][] arr, int row, int col, int val) {
        for (int i = row; i < arr.length; i++) {
            if (arr[i][col] != val) {
                return i - 1;
            }
        }

        return arr.length - 1;
    }

    public static int getRightCol(int[][] arr, int row, int col, int val) {
        for (int i = col; i < arr[row].length; i++) {
            if (arr[row][i] != val) {
                return i - 1;
            }
        }

        return arr[row].length - 1;
    }

    public static String[][] transpose(String[][] arr) {
        String[][] newArr = new String[arr[0].length][arr.length];

        for (int i = 0; i < newArr.length; i++) {
            for (int j = 0; j < newArr[i].length; j++) {
                newArr[i][j] = arr[j][i];
            }
        }

        return newArr;
    }
}
