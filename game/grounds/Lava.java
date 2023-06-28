package game.grounds;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.enums.Element;
import game.time.TimePerception;
import game.time.TimePerceptionManager;

import java.util.Random;

/**
 * Created by:
 *
 * @author Riordan D. Alfredo
 * Modified by:
 *
 * @author Edmund
 */
public class Lava extends Ground implements TimePerception {
    /**
     * A new instance of random.
     */
    private final Random random = new Random();

    /**
     * The location of lava.
     */
    private Location lavaLocation;

    /**
     * The chances for lava to expand.
     */
    private static final int LAVAEXPANDCHANCE = 10;

    /**
     * The chances for lava to get destroyed.
     */
    private static final int LAVEDESTROYCHANCE = 10;


    /**
     * Constructor.
     */
    public Lava() {
        super('^');
        this.addCapability(Element.FIRE);
        registerInstance();
    }

    /**
     * Constructor
     * @param currentLocation
     */
    public Lava(Location currentLocation) {
        super('^');
        this.addCapability(Element.FIRE);
        registerInstance();
        setLavaLocation(currentLocation);
    }

    /**
     * A setter that set the location of the lava.
     * @param lavaLocation
     */
    public void setLavaLocation(Location lavaLocation) {
        this.lavaLocation = lavaLocation;
    }

    /**
     * A getter that return the location of the lava.
     * @return the lava location.
     */
    public Location getLavaLocation() {
        return lavaLocation;
    }

    /**
     * Aquire the location of the lava every turn.
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        setLavaLocation(location);
    }

    /**
     * The day effect where the lava will expand at random.
     */
    @Override
    public void dayEffect() {
        int val = random.nextInt(100);
        if (getLavaLocation() != null) {
            if (val < LAVAEXPANDCHANCE) {
                for (Exit exit : getLavaLocation().getExits()) {
                    Location destination = exit.getDestination();
                    if (!destination.getGround().hasCapability(Element.FIRE) && destination.getDisplayChar() != '_' && destination.getDisplayChar() != '#') {
                        Lava lava = new Lava(destination);
                        destination.setGround(lava);
                    }
                }
            }
        }
    }

    /**
     * The night effect where the lava will get destroyed at random.
     */
    @Override
    public void nightEffect() {
        int val = random.nextInt(100);
        if (getLavaLocation() != null) {
            if (val < LAVEDESTROYCHANCE) {
                if (!getLavaLocation().containsAnActor()) {
                    Dirt dirt = new Dirt();
                    TimePerceptionManager.getInstance().objectsToRemove.add(this);

                    getLavaLocation().setGround(dirt);
                }
            }
        }
    }

}
