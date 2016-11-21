package PlanetGame;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;





import Particle.Trail;

public class Menu extends BasicGameState{

	ParticleSystem particleSystem;
	Trail trailParticle;
	public static float x = 300, y = 300;
	/*Polygon poly;
	Polygon poly2;
	Polygon poly3;*/



	@Override public int getID() {
		return Main.MENU;
	}

	@Override public void init(GameContainer container, StateBasedGame game) throws SlickException {

		/*poly = new Polygon();
		poly2 = new Polygon();
		poly3 = new Polygon();
		poly.setClosed(false);
		poly2.setClosed(false);
		poly.setClosed(false);
		for (float i = 0; i < 200; i+=0.1f) {

		}
		for (float i = 0; i < 72; i+=0.1f) {
			float R = 200;
			float r = 5;
			System.out.println(r);
			poly2.addPoint(500+(float)((R+r)*Math.cos(Math.toRadians(5*i))),500+(float)((R+r)*Math.sin(Math.toRadians(5*i))));
			R = 190;
			poly3.addPoint(500+(float)((R+r)*Math.cos(Math.toRadians(5*i))),500+(float)((R+r)*Math.sin(Math.toRadians(5*i))));
		}
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
		g.fill(poly3);*/


		trailParticle = new Trail();
		particleSystem = new ParticleSystem(trailParticle.getImage());
		particleSystem.addEmitter(new Trail());
	}

	@Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		particleSystem.render();

	}

	@Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(container.getInput().isKeyDown(Input.KEY_ESCAPE)) System.exit(0);
		if(container.getInput().isKeyDown(Input.KEY_R)) init(container, game);
		if(container.getInput().isKeyDown(Input.KEY_LEFT)) x -= 0.5f*delta;
		if(container.getInput().isKeyDown(Input.KEY_RIGHT)) x += 0.5f*delta;
		if(container.getInput().isKeyDown(Input.KEY_UP)) y -= 0.5f*delta;
		if(container.getInput().isKeyDown(Input.KEY_DOWN)) y += 0.5f*delta;
		particleSystem.update(delta);
	}
}
