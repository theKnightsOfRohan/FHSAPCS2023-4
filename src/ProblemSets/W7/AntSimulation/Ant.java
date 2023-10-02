package ProblemSets.W7.AntSimulation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;

public class Ant {
    private int[] location;
    private Stack<String> visitedLocations;
    private boolean foundFood;

    public Ant(int x, int y) {
        this.location = new int[] { x, y };
        this.visitedLocations = new Stack<String>();
        this.foundFood = false;
    }

    /**
     * This method represents the behavior of an ant. If the ant has found food, it
     * will return to the spawn point by following the pheromones trail. If the ant
     * has not found food, it will search for food by following the pheromones
     * trail. If the ant finds food, it will set the foundFood flag to true. If the
     * ant does not find food, it will push the current location to the
     * visitedLocations stack. The method also updates the pheromones trail and
     * displays the ant on the screen.
     * 
     * @param coordinatesToPheromones a HashMap that maps coordinates to the amount
     *                                of pheromones at that location
     * @param coordinatesToFood       a HashMap that maps coordinates to a boolean
     *                                indicating whether there is food at that
     *                                location
     * @param main                    the Main object representing the main program
     */
    public void act(HashMap<String, Integer> coordinatesToPheromones, HashMap<String, Boolean> coordinatesToFood, Main main) {
        if (this.foundFood) {
            if (this.returnToSpawn(coordinatesToPheromones)) {
                this.foundFood = false;
            }
        } else {
            this.search(coordinatesToPheromones);
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
     * This method is used to make the ant return to its spawn point. If the ant has
     * not visited any locations, it sets the foundFood variable to false and
     * returns true. Otherwise, it pops the last visited location from the
     * visitedLocations stack, updates the ant's location and pheromones, and
     * returns false.
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
        String[] lastLocationArr = lastLocation.substring(1, lastLocation.length() - 1).split(", ");
        int[] lastLocationInts = new int[] { Integer.parseInt(lastLocationArr[0]), Integer.parseInt(lastLocationArr[1]) };
        this.location = lastLocationInts;
        this.updatePheromones(coordinatesToPheromones);
        return false;
    }

    /**
     * Checks if the current location of the ant has food.
     * 
     * @param coordinatesToFood a HashMap containing the coordinates of the food as
     *                          keys and a boolean value indicating if the food is
     *                          present at that location.
     * @return true if the current location of the ant has food, false otherwise.
     */
    private boolean findsFood(HashMap<String, Boolean> coordinatesToFood) {
        String key = this.locString();
        return coordinatesToFood.containsKey(key);
    }

    /**
     * This method searches for the next location for the ant to move to based on
     * the pheromone levels of the surrounding coordinates. It uses a weighted
     * random selection algorithm to choose the next location.
     * 
     * @param coordinatesToPheromones a HashMap that maps coordinates to their
     *                                corresponding pheromone levels
     */
    private void search(HashMap<String, Integer> coordinatesToPheromones) {
        int currX = this.location[0];
        int currY = this.location[1];
        ArrayList<int[]> surroundingCoords = new ArrayList<>(Arrays.asList(new int[] { currX - 1, currY - 1 }, new int[] { currX - 1, currY },
                new int[] { currX - 1, currY + 1 }, new int[] { currX, currY - 1 }, new int[] { currX, currY + 1 },
                new int[] { currX + 1, currY - 1 }, new int[] { currX + 1, currY }, new int[] { currX + 1, currY + 1 }));

        int[] newCoords = findWeightedRandomCoords(surroundingCoords, coordinatesToPheromones);
        this.location = newCoords;
    }

    /**
     * Finds a random coordinate from the given surrounding coordinates based on the
     * amount of pheromones at each coordinate. The coordinates with more pheromones
     * have a higher chance of being selected.
     * 
     * @param surroundingCoords       the array of surrounding coordinates to choose
     *                                from
     * @param coordinatesToPheromones the mapping of coordinates to the amount of
     *                                pheromones at each coordinate
     * @return an array of two integers representing the randomly selected
     *         coordinate
     */
    private int[] findWeightedRandomCoords(ArrayList<int[]> surroundingCoords, HashMap<String, Integer> coordinatesToPheromones) {
        ArrayList<Integer[]> weights = new ArrayList<>();
        int totalPheromones = 0;
        if (this.visitedLocations.size() != 0) {
            int[] prevLocation = parseCoordinateString(visitedLocations.peek());
            surroundingCoords.remove(prevLocation);
        }
        for (int[] coords : surroundingCoords) {
            if (coords[0] < 0 || coords[0] > Settings.WIDTH || coords[1] < 0 || coords[1] > Settings.HEIGHT)
                continue;
            String key = Arrays.toString(coords);
            int pheromones = coordinatesToPheromones.get(key);
            totalPheromones += pheromones;

            for (int i = 0; i < pheromones; i++) {
                weights.add(new Integer[] { coords[0], coords[1] });
            }
        }

        int randomIndex = (int) (Math.random() * totalPheromones);
        Integer[] randomCoords = weights.get(randomIndex);

        return new int[] { randomCoords[0], randomCoords[1] };
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
