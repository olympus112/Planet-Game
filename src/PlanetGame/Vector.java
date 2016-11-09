package PlanetGame;

import org.newdawn.slick.geom.Vector2f;

class Vector extends Vector2f{

    Vector(float x, float y){
        super(x, y);
    }

    static Vector fromPolar(float r, double angle) {
        return new Vector(r * (float) Math.cos(angle), r * (float) Math.sin(angle));
    }
}