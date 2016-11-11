package PlanetGame;

import java.awt.Dimension;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import java.awt.*;


public class Main extends StateBasedGame {

    private static String title;
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int SCREEN;
    static int MENU;

    static {
        title = "Planets";
        SCREEN = 1;
        MENU = 0;
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer gameContainer = new AppGameContainer(new Main(title), screenSize.width, screenSize.height, false);
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
