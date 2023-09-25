package ProblemSets.W5.Plinko;

import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.io.FileWriter;
import java.io.IOException;
import GeneralHelpers.Annotations.HelperMethod;
import GeneralHelpers.Annotations.RunnableMethod;

public class SaveResults {
    @RunnableMethod
    public static void create(int pegNum, int[] locs, int[] money) {
        JSONObject obj = new JSONObject();

        obj.put("pegs", pegNum);
        obj.put("results", createList(locs, money));
        obj.put("mean", calcAverage(locs, money));
        obj.put("median", calcMedian(locs, money));
        obj.put("sample deviations", calculateSampleDeviations(locs, money));

        try (FileWriter file = new FileWriter("src/ProblemSets/W5/Plinko/results.json")) {
            file.write(obj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @HelperMethod
    private static JSONObject calcMedian(int[] locs, int[] money) {
        int[] locsCopy = locs.clone();
        int[] moneyCopy = money.clone();

        Arrays.sort(locsCopy);
        Arrays.sort(moneyCopy);

        JSONObject obj = new JSONObject();

        if (locsCopy.length % 2 == 0) {
            obj.put("loc", (locsCopy[locsCopy.length / 2] + locsCopy[locsCopy.length / 2 - 1]) / 2);
            obj.put("money", (moneyCopy[moneyCopy.length / 2] + moneyCopy[moneyCopy.length / 2 - 1]) / 2);
        } else {
            obj.put("loc", locsCopy[locsCopy.length / 2]);
            obj.put("money", moneyCopy[moneyCopy.length / 2]);
        }

        return obj;
    }

    @HelperMethod
    private static JSONObject calculateSampleDeviations(int[] locs, int[] money) {
        double[] sums = new double[2]; // locs, money
        double[] squares = new double[2]; // locs, money

        for (int i = 0; i < locs.length; i++) {
            sums[0] += locs[i];
            sums[1] += money[i];

            squares[0] += Math.pow(locs[i], 2);
            squares[1] += Math.pow(money[i], 2);
        }

        double[] averages = new double[] { sums[0] / locs.length, sums[1] / money.length };
        double[] averageSquares = new double[] { squares[0] / locs.length, squares[1] / money.length };

        JSONObject obj = new JSONObject();
        obj.put("loc", Math.sqrt(averageSquares[0] - Math.pow(averages[0], 2)));
        obj.put("money", Math.sqrt(averageSquares[1] - Math.pow(averages[1], 2)));

        return obj;
    }

    @HelperMethod
    private static JSONArray createList(int[] locs, int[] money) {
        JSONArray list = new JSONArray();

        for (int i = 0; i < locs.length; i++) {
            JSONObject obj = new JSONObject();
            obj.put("loc", locs[i]);
            obj.put("money", money[i]);
            list.put(obj);
        }

        return list;
    }

    @HelperMethod
    private static JSONObject calcAverage(int[] locs, int[] money) {
        double[] sums = new double[2];

        for (int i = 0; i < locs.length; i++) {
            sums[0] += locs[i];
            sums[1] += money[i];
        }

        JSONObject obj = new JSONObject();
        obj.put("loc", sums[0] / locs.length);
        obj.put("money", sums[1] / money.length);

        return obj;
    }
}