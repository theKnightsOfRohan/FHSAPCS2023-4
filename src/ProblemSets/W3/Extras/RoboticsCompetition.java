package ProblemSets.W3.Extras;

import Utils.Annotations.HelperMethod;
import Utils.Annotations.RunnableMethod;

public class RoboticsCompetition {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++)
            System.out.println(calcAvgDrivingDistance(10000, 6, 54, 27));
    }

    /*
     * Each match is 2:15 and we calculate the expected score from a particular
     * strategy by thinking in terms of scoring cycles. A scoring cycle consists of:
     * moving to a ball location, picking it up, moving to another ball location,
     * picking it up, driving to the nearest scoring position and shooting. We add
     * up the time for all those steps and we can figure out how long a cycle takes,
     * and therefore how many full cycles will fit in 2:15. Write a monte-carlos
     * simulation to determine the average driving distance for a complete scoring
     * cycle assuming 6 balls a random locations on a 54’ x 27’ field.
     */

    @RunnableMethod
    public static double calcAvgDrivingDistance(int trials, int balls, int fieldWidth, int fieldHeight) {
        double totalDistance = 0;

        for (int i = 1; i <= trials; i++) {
            totalDistance += drivingDistance(balls, fieldWidth, fieldHeight);
        }

        return totalDistance / trials;
    }

    @HelperMethod
    private static double drivingDistance(int balls, int fieldWidth, int fieldHeight) {
        double totalDistance = 0;
        double startX = 0;
        double startY = 0;
        double goalX = fieldWidth;
        double goalY = 0;

        for (int i = 0; i < balls / 2; i++) {
            double firstBallX = Math.random() * fieldWidth;
            double firstBallY = Math.random() * fieldHeight;
            double secondBallX = Math.random() * fieldWidth;
            double secondBallY = Math.random() * fieldHeight;

            totalDistance += distance(startX, startY, firstBallX, firstBallY);
            totalDistance += distance(firstBallX, firstBallY, secondBallX, secondBallY);
            totalDistance += distance(secondBallX, secondBallY, goalX, goalY);

            startX = goalX;
            startY = goalY;
        }

        return totalDistance;
    }

    @HelperMethod
    private static double distance(double startX, double startY, double ballX, double ballY) {
        return Math.sqrt(Math.pow(ballX - startX, 2) + Math.pow(ballY - startY, 2));
    }
}
