package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;


/**
 * An interface for WeaponItem capable of effects
 * <p>
 * Currently capable of ground effects, more effects can be
 * integrated into this or similar interfaces in the future
 * <p>
 * Created by:
 * @author Chew Jia Hong
 */
public interface EffectCapable {

    /**
     * Executes any special effects
     *
     * @param actor Wielder of WeaponItem
     * @param map   Map of wielder
     */
    void execute(Actor actor, GameMap map);

}
