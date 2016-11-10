package PlanetGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

class Rocket {

    Vector2f position;
    Vector2f velocity;
    Image image;
    float angle;

    Rocket(float x, float y) throws SlickException {
        position = new Vector2f(x, y);
        velocity = new Vector2f(0, 0);
        image = new Image("res/rocket.png");
    }

    public void draw(Graphics g) {

        // Graphical vector representation
        g.drawGradientLine(450, 450, Color.red, 450 + velocity.x * 10, 450 + velocity.y * 10, Color.green);
        g.drawGradientLine(450, 450, Color.red, 450 + (float) Math.cos(angle) * 50, 450 + (float) Math.sin(angle) * 50, Color.yellow);
        g.drawArc(435, 435, 30, 30,
                Math.min((float)Math.toDegrees(angle), (float)velocity.getTheta()),
                Math.max((float)Math.toDegrees(angle), (float)velocity.getTheta()));
        g.drawString(velocity.toString(), 5, 30);
        g.drawString("" + angle, 5, 50);

        // Rocket image rotation + draw
        image.setRotation((float) Math.toDegrees(angle) + 90f);
        image.draw(Util.screen.width / 2, Util.screen.height / 2);

        // Rocket position updates
        position.add(new Vector2f(velocity.x / 1f, velocity.y / 1f));
    }


}
