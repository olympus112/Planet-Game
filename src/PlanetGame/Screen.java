package PlanetGame;

import Particles.Emitter;
import Particles.FireEmitter;
import Particles.Particle;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import planet.CirclePlanet;
import planet.Planet;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.Util;
import view.Camera;

public class Screen extends BasicGameState{

    private static List<Planet> planets;
    private static Camera camera;
    private Rocket rocket;
    static boolean[] keys = new boolean[256];


    @Override public int getID() {
        return Main.SCREEN;
    }

    @Override public void init(GameContainer container, StateBasedGame game) throws SlickException {
        rocket = new Rocket(0, 0);
        planets = new ArrayList<>();
        for (int i = -2; i < 2; i++) {
            for (int j = -2; j < 2; j++) {
                planets.add(new CirclePlanet(new Vector2f(400*i, 400*j), 100f, 100f));
            }
        }
        camera = new Camera(1.0);
        camera.setFocus(rocket.position);
    }

    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        rocket.draw(g);
        planets.forEach(planet -> planet.draw(g));
    }

    @Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    	planets.forEach((planet -> planet.update(delta)));
        rocket.update(delta);

        // Keyboard event
        if(keys[Input.KEY_SPACE])
            camera.setFocus(planets.get(0).getPosition());
        if(keys[Input.KEY_R])
            camera.setFocus(rocket.position);
        if(keys[Input.KEY_P])
            camera.setZoom(2);
        if(keys[Input.KEY_M])
            camera.setZoom(1);
        if(keys[Input.KEY_ESCAPE])
            System.exit(0);
        if(keys[Input.KEY_Q]) {
            rocket.setAngle((rocket.angle - delta / 500f) % ((float) Math.PI * 2));
            rocket.setAngle(rocket.angle + ((rocket.angle < 0)? (float) Math.PI * 2 : 0));
        }
        if(keys[Input.KEY_D])
            rocket.setAngle((rocket.angle + delta / 500f) % ((float) Math.PI * 2));
        if(keys[Input.KEY_Z])
            rocket.setVelocity(rocket.velocity.add(Util.fromPolar(0.005f, rocket.angle)));
    }

    @Override public void keyPressed(int key, char c) {
        keys[key] = true;
    }

    @Override public void keyReleased(int key, char c) {
        keys[key] = false;
    }

    static void addPlanet(Planet p){
        planets.add(p);
    }
    
    public static Camera getCamera(){
    	return camera;
    }
}
