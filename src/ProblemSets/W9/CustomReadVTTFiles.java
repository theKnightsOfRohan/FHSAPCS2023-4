package ProblemSets.W9;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.nio.file.*;

public class CustomReadVTTFiles {
    /**
     * This method reads .vtt files and converts them to JSON format. It then
     * creates summary statistics and condensed transcript files for each input
     * file.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        parseAllVTTInDirectory("src/ProblemSets/W9/TranscriptFiles");
    }

    /**
     * Parses all VTT files in the given directory path.
     * 
     * @param pathStr the path of the directory containing VTT files
     */
    public static void parseAllVTTInDirectory(String directoryStr) {
        Path directory = Paths.get(directoryStr);
        try {
            for (Path pathToFile : Files.newDirectoryStream(directory)) {
                if (pathToFile.toString().endsWith(".vtt"))
                    processFile(pathToFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processFile(Path pathToFile) {
        ArrayList<String> fileLines = readFile(pathToFile.toString());

        ArrayList<Message> messages = new ArrayList<>();
        String previousSpeaker = "";
        int startIndex = 3;
        for (int i = startIndex; i < fileLines.size(); i += 4) {
            Message message = new Message(new String[] { fileLines.get(i), fileLines.get(i + 1) }, previousSpeaker);
            messages.add(message);
            previousSpeaker = message.speaker;
        }

        String summaryStatistics = CustomCreateSummaryFiles.createSummaryStatisticsFile(messages);
        String summaryStatisticsPath = createDirectoryAndReturnPath(pathToFile, "Stats", ".txt");
        writeToFile(summaryStatistics, summaryStatisticsPath);

        String condensedTranscript = CustomCreateSummaryFiles.createCondensedTranscriptFile(messages);
        String condensedTranscriptPath = createDirectoryAndReturnPath(pathToFile, "Condensed", ".txt");
        writeToFile(condensedTranscript, condensedTranscriptPath);
    }

    /**
     * Creates a new directory with the given name in the path of the provided file,
     * and returns the path of the new directory with the given file extension.
     * 
     * @param filePath      the path of the file to create the directory in
     * @param directoryName the name of the directory to create
     * @param fileExtension the file extension to add to the new directory path
     * @return the path of the new directory with the given file extension
     */
    private static String createDirectoryAndReturnPath(Path filePath, String directoryName, String fileExtension) {
        ArrayList<String> dirsList = new ArrayList<>(Arrays.asList(filePath.toString().split("/")));
        dirsList.add(dirsList.size() - 1, directoryName);
        String newPath = String.join("/", dirsList.toArray(new String[0]));
        return newPath.substring(0, newPath.length() - 4) + fileExtension;
    }

    private static void writeToFile(String output, String path) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

class Message {
    String speaker;
    String words;
    double[] timeSeconds;

    public Message(String[] messageLines, String previousSpeaker) {
        if (messageLines.length != 2)
            throw new IllegalArgumentException("Invalid message lines");

        String[] timesStrings = messageLines[0].split(" --> ");

        timeSeconds = parseToMinutes(timesStrings);

        String[] speakerAndWords = messageLines[1].split(": ");

        if (speakerAndWords.length != 2) {
            speaker = previousSpeaker;
            words = messageLines[0];
        } else {
            speaker = speakerAndWords[0];
            words = speakerAndWords[1];
        }
    }

    // hh:mm:ss.mmm
    private double[] parseToMinutes(String[] timesStrings) {
        double[] times = new double[2];
        for (int i = 0; i < timesStrings.length; i++) {
            String[] time = timesStrings[i].split(":");
            times[i] = Double.parseDouble(time[0]) * 60 + Double.parseDouble(time[1]) + Double.parseDouble(time[2]) / 60;
        }

        return times;
    }
}