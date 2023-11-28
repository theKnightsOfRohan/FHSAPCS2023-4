package Utils;

import java.util.Arrays;

public class ArrayHelpers {
    public static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length - 1; i++)
            System.out.print(arr[i] + ", ");
        System.out.println(arr[arr.length - 1] + "]");
    }

    public static int[] addValue(int[] arr, int value) {
        int[] newArr = Arrays.copyOf(arr, arr.length + 1);
        newArr[newArr.length - 1] = value;
        return newArr;
    }

    public static int[] removeFirst(int[] arr, int value) {
        int[] newArr = new int[arr.length - 1];
        int index = 0;
        boolean found = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value && !found) {
                found = true;
                continue;
            }
            newArr[index] = arr[i];
            index++;
        }
        return newArr;
    }

    public static int[] removeAll(int[] arr, int value) {
        int[] newArr = new int[arr.length];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value)
                continue;
            newArr[index] = arr[i];
            index++;
        }
        return Arrays.copyOf(newArr, index);
    }

    public static double getMax(double[] arr) {
        double max = arr[0];
        for (double d : arr)
            if (d > max)
                max = d;
        return max;
    }
}
