package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.ElementsHelper;
import game.actions.AttackAction;
import game.enums.Element;
import game.enums.Status;
import game.pokemons.Pokemon;

/**
 * A class that represents attacking behaviours of NPCs
 * <p>
 * Created by:
 *
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Chew Jia Hong
 */
public class AttackBehaviour implements Behaviour {

    /**
     * Priority key for treemap
     */
    public static final int PRIORITY = 2;

    /**
     * Checks surrounding and return AttackAction or nothing
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return appropriate action
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

        Location here = map.locationOf(actor);      // current actor location

        for (Exit exit : here.getExits()) {

            Location destination = exit.getDestination();

            // checks if an actor is in exit
            if (map.isAnActorAt(destination)) {

                Actor otherActor = map.getActorAt(destination);

                // checks if actor should be attacked
                if (!(otherActor.hasCapability(Status.IMMUNE) ||
                        ElementsHelper.hasAnySimilarElements(actor,
                                otherActor.findCapabilitiesByType(Element.class)))) {

                    ((Pokemon) actor).buffCheck(otherActor, map);

                    return new AttackAction(otherActor, exit.getName()); // behaviour will stop here.
                }
            }
        }

        return null; // go to next behaviour
    }
}
