package ProblemSets.W7.AntSimulation;

import processing.core.PApplet;

public class Main extends PApplet {
    Simulation simulation;

    public void settings() {
        size(Settings.WIDTH, Settings.HEIGHT);
    }

    public void setup() {
        background(255);
        frameRate(60);
        simulation = Simulation.getInstance();
    }

    public void draw() {
        background(255);
        simulation.run(this);
    }

    public static void main(String[] args) {
        PApplet.main("ProblemSets.W7.AntSimulation.Main");
    }
}
