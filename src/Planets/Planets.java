package Planets;

import PlanetGame.Pieter;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.geom.Circle;


import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Planets{
    private float x;
    private float y;
    private float radius;
    private int planet;
    private Image map;
    public static List<Image> planet_texture;

    public Planets(float x, float y, float radius, int planet, Image map){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.planet = planet;
        this.map = map;
    }

    public Graphics draw_planet(Graphics g, float coord_x, float coord_y, int planet_id) throws SlickException {
        float planet_x = (x-(radius/2))-coord_x+(Display.getWidth()/2);
        float planet_y = (-y-(radius/2))+coord_y+(Display.getHeight()/2);

        //distance between planet and rocket
        float delta_x = coord_x-(x-(radius/2));
        float delta_y = (coord_y-(y+(radius/2)));

        if ((planet_x+radius)>0 && (planet_x-radius)<Display.getWidth() && (planet_y+radius)>0 && (planet_y-radius)<Display.getHeight()){

            new OutlineEffect(50,java.awt.Color.WHITE);
            Circle circle = new Circle(planet_x, planet_y, radius/2);
            g.setColor(new Color(255,255,255,255));
            if (planet_id == 0){
                g.texture(circle, map, true);
                new Image("shadow3.png").draw(planet_x-(radius/2)-(radius/10), planet_y-(radius/2)-(radius/10), radius+2*(radius/10), radius+2*(radius/10));
            }
            if (planet_id == 1){
                Image atmosphere = new Image("glow.png");
                atmosphere.draw(planet_x-(radius/2)-(radius/10), planet_y-(radius/2)-(radius/10), radius+2*(radius/10), radius+2*(radius/10));
                g.texture(circle, map, true);
                new Image("shadow3.png").draw(planet_x-(radius/2)-(radius/10), planet_y-(radius/2)-(radius/10), radius+2*(radius/10), radius+2*(radius/10));
            }
        }

        return g;
    }

    public double GetX(){
        return x;
    }

    public double GetY(){
        return y;
    }

    public int GetPlanet(){
        return planet;
    }

    public double GetRadius(){
        return radius;
    }

    public static boolean inCircle(int x, int y, int radius, int max_x, int max_y){
        boolean condition = false;

        if((x+radius)<max_x && (y+radius)<max_y && (x-radius)>0 && (y-radius)>0){
            condition = true;
        }

        return condition;
    }


    public static BufferedImage GeneratePlanetTexture() {

        int image_size = 300;
        int planet_type = (int)(Math.random()*3);
        //System.out.println(planet_type);
        java.awt.Color fill_color = new java.awt.Color(255, 255, 255);
        java.awt.Color surface_color = new java.awt.Color(0, 0, 0);
        switch (planet_type){
            case 0: fill_color = new java.awt.Color(112, 129, 239);
                    surface_color = new java.awt.Color(135, 211, 124);
                break;
            case 1: fill_color = new java.awt.Color(218, 223, 225);
                    surface_color = new java.awt.Color(108, 122, 137);
                break;
            case 2: fill_color = new java.awt.Color(150, 40, 27);
                    surface_color = new java.awt.Color(211, 84, 0);
                break;
            case 3: fill_color = new java.awt.Color(0, 0, 0);
                surface_color = new java.awt.Color(0, 0, 0);
                break;
        }

        final BufferedImage res = new BufferedImage( image_size, image_size, BufferedImage.TYPE_INT_RGB );
        int tile_pixels = 10;
        for (int x = 0; x < image_size; x+=tile_pixels){
            for (int y = 0; y < image_size; y+=tile_pixels){


                for (int j = 0; j < tile_pixels; j++) {
                    for (int k = 0; k < tile_pixels; k++) {
                        res.setRGB(x+j, y+k, fill_color.getRGB());
                    }
                }
            }
        }

        //draw circle
        for (int repeat = 0; repeat < (int)(300 + (Math.random()*(1000-3)+1)); repeat++) {

            int cirle_radius = 3;

            int circle_x = (int)(cirle_radius + (Math.random()*((image_size-cirle_radius)-cirle_radius)+1));
            int circle_y = (int)(cirle_radius + (Math.random()*((image_size-cirle_radius)-cirle_radius)+1));
            for (int cluster = 0; cluster < (int)(Math.random()*0+1); cluster++) {

                int circle_cluster_x = (int)((Math.random()-0.5)*cirle_radius);
                int circle_cluster_y = (int)((Math.random()-0.5)*cirle_radius);


                if (inCircle(circle_x + circle_cluster_x, circle_y + circle_cluster_y, cirle_radius, image_size, image_size)){

                    circle_x+=circle_cluster_x;
                    circle_y+=circle_cluster_y;

                    double r, angle, x1, y1;
                    for (int j = 0; j < cirle_radius; j++) {
                        for(r = 0; r < 360; r += 0.1)
                        {
                            angle = r;
                            x1 = (cirle_radius-j) * Math.cos(angle * Math.PI / 180);
                            y1 = (cirle_radius-j) * Math.sin(angle * Math.PI / 180);
                            res.setRGB((int)(circle_x+x1),(int)(circle_y+y1), surface_color.getRGB());
                        }
                    }
                }
            }

        }
        return res;
    }


    public static List<Planets> GeneratePlanets(int seg_x, int seg_y, int count, int planet_id){
        List<Planets> planets = new ArrayList<Planets>();

        //not every segment has a planet
        int change = (int)(Math.random()*100);

        //10% of the segments have a planet
        //TODO: the change of finding a planet
        if (change < 10){
            for (int i = 0; i < count; i++) {
                float rand_radius = (float) (Pieter.MIN_PLANET_RADIUS + (Math.random()*(Pieter.MAX_PLANET_RADIUS-Pieter.MIN_PLANET_RADIUS)+1));
                float rand_x = (float) (rand_radius + Math.random()*((Display.getWidth()/2)-rand_radius-rand_radius+1)*Pieter.PLANET_SEGMENT_SCALE);       //number between 0 and display witdh
                float rand_y = (float) (rand_radius + Math.random()*((Display.getHeight()/2)-rand_radius-rand_radius+1)*Pieter.PLANET_SEGMENT_SCALE);      //number between 0 and display height
                float seg_orig_x =  seg_x*(Display.getWidth()/2) ;
                float seg_orig_y =  seg_y*(Display.getHeight()/2);
                float _x = seg_orig_x + rand_x;
                float _y = seg_orig_y + rand_y;

                if ((planet_texture.size()) > 0){
                    int random_texture = (int)(Math.random()*planet_texture.size());
                    Planets s = new Planets(_x, _y, rand_radius, planet_id, planet_texture.get(random_texture));
                    planets.add(s);
                }
            }
        }



        return planets;
    }

    //public static boolean SegmentFount(Vector<Data.Coords> discoverd_planets, int segment_x, int segment_y){
    //    boolean found = false;
    //    for (int i = 0; i < discoverd_planets.size(); i++) {
    //        if ((int)discoverd_planets.get(i).getX() == segment_x && (int)discoverd_planets.get(i).getY() == segment_y){
    //            found = true;
    //            break;
    //        }
    //    }
    //
    //    return found;
    //}

    //public static void addToSegment(int segment_x, int segment_y, int count){
    //        if (Pieter.discoverd_planets.size() > 0) {
    //            //center
    //            if (SegmentFount(Pieter.discoverd_planets, segment_x, segment_y) == false) {
    //                int id = (int)(Math.random()*planet_id_max);
    //                Pieter.planets.addAll(Planets.GeneratePlanets(segment_x, segment_y, count, id));
    //                Data.Coords coords = new Data.Coords(segment_x, segment_y);
    //                Pieter.discoverd_planets.addElement(coords);
    //            }
    //            //left
    //            else if (SegmentFount(Pieter.discoverd_planets, segment_x - 1, segment_y) == false) {
    //                int id = (int)(Math.random()*planet_id_max);
    //                Pieter.planets.addAll(Planets.GeneratePlanets(segment_x - 1, segment_y, count, id));
    //                Data.Coords coords = new Data.Coords(segment_x - 1, segment_y);
    //                Pieter.discoverd_planets.addElement(coords);
    //            }
    //            //left top
    //            else if (SegmentFount(Pieter.discoverd_planets, segment_x - 1, segment_y + 1) == false) {
    //                int id = (int)(Math.random()*planet_id_max);
    //                Pieter.planets.addAll(Planets.GeneratePlanets(segment_x - 1, segment_y + 1, count, id));
    //                Data.Coords coords = new Data.Coords(segment_x - 1, segment_y + 1);
    //                Pieter.discoverd_planets.addElement(coords);
    //            }
    //            //top
    //            else if (SegmentFount(Pieter.discoverd_planets, segment_x, segment_y + 1) == false) {
    //                int id = (int)(Math.random()*planet_id_max);
    //                Pieter.planets.addAll(Planets.GeneratePlanets(segment_x, segment_y + 1, count, id));
    //                Data.Coords coords = new Data.Coords(segment_x, segment_y + 1);
    //                Pieter.discoverd_planets.addElement(coords);
    //            }
    //            //right top
    //            else if (SegmentFount(Pieter.discoverd_planets, segment_x + 1, segment_y + 1) == false) {
    //                int id = (int)(Math.random()*planet_id_max);
    //                Pieter.planets.addAll(Planets.GeneratePlanets(segment_x + 1, segment_y + 1, count, id));
    //                Data.Coords coords = new Data.Coords(segment_x + 1, segment_y + 1);
    //                Pieter.discoverd_planets.addElement(coords);
    //            }
    //            //right
    //            else if (SegmentFount(Pieter.discoverd_planets, segment_x + 1, segment_y) == false) {
    //                int id = (int)(Math.random()*planet_id_max);
    //                Pieter.planets.addAll(Planets.GeneratePlanets(segment_x + 1, segment_y, count, id));
    //                Data.Coords coords = new Data.Coords(segment_x + 1, segment_y);
    //                Pieter.discoverd_planets.addElement(coords);
    //            }
    //            //right bottom
    //            else if (SegmentFount(Pieter.discoverd_planets, segment_x + 1, segment_y - 1) == false) {
    //                int id = (int)(Math.random()*planet_id_max);
    //                Pieter.planets.addAll(Planets.GeneratePlanets(segment_x + 1, segment_y - 1, count, id));
    //                Data.Coords coords = new Data.Coords(segment_x + 1, segment_y - 1);
    //                Pieter.discoverd_planets.addElement(coords);
    //            }
    //            //bottom
    //            else if (SegmentFount(Pieter.discoverd_planets, segment_x, segment_y - 1) == false) {
    //                int id = (int)(Math.random()*planet_id_max);
    //                Pieter.planets.addAll(Planets.GeneratePlanets(segment_x, segment_y - 1, count, id));
    //                Data.Coords coords = new Data.Coords(segment_x, segment_y - 1);
    //                Pieter.discoverd_planets.addElement(coords);
    //            }
    //            //left bottom
    //            else if (SegmentFount(Pieter.discoverd_planets, segment_x - 1, segment_y - 1) == false) {
    //                int id = (int)(Math.random()*planet_id_max);
    //                Pieter.planets.addAll(Planets.GeneratePlanets(segment_x - 1, segment_y - 1, count, id));
    //                Data.Coords coords = new Data.Coords(segment_x - 1, segment_y - 1);
    //                Pieter.discoverd_planets.addElement(coords);
    //            }
    //        } else {
    //            int id = (int)(Math.random()*planet_id_max);
    //            Pieter.planets.addAll(Planets.GeneratePlanets(0, 0, count, id));
    //            Data.Coords coords = new Data.Coords(0, 0);
    //            Pieter.discoverd_planets.addElement(coords);
    //        }
    //
    //
    //
    //}



}
