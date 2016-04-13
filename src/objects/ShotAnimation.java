package objects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import logic.Direction;

public class ShotAnimation extends JLabel{

	private ImageIcon image;
	
	public ShotAnimation(Bullet bullet){
		initImage(bullet.getDirection());
		initPosition(bullet);
		this.setIcon(image);
		playSound();
	}

	private void initPosition(Bullet bullet) {

		int xShift = 3;
		int yShift = 50;
		int dimensionX = 50;
		int dimensionY = 50;		
		boolean directionUp = bullet.getDirection() == Direction.UP;
		boolean directionDown = bullet.getDirection() == Direction.DOWN;
		boolean directionLeft = bullet.getDirection() == Direction.LEFT;
		boolean directionRight = bullet.getDirection() == Direction.RIGHT;
		
		if(directionUp)this.setBounds(bullet.getX() - xShift, bullet.getY() - yShift, dimensionX, dimensionY);
		if(directionDown)this.setBounds(bullet.getX() - xShift, bullet.getY() + yShift, dimensionX, dimensionY);
		if(directionLeft)this.setBounds(bullet.getX() - yShift, bullet.getY() + xShift, dimensionX, dimensionY);
		if(directionRight)this.setBounds(bullet.getX() + yShift, bullet.getY() + xShift, dimensionX, dimensionY);		
	}

	private void playSound() {
		try (AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("shot_sound.wav"));) {
			Clip clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.start();         
        }
        catch(Exception e){  
        	e.printStackTrace();
        }
	}
	
	private void initImage(Direction direction) {
		if(direction == Direction.UP){
			image = new ImageIcon(this.getClass().getResource("shot_up.gif"));
		}
		else if(direction == Direction.DOWN){
			image = new ImageIcon(this.getClass().getResource("shot_down.gif"));
		}
		else if(direction == Direction.LEFT){
			image = new ImageIcon(this.getClass().getResource("shot_left.gif"));
		}
		else{
			image = new ImageIcon(this.getClass().getResource("shot_right.gif"));
		}
	}
}
