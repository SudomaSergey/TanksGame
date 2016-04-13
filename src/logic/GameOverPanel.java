package logic;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GameOverPanel extends JPanel{
	
	private Image imageBackground;
	private String gameResult;
	private PanelManager panelManager;
	
	public GameOverPanel(String gameResult, PanelManager panelManager) throws IOException{
		this.gameResult = gameResult;
		this.panelManager = panelManager;
		initBackground();
		initContent();
	}

	private void initContent() {
		
		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		ImageIcon start_icon_unpressed = new ImageIcon(this.getClass().getResource(
				"start_1.png"));
		JButton start_Button = new JButton(start_icon_unpressed);
		this.add(start_Button, c);
		c.anchor = GridBagConstraints.SOUTH;
		c.weighty = 1.0;
		c.insets = new Insets(0, 0, 0, 0);
		this.add(start_Button, c);
		
		start_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelManager.addMainMenuPanel();				
			}
		});
		
	}

	private void initBackground() throws IOException {
		if(gameResult.equals("win")){
			imageBackground = ImageIO.read(this.getClass().getResource("background_game_over_win.png"));
		}
		else if(gameResult.equals("fail")){
			imageBackground = ImageIO.read(this.getClass().getResource("background_game_over_fail.png"));
		}
		
	}	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imageBackground, 0, 0, null);
	}
	

}
