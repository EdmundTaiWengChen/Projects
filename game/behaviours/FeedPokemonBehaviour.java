package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.FeedPokemonAction;
import game.enums.ItemStatus;
import game.enums.Status;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents the behaviour of NPC where they will feed a pokemon with a pokefruit if possible
 *
 * @author Avinash Rvan (32717792)
 */
public class FeedPokemonBehaviour implements Behaviour {

    /**
     * Priority key for treemap
     */
    public static final int PRIORITY = 2;

    /**
     * Random number generator
     */
    private final Random random = new Random();

    /**
     * Checks if there are any pokemons surrounding the actor, and if there are any pokefruits in the inventory
     * If yes, then returns an action to feed one of the random pokemon with a random pokefruit
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return FeedPokemonAction or null
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);      // current actor location
        ArrayList<Item> pokefruitList = new ArrayList<>();

        // go through the actor's inventory and check if there are any pokfruits
        for (Item item : actor.getInventory()) {
            if (item.hasCapability(ItemStatus.CAN_FEED)) {
                pokefruitList.add(item);
            }
        }

        // go through all the surrounding spaces and check if there are any feedable pokemons
        ArrayList<Actor> pokemonList = new ArrayList<>();
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            // checks if an actor is at one of the surroundings
            // and check if that actor can be fed
            if (map.isAnActorAt(destination) && map.getActorAt(destination).hasCapability(Status.CAN_EAT)) {
                Actor otherActor = map.getActorAt(destination);
                pokemonList.add(otherActor);
            }
        }

        // now check that the list of pokefruit is not empty and that there is a pokemon nearby that can be fed
        if ((pokefruitList.size() > 0) && (pokemonList.size() > 0)) {
            // pick a random fruit
            Item fruit = pokefruitList.get(random.nextInt(pokefruitList.size()));

            // pick a random pokemon to feed
            Actor pokemon = pokemonList.get(random.nextInt(pokemonList.size()));

            // create and return the action to feed this pokemon
            return new FeedPokemonAction(pokemon, fruit);
        } else {
            // if either the player has no fruit or there are no pokemon nearby, return null
            return null;
        }


    }
}
