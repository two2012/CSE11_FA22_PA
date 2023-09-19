import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



/**
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: write-up
 * 
 * this class is for edit image, it will implement a simple image editor that 
 * allows users to edit an image by rotating, lowering resolution, and also 
 * patching a smaller image onto it.
 */
public class ImageEditor {
    /* Constants (Magic numbers) */
    private static final String PNG_FORMAT = "png";
    private static final String NON_RGB_WARNING = "Warning: we do not support the image you provided. \n" +
            "Please change another image and try again.";
    private static final String RGB_TEMPLATE = "(%3d, %3d, %3d) ";
    private static final int BLUE_BYTE_SHIFT = 0;
    private static final int GREEN_BYTE_SHIFT = 8;
    private static final int RED_BYTE_SHIFT = 16;
    private static final int ALPHA_BYTE_SHIFT = 24;
    private static final int BLUE_BYTE_MASK = 0xff << BLUE_BYTE_SHIFT;
    private static final int GREEN_BYTE_MASK = 0xff << GREEN_BYTE_SHIFT;
    private static final int RED_BYTE_MASK = 0xff << RED_BYTE_SHIFT;
    private static final int ALPHA_BYTE_MASK = ~(0xff << ALPHA_BYTE_SHIFT);
    private static final int RIGHTANGLE = 90;
    

    /* Static variables - DO NOT add any additional static variables */
    static int[][] image;


    /**
     * Open an image from disk and return a 2D array of its pixels.
     * Use 'load' if you need to load the image into 'image' 2D array instead
     * of returning the array.
     *
     * @param pathname path and name to the file, e.g. "input.png",
     *                 "D:\\Folder\\ucsd.png" (for Windows), or
     *                 "/User/username/Desktop/my_photo.png" (for Linux/macOS).
     *                 Do NOT use "~/Desktop/xxx.png" (not supported in Java).
     * @return 2D array storing the rgb value of each pixel in the image
     * @throws IOException when file cannot be found or read
     */
    public static int[][] open(String pathname) throws IOException {
        BufferedImage data = ImageIO.read(new File(pathname));
        if (data.getType() != BufferedImage.TYPE_3BYTE_BGR &&
                data.getType() != BufferedImage.TYPE_4BYTE_ABGR) {
            System.err.println(NON_RGB_WARNING);
        }
        int[][] array = new int[data.getHeight()][data.getWidth()];

        for (int row = 0; row < data.getHeight(); row++) {
            for (int column = 0; column < data.getWidth(); column++) {
                /*
                 * Images are stored by column major
                 * i.e. (2, 10) is the pixel on the column 2 and row 10
                 * However, in class, arrays are in row major
                 * i.e. [2][10] is the 11th element on the 2nd row
                 * So we reverse the order of i and j when we load the image.
                 */
                array[row][column] = data.getRGB(column, row) & ALPHA_BYTE_MASK;
            }
        }

        return array;
    }

    /**
     * Load an image from disk to the 'image' 2D array.
     *
     * @param pathname path and name to the file, see open for examples.
     * @throws IOException when file cannot be found or read
     */
    public static void load(String pathname) throws IOException {
        image = open(pathname);
    }

    /**
     * Save the 2D image array to a PNG file on the disk.
     *
     * @param pathname path and name for the file. Should be different from
     *                 the input file. See load for examples.
     * @throws IOException when file cannot be found or written
     */
    public static void save(String pathname) throws IOException {
        BufferedImage data = new BufferedImage(
                image[0].length, image.length, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < data.getHeight(); row++) {
            for (int column = 0; column < data.getWidth(); column++) {
                // reverse it back when we write the image
                data.setRGB(column, row, image[row][column]);
            }
        }
        ImageIO.write(data, PNG_FORMAT, new File(pathname));
    }

    /**
     * Unpack red byte from a packed RGB int
     *
     * @param rgb RGB packed int
     * @return red value in that packed pixel, 0 <= red <= 255
     */
    private static int unpackRedByte(int rgb) {
        return (rgb & RED_BYTE_MASK) >> RED_BYTE_SHIFT;
    }

    /**
     * Unpack green byte from a packed RGB int
     *
     * @param rgb RGB packed int
     * @return green value in that packed pixel, 0 <= green <= 255
     */
    private static int unpackGreenByte(int rgb) {
        return (rgb & GREEN_BYTE_MASK) >> GREEN_BYTE_SHIFT;
    }

    /**
     * Unpack blue byte from a packed RGB int
     *
     * @param rgb RGB packed int
     * @return blue value in that packed pixel, 0 <= blue <= 255
     */
    private static int unpackBlueByte(int rgb) {
        return (rgb & BLUE_BYTE_MASK) >> BLUE_BYTE_SHIFT;
    }

    /**
     * Pack RGB bytes back to an int in the format of
     * [byte0: unused][byte1: red][byte2: green][byte3: blue]
     *
     * @param red   red byte, must satisfy 0 <= red <= 255
     * @param green green byte, must satisfy 0 <= green <= 255
     * @param blue  blue byte, must satisfy 0 <= blue <= 255
     * @return packed int to represent a pixel
     */
    private static int packInt(int red, int green, int blue) {
        return (red << RED_BYTE_SHIFT)
                + (green << GREEN_BYTE_SHIFT)
                + (blue << BLUE_BYTE_SHIFT);
    }

