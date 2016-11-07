package com.company;

import java.awt.Dimension;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.BlobbyTransition;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.RotateTransition;

import java.awt.*;

public class Main extends StateBasedGame {

    private static String title;
    static Dimension screen;
    static int SCREEN;
    static int MENU;

    static {
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        title = "Planets";
        SCREEN = 1;
        MENU = 0;
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer gameContainer = new AppGameContainer(new Main(title), screen.width, screen.height, true);
        Util util = new Util();
        gameContainer.start();
    }

    private Main(String title) {
        super(title);
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new Menu());
        addState(new Screen());
        enterState(MENU, new EmptyTransition(), new BlobbyTransition(Color.orange));
    }
}
