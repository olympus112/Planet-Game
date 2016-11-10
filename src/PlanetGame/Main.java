package PlanetGame;

import java.awt.Dimension;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import java.awt.*;

public class Main extends StateBasedGame {

    private static String title;
    static int SCREEN;
    static int MENU;

    static {
        title = "Planets";
        SCREEN = 1;
        MENU = 0;
    }

    public static void main(String[] args) throws SlickException {
        Util util = new Util();
        AppGameContainer gameContainer = new AppGameContainer(new Main(title), Util.screen.width, Util.screen.height, true);
        gameContainer.start();
    }

    private Main(String title) {
        super(title);
    }

    @Override public void initStatesList(GameContainer container) throws SlickException {
        addState(new Menu());
        addState(new Screen());
        enterState(SCREEN);
    }
}
