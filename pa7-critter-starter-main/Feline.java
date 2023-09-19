/*
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: write-up
 * 
 * the class is represents Feline, it extends Critter.
 * 
 */
public class Feline extends Critter {
    // counter for getMove method before random direction
    private int moveCount;
    // counter for eating
    private int eatCount;
    // current direction
    private Direction currDir;
    private static final String FELINE_NAME = "Fe";
    private static final int FOUR = 4;
    private static final int ZORE = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FIVE = 5;

    /**
     * Default constructor - creates critter with name Fe
     * and set eatCount to 1 and moveCount to 0
     */
    public Feline() {
        super(FELINE_NAME);
        eatCount = 1;
        moveCount = 0;

    }

    /**
     * return the direction for feline
     * 
     * @return the direction for feline
     */
    @Override
    public Direction getMove() {
        // get a random number from four numbers
        int move = this.random.nextInt(FOUR);
        Direction newDirection = null;
        // 25% chance to get one number from 4 numbers
        switch (move) {
            case ZORE:
                newDirection = Direction.EAST;
                break;
            case ONE:
                newDirection = Direction.WEST;
                break;
            case TWO:
                newDirection = Direction.NORTH;
                break;
            case THREE:
                newDirection = Direction.SOUTH;
                break;
        }
        // check if it is the first move
        if (this.currDir == null) {
            moveCount++;
            this.currDir = newDirection;
            return newDirection;
        } else {
            // check if feline complete 5 times move
            if (moveCount != FIVE) {
                moveCount++;
                return this.currDir;
            } else {
                moveCount = ONE;
                this.currDir = newDirection;
                return newDirection;
            }
        }
    }

    /**
     * return result of felline eat food
     * 
     * @return true for feline eat food
     */
    @Override
    public boolean eat() {
        // check if it is the 3rd time encounter the food
        if (eatCount == THREE) {
            eatCount = ONE;
            return true;
        } else {
            eatCount++;
            return false;
        }
    }

    /**
     * return the attack for feline
     * 
     * @return POUNCE
     */
    @Override
    public Attack getAttack(String opponent) {

        return Attack.POUNCE;
    }
}