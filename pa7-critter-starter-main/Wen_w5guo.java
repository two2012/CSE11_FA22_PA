import java.awt.Color;

/*
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: write-up
 * 
 * the class is represents my own critter, it extends ocelot.
 * 
 */

public class Wen_w5guo extends Ocelot {

    private static final String NAME = "O";
    private static final int TEN = 10;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int ZERO = 0;
    private static final int WIDTH = 60;
    private static final int HEIGHT = 50;
    private static final int PROBABILTY = 8;
    // flage for mate
    private boolean isMate = false;
    //four mate point in four quadrant
    private static int matePoint1X;
    private static int matePoint1Y; 
    private static int matePoint2X;
    private static int matePoint2Y;
    private static int matePoint3X;
    private static int matePoint3Y;
    private static int matePoint4X;
    private static int matePoint4Y;
    // center cordinate
    private static int centerX; 
    private static int centerY;
    

    public Wen_w5guo(){
        this.displayName = NAME;
        // get center cordinate
        centerX = WIDTH / TWO; 
        centerY = HEIGHT / TWO; 
        // set four mate point in four quadrant
        matePoint1X = randomCordinate(ZERO, centerX);
        matePoint1Y = randomCordinate(ZERO, centerY);
        matePoint2X = randomCordinate(centerX, HEIGHT);
        matePoint2Y = randomCordinate(ZERO, centerY);
        matePoint3X = randomCordinate(centerX, HEIGHT);
        matePoint3Y = randomCordinate(centerY, WIDTH);
        matePoint4X = randomCordinate(ZERO, centerX);
        matePoint4Y = randomCordinate(centerY, WIDTH);
        
    }

      /**
     * called when getting the color of critter.
     * @return red orange
     */
    @Override
    public Color getColor() {
        
        return Color.ORANGE;
    }

     /**
     * Returns the type of attack that the fighting critter uses.  
     * @param opponent the encountered critter
     * @return a attack
     */
    @Override
    public Attack getAttack(String opponent) {
        // int array has 10 element which 10% chance to get one element
        int chance[] = new int[TEN];
        // add element to array based on value of confidence
        for (int i = 1; i <= chance.length; i++){
            if (i <= PROBABILTY) {
                chance[i-1] = ONE;
            } else {
                chance[i-1] = ZERO;
            }
        }
        // get a random number form 10 numbers
        int rIndex =  this.random.nextInt(TEN);
        // check if the element from random index is 1        
        if (chance[rIndex] == ONE){
            return Attack.POUNCE;
        } else {
            return randomAttack();
        }
        
    }

    
     /**
     * called to move in a certain direction.
     * @return the direction
     */
    @Override
    public Direction getMove() {
        // check if orange is mate
        if (isMate){
            return super.getMove();
        } else {
            // get orange's current cordinate
            int currentX = info.getX(); 
            int currentY = info.getY(); 
            // check if orange in 1st quadrant
            if (currentX <= currentX && currentY <= currentY){
                
                return Move(currentX, currentY, matePoint1X, matePoint1Y);
            }
            // check if orange in 2nd quadrant 
            else if (currentX > centerX && currentY <= centerY){
                return Move(currentX, currentY, matePoint2X, matePoint2Y);
            } 
            // check if orange in 3rd quadrant
            else if (currentX >= currentX && currentY > centerY){
                return Move(currentX, currentY, matePoint3X, matePoint3Y);
            } 
            // orange in 4th quadrant
            else {
                return Move(currentX, currentY, matePoint4X, matePoint4Y);
            }

        }  
    }

    /**
     * called when critter mates with another critter.
     */
    @Override
    public void mate() {
        isMate = true;
    }

    /**
     * called when critter encounter the food.
     */
    @Override
    public boolean eat(){
        return true;

    }

   
     /**
      * generate a random cordinate for axis from a range
      * @param min min for the range
      * @param max max for the rage
      * @return a random cordinate
      */
    private int randomCordinate(int min, int max){
        // get a random number from range min to max
        int coordinate = random.nextInt(max - min) + min;

        return coordinate;
    }

    /**
     * return a direction based on current cordinate and 
     * goal cordinate
     * @param cX current x cordinate
     * @param cY current y cordinate
     * @param gX goal x cordinate
     * @param gY goal y cordinate
     * @return return a direction
     */
    private Direction Move(int cX, int cY, int gX, int gY){

        Direction direX = null;
        Direction direY = null;
        // distance from goal cordinate to current cordinate
        int dX = gX - cX;
        int dY = gY - cY;

        // determint which way to move for x-axis
        if (dX < 0 ){
            direX = Direction.WEST;
            dX *= -1;
        } else {
            direX = Direction.EAST;
        }

        // determine which way to move for y-axis
        if (dY < 0 ){
            direY = Direction.NORTH;
            dY *= -1;
        } else {
            direY = Direction.SOUTH;
        }

        // determine move on x-axis or y-axis
        if (dX >= dY){
            return direX;
        } else {
            return direY;
        }
    }

    /**
     * return a random attack from roar and scratch
     * @return  a random attack
     */
    private Attack randomAttack(){
        // get a random number from two numbers
        int chance = random.nextInt(TWO);
        // choose scratch if random number is 0
        if (chance == ZERO){
            return Attack.SCRATCH;
        } else {
            return Attack.ROAR;
        }
    }


    
    
}
