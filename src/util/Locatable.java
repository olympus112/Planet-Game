package util;

import org.newdawn.slick.geom.Vector2f;

public interface Locatable {
	public float getAngle();
	public void setAngle(float newAngle);
	public Vector2f getPosition();
	public void setPosition(Vector2f newPos);
}
