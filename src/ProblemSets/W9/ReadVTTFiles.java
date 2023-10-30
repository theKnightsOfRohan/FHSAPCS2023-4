package ProblemSets.W9;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadVTTFiles {
    public static void main(String[] args) {
        String path = "src/ProblemSets/W9/TranscriptFiles/sample01.vtt";
        ArrayList<String> fileLines = readFile(path);
        for (String line : fileLines) {
            System.out.println(line);
        }
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