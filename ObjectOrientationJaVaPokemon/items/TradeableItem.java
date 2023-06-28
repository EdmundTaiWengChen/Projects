package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.enums.ItemStatus;

/**
 * This abstract class represents all the tradeable items that the player can interact with
 *
 * @author Avinash Rvan(32717792)
 */
public abstract class TradeableItem extends Item {

    /**
     * A parent class for all items which can be traded with NurseJoy
     *
     * @param name        name of the item
     * @param displayChar the display character which will be shown on the map
     * @param portable    a boolean determining if the item can be dropped
     */
    public TradeableItem(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
        addCapability(ItemStatus.TRADEABLE);
    }
}
