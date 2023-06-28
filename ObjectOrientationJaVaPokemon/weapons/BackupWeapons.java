package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.actions.AttackAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by:
 * @author Riordan D. Alfredo
 * <p>
 * Modified by:
 * @author Chew Jia Hong
 * <p>
 * TODO: Use this class to store Pokemon's weapons (special attack) permanently.
 * If a Pokemon needs to use a weapon, put it into that Pokemon's inventory.
 * @see Actor#getWeapon() method.
 * @see AttackAction uses getWeapon() in the execute() method.
 */
public class BackupWeapons implements Iterable<WeaponItem> {

    /**
     * List of WeaponItem instances
     */
    private final ArrayList<WeaponItem> backupWeapons = new ArrayList<>();

    /**
     * Constructor
     */
    public BackupWeapons() {
    }

    /**
     * Shuffles the collection before return
     *
     * @return backupWeapons as Iterator for foreach usage
     */
    @Override
    public Iterator<WeaponItem> iterator() {
        shuffle();
        return Collections.unmodifiableList(backupWeapons).iterator();
    }

    /**
     * Return the <code>i</code>'th Weapon in the collection.
     *
     * @param i index of WeaponItem to retrieve
     * @return <code>i</code>'th WeaponItem in the collection
     * @throws IndexOutOfBoundsException when <code>i</code> &gt;= <code>this.size()</code>
     */
    public WeaponItem get(int i) {
        return backupWeapons.get(i);
    }

    /**
     * Appends a WeaponItem to this collection if it is not
     * present in this collection and not null
     * <p>
     * Shuffles the collection whenever new WeaponItem is added
     *
     * @param weaponItem the WeaponItem to append
     */
    public void add(WeaponItem weaponItem) {
        if (!backupWeapons.contains(weaponItem) && weaponItem != null) {
            backupWeapons.add(weaponItem);
            shuffle();
        }
    }

    /**
     * Remove the first occurrence of the WeaponItem from the collection
     * if it is present.  If it is not present, the list is unchanged.
     *
     * @param weaponItem the WeaponItem to remove
     */
    public void remove(WeaponItem weaponItem) {
        backupWeapons.remove(weaponItem);
    }

    /**
     * Count the number of WeaponItems in the collection.
     *
     * @return the number of WeaponItems in the collection.
     */
    public int size() {
        return backupWeapons.size();
    }

    /**
     * Shuffles the collection to randomize position of WeaponItem
     * Uses the shuffle method in Collections package
     */
    public void shuffle() {
        Collections.shuffle(backupWeapons);
    }

}
