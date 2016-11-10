package util;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public interface PhysicsObject {
	public Shape getPhysicsBox();
	public Vector2f getPosition();
	public void setPosition(Vector2f posistion);
	public Vector2f getVelocity();
	public void setVelocity(Vector2f velocity);
	public float getMass();
	public void setMass(float mass);
	public void update(int delta);
}
