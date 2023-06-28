package game.grounds;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.Element;
import game.items.Candy;
import game.items.Pokefruit;
import game.pokemons.Bulbasaur;
import game.time.TimePerception;

import java.util.Random;

/**
 * This ground class will spawn bulbasaur and pokefruit with element grass.
 *
 * @author Avinash Rvan (32717792)
 */

public class Tree extends SpawnGround implements TimePerception {

    /**
     * instance of Random used for RNG
     */
    private final Random random = new Random();

    /**
     * the chance that a bulbasaur will spawn out of 100
     */
    private static final int BULBASAUR_SPAWN_CHANCE = 15;
//    private static final int BULBASAUR_SPAWN_CHANCE = 5;

    /**
     * the chance that a pokefruit will spawn out of 100
     */
    private static final int POKEFRUIT_SPAWN_CHANCE = 15;

    /**
     * the chance that a candy will spawn out of 100
     */
    private static final int CANDY_SPAWN_CHANCE = 5;

    /**
     * the chance that a tree will expand out of 100
     */
    private static final int TREE_EXPAND_CHANCE = 10;

    /**
     * The tree location.
     */
    private Location treeLocations;

    /**
     * Constructor.
     */
    public Tree() {
        super('T');
        this.addCapability(Element.GRASS);
        registerInstance();
    }

    /**
     * Constructor
     * @param currentLocation the location of the tree.
     */
    public Tree(Location currentLocation) {
        super('T');
        this.addCapability(Element.GRASS);
        registerInstance();
        setTreeLocations(currentLocation);
    }

    /**
     * Setter to set the location of the tree.
     * @param treeLocations
     */
    public void setTreeLocations(Location treeLocations) {
        this.treeLocations = treeLocations;
    }

    /**
     * Getter to get the location of the tree.
     * @return the tree location.
     */
    public Location getTreeLocations() {
        return treeLocations;
    }

    /**
     * Acquire the location of the tree that is created in the map.
     * @param location The location of the Ground
     */
    public void tick(Location location) {
        setTreeLocations(location);

        int sameElementSurrounding = 0;
        Bulbasaur bulbasaur = new Bulbasaur();

        for (Exit exit : location.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getGround().hasCapability(Element.GRASS)) {
                sameElementSurrounding += 1;
            }
        }
        if (!location.containsAnActor() && sameElementSurrounding >= 1) {
            spawnPokemon(bulbasaur, BULBASAUR_SPAWN_CHANCE, location);
        }
        Pokefruit pokefruit = new Pokefruit(Element.GRASS);
        spawnItem(pokefruit, POKEFRUIT_SPAWN_CHANCE, location);
    }

    /**
     * The day effect where the tree will have a chance to drop a candy.
     */
    @Override
    public void dayEffect() {
        int val = random.nextInt(100);
        if (getTreeLocations() != null) {
            if (val < CANDY_SPAWN_CHANCE) {
                Candy candy = new Candy();
                getTreeLocations().addItem(candy);
            }
        }
    }

    /**
     * The night effect where the tree will convert his surrounding ground either to a tree or hay.
     */
    @Override
    public void nightEffect() {
        int val = random.nextInt(100);
        if (getTreeLocations() != null) {
            if (val < TREE_EXPAND_CHANCE) {
                boolean bool = random.nextBoolean();
                for (Exit exit : getTreeLocations().getExits()) {
                    Location destination = exit.getDestination();
                    if (!destination.getGround().hasCapability(Element.GRASS) && destination.getDisplayChar() != '_' && destination.getDisplayChar() != '#') {
                        if (bool) {
                            Tree tree = new Tree(destination);
                            destination.setGround(tree);
                        } else {
                            Hay hay = new Hay();
                            destination.setGround(hay);
                        }
                    }
                }
            }
        }
    }
}
