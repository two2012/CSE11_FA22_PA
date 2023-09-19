/*
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: write-up
 * 
 * the class is my String Builder, its function includes
 * get length, append char or String, subString by given start 
 * index or both start and end index and toString.
 * 
 */
public class MyStringBuilder {
    private CharNode start;
    private CharNode end;
    private int length;
    private static final int ZERO = 0;
    private static final int ONE = 1;


    /**
     * Creates a MyStringBuilder object by given char.
     * 
     * @param ch the data of its start
     */
    public MyStringBuilder(char ch) {

        // create a new CharNode for start
        start = new CharNode(ch);
        // set next to null
        start.setNext(null);
        end = start;
        // set length to 1
        length = ONE;

    }

    /**
     * Creates a MyStringBuilder object by given String.
     * 
     * @param str the data of this object
     */
    public MyStringBuilder(String str) {

        try {
            // check str is a empty string
            if (str.length() == ZERO) {
                length = ZERO;
            } else {
                // assign the length of str to length
                length = str.length();
                CharNode cuCharNode;
                CharNode nextCharNode;
                // new a CharNode with data vaule is the first
                // char of the str
                start = new CharNode(str.charAt(ZERO));
                // let cuCharNode point to start
                cuCharNode = start;
                // loop for str
                for (int i = 1; i < length; i++) {
                    // get every char in str
                    char cuChar = str.charAt(i);
                    // new a CharNode with data vaule is the every
                    // char of the str
                    nextCharNode = new CharNode(cuChar);
                    // let cuCharNode point to nextCharNode
                    cuCharNode.setNext(nextCharNode);
                    // let cuChaiNode point to nextCharNode
                    cuCharNode = nextCharNode;
                    // if loop to last char of the str make
                    // end point to last CharNode
                    // and setNext for end is null
                    if (i == length - ONE) {
                        end = cuCharNode;
                        end.setNext(null);
                    }

                }

            }

        } catch (NullPointerException e) {
            // if any NullPointerException found
            // throw it
            throw e;
        }
    }

    /**
     * Creates a MyStringBuilder object by given a
     * MyStringBuilder object.
     * 
     * @param other the data of this object
     */
    public MyStringBuilder(MyStringBuilder other) {

        try {
            // check if other is a empty MSB
            if (other.length == ZERO) {
                this.length = ZERO;
                this.start = null;
                this.end = null;
            } else {
                // get first data of other and cell it currentChar
                char currentChar = other.start.getData();
                // assign a new CharNode with data vaule currentChar
                // for this start
                this.start = new CharNode(currentChar);
                // set next to null for start
                this.start.setNext(null);
                // let end point to start
                this.end = this.start;
                // update length to 1
                this.length = ONE;
                // let currentCharNode point to other.start
                CharNode currentCharNode = other.start;
                // loop for other to get evey CharNode in other
                for (int i = 0; i < other.length(); i++) {
                    // skip first element
                    if (i == ZERO) {
                    } else {
                        // get every element in other
                        currentCharNode = currentCharNode.getNext();
                        currentChar = currentCharNode.getData();
                        // append to this
                        this.append(currentChar);

                    }

                }
            }

        } catch (NullPointerException e) {
            // if any NullPointerException found
            // throw it
            throw e;
        }

    }

    /**
     * get the length of the object
     * 
     * @return the length
     */
    public int length() {
        return this.length;
    }

    /**
     * append a char to the object
     * 
     * @param ch the char need be appended
     * @return the appended object
     */
    public MyStringBuilder append(char ch) {
        // check if this is empty MSB
        if (this.length == ZERO) {
            length = ONE;
            start = new CharNode(ch);
            start.setNext(null);
            end = start;
            return this;
        } else {

            // new a CharNode with data vaule ch
            CharNode appendCharNode = new CharNode(ch);
            // set end.next to appendCharNode
            end.setNext(appendCharNode);
            // set appendCharNode.next to null
            appendCharNode.setNext(null);
            // let end point to appendCharNode
            end = appendCharNode;
            // update length
            length++;
            return this;
        }
    }

