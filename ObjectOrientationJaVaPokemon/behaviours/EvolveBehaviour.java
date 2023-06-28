package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.pokemons.EvolveCapable;
import game.actions.EvolveAction;


/**
 * A class that represents evolve behaviour of NPCs
 * <p>
 * Created by:
 * @author Chew Jia Hong
 */
public class EvolveBehaviour implements Behaviour {

    /**
     * Priority key for treemap
     */
    public static final int PRIORITY = 1;

    /**
     * Checks evolve criteria and return EvolveAction or nothing
     *
     * @param actor the Actor performing action
     * @param map   the GameMap containing the Actor
     * @return  EvolveAction or null
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

        if (!((EvolveCapable) actor).shouldEvolve()) {
            return null;    // go to next behaviour
        }

        Location here = map.locationOf(actor);  // current actor location

        for (Exit exit : here.getExits()) {

            Location destination = exit.getDestination();

            // checks if an actor is in exit
            if (map.isAnActorAt(destination)) {

                return null;    // go to next behaviour
            }
        }

        return new EvolveAction(actor);  // go to evolve action
    }

}
