package ProblemSets.W8;

public class D1 {
    public static void main(String[] args) {
        System.out.println(shorten("mello")); // mo
        System.out.println(shorten("a")); // ""
        System.out.println(countString("abc hi ho", "hi")); // 1
        System.out.println(countString("ABChi hi", "hi")); // 2
        System.out.println(countString("hihi", "hi")); // 2
        System.out.println(doubleChar("The")); // TThhee
        System.out.println(doubleChar("AAbb")); // AAAAbbbb
        System.out.println(doubleChar("Hi-There")); // HHii--TThheerree
        System.out.println(bobThere("abcbob")); // true
        System.out.println(bobThere("b9b")); // true
        System.out.println(bobThere("bac")); // false
        System.out.println(maxBlock("hoopla")); // 2
        System.out.println(maxBlock("abbCCCddBBBxx")); // 3
        System.out.println(maxBlock("")); // 0

    }

    public static String shorten(String str) {
        if (str.length() < 2) {
            return "";
        }

        return "" + str.charAt(0) + str.charAt(str.length() - 1);
    }

    public static int countString(String str, String sub) {
        int count = 0;
        int index = 0;

        while (index < str.length()) {
            index = str.indexOf(sub, index);

            if (index == -1) {
                break;
            }

            count++;
            index += sub.length();
        }

        return count;
    }

    public static String doubleChar(String str) {
        String result = "";

        for (int i = 0; i < str.length(); i++) {
            result += str.charAt(i);
            result += str.charAt(i);
        }

        return result;
    }

    public static boolean bobThere(String str) {
        int index = str.indexOf("b");

        if (index == -1) {
            return false;
        }

        return str.charAt(index + 2) == 'b' && index + 1 != 'b';
    }

    public static int maxBlock(String str) {
        if (str.length() == 0)
            return 0;

        int max = 1;
        int count = 1;

        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i - 1))
                count++;
            else
                count = 1;

            if (count > max)
                max = count;
        }

        return max;
    }
}
