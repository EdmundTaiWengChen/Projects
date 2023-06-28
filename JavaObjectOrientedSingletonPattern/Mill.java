import java.util.ArrayList;

/**
 * Class to check if a mill is formed or destroyed.
 */
public class Mill {

    private static Board board = Board.getInstance();
    private static ArrayList<int[]> validMills = new ArrayList<int[]>();
    private static ArrayList<int[]> invalidMills = new ArrayList<int[]>();

    /**
     * Set points where a mill can be formed
     */
    public static void setValidMills(){
        validMills.add(new int[]{0, 1, 2});
        validMills.add(new int[]{3, 4, 5});
        validMills.add(new int[]{6, 7, 8});
        validMills.add(new int[]{9, 10, 11});
        validMills.add(new int[]{12, 13, 14});
        validMills.add(new int[]{15, 16, 17});
        validMills.add(new int[]{18, 19, 20});
        validMills.add(new int[]{21, 22, 23});
        validMills.add(new int[]{0, 9, 21});
        validMills.add(new int[]{3, 10, 18});
        validMills.add(new int[]{6, 11, 15});
        validMills.add(new int[]{1, 4, 7});
        validMills.add(new int[]{16, 19, 22});
        validMills.add(new int[]{8, 12, 17});
        validMills.add(new int[]{5, 13, 20});
        validMills.add(new int[]{2, 14, 23});
    }

    /**
     * Method to add a valid mill to the board.
     * @param mill mill to add to valid array
     */
    public void addValidMill(int[] mill) {
        validMills.add(mill);
    }

    /**
     * Method to return all mills in the board.
     */
    public static ArrayList<int[]> getValidMills() {
        return validMills;
    }

    /**
     * Method to add an invalid mill to the board.
     * @param mill to add to invalid array
     * @return
     */
    public boolean addInvalidMill(int[] mill) {
        return invalidMills.add(mill);
    }

    /**
     * Method to return all invalid mills in the board.
     */
    public static ArrayList<int[]> getInvalidMills() {
        return invalidMills;
    }

    /**
     * Check entire board if a piece is in a mill (three same pieces in a row)
     * @return true if a mill is found, false if not
     */
    public static boolean checkMill() {
        // loop through all mills
        for (int i = 0; i < Mill.getValidMills().size(); i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getPoints().get(Mill.getValidMills().get(i)[j]).getState() == Game.getTurn()) {
                    // if all three points in a mill are occupied by the same player
                    if (j == 2) {
                        // remove from valid mills and add to invalid mills
                        Mill.getInvalidMills().add(Mill.getValidMills().get(i));
                        Mill.getValidMills().remove(i);

                        System.out.println("You have formed a mill!");
                        return true;
                    }
                } else {
                    break;
                }
            }
        }
        return false;
    }

    /**
     * Check if a mill has been broken and add back to array of valid mills
     */
    public static void restoreMill() {
        // loop through all invalid mills
        for (int i = 0; i < Mill.getInvalidMills().size(); i++) {
            int count1 = 0;
            int count2 = 0;
            for (int j = 0; j < 3; j++) {
                if (board.getPoints().get(Mill.getInvalidMills().get(i)[j]).getState() == 1) {
                    // if exactly two points out of three in a mill are occupied by the same player
                    count1++;
                }
            }
            for (int j = 0; j < 3; j++) {
                if (board.getPoints().get(Mill.getInvalidMills().get(i)[j]).getState() == 2) {
                    // if exactly two points out of three in a mill are occupied by the same player
                    count2++;
                }
            }
            if (count1 == 2 || count2 == 2) {
                // remove from invalid mills and add to valid mills
                Mill.getValidMills().add(Mill.getInvalidMills().get(i));
                Mill.getInvalidMills().remove(i);
            }
        }
    }

    /**
     * Method to reset the mills
     */
    public static void resetMill(){
        validMills.clear();
        invalidMills.clear();
        setValidMills();
    }

}
