package PlanetGame;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import util.Perlin;

class Menu extends BasicGameState{

    Polygon poly;
    Perlin p;

    @Override public int getID() {
        return Main.MENU;
    }

    @Override public void init(GameContainer container, StateBasedGame game) throws SlickException {
        p = new Perlin();
        poly = new Polygon();
        poly.setClosed(false);
        for (float i = 0; i < 200; i+=0.2f) {
            poly.addPoint(20*i, p.getVal(i)*2+400);
        }
    }

    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.draw(poly);
    }

    @Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if(container.getInput().isKeyDown(Input.KEY_ESCAPE)) System.exit(0);
        if(container.getInput().isKeyDown(Input.KEY_R)) init(container, game);
    }
}
