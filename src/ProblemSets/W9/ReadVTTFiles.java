package ProblemSets.W9;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReadVTTFiles {
    /**
     * This method reads .vtt files and converts them to JSON format. It then
     * creates summary statistics and condensed transcript files for each input
     * file.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        JSONArray sample01 = vttToJSON("src/ProblemSets/W9/TranscriptFiles/sample01.vtt", "src/ProblemSets/W9/Output/JSON/sample01.json");
        JSONArray DPS1 = vttToJSON("src/ProblemSets/W9/TranscriptFiles/DobervichPlanningSession1.vtt",
                "src/ProblemSets/W9/Output/JSON/DobervichPlanningSession1.json");
        JSONArray DPS2 = vttToJSON("src/ProblemSets/W9/TranscriptFiles/DobervichPlanningSession2.vtt",
                "src/ProblemSets/W9/Output/JSON/DobervichPlanningSession2.json");

        String sample01Stats = CreateSummaryFiles.createSummaryStatisticsFile(sample01);
        String DPS1Stats = CreateSummaryFiles.createSummaryStatisticsFile(DPS1);
        String DPS2Stats = CreateSummaryFiles.createSummaryStatisticsFile(DPS2);

        writeToFile(sample01Stats, "src/ProblemSets/W9/Output/SummaryStatistics/sample01.txt");
        writeToFile(DPS1Stats, "src/ProblemSets/W9/Output/SummaryStatistics/DobervichPlanningSession1.txt");
        writeToFile(DPS2Stats, "src/ProblemSets/W9/Output/SummaryStatistics/DobervichPlanningSession2.txt");

        String sample01Condensed = CreateSummaryFiles.createCondensedTranscriptFile(sample01);
        String DPS1Condensed = CreateSummaryFiles.createCondensedTranscriptFile(DPS1);
        String DPS2Condensed = CreateSummaryFiles.createCondensedTranscriptFile(DPS2);

        writeToFile(sample01Condensed, "src/ProblemSets/W9/Output/CondensedTranscripts/sample01.txt");
        writeToFile(DPS1Condensed, "src/ProblemSets/W9/Output/CondensedTranscripts/DobervichPlanningSession1.txt");
        writeToFile(DPS2Condensed, "src/ProblemSets/W9/Output/CondensedTranscripts/DobervichPlanningSession2.txt");
    }

    /**
     * Converts a VTT file to a JSON array. Reads the file from the input path,
     * parses it to a JSON array, and writes the result to the output path.
     * 
     * @param inputPath  the path of the VTT file to be converted
     * @param outputPath the path of the output file to be written
     * @return the parsed JSON array
     */
    private static JSONArray vttToJSON(String inputPath, String outputPath) {
        ArrayList<String> fileLines = readFile(inputPath);
        JSONArray parsedFile = parseToJSON(fileLines);
        writeToFile(parsedFile.toString(), outputPath);

        return parsedFile;
    }

    private static void writeToFile(String output, String path) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses an ArrayList of file lines into a JSONArray of JSONObjects. Each
     * JSONObject represents a line of dialogue in a VTT file. The VTT file must
     * follow the format of having the speaker's name followed by a colon and then
     * their dialogue. The ArrayList must contain the file lines in the correct
     * order. The JSONArray will contain JSONObjects with the following keys: -
     * "speaker": the name of the speaker - "words": the dialogue spoken by the
     * speaker - "start": the start time of the dialogue in the format
     * "hh:mm:ss.mmm" - "end": the end time of the dialogue in the format
     * "hh:mm:ss.mmm"
     * 
     * @param fileLines the ArrayList of file lines to parse
     * @return the parsed JSONArray of JSONObjects, or null if an exception occurred
     *         during parsing
     */
    private static JSONArray parseToJSON(ArrayList<String> fileLines) {
        try {
            JSONArray parsedFile = new JSONArray();
            int startIndex = 2;
            for (int i = startIndex; i < fileLines.size(); i += 4) {
                JSONObject line = new JSONObject();
                String[] times = fileLines.get(i + 1).split(" --> ");
                String[] nameAndWords = fileLines.get(i + 2).split(": ");
                if (nameAndWords.length == 1) {
                    Object obj = parsedFile.getJSONObject(parsedFile.length() - 1).get("speaker");
                    String speaker = obj.toString();
                    line.put("speaker", speaker);
                    line.put("words", nameAndWords[0]);
                } else {
                    line.put("speaker", nameAndWords[0]);
                    line.put("words", nameAndWords[1]);
                }
                line.put("start", times[0]);
                line.put("end", times[1]);
                parsedFile.put(line);
            }

            return parsedFile;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Reads a file from the given path and returns its contents as an ArrayList of
     * Strings for each line.
     *
     * @param path the path of the file to be read
     * @return an ArrayList of Strings containing the contents of the file
     */
    private static ArrayList<String> readFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            ArrayList<String> fileLines = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                fileLines.add(line);
                line = reader.readLine();
            }
            return fileLines;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}