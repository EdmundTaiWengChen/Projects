package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.AffectionManager;
import game.ElementsHelper;
import game.enums.Element;
import game.items.Pokefruit;
import game.pokemons.Pokemon;

/**
 * A class to handle the feeding of Pokemon using the Pokefruits in the player's inventory
 * <p>
 * Created by: Avinash Rvan (32717792)
 */
public class FeedPokemonAction extends Action {
    /**
     * The Pokemon that is to be fed
     */
    protected Pokemon target;

    /**
     * The direction of incoming feeding.
     */
    protected Pokefruit pokefruit;

    /**
     * Affection manager instance to modify points
     */
    protected AffectionManager affectionManager = AffectionManager.getInstance();

    /**
     * Sets the amount of Affection Points to increase based on the
     * feeding outcome
     */
    private final int GOOD_FEED = 20;
//    private final int GOOD_FEED = 100;

    /**
     * Sets the amount of Affection Points to decrease based on the
     * feeding outcome
     */
    private final int BAD_FEED = 10;

    /**
     * Constructor.
     *
     * @param target the Actor to attack
     */
    public FeedPokemonAction(Actor target, Item pokefruit) {
        this.target = affectionManager.findPokemon(target);
        this.pokefruit = (Pokefruit) pokefruit;
    }

    /**
     * Compares the element of the fruit and the pokemon and then changes the affection points
     * accordingly
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a String showing the result of the feeding attempt
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        String result;

        result = actor + " gives a " + pokefruit + " to " + target;

        // check Pokemon's and Pokefruit's elements
        if (ElementsHelper.hasAnySimilarElements(pokefruit, target.findCapabilitiesByType(Element.class))) {
            result += affectionManager.increaseAffection(target, GOOD_FEED, actor);
        } else {
            result += affectionManager.decreaseAffection(target, BAD_FEED, actor);
        }

        // remove the pokefruit from the player's inventory after feeding
        actor.removeItemFromInventory(pokefruit);

        return result;
    }

    /**
     * Display message of Action on the console menu
     *
     * @param actor The actor performing the action.
     * @return Action menu display
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " feeds " + target + " with " + pokefruit;
    }
}