import java.util.Scanner;

/**
 * Class to handle the menu of the game, including the welcome menu and the game menu.
 * Handles the input of the user and calls the appropriate methods.
 */
public class Menu {

    static boolean welcomeMenu = true; // true = show tutorial and rules selection, false = show game menu
//    static boolean againstComputer = false; // true = against computer, false = against player
    static Game game = Game.getInstance(); // singleton

    static boolean tutorial = false;

    public static void setTutorial(boolean tutorial) {
        Menu.tutorial = tutorial;
    }

    /**
     * Method to display the welcome menu of the game.
     */
    public static int welcomeMenu() {
        System.out.println("+------------------------------------------------+");
        System.out.println("| Welcome to Nine Men's Morris!                  |");
        System.out.println("| View details or start the game.                |");
        System.out.println("+------------------------------------------------+");
        System.out.println("| 1. Details & Rules                             |");
        System.out.println("| 2. Tutorial                                    |");
        System.out.println("| 3. Start Game (Against Player)                 |");
        System.out.println("| 0. End Game                                    |");
        System.out.println("+------------------------------------------------+");
        return inputPrompt();
    }

    /**
     * Method to display the game menu of the game.
     */
    public static int gameMenu() {
        Board board = Board.getInstance();
        System.out.println();
        board.print();
        System.out.println("+------------------------------------------------+");
        System.out.println("| Player " + Game.getTurn() + "'s Turn                                |");
        System.out.println("+------------------------------------------------+");
        System.out.println("| 1. Show Hints                                  |");
        if (Game.getPhase() == 1) {
            System.out.println("| 2. Place                                       |");
        } else if (Game.getPlayerJump(Game.getTurn())) {
            System.out.println("| 2. Jump                                        |");
        } else if (Game.getPhase() == 2) {
            System.out.println("| 2. Move                                        |");
        }
        System.out.println("| 3. Undo                                        |");
        System.out.println("| 0. End Game                                    |");
        System.out.println("+------------------------------------------------+");
        return inputPrompt();
    }

    /**
     * Method to take in input from the player.
     * @return the input of the player.
     */
    public static int inputPrompt() {
        boolean valid = false;
        int selection = 0;

        do {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Please select an option: ");
                selection = input.nextInt();
                valid = true;
            }
            catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!valid);

        return selection;
    }

    /**
     * Method to process the input of the player.
     */
    public static void prompt() {
        int selection = 0;
        do {
            if (welcomeMenu && !tutorial) {
                selection = Menu.welcomeMenu();
                if (selection == 1) {
                    rules();
                }
                else if (selection == 2) {
                    tutorial = true;
                }
                else if (selection == 3) {
                    startAgainstPlayer();
                }
                else if (selection == 0) {
                    end();
                }
                else {
                    System.out.println("Invalid input. Please try again.");
                    detect();
                }
            }
            else if (!welcomeMenu && !tutorial) {
                selection = Menu.gameMenu();
                if (selection == 1) {
                    hints();
                }
                else if (selection == 2) {
                    action();
                }
                else if (selection == 3) {
                    undo();
                }
                else if (selection == 4) {
                    Game.skipToPhase2();
                }
                else if (selection == 5) {
                    Game.skipToPhase2End();
                }
                else if (selection == 0) {
                    end();
                }
                else {
                    System.out.println("Invalid input. Please try again.");
                    detect();
                }
            }
            else if (tutorial){
                Tutorial.start();
            }
        } while (selection != 0);

    }

    /**
     * Method to prompt the player to press the enter key to continue.
     */
    public static void detect() {
        System.out.print("Press [ENTER] key to continue...");
        Scanner scanner = new Scanner(System.in);
        String readString = scanner.nextLine();
        while (readString != null) {
            System.out.println(readString);
            if (readString.isEmpty()) {
                break;
            }
            else {
                System.out.print("Press [ENTER] key to continue...");
                readString = scanner.nextLine();
            }
        }
    }

    /**
     * Method to show the details and rules of the game.
     */
    public static void rules() {
        System.out.println("Each of the two players has 9 pieces (tokens or men) that, in turn, are placed on the board " +
                "on one of the empty 24 line intersections, starting with an empty board. Once all 18 pieces\n" +
                "have been placed onto the board, players, again in turn, slide one of their pieces along a " +
                "board line to an empty adjacent intersection (not diagonally). If a player is able to form a\n" +
                "straight row of three pieces along one of the board's lines (i.e. not diagonally), he/she has a " +
                "“mill” and may remove one of his/her opponent's pieces from the board that is not part of a\n" +
                "mill (Note: rules vary slightly on the removal of pieces that are in a ‘mill’). This can happen " +
                "either during the initial placing of the pieces onto the board or the subsequent sliding of\n" +
                "pieces along the board’s lines. A piece that has been removed from the board cannot be " +
                "placed again and is “lost” for the corresponding player. Once a player has only three pieces\n" +
                "left, he/she may jump (fly or hop) one piece per turn to an empty intersection (and hence " +
                "does not have to slide a piece along one of the board’s lines). The objective of the game\n" +
                "is to leave the opposing player with fewer than three pieces or no legal moves. Hence, once " +
                "one of the players has achieved such a situation, he/she wins the game.");
        detect();
    }

    /**
     * Method to start the game against another player.
     */
    public static void startAgainstPlayer() {
        welcomeMenu = false;
    }

    /**
     * Method to print the hints for the player.
     */
    public static void hints() {
        if (Game.getPhase() == 1) {
            System.out.println("Hint! You may place your piece at any vacant spaces: ");
            System.out.println(Hint.printVacant());
        } else if (Game.getPlayerJump(Game.getTurn())) {
            System.out.println("Hint! You may jump your piece to any vacant spaces: ");
            System.out.println(Hint.printVacant());
        } else if (Game.getPhase() == 2) {
            System.out.println("Hint! You may move your pieces to any vacant spaces: ");
            Hint.getValidMoves(Game.getTurn());
        }
    }

    /**
     * This method is used to perform the action selected by the player.
     */
    public static void action() {
        if (Game.getPhase() == 1) {
            Scanner input = new Scanner(System.in);
            System.out.print("Select a location to place your piece at: ");
            String selection = input.nextLine();
            Action.place(selection);
        } else if (Game.getPlayerJump(Game.getTurn())) {
            Scanner input = new Scanner(System.in);
            System.out.print("Select a piece jump start and end locations (eg: A1 G7): ");
            String selection = input.nextLine();
            try {
                String[] selections = selection.split(" ");
                String selection1 = selections[0];
                String selection2 = selections[1];
                Action.jump(selection1, selection2);
            }
            catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                detect();
            }
        } else if (Game.getPhase() == 2) {
            Scanner input = new Scanner(System.in);
            System.out.print("Select a piece move start and end locations (eg: A1 D1): ");
            String selection = input.nextLine();
            try {
                String[] selections = selection.split(" ");
                String selection1 = selections[0];
                String selection2 = selections[1];
                Action.move(selection1, selection2);
            }
            catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                detect();
            }
        }
    }

    /**
     * Method to undo the previous action.
     * #!#!#!# OUR TEAM IS NOT IMPLEMENTING THIS ADVANCED REQUIREMENT. #!#!#!#
     */
    public static void undo() {
        System.out.println("Advanced Requirement B - OUR TEAM WILL NOT IMPLEMENT THIS");
        detect();
    }

    /**
     * Method to end the game.
     */
    public static void end() {
        System.out.println("Thanks for playing!");
        detect();
        System.exit(0);
    }

}