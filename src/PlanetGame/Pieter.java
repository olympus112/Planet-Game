package PlanetGame;

import Particles.Emitter;
import Particles.FireEmitter;
import Particles.MainEngine;
import Particles.Particle;
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
    static boolean[] keys = new boolean[256];
    public int angle;

    @Override public int getID() {
            return Main.PIETER;
        }

    @Override public void init(GameContainer container, StateBasedGame game) throws SlickException {
        //Renderer.setRenderer(Renderer.VERTEX_ARRAY_RENDERER);
        angle = 0;
    }

    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        //draw particles
        for(Iterator<Particle> it = particles.iterator(); it.hasNext();){
            Particle p = it.next();
            float alpha = (float)p.GetLife();
            alpha = alpha*255;
            g.setColor(new Color(p.getColor().getRed(),p.getColor().getGreen(),p.getColor().getBlue(),(int)alpha));
            g.fillOval(p.GetX(),p.GetY(),p.GetRadius(),p.GetRadius());

        }

        //draw spaceship
        Image spaceship = new Image("spaceship_small.png");
        spaceship.setRotation(45+angle);
        spaceship.draw((int)(Display.getWidth()/2)-(spaceship.getWidth()/2),(int)(Display.getHeight()/2)-(spaceship.getHeight()/2));
        //g.fillRect((int)(Display.getWidth()/2),(int)(Display.getHeight()/2),100,100,spaceship,0,0);
        //g.fillRect((int)(Display.getWidth()/2)-70,(int)(Display.getHeight()/2)-70,140,140,spaceship,-20,-20);
        //Rectangle ship = new Rectangle((int)(Display.getWidth()/2),(int)(Display.getHeight()/2),100,100,spaceship_pattern,0,0)

    }

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

        if(container.getInput().isKeyDown(Input.KEY_SPACE)){
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

