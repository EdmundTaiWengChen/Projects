package game;

import edu.monash.fit2099.engine.actors.Actor;
import game.behaviours.FollowBehaviour;
import game.pokemons.Pokemon;

import java.util.*;

/**
 * Affection Manager
 * <p>
 * Created by:
 *
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Chew Jia Hong
 * @author Avinash Rvan
 */
public class AffectionManager {

    /**
     * Singleton instance (the one and only for a whole game).
     */
    private static AffectionManager instance;


    /**
     * Create a map of trainers to their corresponding affection point maps
     */
    private final Map<Actor, Map> trainerMap;  // pokemon map within trainer map

    /**
     * A list of all the Pokemons in the game
     */
    private final ArrayList<Pokemon> pokemonList = new ArrayList<>();

    /**
     * Create a variable for the main trainer (in our case this is Ash)
     */
    private static Actor trainer;

    /**
     * Maximum affection level a Pokemon can have towards a player
     */
    private static final int MAX_AFFECTION = 100;

    /**
     * Pokemon starts following a player
     */
    private static final int FOLLOW_AFFECTION = 75;

    /**
     * Pokemon can be captured by player
     */
    private static final int CATCHABLE_AFFECTION = 50;

    /**
     * Relationship between Pokemon and player cannot be fixed,
     * supposedly minimum but can go lower
     */
    private static final int MIN_AFFECTION = -50;

    /**
     * The base level of affection points when a pokemon is instantiated
     */
    private static final int BASE_AFFECTION = 0;

    /**
     * private singleton constructor
     */
    private AffectionManager() {
//        this.affectionPoints = new HashMap<>();

        // create a map of trainers and their corresponding AP maps
        this.trainerMap = new HashMap<>();
    }

    /**
     * Access single instance publicly
     *
     * @return this instance
     */
    public static AffectionManager getInstance() {
        if (instance == null) {
            instance = new AffectionManager();
        }
        return instance;
    }

    /**
     * Add a trainer to this class's attribute. Assume there's only one trainer at a time.
     *
     * @param trainer the actor instance
     */
    public void registerTrainer(Actor trainer) {
        Map<Pokemon, Integer> affectionPoints = new HashMap<Pokemon, Integer>();
        this.trainerMap.put(trainer, affectionPoints);
    }

    /**
     * This method will register a trainer by creating a new affection map for them
     *
     * @param trainer       the trainer to be registered
     * @param mainTrainer   set true if this is the main trainer of the game
     */
    public void registerTrainer(Actor trainer, boolean mainTrainer) {
        Map<Pokemon, Integer> affectionPoints = new HashMap<Pokemon, Integer>();
        this.trainerMap.put(trainer, affectionPoints);

        // set the main trainer
        // this will set the main trainer if the boolean is true
        if (mainTrainer) {
            AffectionManager.trainer = trainer;
        }
    }

    /**
     * Add Pokemon to the collection. By default, it has 0 affection point. Ideally, you'll register all instantiated Pokemon
     *
     * @param pokemon new Pokemon instance
     */
    public void registerPokemon(Pokemon pokemon) {
        // add the pokemon to the full list of all the pokemons in the game
        pokemonList.add(pokemon);

        // iterate through all the trainers and add the pokemon to those trainers
        for (Actor trainer : this.trainerMap.keySet()) {
            // this will get the affection map for all the trainers
            Map affectionMap = this.trainerMap.get(trainer);
            affectionMap.put(pokemon, BASE_AFFECTION);
        }
    }

    /**
     * Removes all the pokemons which have fainted from all the affection lists and the full list of pokemons present in the game
     */
    public void cleanUp() {
        ArrayList<Pokemon> removalList = new ArrayList<>();
        // goes through all the pokemons and removes the ones which are unconscious
        for (Pokemon pokemon : pokemonList) {
            if (!pokemon.isConscious()) {
                // add it to the removal list
                removalList.add(pokemon);
            }
        }

        for (Pokemon pokemon : removalList) {
            // first, remove the pokemon from the full list
            pokemonList.remove(pokemon);

            // next, remove from all the trainers affection list
            for (Actor trainer : trainerMap.keySet()) {
                Map<Pokemon, Integer> affectionList = trainerMap.get(trainer);
                affectionList.remove(pokemon);
            }
        }
    }

