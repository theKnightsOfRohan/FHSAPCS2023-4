package ProblemSets.W12;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class Utils {
	public static HashMap<String, List<Double>> parseCSVString(HashMap<String, List<String>> dataAsStrings) {
		HashMap<String, List<Double>> data = new HashMap<String, List<Double>>();
		for (String key : dataAsStrings.keySet()) {
			data.put(key, new ArrayList<Double>());
			for (String value : dataAsStrings.get(key)) {
				data.get(key).add(Double.parseDouble(value));
			}
		}
		return data;
	}

	public static HashMap<String, List<String>> readCSVFileAsMap(String filePath) {
		HashMap<String, List<String>> data = new HashMap<String, List<String>>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String[] lineNames = reader.readLine().split(",");
			for (String name : lineNames) {
				data.put(name, new ArrayList<String>());
			}
			String line = reader.readLine();
			while (line != null) {
				String[] row = line.split(",");
				for (int i = 0; i < row.length; i++) {
					data.get(lineNames[i]).add(row[i]);
				}
				line = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public static List<Double> getMagnitudes(List<Double> x, List<Double> y, List<Double> z) {
		List<Double> magnitudes = new ArrayList<Double>();
		for (int i = 0; i < x.size(); i++) {
			magnitudes.add(Math.sqrt(Math.pow(x.get(i), 2) + Math.pow(y.get(i), 2) + Math.pow(z.get(i), 2)));
		}
		return magnitudes;
	}
}
