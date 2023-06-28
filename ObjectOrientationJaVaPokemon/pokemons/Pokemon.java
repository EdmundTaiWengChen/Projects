package game.pokemons;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.AffectionManager;
import game.ElementsHelper;
import game.actions.*;
import game.actors.NonPlayerCharacter;
import game.behaviours.AttackBehaviour;
import game.behaviours.Behaviour;
import game.behaviours.WanderBehaviour;
import game.enums.Element;
import game.enums.ItemStatus;
import game.enums.Status;
import game.weapons.BackupWeapons;
import game.weapons.EffectCapable;

import java.util.TreeMap;

/**
 * Abstract class for pokemons. Holds backup weapons.
 * <p>
 * Created by:
 * @author Chew Jia Hong
 */
public abstract class Pokemon extends NonPlayerCharacter {

    /**
     * Age of pokemon analogue to game turn
     */
    private int age;

    /**
     * Maximum age of pokemon
     */
    public static final int LIFE_SPAN = 20;

    /**
     * True if pokemon is equipped with weapon item
     */
    private boolean equipped;

    /**
     * Second inventory storing weapons for special attacks
     */
    private final BackupWeapons backupWeapons = new BackupWeapons();

    /**
     * Turns required to hatch pokemon egg
     */
    private int eggHatchTurns;

    /**
     * Constructor.
     */
    public Pokemon(String name, char displayChar, int hitPoints) {

        super(name, displayChar, hitPoints);
        addCapability(Status.HOSTILE);
        addCapability(Status.CATCHABLE);
        addCapability(Status.CAN_EAT);

        setAge(0);

        addBehaviour(AttackBehaviour.PRIORITY, new AttackBehaviour());
        addBehaviour(WanderBehaviour.PRIORITY, new WanderBehaviour());

        // initially register the pokemon
        AffectionManager.getInstance().registerPokemon(this);

    }

    /**
     * @return Name of Pokemon
     */
    public String getName() {
        return name;
    }

    /**
     * @return Age of Pokemon
     */
    public int getAge() {
        return age;
    }

    /**
     * @return Pokemon egg required turn to hatch
     */
    public int getEggHatchTurns() {
        return eggHatchTurns;
    }

    /**
     * @return Pokemon backup weapons
     */
    public BackupWeapons getBackupWeapons() {
        return backupWeapons;
    }

    /**
     * Sets age of Pokemon
     * @param age Age of Pokemon
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Sets Pokemon egg required turn to hatch
     * @param turn Number of turns
     */
    public void setEggHatchTurns(int turn) {
        this.eggHatchTurns = turn;
    }

    /**
     * @return True if evolve conditions met
     */
    public boolean shouldEvolve() {
        return isMature();
    }

    /**
     * @param otherActor the Actor that might perform an action.
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {

        ActionList actions = new ActionList();

        AffectionManager affectionManager = AffectionManager.getInstance();

        // checks if pokemon can evolve && with enough AP
        if (otherActor.hasCapability(Status.TRAINER)) {
            if (this.hasCapability(Status.CAN_EVOLVE) && affectionManager.canEvolve(this, otherActor)) {

                actions.add(new EvolveAction(this));
            }
        }

        // attacker and defender are mutually hostile
        if (this.hasCapability(Status.HOSTILE) && otherActor.hasCapability(Status.HOSTILE)) {
            actions.add(new AttackAction(this, direction));
        }

        // check if it is catchable and add the catchPokemonAction
        if (this.hasCapability(Status.CATCHABLE)) {
            actions.add(new CatchPokemonAction(this, direction));
        }

        // check if the player has pokefruit in inventory and allow player to feed pokemon
        for (Item item : otherActor.getInventory()) {
            if (item.hasCapability(ItemStatus.CAN_FEED)) {
                actions.add(new FeedPokemonAction(this, item));
            }
        }

        return actions;
    }

    /**
     * By using behaviour loops, it will decide what will be the next action automatically.
     *
     * @see Actor#playTurn(ActionList, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        if (!this.isConscious() || this.isMature()) {
            return new DeadAction();
        }

        // update the latest location every turn
        currentLocation = map.locationOf(this);

        // update age
        setAge(getAge() + 1);

        // weapon buff
        if (equipped) {
            for (WeaponItem weapon : backupWeapons) {
                if (weapon.hasCapability(ItemStatus.HAS_EFFECT)) {
                    ((EffectCapable) weapon).execute(this, map);
                }
            }
        }

        for (Behaviour behaviour : getBehaviours().values()) {
            Action action = behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * Checks for the condition to give pokemon buffs during attacking.
     * <p>
     * By default, gives buffs if the pokemon and the ground they are on
     * share the same element
     * <p>
     * Override this method to create pokemon specific buffs.
     *
     * @param otherActor actor to be attacked by this pokemon
     * @param map        current game map
     */
    public void buffCheck(Actor otherActor, GameMap map) {

        Location here = map.locationOf(this);

        // equips pokemon if pokemon and ground share same element
        toggleWeapon(ElementsHelper.hasAnySimilarElements(here.getGround(),
                this.findCapabilitiesByType(Element.class)));

    }

    /**
     * Equips or unequips pokemon
     *
     * @param isEquipping True if this pokemon should be equipped
     */
    public void toggleWeapon(boolean isEquipping) {

        if (!equipped && isEquipping) {

            // put weapon in inventory
            for (WeaponItem weapon : backupWeapons) {
                addItemToInventory(weapon);
            }
            equipped = true;

        } else if (equipped) {

            // remove weapon from inventory
            for (WeaponItem weapon : backupWeapons) {
                removeItemFromInventory(weapon);
            }
            equipped = false;

        }

    }

    /**
     * @return name of pokemon along with its hit points & affection points
     */
    public String toString() {

        AffectionManager affectionManager = AffectionManager.getInstance();

        Actor trainer = affectionManager.getMainTrainer();
        int affectionPoints = affectionManager.getAffectionPoint(this, trainer);

        return name + printHp() + printAge() + "(AP: " + affectionPoints + ")";
    }

    /**
     * This is a special method used to print the Pokemon with the affection points associated
     * with a specific trainer instead of the main one
     *
     * @param trainer trainer with associated affection points
     * @return name of pokemon along with its hit points
     */
    public String info(Actor trainer) {

        AffectionManager affectionManager = AffectionManager.getInstance();

        // this will return the affection points of the Pokemon with any specified trainer
        int affectionPoints = affectionManager.getAffectionPoint(this, trainer);

        return name + printHp() + printAge() + "(AP: " + affectionPoints + ")";
    }

    /**
     * @return Message about Pokemon age
     */
    public String printAge() {
        return "(" + getAge() + "yo)";
    }

    /**
     * @return True if pokemon reaches maximum age
     */
    public boolean isMature() {
        return getAge() >= LIFE_SPAN;
    }

}
