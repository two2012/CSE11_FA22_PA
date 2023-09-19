
/*
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: write-up
 * 
 * the class is represents Turtle, it extends Critter.
 * 
 */
import java.awt.Color;

/**
 * the class is represents Turtle, it extends Critter.
 */
public class Turtle extends Critter {

    private static final String TURTLE_NAME = "Tu";
    private static final String FOOD = ".";
    private static final String EMPTY = " ";
    private static final int ZERO = 0;
    private static final int TWO = 2;

    /**
     * Default constructor - creates critter with name Tu
     */
    public Turtle() {
        super(TURTLE_NAME);

    }

    /**
     * Returns the move direction of the turtle
     * 
     * @return direction west
     */
    @Override
    public Direction getMove() {

        return Direction.WEST;
    }

    /**
     * Returns the color of the turtle
     * 
     * @return Color green
     */
    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    /**
     * return result of turtle eat food
     * 
     * @return true for turtle eat food
     */
    @Override
    public boolean eat() {
        // get neighor from east, north, west and south
        String east = this.info.getNeighbor(Direction.EAST);
        String north = this.info.getNeighbor(Direction.NORTH);
        String west = this.info.getNeighbor(Direction.WEST);
        String south = this.info.getNeighbor(Direction.SOUTH);
        // if hostile animals from four direction
        boolean eastB = east.equals(EMPTY) ||
                east.equals(FOOD) ||
                east.equals(TURTLE_NAME);
        boolean northB = north.equals(EMPTY) ||
                north.equals(FOOD) ||
                north.equals(TURTLE_NAME);
        boolean westB = west.equals(EMPTY) ||
                west.equals(FOOD) ||
                west.equals(TURTLE_NAME);
        boolean southB = south.equals(EMPTY) ||
                south.equals(FOOD) ||
                south.equals(TURTLE_NAME);

        // check if hostile animals
        if (eastB && northB && westB && southB) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * return the attack for turtle
     * 
     * @return 50% chance return ROAR
     *         and 50% chance return FORFEIT
     */
    @Override
    public Attack getAttack(String opponent) {

        // get a random number from two numbers
        int chance = this.random.nextInt(TWO);

        // 50% chance get 0
        if (chance == ZERO) {
            return super.getAttack(opponent);
        } else {
            return Attack.ROAR;
        }

    }

}
