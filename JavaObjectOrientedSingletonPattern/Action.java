import java.util.Scanner;

/**
 * Class to handle the game actions.
 * This includes placing, moving, and jumping; and also removing pieces.
 */
public class Action {

    private static Board board = Board.getInstance();
    private static Game game = Game.getInstance();

    /**
     * Let the player place a piece on the board in an empty point for phase 1.
     * @param selection the point the player wants to place a piece on
     */
    public static void place(String selection) {
        boolean valid = false;
        for (int i = 0; i < 24; i++) {
            if (selection.equals(board.getPoints().get(i).getAlias()) || selection.equals(board.getPoints().get(i).getAlias2())) {
                // invalid move
                if (board.getPoints().get(i).getState() != 0) {
                    System.out.println("That point is already occupied.");
                    Menu.detect();
                    return;
                }
                if ((Game.getPhase() == 1 && Game.getTurn() == 1 && Game.getPlayerPiecesPlaced(1) == 9) || (Game.getPhase() == 1 && Game.getTurn() == 2 && Game.getPlayerPiecesPlaced(2) == 9)) {
                    System.out.println("You have already placed all of your pieces.");
                    Menu.detect();
                    Game.nextTurn();
                    return;
                }
                // valid move
                valid = true;
                board.getPoints().get(i).setState(Game.getTurn());
                if (Game.getTurn() == 1) {
                    Game.addPlayerPiecesPlaced(1);
                } else {
                    Game.addPlayerPiecesPlaced(2);
                }

                if (Mill.checkMill()) {
                    remove();
                }

                Mill.restoreMill();
                Game.nextTurn();
                Game.checkProgress();
            }
        }
        if (!valid) {
            System.out.println("That is not a valid point.");
            Menu.detect();
        }
    }

    /**
     * Let the player move a piece on the board to an empty point for phase 2.
     * @param startLocation the point the player wants to move a piece from
     * @param endLocation the point the player wants to move a piece to
     */
    public static void move(String startLocation, String endLocation) {
        int start = 0;
        int end = 0;
        for (int i = 0; i < 24; i++) {
            if (startLocation.equals(board.getPoints().get(i).getAlias()) || startLocation.equals(board.getPoints().get(i).getAlias2())) {
                start = i;
            }
            if (endLocation.equals(board.getPoints().get(i).getAlias()) || endLocation.equals(board.getPoints().get(i).getAlias2())) {
                end = i;
                end = i;
            }
        }
        if (board.getPoints().get(start).getState() != Game.getTurn()) {
            System.out.println("You do not own that piece.");
            Menu.detect();
            return;
        }
        if (board.getPoints().get(end).getState() != 0) {
            System.out.println("That point is already occupied.");
            Menu.detect();
            return;
        }
        if (board.getPoints().get(start).getMoves().contains(end)) {
            board.getPoints().get(start).setState(0); // start point to empty
            board.getPoints().get(end).setState(Game.getTurn()); // end point to player

            if (Mill.checkMill()) {
                remove();
            }

            Mill.restoreMill();
            Game.nextTurn();
            Game.checkProgress();

        } else {
            System.out.println("That is not a valid move.");
            Menu.detect();
        }
    }

    /**
     * Let the player jump a piece on the board to an empty point for phase 3.
     * @param startLocation the point the player wants to jump a piece from
     * @param endLocation the point the player wants to jump a piece to
     */
    public static void jump(String startLocation, String endLocation) {
        int start = 0;
        int end = 0;
        for (int i = 0; i < 24; i++) {
            if (startLocation.equals(board.getPoints().get(i).getAlias()) || startLocation.equals(board.getPoints().get(i).getAlias2())) {
                start = i;
            }
            if (endLocation.equals(board.getPoints().get(i).getAlias()) || endLocation.equals(board.getPoints().get(i).getAlias2())) {
                end = i;
            }
        }
        // invalid move
        if (board.getPoints().get(start).getState() != Game.getTurn()) {
            System.out.println("You do not own that piece.");
            Menu.detect();
            return;
        }
        if (board.getPoints().get(end).getState() != 0) {
            System.out.println("That point is already occupied.");
            Menu.detect();
            return;
        }
        board.getPoints().get(start).setState(0); // start point to empty
        board.getPoints().get(end).setState(Game.getTurn()); // end point to player

        if (Mill.checkMill()) {
            remove();
        }

        Mill.restoreMill();
        Game.nextTurn();
        Game.checkProgress();
    }

    /**
     * Prompt to remove an opponent piece from the board
     */
    public static void remove(){
        boolean valid = false;
        boolean printInvalidPoint = true;
        int i = 0;
        while (!valid) {
            System.out.print("Which piece would you like to remove? ");
            Scanner scanner = new Scanner(System.in);
            String selection = scanner.nextLine();
            for (i = 0; i < 24; i++) {
                if (selection.equals(board.getPoints().get(i).getAlias()) || selection.equals(board.getPoints().get(i).getAlias2())) {
                    // invalid move
                    if (board.getPoints().get(i).getState() == 0) {
                        System.out.println("That point is already empty.");
                        printInvalidPoint = false;
                        Menu.detect();
                    } else if (board.getPoints().get(i).getState() == Game.getTurn()) {
                        System.out.println("You cannot remove your own piece.");
                        printInvalidPoint = false;
                        Menu.detect();
                    } else {
                        valid = true;
                        board.getPoints().get(i).setState(0);
                        if (Game.getTurn() == 1) {
                            Game.addPlayerPiecesRemoved(2);
                        } else {
                            Game.addPlayerPiecesRemoved(1);
                        }
                    }
                }
            }
            if (!valid && printInvalidPoint) {
                System.out.println("That is not a valid point.");
                Menu.detect();
            }
        }
    }

}
