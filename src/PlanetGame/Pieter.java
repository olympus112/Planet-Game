package PlanetGame;

import Particles.Emitter;
import Particles.FireEmitter;
import Particles.MainEngine;
import Particles.Particle;
import Planets.Planets;
import Stars.Stars;
import javafx.scene.image.*;
import javafx.scene.paint.ImagePattern;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Pieter on 17/11/2016.
 */
class Pieter extends BasicGameState {


    // Particles
    private Emitter emitter = new FireEmitter();
    private Emitter mainengine = new MainEngine();
    private List<Particle> particles = new ArrayList<>();
    private List<Planets> planets = new ArrayList<>();
    private List<Stars> stars = new ArrayList<>();

    //minimum and maximum area explored
    double min_x, min_y, max_x, max_y;

    static boolean[] keys = new boolean[256];
    public int angle;
    public double velocity_x, velocity_y, coord_x, coord_y;

    @Override public int getID() {
            return Main.PIETER;
        }

    @Override public void init(GameContainer container, StateBasedGame game) throws SlickException {
        //Renderer.setRenderer(Renderer.VERTEX_ARRAY_RENDERER);
        angle = 0;
        Planets p = new Planets(0.0,-500.0,400.0,new Color(245,45,89));
        planets.add(p);

        Stars s = new Stars(50, 50, 2, new Color(245,245,245));
        stars.add(s);

        //set minimum and maximum area explored
        min_x = coord_x-(Display.getWidth()/2);
        min_y = coord_y-(Display.getHeight()/2);
        max_x = coord_x+(Display.getWidth()/2);
        max_y = coord_y+(Display.getHeight()/2);



    }

    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        List<Planets> visible_planets = new ArrayList<>();

        //draw planets
        for(Iterator<Planets> it = planets.iterator(); it.hasNext();){
            Planets p = it.next();
            if ((p.GetX()+p.GetRadius())>0 && (p.GetX())<Display.getWidth() && (p.GetY()+p.GetRadius())>0 && (p.GetY())<Display.getHeight()){
                p.draw_planet(g);
                g.setColor(new Color(255,255,255));
                visible_planets.add(p);
                g.drawString("Planet detected: " +visible_planets.size(), 10,100);

            }else{
                g.setColor(new Color(255,255,255));
                g.drawString("Planet detected: "+visible_planets.size(), 10,100);

            }

        }

        //draw stars
        for(Iterator<Stars> it = stars.iterator(); it.hasNext();){
            Stars p = it.next();
            if ((p.GetX()+p.GetRadius())>0 && (p.GetX())<Display.getWidth() && (p.GetY()+p.GetRadius())>0 && (p.GetY())<Display.getHeight()){
                p.draw_stars(g);
            }

        }

        //draw particles
        for(Iterator<Particle> it = particles.iterator(); it.hasNext();){
            Particle p = it.next();
            p.draw(g);
        }

        visible_planets.clear();

        g.setColor(new Color(255,255,255));
        g.drawString("Velocity_x: "+Double.toString(velocity_x)+" m/s",10,30);
        g.drawString("Velocity_y: "+Double.toString(velocity_y)+" m/s",10,45);
        g.drawString("Coord_x: "+Double.toString(coord_x),10,60);
        g.drawString("Coord_y: "+Double.toString(coord_y),10,75);
        g.drawString("min (x,y): ("+Double.toString(min_x)+","+Double.toString(min_y)+")",10,125);
        g.drawString("max (x,y): ("+Double.toString(max_x)+","+Double.toString(max_y)+")",10,140);
        g.drawString("Stars: " + stars.size(),10,155);

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

        //update minimum and maximum area explored
        if ((coord_x-(Display.getWidth()/2)) < min_x){
            min_x = coord_x-(Display.getWidth()/2);
            count_min+=1;
        }
        if ((coord_y-(Display.getHeight()/2)) < min_y){
            min_y = coord_y-(Display.getHeight()/2);
            count_min+=1;
        }
        if ((coord_x+(Display.getWidth()/2)) > max_x){
            max_x = coord_x+(Display.getWidth()/2);
        }
        if ((coord_y+(Display.getHeight()/2)) > max_y){
            max_y = coord_y+(Display.getHeight()/2);
        }

        if (count_min > 100){
            count_min = 0;

            //linker kant
            double min_x_ = min_x - 100;
            double rand_x = min_x + (int)(Math.random() * ((max_x - min_x) + 1));
            double rand_y = ((Display.getHeight()/2)-coord_y) + (Math.random() * ((((Display.getHeight()/2)+coord_y) - ((Display.getHeight()/2)-coord_y)) + 1));



            Stars s = new Stars(rand_x, rand_y, 3, new Color(245,245,245));
            stars.add(s);
        }


        //draw planets
        for(Iterator<Planets> it = planets.iterator(); it.hasNext();){
            Planets p = it.next();
            p.update(velocity_x, velocity_y);
        }

        //draw stars
        for(Iterator<Stars> it = stars.iterator(); it.hasNext();){
            Stars s = it.next();
            s.update(velocity_x, velocity_y);
        }

        //update coordinate rocket
        coord_x += (velocity_x/60.0);
        coord_y += (velocity_y/60.0);


        if(container.getInput().isKeyDown(Input.KEY_SPACE)){
            velocity_x+=0.5*Math.cos(Math.toRadians(angle));
            velocity_y-=0.5*Math.sin(Math.toRadians(angle));
            particles.addAll(emitter.emit((int)(Display.getWidth()/2)-Math.cos(Math.toRadians(angle+((Math.random()-0.5)*30)))*40, (int)(Display.getHeight()/2)+Math.sin(Math.toRadians(-angle-((Math.random()-0.5)*30)))*40, -angle, new Color(250,40,40), 15)); //emit(x, y, angle)
        }

        if(container.getInput().isKeyDown(Input.KEY_LEFT)){
            particles.addAll(emitter.emit((int)(Display.getWidth()/2)-Math.cos(Math.toRadians(angle-45))*40, (int)(Display.getHeight()/2)+Math.sin(Math.toRadians(-angle+45))*40, -angle, new Color(200,200,200), 2)); //emit(x, y, angle)
            angle-=1;
        }

        if(container.getInput().isKeyDown(Input.KEY_RIGHT)){
            particles.addAll(emitter.emit((int)(Display.getWidth()/2)-Math.cos(Math.toRadians(angle+45))*40, (int)(Display.getHeight()/2)+Math.sin(Math.toRadians(-angle-45))*40, -angle, new Color(200,200,200), 2)); //emit(x, y, angle)
            angle+=1;
        }




    }
    }

