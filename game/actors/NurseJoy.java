package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.TradeCandyAction;
import game.enums.Element;
import game.enums.Status;
import game.items.Pokefruit;
import game.items.PokemonEgg;
import game.pokemons.Bulbasaur;
import game.pokemons.Charmander;
import game.pokemons.Squirtle;

/**
 * This class represents the Nurse Joy who will interact with the player and allow them to trade their inventory items
 * <p>
 * Created by: Avinash Rvan(32717792)
 */
public class NurseJoy extends Actor {

    /**
     * Set up the NurseJoy actor which is immune to attacks
     *
     * @param name
     * @param displayChar
     * @param hitPoints
     */
    public NurseJoy(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);

        addCapability(Status.IMMUNE);
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction(); // NurseJoy does nothing on her own for now
    }

    /**
     * List out all the Trades the player can do, for now that is:
     * <p>
     * -Trade 1 Candy for Pokefruit
     * -Trade 5 Candy for Pokemon Egg with any of the base pokemon inside
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return
     */
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        // create the return items for the trading actions
        Pokefruit fireFruit = new Pokefruit(Element.FIRE);
        Pokefruit waterFruit = new Pokefruit(Element.WATER);
        Pokefruit grassFruit = new Pokefruit(Element.GRASS);
        PokemonEgg charmenderEgg = new PokemonEgg(new Charmander());
        PokemonEgg bulbasaurEgg = new PokemonEgg(new Bulbasaur());
        PokemonEgg squirtleEgg = new PokemonEgg(new Squirtle());

        // add the actions for trading candy
        actions.add(new TradeCandyAction(fireFruit, 1));
        actions.add(new TradeCandyAction(waterFruit, 1));
        actions.add(new TradeCandyAction(grassFruit, 1));
        actions.add(new TradeCandyAction(charmenderEgg, 5));
        actions.add(new TradeCandyAction(bulbasaurEgg, 5));
        actions.add(new TradeCandyAction(squirtleEgg, 5));

        return actions;
    }
}
