package game.grounds;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.ItemStatus;
import game.items.PokemonEgg;

/**
 * This ground class will incubate any PokemonEggs that are placed on top of it once every turn
 *
 * @author Avinash Rvan (32717792)
 */
public class Incubator extends Ground {
    /**
     * This ground class will incubate any PokemonEggs that are placed on top of it once every turn
     */
    public Incubator() {
        super('x');
    }

    /**
     * This method will incubate all the eggs placed on top of it once every turn
     *
     * @param location The location of the Ground
     */
    public void tick(Location location) {
        // check all the items currently on the ground
        for (Item items : location.getItems()) {
            if (items.hasCapability(ItemStatus.CAN_HATCH)) {
                // if it is hatchable, then it is an egg so downcast it and incubate it once
                PokemonEgg egg = (PokemonEgg) items;
                egg.incubate();
            }
        }
    }


}
