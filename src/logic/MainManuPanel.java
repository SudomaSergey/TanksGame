package logic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainManuPanel extends JPanel {

	private PanelManager panelManager;
	private String selectedTank = null;
	private String selectedPlayer = null;

	public MainManuPanel(PanelManager panelManager) {
		this.panelManager = panelManager;
		createComponents();
	}

	private void createComponents() {

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		ImageIcon start_icon_unpressed = new ImageIcon(this.getClass().getResource("start_1.png"));
		JButton start_Button = new JButton(start_icon_unpressed);
		springLayout.putConstraint(SpringLayout.WEST, start_Button, -78, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, start_Button, -171, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, start_Button, -10, SpringLayout.EAST, this);
		this.add(start_Button);
		
		ImageIcon replay_icon = new ImageIcon(this.getClass().getResource("replay.png"));
		JButton replay_button = new JButton(replay_icon);
		springLayout.putConstraint(SpringLayout.NORTH, replay_button, 98, SpringLayout.SOUTH, start_Button);
		springLayout.putConstraint(SpringLayout.WEST, replay_button, 0, SpringLayout.WEST, start_Button);
		springLayout.putConstraint(SpringLayout.SOUTH, replay_button, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, replay_button, 0, SpringLayout.EAST, start_Button);
		this.add(replay_button);
		
		ImageIcon human_icon = new ImageIcon(this.getClass().getResource("human_button.png"));
		ImageIcon human_icon_selected = new ImageIcon(this.getClass().getResource("human_button_selected.png"));
		JButton human_button = new JButton(human_icon);
		springLayout.putConstraint(SpringLayout.NORTH, human_button, 17, SpringLayout.SOUTH, start_Button);
		springLayout.putConstraint(SpringLayout.WEST, human_button, 0, SpringLayout.WEST, start_Button);
		springLayout.putConstraint(SpringLayout.SOUTH, human_button, 80, SpringLayout.SOUTH, start_Button);
		springLayout.putConstraint(SpringLayout.EAST, human_button, 0, SpringLayout.EAST, start_Button);
		add(human_button);
		
		ImageIcon pc_icon = new ImageIcon(this.getClass().getResource("pc_button.png"));
		ImageIcon pc_icon_selected = new ImageIcon(this.getClass().getResource("pc_button_selected.png"));
		JButton pc_button = new JButton(pc_icon);
		springLayout.putConstraint(SpringLayout.WEST, pc_button, 417, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, pc_button, -6, SpringLayout.WEST, human_button);
		springLayout.putConstraint(SpringLayout.NORTH, pc_button, 0, SpringLayout.NORTH, human_button);
		springLayout.putConstraint(SpringLayout.SOUTH, pc_button, 63, SpringLayout.NORTH, human_button);
		add(pc_button);
		
		ImageIcon tiger_icon = new ImageIcon(this.getClass().getResource("red_tank_right.png"));
		ImageIcon tiger_icon_selected = new ImageIcon(this.getClass().getResource("red_tank_right_selected.png"));
		JButton tiger_Button = new JButton(tiger_icon);
		springLayout.putConstraint(SpringLayout.NORTH, tiger_Button, 0, SpringLayout.NORTH, start_Button);
		springLayout.putConstraint(SpringLayout.WEST, tiger_Button, -74, SpringLayout.WEST, start_Button);
		springLayout.putConstraint(SpringLayout.SOUTH, tiger_Button, 0, SpringLayout.SOUTH, start_Button);
		springLayout.putConstraint(SpringLayout.EAST, tiger_Button, -6, SpringLayout.WEST, start_Button);
		add(tiger_Button);
		
		ImageIcon bt7_icon = new ImageIcon(this.getClass().getResource("green_tank_left.png"));
		ImageIcon bt7_icon_selected = new ImageIcon(this.getClass().getResource("green_tank_left_selected.png"));
		JButton bt7_button = new JButton(bt7_icon);
		springLayout.putConstraint(SpringLayout.NORTH, bt7_button, 0, SpringLayout.NORTH, start_Button);
		springLayout.putConstraint(SpringLayout.WEST, bt7_button, 343, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, bt7_button, 0, SpringLayout.SOUTH, start_Button);
		springLayout.putConstraint(SpringLayout.EAST, bt7_button, -6, SpringLayout.WEST, tiger_Button);
		add(bt7_button);
		
				replay_button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							panelManager.addActionFieldAndPlayLastGamePlay();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}						
					}
				});
		
		
				start_Button.addActionListener(new ActionListener() {
		
					@Override
					public void actionPerformed(ActionEvent e) {
						if (selectedTank != null && selectedPlayer != null) {
		
							try {
								panelManager.addActionFieldAndRunGame(selectedTank, selectedPlayer);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				});
		
		
		pc_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pc_button.setIcon(pc_icon_selected);
				human_button.setIcon(human_icon);
				selectedPlayer = "pc";
			}
		});
		
		
		human_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pc_button.setIcon(pc_icon);
				human_button.setIcon(human_icon_selected);
				selectedPlayer = "human";
			}
		});
		
		
		bt7_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				bt7_button.setIcon(bt7_icon_selected);
				tiger_Button.setIcon(tiger_icon);
				selectedTank = "t34";
			}
		});

		tiger_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tiger_Button.setIcon(tiger_icon_selected);
				bt7_button.setIcon(bt7_icon);
				selectedTank = "tiger";
			}
		});

	}

	@Override
	public void paintComponent(Graphics g) {
		Image image = null;
		try {
			image = ImageIO.read(this.getClass().getResource(
					"background_menu_1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(image, 0, 0, null);
	}
}