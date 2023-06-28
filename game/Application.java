package game;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Goh;
import game.actors.NurseJoy;
import game.actors.Player;
import game.grounds.*;

import java.util.Arrays;
import java.util.List;

/**
 * The main class to start the game.
 * Created by:
 *
 * @author Riordan D. Alfredo
 * Modified by:
 */
public class Application {

    public static void main(String[] args) {

        World world = new World(new Display());

        FancyGroundFactory groundFactory2 = new FancyGroundFactory(new Floor(), new Wall(), new Dirt(), new Door(), new Incubator());
        List<String> map2 = Arrays.asList(
                "########################",
                "#______________________#",
                "#_..x..............x.._#",
                "#_______.........______#",
                "#________......._______#",
                "###########_=_##########"
        );
        GameMap gameMap1 = new GameMap(groundFactory2, map2);
        world.addGameMap(gameMap1);

        //Add player - Nurse Joy
        NurseJoy nurseJoy = new NurseJoy("Nurse Joy", '%', 1);
        world.addPlayer(nurseJoy, gameMap1.at(12, 2));

        FancyGroundFactory groundFactory = new FancyGroundFactory(new Floor(), new Wall(),
                new Dirt(), new Hay(), new Lava(), new Puddle(),
                new Tree(), new Waterfall(), new Crater(), new Door());
        List<String> map = Arrays.asList(
                ".............................................^^^^^^^^^^^^^^",
                "...........,T,................................,T,..^^^^O^^^",
                ".....................................................^^^^^^",
                "........................................................^^^",
                "............................###.............,,...........^^",
                "............................#=#.............,T............^",
                "....................,T.....................................",
                "..,T,......~...............................................",
                "...~~~~~~~~................................................",
                "....~~~~~...........T......................................",
                "~~W~~~~.,..................................................",
                "~~~~~~.,T............................,T,...................",
                "~~~~~~~~~..................................................");
        GameMap gameMap = new GameMap(groundFactory, map);
        world.addGameMap(gameMap);

        // Add player - Goh
        Goh goh = Goh.getInstance();
        world.addPlayer(goh, gameMap.at(21, 10));

        //Add player - Ash
        // add ash last so that he is at the front of the actors list (use a boolean to ensure Ash is the Main Trainer)
        Player ash = new Player("Ash", '@', 1, true);
        world.addPlayer(ash, gameMap.at(29, 10));

        world.run();

    }
}
