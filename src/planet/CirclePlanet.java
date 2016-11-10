package planet;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import planet.Planet;

public class CirclePlanet implements Planet{
	private Vector2f position, velocity;
	private Circle physicsBox;
	private float mass;
	
	public CirclePlanet(Vector2f position, float mass, float width){
		this.position = position;
		this.velocity = new Vector2f();
		this.mass = mass;
		physicsBox = new Circle(0, 0, width/2);
		
	}
	
	@Override public Shape getPhysicsBox(){
		return physicsBox;
	}

	
	
	@Override public void draw(Graphics g){
		g.draw(physicsBox);
	}
	@Override public void update(int delta){}
	
	
	
	
	@Override public Vector2f getPosition(){return position;}
	@Override public void setPosition(Vector2f pos){this.position = pos;}
	
	@Override public Vector2f getVelocity(){return velocity;}
	@Override public void setVelocity(Vector2f v){this.velocity = v;}
	
	@Override public float getMass(){return mass;}
	@Override public void setMass(float mass){this.mass = mass;}
}
