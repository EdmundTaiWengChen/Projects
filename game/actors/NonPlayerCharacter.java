package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.DeadAction;
import game.behaviours.Behaviour;

import java.util.TreeMap;

/**
 * An abstract class used by all the NPCs with behaviours in the game
 *
 * @author Avinash Rvan (32717792)
 */
public abstract class NonPlayerCharacter extends Actor {

    /**
     * Data structure for priority and behaviours
     */
    private final TreeMap<Integer, Behaviour> behaviours = new TreeMap<>();

    /**
     * Latest location of the player
     */
    protected Location currentLocation;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public NonPlayerCharacter(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }

    /**
     * @return Behaviours treemap
     */
    public TreeMap<Integer, Behaviour> getBehaviours() {
        return behaviours;
    }

    /**
     * Adds a new Player behaviour if not present in collection
     *
     * @param priority  priority key for behaviours map
     * @param behaviour new behaviour instance
     */
    public void addBehaviour(int priority, Behaviour behaviour) {

        if (!behaviours.containsValue(behaviour)) {
            this.behaviours.put(priority, behaviour);
        }
    }

    /**
     * Removes a Player behaviour if present in collection
     *
     * @param priority priority key for behaviours map
     */
    public void removeBehaviour(int priority) {
        this.behaviours.remove(priority);
    }

    /**
     * By using behaviour loops, it will decide what will be the next action automatically.
     *
     * @see Actor#playTurn(ActionList, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // update the latest location every turn
        currentLocation = map.locationOf(this);

        if (!this.isConscious()) {
            return new DeadAction();
        }

        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }
}
