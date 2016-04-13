package objects;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;

import logic.Destroyable;
import logic.Direction;
import logic.Drawable;

public class Bullet implements Drawable, Destroyable{

	private  int x;
	private  int y;
	private Direction direction;
	private Integer shooter;
	private Image image_up;
	private Image image_down;
	private Image image_left;
	private Image image_right;
	private static CopyOnWriteArrayList<Bullet> bulletList = new CopyOnWriteArrayList<Bullet>();

	private int speed = 3;

	public Bullet(int x, int y, Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		initImage();
		bulletList.add(this);
		Drawable.drawableList.add(this);
	}
	
	public static CopyOnWriteArrayList<Bullet> getBulletList(){
		return bulletList;
	}

	public void updateX(int x) {
		this.x = this.x + x;
	}

	public void updateY(int y) {
		this.y = this.y + y;
	}

	public void destroy() {
		x = -1000;
		y = -1000;
		setShooter(null);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getSpeed(){
		return speed;
	}

	public Direction getDirection() {
		return direction;
	}

	private void initImage(){		
		try {

			image_up = ImageIO.read(this.getClass().getResource("Bullet_up.png"));
			image_down = ImageIO.read(this.getClass().getResource("Bullet_down.png"));
			image_left = ImageIO.read(this.getClass().getResource("Bullet_left.png"));
			image_right = ImageIO.read(this.getClass().getResource("Bullet_right.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g) {
		if (this.getDirection() == Direction.UP) {
			g.drawImage(image_up, getX(), getY(), null);
		} else if (this.getDirection() == Direction.DOWN) {
			g.drawImage(image_down, getX(), getY(), null);
		} else if (this.getDirection() == Direction.LEFT) {
			g.drawImage(image_left, getX(), getY(), null);
		} else {
			g.drawImage(image_right, getX(), getY(), null);
		}
	}

	public Integer getShooter() {
		return shooter;
	}

	public void setShooter(Integer shooter) {
		this.shooter = shooter;
	}


}
