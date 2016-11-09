package PlanetGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
class Menu extends BasicGameState{

    Polygon p = new Polygon(new float[]{200, 500, 300, 600, 100, 150, 260, 870, 780, 360});

    @Override public int getID() {
        return Main.MENU;
    }

    @Override public void init(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.draw(p);

    }

    @Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        p = (Polygon) p.transform(new Transform(new float[]{
                (float)Math.cos(0.001), 1, 0.01f,
                0, 1, 0
        }));
    }
}
