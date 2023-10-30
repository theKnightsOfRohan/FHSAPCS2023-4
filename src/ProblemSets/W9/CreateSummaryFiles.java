package ProblemSets.W9;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class CreateSummaryFiles {
    public static String createSummaryStatisticsFile(JSONArray arr) {
        int people = 0;
        double length = parseTimeToSeconds(arr.getJSONObject(arr.length() - 1).getString("end"));
        double speakingTime = 0;
        int totalSwitches = 0;
        String previousSpeaker = "";
        // [total speaking time, number of times spoken]
        HashMap<String, Double[]> speakingTimes = new HashMap<String, Double[]>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject line = arr.getJSONObject(i);
            String speaker = line.getString("speaker");
            if (!speaker.equals(previousSpeaker)) {
                totalSwitches++;
                previousSpeaker = speaker;
            }
            double elapsedTime = parseElapsedTime(line.getString("start"), line.getString("end"));
            speakingTime += elapsedTime;
            if (!speakingTimes.containsKey(speaker)) {
                speakingTimes.put(speaker, new Double[] { 0.0, 0.0 });
                people++;
            }
            Double[] times = speakingTimes.get(speaker);
            times[0] += elapsedTime;
            times[1]++;
            speakingTimes.put(speaker, times);
        }

        return createStatisticsStringOutput(people, length, speakingTime, totalSwitches, speakingTimes);
    }

    private static String createStatisticsStringOutput(int people, double length, double speakingTime, int totalSwitches,
            HashMap<String, Double[]> speakingTimes) {
        String output = "";
        output += "Total # of people: " + people + "\n";
        output += "Total length of session: " + (length / 60) + " min\n";
        output += "Total speaking time: " + (speakingTime / 60) + " min\n";
        output += "Total # of speaker switches: " + totalSwitches + "\n\n";
        String totalTalkTime = "";
        String averageLengthOfASpeechEvent = "";
        for (String speaker : speakingTimes.keySet()) {
            Double[] times = speakingTimes.get(speaker);
            double total = times[0];
            double average = total / times[1];
            totalTalkTime += speaker + ": " + (total / 60) + " min - " + (total * 100 / length) + "%\n";
            averageLengthOfASpeechEvent += speaker + ": " + (average / 60) + " min\n";
        }
        output += "Total talk time:\n" + totalTalkTime + "\n";
        output += "Average length of a speech event:\n" + averageLengthOfASpeechEvent + "\n";
        return output;
    }

    private static double parseElapsedTime(String startTime, String endTime) {
        double start = parseTimeToSeconds(startTime);
        double end = parseTimeToSeconds(endTime);
        return end - start;
    }

    private static double parseTimeToSeconds(String time) {
        String[] splitTime = time.split(":");
        double hours = Double.parseDouble(splitTime[0]);
        double minutes = Double.parseDouble(splitTime[1]);
        double seconds = Double.parseDouble(splitTime[2]);
        return hours * 3600 + minutes * 60 + seconds;
    }

    public static String createCondensedTranscriptFile(JSONArray arr) {
        ArrayList<String[]> lines = new ArrayList<>();
        String previousSpeaker = "";
        for (int i = 0; i < arr.length(); i++) {
            JSONObject line = arr.getJSONObject(i);
            String speaker = line.getString("speaker");
            String words = line.getString("words");
            if (speaker.equals(previousSpeaker)) {
                String[] previousLine = lines.get(lines.size() - 1);
                previousLine[1] += " " + words;
                lines.set(lines.size() - 1, previousLine);
            } else {
                lines.add(new String[] { speaker, words });
                previousSpeaker = speaker;
            }
        }

        return createCondensedTranscriptStringOutput(lines);
    }

    private static String createCondensedTranscriptStringOutput(ArrayList<String[]> lines) {
        String output = "";
        for (String[] line : lines) {
            output += line[0] + ":\n" + line[1] + "\n\n";
        }

        return output;
    }
}
