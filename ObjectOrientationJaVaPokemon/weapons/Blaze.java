package game.weapons;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.enums.Element;


/**
 * A WeaponItem for Pokemon special attack
 * <p>
 * Created by:
 * @author Chew Jia Hong
 */
public class Blaze extends WeaponItem {

    /**
     * Constructor.
     */
    public Blaze() {
        super("Blaze", ' ', 60, "blazes", 90);
        addCapability(Element.FIRE);
    }

}
