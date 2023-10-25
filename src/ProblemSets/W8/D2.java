package ProblemSets.W8;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class D2 {
    public static void main(String[] args) {
        String[] words = readFileByLine("src/ProblemSets/W8/TextDataFiles/words.txt");

        System.out.println(getRuleFollowingWords(words).length);
        System.out.println(getNotRuleFollowingWords(words).length);
    }

    public static String[] getNotRuleFollowingWords(String[] words) {
        ArrayList<String> notRuleFollowingWords = getWordsWithout(words, "ie");
        notRuleFollowingWords = getWordsWithout(notRuleFollowingWords, "cie");
        notRuleFollowingWords = getWordsWithout(notRuleFollowingWords, "ei");
        notRuleFollowingWords = getWordsWithout(notRuleFollowingWords, "cei");

        return notRuleFollowingWords.toArray(new String[notRuleFollowingWords.size()]);
    }

    private static String[] getRuleFollowingWords(String[] words) {
        ArrayList<String> ruleFollowingWords = new ArrayList<String>();
        for (String word : words) {
            if (isRuleFollowing(word))
                ruleFollowingWords.add(word);
        }

        return ruleFollowingWords.toArray(new String[ruleFollowingWords.size()]);
    }

    // i before e except after c
    private static boolean isRuleFollowing(String word) {
        if (word.contains("cie"))
            return false;
        if (word.contains("ei") && !word.contains("cei"))
            return false;
        return word.contains("ie") || word.contains("cei");
    }

    private static String[] getAllPermutations(String[] words, String string) {
        ArrayList<String> permutations = new ArrayList<String>();
        for (String word : words) {
            if (isPermutation(word, string))
                permutations.add(word);
        }

        return permutations.toArray(new String[permutations.size()]);
    }

    private static boolean isPermutation(String word, String string) {
        if (word.length() != string.length())
            return false;

        HashSet<Character> wordChars = new HashSet<Character>();
        for (int i = 0; i < word.length(); i++) {
            wordChars.add(word.charAt(i));
        }

        for (int i = 0; i < string.length(); i++) {
            if (!wordChars.contains(string.charAt(i)))
                return false;
            wordChars.remove(string.charAt(i));
        }

        return true;
    }

    private static String[] removeEmpty(String[] words) {
        ArrayList<String> nonEmptyWords = new ArrayList<String>();
        for (String word : words) {
            if (!word.equals(""))
                nonEmptyWords.add(word);
        }

        return nonEmptyWords.toArray(new String[nonEmptyWords.size()]);
    }

    private static String getLongestWord(String[] words) {
        String longestWord = "";
        for (String word : words) {
            if (word.length() > longestWord.length())
                longestWord = word;
        }

        return longestWord;
    }

    public static String[] readFileByLine(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            ArrayList<String> lines = new ArrayList<String>();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }

            return lines.toArray(new String[lines.size()]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String readFile(String filePath) {
        try (FileInputStream stream = new FileInputStream(filePath)) {
            byte[] data = new byte[stream.available()];
            stream.read(data);
            String str = new String(data);
            System.out.println(str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getNLetterWords(String[] words, int n) {
        ArrayList<String> nLetterWords = new ArrayList<String>();
        for (String word : words) {
            if (word.length() == n)
                nLetterWords.add(word);
        }

        return nLetterWords;
    }

    public static ArrayList<String> getWordsWithout(String[] words, String forbidden) {
        ArrayList<String> wordsWithout = new ArrayList<String>();
        for (String word : words) {
            if (!word.contains(forbidden))
                wordsWithout.add(word);
        }

        return wordsWithout;
    }

    public static ArrayList<String> getWordsWithout(ArrayList<String> words, String forbidden) {
        ArrayList<String> wordsWithout = new ArrayList<String>();
        for (String word : words) {
            if (!word.contains(forbidden))
                wordsWithout.add(word);
        }

        return wordsWithout;
    }

    public static ArrayList<String> getProsAndCons(String[] words) {
        String[] pros = getWordsWithPrefix(words, "pro");
        String[] cons = getWordsWithPrefix(words, "con");

        ArrayList<String> prosAndCons = new ArrayList<String>();

        for (String pro : pros) {
            String suffix = pro.substring(3);
            for (String con : cons) {
                if (con.endsWith(suffix) && con.length() == pro.length())
                    prosAndCons.add(pro + " " + con);
            }
        }

        return prosAndCons;
    }

    private static String[] getWordsWithPrefix(String[] words, String string) {
        ArrayList<String> wordsWith = new ArrayList<String>();
        for (String word : words) {
            if (word.startsWith(string))
                wordsWith.add(word);
        }

        return wordsWith.toArray(new String[wordsWith.size()]);
    }
}
