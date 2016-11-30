package util;

import org.newdawn.slick.geom.Vector2f;

public interface Locatable {
	float getAngle();
	void setAngle(float newAngle);
	Vector2f getPosition();
	void setPosition(Vector2f newPos);
}
