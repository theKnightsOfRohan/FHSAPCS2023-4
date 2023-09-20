package ProblemSets.W4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import GeneralHelpers.Annotations.HelperMethod;
import GeneralHelpers.Annotations.RunnableMethod;

public class D3 {
    public static void main(String[] args) {
        int roadLength = 20;
        int mixStepNum = 1000;
        int numCars = 5;
        int maxVelocity = 5;
        double prob = 1.0 / 3;
        int simSteps = 10;

        Car[] simulation = burnSimulation(roadLength, numCars, maxVelocity, prob, mixStepNum);

        // System.out.println("Total distance traveled: " + runSimulation(simulation,
        // simSteps));
    }

    /*
     * A length of road is divided into m equally sized zones, each of which can
     * hold one vehicle. There are n vehicles on the road (with m > n). For example,
     * you could have 100 cars on a road with 1000 spaces. Time moves forward in
     * discrete steps. A vehicle with velocity v moves forward by v zones in each
     * time step. There is a maximum speed of vMax which no vehicle can go faster
     * than. In the simplest configuration, the road is a circular loop. (In an
     * implementation, you could use an array and when a car goes off the end of the
     * array you “loop” it back to the beginning, mimicking the behavior of a closed
     * loop).
     * 
     * The rules for Nagel-Schreckenberg traffic are as follows. At each stage of
     * the simulation, every car goes through the following four steps.
     * 
     * 1. if its velocity is below vmax, it increases its velocity by one unit. The
     * drivers are eager to move ahead.
     * 
     * 2. it checks the distance to the car in front of it. If that distance is d
     * spaces and the car has velocity v > d then it reduces its velocity to d − 1
     * in order to avoid collision.
     * 
     * 3. if the velocity is positive then with probability p it reduces velocity by
     * 1 unit. This is the key step which models random driver behavior.
     * 
     * 4. the car moves ahead by v units to complete the stage. These four steps
     * take place in parallel for all N vehicles
     */

    @RunnableMethod
    public static Car[] burnSimulation(int roadLength, int numCars, int maxVelocity, double prob, int mixStepNum) {
        Car[] road = new Car[roadLength];
        for (int i = 0; i < numCars; i++) {
            int position = roadLength * i / numCars;

            road[position] = new Car(0, maxVelocity, position, prob);
        }

        printRoad(road);
        runSimulation(road, mixStepNum);
        System.out.println("Burned in ---------------");
        return road;
    }

    @RunnableMethod
    public static int runSimulation(Car[] simulation, int steps) {
        int totalDistanceTraveled = 0;
        Car[] workingSimulation = Arrays.copyOf(simulation, simulation.length);
        for (int i = 0; i < steps; i++) {
            for (int j = 0; j < workingSimulation.length; j++) {
                Car car = workingSimulation[j];
                if (car != null) {
                    totalDistanceTraveled = getNewPosition(totalDistanceTraveled, workingSimulation, car);
                }
            }
            printRoad(workingSimulation);
        }

        return totalDistanceTraveled;
    }

    @HelperMethod
    private static int getNewPosition(int totalDistanceTraveled, Car[] workingSimulation, Car car) {
        int oldPosition = car.position;
        car.step(workingSimulation);
        int newPosition = car.position;
        workingSimulation[oldPosition] = null;
        workingSimulation[newPosition] = car;

        if (newPosition > oldPosition)
            totalDistanceTraveled += newPosition - oldPosition;
        else
            totalDistanceTraveled += newPosition + workingSimulation.length - oldPosition;
        return totalDistanceTraveled;
    }

    @HelperMethod
    public static void printRoad(Car[] road) {
        for (Car car : road) {
            if (car == null) {
                System.out.print("_");
            } else {
                System.out.print(car);
            }
        }

        System.out.println();
    }
}

class Car {
    int velocity, maxVelocity, position;
    double prob;

    public Car(int velocity, int maxVelocity, int position, double prob) {
        this.velocity = velocity;
        this.maxVelocity = maxVelocity;
        this.position = position;
        this.prob = prob;
    }

    public void step(Car[] road) {
        if (velocity < maxVelocity)
            velocity++;

        int distanceToNextCar = distanceToNextCar(this, road);
        if (distanceToNextCar < velocity)
            velocity = distanceToNextCar;

        if (velocity > 0 && Math.random() < prob)
            velocity--;

        position += velocity;
        position %= road.length;
    }

    private int distanceToNextCar(Car car, Car[] road) {
        int distance = 0;
        for (int i = car.position + 1; i < road.length; i++) {
            if (road[i] != null)
                return distance;
            distance++;
        }

        for (int i = 0; i < car.position; i++) {
            if (road[i] != null)
                return distance;
            distance++;
        }

        return distance;
    }

    public String toString() {
        return "*";
    }
}