package Particles;

//import com.sun.java.swing.plaf.windows.TMSchema;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pieter on 16/11/2016.
 */
public class FireEmitter extends Emitter {

    @Override
    public List<Particle> emit(double x, double y, double angle, int fps){
        List<Particle> particles = new ArrayList<>();

        int numParticles = 1;
        for(int i=0; i < numParticles; i++){
            double len = Math.sqrt(Math.random()+(Math.random()*1)*(Math.random()*1));
            //Particle p = new Particle(x, y, angle, new Point2D(len*Math.cos(Math.toRadians(angle)), len*Math.cos(Math.toRadians(angle))), 5, 1.0, Color.rgb(230, 40, 45), BlendMode.ADD);
            Particle p = new Particle(x, y, angle, len, 10, 1.0, fps);
            particles.add(p);

        }

        return particles;
    }
}