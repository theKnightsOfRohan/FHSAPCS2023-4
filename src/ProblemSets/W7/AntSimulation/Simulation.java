package ProblemSets.W7.AntSimulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Simulation {
    private static ArrayList<Ant> ants;
    private static HashMap<String, Integer> coordinatesToPheromones;
    private static HashMap<String, Boolean> coordinatesToFood;

    public Simulation() {
        ants = new ArrayList<Ant>();
        coordinatesToPheromones = new HashMap<String, Integer>();
        coordinatesToFood = new HashMap<String, Boolean>();
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

        /*
         * for (String key : coordinatesToPheromones.keySet()) { int[] coordinates =
         * Arrays.stream(key.substring(1, key.length() -
         * 1).split(", ")).mapToInt(Integer::parseInt).toArray(); int x =
         * coordinates[0]; int y = coordinates[1]; int pheromones =
         * coordinatesToPheromones.get(key); }
         */

        for (String key : coordinatesToFood.keySet()) {
            int[] coordinates = Arrays.stream(key.substring(1, key.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
            int x = coordinates[0];
            int y = coordinates[1];
            if (coordinatesToFood.get(key)) {
                main.fill(150, 75, 0);
                main.ellipse(x, y, 5, 5);
            }
        }
    }

    private static void initializeAllAnts() {
        for (int i = 0; i < Settings.NUM_ANTS; i++) {
            int x = Settings.WIDTH / 2;
            int y = Settings.HEIGHT / 2;
            ants.add(new Ant(x, y));
        }
    }

    private static void initializeAllPheromones() {
        for (int i = 0; i < Settings.HEIGHT; i++) {
            for (int j = 0; j < Settings.WIDTH; j++) {
                coordinatesToPheromones.put(Arrays.toString(new int[] { i, j }), 1);
            }
        }
    }

    private static void initializeAllFood() {
        for (int i = 0; i < Settings.NUM_FOOD; i++) {
            int x = (int) (Math.random() * Settings.WIDTH);
            int y = (int) (Math.random() * Settings.HEIGHT);
            coordinatesToFood.put(Arrays.toString(new int[] { x, y }), true);
        }
    }
}
