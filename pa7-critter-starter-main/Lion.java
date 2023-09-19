
/*
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: write-up
 * 
 * the class is represents Lion, it extends Feline.
 * 
 */
import java.awt.Color;

/**
 * the class is represents Lion, it extends Feline.
 */
public class Lion extends Feline {
    // count for fightt won
    private int winCounter;
    // count for movement
    private int moveCount;
    // current direction
    private Direction currDir;

    private static final String LION_NAME = "Lion";
    private static final String REVERSE_LION_NAME = "noiL";
    private static final int TIMES = 5;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;

    /**
     * Default constructor - creates critter with name Lion
     * set direction to east, moveCount and winCount to 0
     */
    public Lion() {
        this.displayName = LION_NAME;
        this.currDir = Direction.EAST;
        this.moveCount = ZERO;
        this.winCounter = ZERO;
    }

    /**
     * Returns whether the critter wants to eat the encountered food or not.
     * 
     * @return eat if lion won a fight
     */
    @Override
    public boolean eat() {
        // check if the lion has won a fight
        if (winCounter != ZERO) {
            winCounter = ZERO;
            return true;
        } else {
            return false;
        }
    }

    /**
     * called to move in a certain direction.
     * 
     * @return the direction
     */
    @Override
    public Direction getMove() {
        // the direction of every 5 move
        int direction = moveCount / TIMES;
        // count for the same direction
        int counter = moveCount % TIMES;

        // determin current direction
        switch (direction) {
            case ZERO:
                moveCount++;
                this.currDir = Direction.EAST;
                break;
            case ONE:
                moveCount++;
                this.currDir = Direction.SOUTH;
                break;
            case TWO:
                moveCount++;
                this.currDir = Direction.WEST;
                break;
            case THREE:
                moveCount++;
                this.currDir = Direction.NORTH;
                break;
            case FOUR:
                moveCount++;
                this.currDir = Direction.NORTH;
                break;
        }

        // if the lion need change direction
        if (direction == FOUR && counter == ZERO) {

            moveCount = ONE;
            this.currDir = Direction.EAST;
        }
        return this.currDir;

    }

    /**
     * called when getting the color of your critter.
     * 
     * @return yellow color
     */
    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    /**
     * called when your animal is put to sleep for eating too much food.
     */
    @Override
    public void sleep() {
        winCounter = ZERO;
        this.displayName = REVERSE_LION_NAME;
    }

    /**
     * called when your animal wakes up from sleeping.
     */
    @Override
    public void wakeup() {
        this.displayName = LION_NAME;
    }

    /**
     * called when you win a fight against another animal.
     */
    @Override
    public void win() {
        winCounter++;
    }

}
