package W1;

import java.util.Arrays;

public class D2 {
    public static void main(String[] args) {
        int[] arr1 = { 1, 2, 3, 4, 5 };
        int[] arr2 = { 5, 4, 3, 4, 5, 4, 3, 4, 5 };
        int[] arr3 = { 9, 8, 7, 6, 5, 4, 3, 4, 5, 6, 7, 8, 9, 10 };

        System.out.println(find1StepMinimum(arr1));
        System.out.println(find1StepMinimum(arr2));
        System.out.println(find1StepMinimum(arr3));
    }

    public static int minValue(int[] array) {
        int minValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            }
        }

        return minValue;
    }

    public static int maxValue(int[] array) {
        int maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (maxValue < array[i]) {
                maxValue = array[i];
            }
        }

        return maxValue;
    }

    public static int findMaxBetweenNegatives(int[] array) {
        int lowerIndex = -1;
        int higherIndex = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 0 && lowerIndex < 0) {
                lowerIndex = i;
            } else {
                if (array[i] < 0) {
                    higherIndex = i;
                }
            }
        }

        int maxValue = array[lowerIndex];
        for (int i = lowerIndex + 1; i < higherIndex; i++) {
            if (maxValue < array[i]) {
                maxValue = array[i];
            }
        }

        return maxValue;
    }

    public static int findSecondMax(int[] array) {
        int max = maxValue(array);
        int secondMax = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < max && secondMax < array[i]) {
                secondMax = array[i];
            }
        }

        return secondMax;
    }

    public static double findAverageOfLast10(int[] array) {
        int sum = 0;
        int count = 0;
        for (int i = array.length - 1; i >= 0 && count < 10; i--) {
            sum += array[i];
            count++;
        }

        return (double) sum / count;
    }

    public static double findAverageOfEvenValues(int[] array) {
        int sum = 0;
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 == 0) {
                sum += array[i];
                count++;
            }
        }

        return (double) sum / count;
    }

    public static int findHottestAverage5DaySpan(int[] temps) {
        int greatestSum = 0;
        int indexOfFirstDayInSpan = 0;
        for (int i = 0; i < temps.length - 4; i++) {
            int sum = 0;
            for (int j = i; j < i + 5; j++) {
                sum += temps[j];
            }

            if (greatestSum < sum) {
                greatestSum = sum;
                indexOfFirstDayInSpan = i;
            }
        }

        return indexOfFirstDayInSpan;
    }

    public static int find1StepMinimum(int[] array) {
        // Finds the minimum value of an array in which the difference between any two
        // adjacent elements is 1.
        int min = array[0];
        for (int i = 0; i < array.length - 1; i++) {
            int diff = array[i + 1] - min;
            if (diff == -1) {
                min = array[i + 1];
            } else {
                i += diff;
            }
        }

        return min;
    }
}