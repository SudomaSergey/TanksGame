package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.sun.xml.internal.ws.developer.Serialization;

import objects.AbstractTank;

public class GameplayRecordDatabase implements Serializable{
	
	private static final long serialVersionUID = 2652718269037451607L;
	private ArrayList<GameplayRecord> gameplayRecords = new ArrayList<GameplayRecord>();
	private String[][] battlefield;
	private final transient String GAMEPLAYRECORDFILENAME = "tanksGamePlay.tnk";
	private int agressorX;
	private int agressorY;
	private int killerX;
	private int killerY;
	
	public GameplayRecordDatabase(){
	}

	public void addGameplayRecord(GameplayRecord gameplayRecord){
		gameplayRecords.add(gameplayRecord);
	}
	
	public void writeGamePlayToFile() throws FileNotFoundException, IOException{
		File gamePlayRecordFile = new File(GAMEPLAYRECORDFILENAME);
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(gamePlayRecordFile));
		out.writeObject(this);
		out.close();
	}
	
	public GameplayRecordDatabase getGameplayFromFile() throws Exception{
		File gamePlayRecordFile = new File(GAMEPLAYRECORDFILENAME);
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(gamePlayRecordFile));
		GameplayRecordDatabase gameplayRecordDatabase = (GameplayRecordDatabase) in.readObject();
		in.close();
		return gameplayRecordDatabase;
	}
	
	public ArrayList<GameplayRecord> getGameplayRecords(){
		return gameplayRecords;
	}

	public String[][] getBattlefield() {
		return battlefield;
	}
	
	public void setBattlefield(String[][] battlefield){
		this.battlefield = battlefield;		
	}
	
	public void setInitialStateAgressor(AbstractTank agressor){
		agressorX = agressor.getX();
		agressorY = agressor.getY();
	}
	
	public void setInitialStateKiller(AbstractTank killer){
		killerX = killer.getX();
		killerY = killer.getY();
	}
	
	public int getAgressorX() {
		return agressorX;
	}

	public void setAgressorX(int agressorX) {
		this.agressorX = agressorX;
	}

	public int getAgressorY() {
		return agressorY;
	}

	public void setAgressorY(int agressorY) {
		this.agressorY = agressorY;
	}

	public int getKillerX() {
		return killerX;
	}

	public void setKillerX(int killerX) {
		this.killerX = killerX;
	}

	public int getKillerY() {
		return killerY;
	}

	public void setKillerY(int killerY) {
		this.killerY = killerY;
	}
}

