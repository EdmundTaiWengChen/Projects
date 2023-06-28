package game.grounds;


import edu.monash.fit2099.engine.positions.Location;
import game.enums.Element;
import game.items.Pokefruit;
import game.pokemons.Charmander;

/**
 * This ground class will spawn Charmander and pokefruit with element Fire.
 *
 * @author Edmund
 */
public class Crater extends SpawnGround {

    /**
     * Spawn chance of Charmander
     */
    private static final int CHARMANDER_SPAWN_CHANCE = 10;
//    private static final int CHARMANDER_SPAWN_CHANCE = 50;

    /**
     * Spawn chance of Pokefruit
     */
    private static final int POKEFRUIT_SPAWN_CHANCE = 25;

    /**
     * Constructor.
     */
    public Crater() {
        super('O');
        this.addCapability(Element.FIRE);
    }

    /**
     * Charmander and pokefruit (fire) will spawn at random every turn.
     * @param currentLocation The location of the Ground
     */
    public void tick(Location currentLocation) {
        Charmander charmander = new Charmander();
        if (!currentLocation.containsAnActor()) {
            spawnPokemon(charmander, CHARMANDER_SPAWN_CHANCE, currentLocation);
        }
        Pokefruit pokefruit = new Pokefruit(Element.FIRE);
        spawnItem(pokefruit, POKEFRUIT_SPAWN_CHANCE, currentLocation);
    }
}