package Stars;

import PlanetGame.Data;
import PlanetGame.Screen;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Screen on 22/11/2016.
 */
public class Stars {
    private double x;
    private double y;
    private double radius;
    private Color color;
    public static Vector<Data.Coords> discoverd_segments;
    public static List<Stars> stars = new ArrayList<>();


    public Stars(double x, double y, double radius, Color color){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public Graphics draw_stars(Graphics g, double coord_x, double coord_y){
        int Min = 100;
        int Max = 255;
        double star_x = x-(coord_x/Screen.BACKGROUND_SPEED)+(Display.getWidth()/2);
        double star_y = (-y)+(coord_y/Screen.BACKGROUND_SPEED);
        if ((star_x+radius)>0 && (star_x)<Display.getWidth() && (star_y+radius)>0 && (star_y)<Display.getHeight()){
            double alpha = Min + (int)(Math.random() * ((Max - Min) + 1));
            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)alpha));
            g.fillOval((float)star_x, (float)star_y, (float)radius, (float)radius);
        }
        return g;
    }

    public double GetX(){
        return x;
    }

    public double GetY(){
        return y;
    }

    public double GetRadius(){
        return radius;
    }

    public static boolean SegmentFount(Vector<Data.Coords> discoverd_segments, int segment_x, int segment_y){
        boolean found = false;
        for (int i = 0; i < discoverd_segments.size(); i++) {
            if ((int)discoverd_segments.get(i).getX() == segment_x && (int)discoverd_segments.get(i).getY() == segment_y){
                found = true;
                break;
            }
        }

        return found;
    }

    public static List<Stars> GenerateStars(int seg_x, int seg_y, int count){
        List<Stars> stars = new ArrayList<Stars>();

        for (int i = 0; i < count; i++) {
            double rand_x = Math.random()*(Display.getWidth()/2);       //number between 0 and display witdh
            double rand_y = Math.random()*(Display.getHeight()/2);      //number between 0 and display height
            double seg_orig_x = seg_x*(Display.getWidth()/2) ;
            double seg_orig_y = seg_y*(Display.getHeight()/2);
            double _x = seg_orig_x + rand_x;
            double _y = seg_orig_y - rand_y;
            double rand_radius = Math.random() * Screen.MAX_STAR_RADIUS;
            double rand_color = 155 + Math.random() * 100;
            Stars s = new Stars(_x, _y, rand_radius, new Color(255,255,(int)rand_color));
            stars.add(s);
        }

        return stars;
    }

    public static void AddToSegment(int segment_x, int segment_y, int count){
        if (discoverd_segments.size() > 0){
            //center
            if (SegmentFount(discoverd_segments, segment_x, segment_y) == false){
                stars.addAll(Stars.GenerateStars(segment_x, segment_y, count));
                Data.Coords coords = new Data.Coords(segment_x, segment_y);
                discoverd_segments.addElement(coords);
            }
            //left
            if (SegmentFount(discoverd_segments, segment_x-1, segment_y) == false){
                stars.addAll(Stars.GenerateStars(segment_x-1, segment_y, count));
                Data.Coords coords = new Data.Coords(segment_x-1, segment_y);
                discoverd_segments.addElement(coords);
            }
            //left top
            if (SegmentFount(discoverd_segments, segment_x-1, segment_y+1) == false){
                stars.addAll(Stars.GenerateStars(segment_x-1, segment_y+1, count));
                Data.Coords coords = new Data.Coords(segment_x-1, segment_y+1);
                discoverd_segments.addElement(coords);
            }
            //top
            if (SegmentFount(discoverd_segments, segment_x, segment_y+1) == false){
                stars.addAll(Stars.GenerateStars(segment_x, segment_y+1, count));
                Data.Coords coords = new Data.Coords(segment_x, segment_y+1);
                discoverd_segments.addElement(coords);
            }
            //right top
            if (SegmentFount(discoverd_segments, segment_x+1, segment_y+1) == false){
                stars.addAll(Stars.GenerateStars(segment_x+1, segment_y+1, count));
                Data.Coords coords = new Data.Coords(segment_x+1, segment_y+1);
                discoverd_segments.addElement(coords);
            }
            //right
            if (SegmentFount(discoverd_segments, segment_x+1, segment_y) == false){
                stars.addAll(Stars.GenerateStars(segment_x+1, segment_y, count));
                Data.Coords coords = new Data.Coords(segment_x+1, segment_y);
                discoverd_segments.addElement(coords);
            }
            //right bottom
            if (SegmentFount(discoverd_segments, segment_x+1, segment_y-1) == false){
                stars.addAll(Stars.GenerateStars(segment_x+1, segment_y-1, count));
                Data.Coords coords = new Data.Coords(segment_x+1, segment_y-1);
                discoverd_segments.addElement(coords);
            }
            //bottom
            if (SegmentFount(discoverd_segments, segment_x, segment_y-1) == false){
                stars.addAll(Stars.GenerateStars(segment_x, segment_y-1, count));
                Data.Coords coords = new Data.Coords(segment_x, segment_y-1);
                discoverd_segments.addElement(coords);
            }
            //left bottom
            if (SegmentFount(discoverd_segments, segment_x-1, segment_y-1) == false){
                stars.addAll(Stars.GenerateStars(segment_x-1, segment_y-1, count));
                Data.Coords coords = new Data.Coords(segment_x-1, segment_y-1);
                discoverd_segments.addElement(coords);
            }
        }else{
            stars.addAll(Stars.GenerateStars(0, 0, count));
            Data.Coords coords = new Data.Coords(0, 0);
            discoverd_segments.addElement(coords);
        }

    }

}
