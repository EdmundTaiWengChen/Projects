package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.ElementsHelper;
import game.enums.Element;
import game.enums.Status;


/**
 * A weaponItem for Pokemon special attack
 * <p>
 * Created by:
 *
 * @author Chew Jia Hong
 */
public class Fire extends WeaponItem {

    /**
     * Turns left before extinguishing
     */
    private int life;

    /**
     * Maximum turns of existence
     */
    private static final int LIFETIME = 2;

    /**
     * Constructor
     */
    public Fire() {

        super("Fire", 'v', 10, "scorches", 100);
        addCapability(Element.FIRE);
        life = LIFETIME;

    }

    /**
     * Damages non-fire actor, updates life or extinguish
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {

        Actor actor = currentLocation.getActor();

        if (actor != null) {
            if (!(ElementsHelper.hasAnySimilarElements(this,
                    actor.findCapabilitiesByType(Element.class)) || actor.hasCapability(Status.IMMUNE))) {

                actor.hurt(damage());
                String result = this + " " + verb() + " " + actor + " for " + damage() + " damage";
                System.out.println(result);
            }
        }

        life -= 1;

        if (life <= 0) {
            currentLocation.removeItem(this);
        }

    }

}
