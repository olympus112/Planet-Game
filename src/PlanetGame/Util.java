package PlanetGame;


import org.newdawn.slick.geom.Vector2f;

import java.util.Random;

public class Util {

    public static Random random;
    public static float PI;
    public static float PI_2;

    Util(){
        random = new Random();
        PI = (float) Math.PI;
        PI_2 = 2 * PI;
    }

    static Vector2f fromPolar(float r, double angle) {
        return new Vector2f(r * (float) Math.cos(angle), r * (float) Math.sin(angle));
    }

}
