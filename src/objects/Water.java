package objects;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Water extends AbstractObjectOfField{
		
	
	private Image image;
	
	
		public Water(int x, int y){
			super(x, y);
			initImage();
		}


		private void initImage() {
			try {
				image = ImageIO.read(this.getClass().getResource("water.jpg"));
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
			setX(-1000);
			setY(-1000);
		}
	}