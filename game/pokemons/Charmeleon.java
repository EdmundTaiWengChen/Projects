package game.pokemons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.behaviours.EvolveBehaviour;
import game.enums.Element;
import game.enums.Status;
import game.weapons.Blaze;
import game.weapons.Ember;


/**
 * Class for Charmeleon instances
 * <p>
 * Created by:
 * @author Chew Jia Hong
 */
public class Charmeleon extends Pokemon implements EvolveCapable {

    /**
     * Constructor.
     */
    public Charmeleon() {

        super("Charmeleon", 'C', 150);

        addCapability(Element.FIRE);
        addCapability(Status.CAN_EVOLVE);

        getBackupWeapons().add(new Ember());
        getBackupWeapons().add(new Blaze());
        addBehaviour(EvolveBehaviour.PRIORITY, new EvolveBehaviour());

    }

    /**
     * @return a new intrinsicWeapon for Charmeleon
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(10, "scratch");
    }

    /**
     * @return a new Charizard instance
     */
    public Pokemon evolve() {
        return new Charizard();
    }

}
