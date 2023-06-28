package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.enums.Element;
import game.enums.ItemStatus;


/**
 * A WeaponItem for special attack
 * <p>
 * Created by:
 * @author Chew Jia Hong
 */
public class FireSpin extends WeaponItem implements EffectCapable {

    /**
     * Constructor.
     */
    public FireSpin() {

        super("FireSpin", ' ', 70, "burns", 90);
        addCapability(Element.FIRE);
        addCapability(ItemStatus.HAS_EFFECT);

    }

    /**
     * Sets the ground on all exits on fire
     *
     * @param actor Wielder of WeaponItem
     * @param map   Map of wielder
     */
    public void execute(Actor actor, GameMap map) {

        Location here = map.locationOf(actor);

        for (Exit exit : here.getExits()) {

            Location destination = exit.getDestination();
            destination.addItem(new Fire());

        }

    }

}
