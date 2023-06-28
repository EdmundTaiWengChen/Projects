package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.items.Item;

/**
 * A class which will encompass all trade actions when interacting with Nurse Joy
 * <p>
 * Created by: Avinash Rvan (32717792)
 */
public abstract class TradeAction extends Action {

    /**
     * The item which the player wants to obtain
     */
    public Item beingReturned;

    /**
     * The cost of how many items the player needs to give
     */
    public int cost;


    /**
     * Constructor setting the parameters
     *
     * @param beingReturned
     * @param cost
     */
    public TradeAction(Item beingReturned, int cost) {
        this.beingReturned = beingReturned;
        this.cost = cost;
    }

}
