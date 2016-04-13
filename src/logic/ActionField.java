package logic;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.security.auth.DestroyFailedException;
import javax.swing.JPanel;
import javax.swing.Timer;

import logic.BattleField;
import logic.Direction;
import objects.AbstractObjectOfField;
import objects.AbstractTank;
import objects.BT7;
import objects.Blank;
import objects.Bullet;
import objects.BurnedBlank;
import objects.ExplosionAnimation;
import objects.ShotAnimation;
import objects.T34;
import objects.Tiger;

public class ActionField extends JPanel {

	private BattleField battleField;
	private AbstractTank defender;
	private Tiger agressor;
	private BT7 killer;
	private String coordinates;
	private AI ai;
	private GameFrame gameFrame;
	private String gameResult = null;
	private HashMap<Direction, String> availableDirectionMap = new HashMap<Direction, String>();
	private boolean isReplay = false;
	private GameplayRecordDatabase gameplayRecordDatabase = new GameplayRecordDatabase();
	private KeyboardListener keyboardListener;
	private String player = "pc";

	public static final int STEP = 1;

	public ActionField(GameFrame gameFrame, boolean isReplay) throws Exception {
		this.gameFrame = gameFrame;
		this.isReplay = isReplay;
		initFpsRateDaemon();
		initContent();
	}	
	
	public void initKeyboardListener(){
		keyboardListener = new KeyboardListener(this);
	}

	private void initFpsRateDaemon() {
		new ScreenRefreshDaemon(this);		
	}

	private void initContent() throws Exception {
		initBattlefield();
		initAgressor();
		initKiller();
		initAI();
	}

	private void initAI() {
		ai = new AI(this);
	}

	private void initBattlefield() throws Exception {

		if(isReplay == false){
			battleField = new BattleField();
		}
		else{
			loadGamePlayDatabaseFromFile();
			battleField = new BattleField(gameplayRecordDatabase.getBattlefield());
		}
	}

	private void initAgressor(){			
		if(agressor == null){
			getPredefiendCoordinates();
			agressor = new Tiger(parseX(coordinates), parseY(coordinates), Direction.DEFAULT);
		}
		if(isReplay == false){
				gameplayRecordDatabase.setInitialStateAgressor(agressor);
		}
		
	}

	private void initKiller() {
		if(killer == null){
			killer = new BT7(64, 448, Direction.DEFAULT);
		}
		if(isReplay == false){
			gameplayRecordDatabase.setInitialStateKiller(killer);			
		}
		
	}

	public void addT34() {
		defender = new T34();
	}

	public void addTiger() {
		defender = new Tiger();
	}

	public void runTheGame() throws Exception {		
		if(isReplay){
			setTanksInitialState();
		}		
		new GameEventTimer(this);
	}	
	

	private void setTanksInitialState() {
		agressor.setX(gameplayRecordDatabase.getAgressorX());
		agressor.setY(gameplayRecordDatabase.getAgressorY());
		killer.setX(gameplayRecordDatabase.getKillerX());
		killer.setY(gameplayRecordDatabase.getKillerY());		
	}

