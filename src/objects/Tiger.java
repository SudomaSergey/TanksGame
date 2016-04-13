package objects;

import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import logic.BattleField;
import logic.Direction;

public class Tiger extends AbstractTank implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2741444099213608519L;
	private int armor = 2;

	public Tiger(int x, int y,
			Direction direction) {
		super(x, y, direction);
		initImage();
		
	}
	
	public Tiger() {
		super();
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
