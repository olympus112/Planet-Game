package PlanetGame;

import Particles.Emitter;
import Particles.FireEmitter;
import Particles.MainEngine;
import Particles.Particle;
import Planets.Planets;
import Stars.Stars;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import planet.Planet;


import java.util.*;
import java.util.List;


/**
 * Created by Pieter on 17/11/2016.
 */
public class Pieter extends BasicGameState {

    //variables
    public static int BACKGROUND_SPEED = 10;
    public static int MAX_STAR_RADIUS = 3;          //maximum radius of a star in the background
    public static int MAX_PLANET_RADIUS = 800;
    public static int MIN_PLANET_RADIUS = 50;
    public static int PLANET_SEGMENT_SCALE = 5;
    public static int ZOOM = 1;

    // Particles
    private Emitter emitter = new FireEmitter();
    private Emitter mainengine = new MainEngine();
    private List<Particle> particles = new ArrayList<>();
    public static List<Planets> planets = new ArrayList<>();
    public static List<Stars> stars = new ArrayList<>();

    public static int count_p = 0;

    //get screen segment
    int segment_stars_x, segment_stars_y, segment_planets_x, segment_planets_y;
    public static Vector<Data.Coords> discoverd_segments;
    public static Vector<Data.Coords> discoverd_planets;


    //rectagle
    double rect_x;
    double rect_y;

    static boolean[] keys = new boolean[256];
    public int angle;
    public double velocity_x, velocity_y, coord_x, coord_y;

    @Override public int getID() {
            return Main.PIETER;
        }

    @Override public void init(GameContainer container, StateBasedGame game) throws SlickException {
        discoverd_segments = new Vector<>();
        discoverd_planets = new Vector<>();

        angle = 0;
        Planets p = new Planets(0,0,400.0,new Color(245,45,89));
        //planets.addAll(Planets.GeneratePlanets(0, 0, 1));



        //set segment on start
        segment_stars_x = 0;
        segment_stars_y = 0;
        segment_planets_x = 0;
        segment_planets_y = 0;

        rect_x = 0;
        rect_y = 0;


    }

    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {


        //draw stars on background
        for(Iterator<Stars> it = stars.iterator(); it.hasNext();){
            Stars p = it.next();
            //if ((p.GetX()+p.GetRadius())>0 && (p.GetX())<Display.getWidth() && (p.GetY()+p.GetRadius())>0 && (p.GetY())<Display.getHeight()){
                p.draw_stars(g, coord_x, coord_y);
            //}
        }

        //draw planets
        for(Iterator<Planets> it = planets.iterator(); it.hasNext();){
            Planets p = it.next();
            p.draw_planet(g, coord_x, coord_y);
        }


        //draw particles
        for(Iterator<Particle> it = particles.iterator(); it.hasNext();){
            Particle p = it.next();
            p.draw(g);
        }


        g.setColor(new Color(255,255,255));
        g.drawString("Velocity_x: "+Double.toString(velocity_x)+" m/s",10,30);
        g.drawString("Velocity_y: "+Double.toString(velocity_y)+" m/s",10,45);
        g.drawString("Coord_x: "+Double.toString(coord_x),10,60);
        g.drawString("Coord_y: "+Double.toString(coord_y),10,75);
        g.drawString("Planets: " + planets.size(),10,155);
        g.drawString("segment_planets_x: " + segment_planets_x,10,170);
        g.drawString("segment_planets_y: " + segment_planets_y,10,185);



        //draw spaceship
        Image spaceship = new Image("spaceship_small.png");
        spaceship.setRotation(45+angle);
        spaceship.draw((int)(Display.getWidth()/2)-(spaceship.getWidth()/2),(int)(Display.getHeight()/2)-(spaceship.getHeight()/2));
        //g.fillRect((int)(Display.getWidth()/2),(int)(Display.getHeight()/2),100,100,spaceship,0,0);
        //g.fillRect((int)(Display.getWidth()/2)-70,(int)(Display.getHeight()/2)-70,140,140,spaceship,-20,-20);
        //Rectangle ship = new Rectangle((int)(Display.getWidth()/2),(int)(Display.getHeight()/2),100,100,spaceship_pattern,0,0)

    }

    int count_min = 0;

    @Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
            if(container.getInput().isKeyDown(Input.KEY_ESCAPE)) System.exit(0);
            if(container.getInput().isKeyDown(Input.KEY_R)) init(container, game);

        //set FPS to 60
        Display.sync(60);

        for(Iterator<Particle> it = particles.iterator(); it.hasNext();){
            Particle p = it.next();
            p.update();

            if(!p.isAlive()){
                it.remove();
                continue;
            }
        }

        rect_x-=velocity_x*(1/60);
        rect_y+=velocity_x*(1/60);

        //get segment
        segment_stars_x =(int)(Math.floor((coord_x/BACKGROUND_SPEED)/(Display.getWidth()/2)));
        segment_stars_y = (int)(Math.floor((coord_y/BACKGROUND_SPEED)/(Display.getHeight()/2)));
        segment_planets_x =(int)(Math.floor((coord_x/PLANET_SEGMENT_SCALE)/(Display.getWidth()/2)));
        segment_planets_y = (int)(Math.floor((coord_y/PLANET_SEGMENT_SCALE)/(Display.getHeight()/2)));

        //add stars to new segments
        Stars.AddStarToSegment(segment_stars_x, segment_stars_y, 100);

        //add planets to new segments
        Planets.AddStarToSegment(segment_planets_x, segment_planets_y, 1);



        //update coordinate rocket
        coord_x += ((velocity_x/60.0)/ZOOM);
        coord_y += ((velocity_y/60.0)/ZOOM);


        if(container.getInput().isKeyDown(Input.KEY_SPACE)){
            velocity_x+=1*Math.cos(Math.toRadians(angle));
            velocity_y-=1*Math.sin(Math.toRadians(angle));
            //new SoundClipTest();
            particles.addAll(emitter.emit((int)(Display.getWidth()/2)-Math.cos(Math.toRadians(angle+((Math.random()-0.5)*30)))*40, (int)(Display.getHeight()/2)+Math.sin(Math.toRadians(-angle-((Math.random()-0.5)*30)))*40, -angle, new Color(250,40,40), 15)); //emit(x, y, angle)
        }

        if(container.getInput().isKeyDown(Input.KEY_LEFT)){
            particles.addAll(emitter.emit((int)(Display.getWidth()/2)-Math.cos(Math.toRadians(angle-45))*40, (int)(Display.getHeight()/2)+Math.sin(Math.toRadians(-angle+45))*40, -angle, new Color(200,200,200), 2)); //emit(x, y, angle)
            angle-=2;
        }

        if(container.getInput().isKeyDown(Input.KEY_RIGHT)){
            particles.addAll(emitter.emit((int)(Display.getWidth()/2)-Math.cos(Math.toRadians(angle+45))*40, (int)(Display.getHeight()/2)+Math.sin(Math.toRadians(-angle-45))*40, -angle, new Color(200,200,200), 2)); //emit(x, y, angle)
            angle+=2;
        }

        if(container.getInput().isKeyDown(Input.KEY_Z)){
            ZOOM=2;
        }

        if(container.getInput().isKeyDown(Input.KEY_A)){
            ZOOM=1;
        }



    }
    }

