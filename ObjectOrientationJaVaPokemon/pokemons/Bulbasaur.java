package game.pokemons;


import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.enums.Element;
import game.time.TimePerception;
import game.weapons.VineWhip;

/**
 * Class for Bulbasaur instances
 * <p>
 * Created by:
 * @author Chew Jia Hong
 */
public class Bulbasaur extends Pokemon implements TimePerception {

    /**
     * Constructor.
     */
    public Bulbasaur() {

        super("Bulbasaur", 'b', 100);
        this.addCapability(Element.GRASS);
        getBackupWeapons().add(new VineWhip());
        setEggHatchTurns(3);
        registerInstance();     // time perception

    }

    /**
     * @return a new intrinsicWeapon for Bulbasaur
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(10, "tackle");
    }

    /**
     * Effect of day on this Bulbasaur
     */
    public void dayEffect() {
        hurt(5);
    }

    /**
     * Effect of night on this Bulbasaur
     */
    public void nightEffect() {
        heal(5);
    }

}
