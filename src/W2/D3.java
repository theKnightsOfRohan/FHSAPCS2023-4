package W2;

import java.util.Arrays;

public class D3 {
    public static void main(String[] args) {
        String[] names = { "A", "B", "C" };
        String[] colors = { "red", "blue", "green" };
        String[] descriptions = { "guy", "gal", "grill" };

        System.out.println(Arrays.toString(disproveEulerSumOfPowersConjecture()));
    }

    public static void allPossibleNameCombos(String[] name, String[] color, String[] desc) {
        for (int i = 0; i < name.length; i++) {
            for (int j = 0; j < color.length; j++) {
                for (int k = 0; k < desc.length; k++) {
                    System.out.println(name[i] + " the " + color[j] + " " + desc[k]);
                }
            }
        }
    }

    public static boolean oneStringMatches(int[] nums, String[] words) {
        for (int num : nums) {
            for (String word : words) {
                if (word.length() == num)
                    return true;
            }
        }

        return false;
    }

    public static boolean allLengthsMatch(int[] nums, String[] words) {
        for (int num : nums) {
            boolean found = false;
            for (String word : words) {
                if (word.length() == num) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return false;
        }

        return true;
    }

    public static boolean allWordsMatch(String[] words, int[] nums) {
        for (String word : words) {
            boolean found = false;
            for (int num : nums) {
                if (word.length() == num) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return false;
        }

        return true;
    }

    public static boolean bothArrsMatch(String[] words, int[] nums) {
        return allLengthsMatch(nums, words) && allWordsMatch(words, nums);
    }

    public static int[] closestValueIndexes(int[] arr) {
        int maxDiff = Integer.MAX_VALUE;
        int[] indexes = { 0, 0 };
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int newDiff = Math.abs(arr[i] - arr[j]);
                if (maxDiff > newDiff) {
                    maxDiff = newDiff;
                    indexes = new int[] { i, j };
                }
            }
        }

        return indexes;
    }

    public static int[] smallestDiffAdjacents(int[] arr) {
        int minDiff = Integer.MAX_VALUE;
        int[] indexes = { 0, 0 };
        for (int i = 0; i < arr.length - 1; i++) {
            int newDiff = Math.abs(arr[i] - arr[i + 1]);
            if (minDiff > newDiff) {
                minDiff = newDiff;
                indexes = new int[] { i, i + 1 };
            }
        }

        return indexes;
    }

    public static void printAllCombos(int[] fontSizes, String[] fontStyles, String[] fontColors) {
        for (int fontSize : fontSizes) {
            for (String fontStyle : fontStyles) {
                for (String fontColor : fontColors) {
                    System.out.println(fontSize + " pt " + fontStyle + " " + fontColor);
                }
            }
        }
    }

    public static void printAll5CharPasswords() {
        String[] letters = "abcdefghijklmnopqrstuvwxyz".split("");
        for (String letter1 : letters) {
            for (String letter2 : letters) {
                for (String letter3 : letters) {
                    for (String letter4 : letters) {
                        for (String letter5 : letters) {
                            System.out.println(letter1 + letter2 + letter3 + letter4 + letter5);
                        }
                    }
                }
            }
        }
    }

    public static int latticePointsInCircle(int radius) {
        int count = 0;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                if (x * x + y * y <= radius * radius)
                    count++;
                System.out.println("[" + x + ", " + y + "]");
            }
        }

        return count;
    }

    public static boolean isAbundant(int num) {
        int sum = 0;
        for (int i = 1; i < (num / 2) + 1; i++) {
            if (num % i == 0) {
                if (sum + i > num) {
                    // System.out.println((sum + i) + " > " + num);
                    return true;
                } else {
                    sum += i;
                }
            }
        }

        // System.out.println(sum + " < " + num);
        return false;
    }

    public static long[] disproveEulerSumOfPowersConjecture() {
        long cycle = 0;
        for (long i = 1; i < Long.MAX_VALUE; i++) {
            for (long j = i + 1; j < Long.MAX_VALUE; j++) {
                for (long k = j + 1; k < Long.MAX_VALUE; k++) {
                    for (long l = k + 1; l < Long.MAX_VALUE; l++) {
                        if (Math.pow(Math.pow(i, 5) + Math.pow(j, 5) + Math.pow(k, 5) + Math.pow(l, 5), 1.0 / 5.0)
                                % 1 == 0) {
                            System.out.println(i + " " + j + " " + k + " " + l);
                            return new long[] { i, j, k, l };
                        } else {
                            cycle++;
                            System.out.println("notFound:" + cycle);
                        }
                    }
                }
            }
        }

        return new long[] { 0, 0, 0, 0 };
    }
}
