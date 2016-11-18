package Particles;




import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import planet.Planet;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Pieter on 16/11/2016.
 */
public class Particle {

    private double x;
    private double y;
    private double angle;
    private double len;

    private Point2D velocity;

    private double radius;
    private double life = 1.0;
    private double decay;
    private double fps, expireTime;

    private Paint color;

    //public Particle(double x, double y, double angle, Point2D velocity, double radius, double expireTime, Paint color, BlendMode blendMode) {
    public Particle(double x, double y, double angle, double len, double radius, double expireTime, int fps) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        //this.velocity = velocity;
        this.len = len;
        this.radius = radius;
        this.fps = fps;
        this.expireTime = expireTime;
        this.color = color;
        //this.blendMode = blendMode;

        if(fps>0)
        this.decay = (1/fps)/expireTime;
    }

    public boolean isAlive(){
        return life > 0;
    }

    public void update(){
        decay = (1/fps)/expireTime;
        System.out.println(decay);
        life -= decay;
        x += 0.05*(len)*(-Math.cos(Math.toRadians(angle)));
        y += 0.05*(len)*Math.sin(Math.toRadians(angle));
        /*x += velocity.getX();
        y += velocity.getY();*/

        //System.out.println(velocity.getX());


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

    public void render(Graphics g){
        //g.drawString("Dit is een test", 30, 30);
        /*g.draw(new Circle(450,450,10));
        g.setColor(Color.red);*/
        //g.fillOval((int)x, (int)y, (int)radius, (int)radius);

    }

}
