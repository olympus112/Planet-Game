package view;



import org.newdawn.slick.geom.Vector2f;

import PlanetGame.Main;



public class Camera {
	private double zoom;
	private Vector2f focus = new Vector2f(0, 0);
	
	public Camera(double zoom){
		this.zoom = zoom;
	}
	
	public void setFocus(Vector2f focusPoint){
		this.focus = focusPoint;
	}
	public void setZoom(double zoom){
		this.zoom = zoom;
	}
	
	public Vector2f realToPixelCoords(Vector2f vector){
		return new Vector2f((float) ((vector.x - focus.x) * zoom + Main.screenSize.getWidth() / 2), (float) ((vector.y - focus.y) * zoom + Main.screenSize.getHeight() / 2));
	}
}
