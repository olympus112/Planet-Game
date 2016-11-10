package util;

import org.newdawn.slick.geom.Vector2f;

import java.awt.*;
import java.util.Random;

public class Util {

    public static Vector2f fromPolar(float r, double angle) {
        return new Vector2f(r * (float) Math.cos(angle), r * (float) Math.sin(angle));
    }

}
