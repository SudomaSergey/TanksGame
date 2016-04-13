package logic;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Drawable {
	
	public static CopyOnWriteArrayList<Drawable> drawableList = new CopyOnWriteArrayList<Drawable>();
	public void draw(Graphics g);
	
}
