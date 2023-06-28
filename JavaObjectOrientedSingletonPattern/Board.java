import java.util.ArrayList;

/**
 * Board class to handle the board logic, its points and their states.
 */
public class Board {

    private static Board board; // singleton
    private static ArrayList<Point> points = new ArrayList<Point>(); // 24 points

    private Board(){}; // constructor

    /**
     * Singleton method to return the board instance
     */
    public static Board getInstance(){
        if (board == null){
            board = new Board();
        }
        return board;
    }

    /**
     * Initialize the board with 24 vacant points
     */
    public void init(){
        for(int i = 0; i < 24; i++){
            points.add(new Point(i, 0));
        }
        Mill.setValidMills();
    }

    /**
     * Method to reset the board. (After tutorial)
     */
    public void reset(){
        for(int i = 0; i < 24; i++){
            points.get(i).setState(0);
        }
    }

    /**
     * Method to store the board state (of all points) as a string.
     */
    public String storeState(){
        String state = "";
        for(int i = 0; i < 24; i++){
            state += points.get(i).getState();
        }
        return state;
    }

    /**
     * Method to load the board state (of all points) from a string.
     * @param state String of the states of the board's points.
     */
    public static void loadState(String state) {
        for (int i = 0; i < 24; i++) {
            points.get(i).setState(Integer.parseInt(state.substring(i, i + 1)));
        }
    }

    /**
     * Method to return all points in the board.
     * @return ArrayList of all points in the board.
     */
    public ArrayList<Point> getPoints() {
        return points;
    }

    //  1  #----------------------#----------------------#  XXX
    //     |                      |                      |
    //     |                      |                      |  000
    //  2  |       #--------------#--------------#       |
    //     |       |              |              |       |
    //     |       |              |              |       |
    //  3  |       |       #------#------#       |       |
    //     |       |       |             |       |       |
    //     |       |       |             |       |       |
    //  4  #-------#-------#             #-------#-------#
    //     |       |       |             |       |       |
    //     |       |       |             |       |       |
    //  5  |       |       #------#------#       |       |
    //     |       |              |              |       |
    //     |       |              |              |       |
    //  6  |       #--------------#--------------#       |
    //     |                      |                      |
    //     |                      |                      |
    //  7  #----------------------#----------------------#
    //     A       B       C      D      E       F       G


    /**
     * Method to print the board.
     */
    public void print(){
        System.out.println("1  "+points.get(0)+"----------------------"+points.get(1)+"----------------------"+points.get(2) + "  " + Game.getUnplaced(1));
        System.out.println("   "+"|                      |                      |");
        System.out.println("   "+"|                      |                      |" + "  " + Game.getUnplaced(2));
        System.out.println("2  "+"|       "+points.get(3)+"--------------"+points.get(4)+"--------------"+points.get(5)+"       |");
        System.out.println("   "+"|       |              |              |       |");
        System.out.println("   "+"|       |              |              |       |");
        System.out.println("3  "+"|       |       "+points.get(6)+"------"+points.get(7)+"------"+points.get(8)+"       |       |");
        System.out.println("   "+"|       |       |             |       |       |");
        System.out.println("   "+"|       |       |             |       |       |");
        System.out.println("4  "+points.get(9)+"-------"+points.get(10)+"-------"+points.get(11)+"             "+points.get(12)+"-------"+points.get(13)+"-------"+points.get(14));
        System.out.println("   "+"|       |       |             |       |       |");
        System.out.println("   "+"|       |       |             |       |       |");
        System.out.println("5  "+"|       |       "+points.get(15)+"------"+points.get(16)+"------"+points.get(17)+"       |       |");
        System.out.println("   "+"|       |              |              |       |");
        System.out.println("   "+"|       |              |              |       |");
        System.out.println("6  "+"|       "+points.get(18)+"--------------"+points.get(19)+"--------------"+points.get(20)+"       |");
        System.out.println("   "+"|                      |                      |" + "  " + Game.getRemoved(1));
        System.out.println("   "+"|                      |                      |");
        System.out.println("7  "+points.get(21)+"----------------------"+points.get(22)+"----------------------"+points.get(23) + "  " + Game.getRemoved(2));
        System.out.println("   "+"A       B       C      D      E       F       G");
    }

}
