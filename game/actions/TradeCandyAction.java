package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.enums.ItemStatus;

import java.util.ArrayList;

/**
 * A class to handle all trade cand
 * <p>
 * Created by: Avinash Rvan (32717792)
 */
public class TradeCandyAction extends TradeAction {

    /**
     * This class is specifically for trades where the player wishes to trade candies
     *
     * @param beingReturned
     * @param cost
     */
    public TradeCandyAction(Item beingReturned, int cost) {
        super(beingReturned, cost);
    }

    /**
     * Checks if the player has enough candies for the trade they wish to perform.
     * If they do, place the item in their inventory and remove the corresponding number of candies.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        ArrayList<Item> candyList = new ArrayList();
        String res;

        // check how many candies the player has
        for (Item item : actor.getInventory()) {
            if (item.hasCapability(ItemStatus.TRADEABLE)) {
                candyList.add(item);
            }
        }

        // if the player has enough candies then execute
        if (candyList.size() >= cost) {
            // add the item to player's inventory
            actor.addItemToInventory(beingReturned);

            // remove the number of candies from the player's inventory
            int numRemoved = 1;
            for (Item item : candyList) {
                if (numRemoved <= cost) {
                    actor.removeItemFromInventory(item);
                    numRemoved++;
                } else {
                    break;
                }
            }

            res = actor + " has successfully traded " + this.cost + " candies for a " + this.beingReturned;
        }
        // insufficient candies
        else {
            res = "Transaction failed: " + actor + " had insuffcient candies!";
        }

        return res;
    }

    /**
     * Returns a description of the actor trying to trade, the item that can be obtained, and how many candies it will cost
     *
     * @param actor The actor performing the action.
     * @return a description of the actor trying to trade, the item that can be obtained, and how many candies it will cost
     */
    @Override
    public String menuDescription(Actor actor) {
        String res = actor + " trades " + this.beingReturned + " with " + this.cost + " candies";

        return res;
    }
}
