package game.grounds;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.Element;
import game.time.TimePerception;
import game.time.TimePerceptionManager;

import java.util.Random;

/**
 * This ground class will extend during night and get destroyed during day.
 *
 * @author Edmund
 */

public class Puddle extends Ground implements TimePerception {
    /**
     * Instance of Random.
     */
    private final Random random = new Random();
    /**
     * The location of puddle
     */
    private Location puddleLocation;
    /**
     * The chances of puddle to expand.
     */
    private static final int PUDDLEEXPANDCHANCE = 10;
    /**
     * The chances of puddle get destroyed.
     */
    private static final int PUDDLEDESTROYCHANCE = 10;

    /**
     * Constructor.
     */
    public Puddle() {
        super('~');
        this.addCapability(Element.WATER);
        registerInstance();
    }

    /**
     * Constructor.
     */
    public Puddle(Location currentLocation) {
        super('~');
        this.addCapability(Element.WATER);
        registerInstance();
        setPuddleLocation(currentLocation);
    }

    /**
     * A setter that change the location of puddle.
     * @param puddleLocation
     */
    public void setPuddleLocation(Location puddleLocation) {
        this.puddleLocation = puddleLocation;
    }

    /**
     * A getter that get the puddle location.
     * @return the puddle location
     */
    public Location getPuddleLocation() {
        return puddleLocation;
    }

    /**
     * Get all the puddle location that is created in the map.
     * @param location The location of the Ground
     */
    public void tick(Location location) {
        setPuddleLocation(location);
    }

    /**
     * The day effect where the puddle will get destroyed.
     */
    @Override
    public void dayEffect() {
        int val = random.nextInt(100);
        if (getPuddleLocation() != null) {
            if (val < PUDDLEDESTROYCHANCE) {
                Dirt dirt = new Dirt();
                TimePerceptionManager.getInstance().objectsToRemove.add(this);
                getPuddleLocation().setGround(dirt);
            }
        }
    }

    /**
     * The night effect where the puddle will expand.
     */
    @Override
    public void nightEffect() {
        int val = random.nextInt(100);
        if (getPuddleLocation() != null) {
            if (val < PUDDLEEXPANDCHANCE) {
                for (Exit exit : getPuddleLocation().getExits()) {
                    Location destination = exit.getDestination();
                    if (!destination.getGround().hasCapability(Element.WATER) && destination.getDisplayChar() != '_' && destination.getDisplayChar() != '#') {
                        Puddle puddle = new Puddle(destination);
                        destination.setGround(puddle);
                    }
                }
            }
        }
    }

}
