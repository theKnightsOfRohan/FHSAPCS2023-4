package ProblemSets.W12;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;

public class CSVDataUtils {
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

	public static void writeToCSVFile(String filePath, List<Double> data) {
		try (java.io.PrintWriter writer = new java.io.PrintWriter(filePath)) {
			for (int i = 0; i < data.size(); i++) {
				writer.println(i + "," + data.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Double> applyBasicMedianFilter(List<Double> magnitudes) {
		Arrays.sort(magnitudes.toArray());
		return magnitudes.subList(magnitudes.size() / 2, magnitudes.size());
	}

	public static List<Double> applyMovingAverage(List<Double> magnitudes, int spread) {
		List<Double> filteredMagnitudes = new ArrayList<Double>();
		for (int i = 0; i < magnitudes.size(); i++) {
			double sum = 0;
			for (int j = i - spread; j < i + spread; j++) {
				if (j >= 0 && j < magnitudes.size()) {
					sum += magnitudes.get(j);
				}
			}
			filteredMagnitudes.add(sum / (spread * 2 + 1));
		}
		return filteredMagnitudes;
	}

	public static String getOutputPath(String filePath) {
		List<String> pathParts = Arrays.asList(filePath.split("/"));
		pathParts.set(pathParts.size() - 2, "Output");
		String fileName = pathParts.get(pathParts.size() - 1);
		pathParts.set(pathParts.size() - 1, fileName.substring(0, fileName.length() - 4) + "_output.txt");
		return String.join("/", pathParts);
	}
}
