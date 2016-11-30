package PlanetGame;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import util.Drawable;
import util.PhysicsObject;


class Rocket implements PhysicsObject, Drawable{
	float mass = 1;
    Vector2f position;
    Vector2f velocity;
    Image image;
    float angle;
    float maximumVelocity = 1000;

    Rocket(float x, float y) throws SlickException {
        position = new Vector2f(x, y);
        velocity = new Vector2f(0, 0);
        image = new Image("res/rocket.png");
    }

    @Override public void draw(Graphics g) {
        g.drawGradientLine(450, 450, Color.red, 450 + velocity.x, 450 + velocity.y, Color.green);
        g.drawGradientLine(450, 450, Color.red, 450 + (float) Math.cos(angle) * 50, 450 + (float) Math.sin(angle) * 50, Color.yellow);
        g.drawArc(435, 435, 30, 30, Math.min((float)Math.toDegrees(angle), (float)velocity.getTheta()), Math.max((float)Math.toDegrees(angle), (float)velocity.getTheta()));
        g.drawString(velocity.toString(), 5, 30);
        g.drawString("" + angle, 5, 50);

        image.setRotation((float) Math.toDegrees(angle) + 90f);

        g.drawImage(image, position.x, position.y);
    }
    
	public void update(int delta){
		position.add(new Vector2f(velocity).scale(delta / 1000f));
	}
	
	@Override
	public Shape getPhysicsBox() {
		return null;
	}
	
	@Override public Vector2f getPosition(){return position;}
	
	@Override public void setPosition(Vector2f position){this.position = position;}
	
	@Override public Vector2f getVelocity(){return velocity;}
	
	@Override public void setVelocity(Vector2f velocity) {
        if(velocity.lengthSquared() > maximumVelocity * maximumVelocity)
        	velocity.normalise().scale(maximumVelocity);
        this.velocity = velocity;
    }
	
	@Override public float getMass(){return mass;}
	
	@Override public void setMass(float mass){this.mass = mass;}
	
	@Override public float getAngle(){return angle;}
	
	@Override public void setAngle(float newAngle){this.angle = newAngle;}
	
	
	
    

    
}
