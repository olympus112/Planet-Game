package Particles;

import org.newdawn.slick.Color;

import java.util.List;

/**
 * Created by Pieter on 16/11/2016.
 */
public abstract class Emitter {
    public abstract List<Particle> emit(double x, double y, double angle, Color color, int numParticles);
}
