package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An Action that ensures unconscious pokemons are removed from map
 * <p>
 * Created by:
 * @author Avinash Rvan
 * Modified by:
 * @author Chew Jia Hong
 */
public class DeadAction extends Action {

    /**
     * Constructor
     */
    public DeadAction() {
    }

    /**
     * Removes Actor from map
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return message description of action performed
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        String result = "";

        if (actor.isConscious()) {
            result += actor + " died of old age";
        } else {
            result += actor + " is killed";
        }

        map.removeActor(actor);

        return result;
    }

    /**
     * Display message of DeadAction on the console menu
     *
     * @param actor The actor performing the action.
     * @return DeadAction menu display
     */
    @Override
    public String menuDescription(Actor actor) {
        return "";
    }


}
