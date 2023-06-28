package game.items;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.ItemStatus;
import game.pokemons.Pokemon;

/**
 * This class represents the Pokemon egg which will store a pokemon instance and release it into the GameMap when it is hatched.
 *
 * @author Avinash Rvan(32717792)
 */
public class PokemonEgg extends Item {

    /**
     * The instance of the Pokemon being stored inside this PokemonEgg
     */
    private final Pokemon storedPokemon;

    /**
     * The integer variable representing how many turns the egg has been sitting on the incubator
     */
    private int turnsIncubator;


    /**
     * This class represents the Pokemon egg which will store a pokemon instance and release it into the GameMap when it is hatched.
     *
     * @param storedPokemon
     */
    public PokemonEgg(Pokemon storedPokemon) {

        super(storedPokemon.getName() + " egg", 'g', true);

        this.storedPokemon = storedPokemon;
        this.turnsIncubator = 0;
        this.addCapability(ItemStatus.CAN_HATCH);
    }

    /**
     * @return The stored Pokemon
     */
    public Pokemon getStoredPokemon() {
        return storedPokemon;
    }

    /**
     * Will check if there is an actor at the hatching location.
     * If there isn't then the egg is hatched at that location
     *
     * @param hatchingLocation location where the pokemon will be born
     * @param eggLocation      the location of the egg to be removed
     * @return a boolean representing whether the hatch was successful or not
     */
    public boolean hatchPokemon(Location hatchingLocation, Location eggLocation) {
        // check if there is an actor and if the pokemon can be hatched there
        if (hatchingLocation.canActorEnter(this.getStoredPokemon())) {
            hatchingLocation.addActor(getStoredPokemon());
            eggLocation.removeItem(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Incubates the egg by 1 turn
     */
    public void incubate() {
        this.turnsIncubator += 1;
    }

    /**
     * Every turn will check if it is time to hatch and if it is then it will hatch.
     * First, it will attempt to hatch it at the current location. If it is not allowed, then it will try to hatch
     * at any one of the surrounding locations. If that is also not possible, then it will wait until there is a
     * empty space.
     *
     * @param currentLocation the current location of the egg
     */
    public void tick(Location currentLocation) {
        // check if it has passed the hatching turns and release the pokemon if it has
        // then hatch the pokemon
        if (turnsIncubator >= getStoredPokemon().getEggHatchTurns()) {
            // first check if the current location can be added
            if (!hatchPokemon(currentLocation, currentLocation)) {

                // next try checking the surroundings for empty space
                for (Exit exits : currentLocation.getExits()) {
                    // try to hatch and if it succeeds, then break out of the loop
                    if (hatchPokemon(exits.getDestination(), currentLocation)) {
                        break;
                    }
                }
            }
        }
    }


}
