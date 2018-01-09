import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        /**
         * ROBOT STATS
         */
        Robot blue1 = new Robot(25, 0);
        Robot blue2 = new Robot(9999, 0);
        Robot blue3 = new Robot(9999, 0);

        Robot red1 = new Robot(50, 0);
        Robot red2 = new Robot(9999, 0);
        Robot red3 = new Robot(9999, 0);

        /**
         * SIMULATION STATS
         * 105 teleop no endgame, 135 teleop
         */

        int secondsPerMatch = 105;
        int matchesToSimulate = 500;
        Robot[] blueAlliance = new Robot[] {blue1, blue2, blue3};
        Robot[] redAlliance = new Robot[] {red1, red2, red3};

        SummaryStatistics finalBluePoints = new SummaryStatistics();
        SummaryStatistics finalRedPoints = new SummaryStatistics();
        SummaryStatistics blueWinningAmount = new SummaryStatistics();
        SummaryStatistics redWinningAmount = new SummaryStatistics();
        SummaryStatistics contestedTime = new SummaryStatistics();

        //Run multiple matches
        for(int i = 0; i < matchesToSimulate; i++) {
            //Run each match
            int bluePoints = 0, redPoints = 0, contestedMatchTime = 0;

            for(Robot robot : blueAlliance) {
                robot.reset();
            }

            for(Robot robot : redAlliance) {
                robot.reset();
            }

            for(int j = 1; j < secondsPerMatch; j++) {

                //Update blue robots
                for(Robot robot : blueAlliance) {
                    robot.cycle();
                }

                //Update red robots
                for(Robot robot : redAlliance) {
                    robot.cycle();
                }

                //Calculate blue cubes at the end of this second
                int blueCubes = 0;
                for(Robot robot : blueAlliance) {
                    blueCubes+=robot.getCubesPlaced();
                }

                //Calculate red cubes at the end of this second
                int redCubes = 0;
                for(Robot robot : redAlliance) {
                    redCubes+=robot.getCubesPlaced();
                }

                //Increment points each second
                if(blueCubes > redCubes) {
                    bluePoints++;
                } else if(redCubes > blueCubes) {
                    redPoints++;
                } else {
                    //assuming neutral means contested, with the exception of the first period
                    if(bluePoints!=0 || redPoints!=0) {
                        contestedMatchTime++;
                    }
                }
            }

            //Store outcome of match
            finalBluePoints.addValue(bluePoints);
            finalRedPoints.addValue(redPoints);

            if(bluePoints > redPoints) {
                blueWinningAmount.addValue(bluePoints-redPoints);
            } else if(redPoints > bluePoints) {
                redWinningAmount.addValue(redPoints-bluePoints);
            }

            contestedTime.addValue(contestedMatchTime);

        }

        //How many matches were won by who
        System.out.println("Out of " + Integer.toString(matchesToSimulate) + " matches, the blue alliance won " + Integer.toString((int) blueWinningAmount.getN())
                + " matches, the red alliance won "+ Integer.toString((int) redWinningAmount.getN()) + " matches, and "
                + Integer.toString((int) (matchesToSimulate-blueWinningAmount.getN()-redWinningAmount.getN())) + " matches were tied.");

        if(blueWinningAmount.getN() > 0) {
            System.out.println("In their winning matches, the blue alliance won by an average of " + Integer.toString((int) blueWinningAmount.getMean()) + " points with a standard deviation of "
                    + Integer.toString((int) blueWinningAmount.getStandardDeviation()) + " points.");
        }

        if(redWinningAmount.getN() > 0) {
            System.out.println("In their winning matches, the red alliance won by an average of " + Integer.toString((int) redWinningAmount.getMean()) + " points with a standard deviation of "
                    + Integer.toString((int) redWinningAmount.getStandardDeviation()) + " points.");
        }

        System.out.println("On average, the scale was contested(neutral) for " + Integer.toString((int) contestedTime.getMean())
                + " seconds, with a standard deviation of " + Integer.toString((int) contestedTime.getStandardDeviation()) + " seconds.");
    }

}
