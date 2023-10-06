package ProblemSets.W7.AntSimulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Simulation {
    private static ArrayList<Ant> ants;
    private static List<Coordinate> coordinates;
    private static HashSet<Coordinate> coordinatesWithFood;
    private static Simulation instance;

    public static Simulation getInstance() {
        if (instance == null)
            instance = new Simulation();
        return instance;
    }

    private Simulation() {
        ants = new ArrayList<Ant>();
        coordinates = new ArrayList<Coordinate>();
        coordinatesWithFood = new HashSet<Coordinate>();
        initializeCoordinates();
    }

    private void initializeCoordinates() {
        for (int i = 0; i <= Settings.WIDTH; i++) {
            for (int j = 0; j <= Settings.HEIGHT; j++) {
                Coordinate coordinate = new Coordinate(i, j);
                coordinates.add(coordinate);
                initializeAllAnts(coordinate);
                initializeAllFood(coordinate);
            }
        }
    }

    private void initializeAllAnts(Coordinate coordinate) {
        if (coordinate.x == Settings.ANT_SPAWN_X && coordinate.y == Settings.ANT_SPAWN_Y) {
            for (int i = 0; i < Settings.NUM_ANTS; i++) {
                Ant ant = new Ant(coordinate);
                ants.add(ant);
            }
        }
    }

    private void initializeAllFood(Coordinate coordinate) {
        if (Math.random() < (double) Settings.NUM_FOOD / (Settings.WIDTH * Settings.HEIGHT)) {
            coordinatesWithFood.add(coordinate);
        }
    }

    /**
     * Runs the simulation.
     * 
     * @param main The Main object that controls the simulation.
     */
    public void run(Main main) {
        for (Ant ant : ants) {
            ant.act(coordinates, coordinatesWithFood, main);
        }

        for (Coordinate coordinate : coordinatesWithFood) {
            main.fill(150, 75, 0);
            main.ellipse(coordinate.x, coordinate.y, 5, 5);
        }
    }
}
