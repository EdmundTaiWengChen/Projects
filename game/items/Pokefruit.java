package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.enums.Element;
import game.enums.ItemStatus;

/**
 * This class represents the Pokefruit item which can be picked up from the map and then
 * be fed to the Pokemons
 *
 * @author Avinash Rvan(32717792)
 */
public class Pokefruit extends Item {

    /**
     * Stores the element of the Pokefruit
     */
    private final Enum element;

    /**
     * Constructor
     *
     * @param element element of pokefruit
     */
    public Pokefruit(Element element) {
        super((element + " Pokefruit"), 'f', true);
        this.element = element;
        addCapability(element);
        addCapability(ItemStatus.CAN_FEED);
    }

    /**
     * @return the element of pokefruit
     */
    public Enum getElement() {
        return element;
    }

}
