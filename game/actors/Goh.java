package game.actors;

import edu.monash.fit2099.engine.items.Item;
import game.AffectionManager;
import game.behaviours.CatchPokemonBehaviour;
import game.behaviours.FeedPokemonBehaviour;
import game.behaviours.PickUpItemBehaviour;
import game.behaviours.WanderBehaviour;
import game.enums.Status;
import game.pokemons.Pokemon;

import java.util.List;

/**
 * This class is responsible for an NPC trainer which will carry out his own actions based on a set priority list
 *
 * @author Avinash Rvan (32717792)
 */
public class Goh extends NonPlayerCharacter {
    /**
     * Affection manager instance
     */
    private final AffectionManager affectionManager = AffectionManager.getInstance();

    /**
     * Instance of the Goh trainer
     *
     * The Goh is done as a singleton class because he has to be accessed from the other classes.
     * This should be done using the actorLocationsIterator, however, we cannot access that as it is in the engine code
     */
    private static Goh instance;

    /**
     * Goh name
     */
    private static final String name = "Goh";

    /**
     * The display character of Goh
     */
    private static final Character displayChar = 'G';

    /**
     * The hitpoints of Goh
     */
    private static final int hitPoints = 1;

    /**
     * This is a NPC trainer which will carry out his own actions based on a set priority list
     *
     * @param name        name of the trainer
     * @param displayChar display character of the trainer
     * @param hitPoints   health points
     */
    private Goh(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);

        this.addCapability(Status.IMMUNE);
        this.addCapability(Status.TELEPORT);
        this.addCapability(Status.TRAINER);

        // add all the behaviours of the Goh character
        addBehaviour(CatchPokemonBehaviour.PRIORITY, new CatchPokemonBehaviour());
        addBehaviour(FeedPokemonBehaviour.PRIORITY, new FeedPokemonBehaviour());
        addBehaviour(PickUpItemBehaviour.PRIORITY, new PickUpItemBehaviour());
        addBehaviour(WanderBehaviour.PRIORITY, new WanderBehaviour());

        // initially register the player
        affectionManager.registerTrainer(this);
    }

    /**
     *
     * @return an instance of the Goh trainer
     */
    public static Goh getInstance() {
        if (instance == null) {
            instance = new Goh(name, displayChar, hitPoints);
        }
        return instance;
    }

    /**
     * @return a string of the list of items in the trainer's inventory
     */
    public String printInventory() {
        List<Item> inventoryList = this.getInventory();
        String ret = "";
        ret += "inventory: [";
        for (int i = 0; i < inventoryList.size(); i++) {
            if (i == (inventoryList.size() - 1)) {
                ret += inventoryList.get(i);
            } else {
                ret += inventoryList.get(i) + ", ";
            }
        }
        ret += "]";
        return ret;
    }

    /**
     * @return a full list of the status of the trainer including the location and affection points of all the pokemons to the trainer
     */
    public String fullStatusPrint() {
        String ret = "";
        if(currentLocation == null){
            return("Goh has no location yet, please try again next round");
        }

        ret += String.format("%s | %d,%d | %s\n", name, currentLocation.x(), currentLocation.y(), printInventory());

        List<Pokemon> pokemonList = affectionManager.getAllPokemon();
        for (Pokemon pokemon : pokemonList) {
            if (pokemon.currentLocation != null) {
                ret += String.format("- %s at %d,%d\n", pokemon.info(this), pokemon.currentLocation.x(), pokemon.currentLocation.y());
            }
        }

        return ret;
    }


}
