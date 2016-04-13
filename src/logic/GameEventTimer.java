package logic;

import java.util.Timer;
import java.util.TimerTask;

public class GameEventTimer extends Timer {

	private ActionField af;

	public GameEventTimer(ActionField af) {
		this.af = af;
		initTask();
	}

	private void initTask() {
	
		this.schedule(new TimerTask() {

			@Override
			public void run() {
				if(af.isReplay() == false){
					processPlayGame();
				}
				else{
					try {
						processReplayGame();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, 0, 1500);
	}

	private void processPlayGame(){
		try {
			af.setCurrentActions();
			af.recordGameplay();
			af.processCurrentActions();
			
			if(af.isGameOver()){
				this.cancel();
				af.callGameOverMenu();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void processReplayGame() throws Exception{
		af.setCurrentActionsReplay();
		af.processCurrentActions();
		
		if(af.getGameplayRecordDatabase().getGameplayRecords().size() == 0){
			this.cancel();
			af.loadGamePlayDatabaseFromFile();
			af.callMainMenu();

		}
		
	}
}
