package PlanetGame;

import Particles.Emitter;
import Particles.FireEmitter;
import Particles.Particle;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Pieter on 17/11/2016.
 */
class Pieter extends BasicGameState {


    // Particles
    private Emitter emitter = new FireEmitter();
    private List<Particle> particles = new ArrayList<>();
    private Graphics gr;
    static boolean[] keys = new boolean[256];
    public int angle;

    @Override public int getID() {
            return Main.PIETER;
        }

    @Override public void init(GameContainer container, StateBasedGame game) throws SlickException {
        gr = new Graphics();
    }

    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        for(Iterator<Particle> it = particles.iterator(); it.hasNext();){
            Particle p = it.next();
            p.update();

            if(!p.isAlive()){
                it.remove();
                continue;
            }
            g.fillOval(p.GetX(),p.GetY(),p.GetRadius(),p.GetRadius());
            g.setColor(new Color(250,40,40,(float)p.GetLife()));
        }

    }

    @Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
            if(container.getInput().isKeyDown(Input.KEY_ESCAPE)) System.exit(0);
            if(container.getInput().isKeyDown(Input.KEY_R)) init(container, game);

        if(container.getInput().isKeyDown(Input.KEY_Z)){
            angle=angle+45;
            if(angle>360){
                angle-=360;
            }
        }


        if (particles.size() < 100)
        particles.addAll(emitter.emit(900, 450, angle, container.getFPS()));


        /*for (int i=0; i < particles.size(); i++){
            System.out.println(particles.get(i).GetLife());
        }*/


        System.out.println(particles.size());


        /*Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp.getTime());*/



    }
    }

