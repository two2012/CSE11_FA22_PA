/**
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: wtite-up, Java StringBuilder API, Java String API
 */

/**
 * This class is a calculator that
 * calculates string type double numbers
 */
public class Calculator {

    // to prevent magic number
    private static final String ZERO = "0";
    private static final char ASCIIFORZERO = '0';
    private static final int DECIMAL = 10;
    private static final int ONE = 1;
    private static final String  STRING_ONE = "1";
    private static final int INDEXZERO = 0;
    private static final String DECIMALPOINT = ".";
    private static final String REGEX = "\\.";

    
    
    // to store carry from decimal to whole number
    // when multiply
    private static int carryForMul = 0;




    /**
     * perform addition in two string type numbers
     * 
     * @param firstNum
     * @param secondNum
     * @return the result of addition
     * 
     */
    public static String add(String firstNum, String secondNum) {

        // to store final result of addition
        StringBuilder result = new StringBuilder();
        // to seperate whole number part and decimal part
        // for both firstNum and secondNum and store
        String wholeFNum = extractWholeNumber(firstNum);
        String decimalFNum = extractDecimal(firstNum);
        String wholeSNum = extractWholeNumber(secondNum);
        String decimalSNum = extractDecimal(secondNum);

        // to get whole number part length of first and second number
        int wholeFNumLen = wholeFNum.length();
        int wholeSNumLen = wholeSNum.length();

        // to store number of zero needs to be
        // prepend on whole number part
        int numZerosW = 0;

        // to make sure numZeroW is always a positive number
        if (wholeFNumLen > wholeSNumLen) {
            numZerosW = wholeFNumLen - wholeSNumLen;
        } else if (wholeFNumLen < wholeSNumLen) {
            numZerosW = wholeSNumLen - wholeFNumLen;
        }

        // to determin which number need to be prepend zero
        if (wholeFNumLen > wholeSNumLen) {
            wholeSNum = prependZeros(wholeSNum, numZerosW);
        } else if (wholeFNumLen < wholeSNumLen) {
            wholeFNum = prependZeros(wholeFNum, numZerosW);
        }

        // to get decimal part length of first and second number
        int decimalFNumLen = decimalFNum.length();
        int decimalSNumLen = decimalSNum.length();

        // to store number of zero needs to be
        // prepend on decimal part
        int numZerosD = 0;

        // to make sure numZeroD is always a positive number
        if (decimalFNumLen > decimalSNumLen) {
            numZerosD = decimalFNumLen - decimalSNumLen;
        } else if (decimalFNumLen < decimalSNumLen) {
            numZerosD = decimalSNumLen - decimalFNumLen;
        }

        // to determin which number need to be append zero
        if (decimalFNumLen > decimalSNumLen) {
            decimalSNum = appendZeros(decimalSNum, numZerosD);
        } else if (decimalFNumLen < decimalSNumLen) {
            decimalFNum = appendZeros(decimalFNum, numZerosD);
        }

        // flag to indicate if there is carry from decimal to whole number
        boolean carryIn = false;
        boolean carryOut = false;
        carryOut = carry(decimalFNum, decimalSNum);

        // construct the reuslt
        result.append(addNum(decimalFNum, decimalSNum, carryIn));
        result.insert(INDEXZERO, DECIMALPOINT);
        result.insert(INDEXZERO, addNum(wholeFNum, wholeSNum, carryOut));

        // trip leading and trailing zeros from result and return
        return stripZeros(result.toString());
        
    }

