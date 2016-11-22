package PlanetGame;

import Particle.Emitter;
import org.newdawn.slick.*;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import Particle.Trail;

public class Menu extends BasicGameState{

	ParticleSystem particleSystem;
	Trail trailParticle;
	public static float x = 300, y = 300, angle = 0;

	@Override public int getID() {
		return Main.MENU;
	}

	@Override public void init(GameContainer container, StateBasedGame game) throws SlickException {
		trailParticle = new Trail();
		particleSystem = new ParticleSystem(trailParticle.getImage(), 10000);
		particleSystem.addEmitter(new Trail());
		particleSystem.addEmitter(new Emitter(200, 200));
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
		if(container.getInput().isKeyDown(Input.KEY_O)) angle += 0.1f*delta;
		particleSystem.update(delta);
	}
}
