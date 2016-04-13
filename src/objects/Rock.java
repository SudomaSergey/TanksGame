package objects;

import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;

import logic.Destroyable;
import logic.Targetable;

public class Rock extends AbstractObjectOfField implements Targetable{
	
	private Image image;
	
	public Rock(int x, int y){
		super(x, y);
		initImage();
	}


	private void initImage() {		
		try {
			image = ImageIO.read(this.getClass().getResource("rock.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void draw(Graphics g) {
		g.drawImage(image, getX(), getY(), null);
	}


	@Override
	public void destroy() {
//		setX(-1000);
//		setY(-1000);
	}
}


