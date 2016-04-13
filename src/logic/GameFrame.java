package logic;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class GameFrame extends JFrame implements PanelManager{
	
	private JPanel currentJPanel;
	private ActionField af;
	
	public GameFrame() throws Exception{
		super("TANKS BATTLE");
		createFrame();
	}
	
	public void createFrame(){
		this.setLocation(450, 75);
		this.setMinimumSize(new Dimension(BattleField.bfWidth, BattleField.bfHeight + 25));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		addMainMenuPanel();
		this.setVisible(true);
	}

	@Override
	public void addMainMenuPanel() {
		MainManuPanel ts = new MainManuPanel(this);
		
		if(currentJPanel != null){
			remove(currentJPanel);
		}
		
		currentJPanel = ts;
		this.add(ts);
		this.revalidate();
	}
	
	
	@Override
	public void addActionFieldAndRunGame(String selectedTank, String selectedPlayer) throws Exception{
		
		if(af == null){		
			af = new ActionField(this, false);
		};
		
		this.remove(currentJPanel);
		currentJPanel = af;
		this.add(af);

		addTank(selectedTank);
		addPlayer(selectedPlayer);
		
		this.revalidate();
		af.runTheGame();
	}

	private void addPlayer(String selectedPlayer) {
		af.setPlayer(selectedPlayer);
		
		if(selectedPlayer.equals("human")){
			af.initKeyboardListener();
		}
	}

	private void addTank(String selectedTank) {
		if(selectedTank.equals("t34")){
			af.addT34();
		}
		else{
			af.addTiger();
		}
	}

	@Override
	public void addGameOverMenu(String gameResult) throws IOException {
		af = null;
		GameOverPanel gameOverPanel = new GameOverPanel(gameResult, this);
		this.remove(currentJPanel);
		currentJPanel = gameOverPanel;
		this.add(gameOverPanel);		
		this.revalidate();
		
	}

	@Override
	public void addActionFieldAndPlayLastGamePlay() throws Exception {
		
		af = new ActionField(this, true);

		this.remove(currentJPanel);
		currentJPanel = af;
		this.add(af);
		
		af.addT34();
		
		this.revalidate();
		af.runTheGame();
	}
}
