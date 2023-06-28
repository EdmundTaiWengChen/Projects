package game.pokemons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.enums.Element;
import game.weapons.Blaze;
import game.weapons.Ember;
import game.weapons.FireSpin;


/**
 * Class for Charizard instances
 * <p>
 * Created by:
 * @author Chew Jia Hong
 */
public class Charizard extends Pokemon {

    /**
     * Constructor.
     */
    public Charizard() {

        super("Charizard", 'Z', 250);
        addCapability(Element.FIRE);
        addCapability(Element.DRAGON);
        getBackupWeapons().add(new Ember());
        getBackupWeapons().add(new Blaze());
        getBackupWeapons().add(new FireSpin());

    }

    /**
     * @return a new intrinsicWeapon for Charizard
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(10, "scratch");
    }

}
