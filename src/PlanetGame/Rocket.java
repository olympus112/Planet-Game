package PlanetGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

class Rocket {

    Vector2f position;
    Vector2f velocity;
    float angle;

    Rocket(float x, float y) {
        position = new Vector2f(x, y);
        velocity = new Vector2f(0, 0);
    }

    public void draw(Graphics g) {
        g.drawGradientLine(450, 450, Color.red, 450 + velocity.x * 10, 450 + velocity.y * 10, Color.green);
        g.drawGradientLine(450, 450, Color.red, 450 + (float) Math.cos(angle) * 50, 450 + (float) Math.sin(angle) * 50, Color.yellow);
        g.drawArc(435, 435, 30, 30,
                Math.min((float)Math.toDegrees(angle), (float)velocity.getTheta()),
                Math.max((float)Math.toDegrees(angle), (float)velocity.getTheta()));
        g.drawString(velocity.toString(), 5, 30);
        g.drawString("" + angle, 5, 50);
    }


}
