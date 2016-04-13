package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Blank extends AbstractObjectOfField{

	private Image image;

	public Blank(int x, int y){
		super(x, y);
		initImage();
	}


	private void initImage() {
		try {
			image = ImageIO.read(this.getClass().getResource("ground2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


	@Override
	public void draw(Graphics g) {		
		g.drawImage(image, getX(), getY(), null);
	}

}
