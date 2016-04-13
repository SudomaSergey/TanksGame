package objects;

import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import logic.BattleField;
import logic.Direction;

public class T34 extends AbstractTank{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5250358646148151621L;
	private int health = 2;

	public T34(BattleField bf,int x, int y, Direction direction) {
		super(x, y, direction);
		initImages();
	}
	
	public T34() {
		super();
		initImages();		
	}
	
	private void initImages(){
		
		try {
			image_up = ImageIO.read(this.getClass().getResource("green_tank_up.png"));
			image_down = ImageIO.read(this.getClass().getResource("green_tank_down.png"));
			image_left = ImageIO.read(this.getClass().getResource("green_tank_left.png"));
			image_right = ImageIO.read(this.getClass().getResource("green_tank_right.png"));			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void destroy() {
		health--;
		if(health <= 0){
			super.destroy();
		}
	}
}
