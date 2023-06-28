package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.AffectionManager;
import game.items.Candy;
import game.items.Pokeball;
import game.pokemons.Pokemon;

/**
 * A class to handle the catching of Pokemon and storing them into Pokeball
 * <p>
 * Created by: Avinash Rvan (32717792)
 */
public class CatchPokemonAction extends Action {
    /**
     * The Pokemon that is to be captured
     */
    protected Pokemon target;

    /**
     * The direction of incoming catch.
     */
    protected String direction;

    /**
     * Affection manager instance to check points
     */
    protected AffectionManager affectionManager = AffectionManager.getInstance();

    /**
     * Sets the amount of affection points to deduct if the capture fails
     */
    private final int FAIL_CAPTURE_DEDUCTION = 10;


    /**
     * Constructor.
     *
     * @param target the Actor to attack
     */
    public CatchPokemonAction(Actor target, String direction) {
        this.target = affectionManager.findPokemon(target);
        this.direction = direction;
    }

    /**
     * Checks if pokemon is catchable in any way
     * If it is, stores the pokemon in a pokeball and stores it in the player's inventory
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a String showing the result of the capture attempt
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        String result;

        // check pokemon's affection points
        if (affectionManager.isCatchable(target, actor)) {
            // if it is, then catch the pokemon and store in player's inventory
            Pokeball pokeball = new Pokeball(actor, target);
            actor.addItemToInventory(pokeball);

            // create a candy and drop onto the ground
            Candy candy = new Candy();
            Location here = map.locationOf(target);
            here.addItem(candy);

            // remove the pokemon from the map
            map.removeActor(target);

            result = actor + " successfully captures " + target;

        } else {
            // affection points are insufficient
            // reduce affection points
            affectionManager.decreaseAffection(target, FAIL_CAPTURE_DEDUCTION, actor);

            result = actor + " fails to capture " + target;
        }

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
        return actor + " catches " + target + " at " + direction;
    }
}