    /**
     * append a String to the object
     * 
     * @param str the String need be appended
     * @return the appended object
     */
    public MyStringBuilder append(String str) {

        try {
            // check if this is a empty MSB
            if (this.length == ZERO) {
                // check if str has length 0
                if (str.length() != ZERO) {
                    // set this length to length of str
                    length = str.length();
                    CharNode cuCharNode;
                    CharNode nextCharNode;
                    // new a CharNode with data vaule is the first
                    // char of the str
                    start = new CharNode(str.charAt(ZERO));
                    // let cuCharNode point to start
                    cuCharNode = start;
                    // loop for str
                    for (int i = 1; i < length; i++) {
                        // get every char in str
                        char cuChar = str.charAt(i);
                        // new a CharNode with data vaule is the every
                        // char of the str
                        nextCharNode = new CharNode(cuChar);
                        // let cuCharNode point to nextCharNode
                        cuCharNode.setNext(nextCharNode);
                        // let cuChaiNode point to nextCharNode
                        cuCharNode = nextCharNode;
                        // if loop to last char of the str make
                        // end point to last CharNode
                        // and setNext for end is null
                        if (i == length - ONE) {
                            end = cuCharNode;
                            end.setNext(null);
                        }

                    }
                    return this;

                } else {
                    return this;
                }
            } else {
                if (str.length() != ZERO) {
                    // loop for str append every char to this
                    for (int i = 0; i < str.length(); i++) {
                        this.append(str.charAt(i));
                    }

                    return this;

                } else {
                    return this;
                }
            }

        } catch (NullPointerException e) {
            // if any NullPointerException found
            // throw it
            throw e;
        }

    }

    /**
     * covert the MyStringBuilder to String type
     * 
     * @return String type object
     */
    public String toString() {
        // define a ouput string with value ""
        String output = "";
        // assign start to cuCharNode
        CharNode cuCharNode = start;
        // loop for this
        for (int i = 0; i < length; i++) {
            // cast every data to output string
            output += cuCharNode.getData();
            // move to next CharNode
            cuCharNode = cuCharNode.getNext();
        }

        return output;

    }

    /**
     * Returns a substring starting from startIdx to
     * the end.
     * 
     * @param startIdx started index
     * @return String from startInx
     */
    public String subString(int startIdx) {
        // check startIdx if out of range
        if (startIdx < ZERO ||
                startIdx >= this.length) {

            throw new IndexOutOfBoundsException();
        }
        String output = "";
        CharNode cuCharNode = null;

        // loop this MSB
        for (int i = 0; i < this.length; i++) {
            if (i == ZERO) {
                cuCharNode = start;
            } else {
                cuCharNode = cuCharNode.getNext();
            }

            // cast data to output string after startIdx
            if (i >= startIdx) {
                output += cuCharNode.getData();
            }
        }

        return output;
    }

    /**
     * Returns a substring from startIdx (inclusive) to
     * endIdx (exclusive).
     * 
     * @param startIdx started index
     * @param endIdx   end index
     * @return String from startIdx to endIdx
     */
    public String subString(int startIdx, int endIdx) {
        String output = "";
        // check if startIdx is equal to endIdx
        if (startIdx == endIdx) {
            return output;
        }
        CharNode cuCharNode = null;
        // check if startIdx and endIdx out of range
        if (startIdx < endIdx &&
                startIdx >= ZERO &&
                endIdx < this.length + ONE) {
            // loop this MSB
            for (int i = 0; i < this.length; i++) {
                if (i == ZERO) {
                    cuCharNode = start;
                } else {
                    cuCharNode = cuCharNode.getNext();
                }
                // cast data to output string after startIdx
                // and before endIdx
                if (i >= startIdx && i < endIdx) {
                    output += cuCharNode.getData();
                }
            }
        } else {
            throw new IndexOutOfBoundsException();
        }

        return output;
    }
}
