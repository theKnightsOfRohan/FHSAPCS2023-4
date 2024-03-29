package ProblemSets.W12;

import Utils.FileIO;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import org.apache.commons.math3.fitting.HarmonicCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import Plot.PlotWindow;
import Plot.ScatterPlot;

public class ReadStepsData {
	public static void main(String[] args) {
		processData("src/ProblemSets/W12/Steps_Data/1-200-step-regular.csv", 0);
		processData("src/ProblemSets/W12/Steps_Data/2-200-step-variable.csv", 0);
		processData("src/ProblemSets/W12/Steps_Data/3-200-step-jacket.csv", 0);
	}

	public static void processData(String filePath, double threshold) {
		String ouputPath = CSVDataUtils.getOutputPath(filePath);
		String outputStr = "Threshold: " + threshold + "\n";

		HashMap<String, List<String>> dataAsStrings = CSVDataUtils.readCSVFileAsMap(filePath);

		HashMap<String, List<Double>> data = CSVDataUtils.parseCSVString(dataAsStrings);

		List<Double> magnitudes = CSVDataUtils.getMagnitudes(data.get("BMI160_accelerometer.x"), data.get("BMI160_accelerometer.y"),
				data.get("BMI160_accelerometer.z"));

		outputStr += "Length of data before filtering: " + magnitudes.size() + "\n";

		// magnitudes = CSVDataUtils.applyBasicMedianFilter(magnitudes);
		// outputStr += "Length of data after median filter: " + magnitudes.size() +
		// "\n";

		magnitudes = CSVDataUtils.applyMovingAverage(magnitudes, 5);
		outputStr += "Number of steps after moving average: " + calculateSteps(magnitudes, threshold) + "\n";
		FileIO.writeToFile(ouputPath, outputStr);

		plotData(magnitudes, threshold);
	}

	private static int calculateSteps(List<Double> magnitudes, double threshold) {
		int stepCount = 0;
		for (int i = 1; i < magnitudes.size() - 1; i++) {
			if (isPeak(magnitudes, i, threshold) || isValley(magnitudes, i, threshold)) {
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

			if (isPeak(data, i, threshold) || isValley(data, i, threshold)) {
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

	private static boolean isPeak(List<Double> magnitudes, int index, double threshold) {
		try {
			return magnitudes.get(index) - magnitudes.get(index - 1) > threshold && magnitudes.get(index) - magnitudes.get(index + 1) > threshold;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	private static boolean isValley(List<Double> magnitudes, int index, double threshold) {
		try {
			return magnitudes.get(index - 1) - magnitudes.get(index) > threshold && magnitudes.get(index + 1) - magnitudes.get(index) > threshold;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

}