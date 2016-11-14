package util;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public interface PhysicsObject {
	Shape getPhysicsBox();
	Vector2f getPosition();
	void setPosition(Vector2f position);
	Vector2f getVelocity();
	void setVelocity(Vector2f velocity);
	float getMass();
	void setMass(float mass);
	void update(int delta);

}
