package ProblemSets.W9;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReadVTTFiles {
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
                    // System.out.println(speaker);
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

            // System.out.println(parsedFile);

            return parsedFile;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

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