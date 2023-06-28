package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.AffectionManager;
import game.actions.CatchPokemonAction;
import game.enums.Status;

/**
 * This is the behaviour of the NPC trainer which allows them to catch any pokemon nearby which have sufficient affection points
 *
 * @author Avinash Rvan (32717792)
 */
public class CatchPokemonBehaviour implements Behaviour {
    /**
     * Priority key for treemap
     */
    public static final int PRIORITY = 1;

    /**
     * Affection manager instance
     */
    private final AffectionManager affectionManager = AffectionManager.getInstance();

    /**
     * This will check the surrounding for any pokemon which is catchable and will catch it
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return a CatchPokemonAction if there is a pokemon which can be caught successfully
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location currentLocation = map.locationOf(actor);

        for (Exit exits : currentLocation.getExits()) {
            // check for any pokemons which can be caught
            Location destination = exits.getDestination();

            // check if there is a pokemon and it can be caught

            if (destination.containsAnActor()) {
                Actor pokemon = destination.getActor();
                if (pokemon.hasCapability(Status.CATCHABLE) && affectionManager.isCatchable(affectionManager.findPokemon(pokemon), actor)) {
                    // if the pokemon is catchable then return a catching action
                    return new CatchPokemonAction(pokemon, exits.getName());
                }
            }
        }

        return null;
    }
}
