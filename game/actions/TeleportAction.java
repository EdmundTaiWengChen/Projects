package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.MapName;

/**
 * An Action to teleport player to another map.
 * <p>
 * Created by:
 * @author Edmund
 */
public class TeleportAction extends Action {
    /**
     * The Actor that is to be teleported.
     */
    private final Actor actor;

    /**
     * The Location of the door where the actor come from.
     */
    private final Location doorFrom;

    /**
     * The Location of the door where the actor going to.
     */
    private final Location doorTo;

    /**
     * Constructor
     * @param actor the player
     * @param doorFrom the location of the door where the player currently at.
     * @param doorTo the location of the door where the player going to.
     */
    public TeleportAction(Actor actor, Location doorFrom, Location doorTo) {
        this.actor = actor;
        this.doorFrom = doorFrom;
        this.doorTo = doorTo;
    }

    /**
     * Execute TeleportAction.
     * @param actor The actor performing the action.
     * @param mapCurrent The map the actor is on.
     * @return the actor teleport to another map.
     */
    @Override
    public String execute(Actor actor, GameMap mapCurrent) {

        String result;
        GameMap mapTo = doorTo.map();
        mapCurrent.removeActor(actor);
        mapTo.moveActor(actor, doorTo);
        if (doorTo.getGround().hasCapability(MapName.POKEMONCENTER)) {
            result = actor + "has teleport to Pokemon Center.";
        } else {
            result = actor + "has teleport to Pallet town.";
        }

        return result;
    }

    /**
     * Display message of TeleportAction on the console menu
     *
     * @param actor The actor performing the action.
     * @return the map where the player teleport to.
     */
    @Override
    public String menuDescription(Actor actor) {
        if (doorTo.getGround().hasCapability(MapName.PALLETTOWN)) {
            return actor + " enters Pallet Town";
        } else {
            return actor + " enters Pokemon Center";
        }

    }
}
