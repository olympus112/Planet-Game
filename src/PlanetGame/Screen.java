package PlanetGame;

import Particles.Particle;
import Stars.Stars;
import config.InvalidConfigException;
import config.KeyValueConfig;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.File;
import java.io.IOException;
import java.util.*;

import util.Action;
import util.Util;
import view.Camera;


public class Screen extends BasicGameState{

    private static Map<String, Action> keyMap = new HashMap<>();
    private static List<String> keys = new ArrayList<>();
    KeyValueConfig config;
    private static boolean[] oldKeys = new boolean[256];
    private List<Particle> particles = new ArrayList<>();
    public static int BACKGROUND_SPEED = 10;
    private Camera camera;
    private Rocket rocket;
    private Stars stars;

    @Override public int getID() {
        return Main.SCREEN;
    }

    @Override public void init(GameContainer container, StateBasedGame game) throws SlickException {
        stars = new Stars();
        rocket = new Rocket(0, 0);
		camera = new Camera(rocket, .5f);
        mapKeys("config.txt");

        //for (int i = 0; i < texture_nr; i++) {
        //    planet_texture.add(FishEye.FishEye_Image(Planets.GeneratePlanetTexture()));
        //}
    }

    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
    	for(Stars star : Stars.stars) {
            star.draw(g, rocket.position.x, rocket.position.y);
        }

        applyTransformation(g);
        rocket.draw(g);

        for(Particle particle : particles) {
            particle.draw(g);
        }
    }

    @Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int segment_stars_x = (int) (Math.floor((rocket.position.x / BACKGROUND_SPEED) / (Display.getWidth() / 2)));
        int segment_stars_y = (int) (Math.floor((rocket.position.y / BACKGROUND_SPEED) / (Display.getHeight() / 2)));
        stars.addToSegment(segment_stars_x, segment_stars_y, 100);

        for(Iterator<Particle> it = particles.iterator(); it.hasNext();){
            Particle p = it.next();
            p.update();
            if(!p.isAlive()) it.remove();
        }

        keys.forEach((key) -> keyMap.get(key).perform(delta));
        rocket.update(delta);
    }

    //left particles.addAll(emitter.emit((Display.getWidth() / 2) - Math.cos(Math.toRadians(angle - 45)) * 40, (Display.getHeight() / 2) + Math.sin(Math.toRadians(-angle + 45)) * 40, -angle, new Color(200, 200, 200), 2));
    //right particles.addAll(emitter.emit((Display.getWidth() / 2) - Math.cos(Math.toRadians(angle + 45)) * 40, (Display.getHeight() / 2) + Math.sin(Math.toRadians(-angle - 45)) * 40, -angle, new Color(200, 200, 200), 2));
    //forward particles.addAll(emitter.emit((Display.getWidth() / 2) - Math.cos(Math.toRadians(angle + ((Math.random() - 0.5) * 30))) * 40, (Display.getHeight() / 2) + Math.sin(Math.toRadians(-angle - ((Math.random() - 0.5) * 30))) * 40, -angle, new Color(250, 40, 40), 15)); //emit(x, y, angle);

    private void mapKeys(String path) {
        try {
            config = new KeyValueConfig(new File(path));
            keyMap.put(config.get("ZoomIn"), (delta) -> camera.setScale(camera.getScale() + delta / 500f));
            keyMap.put(config.get("ZoomOut"), (delta) -> camera.setScale(camera.getScale() - delta / 500f));
            keyMap.put(config.get("KeyExit"), (delta) -> System.exit(0));
            keyMap.put(config.get("KeySetSubject"), (delta) -> camera.setSubject(rocket));
            keyMap.put(config.get("KeyForward"), (delta) ->  rocket.setVelocity(rocket.velocity.add(Util.fromPolar(0.1f * delta, rocket.angle))));
            keyMap.put(config.get("KeyRight"), (delta) ->  rocket.setAngle((rocket.angle + delta / 500f) % ((float) Math.PI * 2)));
            keyMap.put(config.get("KeyLeft"), (delta) ->  {
                rocket.setAngle((rocket.angle - delta / 500f) % ((float) Math.PI * 2));
                rocket.setAngle(rocket.angle + ((rocket.angle < 0)? (float) Math.PI * 2 : 0));
            });
        } catch (InvalidConfigException | IOException e) {e.printStackTrace();}
    }

    private void applyTransformation(Graphics g) {
        float zoom = camera.getScale();
        g.translate(Display.getWidth() / 2, Display.getHeight() / 2);
        g.scale(zoom, zoom);
        g.translate(-rocket.getPosition().x, -rocket.getPosition().y);
    }

    @Override public void keyPressed(int key, char c) {
        if (config.keyValuePairs.containsValue(Input.getKeyName(key)))
            keys.add(Input.getKeyName(key));
    }
    
    @Override public void keyReleased(int key, char c) {
        if (keys.contains(Input.getKeyName(key)))
            keys.remove(Input.getKeyName(key));
    }
}
