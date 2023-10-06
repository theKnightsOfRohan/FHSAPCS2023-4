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

    // Returns number of squares an ant would have to travel to get to this
    // coordinate
    // from the given coordinate, including diagonals.
    public int getTravelDistance(Coordinate coordinate) {
        int xDistance = Math.abs(this.x - coordinate.x);
        int yDistance = Math.abs(this.y - coordinate.y);
        return Math.max(xDistance, yDistance);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + "), " + this.pheromones;
    }
}