    /**
     * perform multiply operation
     * 
     * @param string   type double number
     * @param numTimes
     * @return the result of multiply
     */
    public static String multiply(String number, int numTimes) {

        // check if numTimes is a negative number
        if (numTimes < 0) {
            return stripZeros(number);
        }
        // check if numTimes is zero
        if (numTimes == 0) {
            return ZERO + DECIMALPOINT + ZERO;
        }

        StringBuilder result = new StringBuilder();

        // seperate whole number and decimal
        String wholePart = extractWholeNumber(number);
        String decimalPart = extractDecimal(number);

        // store carry from decimal to whole number
        int carryInForMul = 0;

        // perform multilication on decimal part
        String resultD = multiplyByPartD(decimalPart, numTimes, carryInForMul);
        result.append(resultD);
        // add decimal point to seperate whole number and decimal
        result.insert(INDEXZERO, DECIMALPOINT);
        // perform multilication on whole number part
        String resultW = multiplyByPartW(wholePart, numTimes, carryForMul);
        result.insert(INDEXZERO, resultW);

        // trip leading and trailing zeros from result and return
        return stripZeros(result.toString());

    }

    /**
     * this method is extract the whole number part
     * of the string type double umber
     * 
     * @param string type double number
     * @return the whole number part
     */
    public static String extractWholeNumber(String number) {

        // check if number contain decimal point
        if (!number.contains(DECIMALPOINT)) {
            return number;
        }
        // check if decimal point in the front
        if (number.indexOf(DECIMALPOINT) == INDEXZERO) {
            return ZERO;
        } else {
            // split the number by decimal point
            String[] splitedNum = number.split(REGEX);
            // return the whole number part
            return splitedNum[0];
        }

    }

    /**
     * this method is extract the decimal part
     * of the string type double umber
     * 
     * @param string type double number
     * @return the decimal part
     */
    public static String extractDecimal(String number) {

        // check if number contain decimal point
        if (!number.contains(DECIMALPOINT)) {
            return ZERO;
        } else {
            // split the number by decimal point
            String[] splitedNum = number.split(REGEX);
            if (number.indexOf(DECIMALPOINT) == (number.length() - 1)) {
                return ZERO;
            } else {
                // return the decimal part
                return splitedNum[1];
            }
        }

    }

    /**
     * this method is to prepending numZeros number of zeros to number.
     * 
     * @param number
     * @param numZeros
     * @return the prepemed number
     */
    public static String prependZeros(String number, int numZeros) {

        StringBuilder prependNum = new StringBuilder(number);
        // loop to prepend numZeros of zero to number
        for (int i = 0; i < numZeros; i++) {

            prependNum.insert(INDEXZERO, ZERO);
        }

        return prependNum.toString();

    }

    /**
     * this method is to appending numZeros number of zeros to number.
     * 
     * @param number
     * @param numZeros
     * @return the appened number
     */
    public static String appendZeros(String number, int numZeros) {
        StringBuilder appendNum = new StringBuilder(number);

        //// loop to append numZeros of zero to number
        for (int i = 0; i < numZeros; i++) {
            appendNum.append(ZERO);
        }

        return appendNum.toString();
    }

    /**
     * this method is to perform addition on firstDigit and secondDigit
     * 
     * @param firstDigit
     * @param secondDigit
     * @param carryIn
     * @return the rightmost digit of the sum firstDigit and secondDigit
     */
    public static char addDigits(char firstDigit, char secondDigit, boolean carryIn) {
        // convert char to int using ASCII table
        int firstNum = firstDigit - ASCIIFORZERO;
        int secondNum = secondDigit - ASCIIFORZERO;
        int result;

        // check if there is carry from previous colum
        if (carryIn) {
            result = firstNum + secondNum + ONE;
        } else {
            result = firstNum + secondNum;
        }
        // check if need set carry to next colum
        if (result > 9) {
            return (char) (ASCIIFORZERO + result % DECIMAL);
        } else {
            return (char) (ASCIIFORZERO + result);
        }
    }

