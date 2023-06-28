import java.util.Scanner;

/**
 * Class to handle the tutorial.
 */
public class Tutorial {

    private static Board board = Board.getInstance();

    /**
     * Method to set the board for the tutorial.
     * @param states the states of the points on the board
     * @param player1PiecesPlaced the number of pieces player 1 has placed
     * @param player2PiecesPlaced the number of pieces player 2 has placed
     * @param player1PiecesRemoved the number of pieces player 1 has removed
     * @param player2PiecesRemoved the number of pieces player 2 has removed
     */
    public static void setBoard(String states, int player1PiecesPlaced, int player2PiecesPlaced, int player1PiecesRemoved, int player2PiecesRemoved) {
        Board.loadState(states);
        Game.setPlayerPiecesPlaced(1, player1PiecesPlaced);
        Game.setPlayerPiecesPlaced(2, player2PiecesPlaced);
        Game.setPlayerPiecesRemoved(1, player1PiecesRemoved);
        Game.setPlayerPiecesRemoved(2, player2PiecesRemoved);
    }

    /**
     * Method to set the scene for the tutorial.
     * @param turn the turn of the game
     * @param phase the phase of the game
     * @param player1Jump if the player can jump
     * @param player2Jump if the player can jump
     */
    public static void setScene(int turn, int phase, boolean player1Jump, boolean player2Jump) {
        Game.setTurn(turn);
        Game.setPhase(phase);
        Game.setPlayerJump(1, player1Jump);
        Game.setPlayerJump(2, player2Jump);
    }

    /**
     * Method to start the tutorial.
     */
    public static void start() {
        System.out.println("Welcome to the tutorial!");
        System.out.println("This is a two player game, where each player has 9 pieces.");
        System.out.println("The game is played in 3 phases on a board with 24 intersections.");
        System.out.println("***For demonstration purposes, only one player interacts with the board and goes through the three phases.***");
        Menu.detect();

        part1();
        part2();
        part3();
        part4();

        System.out.println("This concludes the tutorial. Good luck!");

        //reset the board
        setBoard("000000000000000000000000", 0, 0, 0, 0);
        setScene(1, 1, false, false);
        Mill.resetMill();
        Menu.setTutorial(false);
        Menu.detect();
    }

    /**
     * Part 1 of Tutorial:
     * Placing first piece on the board.
     */
    public static void part1(){

        Scanner input = new Scanner(System.in);
        int numberOfTry = 0;
        String selection = "";

        setBoard("000000000000000000000000", 0, 0, 0, 0);
        setScene(1, 1, false, false);
        board.print();

        System.out.println("[Phase 1: Placing] Player 1 (you!) begins by placing their piece at any vacant point.");
        System.out.println("You should attempt to place your first piece at A1.");
        while (!selection.equals("A1")) {
            if (!selection.equals("")) {
                System.out.println("Please try again.");
                numberOfTry+=1;
                if (numberOfTry >=3){
                    System.out.println("Hints: The alphabet (A..G) represents the board's Column, while the number represents the row.");
                    System.out.println("Try typing the word 'A1'.");
                }
            }
            System.out.print("Select point to place your piece: ");
            selection = input.nextLine();
        }

        Action.place("A1");
        board.print();

        System.out.println("Well done! You have placed your first piece.");
        Menu.detect();

        /*-------------------------------------------------------------------------------*/
        int numberOfTry2 = 0;
        String selection2 = "";
        setBoard("110000000000000020000200", 2, 2, 0, 0);
        setScene(1, 1, false, false);
        board.print();

        System.out.println("Now lets try to create a mill and remove a piece belongs to player 2.");
        System.out.println("[Phase 1: Placing(Form a mill and Remove a piece)] Player 1 (you!) begins by placing their piece at any vacant point.");
        System.out.println("Try to place a piece at G1 then remove the opponent piece at D5");
        while (!selection2.equals("G1")) {
            if (!selection2.equals("")) {
                System.out.println("Please try again.");
                numberOfTry2+=1;
                if (numberOfTry2 >=3){
                    System.out.println("Hints: The alphabet (A..G) represents the board's Column, while the number represents the row.");
                    System.out.println("Try typing the word 'G1'.");
                }
            }
            System.out.print("Select a piece on the board: ");
            selection2 = input.nextLine();
        }

        System.out.println("-----------------------------------------");
        System.out.println("Remove the piece at D5 belong to player 2");
        System.out.println("-----------------------------------------");
        Action.place(selection2);

        board.print();
        System.out.println("Well done! You have removed your first piece by placing a piece.");
        Menu.detect();

    }

