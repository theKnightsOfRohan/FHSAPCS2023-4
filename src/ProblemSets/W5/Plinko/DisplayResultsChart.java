package ProblemSets.W5.Plinko;

import processing.core.PApplet;
import org.json.JSONObject;

import Utils.Annotations.HelperMethod;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DisplayResultsChart extends PApplet {
    public void settings() {
        size(1000, 1000);
    }

    public void setup() {
        background(255);
        // Displays data in results.json as a histogram
        JSONObject obj;
        try {
            obj = readOutputFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JSONArray results = obj.getJSONArray("results");
        int[] locs = new int[results.length()];
        int[] money = new int[results.length()];
        int maxLoc = 0;
        int maxMoney = 0;

        for (int i = 0; i < results.length(); i++) {
            locs[i] = results.getJSONObject(i).getInt("loc");
            money[i] = results.getJSONObject(i).getInt("money");
            if (locs[i] > maxLoc)
                maxLoc = locs[i];
            if (money[i] > maxMoney)
                maxMoney = money[i];
        }

        int[] locBins = new int[maxLoc + 1];
        int[] moneyBins = new int[maxMoney + 1];
        int maxAmtLoc = 0;
        int maxAmtMoney = 0;

        for (int i = 0; i < locs.length; i++) {
            locBins[locs[i]]++;
            moneyBins[money[i]]++;

            if (locs[i] > maxAmtLoc)
                maxAmtLoc = locs[i];
            if (money[i] > maxAmtMoney)
                maxAmtMoney = money[i];
        }

        int locBinWidth = width / locBins.length;
        int moneyBinWidth = width / moneyBins.length;
        int scaleLoc = (height / 2 - 50) / maxAmtLoc;
        int scaleMoney = (height / 2 - 50) / maxAmtMoney;

        for (int i = 0; i < locBins.length; i++) {
            fill(0, 0, 255);
            rect(i * locBinWidth, height / 2 - 50 - locBins[i] * scaleLoc, locBinWidth, locBins[i] * scaleLoc);
        }

        for (int i = 0; i < moneyBins.length; i++) {
            fill(255, 0, 0);
            rect(i * moneyBinWidth, height - moneyBins[i] * scaleMoney, moneyBinWidth, moneyBins[i] * scaleMoney);
        }

        noLoop();
    }

    /**
     * Reads the contents of the "results.json" file located in the specified file
     * path and returns a JSONObject representation of the file contents.
     *
     * @return a JSONObject representation of the contents of the "results.json"
     *         file
     * @throws JSONException if there is an error parsing the contents of the file
     *                       as a JSONObject
     * @throws IOException   if there is an error reading the contents of the file
     */
    @HelperMethod
    private JSONObject readOutputFile() throws JSONException, IOException {
        return new JSONObject(new String(Files.readAllBytes(Paths.get("src/ProblemSets/W5/Plinko/results.json"))));
    }

    public static void main(String[] args) {
        PApplet.main("ProblemSets.W5.Plinko.DisplayResultsChart");
    }
}
