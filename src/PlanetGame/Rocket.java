package PlanetGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.effects.FireEmitter;
import util.Drawable;
import util.PhysicsObject;

class Rocket implements PhysicsObject, Drawable{

    Vector2f position;
    Vector2f velocity;
    Image image;
    float maximumVelocity = 1;
    float angle;
    float mass;

    Rocket(float x, float y) throws SlickException {
        position = new Vector2f(x, y);
        velocity = new Vector2f(0, 0);
        image = new Image("res/rocket.png");
    }

    @Override public void draw(Graphics g) {
        // Graphical vector representation
        g.drawGradientLine(450, 450, Color.red, 450 + velocity.x * 30, 450 + velocity.y * 30, Color.green);
        g.drawGradientLine(450, 450, Color.red, 450 + (float) Math.cos(angle) * 50, 450 + (float) Math.sin(angle) * 50, Color.yellow);
        g.drawArc(435, 435, 30, 30, Math.min((float)Math.toDegrees(angle), (float)velocity.getTheta()), Math.max((float)Math.toDegrees(angle), (float)velocity.getTheta()));
        g.drawString(velocity.toString(), 5, 30);
        g.drawString("" + angle, 5, 50);
        g.drawString("" + velocity.lengthSquared(), 5, 70);

        // Draw the rocket at (x, y)
        g.drawImage(image, Screen.getCamera().realToPixelCoords(position).x, Screen.getCamera().realToPixelCoords(position).y);
        g.draw(getPhysicsBox());
    }

    @Override public void update(int delta) {
        // Rocket image rotation + draw
        image.setRotation((float) Math.toDegrees(angle) + 90f);
        // Rocket position updates
        position.add(new Vector2f(velocity.x / 1f, velocity.y / 1f));
    }

    @Override public Shape getPhysicsBox() {
        return new Rectangle(position.x, position.y, image.getWidth(), image.getHeight());
    }

    @Override public Vector2f getPosition() {
        return position;
    }

    @Override public void setPosition(Vector2f position) {
        this.position = position;
    }

    @Override public Vector2f getVelocity() {
        return velocity;
    }

    @Override public void setVelocity(Vector2f velocity) {
        if(velocity.lengthSquared() > maximumVelocity) velocity.normalise().scale(maximumVelocity);
        this.velocity = velocity;
    }

    @Override public float getMass() {
        return mass;
    }

    @Override public void setMass(float mass) {
        this.mass = mass;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
