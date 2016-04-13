package objects;

import java.awt.Image;
import java.io.Serializable;

import javax.security.auth.Destroyable;

import logic.Drawable;
import logic.Targetable;

public abstract class AbstractObjectOfField implements Drawable, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -891363379979214622L;
	protected int x;
	protected int y;
	protected transient Image image;
	protected transient Image image_up;
	protected transient Image image_down;
	protected transient Image image_left;
	protected transient Image image_right;
	protected boolean isDestroyed = false;
		
	public AbstractObjectOfField(){

	}
	
	public AbstractObjectOfField(int x, int y){
		this.x = x;
		this.y = y;
	}
		
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean getIsDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed() {
		isDestroyed = true;
	}
	
	public void setNotDestroyed(){
		isDestroyed = false;
	}
	
	public void destroy(){
		setX (-1000);
		setY (-1000);
		setDestroyed();
	}
}


