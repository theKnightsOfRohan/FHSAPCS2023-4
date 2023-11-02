package ProblemSets.W9;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomCreateSummaryFiles {
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
    public static String createSummaryStatisticsFile(ArrayList<Message> messages) {
        int people = 0;
        double length = messages.get(messages.size() - 1).timeSeconds[1];
        double speakingTime = 0;
        int totalSwitches = 0;
        String previousSpeaker = "";
        // speaker: [total speaking time, number of times spoken]
        HashMap<String, Double[]> speakingTimes = new HashMap<String, Double[]>();

        for (Message message : messages) {
            String speaker = message.speaker;
            if (!speaker.equals(previousSpeaker)) {
                totalSwitches++;
                previousSpeaker = speaker;
            }
            double elapsedTime = message.timeSeconds[1] - message.timeSeconds[0];
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
        output += "Total length of session: " + length + " min\n";
        output += "Total speaking time: " + speakingTime + " min\n";
        output += "Total # of speaker switches: " + totalSwitches + "\n\n";
        String totalTalkTime = "";
        String averageLengthOfASpeechEvent = "";
        for (String speaker : speakingTimes.keySet()) {
            Double[] times = speakingTimes.get(speaker);
            double total = times[0];
            double average = total / times[1];
            totalTalkTime += speaker + ": " + total + " min - " + (total * 100 / length) + "%\n";
            averageLengthOfASpeechEvent += speaker + ": " + average + " min\n";
        }
        output += "Total talk time:\n" + totalTalkTime + "\n";
        output += "Average length of a speech event:\n" + averageLengthOfASpeechEvent + "\n";
        return output;
    }

    /**
     * Creates a condensed transcript file from a JSONArray of speaker and words.
     * The method combines consecutive lines spoken by the same speaker into a
     * single line.
     * 
     * @param arr the JSONArray of speaker and words
     * @return the condensed transcript file as a string
     */
    public static String createCondensedTranscriptFile(ArrayList<Message> messages) {
        // [speaker, words]
        ArrayList<String[]> lines = new ArrayList<>();
        String previousSpeaker = "";
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            String speaker = message.speaker;
            String words = message.words;
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