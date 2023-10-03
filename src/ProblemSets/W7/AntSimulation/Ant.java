package ProblemSets.W7.AntSimulation;

import java.util.HashMap;
import java.util.HashSet;
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

    /**
     * This method represents the behavior of an ant in the simulation. If the ant
     * has found food, it checks if it has returned to its spawn point. If it has,
     * it sets the foundFood flag to false. If the ant has not found food, it
     * searches for food and checks if it has found any. If it has found food, it
     * sets the foundFood flag to true. If it has not found food, it adds its
     * current location to the visitedLocations stack. The method also updates the
     * pheromones in the coordinatesToPheromones HashMap. Finally, it draws an
     * ellipse representing the ant's location on the screen using the Main object
     * passed as a parameter.
     * 
     * @param coordinatesToPheromones a HashMap containing the pheromone levels for
     *                                each coordinate in the simulation
     * @param coordinatesToFood       a HashSet containing the coordinates of all
     *                                the food sources in the simulation
     * @param main                    a Main object representing the main class of
     *                                the simulation
     */
    public void act(HashMap<String, Integer> coordinatesToPheromones, HashSet<String> coordinatesToFood, Main main) {
        if (this.foundFood) {
            if (this.returnToSpawn(coordinatesToPheromones)) {
                this.foundFood = false;
            }
        } else {
            this.search(coordinatesToPheromones, coordinatesToFood);
            if (this.findsFood(coordinatesToFood))
                this.foundFood = true;
            else {
                this.visitedLocations.push(this.locString());
            }
        }

        this.updatePheromones(coordinatesToPheromones);

        main.fill(0, 0, 0);
        main.ellipse(this.location[0], this.location[1], 5, 5);
    }

    /**
     * This method checks if the ant has visited any locations. If it has not, it
     * sets the foundFood variable to false and returns true. Otherwise, it pops the
     * last visited location from the stack, updates the ant's location and
     * pheromones, and returns false.
     * 
     * @param coordinatesToPheromones a HashMap that maps coordinates to pheromone
     *                                levels
     * @return true if the ant has not visited any locations, false otherwise
     */
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

    /**
     * Checks if the current ant's location is in the set of coordinates to food.
     * 
     * @param coordinatesToFood the set of coordinates to food
     * @return true if the current ant's location is in the set of coordinates to
     *         food, false otherwise
     */
    private boolean findsFood(HashSet<String> coordinatesToFood) {
        String key = this.locString();
        return coordinatesToFood.contains(key);
    }

    /**
     * This method searches for food and updates the ant's location accordingly. It
     * takes in a HashMap of coordinates to pheromone levels and a HashSet of
     * coordinates to food. It finds the closest coordinates to food among the
     * surrounding coordinates and moves towards them in direct proportion to the
     * pheromone levels at those coordinates.
     * 
     * @param coordinatesToPheromones a HashMap of coordinates to pheromone levels
     * @param coordinatesToFood       a HashSet of coordinates to food
     */
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

    /**
     * Finds the coordinates of the closest proportionally weighted location based
     * on pheromones, distance from previous location, and distance from original
     * location.
     * 
     * @param closestCoords           an array of coordinates representing the
     *                                closest locations to the ant's current
     *                                location
     * @param coordinatesToPheromones a HashMap containing the pheromone levels for
     *                                each coordinate
     * @return an array of integers representing the coordinates of the randomly
     *         selected location
     */
    private int[] findDirectProportionCoords(int[][] closestCoords, HashMap<String, Integer> coordinatesToPheromones) {
        int totalWeightage = 0;
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
        }

        int randomIndex = (int) (Math.random() * totalWeightage);
        int[] randomCoords = null;
        int currentWeightage = 0;

        for (int[] coord : closestCoords) {
            String key = Arrays.toString(coord);
            int pheromones = coordinatesToPheromones.get(key);
            int weightage = pheromones * distanceFromPreviousLocation(coord) * distanceFromOriginalLocation(coord);
            double distanceToFood = Math.sqrt(Math.pow(coord[0] - closestCoords[0][0], 2) + Math.pow(coord[1] - closestCoords[0][1], 2));
            double proportion = distanceToFood / minDistance;
            int adjustedWeightage = (int) (proportion * weightage);
            if (randomIndex >= currentWeightage && randomIndex < currentWeightage + adjustedWeightage) {
                randomCoords = coord;
                break;
            }
            currentWeightage += adjustedWeightage;
        }

        return randomCoords;
    }

    /**
     * Finds the closest coordinates to the food source from a given set of
     * surrounding coordinates.
     * 
     * @param coordinatesToFood a HashSet of strings representing the coordinates of
     *                          the food source
     * @param surroundingCoords an ArrayList of integer arrays representing the
     *                          surrounding coordinates
     * @param numClosest        an integer representing the number of closest
     *                          coordinates to find
     * @return a 2D integer array representing the closest coordinates to the food
     *         source
     */
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

    /**
     * Calculates the distance between the current location of the ant and its
     * previous location. If the ant has not visited any location before, the
     * distance is considered to be 1.
     * 
     * @param coord an integer array representing the current location of the ant
     * @return an integer representing the distance between the current location and
     *         the previous location of the ant
     */
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

    /**
     * Calculates the Manhattan distance between the given coordinates and the
     * original location of the ant.
     * 
     * @param coord the coordinates to calculate the distance from
     * @return the Manhattan distance between the given coordinates and the original
     *         location of the ant
     */
    private int distanceFromOriginalLocation(int[] coord) {
        return Math.abs(coord[0] - this.originalLocation[0]) + Math.abs(coord[1] - this.originalLocation[1]);
    }

    /**
     * Parses a string representing a coordinate in the format "(x, y)" and returns
     * an integer array containing the x and y values.
     * 
     * @param string the string representing the coordinate
     * @return an integer array containing the x and y values of the coordinate
     */
    private int[] parseCoordinateString(String string) {
        return Arrays.stream(string.substring(1, string.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
    }

    /**
     * Updates the pheromones for the current ant's location in the given HashMap.
     * If the HashMap already contains the current location, the pheromone count is
     * incremented by 1.
     * 
     * @param coordinatesToPheromones the HashMap containing the coordinates and
     *                                their corresponding pheromone counts
     */
    private void updatePheromones(HashMap<String, Integer> coordinatesToPheromones) {
        String key = this.locString();
        if (coordinatesToPheromones.containsKey(key))
            coordinatesToPheromones.put(key, coordinatesToPheromones.get(key) + 1);
    }

    private String locString() {
        return Arrays.toString(this.location);
    }
}
