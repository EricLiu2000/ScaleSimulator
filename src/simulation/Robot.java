package simulation;

import java.util.Random;
import simulation.Main.Color;

public class Robot {
    private double avgCycleTime, stdDev, thisCycleTime, currentTime, scaleAdvantageTime = 1, scaleDisadvantageTime = -4;

    //How many cubes this has placed over the match
    private int cubesPlaced;

    //Used for random cycle time calculations
    private Random r;

    private Color color;

    /**
     * Constructs this robot with min cycle time and standard deviation
     *
     * @param minCycleTime in seconds
     * @param stdDev in seconds
     */
    public Robot(double minCycleTime, double stdDev, Color color) {
        this.avgCycleTime = minCycleTime;
        this.stdDev = stdDev;
        this.r = new Random();
        this.thisCycleTime = (Math.pow(r.nextGaussian(), 2)*stdDev) + minCycleTime;
        this.cubesPlaced = 0;
        this.currentTime = 0;
        this.color = color;
    }

    /**
     *
     * @param scaleColor the current color(ownership) of the scale
     */
    public void cycle(Color scaleColor) {
        this.currentTime++;

        if(scaleColor.equals(Color.NEUTRAL)) {
            checkCycle(0);
        } else if(this.color.equals(scaleColor)) {
            checkCycle(scaleAdvantageTime);
        } else {
            checkCycle(scaleDisadvantageTime);
        }
    }

    /**
     * Checks to see  if the robot has finished a cycle
     *
     * @param scaleAdvantageTime modification to cycle time based on if the scale is in your favor. Positive decreases cycle times, Negative increases cycle times.
     */
    private void checkCycle(double scaleAdvantageTime) {
        //if a cycle is over
        if(this.currentTime - scaleAdvantageTime >= this.thisCycleTime) {
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
