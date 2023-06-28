package game.enums;

/**
 * This class represents the status for items to check what they are used for
 *
 * @author Avinash Rvan(32717792)
 */
public enum ItemStatus {
    CAN_FEED, // used for items which can be used to feed
    TRADEABLE, // for items that can be traded with NurseJoy
    CAN_HATCH, // the item itself can be change into a pokemon
    HAS_EFFECT, // for items that can grant or inflict effects on others
}
