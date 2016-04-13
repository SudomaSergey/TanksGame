package objects;

import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import logic.BattleField;
import logic.Direction;

public class BT7 extends AbstractTank implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 456300247062473337L;
	private int armor = 2;

	public BT7(int x, int y,
			Direction direction) {
		super(x, y, direction);
		initImage();	
	}
	

	private void initImage() {		
		try {
			image_up = ImageIO.read(this.getClass().getResource("red_tank_up.png"));
			image_down = ImageIO.read(this.getClass().getResource("red_tank_down.png"));
			image_left = ImageIO.read(this.getClass().getResource("red_tank_left.png"));
			image_right = ImageIO.read(this.getClass().getResource("red_tank_right.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public int getArmor(){
		return armor;
	}
	
	public int delArmor(){
		return armor = armor - 1;
	}	
}
