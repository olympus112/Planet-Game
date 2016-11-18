package PlanetGame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.effects.FireEmitter;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


class Menu extends BasicGameState{


    @Override public int getID() {
        return Main.MENU;
    }

    @Override public void init(GameContainer container, StateBasedGame game) throws SlickException {
    }

    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

    }

    @Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if(container.getInput().isKeyDown(Input.KEY_ESCAPE)) System.exit(0);
        if(container.getInput().isKeyDown(Input.KEY_R)) init(container, game);
    }
}
