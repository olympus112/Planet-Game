package Particle;

import PlanetGame.Menu;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;

public class Emitter implements ParticleEmitter{

    private int x;
    private int y;
    private int interval = 50;
    private float startLife = 1000f;
    private int timer;
    private float size = 40.0F;
    Image i;

    public Emitter() {
        try {
            i = new Image("res/rocket.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public Emitter(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Emitter(int x, int y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void update(ParticleSystem system, int delta) {
        this.timer -= delta;
        if(this.timer <= 0) {
            this.timer = this.interval;
            Particle newParticle = system.getNewParticle(this, startLife);
            newParticle.setColor(1.0F, 1.0F, 1.0F, 0.5F);
            newParticle.setPosition((float)this.x, (float)this.y);
            newParticle.setSize(this.size);
            float vx = (float)(-0.02 + Math.random() * 0.04);
            float vy = (float)(-Math.random() * 0.15);
            newParticle.setVelocity(vx, vy, 1.1f);;
        }

    }

    public void updateParticle(Particle particle, int delta) {
        if(particle.getLife() > 600.0F) {
            particle.adjustSize(0.07F * (float)delta);
        } else {
            particle.adjustSize(-0.04F * (float)delta * (this.size / 40.0F));
        }

        float c = 0.002f * (float)delta;
        particle.adjustColor(0.0f, -c / 2.0f, -c * 2.0f, -c / 4.0f);
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
