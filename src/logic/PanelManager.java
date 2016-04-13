package logic;

import java.io.IOException;

public interface PanelManager {
	
	public void addMainMenuPanel();
	public void addActionFieldAndRunGame(String selectedTank, String selectedPlayer) throws Exception;
	public void addGameOverMenu(String gameResult) throws IOException;
	public void addActionFieldAndPlayLastGamePlay() throws Exception;
}
