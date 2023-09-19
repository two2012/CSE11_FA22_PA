
/**
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: write-up, Java Arrays API, Java Integer API
 * 
 * this class is a air line reservation application, it includes functions 
 * book a seat with name and row number, book a seat with name and class number,
 * cancel a seat, upgrade class, lookup passenger's seat, and lookup all avalibale
 * tickects.
 */

import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class AirlineReservation {
    /* Delimiters and Formatters */
    private static final String CSV_DELIMITER = ",";
    private static final String DELIMITER = ",|\n";
    private static final String REGDIGTS = "-?\\d+(\\.\\d+)?";
    private static final String COMMAND_DELIMITER = " ";
    private static final String PLANE_FORMAT = "%d\t | %s | %s \n";

    // To prevent magic numbers
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int NEGATIVEONE = -1;

    /* Travel Classes */
    private static final int FIRST_CLASS = 0;
    private static final int BUSINESS_CLASS = 1;
    private static final int ECONOMY_CLASS = 2;
    private static final String[] CLASS_LIST = new String[] { "F", "B", "E" };
    private static final String[] CLASS_FULLNAME_LIST = new String[] {
            "First Class", "Business Class", "Economy Class" };

    /* Commands */
    private static final String[] COMMANDS_LIST = new String[] { "book",
            "cancel", "lookup", "availabletickets", "upgrade", "print", "exit" };
    private static final int BOOK_IDX = 0;
    private static final int CANCEL_IDX = 1;
    private static final int LOOKUP_IDX = 2;
    private static final int AVAI_TICKETS_IDX = 3;
    private static final int UPGRADE_IDX = 4;
    private static final int PRINT_IDX = 5;
    private static final int EXIT_IDX = 6;
    private static final int BOOK_UPGRADE_NUM_ARGS = 3;
    private static final int CANCEL_LOOKUP_NUM_ARGS = 2;

    /* Strings for main */
    private static final String USAGE_HELP = "Available commands:\n" +
            "- book <travelClass(F/B/E)> <passengerName>\n" +
            "- book <rowNumber> <passengerName>\n" +
            "- cancel <passengerName>\n" +
            "- lookup <passengerName>\n" +
            "- availabletickets\n" +
            "- upgrade <travelClass(F/B)> <passengerName>\n" +
            "- print\n" +
            "- exit";
    private static final String CMD_INDICATOR = "> ";
    private static final String INVALID_COMMAND = "Invalid command.";
    private static final String INVALID_ARGS = "Invalid number of arguments.";
    private static final String INVALID_ROW = "Invalid row number %d, failed to book.\n";
    private static final String DUPLICATE_BOOK = "Passenger %s already has a booking and cannot book multiple seats.\n";
    private static final String BOOK_SUCCESS = "Booked passenger %s successfully.\n";
    private static final String BOOK_FAIL = "Could not book passenger %s.\n";
    private static final String CANCEL_SUCCESS = "Canceled passenger %s's booking successfully.\n";
    private static final String CANCEL_FAIL = "Could not cancel passenger %s's booking, do they have a ticket?\n";
    private static final String UPGRADE_SUCCESS = "Upgraded passenger %s to %s successfully.\n";
    private static final String UPGRADE_FAIL = "Could not upgrade passenger %s to %s.\n";
    private static final String LOOKUP_SUCCESS = "Passenger %s is in row %d.\n";
    private static final String LOOKUP_FAIL = "Could not find passenger %s.\n";
    private static final String AVAILABLE_TICKETS_FORMAT = "%s: %d\n";

    /* Static variables - DO NOT add any additional static variables */
    static String[] passengers;
    static int planeRows;
    static int firstClassRows;
    static int businessClassRows;

    /**
     * Runs the command-line interface for our Airline Reservation System.
     * Prompts user to enter commands, which correspond to different functions.
     * 
     * @param args args[0] contains the filename to the csv input
     * @throws FileNotFoundException if the filename args[0] is not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        // If there are an incorrect num of args, print error message and quit
        if (args.length != 1) {
            System.out.println(INVALID_ARGS);
            return;
        }
        initPassengers(args[0]); // Populate passengers based on csv input file
        System.out.println(USAGE_HELP);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(CMD_INDICATOR);
            String line = scanner.nextLine().trim();

            // Exit
            if (line.toLowerCase().equals(COMMANDS_LIST[EXIT_IDX])) {
                scanner.close();
                return;
            }

            String[] splitLine = line.split(COMMAND_DELIMITER);
            splitLine[0] = splitLine[0].toLowerCase();

            // Check for invalid commands
            boolean validFlag = false;
            for (int i = 0; i < COMMANDS_LIST.length; i++) {
                if (splitLine[0].toLowerCase().equals(COMMANDS_LIST[i])) {
                    validFlag = true;
                }
            }
            if (!validFlag) {
                System.out.println(INVALID_COMMAND);
                continue;
            }

            // Book
            if (splitLine[0].equals(COMMANDS_LIST[BOOK_IDX])) {
                if (splitLine.length < BOOK_UPGRADE_NUM_ARGS) {
                    System.out.println(INVALID_ARGS);
                    continue;
                }
                String[] contents = line.split(COMMAND_DELIMITER,
                        BOOK_UPGRADE_NUM_ARGS);
                String passengerName = contents[contents.length - 1];
                try {
                    // book row <passengerName>
                    int row = Integer.parseInt(contents[1]);
                    if (row < 0 || row >= passengers.length) {
                        System.out.printf(INVALID_ROW, row);
                        continue;
                    }
                    // Do not allow duplicate booking
                    boolean isDuplicate = false;
                    for (int i = 0; i < passengers.length; i++) {
                        if (passengerName.equals(passengers[i])) {
                            isDuplicate = true;
                        }
                    }
                    if (isDuplicate) {
                        System.out.printf(DUPLICATE_BOOK, passengerName);
                        continue;
                    }
                    if (book(row, passengerName)) {
                        System.out.printf(BOOK_SUCCESS, passengerName);
                    } else {
                        System.out.printf(BOOK_FAIL, passengerName);
                    }
                } catch (NumberFormatException e) {
                    // book <travelClass(F/B/E)> <passengerName>
                    validFlag = false;
                    contents[1] = contents[1].toUpperCase();
                    for (int i = 0; i < CLASS_LIST.length; i++) {
                        if (CLASS_LIST[i].equals(contents[1])) {
                            validFlag = true;
                        }
                    }
                    if (!validFlag) {
                        System.out.println(INVALID_COMMAND);
                        continue;
                    }
                    // Do not allow duplicate booking
                    boolean isDuplicate = false;
                    for (int i = 0; i < passengers.length; i++) {
                        if (passengerName.equals(passengers[i])) {
                            isDuplicate = true;
                        }
                    }
                    if (isDuplicate) {
                        System.out.printf(DUPLICATE_BOOK, passengerName);
                        continue;
                    }
                    int travelClass = FIRST_CLASS;
                    if (contents[1].equals(CLASS_LIST[BUSINESS_CLASS])) {
                        travelClass = BUSINESS_CLASS;
                    } else if (contents[1].equals(
                            CLASS_LIST[ECONOMY_CLASS])) {
                        travelClass = ECONOMY_CLASS;
                    }
                    if (book(passengerName, travelClass)) {
                        System.out.printf(BOOK_SUCCESS, passengerName);
                    } else {
                        System.out.printf(BOOK_FAIL, passengerName);
                    }
                }
            }

            // Upgrade
            if (splitLine[0].equals(COMMANDS_LIST[UPGRADE_IDX])) {
                if (splitLine.length < BOOK_UPGRADE_NUM_ARGS) {
                    System.out.println(INVALID_ARGS);
                    continue;
                }
                String[] contents = line.split(COMMAND_DELIMITER,
                        BOOK_UPGRADE_NUM_ARGS);
                String passengerName = contents[contents.length - 1];
                validFlag = false;
                contents[1] = contents[1].toUpperCase();
                for (int i = 0; i < CLASS_LIST.length; i++) {
                    if (CLASS_LIST[i].equals(contents[1])) {
                        validFlag = true;
                    }
                }
                if (!validFlag) {
                    System.out.println(INVALID_COMMAND);
                    continue;
                }
                int travelClass = FIRST_CLASS;
                if (contents[1].equals(CLASS_LIST[BUSINESS_CLASS])) {
                    travelClass = BUSINESS_CLASS;
                } else if (contents[1].equals(CLASS_LIST[ECONOMY_CLASS])) {
                    travelClass = ECONOMY_CLASS;
                }
                if (upgrade(passengerName, travelClass)) {
                    System.out.printf(UPGRADE_SUCCESS, passengerName,
                            CLASS_FULLNAME_LIST[travelClass]);
                } else {
                    System.out.printf(UPGRADE_FAIL, passengerName,
                            CLASS_FULLNAME_LIST[travelClass]);
                }
            }

            // Cancel
            if (splitLine[0].equals(COMMANDS_LIST[CANCEL_IDX])) {
                if (splitLine.length < CANCEL_LOOKUP_NUM_ARGS) {
                    System.out.println(INVALID_ARGS);
                    continue;
                }
                String[] contents = line.split(COMMAND_DELIMITER,
                        CANCEL_LOOKUP_NUM_ARGS);
                String passengerName = contents[contents.length - 1];
                if (cancel(passengerName)) {
                    System.out.printf(CANCEL_SUCCESS, passengerName);
                } else {
                    System.out.printf(CANCEL_FAIL, passengerName);
                }
            }

            // Lookup
            if (splitLine[0].equals(COMMANDS_LIST[LOOKUP_IDX])) {
                if (splitLine.length < CANCEL_LOOKUP_NUM_ARGS) {
                    System.out.println(INVALID_ARGS);
                    continue;
                }
                String[] contents = line.split(COMMAND_DELIMITER,
                        CANCEL_LOOKUP_NUM_ARGS);
                String passengerName = contents[contents.length - 1];
                if (lookUp(passengerName) == -1) {
                    System.out.printf(LOOKUP_FAIL, passengerName);
                } else {
                    System.out.printf(LOOKUP_SUCCESS, passengerName,
                            lookUp(passengerName));
                }
            }

            // Available tickets
            if (splitLine[0].equals(COMMANDS_LIST[AVAI_TICKETS_IDX])) {
                int[] numTickets = availableTickets();
                for (int i = 0; i < CLASS_FULLNAME_LIST.length; i++) {
                    System.out.printf(AVAILABLE_TICKETS_FORMAT,
                            CLASS_FULLNAME_LIST[i], numTickets[i]);
                }
            }

            // Print
            if (splitLine[0].equals(COMMANDS_LIST[PRINT_IDX])) {
                printPlane();
            }
        }
    }

    /**
     * Initializes the static variables passengers, planeRows, firstClassRows,
     * and businessClassRows using the contents of the CSV file named fileName.
     * 
     * @param fileName the name of the source file
     * @throws FileNotFoundException
     */
    private static void initPassengers(String fileName) throws FileNotFoundException {
        // TODO
        File file = new File(fileName);
        Scanner input = new Scanner(file).useDelimiter(DELIMITER);

        // Initializes the variables form first line of the source file
        planeRows = Integer.parseInt(input.next());
        firstClassRows = Integer.parseInt(input.next());
        businessClassRows = Integer.parseInt(input.next());

        // Initializes the passengers array with size of number of planeRows
        passengers = new String[planeRows];

        while (input.hasNext()) {
            // put next string from the source file to the temp
            String temp = input.next();
            // check if temp is a number
            if (temp.matches(REGDIGTS)) {
                passengers[Integer.parseInt(temp)] = input.next();
            }
        }
    }

    /**
     * find the travel class corresponding to the given row.
     * 
     * @param row given row
     * @return the travel class corresponding to the given row.
     */
    private static int findClass(int row) {
        // TODO
        // check if row eist in the plane
        if (row >= planeRows || row < 0) {
            return -1;
        }

        // check if the row is in first class
        if (row < firstClassRows) {
            return FIRST_CLASS;

            // check if the row is in business class
        } else if (row < firstClassRows + businessClassRows) {
            return BUSINESS_CLASS;
            // the rest will in economy class
        } else {
            return ECONOMY_CLASS;
        }
    }

    /**
     * find the first row of the given travelClass.
     * 
     * @param travelClass given travelClass
     * @return Return the first row of the given travelClass.
     *         Return -1 if travelClass is not FIRST_CLASS,
     *         BUSINESS_CLASS, or ECONOMY_CLASS.
     */
    private static int findFirstRow(int travelClass) {
        // TODO
        // check if travalClass is FIRST_CLASS
        if (travelClass == FIRST_CLASS) {
            if (firstClassRows != ZERO) {
                return ZERO;
            } else {
                return -1;
            }
            // check if travalClass is BUSINESS_CLASS
        } else if (travelClass == BUSINESS_CLASS) {
            if (businessClassRows != ZERO) {
                return firstClassRows;
            } else {
                return -1;
            }
            // check if travalClass is ECONOMY_CLASS
        } else if (travelClass == ECONOMY_CLASS) {
            if (planeRows != firstClassRows + businessClassRows) {
                return firstClassRows +
                        businessClassRows;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /**
     * find the last row of a given travelClass.
     * 
     * @param travelClass given travelClass
     * @return Return the last row of a given travelClass.
     *         Return -1 if travelClass is not FIRST_CLASS,
     *         BUSINESS_CLASS, or ECONOMY_CLASS.
     */
    private static int findLastRow(int travelClass) {
        // TODO
        // check if travalClass is FIRST_CLASS
        if (travelClass == FIRST_CLASS) {
            if (firstClassRows != ZERO) {
                return firstClassRows - ONE;
            } else {
                return -1;
            }
            // check if travalClass is BUSINESS_CLASS
        } else if (travelClass == BUSINESS_CLASS) {
            if (businessClassRows != ZERO) {
                return firstClassRows +
                        businessClassRows -
                        ONE;
            } else {
                return -1;
            }
            // check if travalClass is ECONOMY_CLASS
        } else if (travelClass == ECONOMY_CLASS) {
            if (planeRows != firstClassRows + businessClassRows) {
                return planeRows - ONE;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /**
     * Book a ticket for passengerName in the travelClass section.
     * Place passengerName in the first available row of the selected travelClass.
     * A row is available if there is no other passenger that booked that row.
     * 
     * @param passengerName given passenger name
     * @param travelClass   given travelClass
     * @return Return false if no seats are available for the selected travelClass,
     *         otherwise return true.
     */
    public static boolean book(String passengerName, int travelClass) {
        // TODO

        // check if passengerName is null
        if (passengerName == null) {
            return false;
        }
        // find and store first and last row of travelClass
        int first = findFirstRow(travelClass);
        int last = findLastRow(travelClass);
        // loop for all seat in travleClass and check
        // if it is available
        for (int i = first; i <= last; ++i) {
            if (passengers[i] == null) {
                passengers[i] = passengerName;
                return true;
            }
        }
        return false;
    }

    /**
     * Book a ticket for passengerName at the seat number row.
     * If the row is available, place passengerName in the row.
     * If the row is not available, place passengerName in the
     * first available row in the same travel class as row.
     * 
     * @param row           given row
     * @param passengerName given passenger name
     * @return flase if book fialed otherwise return true
     */
    public static boolean book(int row, String passengerName) {
        // TODO

        // check if passengerName is null
        if (passengerName == null) {
            return false;
        }

        // find the travelClass for the given row
        int travelClass = findClass(row);

        // check if the row is available
        if (passengers[row] == null) {
            passengers[row] = passengerName;
            return true;
        } else {
            return book(passengerName, travelClass);
        }
    }

    /**
     * Cancel the booking for passengerName.
     * Remove passengerName from the passengers array.
     * 
     * @param passengerName given passenger name
     * @return Return true upon successful removal,
     *         false otherwise.
     */
    public static boolean cancel(String passengerName) {
        // TODO
        // check if passengerName is null

        if (passengerName == null) {
            System.out.println(passengerName == null);
            return false;
        } else {
            // loop to find the passgengerName in passengers
            // and remove it
            for (int i = 0; i < planeRows; ++i) {
                if (passengerName.equals(passengers[i])) {
                    Arrays.fill(passengers, i, i + ONE, null);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Look up the row number of passengerName.
     * 
     * @param passengerNamegiven passenger name
     * @return Return the row number of passengerName,
     *         or return -1 if not found.
     */
    public static int lookUp(String passengerName) {
        // TODO
        // check if passengerName is null
        if (passengerName == null) {
            return -1;
        } else {
            // loop to find the passgengerName in passengers
            for (int i = 0; i < planeRows; ++i) {
                if (passengerName.equals(passengers[i])) {
                    return i;
                }
            }
            return -1;
        }
    }

    /**
     * Find and return the number of available tickets in
     * each travel class.
     * 
     * @return return the number of available tickets in
     *         each travel class.
     */
    public static int[] availableTickets() {
        // TODO
        // array for available tickets
        int[] availableTickets;
        // conuter for first, business and economy class
        int countF = ZERO;
        int countB = ZERO;
        int countE = ZERO;

        // find the the start and end row for
        // first, business and economy class
        int startF = findFirstRow(FIRST_CLASS);
        int endF = findLastRow(FIRST_CLASS);

        int startB = findFirstRow(BUSINESS_CLASS);
        int endB = findLastRow(BUSINESS_CLASS);

        int startE = findFirstRow(ECONOMY_CLASS);
        int endE = findLastRow(ECONOMY_CLASS);

        if (startF == NEGATIVEONE || endF == NEGATIVEONE) {

        } else {
            // loop the first class rows and
            // count available tickets
            for (int i = startF; i <= endF; ++i) {
                if (passengers[i] == null) {
                    countF++;
                }
            }

        }

        if (startB == NEGATIVEONE || endB == NEGATIVEONE) {

        } else {
            // loop the business class rows and
            // count available tickets
            for (int i = startB; i <= endB; ++i) {
                if (passengers[i] == null) {
                    countB++;
                }
            }
        }

        if (startE == NEGATIVEONE || endE == NEGATIVEONE) {

        } else {
            // loop the economy class rows and
            // count available tickets
            for (int i = startE; i <= endE; ++i) {
                if (passengers[i] == null) {
                    countE++;
                }
            }
        }

        availableTickets = new int[] { countF, countB, countE };

        return availableTickets;
    }

    /**
     * Upgrade passengerName to a seat in the upgradeClass.
     * 
     * @param passengerName given passenger name
     * @param upgradeClass  the class passenger want to upgrade
     * @return Return true if the upgrade is successful,
     *         false otherwise.
     */
    public static boolean upgrade(String passengerName, int upgradeClass) {
        // TODO
        // check if passengerName is null
        if (passengerName == null) {
            return false;
        }

        int originalClass = NEGATIVEONE;
        int start = findFirstRow(upgradeClass);
        int end = findLastRow(upgradeClass);

        // check if the passengerName is in passengers
        for (int i = 0; i < planeRows; ++i) {
            if (passengerName.equals(passengers[i])) {
                originalClass = findClass(i);
            }
        }

        if (originalClass == NEGATIVEONE) {
            return false;
        } else {
            // check if upgradeClass is lower or equal
            // to passengerName's existing class
            if (originalClass > upgradeClass) {
                int[] availableTickets = availableTickets();
                // check if is there available seats in upgradeClass
                if (availableTickets[upgradeClass] != ZERO) {
                    cancel(passengerName);
                    book(passengerName, upgradeClass);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Prints out the names of each of the passengers according to their booked
     * seat row. No name is printed for an empty (currently available) seat.
     */
    public static void printPlane() {
        for (int i = 0; i < passengers.length; i++) {
            System.out.printf(PLANE_FORMAT, i, CLASS_LIST[findClass(i)],
                    passengers[i] == null ? "" : passengers[i]);
        }
    }
}
