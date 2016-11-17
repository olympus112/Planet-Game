package planet;

import PlanetGame.Screen;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.imageout.ImageIOWriter;
import org.newdawn.slick.opengl.Texture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestPlanet implements Planet{
    private Color water = new Color(74, 191, 255);
    private Image image;
    private Graphics imageGraphics;
    private Vector2f position;
    private Vector2f velocity;
    private Circle physicsBox;
    private float mass;

    public TestPlanet(Vector2f position, float mass, float width){
        this.position = position;
        this.velocity = new Vector2f();
        this.mass = mass;
        physicsBox = new Circle(position.x, position.y , width / 2);
        try {
            image = new Image((int)width, (int)width);

            // Edit image
            editImage("edit", image);
            imageGraphics.setColor(Color.cyan);
            imageGraphics.fill(new Circle(100, 100, 100));

            editImage("dispose", image);
        } catch (SlickException | IOException e) {e.printStackTrace();}
    }

    private void editImage(String action, Image image) throws IOException {
        switch (action) {
            case "save":
                ImageIOWriter writer = new ImageIOWriter();
                writer.saveImage(image, "png", new FileOutputStream(new File("res/test.png")), false);
                break;
            case "edit":
                try {
                    imageGraphics = image.getGraphics();
                } catch (SlickException e) {e.printStackTrace();}
                break;
            case "dispose":
                imageGraphics.flush();
                break;
        }

    }

    @Override public void draw(Graphics g) {
        g.drawImage(image, 300, 300);

    }
    @Override public Shape getPhysicsBox(){
        return physicsBox;
    }
    @Override public void update(int delta){

    }
    @Override public boolean collide(Shape other) {
        return getPhysicsBox().intersects(other);
    }
    @Override public Vector2f getPosition(){return position;}
    @Override public void setPosition(Vector2f pos){this.position = pos;}
    @Override public Vector2f getVelocity(){return velocity;}
    @Override public void setVelocity(Vector2f v){this.velocity = v;}
    @Override public float getMass(){return mass;}
    @Override public void setMass(float mass){this.mass = mass;}


}