    /**
     * Get the affection point by using the pokemon instance as the key.
     *
     * @param pokemon Pokemon instance
     * @return integer of affection point.
     */
    public int getAffectionPoint(Actor pokemon, Actor trainer) {
        return (int) trainerMap.get(trainer).get(pokemon);
    }

    /**
     * Useful method to search a pokemon by using Actor instance.
     *
     * @param actor general actor instance
     * @return the Pokemon instance.
     */
    public Pokemon findPokemon(Actor actor) {
        Map<Pokemon, Integer> affectionList = trainerMap.get(trainer);
        for (Pokemon pokemon : affectionList.keySet()) {
            if (pokemon.equals(actor)) {
                return pokemon;
            }
        }
        return null;
    }

    /**
     * Increase the affection. Works on both cases when there's a Pokemon,
     * or when it doesn't exist in the collection.
     *
     * @param pokemon Pokemon instance
     * @param point   positive affection modifier
     * @return custom message to be printed by Display instance later.
     */
    public String increaseAffection(Pokemon pokemon, int point, Actor trainer) {

        String result = "";

        int affectionPoint = getAffectionPoint(pokemon, trainer);

        // Relationship unfixable
        if (affectionPoint <= MIN_AFFECTION) {
            result += trainer + " fell out with " + pokemon + ", relationship cannot be fixed!";
            return result;
        }

        // Maximum AP reached before increase
        if (affectionPoint == MAX_AFFECTION) {
            result += pokemon + " likes it! " + MAX_AFFECTION + " affection points reached!";
            return result;
        }

        // Point increase
        affectionPoint += point;

        // Overflow cap, map update
        affectionPoint = Math.min(affectionPoint, MAX_AFFECTION);
        trainerMap.get(trainer).put(pokemon, affectionPoint);

        result += pokemon.info(trainer) + " likes it! +" + point + " affection points";

        // Maximum AP reached
        if (affectionPoint == MAX_AFFECTION) {
            result += "\nMaximum affection points reached!";
        }

        // Pokemon follows trainer
        if (affectionPoint >= FOLLOW_AFFECTION) {
            pokemon.addBehaviour(FollowBehaviour.PRIORITY, new FollowBehaviour(trainer));
        }

        return result;
    }

    /**
     * Decrease the affection. Works on both cases when there's a Pokemon,
     * or when it doesn't exist in the collection.
     *
     * @param pokemon Pokemon instance
     * @param point   positive affection modifier (to be subtracted later)
     * @return custom message to be printed by Display instance later.
     */
    public String decreaseAffection(Pokemon pokemon, int point, Actor trainer) {

        String result = "";

        int affectionPoint = getAffectionPoint(pokemon, trainer);

        // Point decreases regardless
        affectionPoint -= point;
        trainerMap.get(trainer).put(pokemon, affectionPoint);

        result += pokemon.info(trainer) + " dislikes it! -" + point + " affection points";

        // Relationship unfixable
        if (affectionPoint <= MIN_AFFECTION) {
            result += "\n" + trainer + " fell out with " + pokemon + ", relationship cannot be fixed!";
        }

        // Pokemon stops following trainer
        if (affectionPoint < FOLLOW_AFFECTION) {
            pokemon.removeBehaviour(FollowBehaviour.PRIORITY);
        }

        return result;
    }

    /**
     * @return the main trainer
     */
    public Actor getMainTrainer() {
        return trainer;
    }

    /**
     * Checks whether the trainer can catch a particular pokemon and will return a boolean
     *
     * @param pokemon pokemon to be checked
     * @param trainer trainer which wants to catch it
     * @return a boolean indicating whether the catch will be successful or not
     */
    public boolean isCatchable(Pokemon pokemon, Actor trainer) {
        return (int) (trainerMap.get(trainer).get(pokemon)) >= CATCHABLE_AFFECTION;
    }

    /**
     * Checks whether Pokemon and trainer relationship reached level to allow evolution
     *
     * @param pokemon   Pokemon to be evolved
     * @param trainer   Trainer triggering the evolve
     * @return True if affection point reached
     */
    public boolean canEvolve(Pokemon pokemon, Actor trainer) {

        int affectionPoint = getAffectionPoint(pokemon, trainer);

        return affectionPoint >= MAX_AFFECTION;
    }

    /**
     * Returns an unmodifiable list of all the Pokemon currently in the game
     *
     * @return
     */
    public List<Pokemon> getAllPokemon() {
        List<Pokemon> returnList = Collections.unmodifiableList(pokemonList);

        return returnList;
    }

}
