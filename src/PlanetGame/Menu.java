package PlanetGame;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import Particle.Trail;

public class Menu extends BasicGameState{

	ParticleSystem s;
	public static float x = 300, y = 300;

	@Override public int getID() {
		return Main.MENU;
	}

	@Override public void init(GameContainer container, StateBasedGame game) throws SlickException {
		Image i = new Image(40, 40);
        Graphics g = i.getGraphics();
        g.setColor(Color.white);
        g.fill(new Circle(20, 20, 20));
        g.flush();

		s = new ParticleSystem(i);
        s.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
		s.setPosition(200, 200);
		s.setVisible(true);
		//s.addEmitter(new Emitter());
		s.addEmitter(new Trail());
	}

	@Override public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		s.render();
		g.draw(new Circle(x, y, 20));
	}

	@Override public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(container.getInput().isKeyDown(Input.KEY_ESCAPE)) System.exit(0);
		if(container.getInput().isKeyDown(Input.KEY_R)) init(container, game);
		if(container.getInput().isKeyDown(Input.KEY_LEFT)) x -= 0.5f*delta;
		if(container.getInput().isKeyDown(Input.KEY_RIGHT)) x += 0.5f*delta;
		if(container.getInput().isKeyDown(Input.KEY_UP)) y -= 0.5f*delta;
		if(container.getInput().isKeyDown(Input.KEY_DOWN)) y += 0.5f*delta;
		s.update(delta);
	}
}
