package game.teleport;

import game.grounds.Door;

/**
 * Created by:
 * @author Edmund
 * Modified by:
 * @author Edmund
 */

public interface TeleportPerception {
    /**
     * a default interface method that register current instance to the Singleton manager.
     * It allows corresponding class uses to be affected by global reset
     * TODO: Use this method at the constructor of the concrete class that implements it (`this` instance).
     *       For example:
     *       Simple(){
     *          // other stuff for constructors.
     *          this.registerInstance()  // add this instance to the relevant manager.
     *       }
     */
    default void registerInstance() {
        TeleportPerceptionManager.getInstance().append((Door) this);
    }
}

