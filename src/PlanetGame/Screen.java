package PlanetGame;

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
        for (int i = -20; i < 20; i++) {
            for (int j = -20; j < 20; j++) {
                planets.add(new CirclePlanet(new Vector2f(100*i, 100*j), 50f, 50f));
            }
        }

        camera = new Camera(1.0);
        camera.setFocus(rocket.position);
    }

    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        rocket.draw(g);
        for(Planet planet : planets){
            //slow check -> if(planet.getPhysicsBox().intersects(new Rectangle(0, 0, container.getWidth(), container.getHeight())))
            //faster check but there should be a general method for checking if a physics object is in the screen ->
            //this is basically if(distance(planet, center) > screenDiagonal / 2) draw(planet)
            //if(camera.realToPixelCoords(planet.getPosition()).distance(new Vector2f(container.getWidth()/2, container.getHeight()/2))
            // + planet.getPhysicsBox().getWidth()/2 < Math.sqrt(container.getWidth()*container.getWidth()+container.getHeight()*container.getHeight());)
                planet.draw(g);
        }

    }

    @Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    	for(Planet planet : planets){
    		planet.update(delta);
    	}
    	
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
            rocket.angle = (rocket.angle - delta / 500f) % ((float) Math.PI * 2);
            rocket.angle += (rocket.angle < 0)? (float) Math.PI * 2 : 0;
        }
        if(keys[Input.KEY_D])
            rocket.angle = (rocket.angle + delta / 500f) % ((float) Math.PI * 2);
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
    
    public static Camera getCamera(){
    	return camera;
    }
}
