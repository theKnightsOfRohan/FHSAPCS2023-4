package ProblemSets.W7.AntSimulation;

public class Coordinate {
    int x;
    int y;
    int pheromones;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.pheromones = 1;
    }

    public void updatePheromones() {
        this.pheromones++;
    }

    /**
     * Calculates the travel distance between this coordinate and the given
     * coordinate. The travel distance is the maximum of the absolute differences
     * between the x and y coordinates.
     *
     * @param coordinate the coordinate to calculate the travel distance to
     * @return the travel distance between this coordinate and the given coordinate
     */
    public int getTravelDistance(Coordinate coordinate) {
        int xDistance = Math.abs(this.x - coordinate.x);
        int yDistance = Math.abs(this.y - coordinate.y);
        return Math.max(xDistance, yDistance);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + "), " + this.pheromones;
    }
}
