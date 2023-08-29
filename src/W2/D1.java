package W2;

import java.util.ArrayList;
import java.util.Arrays;

public class D1 {
    public static void main(String[] args) {
        int[] arr1 = { 1, 2, 3 };
        int[] arr2 = { 1, 2, 3 };
        int[] arr3 = { 1, 2, 3, 4 };
        int[] arr4 = { 4, 2, 1, 3 };

        int[] arr5 = { 3, 4, 5, 2, 1, 6, 7, 8, 9 };
        int[] arr6 = { 3, 5, 5, 2, 1, 6, 7, 8, 9 };

        String[] words1 = { "lady", "bug", "cup" };
        String[] words2 = { "lady", "bug", "cup", "cake", "butter", "fly" };

        // Make the coordinates of the points somewhat irregular
        Point[] points1 = { new Point(6, 1), new Point(7, 2), new Point(3, 2), new Point(4, 9), new Point(3, 5),
                new Point(6, 1), new Point(4, 7), new Point(8, 6), new Point(9, 2) };

        Point[] points2 = { new Point(6, 1), new Point(7, 2), new Point(3, 2), new Point(4, 9), new Point(3, 5),
                new Point(6, 1), new Point(4, 7), new Point(8, 6), new Point(9, 2), new Point(1, 1), new Point(2, 2),
                new Point(3, 3), new Point(4, 4), new Point(5, 5), new Point(6, 6), new Point(7, 7), new Point(8, 8),
                new Point(9, 9) };

        /*
         * System.out.println(sameValues(arr1, arr2));
         * System.out.println(sameValues(arr1, arr3));
         * System.out.println(sameValues(arr1, arr4));
         * 
         * System.out.println(isValidRow(arr1));
         * System.out.println(isValidRow(arr5));
         * System.out.println(isValidRow(arr6));
         * 
         * System.out.println(countCompounds(words1));
         * System.out.println(countCompounds(words2));
         */
        System.out.println(Arrays.toString(largestTriangle(points1)));
        System.out.println(Arrays.toString(largestTriangle(points2)));

    }

    public static boolean sameValues(int[] arr1, int[] arr2) {
        boolean isSame = false;
        for (int val1 : arr1) {
            isSame = false;
            for (int val2 : arr2) {
                if (val1 == val2) {
                    isSame = true;
                    break;
                }
            }

            if (!isSame)
                return false;
        }

        for (int val2 : arr2) {
            isSame = false;
            for (int val1 : arr1) {
                if (val2 == val1) {
                    isSame = true;
                    break;
                }
            }

            if (!isSame)
                return false;
        }

        return true;
    }

    public static boolean isValidRow(int[] row) {
        if (row.length != 9)
            return false;

        ArrayList<Integer> rowValues = new ArrayList<>();
        for (int i = 1; i < 10; i++)
            rowValues.add(i);

        for (int val : row) {
            if (rowValues.contains(val)) {
                rowValues.remove(rowValues.indexOf(val));
            } else {
                return false;
            }
        }

        return true;
    }

    public static int countCompounds(String[] words) {
        int counter = 0;
        String[] compoundList = { "ladybug", "cupcake", "butterfly", "rainbow", "starlight", "buttercup" };
        for (int i = 0; i < words.length - 1; i++) {
            for (int j = i + 1; j < words.length; j++) {
                for (String compound : compoundList) {
                    if ((words[i] + words[j]).equals(compound))
                        counter++;
                    if ((words[j] + words[i]).equals(compound))
                        counter++;

                }
            }
        }

        return counter;
    }

    public static Point[] largestTriangle(Point[] points) {
        Point[] largestTriangle = new Point[3];
        double maxArea = 0;
        for (int i = 0; i < points.length - 2; i++) {
            for (int j = i + 1; j < points.length - 1; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    double area = area(points[i], points[j], points[k]);
                    if (area > maxArea) {
                        maxArea = area;
                        largestTriangle[0] = points[i];
                        largestTriangle[1] = points[j];
                        largestTriangle[2] = points[k];
                    }
                }
            }
        }

        return largestTriangle;
    }

    /* Area=1/2∣(𝑥𝐴−𝑥𝐶)(𝑦𝐵−𝑦𝐴)−(𝑥𝐴−𝑥𝐵)(𝑦𝐶−𝑦𝐴)∣ */
    public static double area(Point p1, Point p2, Point p3) {
        return Math.abs(
                (p1.getX() - p3.getX()) * (p2.getY() - p1.getY()) - (p1.getX() - p2.getX()) * (p3.getY() - p1.getY()))
                / 2;
    }
}

class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double distanceTo(Point other) {
        return Math.sqrt(Math.pow(this.x - other.getX(), 2) + Math.pow(this.y - other.getY(), 2));
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
