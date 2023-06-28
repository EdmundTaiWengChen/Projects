package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.pokemons.EvolveCapable;


/**
 * An Action to evolve an actor
 * <p>
 * Created by:
 * @author Chew Jia Hong
 */
public class EvolveAction extends Action {

    /**
     * Actor to evolve
     */
    private Actor target;

    /**
     * Constructor for behavioural evolution
     *
     * @param target Pokemon to evolve
     */
    public EvolveAction(Actor target) {
        this.target = target;
    }

    /**
     * Implements EvolveCapable evolve mechanism
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return message description of performed action
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        Location location = map.locationOf(target);  // get actor location before removing
        Actor evolved = ((EvolveCapable) target).evolve();

        // Actor swap
        map.removeActor(target);
        map.addActor(evolved, location);

        return target + " has evolved into a " + evolved;
    }

    /**
     * Display message of Action on the console menu
     *
     * @param actor The actor performing the action.
     * @return Action menu display
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " evolves " + target;
    }

}
