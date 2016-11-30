package Stars;

import PlanetGame.Screen;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Stars {
    private float x;
    private float y;
    private float radius;
    private Color color;
    public static List<Vector2f> discovered_segments = new ArrayList<>();
    public static List<Stars> stars = new ArrayList<>();
    private float maxRadius = 3;

    public Stars() {

    }

    public Stars(float x, float y, float radius, Color color){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public void draw(Graphics g, float coord_x, float coord_y){
        int min = 100; int max = 255; // alpha levels
        float star_x = x - (coord_x / Screen.BACKGROUND_SPEED) + (Display.getWidth()/2);
        float star_y = -y + (coord_y / Screen.BACKGROUND_SPEED);
        if (star_x + radius > 0 && star_x < Display.getWidth() && star_y + radius > 0 && star_y < Display.getHeight()) {
            float alpha = (float) (min + Math.random() * ((max - min) + 1));
            g.setColor(new Color(color.r, color.g, color.b, alpha));
            g.fillOval(star_x, star_y, radius, radius);
        }
    }

    private void generateStars(int seg_x, int seg_y, int count){
        for (int i = 0; i < count; i++) {
            float randX = (float) (Math.random() * Display.getWidth() / 2);       //number between 0 and display width
            float randY = (float) (Math.random() * Display.getHeight() / 2);      //number between 0 and display height
            float seg_orig_x = seg_x * Display.getWidth() / 2;
            float seg_orig_y = seg_y * Display.getHeight() / 2;
            float x = seg_orig_x + randX;
            float y = seg_orig_y - randY;
            float radius = (float) (Math.random() * maxRadius);
            float color = (float) (155 + Math.random() * 100f);
            Stars s = new Stars(x, y, radius, new Color(255, 255, color));
            stars.add(s);
        }
    }

    public void addToSegment(int segment_x, int segment_y, int count){
        if (discovered_segments.size() > 0){
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j < 1; j++) {
                    if(!discovered_segments.contains(new Vector2f(segment_x + i, segment_y + j))) {
                        generateStars(segment_x + i, segment_y + j, count);
                        discovered_segments.add(new Vector2f(segment_x + i, segment_y + j))   ;
                    }
                }
            }
        } else {
            generateStars(0, 0, count);
            discovered_segments.add(new Vector2f());
        }

    }

}
