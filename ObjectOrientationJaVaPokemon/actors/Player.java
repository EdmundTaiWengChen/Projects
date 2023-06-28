package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.AffectionManager;
import game.actions.PrintPlayerStatusAction;
import game.enums.Element;
import game.enums.Status;
import game.items.Candy;
import game.items.Pokefruit;
import game.time.TimePerceptionManager;

import java.util.List;

/**
 * Class representing the Player.
 * <p>
 * Created by:
 *
 * @author Riordan D. Alfredo
 * Modified by: Avinash Rvan, Jia Hong, Edmund
 */
public class Player extends Actor {

    /**
     * the instance of the menu for the user to select an option
     */
    private final Menu menu = new Menu();

    /**
     * Affection manager instance to check for affection points
     */
    private final AffectionManager affectionManager = AffectionManager.getInstance();

    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     */
    public Player(String name, char displayChar, int hitPoints) {

        super(name, displayChar, hitPoints);
        this.addCapability(Status.IMMUNE);
        this.addCapability(Status.TELEPORT);
        this.addCapability(Status.TRAINER);

        // initially register the player
        affectionManager.registerTrainer(this);

    }

    /**
     * Constructor
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     * @param mainTrainer set true if this player is the main trainer of the game
     */
    public Player(String name, char displayChar, int hitPoints, boolean mainTrainer) {

        super(name, displayChar, hitPoints);
        this.addCapability(Status.IMMUNE);
        this.addCapability(Status.TELEPORT);
        this.addCapability(Status.TRAINER);

        // initially register the player
        affectionManager.registerTrainer(this, mainTrainer);

    }

    /**
     * Plays the turn of the player
     * <p>
     * Since this player's playTurn is always called at the start of each round, some game manager code is written here:
     * - any time effects due to day or night are run
     * - the affection manager list is cleaned of all the pokemons which are dead
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return appropriate action
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction,
                           GameMap map, Display display) {

        // run all the day and night actions
        TimePerceptionManager.getInstance().run();

        // print out the inventory of the player
        display.println(this.printInventory());

        // if the pokemon has fainted, then run the cleanup to remove the pokemon from the affection lists
        if (!this.isConscious()) {
            affectionManager.cleanUp();
        }

        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        // add an action to allow user to print the full status of Goh
        Goh goh = Goh.getInstance();
        actions.add(new PrintPlayerStatusAction(goh));

        // return/print the console menu
        return menu.showMenu(this, actions, display);
    }

    /**
     *
     * @return the display character
     */
    @Override
    public char getDisplayChar() {
        return super.getDisplayChar();
    }

    /**
     *
     * @return a string of the full inventory of the Player
     */
    public String printInventory() {
        List<Item> inventoryList = this.getInventory();
        String ret = "";
        ret += "Player inventory: [";
        for (Item i : inventoryList) {
            ret += i + ", ";
        }
        ret += "]";
        return ret;
    }

}
