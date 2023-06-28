package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import game.enums.Element;

/**
 * This ground class is just a hay.
 *
 * @author Edmund
 */
public class Hay extends Ground {

    /**
     * Constructor.
     */
    public Hay() {
        super(',');
        this.addCapability(Element.GRASS);
    }
}