    /**
     * this method is to determin if there is carry from previous colum
     * 
     * @param firstDigit
     * @param secondDigit
     * @param carryIn
     * @return true if adding firstDigit, secondDigit,
     *         and carryIn results in a carry.
     */
    public static boolean carryOut(char firstDigit, char secondDigit, boolean carryIn) {

        int firstNum = firstDigit - ASCIIFORZERO;
        int secondNum = secondDigit - ASCIIFORZERO;
        int result;

        // check if there is carry from previous colum
        if (carryIn) {
            result = firstNum + secondNum + ONE;
        } else {
            result = firstNum + secondNum;
        }
        // check if need set carry to next colum
        if (result > 9) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean carry(String first, String second){
        boolean carryIn = false;
        int length = first.length();

        for (int i = length - 1; i >= 0; --i){
            carryIn = carryOut(first.charAt(i), second.charAt(i), carryIn);
        }
        return carryIn;
    }
    /**
     * this method is to perform addition on
     * either whole number part or decimal part
     * 
     * @param firstNum
     * @param secondNum
     * @param carryIn
     * @return the sum of either whole number part or
     *         decimal part addition and carry
     */
    public static String addNum(String firstNum, String secondNum, boolean carryIn) {

        StringBuilder result = new StringBuilder();
        char[] firstNumArr = firstNum.toCharArray();
        char[] secondNumArr = secondNum.toCharArray();
        int arrLenght = firstNumArr.length;

        // loop for add either decimal part or whole number part
        // colum by colum form right to left
        for (int i = arrLenght - 1; i >= 0; --i) {
            char charResult = addDigits(firstNumArr[i], secondNumArr[i], carryIn);
            // check if the rightmost colum
            if (i == arrLenght - 1) {
                result.append(charResult);
                carryIn = carryOut(firstNumArr[i], secondNumArr[i], carryIn);
            } else {
                result.insert(INDEXZERO, charResult);
                carryIn = carryOut(firstNumArr[i], secondNumArr[i], carryIn);
            }
        }
        return result.toString();
    }

    /**
     * this method is removing all leading and trailing zeros from
     * number unless it is the only zero before or after the decimal.
     * 
     * @param number
     * @return the striped number
     */
    public static String stripZeros(String number) {

        char[] numberChars = number.toCharArray();
        int numberLen = numberChars.length;


        // check if unmber contains decimal point
        if(number.contains(DECIMALPOINT)){
            //System.out.println("have decimal point");
            //no whole part
            if (numberChars[INDEXZERO] == '.'){
                System.out.println("no whole part");
                int index = 0;
                boolean allZreosFlag = true;
                //have zero fellow
                if(numberChars[numberLen-1] == '0' && numberLen > 2){
                    for (int i = numberLen - 1; i > 1; --i){
                        if (numberChars[i] != '0' ){
                            index = i;
                            allZreosFlag = false;
                            break;
                        }
                    }
                    if(allZreosFlag){
                        number = number.substring(INDEXZERO, 2);
                        return number;
                    } else {
                        number = number.substring(INDEXZERO, index + 1);
                        return number;
                    }

                //have no zero fellow
                } else {
                    return number;
                }
                
            
            } 
            //no decimal part
            else if (numberChars[numberLen-1] == '.'){
                System.out.println("no decimal part");
                //have zero infront
                if(numberChars[INDEXZERO] == '0'){
                    boolean allZreosFlag = true;
                    int index = 0;
                
                    for (int i = 0; i < numberLen - 1; ++i){
                        if (numberChars[i] != '0'){
                            index = i;
                            allZreosFlag = false;
                            break;
                        }
                    }
                    if(allZreosFlag){
                        return ZERO + DECIMALPOINT;
                    } else {
                        number = number.substring(index, numberLen);
                        return number;
                    }
                }
                //have no zero infront
                else {

                    return number;
                }
                
            
            } 
            //have both whole and decimal 
            else {
                //System.out.println("have both whole and decimal");
                //have zero infront || have zero followed
                if (numberChars[INDEXZERO] == '0' || 
                    numberChars[numberLen -1] == '0')
                {
                    int decimalIndex = number.indexOf(DECIMALPOINT);
                    int indexW = 0;
                    int indexD = 0;
                    boolean allZreosFlagW = true;
                    boolean allZreosFlagD = true;
                    for (int i = 0; i < decimalIndex; ++i){
                        if (numberChars[i] != '0'){
                            indexW = i;
                            //System.out.println("indexW-->"+indexW);
                            allZreosFlagW = false;
                            break;
                        }
                    }
                    for (int i = numberLen - 1; i > decimalIndex; --i){
                        if (numberChars[i] != '0'){
                            indexD = i;
                            //System.out.println("indexD-->"+indexD);
                            allZreosFlagD = false;
                            break;
                        }
                    }

                    if (allZreosFlagW && allZreosFlagD){
                        return ZERO + DECIMALPOINT + ZERO;
                    } else if (allZreosFlagW && !allZreosFlagD){
                        number = ZERO + number.substring(decimalIndex,indexD+1);
                        return number;
                    } else if (!allZreosFlagW && allZreosFlagD){
                        number = number.substring(indexW, decimalIndex + 2);
                        return number;
                    } else {
                        number = number.substring(indexW, indexD + 1);
                        return number;
                    }
                }
                //have no zero both infront and followed
                else {
                    return number;
                }
                
            }
        } 
        // number does not contain decimal point
        else{
            //have zero infront
            //System.out.println("have no decimal point");
            if(numberChars[INDEXZERO] == '0'){
                boolean allZreosFlag = true;
                int index = 0;
                //System.out.println("have zero infront");
                for (int i = 0; i <= numberLen - 1; ++i){
                    if (numberChars[i] != '0'){
                        index = i;
                        allZreosFlag = false;
                        break;
                    }
                }
                if(allZreosFlag){
                    return ZERO;
                } else {
                    number = number.substring(index, numberLen);
                    return number;
                }

            //no zero infront
            } else {
                //System.out.println("have no zero infront");
                return number;

            }
            

        }

        
    }

    /**
     * this method is perform multiplication on decimal part
     * 
     * @param part
     * @param numTimes
     * @param carry
     * @return the result of decimal part multiply numTimes
     */
    public static String multiplyByPartD(String part, int numTimes, int carry) {

        StringBuilder result = new StringBuilder();

        char[] partArray = part.toCharArray();
        int length = partArray.length;

        // loop for decimal part times the numTimes
        for (int i = length - 1; i >= 0; --i) {
            // convert the char to int
            int digit = partArray[i] - ASCIIFORZERO;
            // perform multilication
            int mulResult = digit * numTimes;
            // insert the reminder of mulResult divid by 10
            // and add the carry from previous colum
            result.insert(INDEXZERO, (mulResult % DECIMAL + carry));
            // store the carry from this colum
            carry = mulResult / DECIMAL;

            if (i == 0) {
                // store the carry from decimal to whole number
                carryForMul = mulResult / DECIMAL;
            }
        }

        return result.toString();
    }

    /**
     * this method is perform multiplication on whole number part
     * 
     * @param part
     * @param numTimes
     * @param carry
     * @return the result of whole number part multiply numTimes
     */
    public static String multiplyByPartW(String part, int numTimes, int carry) {

        StringBuilder result = new StringBuilder();

        char[] partArray = part.toCharArray();
        int length = partArray.length;

        // loop for whole number part times the numTimes
        for (int i = length - 1; i >= 0; --i) {

            // convert char to int
            int digit = partArray[i] - ASCIIFORZERO;
            // perform multilication
            int mulResult = digit * numTimes;
            // insert the reminder of mulResult divid by 10
            // and add the carry from previous colum
            result.insert(INDEXZERO, (mulResult % DECIMAL + carry));
            // store the carry from this colum
            carry = mulResult / DECIMAL;

            if (i == 0) {
                // check if there is carry from leftmost colum
                if (carry > 0) {
                    result.insert(INDEXZERO, carry);
                }
            }
        }

        return result.toString();
    }

}
