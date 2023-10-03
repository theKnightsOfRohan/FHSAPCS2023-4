package ProblemSets.W7.AntSimulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Simulation {
    private static ArrayList<Ant> ants;
    private static HashMap<String, Integer> coordinatesToPheromones;
    private static HashSet<String> coordinatesToFood;
    private static Simulation instance;

    public static Simulation getInstance() {
        if (instance == null)
            instance = new Simulation();
        return instance;
    }

    private Simulation() {
        ants = new ArrayList<Ant>();
        coordinatesToPheromones = new HashMap<String, Integer>();
        coordinatesToFood = new HashSet<String>();
        initializeAllAnts();
        initializeAllPheromones();
        initializeAllFood();
    }

    /**
     * Runs the simulation.
     * 
     * @param main The Main object that controls the simulation.
     */
    public void run(Main main) {
        for (Ant ant : ants) {
            ant.act(coordinatesToPheromones, coordinatesToFood, main);
        }

        for (String key : coordinatesToFood) {
            int[] coordinates = Arrays.stream(key.substring(1, key.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
            int x = coordinates[0];
            int y = coordinates[1];
            main.fill(150, 75, 0);
            main.ellipse(x, y, 5, 5);
        }
    }

    /**
     * Initializes all ants by creating a new Ant object with the starting position
     * at the center of the simulation area. The number of ants created is
     * determined by the constant value NUM_ANTS in the Settings class.
     */
    private static void initializeAllAnts() {
        for (int i = 0; i < Settings.NUM_ANTS; i++) {
            int x = Settings.WIDTH / 2;
            int y = Settings.HEIGHT / 2;
            ants.add(new Ant(x, y));
        }
    }

    /**
     * Initializes all pheromones for the simulation by iterating through all
     * coordinates and setting the pheromone value to 1.
     */
    private static void initializeAllPheromones() {
        for (int x = 1; x <= Settings.WIDTH; x++) {
            for (int y = 1; y <= Settings.HEIGHT; y++) {
                coordinatesToPheromones.put(Arrays.toString(new int[] { x, y }), 1);
            }
        }
    }

    /**
     * Initializes all the food in the simulation by generating random coordinates
     * for each food item and adding it to the coordinatesToFood HashMap.
     */
    private static void initializeAllFood() {
        for (int i = 0; i < Settings.NUM_FOOD; i++) {
            int x = (int) (Math.random() * Settings.WIDTH);
            int y = (int) (Math.random() * Settings.HEIGHT);
            coordinatesToFood.add(Arrays.toString(new int[] { x, y }));
        }
    }
}
