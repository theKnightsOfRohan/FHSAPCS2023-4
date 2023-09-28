package GeneralHelpers;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import GeneralHelpers.Annotations.HelperMethod;
import GeneralHelpers.Annotations.RunnableMethod;

public class JSONSaver {
    JSONObject obj;

    public JSONSaver() {
        obj = new JSONObject();
    }

    public JSONSaver(String fileString) {
        if (!fileString.endsWith(".json"))
            fileString += ".json";

        try {
            String content = new String(Files.readAllBytes(Paths.get(fileString)));
            obj = new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("File does not exist");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("File is not a valid JSON file");
        }
    }

    public JSONSaver(File file) {
        if (!file.getName().endsWith(".json"))
            file = new File(file.getName() + ".json");

        try {
            String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            obj = new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("File is not a valid JSON file");
        }
    }

    /**
     * Saves the given data with the specified key to a JSON object.
     * 
     * @param key  the key to associate with the data
     * @param data the data to be saved
     */
    @RunnableMethod
    public void saveData(String key, Object data) {
        JSONObject obj = new JSONObject();

        obj.put(key, data);
    }

    /**
     * Writes all data to a JSON file.
     * 
     * @param fileString the file path to write the data to
     */
    @RunnableMethod
    public void writeAllData(String fileString) {
        if (obj == null) {
            System.out.println("No data to write");
            return;
        }

        if (!fileString.endsWith(".json"))
            fileString += ".json";

        try (FileWriter file = new FileWriter(fileString)) {
            file.write(obj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes all data to a JSON file.
     * 
     * @param file the file to write the data to
     */
    @RunnableMethod
    public void writeAllData(File file) {
        if (obj == null) {
            System.out.println("No data to write");
            return;
        }

        if (!file.getName().endsWith(".json"))
            file = new File(file.getName() + ".json");

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(obj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RunnableMethod
    public void updateData(String key, Object data) {
        if (obj == null) {
            System.out.println("No data to update");
            return;
        }

        obj.put(key, data);
    }
}
