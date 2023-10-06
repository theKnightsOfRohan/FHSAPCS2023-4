package ProblemSets.W7.AntSimulation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;

public class Ant {
    private Coordinate location;
    private Coordinate originalLocation;
    private Stack<Coordinate> visitedLocations;
    private boolean foundFood;

    public Ant(Coordinate coordinate) {
        this.location = coordinate;
        this.originalLocation = coordinate;
        this.visitedLocations = new Stack<Coordinate>();
        this.visitedLocations.push(this.location);
        this.foundFood = false;
    }

    public void act(List<Coordinate> coordinates, HashSet<Coordinate> coordinatesWithFood, Main main) {
        if (this.foundFood) {
            if (this.returnToSpawn(coordinates)) {
                this.foundFood = false;
            }
        } else {
            this.search(coordinates, coordinatesWithFood);
            if (this.findsFood(coordinatesWithFood)) {
                this.foundFood = true;
                coordinatesWithFood.remove(this.location);
            } else {
                if (!this.visitedLocations.contains(this.location))
                    this.visitedLocations.push(this.location);
            }
        }

        this.updatePheromones(coordinates);

        main.fill(0, 0, 0);
        main.ellipse(this.location.x, this.location.y, 5, 5);
    }

    private boolean returnToSpawn(List<Coordinate> coordinates) {
        if (this.visitedLocations.isEmpty()) {
            this.foundFood = false;
            return true;
        }

        this.location = this.visitedLocations.pop();
        this.updatePheromones(coordinates);
        return false;
    }

    private boolean findsFood(HashSet<Coordinate> coordinatesWithFood) {
        return coordinatesWithFood.contains(this.location);
    }

    private void search(List<Coordinate> coordinates, HashSet<Coordinate> coordinatesWithFood) {
        ArrayList<Coordinate> surroundingCoords = getSurroundingCoords(coordinates);

        Coordinate[] closestCoords = findClosestCoords(coordinatesWithFood, surroundingCoords, 3);
        Coordinate newCoords = findDirectProportionCoords(closestCoords, coordinates);
        this.location = newCoords;
    }

    private ArrayList<Coordinate> getSurroundingCoords(List<Coordinate> coordinates) {
        ArrayList<Coordinate> surroundingCoords = new ArrayList<>();
        int listsize = 8;
        if (this.location.x == 0 || this.location.x == Settings.WIDTH)
            listsize -= 3;
        if (this.location.y == 0 || this.location.y == Settings.HEIGHT)
            listsize -= 3;

        for (Coordinate coord : coordinates) {
            if (this.location.getTravelDistance(coord) == 1) {
                surroundingCoords.add(coord);
                if (surroundingCoords.size() == listsize)
                    break;
            }
        }

        return surroundingCoords;
    }

    private Coordinate findDirectProportionCoords(Coordinate[] closestCoords, List<Coordinate> coordinates) {
        int totalWeightage = 0;
        List<Coordinate> weightedCoords = new ArrayList<>();

        for (Coordinate coord : closestCoords) {
            int pheromones = coord.pheromones;
            int distanceFromOriginalLocation = coord.getTravelDistance(this.originalLocation);
            int distanceFromPreviousLocation = distanceFromPreviousLocation(coord);
            int currentWeightage = pheromones * distanceFromOriginalLocation * distanceFromPreviousLocation;
            totalWeightage += currentWeightage;

            for (int i = 0; i < currentWeightage; i++) {
                weightedCoords.add(coord);
            }
        }

        int randomIndex = (int) (Math.random() * totalWeightage);
        Coordinate randomCoord = weightedCoords.get(randomIndex);

        return randomCoord;
    }

    private Coordinate[] findClosestCoords(HashSet<Coordinate> coordinatesWithFood, ArrayList<Coordinate> surroundingCoords, int numClosest) {
        Coordinate[] closestCoords = new Coordinate[numClosest];
        double[] closestDistances = new double[numClosest];
        Arrays.fill(closestDistances, Double.MAX_VALUE);

        for (Coordinate foodCoord : coordinatesWithFood) {
            for (Coordinate coord : surroundingCoords) {
                double distance = coord.getTravelDistance(foodCoord);
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

    private int distanceFromPreviousLocation(Coordinate coord) {
        Coordinate prevLocation;

        try {
            prevLocation = this.visitedLocations.peek();
        } catch (EmptyStackException e) {
            return 1;
        }

        return coord.getTravelDistance(prevLocation);
    }

    private void updatePheromones(List<Coordinate> coordinates) {
        this.location.pheromones++;
    }
}
