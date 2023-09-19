
/*
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: write-up
 * 
 * the class is represents starfish, it extends Critter.
 * 
 */
import java.awt.Color;

/**
 * the class is represents starfish, it extends Critter.
 */
public class Starfish extends Critter {
    private static final String STARFISH_NAME = "Patrick";

    /**
     * Default constructor - creates critter with name Patrick
     */
    public Starfish() {
        super(STARFISH_NAME);
    }

    /**
     * Returns the move direction of the Starfish
     * 
     * @return direction center
     */
    @Override
    public Direction getMove() {

        return Direction.CENTER;
    }

    /**
     * Returns the color of the Starfish
     * 
     * @return Color pink
     */
    @Override
    public Color getColor() {
        return Color.PINK;
    }
}