package simulation;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;

public class Robot {
    private double minCycleTime, variance, thisCycleTime, currentTime;

    //How many cubes this has placed over the match
    private int cubesPlaced;

    //Used for random cycle time calculations
    private ChiSquaredDistribution chi = new ChiSquaredDistribution(1);

    /**
     * Constructs this robot with min cycle time and standard deviation
     *
     * @param minCycleTime in seconds
     * @param variance in seconds
     */
    public Robot(double minCycleTime, double variance) {
        this.minCycleTime = minCycleTime;
        this.variance = variance;

        this.thisCycleTime = getNextCycleTime();
        this.cubesPlaced = 0;
        this.currentTime = 0;
    }

    /**
     * Gets the time it takes the next cycle to finish
     *
     * @return the time it will take for the next cycle to finish
     */
    private double getNextCycleTime() {
        return minCycleTime + chi.sample()*variance/chi.getNumericalVariance();
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
            this.thisCycleTime += getNextCycleTime();

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
        this.thisCycleTime = getNextCycleTime();
    }
}