    /**
     * Part 2 of Tutorial:
     * Moving a piece.
     */
    public static void part2() {
        Scanner input = new Scanner(System.in);
        String selection = "";
        String selection1 = "";
        String selection2 = "";
        int numberOfTry = 0;

        setBoard("111121121010010020002200", 9, 9, 0, 4);
        setScene(1, 2, false, false);
        board.print();

        System.out.println("***After placing all the 9 pieces by player 1 and player 2 -> Moving Enabled ***");
        System.out.println("[Phase 2: Moving] Player 1 (you!) begins by selecting your piece and move your piece at any adjacent vacant point.");
        System.out.println("You should attempt to select your piece at A1 and move the piece to A4.");
        while (!selection1.equals("A1") || !selection2.equals("A4")) {
            if (!selection1.equals("") || !selection2.equals("")) {
                System.out.println("Please try again.");
                numberOfTry+=1;
                if (numberOfTry >=3){
                    System.out.println("Hints: The alphabet (A..G) represents the board's Column, while the number represents the row.");
                    System.out.println("Try typing the word 'A1 A4' which represent the [start location] [end location] on the board.");
                }
                else{ //give hints
                    if (!selection1.equals("A1") && selection2.equals("A4")){
                        System.out.println("-----------------------------------------------------");
                        System.out.println("Your start location is wrong based on what I mention.");
                        System.out.println("Your end location is correct based on what I mention.");
                        System.out.println("-----------------------------------------------------");
                    }else if (!selection2.equals("A4") && selection1.equals("A1")){
                        System.out.println("-------------------------------------------------------");
                        System.out.println("Your start location is correct based on what I mention.");
                        System.out.println("Your end location is wrong based on what I mention.");
                        System.out.println("-------------------------------------------------------");
                    } else if (!selection1.equals("A1") && !selection2.equals("A4")) {
                        System.out.println("-------------------------------------------------------------");
                        System.out.println("Both start and end location is wrong based on what I mention.");
                        System.out.println("-------------------------------------------------------------");
                    }
                }
            }
            System.out.print("Select a piece move start and end locations (eg: A1 D1): ");
            selection = input.nextLine();
            try {
                String[] selections = selection.split(" ");
                selection1 = selections[0];
                selection2 = selections[1];
            } catch (Exception e) {}
        }
        Action.move(selection1, selection2);
        board.print();
        System.out.println("Well done! You have moved your first piece.");
        Menu.detect();
    }

    /**
     * Part 3 of Tutorial:
     * Jumping a piece.
     */
    public static void part3() {
        Scanner input = new Scanner(System.in);
        String selection = "";
        String selection1 = "";
        String selection2 = "";
        int numberOfTry = 0;
        setBoard("100101000000000000002202", 9, 9, 6, 6);
        setScene(1, 3, true, true);
        board.print();

        System.out.println("***Player 1 left 3 pieces on the board -> Player 1 Jumping Enabled***");
        System.out.println("[Phase 3: Jumping] Player 1 (you!) begins by selecting your piece and jump to any vacant point.");
        System.out.println("You should attempt to select your piece at A1 and jump to location D7.");

        while (!selection1.equals("A1") || !selection2.equals("D7")) {
            if (!selection1.equals("") || !selection2.equals("")) {
                System.out.println("Please try again.");
                numberOfTry+=1;
                if (numberOfTry >=3){
                    System.out.println("Hints: The alphabet (A..G) represents the board's Column, while the number represents the row.");
                    System.out.println("Try typing the word 'A1 D7' which represent the [start location] [end location] on the board.");
                }
                else{ //give hints
                    if (!selection1.equals("A1") && selection2.equals("D7")){
                        System.out.println("-----------------------------------------------------");
                        System.out.println("Your start location is wrong based on what I mention.");
                        System.out.println("Your end location is correct based on what I mention.");
                        System.out.println("-----------------------------------------------------");
                    }else if (!selection2.equals("D7") && selection1.equals("A1")){
                        System.out.println("-------------------------------------------------------");
                        System.out.println("Your start location is correct based on what I mention.");
                        System.out.println("Your end location is wrong based on what I mention.");
                        System.out.println("-------------------------------------------------------");
                    } else if (!selection1.equals("A1") && !selection2.equals("D7")) {
                        System.out.println("-------------------------------------------------------------");
                        System.out.println("Both start and end location is wrong based on what I mention.");
                        System.out.println("-------------------------------------------------------------");
                    }
                }
            }
            System.out.print("Select a piece jump start and end locations (eg: A1 D1): ");
            selection = input.nextLine();
            try {
                String[] selections = selection.split(" ");
                selection1 = selections[0];
                selection2 = selections[1];
            } catch (Exception e) {}
        }
        Action.jump(selection1, selection2);
        board.print();
        System.out.println("Well done! You have jumped your first piece.");
        Menu.detect();
    }

    /**
     * Part 4 of Tutorial:
     * Winning the Game
     */
    public static void part4(){

        System.out.println("[Winning] Once a player has two pieces left, the game will end.");
        System.out.println("The player with two pieces left will lose the game whilst the other player wins.");
        Menu.detect();

    }

}
