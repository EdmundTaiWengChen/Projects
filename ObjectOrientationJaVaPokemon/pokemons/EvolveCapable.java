package game.pokemons;

/**
 * An interface for those that evolve
 * <p>
 * Created by:
 * @author Chew Jia Hong
 */
public interface EvolveCapable {

    /**
     * @return True if evolve condition satisfied
     */
    boolean shouldEvolve();

    /**
     * @return New evolved Pokemon instance
     */
    Pokemon evolve();

}
