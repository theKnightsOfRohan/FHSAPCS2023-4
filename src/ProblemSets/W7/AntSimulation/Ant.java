package ProblemSets.W7.AntSimulation;

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

    /**
     * Creates a new Ant object with the given starting location.
     * 
     * @param coordinate the starting location of the Ant
     */
    public Ant(Coordinate coordinate) {
        this.location = coordinate;
        this.originalLocation = coordinate;
        this.visitedLocations = new Stack<Coordinate>();
        this.visitedLocations.push(this.location);
        this.foundFood = false;
    }

    /**
     * This method represents the behavior of an ant in the simulation. If the ant
     * has found food, it checks if it has returned to its spawn point. If it has,
     * the ant sets its foundFood variable to false. If the ant has not found food,
     * it searches for food and checks if it has found any. If it has found food, it
     * sets its foundFood variable to true and removes the food from the
     * coordinatesWithFood HashSet. If it has not found food, it adds its current
     * location to the visitedLocations Stack if it has not already visited it. The
     * method also updates the pheromones in the coordinates. Finally, the method
     * draws an ellipse at the ant's current location using the main object.
     * 
     * @param coordinates         A List of all the coordinates in the simulation.
     * @param coordinatesWithFood A HashSet of all the coordinates that have food in
     *                            the simulation.
     * @param main                The Main object used to draw the simulation.
     */
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

        this.location.updatePheromones();

        main.fill(0, 0, 0);
        main.ellipse(this.location.x, this.location.y, 5, 5);
    }

    /**
     * Returns true if the ant has returned to its spawn location, false otherwise.
     * If the ant has not visited any locations, sets foundFood to false and returns
     * true. Otherwise, sets the ant's location to the last visited location and
     * returns false.
     * 
     * @param coordinates a list of coordinates representing the ant's visited
     *                    locations
     * @return true if the ant has returned to its spawn location, false otherwise
     */
    private boolean returnToSpawn(List<Coordinate> coordinates) {
        if (this.visitedLocations.isEmpty()) {
            this.foundFood = false;
            return true;
        }

        this.location = this.visitedLocations.pop();

        return false;
    }

    private boolean findsFood(HashSet<Coordinate> coordinatesWithFood) {
        return coordinatesWithFood.contains(this.location);
    }

    /**
     * This method searches for food by finding the closest coordinates with food
     * and moving towards them. It updates the ant's location to the new
     * coordinates.
     * 
     * @param coordinates         The list of coordinates to search around
     * @param coordinatesWithFood The set of coordinates that have food
     */
    private void search(List<Coordinate> coordinates, HashSet<Coordinate> coordinatesWithFood) {
        ArrayList<Coordinate> surroundingCoords = getSurroundingCoords(coordinates);

        Coordinate[] closestCoords = findClosestCoords(coordinatesWithFood, surroundingCoords, 3);
        Coordinate newCoords = findDirectProportionCoords(closestCoords, coordinates);
        this.location = newCoords;
    }

    /**
     * Returns a list of coordinates that are adjacent to the ant's current
     * location. If the ant is at the edge of the simulation area, it will have
     * fewer than 8 surrounding coordinates.
     * 
     * @param coordinates a list of all coordinates in the simulation area
     * @return a list of coordinates that are adjacent to the ant's current location
     */
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

    /**
     * Finds the coordinates that are in direct proportion to the pheromones, travel
     * distance from the original location, and distance from the previous location.
     * 
     * @param closestCoords an array of coordinates that are closest to the ant's
     *                      current location
     * @param coordinates   a list of all coordinates in the simulation
     * @return a coordinate that is randomly selected based on the weights of the
     *         closest coordinates
     */
    private Coordinate findDirectProportionCoords(Coordinate[] closestCoords, List<Coordinate> coordinates) {
        int totalWeightage = 0;
        List<Coordinate> weightedCoords = new ArrayList<>();

        for (Coordinate coord : closestCoords) {
            int pheromones = coord.pheromones;
            int currentWeightage = pheromones * coord.getTravelDistance(this.originalLocation) * distanceFromPreviousLocation(coord);
            totalWeightage += currentWeightage;

            for (int i = 0; i < currentWeightage; i++) {
                weightedCoords.add(coord);
            }
        }

        int randomIndex = (int) (Math.random() * totalWeightage);
        Coordinate randomCoord = weightedCoords.get(randomIndex);

        return randomCoord;
    }

    /**
     * Finds the closest coordinates to a set of coordinates with food, given a list
     * of surrounding coordinates.
     * 
     * @param coordinatesWithFood the set of coordinates with food
     * @param surroundingCoords   the list of surrounding coordinates
     * @param numClosest          the number of closest coordinates to find
     * @return an array of the closest coordinates
     */
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

    /**
     * Calculates the distance between the given coordinate and the previous
     * location visited by the ant. If there is no previous location, returns 1.
     * 
     * @param coord the coordinate to calculate the distance from
     * @return the distance between the given coordinate and the previous location
     *         visited by the ant
     */
    private int distanceFromPreviousLocation(Coordinate coord) {
        Coordinate prevLocation;

        try {
            prevLocation = this.visitedLocations.peek();
        } catch (EmptyStackException e) {
            return 1;
        }

        return coord.getTravelDistance(prevLocation);
    }
}
