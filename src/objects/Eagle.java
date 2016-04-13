package objects;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

import logic.Destroyable;
import logic.Targetable;

public class Eagle extends AbstractObjectOfField implements Destroyable{
	
	private Image image;

	public Eagle(int x, int y){
		super(x, y);
		initImage();
	}


	private void initImage() {
		try {
			image = ImageIO.read(this.getClass().getResource("eagle1.jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


	@Override
	public void draw(Graphics g) {		
		g.drawImage(image, getX(), getY(), null);
	}

}

