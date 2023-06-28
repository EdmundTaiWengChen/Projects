package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Goh;

/**
 * A class that represents the action to print player information
 */
public class PrintPlayerStatusAction extends Action {

    /**
     * The target actor whose status needs to be printed
     */
    private final Goh target;

    /**
     * This class will print the full status of a trainer
     *
     * @param target
     */
    public PrintPlayerStatusAction(Goh target) {
        this.target = target;
    }

    /**
     * This method will execute the printing statement of the
     * Goh actor
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the result of the printing action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String ret = "";
        ret += target.fullStatusPrint();
        return ret;
    }

    /**
     * This method will return the menu description for the user to select this action
     *
     * @param actor The actor performing the action.
     * @return the menu description for the user to select this action
     */
    @Override
    public String menuDescription(Actor actor) {
        String ret = "";
        ret += "Display full status of " + target;
        return ret;
    }

    /**
     * Sets a fixed hotkey for this action which in this case is 'z'
     * @return the hotkey
     */
    public String hotkey() {
        return "z";
    }
}
