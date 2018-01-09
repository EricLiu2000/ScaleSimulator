import java.util.Random;

public class Robot {
    private double avgCycleTime, stdDev, thisCycleTime, currentTime;

    //How many cubes this has placed over the match
    private int cubesPlaced;

    //Used for random cycle time calculations
    private Random r;

    /**
     * Constructs this robot with cycle time and standard deviation
     *
     * @param avgCycleTime in seconds
     * @param stdDev in seconds
     */
    public Robot(double avgCycleTime, double stdDev) {
        this.avgCycleTime = avgCycleTime;
        this.stdDev = stdDev;
        this.r = new Random();
        this.thisCycleTime = (r.nextGaussian())*stdDev + avgCycleTime;
        this.cubesPlaced = 0;
        this.currentTime = 0;
    }

    //One cycle will represent one second
    // changeForNotOwning is a boolean representing if you do not have the scale currently
    public void cycle(boolean changeForNotOwning) {
        this.currentTime++;

        //if a cycle is over
        if(this.currentTime >= this.thisCycleTime) {
            //calculate next cube drop time
            this.thisCycleTime+= ((r.nextGaussian())*stdDev + avgCycleTime);
            this.thisCycleTime += (changeForNotOwning ? 2 : -2); // this is kind of hacky because the time is actually added to the previous
                                                                // cycle, but this should be the same.

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
