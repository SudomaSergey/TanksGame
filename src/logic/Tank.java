package logic;

import objects.Bullet;

public interface Tank {

	public int getSpeed();
	public int getX();
	public int getY();
	public void setX(int x);
	public void setY(int y);
	public void updateX(int x);
	public void updateY(int y);
	public Direction getDirection();
	public void setDirection(Direction direction);
	public void turn(Direction direction) throws Exception;
	public Bullet fire() throws Exception;
	public String getMySimpleName();

}
