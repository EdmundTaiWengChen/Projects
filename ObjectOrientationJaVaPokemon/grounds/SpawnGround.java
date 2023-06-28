package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Pokefruit;
import game.pokemons.Pokemon;

import java.util.Random;


/**
 * This ground class will spawn the pokemon and pokefruit.
 *
 * @author Edmund
 */

public abstract class SpawnGround extends Ground {
    /**
     * A new instance of random
     */
    private final Random random = new Random();

    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of terrain
     */
    public SpawnGround(char displayChar) {
        super(displayChar);
    }

    /**
     * The spawning of pokemon on that ground.
     * @param pokemon
     * @param chances
     * @param currentLocation
     */
    public void spawnPokemon(Pokemon pokemon, int chances, Location currentLocation) {
        int val = random.nextInt(100);
        if (val < chances) {
            currentLocation.addActor(pokemon);
        }
    }

    /**
     * The spawning of item on that ground.
     * @param pokefruit
     * @param chances
     * @param currentLocation
     */
    public void spawnItem(Pokefruit pokefruit, int chances, Location currentLocation) {
        int val = random.nextInt(100);
        if (val < chances) {
            currentLocation.addItem(pokefruit);
        }
    }
}
