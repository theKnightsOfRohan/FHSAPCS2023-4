package ProblemSets.W12;

import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import org.apache.commons.math3.fitting.HarmonicCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import Plot.PlotWindow;
import Plot.ScatterPlot;

public class ReadStepsData {
	public static void main(String[] args) {
		String filePath = "src/ProblemSets/W12/Steps_Data/1-100-step-regular.csv";
		HashMap<String, List<String>> dataAsStrings = Utils.readCSVFileAsMap(filePath);

		HashMap<String, List<Double>> data = Utils.parseCSVString(dataAsStrings);

		List<Double> magnitudes = Utils.getMagnitudes(data.get("BMI160_accelerometer.x"), data.get("BMI160_accelerometer.y"),
				data.get("BMI160_accelerometer.z"));

		System.out.println("Data size: " + magnitudes.size());
		Utils.writeToCSVFile("src/ProblemSets/W12/Output/FullMagnitudes.csv", magnitudes);

		magnitudes = Utils.applyBasicMedianFilter(magnitudes);
		System.out.println("Data size after median filter: " + magnitudes.size());
		Utils.writeToCSVFile("src/ProblemSets/W12/Output/FilteredMagnitudes.csv", magnitudes);

		magnitudes = Utils.applyMovingAverage(magnitudes, 5);
		Utils.writeToCSVFile("src/ProblemSets/W12/Output/MovingAverage.csv", magnitudes);

		double threshold = 0;
		System.out.println(calculateSteps(magnitudes, threshold) + " steps calculated.");

		plotData(magnitudes, threshold);
	}

	private static int calculateSteps(List<Double> magnitudes, double threshold) {
		int stepCount = 0;
		for (int i = 1; i < magnitudes.size() - 1; i++) {
			if (isPeakOrValley(magnitudes, i, threshold)) {
				stepCount++;
			}
		}

		return stepCount;
	}

	private static void plotData(List<Double> data, double threshold) {
		ScatterPlot plt = new ScatterPlot(100, 100, 1100, 700);
		WeightedObservedPoints obs = new WeightedObservedPoints();

		for (int i = 0; i < data.size(); i++) {
			plt.plot(0, i, data.get(i)).strokeColor("red").strokeWeight(2).style("-");
			obs.add(i, data.get(i));

			if (isPeakOrValley(data, i, threshold)) {
				plt.plot(1, i, data.get(i)).strokeColor("green").strokeWeight(2).style(".");
			}
		}

		HarmonicCurveFitter fitter = HarmonicCurveFitter.create();
		double[] params = fitter.fit(obs.toList());
		// Print the equation for the fitted curve
		System.out.println(params[0] + " * sin(" + params[1] + " * x + " + params[2] + ")");

		for (int i = 0; i < data.size(); i++) {
			plt.plot(2, i, params[0] * Math.sin(params[1] * i + params[2]) + 10).strokeColor("blue").strokeWeight(2).style("-");
		}

		PlotWindow window = PlotWindow.getWindowFor(plt, 1200, 800);
		window.show();
	}

	public static boolean isPeakOrValley(List<Double> data, int index, double threshold) {
		try {
			boolean isPeak = data.get(index) - data.get(index - 1) > threshold && data.get(index) - data.get(index + 1) > threshold;
			boolean isValley = data.get(index - 1) - data.get(index) > threshold && data.get(index + 1) - data.get(index) > threshold;
			return isPeak || isValley;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
}