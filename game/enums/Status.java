package game.enums;

/**
 * Use this enum class to give `buff` or `debuff`.
 * It is also useful to give a `state` to abilities or actions that can be attached-detached.
 * <p>
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Chew Jia Hong
 */
public enum Status {
    IMMUNE, // an enum to identify that an object is immune to any attack.
    HOSTILE, // use this status to be considered hostile towards enemy (e.g., to be attacked by enemy)
    CATCHABLE, // consider the pokemon always catchable
    TELEPORT, // player can teleport only
    CAN_EAT, // signals that a pokemon can be fed
    CAN_EVOLVE, // mark pokemon as evolve capable
    TRAINER, // mark actor as a trainer
}
