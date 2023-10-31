package ProblemSets.W9;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class CreateSummaryFiles {
    /**
     * This method takes in a JSONArray of meeting transcript lines and returns a
     * summary statistics file as a string. The summary statistics include the
     * number of people in the meeting, the total length of the meeting, the total
     * speaking time, the number of times the speaker changed, and the total
     * speaking time and number of times spoken for each speaker.
     * 
     * @param arr the JSONArray of meeting transcript lines
     * @return a summary statistics file as a string
     */
    public static String createSummaryStatisticsFile(JSONArray arr) {
        int people = 0;
        double length = parseTimeToSeconds(arr.getJSONObject(arr.length() - 1).getString("end"));
        double speakingTime = 0;
        int totalSwitches = 0;
        String previousSpeaker = "";
        // speaker: [total speaking time, number of times spoken]
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

    /**
     * Returns a string containing statistics about a session, including the total
     * number of people, the total length of the session, the total speaking time,
     * the total number of speaker switches, the total talk time for each speaker,
     * and the average length of a speech event for each speaker.
     * 
     * @param people        the total number of people in the session
     * @param length        the total length of the session in seconds
     * @param speakingTime  the total speaking time in seconds
     * @param totalSwitches the total number of speaker switches
     * @param speakingTimes a HashMap containing the total talk time and number of
     *                      speech events for each speaker
     * @return a string containing the statistics about the session
     */
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

    /**
     * Calculates the elapsed time between two given times in seconds.
     * 
     * @param startTime the starting time in the format "hh:mm:ss"
     * @param endTime   the ending time in the format "hh:mm:ss"
     * @return the elapsed time in seconds
     */
    private static double parseElapsedTime(String startTime, String endTime) {
        double start = parseTimeToSeconds(startTime);
        double end = parseTimeToSeconds(endTime);
        return end - start;
    }

    /**
     * Parses a time string in the format "hh:mm:ss" to seconds.
     * 
     * @param time the time string to be parsed
     * @return the time in seconds
     */
    private static double parseTimeToSeconds(String time) {
        String[] splitTime = time.split(":");
        double hours = Double.parseDouble(splitTime[0]);
        double minutes = Double.parseDouble(splitTime[1]);
        double seconds = Double.parseDouble(splitTime[2]);
        return hours * 3600 + minutes * 60 + seconds;
    }

    /**
     * Creates a condensed transcript file from a JSONArray of speaker and words.
     * The method combines consecutive lines spoken by the same speaker into a
     * single line.
     * 
     * @param arr the JSONArray of speaker and words
     * @return the condensed transcript file as a string
     */
    public static String createCondensedTranscriptFile(JSONArray arr) {
        // [speaker, words]
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

    /**
     * Creates a condensed transcript string output from an ArrayList of String
     * arrays. Each element of the ArrayList represents a line of the transcript,
     * where the first element is the speaker and the second element is the spoken
     * text. The output is formatted as follows: Speaker: Spoken text
     *
     * @param lines the ArrayList of String arrays representing the transcript
     * @return the condensed transcript string output
     */
    private static String createCondensedTranscriptStringOutput(ArrayList<String[]> lines) {
        String output = "";
        for (String[] line : lines) {
            output += line[0] + ":\n" + line[1] + "\n\n";
        }

        return output;
    }
}
