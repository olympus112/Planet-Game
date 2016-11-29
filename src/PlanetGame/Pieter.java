package PlanetGame;

import Particles.Emitter;
import Particles.FireEmitter;
import Particles.MainEngine;
import Particles.Particle;
import Planets.Planets;
import Stars.Stars;
import config.Config;
import config.FileConfig;
import config.InvalidConfigException;
import config.KeyValueConfig;
import javafx.scene.paint.ImagePattern;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.BufferedImageUtil;
import org.newdawn.slick.util.ResourceLoader;
import planet.Planet;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import static Planets.Planets.*;


/**
 * Created by Pieter on 17/11/2016.
 */
public class Pieter extends BasicGameState {

    //variables
    public static int BACKGROUND_SPEED = 10;
    public static int MAX_STAR_RADIUS = 3;          //maximum radius of a star in the background
    public static int MAX_PLANET_RADIUS = 200;
    public static int MIN_PLANET_RADIUS = 50;
    public static int PLANET_SEGMENT_SCALE = 1;
    public static float ZOOM = 1;

    // Particles
    private Emitter emitter = new FireEmitter();
    private Emitter mainengine = new MainEngine();
    private List<Particle> particles = new ArrayList<>();
    public static List<Planets> planets = new ArrayList<>();
    public static List<Stars> stars = new ArrayList<>();

    //planet texture
  /*  public static BufferedImage background;
    public static Image backg_moon;
    public static Image backg_earth;
    public static Image planet1;
    public static Image planet2;
    public static Image planet3;
    public static Image planet4;
    public static Image bg_circle;*/

    public static int count_p = 0;

    //get screen segment
    int segment_stars_x, segment_stars_y, segment_planets_x, segment_planets_y;
    public static Vector<Data.Coords> discoverd_segments;
    public static Vector<Data.Coords> discoverd_planets;

    //firt run
    public boolean firstrun = true;
    int countdown = 0;

    //number of textures that are generated when the game starts
    public static int texture_nr = 10;

    static boolean[] keys = new boolean[256];
    public static int angle;
    public static double velocity_x, velocity_y, coord_x, coord_y;

    @Override public int getID() {
        return Main.PIETER;
    }

    public boolean inCircle(int x, int y, int radius, int max_x, int max_y){
        boolean condition = false;

        if((x+radius)<max_x && (y+radius)<max_y && (x-radius)>0 && (y-radius)>0){
            condition = true;
        }

        return condition;
    }

