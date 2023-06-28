package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.List;
import java.util.Random;

/**
 * A class that represents the behaviour of NPC where they will pick up any items on the ground currently
 *
 * @author Avinash Rvan (32717792)
 */
public class PickUpItemBehaviour implements Behaviour {
    /**
     * Priority key for treemap
     */
    public static final int PRIORITY = 3;

    /**
     * Random number generator
     */
    private final Random random = new Random();

    /**
     * Will check if there are any items currently at the actor's location.
     * If yes, will return an action for the actor to pick it up
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return PickUpItemAction or null
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // get the list of all the items at the current location
        List<Item> itemsList = map.locationOf(actor).getItems();

        // choose one of the items to pick up at random
        if (itemsList.size() > 0) {
            return itemsList.get(random.nextInt(itemsList.size())).getPickUpAction(actor);
        } else {
            // if there are no items on the ground, then return null
            return null;
        }


    }
}
