package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ReleasePokemonAction;
import game.pokemons.Pokemon;

/**
 * This class represents the Pokeball item which will store a pokemon instance and later allow the player to release
 * it into the map
 *
 * @author Avinash Rvan(32717792)
 */
public class Pokeball extends Item {

    /**
     * The instance of the Pokemon being stored inside this Pokeball
     */
    private final Pokemon storedPokemon;

    /**
     * The player
     */
    private final Actor player;


    /**
     * Constructor that sets the player, and the captured pokemon
     *
     * @param player
     * @param capturedPokemon
     */
    public Pokeball(Actor player, Pokemon capturedPokemon) {
        super((capturedPokemon.getName() + " pokeball"), '0', false);

        this.storedPokemon = capturedPokemon;
        this.player = player;
    }

    /**
     * @return The stored Pokemon
     */
    public Pokemon getStoredPokemon() {
        return storedPokemon;
    }

    /**
     * Override the tick method to update the location of the release destinations every turn
     * This will ensure that the locations are always following the latest player location
     *
     * @param currentLocation the location of the pokeball currently
     * @param actor           the actor who is holding the pokeball
     */
    public void tick(Location currentLocation, Actor actor) {
        // clear the action list first
        this.clearActions();

        // find all the possible release locations and add to the Pokeballs actions
        for (Exit exit : currentLocation.getExits()) {
            Location destination = exit.getDestination();

            // check if the destination has no current actors on it then add that as an option
            if (!destination.containsAnActor() && destination.canActorEnter(this.getStoredPokemon())) {
                this.addAction(new ReleasePokemonAction(this, destination, exit.getName()));
            }
        }
    }


}
