package util;

import org.newdawn.slick.geom.Vector2f;

import java.awt.*;
import java.util.Random;

public class Util {

    public static Random random;
    public static Dimension screen;
    public static float screenDiagonal;
    public static float PI;
    public static float PI_2;

    public Util(){
        random = new Random();
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        screenDiagonal = (float) Math.sqrt(screen.width * screen.width + screen.height * screen.height);
        PI = (float) Math.PI;
        PI_2 = 2 * PI;
    }

    static Vector2f fromPolar(float r, double angle) {
        return new Vector2f(r * (float) Math.cos(angle), r * (float) Math.sin(angle));
    }

}
