package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BurnedBlank extends Blank{
	
	Image image;
	
	public BurnedBlank(int x, int y) {
		super(x, y);
		initImage();
	}
	
	private void initImage() {
		try {
			image = ImageIO.read(this.getClass().getResource("ground_with_crater.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


	@Override
	public void draw(Graphics g) {		
		g.drawImage(image, getX(), getY(), null);
	}

}
