package PlanetGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;

class Planet {

    double x;
    double y;
    double size;
    double mass;
    double attractionRadius;
    double moonAngle;
    double moonSize;
    double moonVelocity;
    boolean moon;
    int luminance;
    int planetType;
    int layerWidth = 1;

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

    Planet(double x, double y, double size, double mass, int PlanetType, int luminance){
            this.x = x;
            this.y = y;
            this.size = size;
            this.mass = mass;
            this.planetType = PlanetType;
            this.luminance = luminance;
            this.moon = false;
    }

    public Planet(double x, double y, double size, double mass, int PlanetType, int luminance, double moonAngle, double moonSize, double moonVelocity){
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
        moonAngle += moonVelocity;

        Color tempColor;
        double layer = size;
        double planetX = Math.round(x + Main.screen.getHeight()/2 - Screen.rocket.position.getX());
        double planetY = Math.round(y + Main.screen.getHeight()/2 - Screen.rocket.position.getY());
        double rocketX = Main.screen.getWidth()/2 - Rocket.rocketImage.getWidth()/2;
        double rocketY = Main.screen.getHeight()/2 - Rocket.rocketImage.getHeight()/2;

        // choose planet type
        for(int i = 0; i < planetTypes[planetType].length; i++) {
            switch (luminance) {
                case -1:
                    tempColor = Color.decode(planetTypes[planetType][i]).darker();
                    break;
                case 1:
                    tempColor = Color.decode(planetTypes[planetType][i]).brighter();
                    break;
                default:
                    tempColor = Color.decode(planetTypes[planetType][i]);
                    break;
            }
            g.setColor(tempColor);

            // draw layers
            g.fillOval(
                    (int)(planetX - layer/2),
                    (int)(planetY - layer/2),
                    (int)Math.round(layer),
                    (int)Math.round(layer)
            );

            // draw moon ring if there is a moon
            if(moon) {
                g.setColor(Color.decode(planetTypes[planetType][0]).darker());
                g.fillOval(
                        (int)(planetX + Math.cos(moonAngle)*size/1.34 - moonSize/2),
                        (int)(planetY + Math.sin(moonAngle)*size/1.34 - moonSize/2),
                        (int)moonSize,
                        (int)moonSize);
                g.setColor(Color.decode(planetTypes[planetType][planetTypes[planetType].length-1]));
                g.drawOval(
                        (int)(planetX + Math.cos(moonAngle)*size/1.34 - moonSize/2),
                        (int)(planetY + Math.sin(moonAngle)*size/1.34 - moonSize/2),
                        (int)moonSize,
                        (int)moonSize);
            }

            // reduce layer size
            layer -= size / (layerWidth * planetTypes[planetType].length);

            // draw line if attracted
            if (Point.distance(x, y, Screen.rocket.position.getX(), Screen.rocket.position.getY()) < 1.5 * size) {
                double xd = rocketX - planetX;
                double yd = rocketY - planetY;
                double distance = Math.max(Math.sqrt(xd*xd + yd*yd), 5);
                double F = mass / distance;

                Screen.rocket.velocity = Screen.rocket.velocity.addVector(new Vector(-F*(xd/distance)*0.999999, -F*(yd/distance)*0.999999));

                g.drawString(String.valueOf(Point.distance(x, y, Screen.rocket.position.getX(), Screen.rocket.position.getY())), 10, 50);
                g.drawOval(
                        (int)(planetX - size/2),
                        (int)(planetY - size/2),
                        (int)size,
                        (int)size);
            }
        }
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
        double moonChance = 0.4;
        double planetChance = 0.5;
        double mass = 0.05;

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
            moonChance = (key.equalsIgnoreCase("moonChance")) ? Double.parseDouble(value) : moonChance;
            planetChance = (key.equalsIgnoreCase("planetChance")) ? Double.parseDouble(value) : planetChance;
            mass = (key.equalsIgnoreCase("mass")) ? Double.parseDouble(value) : mass;
        }

        for(int x = xMin; x < xMax; x += dX){
            for(int y = yMin; y < yMax; y += dY){
                if (Util.random.nextDouble() > planetChance)
                    if(Util.random.nextDouble() > moonChance)
                        Screen.addPlanet(
                                new Planet(
                                        x + Util.random.nextInt(xRand), // x
                                        y + Util.random.nextInt(yRand), // y
                                        sizeMin + Util.random.nextInt(sizeRand), // size
                                        mass, // mass
                                        Util.random.nextInt(9), // planetType
                                        -1 + Util.random.nextInt(3))); // luminance
                    else
                        Screen.addPlanet(
                                new Planet(
                                        x + Util.random.nextInt(xRand), // x
                                        y + Util.random.nextInt(yRand), // y
                                        sizeMin + Util.random.nextInt(sizeRand), // size
                                        mass, // mass
                                        Util.random.nextInt(9), // planetType
                                        -1 + Util.random.nextInt(3), // luminance
                                        Math.toRadians(Util.random.nextInt(360)), // moonAngle
                                        moonSize, //moonSize
                                        (1 + Util.random.nextInt(4)) * 0.0007)); //moonVelocity
            }
        }
    }
}
