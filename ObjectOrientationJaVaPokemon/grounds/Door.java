package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;
import game.enums.MapName;
import game.enums.Status;
import game.teleport.TeleportPerception;
import game.teleport.TeleportPerceptionManager;

/**
 * This ground class where the teleport between map.
 *
 * @author Edmund
 */
public class Door extends Ground implements TeleportPerception {
    /**
     * The door location
     */
    private Location doorLocation;

    /**
     * TeleportPerceptionManager instance.
     */
    private final TeleportPerceptionManager teleportManager;

    /**
     * Constructor.
     */
    public Door() {
        super('=');
        teleportManager = TeleportPerceptionManager.getInstance();
        registerInstance();
    }

    /**
     * The actions that can be performed by the player.
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return a list of actions.
     */
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        teleportManager.getTeleportPerceptionList();
        ActionList actions = new ActionList();
        if (actor.hasCapability(Status.TELEPORT) && location.containsAnActor()) {
            for (Door elem : teleportManager.getTeleportPerceptionList()) {
                if (elem.getDoorLocation() != location && elem.getDoorLocation() != null) { //here we know which door is belongs to which map.
                    location.getGround().addCapability(MapName.PALLETTOWN);
                    elem.getDoorLocation().getGround().addCapability(MapName.POKEMONCENTER);
                    actions.add(new TeleportAction(actor, location, elem.getDoorLocation()));
                    break;
                }
            }
        }
        return actions;
    }

    /**
     * A setter that change the door location.
     * @param doorLocation
     */
    public void setDoorLocation(Location doorLocation) {
        this.doorLocation = doorLocation;
    }

    /**
     * A getter that get the door location.
     * @return the door location.
     */
    public Location getDoorLocation() {
        return doorLocation;
    }

    /**
     * This method will get all the door location created in the map every turn.
     * @param location The location of the Ground
     */
    public void tick(Location location) {
        setDoorLocation(location);
    }
}