    @Override public void init(GameContainer container, StateBasedGame game) throws SlickException {

        planet_texture = new ArrayList<>();

        discoverd_segments = new Vector<>();
        discoverd_planets = new Vector<>();

        angle = 0;

        //set segment on start
        segment_stars_x = 0;
        segment_stars_y = 0;
        segment_planets_x = 0;
        segment_planets_y = 0;

        /*Planets sun = new Planets(0, 0, 695*2, new Color(255,255,2), 1, new Image("planet3.png"));
        planets.add(sun);
        Planets earth = new Planets(149600, 0, 6.371*2, new Color(255,255,2), 1, new Image("texture_earth_clouds.png"));
        planets.add(earth);*/

    }



    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        // wait until all planet textures are generated
        if (firstrun==true){

            Rectangle panel = new Rectangle(0,0,300,60);
            panel.setX(Display.getWidth()/2-panel.getWidth()/2);
            panel.setY(Display.getHeight()/2-panel.getHeight()/2);
            g.setColor(new Color(144,144,144));
            g.fill(panel);

            float procent = (float) ((countdown/(double)(texture_nr-1.0))*100);
            Rectangle progress = new Rectangle(0,0,300,20);
            progress.setWidth((procent/100)*progress.getWidth());
            progress.setX(Display.getWidth()/2-panel.getWidth()/2);
            progress.setY(Display.getHeight()/2+40-panel.getHeight()/2);
            g.setColor(new Color(50,255,50));
            g.fill(progress);


            g.setColor(new Color(255,255,255));
            g.drawString("Generating planets", Display.getWidth()/2-90, Display.getHeight()/2-20);
            if (countdown < texture_nr) {
                countdown++;
                planet_texture.add(FishEye.FishEye_Image(Planets.GeneratePlanetTexture()));
            }
            if (countdown==(texture_nr-1)){
                firstrun=false;
            }


        }else {


            //draw stars on background
            for (Iterator<Stars> it = Stars.stars.iterator(); it.hasNext(); ) {
                Stars p = it.next();
                p.draw_stars(g, coord_x, coord_y);

            }

            //draw planets
            for (Iterator<Planets> it = planets.iterator(); it.hasNext(); ) {
                Planets p = it.next();
                p.draw_planet(g, coord_x, coord_y, p.GetPlanet());
            }


            //draw particles
            for (Iterator<Particle> it = particles.iterator(); it.hasNext(); ) {
                Particle p = it.next();
                p.draw(g);
            }

            //draw information text
            g.setColor(new Color(255, 255, 255));
            g.drawString("Velocity_x: " + Double.toString(velocity_x) + " m/s", 10, 30);
            g.drawString("Velocity_y: " + Double.toString(velocity_y) + " m/s", 10, 45);
            g.drawString("Coord_x: " + Double.toString(coord_x), 10, 60);
            g.drawString("Coord_y: " + Double.toString(coord_y), 10, 75);
            g.drawString("Zoom: " + Double.toString(ZOOM), 10, 90);
            g.drawString("Stars: " + Stars.stars.size(), 10, 140);
            g.drawString("Planets: " + planets.size(), 10, 155);
            g.drawString("segment_planets_x: " + segment_planets_x, 10, 170);
            g.drawString("segment_planets_y: " + segment_planets_y, 10, 185);

            //draw spaceship
            Image spaceship = new Image("spaceship_small.png");
            spaceship.setRotation(45 + angle);
            spaceship.draw((Display.getWidth() / 2) - (spaceship.getWidth() / 2), (Display.getHeight() / 2) - (spaceship.getHeight() / 2), ZOOM);
        }
    }

    @Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

        if(firstrun==false) {

            if (container.getInput().isKeyDown(Input.KEY_ESCAPE)) System.exit(0);
            if (container.getInput().isKeyDown(Input.KEY_R)) init(container, game);

            //set FPS to 60
            Display.sync(60);

            for (Iterator<Particle> it = particles.iterator(); it.hasNext(); ) {
                Particle p = it.next();
                p.update();

                if (!p.isAlive()) {
                    it.remove();
                    continue;
                }
            }


            //get segment
            segment_stars_x = (int) (Math.floor((coord_x / BACKGROUND_SPEED) / (Display.getWidth() / 2)));
            segment_stars_y = (int) (Math.floor((coord_y / BACKGROUND_SPEED) / (Display.getHeight() / 2)));
            segment_planets_x = (int) (Math.floor((coord_x / PLANET_SEGMENT_SCALE) / (Display.getWidth() / 2)));
            segment_planets_y = (int) (Math.floor((coord_y / PLANET_SEGMENT_SCALE) / (Display.getHeight() / 2)));

            //add stars to new segments
            Stars.AddToSegment(segment_stars_x, segment_stars_y, 100);

            //add planets to new segments
            Planets.AddToSegment(segment_planets_x, segment_planets_y, 1);


            //update coordinate rocket
            coord_x += ((velocity_x / 60.0));
            coord_y += ((velocity_y / 60.0));


            if (container.getInput().isKeyDown(Input.KEY_SPACE)) {
                velocity_x += 1 * Math.cos(Math.toRadians(angle));
                velocity_y -= 1 * Math.sin(Math.toRadians(angle));
                particles.addAll(emitter.emit((int) (Display.getWidth() / 2) - Math.cos(Math.toRadians(angle + ((Math.random() - 0.5) * 30))) * 40, (int) (Display.getHeight() / 2) + Math.sin(Math.toRadians(-angle - ((Math.random() - 0.5) * 30))) * 40, -angle, new Color(250, 40, 40), 15)); //emit(x, y, angle)
            }

            if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
                particles.addAll(emitter.emit((int) (Display.getWidth() / 2) - Math.cos(Math.toRadians(angle - 45)) * 40, (int) (Display.getHeight() / 2) + Math.sin(Math.toRadians(-angle + 45)) * 40, -angle, new Color(200, 200, 200), 2)); //emit(x, y, angle)
                angle -= 2;
            }

            if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
                particles.addAll(emitter.emit((int) (Display.getWidth() / 2) - Math.cos(Math.toRadians(angle + 45)) * 40, (int) (Display.getHeight() / 2) + Math.sin(Math.toRadians(-angle - 45)) * 40, -angle, new Color(200, 200, 200), 2)); //emit(x, y, angle)
                angle += 2;
            }

            if (container.getInput().isKeyDown(Input.KEY_Z)) {
                velocity_x = 0;
                velocity_y = 0;
            }
            if (container.getInput().isKeyDown(Input.KEY_A)) {
                //ZOOM -= 0.05;
            }

            if(angle > 360){
                angle-=360;
            }
            if(angle < 0){
                angle+=360;
            }

        }

    }
}

