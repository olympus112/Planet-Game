package PlanetGame;


import Particles.Emitter;
import Particles.FireEmitter;
import Particles.Particle;
//import Planets.Planets;
import Stars.Stars;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.Util;
import view.Camera;

//import static Planets.Planets.planet_texture;

public class Screen extends BasicGameState{

    private List<Particle> particles = new ArrayList<>();
    private static boolean[] keys = new boolean[256];
    private Dimension screenSize;
    private Camera camera;
    private Rocket rocket;
    private Stars stars;

    int segment_stars_x, segment_stars_y;
    public static int BACKGROUND_SPEED = 10;

    @Override public int getID() {
        return Main.SCREEN;
    }

    @Override public void init(GameContainer container, StateBasedGame game) throws SlickException {
        screenSize = new Dimension(container.getWidth(), container.getHeight());
        stars = new Stars();
        rocket = new Rocket(0, 0);
		camera = new Camera(rocket, .5f);

        //for (int i = 0; i < texture_nr; i++) {
        //    planet_texture.add(FishEye.FishEye_Image(Planets.GeneratePlanetTexture()));
        //}
    }

    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
    	for(Stars star : Stars.stars) {
            star.draw(g, rocket.position.x, rocket.position.y);
        }

        applyTranformation(g);
        rocket.draw(g);

        for(Particle particle : particles) {
            particle.draw(g);
        }
    }

    @Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        segment_stars_x = (int) (Math.floor((rocket.position.x / BACKGROUND_SPEED) / (Display.getWidth() / 2)));
        segment_stars_y = (int) (Math.floor((rocket.position.y / BACKGROUND_SPEED) / (Display.getHeight() / 2)));
        stars.addToSegment(segment_stars_x, segment_stars_y, 100);


        for(Iterator<Particle> it = particles.iterator(); it.hasNext();){
            Particle p = it.next();
            p.update();
            if(!p.isAlive()) it.remove();
        }

        keyInput(delta); // TODO config compatible

        rocket.update(delta);
    }

    void keyInput(int delta) {
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
            //particles.addAll(emitter.emit((Display.getWidth() / 2) - Math.cos(Math.toRadians(angle - 45)) * 40, (Display.getHeight() / 2) + Math.sin(Math.toRadians(-angle + 45)) * 40, -angle, new Color(200, 200, 200), 2));
        }
        if(keys[Input.KEY_D]) {
            rocket.setAngle((rocket.angle + delta / 500f) % ((float) Math.PI * 2));
            //particles.addAll(emitter.emit((Display.getWidth() / 2) - Math.cos(Math.toRadians(angle + 45)) * 40, (Display.getHeight() / 2) + Math.sin(Math.toRadians(-angle - 45)) * 40, -angle, new Color(200, 200, 200), 2));
        }
        if(keys[Input.KEY_Z]) {
            rocket.setVelocity(rocket.velocity.add(Util.fromPolar(0.1f * delta, rocket.angle)));
            //particles.addAll(emitter.emit((Display.getWidth() / 2) - Math.cos(Math.toRadians(angle + ((Math.random() - 0.5) * 30))) * 40, (Display.getHeight() / 2) + Math.sin(Math.toRadians(-angle - ((Math.random() - 0.5) * 30))) * 40, -angle, new Color(250, 40, 40), 15)); //emit(x, y, angle);
        }
    }

    void applyTranformation(Graphics g) {
        float zoom = camera.getScale();
        g.translate(screenSize.width / 2, screenSize.height / 2);
        g.scale(zoom, zoom);
        g.translate(-rocket.getPosition().x, -rocket.getPosition().y);
    }

    @Override public void keyPressed(int key, char c) {
        keys[key] = true;
    }
    
    @Override public void keyReleased(int key, char c) {
        keys[key] = false;
    }
}
