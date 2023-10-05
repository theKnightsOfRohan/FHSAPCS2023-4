package ProblemSets.W7.AntSimulation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;

public class Ant {
    private int[] location;
    private int[] originalLocation;
    private Stack<String> visitedLocations;
    private boolean foundFood;

    public Ant(int x, int y) {
        this.location = new int[] { x, y };
        this.originalLocation = new int[] { x, y };
        this.visitedLocations = new Stack<String>();
        this.visitedLocations.push(Arrays.toString(this.location));
        this.foundFood = false;
    }

    public void act(HashMap<String, Integer> coordinatesToPheromones, HashSet<String> coordinatesToFood, Main main) {
        if (this.foundFood) {
            if (this.returnToSpawn(coordinatesToPheromones)) {
                this.foundFood = false;
            }
        } else {
            this.search(coordinatesToPheromones, coordinatesToFood);
            if (this.findsFood(coordinatesToFood)) {
                this.foundFood = true;
                coordinatesToFood.remove(this.locString());
            } else {
                if (!this.visitedLocations.contains(this.locString()))
                    this.visitedLocations.push(this.locString());
            }
        }

        this.updatePheromones(coordinatesToPheromones);

        main.fill(0, 0, 0);
        main.ellipse(this.location[0], this.location[1], 5, 5);
    }

    private boolean returnToSpawn(HashMap<String, Integer> coordinatesToPheromones) {
        if (this.visitedLocations.isEmpty()) {
            this.foundFood = false;
            return true;
        }

        String lastLocation = this.visitedLocations.pop();
        int[] lastLocationInts = parseCoordinateString(lastLocation);
        this.location = lastLocationInts;
        this.updatePheromones(coordinatesToPheromones);
        return false;
    }

    private boolean findsFood(HashSet<String> coordinatesToFood) {
        String key = this.locString();
        return coordinatesToFood.contains(key);
    }

    private void search(HashMap<String, Integer> coordinatesToPheromones, HashSet<String> coordinatesToFood) {
        int currX = this.location[0];
        int currY = this.location[1];
        ArrayList<int[]> surroundingCoords = new ArrayList<>(Arrays.asList(new int[] { currX - 1, currY - 1 }, new int[] { currX - 1, currY },
                new int[] { currX - 1, currY + 1 }, new int[] { currX, currY - 1 }, new int[] { currX, currY + 1 },
                new int[] { currX + 1, currY - 1 }, new int[] { currX + 1, currY }, new int[] { currX + 1, currY + 1 }));

        int[][] closestCoords = findClosestCoords(coordinatesToFood, surroundingCoords, 3);
        int[] newCoords = findDirectProportionCoords(closestCoords, coordinatesToPheromones);
        this.location = newCoords;
    }

    private int[] findDirectProportionCoords(int[][] closestCoords, HashMap<String, Integer> coordinatesToPheromones) {
        int totalWeightage = 0;
        List<int[]> weightedCoords = new ArrayList<>();
        double minDistance = Double.MAX_VALUE;

        for (int[] coord : closestCoords) {
            String key = Arrays.toString(coord);
            int pheromones = coordinatesToPheromones.get(key);
            int currentWeightage = pheromones * distanceFromPreviousLocation(coord) * distanceFromOriginalLocation(coord);
            totalWeightage += currentWeightage;
            double distanceToFood = Math.sqrt(Math.pow(coord[0] - closestCoords[0][0], 2) + Math.pow(coord[1] - closestCoords[0][1], 2));
            if (distanceToFood < minDistance) {
                minDistance = distanceToFood;
            }
            for (int i = 0; i < currentWeightage; i++) {
                weightedCoords.add(coord);
            }
        }

        int randomIndex = (int) (Math.random() * totalWeightage);
        int[] randomCoords = weightedCoords.get(randomIndex);

        return randomCoords;
    }

    private int[][] findClosestCoords(HashSet<String> coordinatesToFood, ArrayList<int[]> surroundingCoords, int numClosest) {
        int[][] closestCoords = new int[numClosest][2];
        double[] closestDistances = new double[numClosest];
        Arrays.fill(closestDistances, Double.MAX_VALUE);

        for (String key : coordinatesToFood) {
            int[] foodCoords = parseCoordinateString(key);
            for (int[] coord : surroundingCoords) {
                double distance = Math.sqrt(Math.pow(coord[0] - foodCoords[0], 2) + Math.pow(coord[1] - foodCoords[1], 2));
                for (int i = 0; i < numClosest; i++) {
                    if (distance < closestDistances[i]) {
                        for (int j = numClosest - 1; j > i; j--) {
                            closestDistances[j] = closestDistances[j - 1];
                            closestCoords[j] = closestCoords[j - 1];
                        }
                        closestDistances[i] = distance;
                        closestCoords[i] = coord;
                        break;
                    }
                }
            }

        }

        return closestCoords;
    }

    private int distanceFromPreviousLocation(int[] coord) {
        String previousLocation;
        try {
            previousLocation = this.visitedLocations.peek();
        } catch (EmptyStackException e) {
            return 1;
        }

        int[] prevLocation = parseCoordinateString(previousLocation);
        return Math.abs(coord[0] - prevLocation[0]) + Math.abs(coord[1] - prevLocation[1]);
    }

    private int distanceFromOriginalLocation(int[] coord) {
        return Math.abs(coord[0] - this.originalLocation[0]) + Math.abs(coord[1] - this.originalLocation[1]);
    }

    private int[] parseCoordinateString(String string) {
        return Arrays.stream(string.substring(1, string.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
    }

    private void updatePheromones(HashMap<String, Integer> coordinatesToPheromones) {
        String key = this.locString();
        if (coordinatesToPheromones.containsKey(key))
            coordinatesToPheromones.put(key, coordinatesToPheromones.get(key) + 1);
    }

    private String locString() {
        return Arrays.toString(this.location);
    }
}
