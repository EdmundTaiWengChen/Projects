package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import game.pokemons.Pokemon;

/**
 * A class that represents the floor inside a building.
 * <p>
 * Created by:
 *
 * @author Riordan D. Alfredo
 * Modified by:
 */
public class Floor extends Ground {
    public Floor() {
        super('_');
    }

    public boolean canActorEnter(Actor actor) {
        return !(actor instanceof Pokemon);
    }
}
