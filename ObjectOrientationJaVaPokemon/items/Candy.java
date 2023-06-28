package game.items;

/**
 * This class represents the Candy item which can be picked up from the map and traded with Nurse Joy
 *
 * @author Avinash Rvan(32717792)
 */
public class Candy extends TradeableItem {

    /**
     * This item is Tradeable with NurseJoy
     */
    public Candy() {
        super("candy", '*', true);
    }
}
