package PlanetGame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.effects.FireEmitter;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import util.PerlinNoiseGenerator;

class Menu extends BasicGameState{

    Polygon poly;
    Polygon poly2;
    Polygon poly3;
    PerlinNoiseGenerator p;
    FireEmitter f;
    ParticleSystem s;
    private int mode = ParticleSystem.BLEND_COMBINE;

    @Override public int getID() {
        return Main.MENU;
    }

    @Override public void init(GameContainer container, StateBasedGame game) throws SlickException {
        p = new PerlinNoiseGenerator();
        poly = new Polygon();
        poly2 = new Polygon();
        poly3 = new Polygon();
        poly.setClosed(false);
        poly2.setClosed(false);
        poly.setClosed(false);
        for (float i = 0; i < 200; i+=0.1f) {
            poly.addPoint(20*i, p.noise1(i)*50+400);
        }
        for (float i = 0; i < 72; i+=0.1f) {
            float R = 200;
            float r = p.noise1(i)*35;
            System.out.println(r);
            poly2.addPoint(500+(float)((R+r)*Math.cos(Math.toRadians(5*i))),500+(float)((R+r)*Math.sin(Math.toRadians(5*i))));
            R = 190;
            poly3.addPoint(500+(float)((R+r)*Math.cos(Math.toRadians(5*i))),500+(float)((R+r)*Math.sin(Math.toRadians(5*i))));
        }
        Image image = new Image("res/particle.tga", true);
        s = new ParticleSystem(image);
        f = new FireEmitter(200, 200, 50);
        f.setEnabled(true);
        s.addEmitter(f);
        s.setVisible(true);
    }

    @Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        //press R to change poly's
        g.setColor(Color.white);
        g.draw(poly);
        g.setColor(new Color(49, 76, 167));
        g.fill(new Circle(500, 500, 200));
        g.setColor(Color.cyan);
        g.draw(new Circle(500, 500, 200));
        g.setColor(new Color(28, 68, 32));
        g.fill(poly2);
        g.setColor(Color.darkGray);
        g.fill(poly3);
        //for (int i=0;i<100;i++) {
            s.render();

        //}
        g.drawString("Press space to toggle blending mode", 200, 500);
        g.drawString("Particle Count: "+(s.getParticleCount()*100), 200, 520);
    }

    @Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if(container.getInput().isKeyDown(Input.KEY_ESCAPE)) System.exit(0);
        if(container.getInput().isKeyDown(Input.KEY_R)) init(container, game);
        if(container.getInput().isKeyDown(Input.KEY_SPACE)) {
            mode = ParticleSystem.BLEND_ADDITIVE == mode ? ParticleSystem.BLEND_COMBINE : ParticleSystem.BLEND_ADDITIVE;
            s.setBlendingMode(mode);
        }
        s.update(delta);
    }
}
