package PlanetGame;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;
import util.Drawable;
import util.PhysicsObject;


class Rocket implements PhysicsObject, Drawable{
	float mass = 1;
    Vector2f position;
    Vector2f velocity;
    //Image image;
    public static float angle;
    Shape rocketShape;
    
    Image image;
    float maximumVelocity = 100;

    Rocket(float x, float y) throws SlickException {
        position = new Vector2f(x, y);
        velocity = new Vector2f(0, 0);

        image = new Image("res/rocket.png");
        
        rocketShape = new Polygon(new float[]{0, 1, -0.4f, 0, 0.4f, 0});
        rocketShape = rocketShape.transform(Transform.createTranslateTransform(-rocketShape.getCenterX(), -rocketShape.getCenterY()));
        rocketShape = rocketShape.transform(Transform.createRotateTransform((float) Math.PI));
        
        angle = (float) Math.PI;

    }

    @Override public void draw(Graphics g) {
        // Graphical vector representation
        g.drawGradientLine(450, 450, Color.red, 450 + velocity.x * 30, 450 + velocity.y * 30, Color.green);
        g.drawGradientLine(450, 450, Color.red, 450 + (float) Math.cos(angle) * 50, 450 + (float) Math.sin(angle) * 50, Color.yellow);
        g.drawArc(435, 435, 30, 30, Math.min((float)Math.toDegrees(angle), (float)velocity.getTheta()), Math.max((float)Math.toDegrees(angle), (float)velocity.getTheta()));
        g.drawString(velocity.toString(), 5, 30);
        g.drawString("" + angle, 5, 50);

        // Rocket image rotation + draw
       // image.setRotation((float) Math.toDegrees(angle) + 90f);

        //g.draw(rect);
        
        //image.draw(0, 0);
        
        g.draw(rocketShape);
    }
    
	public void update(int delta){
		position.add(velocity.scale(delta / 1000f));
		
		
		
	}
	
	@Override
	public Shape getPhysicsBox() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override public Vector2f getPosition(){return position;}
	
	@Override public void setPosition(Vector2f position){this.position = position;}
	
	@Override public Vector2f getVelocity(){return velocity;}
	
	@Override public void setVelocity(Vector2f velocity) {
        if(velocity.lengthSquared() > maximumVelocity) velocity.normalise().scale(maximumVelocity);
        this.velocity = velocity;
    }
	
	@Override public float getMass(){return mass;}
	
	@Override public void setMass(float mass){this.mass = mass;}
	
	@Override public float getAngle(){return angle;}
	
	@Override public void setAngle(float newAngle){this.angle = newAngle;}
	
	
	
    

    
}
