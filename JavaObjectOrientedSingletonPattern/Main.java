public class Main {

    public static void main(String[] args) {

        // initialize empty board
        Board board = Board.getInstance();
        board.init();

        // prompt user for input
        Menu.prompt();

    }

}