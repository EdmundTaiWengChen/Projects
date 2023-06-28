package game.time;

import java.util.ArrayList;
import java.util.List;

/**
 * A global Singleton manager that gives time perception  on the affected instances.
 * TODO: you may modify (add or remove) methods in this class if you think they are not necessary.
 * HINT: refer to Bootcamp Week 5 about static factory method.
 * <p>
 * Created by:
 *
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Avinash Rvan
 * @author Edmund
 * @author Jia Hong
 */
public class TimePerceptionManager {
    /**
     * A list of polymorph instances (any classes that implements TimePerception,
     * such as, a Charmander implements TimePerception, it will be stored in here)
     */
    private final List<TimePerception> timePerceptionList;

    public int turn;

    public List<TimePerception> objectsToRemove = new ArrayList<>();

    private TimePeriod shift; // DAY or NIGHT

    /**
     * A singleton instance
     */
    private static TimePerceptionManager instance;

    /**
     * Get the singleton instance of time perception manager
     *
     * @return TimePerceptionManager singleton instance
     * <p>
     * FIXME: create a singleton instance.
     */
    public static TimePerceptionManager getInstance() {
        if (instance == null) {
            instance = new TimePerceptionManager();
        }
        return instance;
    }

    /**
     * Private constructor
     */
    private TimePerceptionManager() {
        timePerceptionList = new ArrayList<>();
        turn = 0;
    }


    /**
     * Traversing through all instances in the list and execute them
     * By doing this way, it will avoid using `instanceof` all over the place.
     * <p>
     */
    public void run() {
        if (turn % 10 < 5) {
            shift = TimePeriod.DAY;
            System.out.println("It is a Day-time (turn " + turn + ")");
        } else {
            shift = TimePeriod.NIGHT;
            System.out.println("It is a Night-time (turn " + turn + ")");
        }


        if (turn > 0) {
            int size = timePerceptionList.size();

            for (int i = 0; i < size; i++) {
                if (shift == TimePeriod.DAY) {
                    timePerceptionList.get(i).dayEffect();
                } else {
                    timePerceptionList.get(i).nightEffect();
                }
            }

            // if any of the items were destroyed, remove them from the list
            for (TimePerception i : objectsToRemove) {
                cleanUp(i);
            }
            objectsToRemove.clear();
        }
        turn += 1;
    }


    /**
     * Add the TimePerception instance to the list
     *
     * @param objInstance any instance that implements TimePerception
     */
    public void append(TimePerception objInstance) {
        timePerceptionList.add(objInstance);
    }


    /**
     * Remove a TimePerception instance from the list
     *
     * @param objInstance object instance
     */
    public void cleanUp(TimePerception objInstance) {
        timePerceptionList.remove(objInstance);
    }
}
