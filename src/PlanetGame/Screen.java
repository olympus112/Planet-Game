package PlanetGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
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

    private List<Planet> planets;
    private Camera camera;
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
		camera = new Camera(rocket, 0.1f);
    }
    
    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
    	float zoom = camera.getScale() * container.getHeight();
    	g.pushTransform();
    	
    	//g.translate(-camera.getFocus().x + container.getWidth() / 2, -camera.getFocus().y + container.getHeight() / 2);
    	g.scale(zoom, zoom);
    	rocket.draw(g);
    	g.translate((-camera.getFocus().x + container.getWidth() / 2)/zoom, (-camera.getFocus().y + container.getHeight() / 2)/zoom);
    	
    	
    	g.rotate(0, 0, -(float) (camera.getAngle() * 180 / Math.PI));
    	
    	g.draw(new Circle(-0.5f, -0.5f, 1f));
    	
    	//g.scale(5, 5);
    	
        planets.forEach(planet -> planet.draw(g));
        g.popTransform();
    }
    
    @Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    	planets.forEach((planet -> planet.update(delta)));
        rocket.update(delta);

        // Keyboard event
        if(keys[Input.KEY_SPACE])
            camera.setSubject(planets.get(0));
        if(keys[Input.KEY_R])
            camera.setSubject(rocket);
        if(keys[Input.KEY_P])
            camera.setScale(0.2f);
        if(keys[Input.KEY_M])
            camera.setScale(0.1f);
        if(keys[Input.KEY_ESCAPE])
            System.exit(0);
        if(keys[Input.KEY_Q]) {
            rocket.setAngle((rocket.angle - delta / 500f) % ((float) Math.PI * 2));
            rocket.setAngle(rocket.angle + ((rocket.angle < 0)? (float) Math.PI * 2 : 0));
        }
        if(keys[Input.KEY_D])
            rocket.setAngle((rocket.angle + delta / 500f) % ((float) Math.PI * 2));
        if(keys[Input.KEY_Z])
            rocket.setVelocity(rocket.velocity.add(Util.fromPolar(500f, rocket.angle - Math.PI / 2)));
		rocket.update(delta);
		System.out.println(rocket.position);
    }
    
    @Override public void keyPressed(int key, char c) {
        keys[key] = true;
    }
    
    @Override public void keyReleased(int key, char c) {
        keys[key] = false;
    }

    public void addPlanet(Planet p){
        planets.add(p);
    }
}
