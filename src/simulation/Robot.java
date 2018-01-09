package simulation;

import java.util.Random;

public class Robot {
    private double avgCycleTime, stdDev, thisCycleTime, currentTime;

    //How many cubes this has placed over the match
    private int cubesPlaced;

    //Used for random cycle time calculations
    private Random r;

    /**
     * Constructs this robot with min cycle time and standard deviation
     *
     * @param minCycleTime in seconds
     * @param stdDev in seconds
     */
    public Robot(double minCycleTime, double stdDev) {
        this.avgCycleTime = minCycleTime;
        this.stdDev = stdDev;
        this.r = new Random();
        this.thisCycleTime = (Math.pow(r.nextGaussian(), 2)*stdDev) + minCycleTime;
        this.cubesPlaced = 0;
        this.currentTime = 0;
    }

    /**
     * Simulates one second of the match
     *
     * @param scaleAdvantageTime modification to cycle time based on if the scale is in your favor. Positive decreases cycle times, Negative increases cycle times.
     */
    public void cycle(double scaleAdvantageTime) {
        this.currentTime++;

        //if a cycle is over
        if(this.currentTime + scaleAdvantageTime >= this.thisCycleTime) {
            //calculate next cube drop time
            this.thisCycleTime += ((r.nextGaussian()) * stdDev + avgCycleTime);

            //drop a cube
            this.cubesPlaced++;
        }
    }

    //Gets the cubes placed during this match
    public double getCubesPlaced() {
        return cubesPlaced;
    }

    //Used for resetting at the beginning of each match
    public void reset() {
        this.currentTime = 0;
        this.cubesPlaced = 0;
        this.thisCycleTime = (r.nextGaussian())*stdDev + avgCycleTime;
    }
}
