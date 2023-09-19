/*
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: write-up
 * 
 * the class is represents Elephant, it extends Critter.
 * 
 */

import java.awt.Color;

/**
 * the class is represents Elephant, it extends Critter.
 */
public class Elephant extends Critter {

    protected static int goalX;
    protected static int goalY;
    private static final String ELEPHANT_NAME = "El";
    private static final int ZORE = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
   

    /**
     * Default constructor - creates critter with name El
     * and set goalX and goalY to 0
     */
    public Elephant() {
        super(ELEPHANT_NAME);
        goalX = ZORE;
        goalY = ZORE;

    }

    /**
     * Returns whether the critter wants to eat the encountered food or not.
     * 
     * @return eat always
     */
    @Override
    public boolean eat() {

        return true;
    }

    /**
     * called when getting the color of your critter.
     * 
     * @return gray color
     */
    @Override
    public Color getColor() {

        return Color.GRAY;
    }

    /**
     * called when your critter mates with another critter.
     */
    @Override
    public void mate() {

        this.level += TWO;
    }

    /**
     * called when the goalX and goalY is reset.
     */
    @Override
    public void reset() {

        goalX = ZORE;
        goalY = ZORE;
    }

    /**
     * called to move in a certain direction.
     * 
     * @return the direction
     */
    @Override
    public Direction getMove() {

        // get current cordinate
        int currentX = this.info.getX();
        int currentY = this.info.getY();

        // check if the critter at shared cordinate
        if (currentX == goalX && currentY == goalY) {
            int width = this.info.getWidth();
            int height = this.info.getHeight();
            goalX = randomCordinate(width);
            goalY = randomCordinate(height);
        }

        Direction direX = null;
        Direction direY = null;
        // get distance form shared cordinate to current cordinate
        int distanceX = goalX - currentX;
        int distanceY = goalY - currentY;
        // determint which way to move for x-axis
        if (distanceX < ZORE) {
            direX = Direction.WEST;
            distanceX *= -ONE;
        } else {
            direX = Direction.EAST;
        }

        // determine which way to move for y-axis
        if (distanceY < ZORE) {
            direY = Direction.NORTH;
            distanceY *= -ONE;
        } else {
            direY = Direction.SOUTH;
        }

        // determine move on x-axis or y-axis
        if (distanceX >= distanceY) {
            return direX;
        } else {
            return direY;
        }
    }

    /**
     * generate a random cordinate for axis
     * 
     * @param bundary the length of the axis
     * @return a random cordinate
     */
    private int randomCordinate(int bundary) {

        int coordinate = random.nextInt(bundary);

        return coordinate;
    }

}