	public void processCurrentActions() {
		
		Thread eachTankThread = null;
		
		for(AbstractTank tank : AbstractTank.getTankList()){
			eachTankThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						ActionsPair ap = tank.getCurrentActionPair();
						
						if(ap == null){
							ap = new ActionsPair(Actions.NONE, tank.getDirection());
						}
						
						if(player.equals("human") && tank instanceof T34){
							return;
						}
						
						processAction(ap, tank);
						tank.setCurrentActionPair(null);
						
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
			});
			eachTankThread.start();
		}		
	}
	
	public void setCurrentActions(){
		
		if(player.equals("pc")){
			ActionsPair defenderActionsPair = ai.destroyTheTarget(defender, ai.locateNearestTargetTo(defender));
			defender.setCurrentActionPair(defenderActionsPair);
		}
		
		ActionsPair killerActionsPair = ai.destroyTheTarget(killer,battleField.getEagle());
		killer.setCurrentActionPair(killerActionsPair);
		
		ActionsPair agressorActionsPair = ai.destroyTheTarget(agressor, defender);
		agressor.setCurrentActionPair(agressorActionsPair);		
	}
	
	public void callGameOverMenu() throws IOException{
		destroyAllTanks();
		gameFrame.addGameOverMenu(gameResult);
	}
	
	public void callMainMenu(){
		destroyAllTanks();
		gameFrame.addMainMenuPanel();
	}

	private void destroyAllTanks() {
		Drawable.drawableList.clear();
		AbstractTank.getTankList().clear();
		AbstractTank.tankIDcount = 0;
	}

	public boolean isGameOver() {
		
		if (battleField.getEagle().getIsDestroyed() == true	|| defender.getIsDestroyed() == true) {
			gameResult = "fail";
			return true;
		} 
		else if (agressor.getIsDestroyed() == true && killer.getIsDestroyed() == true) {
			gameResult = "win";
			return true;
		}
		return false;
	}

	private void drawExplosionAnimation(Bullet bullet) throws InterruptedException, IOException {

		String explosionQuadrant = getQuadrant(bullet.getX(), bullet.getY());
		int x = parseX(explosionQuadrant) * BattleField.PIXELS_IN_CELL;
		int y = parseY(explosionQuadrant) * BattleField.PIXELS_IN_CELL;
		
		int delay = 1000;

		ExplosionAnimation explosionAnimation = new ExplosionAnimation();
		explosionAnimation.setBounds(x, y, BattleField.PIXELS_IN_CELL, BattleField.PIXELS_IN_CELL);
		explosionAnimation.setOpaque(false);
		add(explosionAnimation);

		if(battleField.checkQuadrant(x / BattleField.PIXELS_IN_CELL, y / BattleField.PIXELS_IN_CELL) instanceof Destroyable){
			battleField.addToList(new BurnedBlank(x, y));
		}
		
		Timer timer = new Timer(delay, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				remove(explosionAnimation);
			}
		});
		timer.start();

	}
	
	private void drawShotAnimation(Bullet bullet) {

		int delay = 300;

		ShotAnimation shotAnimation = new ShotAnimation(bullet);
		shotAnimation.setOpaque(false);
		add(shotAnimation);

		Timer timer = new Timer(delay, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				remove(shotAnimation);
			}
		});
		timer.start();
	}

	private void initAvailableDirectionMap(Tank tank) {

		int currentQuadrantX = tank.getX() / BattleField.PIXELS_IN_CELL;
		int currentQuadrantY = tank.getY() / BattleField.PIXELS_IN_CELL;
		int oneQuadrant = 1;

		String upperQuadrant = String.valueOf(currentQuadrantX) + "_"
				+ String.valueOf(currentQuadrantY - oneQuadrant);
		String lowerQuadrant = String.valueOf(currentQuadrantX) + "_"
				+ String.valueOf(currentQuadrantY + oneQuadrant);
		String lefterQuadrant = String.valueOf(currentQuadrantX - oneQuadrant)
				+ "_" + String.valueOf(currentQuadrantY);
		String righterQuadrant = String.valueOf(currentQuadrantX + oneQuadrant)
				+ "_" + String.valueOf(currentQuadrantY);

		availableDirectionMap.put(Direction.UP, upperQuadrant);
		availableDirectionMap.put(Direction.DOWN, lowerQuadrant);
		availableDirectionMap.put(Direction.LEFT, lefterQuadrant);
		availableDirectionMap.put(Direction.RIGHT, righterQuadrant);
	}

	boolean isAvailableForMove(Tank tank, Direction direction) {
		initAvailableDirectionMap(tank);
		String coordinatesToCheck = availableDirectionMap.get(direction);
		
		int coordinateXtoCheck = parseX(coordinatesToCheck);
		int coordinateYtoCheck = parseY(coordinatesToCheck);

		if (battleField.checkQuadrant(coordinateXtoCheck, coordinateYtoCheck) instanceof Blank) {
			return true;
		}
		return false;
	}

	void processAction(ActionsPair actionsPair, AbstractTank tank) throws Exception {

		boolean move = actionsPair.getAction().name().equals(Actions.MOVE.name());
		boolean fire = actionsPair.getAction().name().equals(Actions.FIRE.name());
	
		tank.setDirection(actionsPair.getDirection());

		if(move){			
			processMove(tank);
		}
		else if(fire){
			processFire(tank.fire());
		}
	}
	
	public void setCurrentActionsReplay() throws Exception {
		
		GameplayRecord gameplayRecord = gameplayRecordDatabase.getGameplayRecords().get(0);
		
		agressor.setCurrentActionPair(gameplayRecord.getActionsPairAgressor());
		defender.setCurrentActionPair(gameplayRecord.getActionsPairDefender());
		killer.setCurrentActionPair(gameplayRecord.getActionsPairKiller());
		
		gameplayRecordDatabase.getGameplayRecords().remove(0);
	}


	public void recordGameplay() throws FileNotFoundException, IOException, ClassNotFoundException {
		gameplayRecordDatabase.setBattlefield(battleField.getBattleField());
		
		GameplayRecord gameplayRecord = new GameplayRecord();
		
		gameplayRecord.setActionsPairAgressor(agressor.getCurrentActionPair());
		gameplayRecord.setActionsPairDefender(defender.getCurrentActionPair());
		gameplayRecord.setActionsPairKiller(killer.getCurrentActionPair());
		
		gameplayRecordDatabase.addGameplayRecord(gameplayRecord);
		gameplayRecordDatabase.writeGamePlayToFile();
	}

	private boolean isInField(AbstractObjectOfField object) {
		boolean checkY = object.getY() >= 0 && object.getY() <= battleField.getBfHeight() - BattleField.PIXELS_IN_CELL;
		boolean checkX = object.getX() >= 0 && object.getX() <= battleField.getBfWidth() - BattleField.PIXELS_IN_CELL;
		boolean result = checkX && checkY;
		return result;
	}

	private void processMove(Tank tank) throws InterruptedException {
		
		if (isInField((AbstractObjectOfField) tank)) {
			if (isAvailableForMove(tank, tank.getDirection())) {
				for (int i = 0; i < BattleField.PIXELS_IN_CELL; i++) {
					Thread.sleep(tank.getSpeed());

					if (tank.getDirection() == Direction.UP) {
						tank.updateY(-STEP);
					}

					else if (tank.getDirection() == Direction.DOWN) {
						tank.updateY(+STEP);
					}

					else if (tank.getDirection() == Direction.LEFT) {
						tank.updateX(-STEP);
					}

					else if (tank.getDirection() == Direction.RIGHT) {
						tank.updateX(+STEP);
					}
				}
			}
		}
	}
	
	private synchronized boolean processInterception(Bullet bullet) throws InterruptedException,
			DestroyFailedException {
		String bulletQuadrant = getQuadrant(bullet.getX(), bullet.getY());
		int checkBulletX = parseX(bulletQuadrant);
		int checkBulletY = parseY(bulletQuadrant);

		AbstractObjectOfField obj = battleField.checkQuadrant(checkBulletX, checkBulletY);
		
		if (obj instanceof Targetable) {
			return true;
		}		
		
		if (obj instanceof Destroyable){
				battleField.updateObjects(checkBulletX, checkBulletY);
				obj.destroy();
				return true;
		}		

		for (AbstractTank tank : AbstractTank.getTankList()) {			
			if((bullet.getX() >= tank.getX() && bullet.getX() <= tank.getX() + 63) &&
					(bullet.getY() <= tank.getY() && bullet.getY() >= tank.getY() - 63) &&
					(bullet.getShooter() != tank.getTankID())) {
						tank.destroy();
						return true;
			}			
		}
		
		
		for (Bullet bullet2 : Bullet.getBulletList()) {			
			if((bullet.getX() >= bullet2.getX() && bullet.getX() <= bullet2.getX() + 20) &&
					(bullet.getY() <= bullet2.getY() && bullet.getY() >= bullet2.getY() - 20) &&
					(bullet.getShooter() != bullet2.getShooter())) {
				bullet2.destroy();
				return true;
			}			
		}
		return false;
	}

	private void getPredefiendCoordinates() {
		String[] coordinatesList = { "", "64_64", "64_320", "448_448" };
		int randomInt = (int) (System.currentTimeMillis() % 2) + 1;
		coordinates = coordinatesList[randomInt];
	}

	int parseX(String coordinates) {
		return Integer.parseInt(coordinates.substring(0, coordinates.indexOf('_')));
	}

	int parseY(String coordinates) {
		return Integer.parseInt(coordinates.substring(coordinates.indexOf('_') + 1, coordinates.length()));
	}

	public String getQuadrant(int x, int y) {
		int quadrantX = x / BattleField.PIXELS_IN_CELL;
		int quadrantY = y / BattleField.PIXELS_IN_CELL;
		return (quadrantX + "_" + quadrantY);
	}

	@Override
	protected void paintComponent(Graphics g) {
		battleField.draw(g);
		for (Drawable obj : Drawable.drawableList) {
			if (obj != null) {
				obj.draw(g);
			}
		};
	}

	public void processFire(Bullet bullet) throws Exception {

		int compensation = 23;
		int maximalX = battleField.getBfHeight() - compensation;
		int maximalY = battleField.getBfHeight() - compensation;
		int minimalX = 0;
		int minimalY = 0;

		drawShotAnimation(bullet);

		while (bullet.getX() >= minimalX && bullet.getX() <= maximalX
				&& bullet.getY() >= minimalY && bullet.getY() <= maximalY) {

			if (bullet.getDirection() == Direction.UP) {
				bullet.updateY(-STEP);
			}

			else if (bullet.getDirection() == Direction.DOWN) {
				bullet.updateY(+STEP);
			}

			else if (bullet.getDirection() == Direction.LEFT) {
				bullet.updateX(-STEP);
			}

			else if (bullet.getDirection() == Direction.RIGHT) {
				bullet.updateX(+STEP);
			}
			Thread.sleep(bullet.getSpeed());

			if (processInterception(bullet)) {
				drawExplosionAnimation(bullet);
				bullet.destroy();
			}
		}
		bullet.destroy();
	}
	
	
	public void setIsReplay(boolean value){
		isReplay = value;
	}
	
	public boolean isReplay() {
		return isReplay;
	}

	public void setBattleField(BattleField battleField) {
		this.battleField = battleField;
	}

	public void setDefender(AbstractTank defender) {
		this.defender = defender;
	}

	public void setAgressor(Tiger agressor) {
		this.agressor = agressor;
	}

	public void setKiller(BT7 killer) {
		this.killer = killer;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public void setAi(AI ai) {
		this.ai = ai;
	}

	public void setTankFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}

	public void setGameResult(String gameResult) {
		this.gameResult = gameResult;
	}

	public void setAvailableDirectionMap(
			HashMap<Direction, String> availableDirectionMap) {
		this.availableDirectionMap = availableDirectionMap;
	}

	public void setReplay(boolean isReplay) {
		this.isReplay = isReplay;
	}

	public AbstractTank getDefender() {
		return defender;
	}

	public void setPlayer(String value) {
		player = value;		
	}	
	
	public GameplayRecordDatabase getGameplayRecordDatabase(){
		return gameplayRecordDatabase;
	}
	
	public void loadGamePlayDatabaseFromFile() throws Exception{
		gameplayRecordDatabase = gameplayRecordDatabase.getGameplayFromFile();
	}
	
}
