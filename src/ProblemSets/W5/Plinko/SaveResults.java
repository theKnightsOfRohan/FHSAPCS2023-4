package ProblemSets.W5.Plinko;

import org.json.JSONObject;

import Utils.Annotations.HelperMethod;
import Utils.Annotations.RunnableMethod;

import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.io.FileWriter;
import java.io.IOException;

public class SaveResults {
    /**
     * This method creates a JSON object containing the results of a Plinko game
     * simulation. The object includes the number of pegs used in the simulation,
     * the results of the simulation, the mean of the results, the median of the
     * results, and the sample deviation of the results. The JSON object is then
     * written to a file named "results.json" in the specified file path.
     *
     * @param pegNum the number of pegs used in the simulation
     * @param locs   an array of integers representing the locations where the
     *               Plinko chips landed
     * @param money  an array of integers representing the amount of money won for
     *               each Plinko chip
     */
    @RunnableMethod
    public static void create(int pegNum, int[] locs, int[] money) {
        JSONObject obj = new JSONObject();

        obj.put("pegs", pegNum);
        obj.put("results", createList(locs, money));
        obj.put("mean", calcAverage(locs, money));
        obj.put("median", calcMedian(locs, money));
        obj.put("sample deviation", calculateSampleDeviations(locs, money));

        try (FileWriter file = new FileWriter("src/ProblemSets/W5/Plinko/results.json")) {
            file.write(obj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the median of the given arrays of locations and money.
     * 
     * @param locs  the array of locations
     * @param money the array of money
     * @return a JSONObject containing the median location and median money
     */
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

    /**
     * Calculates the sample deviations of the given arrays of Plinko locations and
     * money amounts.
     * 
     * @param locs  An array of Plinko locations.
     * @param money An array of Plinko money amounts.
     * @return A JSONObject containing the sample deviations for the Plinko
     *         locations and money amounts.
     */
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

    /**
     * Creates a JSON array of objects containing the location and money earned for
     * each Plinko chip drop.
     * 
     * @param locs  an array of integers representing the location of each Plinko
     *              chip drop
     * @param money an array of integers representing the money earned for each
     *              Plinko chip drop
     * @return a JSON array of objects containing the location and money earned for
     *         each Plinko chip drop
     */
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

    /**
     * Calculates the average location and money earned for a Plinko game.
     * 
     * @param locs  an array of integers representing the location of each ball drop
     * @param money an array of integers representing the money earned for each ball
     *              drop
     * @return a JSONObject containing the average location and money earned
     */
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