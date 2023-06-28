import java.util.ArrayList;
import java.util.Random;

/**
 * Game class to handle the game logic, its timeline and its states.
 */
public class Game {

    // attributes

    private static Game game; // singleton
    private static Board board = Board.getInstance(); // singleton

    private static int turn = 1; // 1 = player 1's turn, 2 = player 2's turn
    private static int phase = 1; // 1 = placing phase, 2 = moving phase
    private static boolean player1Jump = false; // whether player 1 can jump
    private static boolean player2Jump = false; // whether player 2 can jump

    // to check if player has placed 9 pieces to progress to phase 2
    private static int player1PiecesPlaced = 0;
    private static int player2PiecesPlaced = 0;

    // to check if player has 6 pieces removed to progress to phase 3
    // or if player has 7 pieces removed to lose the game
    private static int player1PiecesRemoved = 0;
    private static int player2PiecesRemoved = 0;

    private Game() {};

    /**
     * Singleton pattern to ensure only one instance of Game is created.
     * @return
     */
    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    // setters / mutators

    /**
     * Set the phase of the game.
     * @param phase 1 = placing phase, 2 = moving phase
     */
    public static void setPhase(int phase) {
        Game.phase = phase;
    }

    /**
     * Set the turn of the game.
     */
    public static void setTurn(int turn) {
        Game.turn = turn;
    }

    /**
     * Add the number of pieces placed by a player.
     * Increase and does not decrease.
     * @param player 1 = player 1, 2 = player 2
     */
    public static void addPlayerPiecesPlaced(int player) {
        if (player == 1) {
            player1PiecesPlaced++;
        } else {
            player2PiecesPlaced++;
        }
    }

    /**
     * Add the number of pieces removed by a player.
     * Increase and does not decrease.
     * @param player 1 = player 1, 2 = player 2
     */
    public static void addPlayerPiecesRemoved(int player) {
        if (player == 1) {
            player1PiecesRemoved++;
        } else {
            player2PiecesRemoved++;
        }
    }

    /**
     * Set the number of pieces placed by a player.
     * @param player 1 = player 1, 2 = player 2
     */
    public static void setPlayerPiecesPlaced(int player, int pieces) {
        if (player == 1) {
            player1PiecesPlaced = pieces;
        } else {
            player2PiecesPlaced = pieces;
        }
    }

    /**
     * Set the number of pieces removed by a player.
     * @param player 1 = player 1, 2 = player 2
     */
    public static void setPlayerPiecesRemoved(int player, int pieces) {
        if (player == 1) {
            player1PiecesRemoved = pieces;
        } else {
            player2PiecesRemoved = pieces;
        }
    }

    /**
     * Set whether a player can jump.
     * @param player 1 = player 1, 2 = player 2
     * @param jump true = player can jump, false = player cannot jump
     */
    public static void setPlayerJump(int player, boolean jump) {
        if (player == 1) {
            player1Jump = jump;
        } else {
            player2Jump = jump;
        }
    }

    // getters / accessors

    /**
     * Get the phase of the game.
     * @return 1 = placing phase, 2 = moving phase
     */
    public static int getPhase() {
        return phase;
    }

    /**
     * Get the turn of the game.
     * @return 1 = player 1, 2 = player 2
     */
    public static int getTurn() {
        return turn;
    }

    /**
     * Get the number of pieces placed by a player.
     * @param player 1 = player 1, 2 = player 2
     * @return number of pieces placed by a player
     */
    public static int getPlayerPiecesPlaced(int player) {
        if (player == 1) {
            return player1PiecesPlaced;
        } else {
            return player2PiecesPlaced;
        }
    }

    /**
     * Get the number of pieces removed by a player.
     * @param player 1 = player 1, 2 = player 2
     * @return number of pieces removed by a player
     */
    public static int getPlayerPiecesRemoved(int player) {
        if (player == 1) {
            return player1PiecesRemoved;
        } else {
            return player2PiecesRemoved;
        }
    }

    /**
     * Get whether a player can jump.
     * @param player 1 = player 1, 2 = player 2
     * @return true = player can jump, false = player cannot jump
     */
    public static boolean getPlayerJump(int player) {
        if (player == 1) {
            return player1Jump;
        } else {
            return player2Jump;
        }
    }

    // methods

    /**
     * Print state the number of times the piece is removed
     * @param player 1 = player 1, 2 = player 2
     */
    public static String getRemoved(int player){
        String removed = "";
        if (player == 1){
            for (int i = 0; i < player1PiecesRemoved; i++){
                removed += "X";
            }
        } else if (player == 2){
            for (int i = 0; i < player2PiecesRemoved; i++){
                removed += "O";
            }
        }
        return removed;
    }

    /**
     * Print state the number of times the pieces are unplaced
     * @param player 1 = player 1, 2 = player 2
     */
    public static String getUnplaced(int player){
        String unplaced = "";
        if (player == 1){
            for (int i = 0; i < 9 - player1PiecesPlaced; i++){
                unplaced += "X";
            }
        } else if (player == 2){
            for (int i = 0; i < 9 - player2PiecesPlaced; i++){
                unplaced += "O";
            }
        }
        return unplaced;
    }

    /**
     * Switches the turn from player 1 to player 2 or vice versa
     */
    public static void nextTurn() {
        if (turn == 1) {
            turn = 2;
        } else {
            turn = 1;
        }
    }

    /**
     * Checks the progress of the game and updates the phase if necessary
     */
    public static void checkProgress(){
        // if phase 1 and both players have placed 9 pieces, go to phase 2
        if (player1PiecesPlaced == 9 && player2PiecesPlaced == 9){
            phase = 2;
        }

        // if phase 2 and player has 3 pieces left, allow player to jump (individual phase 3)
        if (player1PiecesRemoved == 6){
            player1Jump = true;
        }
        if (player2PiecesRemoved == 6){
            player2Jump = true;
        }

        // check if a player has won the game
        if (player1PiecesRemoved == 7){
            System.out.println("Player 2 wins!");
            Menu.detect();
            System.exit(0);
        }
        if (player2PiecesRemoved == 7){
            System.out.println("Player 1 wins!");
            Menu.detect();
            System.exit(0);
        }
    }

    // developer testing methods

    /**
     * Skips to start of phase 2 for testing purposes
     * Fill up the rest of the board randomly with remaining number of pieces left
     */
    public static void skipToPhase2(){
        Random random = new Random();
        int randomPoint;
        while (player1PiecesPlaced <= 9){
            randomPoint = random.nextInt(24);
            if (board.getPoints().get(randomPoint).getState() == 0){
                board.getPoints().get(randomPoint).setState(1);
                player1PiecesPlaced++;
            }
        }
        while (player2PiecesPlaced <= 9){
            randomPoint = random.nextInt(24);
            if (board.getPoints().get(randomPoint).getState() == 0){
                board.getPoints().get(randomPoint).setState(2);
                player2PiecesPlaced++;
            }
        }
        phase = 2;
        System.out.println("Developer Command Triggered: Skip to Phase 2");
    }

    /**
     * Skips to end of phase 2 for testing purposes
     */
    public static void skipToPhase2End(){
        player1PiecesPlaced = 9;
        player2PiecesPlaced = 9;
        phase = 2;

        player1PiecesRemoved = 5;
        player2PiecesRemoved = 5;
        Board.loadState("010002000020101002200001");
        System.out.println("Developer Command Triggered: Skip to Phase 2 End");
    }

}