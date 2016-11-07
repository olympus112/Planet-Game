package com.company;

import org.newdawn.slick.Input;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rocket {

    Vector position;
    Vector velocity;
    double angle = Math.PI/2;
    double anglev = 0;
    double damage;
    double a;
    static BufferedImage rocketImage;

    Rocket(double x, double y) {
        position = new Vector(x, y);
        velocity = new Vector(0, 0);
        //rocketImage = resize(ImageIO.read(new File("Rocket.png")), 35, 35);
    }

    public static BufferedImage resize(Image img, int width, int height)
    {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        bGr.dispose();

        return bufferedImage;
    }

    public void draw(Graphics g) {
        g.drawString(String.valueOf(position.getX()), 10, 20);
        g.drawString(String.valueOf(position.getY()), 10, 35);

        if(Screen.keys[Input.KEY_Q])
            angle -= 0.02;
        if(Screen.keys[Input.KEY_D])
            angle += 0.02;
        if(Screen.keys[Input.KEY_Z])
            velocity = velocity.addVector(Vector.fromPolar(0.005, angle));

        position = position.addVector(velocity);

        g.drawLine(450, 450, (int) (450 + velocity.getX() * 10), (int) (450 + velocity.getY() * 10));

        AffineTransform tx = AffineTransform.getRotateInstance(angle + Math.PI/2, rocketImage.getWidth() / 2, rocketImage.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(
                op.filter(rocketImage, null),
                (int) Main.screen.getWidth()/2 - rocketImage.getWidth(),
                (int) Main.screen.getHeight()/2 - rocketImage.getHeight(),
                null
        );

    }
}
