package game.teleport;

import game.grounds.Door;
import java.util.ArrayList;
import java.util.List;

/**
 * A global Singleton manager that gives Door object on the affected instances.
 * <p>
 * Created by:
 * @author Edmund
 * Modified by:
 * @author Edmund
 */

public class TeleportPerceptionManager {
    /**
     * A list of polymorph instances (any classes that implements TeleportPerception,
     * such as, a Door implements TeleportPerception, it will be stored in here)
     */
    private final List<Door> teleportPerceptionList; // A list that store all the door object that is created in the map.

    /**
     * A singleton instance
     */
    private static TeleportPerceptionManager instance;

    /**
     * Get the singleton instance of teleport perception manager
     *
     * @return TeleportPerception singleton instance
     *
     */
    public static TeleportPerceptionManager getInstance() {
        if (instance == null) {
            instance = new TeleportPerceptionManager();
        }
        return instance;
    }

    /**
     * Private constructor
     */
    private TeleportPerceptionManager() {
        teleportPerceptionList = new ArrayList<>();
    }

    /**
     * Add the Door instance to the list
     *
     * @param objInstance any instance that implements TeleportPerception
     */
    public void append(Door objInstance) {
        teleportPerceptionList.add(objInstance);
    }

    /**
     * A getter that return the list of teleportPerceptionList.
     *
     * @return teleportPerceptionList. A list of door object that created in the map.
     */
    public List<Door> getTeleportPerceptionList() {
        return teleportPerceptionList;
    }

    /**
     * Remove a TeleportPerception instance from the list
     *
     * @param objInstance object instance
     */
    public void cleanUp(Door objInstance) {
        teleportPerceptionList.remove(objInstance);
    }
}
