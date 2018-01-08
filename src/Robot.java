import java.util.Random;

public class Robot {
    private double avgCycleTime, stdDev, thisCycleTime, currentTime;

    private int cubesPlaced;

    private Random r;

    /**
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
    public void cycle() {
        this.currentTime++;

        //if a cycle is over
        if(this.currentTime >= this.thisCycleTime) {
            //calculate next cube drop time
            this.thisCycleTime+= ((r.nextGaussian())*stdDev + avgCycleTime);

            //drop a cube
            this.cubesPlaced++;
        }
    }

    public double getCubesPlaced() {
        return cubesPlaced;
    }

    public void reset() {
        this.currentTime = 0;
        this.cubesPlaced = 0;
        this.thisCycleTime = (r.nextGaussian())*stdDev + avgCycleTime;
    }

}
