package view;

import org.newdawn.slick.geom.Vector2f;


import util.Locatable;

public class Camera {
	private float scale;
	private Locatable subject;
	
	
	public Camera(Locatable subject, float scale){

		this.subject = subject;
		this.scale = scale;
	}
	

	public void setSubject(Locatable subject){this.subject = subject;}
	public Locatable getSubject(){return subject;}

	public void setScale(float scale){this.scale = scale;}
	
	public Vector2f getFocus(){return subject.getPosition();}
	public float    getScale(){return scale;}
	public float    getAngle(){return subject.getAngle();}
	
	/*public Vector2f realToPixelCoords(Vector2f vector){
		return new Vector2f(
				(float) ((vector.x - focus.x) * zoom + Main.screenSize.getWidth() / 2),
				(float) ((vector.y - focus.y) * zoom + Main.screenSize.getHeight() / 2));
	}*/

	/*public Vector2f realToPixelCoords(float x, float y) {
        return realToPixelCoords(new Vector2f(x, y));
    }*/
}
