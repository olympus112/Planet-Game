package Particles;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import planet.Planet;

import java.awt.*;
import java.util.Date;

/**
 * Created by Pieter on 16/11/2016.
 */
public class Particle {

    private double x;
    private double y;
    private double angle;
    private double velocity;
    private double radius;
    private double life = 1.0;
    private double dead, expireTime;
    private Color color;

    public Particle(double x, double y, double angle, double velocity, double radius, double expireTime, int dead, Color color) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocity = velocity;
        this.radius = radius;
        this.dead = dead;
        this.expireTime = expireTime;
        this.color = color;
    }

    public void update(){

        int d = (int) (new Date().getTime());
        life = ((dead-d)/1000)/expireTime;
        x += (velocity)*(-Math.cos(Math.toRadians(angle)));
        y += (velocity)*Math.sin(Math.toRadians(angle));

    }

    public boolean isAlive(){
        return life > 0;
    }

    public int GetX(){
        return (int)x;
    }

    public int GetY(){
        return (int)y;
    }

    public int GetRadius(){
        return (int)radius;
    }

    public double GetLife(){
        return life;
    }

    public Color getColor(){ return color; }

    public Graphics draw(Graphics g){
        float alpha = (float)life;
        alpha = alpha*255;
        g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),(int)alpha));
        g.fillOval((float)x, (float)y, (float)radius, (float)radius);
        return g;
    }
}
