package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;

/**
 * This ground class is a wall that block actor path.
 *
 * @author Edmund
 */
public class Wall extends Ground {
    /**
     * The Constructor.
     */
    public Wall() {
        super('#');
    }

    /**
     * A method where actor cannot enter.
     * @param actor the Actor to check
     * @return false
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    /**
     * Object cannot be on this object.
     * @return true.
     */
    @Override
    public boolean blocksThrownObjects() {
        return true;
    }
}