    /**
     * Print the current image 2D array in (red, green, blue) format.
     * Each line represents a row in the image.
     */
    public static void printImage() {
        for (int[] ints : image) {
            for (int pixel : ints) {
                System.out.printf(
                        RGB_TEMPLATE,
                        unpackRedByte(pixel),
                        unpackGreenByte(pixel),
                        unpackBlueByte(pixel));
            }
            System.out.println();
        }
    }

    /**
     * Rotate the image by degree degrees clockwise.
     * @param degree degree want to rotat
     */
    public static void rotate(int degree){
        // check if the degree less than 0 and
        // it is not evenly divisible by 90.
        if (degree < 0 || degree % RIGHTANGLE != 0){
            return;
        } else {
            // check if the times of rotate divisible by 4
            if ((degree / RIGHTANGLE) % 4 == 0 ){
                return;
            }
        }
        
        // store times of rotate one time is rotate 90 degree
        int rotateTimes = degree / RIGHTANGLE;

        // loop for rotate times
        for (int count = 0; count < rotateTimes; ++count){

            int row = image[0].length;
            int col = image.length;

            int[][] rotatedImage = new int[row][col];
            // loop for old image rows
            for (int i = 0; i < col; ++i){
                // loop for old image colums
                for (int j = 0; j < row; ++j){
                    rotatedImage[j][col-1-i] = image[i][j];
                }
            }
            image = rotatedImage;
        }

    }
    
    /**
     * To downsample the image (lower the resolution of the image)
     * @param heightScale down-scaling factor for the height of the image
     * @param widthScale down-scaling factor for the width of the image
     */
    public static void downSample(int heightScale, int widthScale) {

        // check if heightScale or widthScale is less than 1 
        //or greater than the height or width respectively
        

        if (heightScale < 1 ||
                widthScale < 1 ||
                heightScale > image.length ||
                widthScale > image[0].length) {
            return;
        }

        // check if heightScale or widthScale is NOT a multiplication 
        // factor of the height or width respectively
        if (image.length % heightScale != 0 ||
                image[0].length % widthScale != 0) {
            return;
        }

        int red = 0;
        int green = 0;
        int blue = 0;
        int color = 0;

        // calculate and store new image size
        int newRow = image.length / heightScale;
        int newCol = image[0].length / widthScale;

        int[][] downImage = new int[newRow][newCol];

        // record row and colum for downImage
        int rowCount = 0;
        int colCount = 0;

        // loop for image by row
        for (int row = 0; row < image.length; row += heightScale) {

            // loop for image by colum
            for (int col = 0; col < image[0].length; col += widthScale) {

                // reset color value
                red = 0;
                green = 0;
                blue = 0;

                // loop for downImage by row
                for (int i = 0; i < heightScale; ++i) {
                    // loop for downImage by colum
                    for (int j = 0; j < widthScale; ++j) {
                        red += unpackRedByte(image[i + row][j + col]);
                        green += unpackGreenByte(image[i + row][j + col]);
                        blue += unpackBlueByte(image[i + row][j + col]);
                    }
                }

                // get avg color
                red /= widthScale * heightScale;
                green /= widthScale * heightScale;
                blue /= widthScale * heightScale;

                color = packInt(red, green, blue);
                downImage[rowCount][colCount] = color;

                colCount++;
            }
            rowCount++;
            // reset the colum 
            colCount = 0;

        }

        image = downImage;
    }

    /**
     * Replace a certain part of image with patchImage, 
     * starting from position [startRow][startColumn].
     * @param startRow row start to patch
     * @param startColumn column start to patch
     * @param patchImage a 2D array representing RGB values of the patch image
     * @param transparentRed an integer representing the corresponding 
     *                       red component for the transparent RGB color
     * @param transparentGreen an integer representing the corresponding 
     *                         green component for the transparent RGB color
     * @param transparentBlue an integer representing the corresponding 
     *                        green component for the transparent RGB color
     * @return the number of pixels that we have patched.
     */
    public static int patch(int startRow,
            int startColumn,
            int[][] patchImage,
            int transparentRed,
            int transparentGreen,
            int transparentBlue) {

        int patchRow = patchImage.length;
        int patchCol = patchImage[0].length;
        int endRow = startRow + patchRow;
        int endCol = startColumn + patchCol;
        int count = 0;

        // check if startRow or startColumn is less than 0 
        // or greater than the height or width respectively
        // and the ending row or column is greater than the 
        // height or width respectively
        if (startRow < 0 || startColumn < 0 ||
                startRow > image.length ||
                startColumn > image[0].length ||
                endRow > image.length ||
                endCol > image[0].length) {
            return 0;
        }

        // loop for patch image by row
        for (int row = 0; row < patchRow; row++) {
            // loop for patch image by column
            for (int col = 0; col < patchCol; col++) {

                int red = unpackRedByte(patchImage[row][col]);
                int green = unpackGreenByte(patchImage[row][col]);
                int blue = unpackBlueByte(patchImage[row][col]);
                // check if the pixel's RGB value from patchImage matches the 
                // transparent RGB color
                if (red != transparentRed || 
                    green != transparentGreen || 
                    blue != transparentBlue) 
                {
                    image[startRow + row][startColumn + col] = patchImage[row][col];
                    count++;
                }
            }

        }

        return count;

    }
}