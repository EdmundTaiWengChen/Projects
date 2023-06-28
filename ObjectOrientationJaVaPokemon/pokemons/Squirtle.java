package game.pokemons;


import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.ElementsHelper;
import game.enums.Element;
import game.time.TimePerception;
import game.weapons.Bubble;

/**
 * Class for Squirtle instances
 * <p>
 * Created by:
 * @author Chew Jia Hong
 */
public class Squirtle extends Pokemon implements TimePerception {

    /**
     * Constructor.
     */
    public Squirtle() {

        super("Squirtle", 's', 100);

        this.addCapability(Element.WATER);
        getBackupWeapons().add(new Bubble());
        setEggHatchTurns(2);

        registerInstance();     // time perception

    }

    /**
     * @return a new intrinsicWeapon for Squirtle
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(10, "tackle");
    }

    /**
     * Checks for condition to give Squirtle buffs
     *
     * @param otherActor actor to be attacked by this Squirtle
     * @param map        current game map
     */
    @Override
    public void buffCheck(Actor otherActor, GameMap map) {

        Location here = map.locationOf(this);

        // equips Squirtle if standing on water ground or enemy has fire element
        toggleWeapon(ElementsHelper.hasAnySimilarElements(here.getGround(),
                this.findCapabilitiesByType(Element.class)) ||
                otherActor.hasCapability(Element.FIRE));

    }

    /**
     * Effect of day on this Squirtle
     */
    public void dayEffect() {
        hurt(10);
    }

    /**
     * Effect of night on this Squirtle
     */
    public void nightEffect() {
        heal(10);
    }

}
