package objects;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

import logic.ActionsPair;
import logic.BattleField;
import logic.Destroyable;
import logic.Direction;
import logic.Drawable;
import logic.Tank;

public abstract class AbstractTank extends AbstractObjectOfField implements
		Tank, Destroyable, Drawable, Serializable{

	private int speed = 20;
	public int actionsCount = 0;
	private Direction direction;
	private String mySimpleName = this.getClass().getSimpleName();
	public static Integer tankIDcount = 0;
	private Integer tankID = tankIDcount;
	private static CopyOnWriteArrayList<AbstractTank> tankList = new CopyOnWriteArrayList<AbstractTank>();
	private int delatBtwShots = 1000;
	private long lastshotTime = 0;
	private ActionsPair currentActionPair = null;

	public AbstractTank() {
		this.x = 256;
		this.y = 128;
		this.direction = Direction.UP;
		++tankIDcount;
		tankList.add(this);
		Drawable.drawableList.add(this);
		setNotDestroyed();
	}

	public AbstractTank(int x, int y, Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		++tankIDcount;
		tankList.add(this);
		Drawable.drawableList.add(this);
		setNotDestroyed();
	}
	
	public Integer getTankID(){
		return tankID;
	}
	
	public static CopyOnWriteArrayList<AbstractTank> getTankList(){
		return tankList;
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

	public int getSpeed() {
		return speed;
	}

	public int getX() {
		return x;
	}

	public void updateX(int x) {
		this.x += x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void updateY(int y) {
		this.y += y;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void turn(Direction direction) throws Exception {
		this.direction = direction;
	}

	public Bullet fire() throws Exception {

		Bullet bullet = new Bullet(x, y, direction);
		bullet.setShooter(tankID);		
		return bullet;

		}

	public String getMySimpleName() {
		return mySimpleName;
	}

	public ActionsPair getCurrentActionPair() {
		return currentActionPair;
	}

	public void setCurrentActionPair(ActionsPair currentActionPair) {
		this.currentActionPair = currentActionPair;
	}
}
