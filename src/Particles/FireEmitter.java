package Particles;

import org.newdawn.slick.Color;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Pieter on 16/11/2016.
 */
public class FireEmitter extends Emitter {

    @Override
    public List<Particle> emit(double x, double y, double angle, Color color, int numParticles){
        List<Particle> particles = new ArrayList<>();

        for(int i=0; i < numParticles; i++){
            double velocity = Math.random()*5;
            int d = (int) (new Date().getTime());
            int life_time =2; //in seconds
            int dead_milis = d+(life_time*1000);
            Particle p = new Particle(x, y, angle+((Math.random()-0.5)*10), velocity, 5, (double)life_time, dead_milis, color);
            particles.add(p);
        }

        return particles;
    }
}