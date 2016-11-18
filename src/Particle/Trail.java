package Particle;

import PlanetGame.Menu;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;

public class Trail implements ParticleEmitter{

    private int x = 400;
    private int y = 400;
    private int interval = 20;
    private float startLife = 5000f;
    private int timer;
    private float size = 40.0F;

    public Trail() {

    }

    public void update(ParticleSystem system, int delta) {
        this.timer -= delta;
        if(this.timer <= 0) {
            this.timer = this.interval;
            Particle newParticle = system.getNewParticle(this, startLife);
            newParticle.setColor(1.0F, 1.0F, 1.0F, 0.5F);
            newParticle.setPosition(Menu.x, Menu.y);
            newParticle.setSize(size);
        }

    }

    public void updateParticle(Particle particle, int delta) {
        if (particle.getSize() == 0.0f || particle.getColor().getAlpha() == 0.0f) particle.kill();
        particle.adjustSize(-0.0005f * delta * size);
        particle.adjustColor(0.0f, 0.0f, 0.0f, -0.0001f * delta);
    }

    public boolean isEnabled() {
        return true;
    }

    public void setEnabled(boolean enabled) {
    }

    public boolean completed() {
        return false;
    }

    public boolean useAdditive() {
        return false;
    }

    public Image getImage() {
        return null;
    }

    public boolean usePoints(ParticleSystem system) {
        return false;
    }

    public boolean isOriented() {
        return false;
    }

    public void wrapUp() {
    }

    public void resetState() {
    }
}
