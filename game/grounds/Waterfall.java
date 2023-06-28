package game.grounds;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.Element;
import game.items.Pokefruit;
import game.pokemons.Squirtle;

/**
 * This ground class will spawn Squirtle and pokefruit with element Water.
 *
 * @author Edmund
 */

public class Waterfall extends SpawnGround {

    /**
     * the chance that a squirtle will spawn out of 100
     */
    private static final int SQUIRTLESPAWNCHANCE = 20;

    /**
     * the chance that a pokefruit will spawn out of 100
     */
    private static final int POKEFRUITSPAWNCHANCE = 20;

    /**
     * Constructor.
     */
    public Waterfall() {
        super('W');
        this.addCapability(Element.WATER);
    }

    /**
     * Squirtle and pokefruit (water) will spawn at random every turn.
     * @param currentLocation The location of the Ground
     */
    public void tick(Location currentLocation) {
        int sameElementSurrounding = 0;
        Squirtle squirtle = new Squirtle();

        for (Exit exit : currentLocation.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getGround().hasCapability(Element.WATER)) {
                sameElementSurrounding += 1;
            }
        }
        if (!currentLocation.containsAnActor() && sameElementSurrounding > 2) {
            spawnPokemon(squirtle, SQUIRTLESPAWNCHANCE, currentLocation);
        }
        Pokefruit pokefruit = new Pokefruit(Element.WATER);
        spawnItem(pokefruit, POKEFRUITSPAWNCHANCE, currentLocation);
    }
}
