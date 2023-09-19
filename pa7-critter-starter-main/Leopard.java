
/*
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: write-up
 * 
 * the class is represents Leopard, it extends Feline.
 * 
 */
import java.awt.Color;
import java.util.ArrayList;

/**
 * the class is represents Leopard, it extends Feline.
 */
public class Leopard extends Feline {
    protected static int confidence = 0;
    private static final String LEOPARD_NAME = "Lpd";
    private static final String STARFISH_NAME = "Patrick";
    private static final String TURTLE_NAME = "Tu";
    private static final String FOOD = ".";
    private static final int TEN = 10;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;

    /**
     * Default constructor - creates critter with name Lpd
     */
    public Leopard() {
        this.displayName = LEOPARD_NAME;
    }

    /**
     * Returns whether the critter wants to eat the encountered food or not.
     * 
     * @return (confident * 10)% eat
     */
    @Override
    public boolean eat() {
        // int array has 10 element which 10% chance to get one element
        int chance[] = new int[TEN];
        // add element to array based on value of confidence
        for (int i = 1; i <= chance.length; i++) {
            if (i <= confidence) {
                chance[i - ONE] = ONE;
            } else {
                chance[i - ONE] = ZERO;
            }
        }
        // get a random number form 10 numbers
        int rIndex = this.random.nextInt(TEN);
        if (chance[rIndex] == ONE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the type of attack that the fighting critter uses.
     * 
     * @param opponent the encountered critter
     * @return a attack
     */
    @Override
    public Attack getAttack(String opponent) {

        // check if get pounce
        if (opponent.equals(TURTLE_NAME) || confidence > FIVE) {
            return Attack.POUNCE;

        } else {
            // return a random attack
            return generateAttack(opponent);
        }
    }

    /**
     * called to move in a certain direction.
     * 
     * @return the direction
     */
    @Override
    public Direction getMove() {
        // get neighor from four direction
        String northNeighbor = this.info.getNeighbor(Direction.NORTH);
        String southNeighbor = this.info.getNeighbor(Direction.SOUTH);
        String eastNeighbor = this.info.getNeighbor(Direction.EAST);
        String westNeighbor = this.info.getNeighbor(Direction.WEST);
        // String array store four direction neigbor
        String neighbors[] = { northNeighbor, southNeighbor, eastNeighbor, westNeighbor };
        // Interger arrayList to store directions for food or starfish
        ArrayList<Integer> directions = new ArrayList<>();

        // loop beigbors
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i].equals(FOOD) ||
                    neighbors[i].equals(STARFISH_NAME)) {
                directions.add(i);
            }
        }

        // check if directions has size 0
        if (directions.size() == 0) {
            // return a random direction
            return getRandomDirection();
        } else {
            // return direction of food or starfish
            int direction = directions.get(ZERO);
            Direction firsDirection = null;

            // find first direction has food or starfish
            switch (direction) {
                case ZERO:
                    firsDirection = Direction.NORTH;
                    break;
                case ONE:
                    firsDirection = Direction.SOUTH;
                    break;
                case TWO:
                    firsDirection = Direction.EAST;
                    break;
                case THREE:
                    firsDirection = Direction.WEST;
                    break;
            }
            return firsDirection;
        }
    }

    /**
     * called when getting the color of your critter.
     * 
     * @return red color
     */
    @Override
    public Color getColor() {

        return Color.RED;
    }

    /**
     * called when you lose a fight against another animal, and die.
     */
    @Override
    public void lose() {
        if (confidence > ZERO) {
            confidence--;
        }
    }

    /**
     * rest the confidence to 0 when call
     */
    @Override
    public void reset() {

        confidence = ZERO;
    }

    /**
     * called when you win a fight against another animal.
     */
    @Override
    public void win() {

        if (confidence < TEN) {
            confidence++;
        }
    }

    /**
     * return a random Direction
     * 
     * @return random Direction
     */
    private Direction getRandomDirection() {
        // get a random number from four numbers
        int random = this.random.nextInt(FOUR);
        Direction rDirection = null;

        // find random direction
        switch (random) {
            case ZERO:
                rDirection = Direction.NORTH;
                break;
            case ONE:
                rDirection = Direction.SOUTH;
                break;
            case TWO:
                rDirection = Direction.EAST;
                break;
            case THREE:
                rDirection = Direction.WEST;
                break;
        }
        return rDirection;
    }

    /**
     * generate attack based on opponent
     * 
     * @param oppenent aginst to
     * @return a attack
     */
    protected Attack generateAttack(String opponent) {
        // check if the opponent is starfish
        if (opponent.equals(STARFISH_NAME)) {
            return Attack.FORFEIT;
        } else {
            int random = this.random.nextInt(THREE);
            Attack rAttack = null;
            // get random attack
            switch (random) {
                case ZERO:
                    rAttack = Attack.POUNCE;
                    break;
                case ONE:
                    rAttack = Attack.SCRATCH;
                    break;
                case TWO:
                    rAttack = Attack.ROAR;
                    break;
            }
            return rAttack;
        }
    }

}
