
/*
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: write-up
 * 
 * the class is represents Ocelot, it extends Leopard.
 * 
 */
import java.awt.Color;

/**
 * the class is represents Ocelot, it extends Leopard.
 */
public class Ocelot extends Leopard {

    private static final String OCELOT_NAME = "Oce";
    private static final String LION_NAME = "Lion";
    private static final String SLEEP_LION_NAME = "noiL";
    private static final String FELINE_NAME = "Fe";
    private static final String LEOPARD_NAME = "Lpd";

    /**
     * Default constructor - creates critter with name Lion
     */
    public Ocelot() {
        this.displayName = OCELOT_NAME;
    }

    /**
     * generate attack based on opponent
     * 
     * @param oppenent aginst to
     * @return a attack
     */
    @Override
    protected Attack generateAttack(String opponent) {

        // check if opponent is lion or feline or leopard
        if (opponent.equals(LION_NAME) ||
                opponent.equals(FELINE_NAME) ||
                opponent.equals(LEOPARD_NAME) ||
                opponent.equals(SLEEP_LION_NAME)) {
            return Attack.SCRATCH;
        } else {
            return Attack.POUNCE;
        }

    }

    /**
     * called when getting the color of your critter.
     * 
     * @return light gray color
     */
    @Override
    public Color getColor() {

        return Color.LIGHT_GRAY;
    }

}
