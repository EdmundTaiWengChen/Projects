/**
 * Hint class to print vacant spaces and valid moves for specific phases.
 */
public class Hint {

    private static Board board = Board.getInstance();

    /**
     * Method to check for vacant spaces on the board and print the alias of the vacant spaces
     * e.g. A1 B2 C3
     *
     * @return String of vacant spaces
     */
    public static String printVacant(){
        String vacant = "";
        for (int i = 0; i < board.getPoints().size(); i++){
            if (board.getPoints().get(i).getState() == 0){
                vacant += board.getPoints().get(i).getAlias() + " ";
            }
        }
        return vacant;
    }

    /**
     * Method to check for valid moves around the selected piece and print the alias of the valid moves
     * e.g. A1 -> D1 A4
     *
     * @param selected Selected piece
     * @return String Selected piece to valid moves
     */
    public static String printValidMoves(String selected){
        String output = selected + " ->";
        for (int i = 0; i < 24; i++){
            if (selected.equals(board.getPoints().get(i).getAlias())) {
                for (int j = 0; j < board.getPoints().get(i).getMoves().size(); j++) {
                    if (board.getPoints().get(board.getPoints().get(i).getMoves().get(j)).getState() == 0) {
                        output += " " + board.getPoints().get(board.getPoints().get(i).getMoves().get(j)).getAlias();
                    }
                }
            }
        }
        return output;
    }

    /**
     * Method to get valid moves for all the player's pieces
     *
     * @param player Player number
     */
    public static void getValidMoves(int player) {
        for (int i = 0; i < board.getPoints().size(); i++) {
            if (board.getPoints().get(i).getState() == player) {
                System.out.println(printValidMoves(board.getPoints().get(i).getAlias()));
            }
        }
    }

}
