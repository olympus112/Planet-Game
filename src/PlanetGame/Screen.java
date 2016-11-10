package PlanetGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

class Screen extends BasicGameState{

    private static List<Planet> planets;
    static Rocket rocket;
    static boolean[] keys = new boolean[256];

    @Override public int getID() {
        return Main.SCREEN;
    }

    @Override public void init(GameContainer container, StateBasedGame game) throws SlickException {
        rocket = new Rocket(0, 0);
        planets = new ArrayList<>();
        planets.add(new Planet(300, 300, 100, 1, 1, 1, 90, 20, 0.001f));
        //Planet.generate();
    }

    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        rocket.draw(g);
    }

    @Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {


        // Keyboard event
        if(keys[Input.KEY_SPACE])
            if(container.isPaused()) container.resume();
            else container.pause();
        if(keys[Input.KEY_ESCAPE])
            System.exit(0);
        if(keys[Input.KEY_Q]) {
            rocket.angle = (rocket.angle - delta / 1000f) % Util.PI_2;
            rocket.angle += (rocket.angle < 0)? Util.PI_2 : 0;
        }
        if(keys[Input.KEY_D])
            rocket.angle = (rocket.angle + delta / 1000f) % Util.PI_2;
        if(keys[Input.KEY_Z])
            rocket.velocity.add(Util.fromPolar(0.005f, rocket.angle));
    }

    @Override public void keyPressed(int key, char c) {
        keys[key] = true;
        System.out.println("key = [" + key + "], c = [" + c + "]");
    }

    @Override public void keyReleased(int key, char c) {
        keys[key] = false;
    }

    static void addPlanet(Planet p){
        planets.add(p);
    }

}
