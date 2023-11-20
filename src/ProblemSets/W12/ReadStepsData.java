package ProblemSets.W12;

import java.util.HashMap;
import java.util.List;
import Plot.*;

public class ReadStepsData {
	public static void main(String[] args) {
		String filePath = "src/ProblemSets/W12/Steps_Data/1-200-step-regular.csv";
		HashMap<String, List<String>> dataAsStrings = Utils.readCSVFileAsMap(filePath);

		HashMap<String, List<Double>> data = Utils.parseCSVString(dataAsStrings);

		List<Double> magnitudes = Utils.getMagnitudes(data.get("BMI160_accelerometer.x"), data.get("BMI160_accelerometer.y"),
				data.get("BMI160_accelerometer.z"));

		System.out.println(calculateSteps(magnitudes, 2.2) + " steps calculated.");

		filePath = "src/ProblemSets/W12/Steps_Data/2-200-step-variable.csv";

		dataAsStrings = Utils.readCSVFileAsMap(filePath);
		data = Utils.parseCSVString(dataAsStrings);

		magnitudes = Utils.getMagnitudes(data.get("BMI160_accelerometer.x"), data.get("BMI160_accelerometer.y"), data.get("BMI160_accelerometer.z"));

		System.out.println(calculateSteps(magnitudes, 2.2) + " steps calculated.");
	}

	private static int calculateSteps(List<Double> magnitudes, double threshold) {
		int stepCount = 0;
		for (int i = 1; i < magnitudes.size() - 1; i++) {
			if (magnitudes.get(i) - magnitudes.get(i - 1) > threshold && magnitudes.get(i) - magnitudes.get(i + 1) > threshold) {
				stepCount++;
			}
		}

		return stepCount;
	}

	private static void plotData(List<Double> data) {
		ScatterPlot plt = new ScatterPlot(100, 100, 1100, 700);

		for (int i = 0; i < data.size(); i++) {
			plt.plot(0, i, data.get(i)).strokeColor("red").strokeWeight(2).style("-");
		}

		PlotWindow window = PlotWindow.getWindowFor(plt, 1200, 800);
		window.show();
	}

}