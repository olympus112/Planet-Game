package PlanetGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Circle;

import util.Util;

import java.awt.*;
import java.awt.geom.Arc2D;

class PlanetOld {

    private float x;
    private float y;
    private float size;
    private float mass;
    
    
    private float moonAngle;
    private float moonSize;
    private float moonVelocity;
    private boolean moon;
    private int luminance;
    private int planetType;
    private int layerWidth = 1;

    String[][] planetTypes = {
            {"#002600", "#003300", "#004000", "#004c00", "#005900", "#006600", "#007300"}, //green
            {"#4c0000", "#660000", "#7f0000", "#990000", "#b20000", "#cc0000"}, //red
            {"#001919", "#003333", "#004c4c", "#006666", "#007f7f", "#009999", "#00b2b2"}, //aqua
            {"#00004c", "#000066", "#00007f", "#000099", "#0000b2", "#0000cc"}, //blue
            {"#4c3100", "#664200", "#7f5200", "#996300", "#b27300", "#cc8400"}, //orange
            {"#323232", "#4c4c4c", "#666666", "#7f7f7f", "#999999", "#b2b2b2", "#cccccc"}, //white
            {"#330033", "#400040", "#4c004c", "#590059", "#660066", "#730073", "#800080"}, //purple
            {"#4c4c00", "#666600", "#7f7f00", "#999900", "#b2b200", "#cccc00"}, //yellow
            {"#421010", "#521515", "#631919", "#731d1d", "#842121", "#942525", "#a52a2a"} //brown
    };

    PlanetOld(float x, float y, float size, float mass, int PlanetType, int luminance){
            this.x = x;
            this.y = y;
            this.size = size;
            this.mass = mass;
            this.planetType = PlanetType;
            this.luminance = luminance;
            this.moon = false;
    }

    public PlanetOld(float x, float y, float size, float mass, int PlanetType, int luminance, float moonAngle, float moonSize, float moonVelocity){
        this.x = x;
        this.y = y;
        this.size = size;
        this.mass = mass;
        this.planetType = PlanetType;
        this.luminance = luminance;
        this.moon = true;
        this.moonAngle = moonAngle;
        this.moonSize = moonSize;
        this.moonVelocity = moonVelocity;
    }

    public void draw(Graphics g){

    }

    static void generate(String... params) {
        int xMin = -10000;
        int xMax = 10000;
        int xRand = 500;
        int dX = 800;
        int yMin = -10000;
        int yMax = 10000;
        int yRand = 50;
        int dY = 800;
        int sizeMin = 150;
        int sizeRand = 100;
        int moonSize = 25;
        float moonChance = 0.4f;
        float planetChance = 0.5f;
        float mass = 0.05f;

        for (String param : params) {
            String key = param.split(":")[0];
            String value = param.split(":")[1];

            xMin = (key.equalsIgnoreCase("xMin")) ? Integer.parseInt(value) : xMin;
            xMax = (key.equalsIgnoreCase("xMax")) ? Integer.parseInt(value) : xMax;
            xRand = (key.equalsIgnoreCase("xRand")) ? Integer.parseInt(value) : xRand;
            dX = (key.equalsIgnoreCase("dX")) ? Integer.parseInt(value) : dX;
            yMin = (key.equalsIgnoreCase("yMin")) ? Integer.parseInt(value) : yMin;
            yMax = (key.equalsIgnoreCase("yMax")) ? Integer.parseInt(value) : yMax;
            yRand = (key.equalsIgnoreCase("yRand")) ? Integer.parseInt(value) : yRand;
            dY = (key.equalsIgnoreCase("dY")) ? Integer.parseInt(value) : dY;
            sizeMin = (key.equalsIgnoreCase("sizeMin")) ? Integer.parseInt(value) : sizeMin;
            sizeRand = (key.equalsIgnoreCase("sizeRand")) ? Integer.parseInt(value) : sizeRand;
            moonSize = (key.equalsIgnoreCase("moonSize")) ? Integer.parseInt(value) : moonSize;
            moonChance = (key.equalsIgnoreCase("moonChance")) ? Float.parseFloat(value) : moonChance;
            planetChance = (key.equalsIgnoreCase("planetChance")) ? Float.parseFloat(value) : planetChance;
            mass = (key.equalsIgnoreCase("mass")) ? Float.parseFloat(value) : mass;
        }

        for(int x = xMin; x < xMax; x += dX){
            for(int y = yMin; y < yMax; y += dY){
                if (Util.random.nextDouble() > planetChance)
                    if(Util.random.nextDouble() > moonChance)
                        Screen.addPlanet(
                                new PlanetOld(
                                        x + Util.random.nextInt(xRand), // x
                                        y + Util.random.nextInt(yRand), // y
                                        sizeMin + Util.random.nextInt(sizeRand), // size
                                        mass, // mass
                                        Util.random.nextInt(9), // planetType
                                        -1 + Util.random.nextInt(3))); // luminance
                    else
                        Screen.addPlanet(
                                new PlanetOld(
                                        x + Util.random.nextInt(xRand), // x
                                        y + Util.random.nextInt(yRand), // y
                                        sizeMin + Util.random.nextInt(sizeRand), // size
                                        mass, // mass
                                        Util.random.nextInt(9), // planetType
                                        -1 + Util.random.nextInt(3), // luminance
                                        (float)Math.toRadians(Util.random.nextInt(360)), // moonAngle
                                        moonSize, //moonSize
                                        (float)(1 + Util.random.nextInt(4)) * 0.0007f)); //moonVelocity
            }
        }
    }
}
