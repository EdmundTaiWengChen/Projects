package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Pokeball;

/**
 * A class to handle the releasing of Pokemon into the map
 * <p>
 * Created by: Avinash Rvan (32717792)
 */
public class ReleasePokemonAction extends Action {

    /**
     * The location to release the Pokemon.
     */
    protected Location destination;

    /**
     * The direction of the release
     */
    private final String direction;

    /**
     * The pokeball being used
     */
    private final Pokeball pokeball;

    /**
     * A constructor to set the parameters
     *
     * @param pokeball
     * @param releaseDestination
     * @param direction
     */
    public ReleasePokemonAction(Pokeball pokeball, Location releaseDestination, String direction) {
        this.pokeball = pokeball;
        this.destination = releaseDestination;
        this.direction = direction;
    }

    /**
     * Adds the pokemon into the GameMap and removes the Pokeball from the player's inventory
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        // release the Pokemon into the map at the release destination
        destination.addActor(pokeball.getStoredPokemon());

        // remove the Pokeball from the player's inventory
        actor.removeItemFromInventory(pokeball);

        return "I choose you..." + pokeball.getStoredPokemon() + "!";
    }

    /**
     * Gives a description of the trainer who summoned the pokemon, the pokemon summoned, and where
     * the pokemon has been summoned
     *
     * @param actor The actor performing the action.
     * @return
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " summons " + pokeball.getStoredPokemon() + " at " + direction;
    }

}
