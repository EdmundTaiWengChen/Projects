package game.pokemons;


import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.behaviours.EvolveBehaviour;
import game.enums.Element;
import game.enums.Status;
import game.time.TimePerception;
import game.weapons.Ember;

/**
 * Class for Charmander instances
 * <p>
 * Created by:
 * @author Chew Jia Hong
 */
public class Charmander extends Pokemon implements EvolveCapable, TimePerception {

    /**
     * Constructor.
     */
    public Charmander() {

        super("Charmander", 'c', 100);

        addCapability(Element.FIRE);
        addCapability(Status.CAN_EVOLVE);

        getBackupWeapons().add(new Ember());
        addBehaviour(EvolveBehaviour.PRIORITY, new EvolveBehaviour());
        setEggHatchTurns(4);

        registerInstance();     // time perception

    }

    /**
     * @return a new intrinsicWeapon for Charmander
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(10, "scratch");
    }

    /**
     * @return a new Charmeleon instance
     */
    public Pokemon evolve() {
        return new Charmeleon();
    }

    /**
     * Effect of day on this Charmander
     */
    public void dayEffect() {
        heal(10);
    }

    /**
     * Effect of night on this Charmander
     */
    public void nightEffect() {
        hurt(10);
    }

}
