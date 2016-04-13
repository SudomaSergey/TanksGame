package logic;

import java.awt.Graphics;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.security.auth.DestroyFailedException;

import objects.AbstractObjectOfField;
import objects.Blank;
import objects.Brick;
import objects.Eagle;
import objects.Rock;
import objects.Water;

public class BattleField{

	public static final int bfWidth = 576;
	public static final int bfHeight = 576;
	public static final int PIXELS_IN_CELL = 64;
	
	private int eagleX;
	private int eagleY;
	private Eagle eagle;
	private CopyOnWriteArrayList<AbstractObjectOfField> list = new CopyOnWriteArrayList<AbstractObjectOfField>();	
	private AbstractObjectOfField toBeDeleted;

	private String[][] battleField;
	
	public BattleField() {
		generateBattlefield();
		createObjects();
	}
	
	public BattleField(String[][] battlefield){
		this.battleField = battlefield;
		createObjects();
	}
	
	private void generateBattlefield(){
		String[][] battlefield_1 = {
				{ "B", "B", "B", "B", "B", "B", "B", "B", "B" },
				{ "B", " ", " ", " ", "B", " ", "", "", "B" },
				{ "B", " ", "", " ", "R", " ", "W", " ", "B" },
				{ "B", "B", "", " ", " ", " ", " ", " ", "B" },
				{ "B", "B", "B", "B", " ", " ", " ", " ", "B" },
				{ "B", " ", "W", "B", " ", " ", "", "B", "B" },
				{ "B", " ", " ", " ", "B", " ", " ", " ", "B" },
				{ "B", "E", " ", " ", " ", " ", " ", " ", "B" },
				{ "B", "B", "B", "B", "B", "B", "B", "B", "B" } };
		
		String[][] battlefield_2 = {
				{ " ", " ", "R", " ", " ", " ", " ", " ", " " },
				{ " ", "R", "W", " ", " ", "B", "", "", " " },
				{ " ", " ", "", " ", "R", " ", "W", " ", "W" },
				{ " ", " ", "", " ", " ", "R", " ", " ", " " },
				{ " ", "B", "W", " ", "B", " ", "R", " ", " " },
				{ " ", "E", "W", " ", " ", " ", "", " ", "W" },
				{ " ", "R", "B", " ", " ", "R", " ", " ", " " },
				{ " ", " ", "B", "B", "W", " ", " ", "W", " " },
				{ " ", " ", "B", " ", " ", " ", " ", " ", " " } };
		
		String[][] battlefield_3 = {
				{ " ", "R", " ", " ", " ", " ", "B", " ", " " },
				{ " ", "B", "B", " ", " ", " ", "B", "", " " },
				{ " ", "R", "B", " ", "R", " ", "W", " ", " " },
				{ " ", "E", "B", " ", "W", "W", "B", " ", " " },
				{ " ", " ", "W", "R", "W", "W", "R", " ", " " },
				{ " ", " ", "W", " ", "W", "W", "B", " ", " " },
				{ " ", " ", " ", " ", " ", " ", "B", " ", " " },
				{ " ", " ", " ", " ", " ", " ", "B", " ", " " },
				{ " ", " ", " ", " ", " ", " ", "B", " ", " " } };
		
		Random rnd = new Random();
		int battlefieldNo = rnd.nextInt(3);
		System.out.println(battlefieldNo);
		if(battlefieldNo == 0){
			battleField = battlefield_1;
		}
		else if (battlefieldNo == 1){
			battleField = battlefield_2;
		}
		else{
			battleField = battlefield_3;
		}
	}

	public String[][] getBattleField() {
		return battleField;
	}
	
	public void setBattlefield(String[][] battlefield){
		this.battleField = battlefield;
	}

	public void addToList(AbstractObjectOfField obj) {
		list.add(obj);
	}

	public CopyOnWriteArrayList<AbstractObjectOfField> getList() {
		return list;
	}

	public String scanQuadrant(int x, int y) {
		return battleField[x][y];
	}

	public void updateQuadrant(int x, int y, String value) {
		battleField[x][y] = value;
	}

	public int getDimensionX() {
		return battleField.length;
	}

	public int getDimensionY() {
		return battleField.length;
	}

	public int getBfWidth() {
		return bfWidth;
	}

	public int getBfHeight() {
		return bfHeight;
	}

	public AbstractObjectOfField checkQuadrant(int x, int y) {
		AbstractObjectOfField result = null;
		for (AbstractObjectOfField obj : list) {
			if (obj.getX() == x * PIXELS_IN_CELL
					&& obj.getY() == y * PIXELS_IN_CELL) {
				result = obj;
			}
		}
		return result;
	}

	public void updateObjects(int x, int y) throws DestroyFailedException {
		for (AbstractObjectOfField obj : list) {
			if (obj instanceof Destroyable){
				if (obj.getX() == x * PIXELS_IN_CELL && obj.getY() == y * PIXELS_IN_CELL) {
					toBeDeleted = obj;
				}
			}
		}
		if(toBeDeleted instanceof Destroyable){
			list.remove(toBeDeleted);
		}
	}

	public boolean checkInterception(int x, int y) {
		boolean result = false;
		for (AbstractObjectOfField obj : list) {
			if (obj instanceof Targetable) {
				if (obj.getX() == x * PIXELS_IN_CELL && obj.getY() == y * PIXELS_IN_CELL) {
					result = true;
				}
			}
		}
		return result;
	}
	
	public void createObjects() {
		for (int k = 0; k < this.getDimensionY(); k++) {
			for (int j = 0; j < this.getDimensionX(); j++) {
				if (this.scanQuadrant(j, k).equals("B")) {
					list.add(new Brick(j * PIXELS_IN_CELL, k * PIXELS_IN_CELL));
				} else if (this.scanQuadrant(j, k).equals("R")) {
					list.add(new Rock(j * PIXELS_IN_CELL, k * PIXELS_IN_CELL));
				} else if (this.scanQuadrant(j, k).equals("E")) {
					list.add(eagle = new Eagle(j * PIXELS_IN_CELL, k
							* PIXELS_IN_CELL));
					setEagleX(j);
					setEagleY(k);
				} else if (this.scanQuadrant(j, k).equals("W")) {
					list.add(new Water(j * PIXELS_IN_CELL, k * PIXELS_IN_CELL));

				}

				else {
					list.add(new Blank(j * PIXELS_IN_CELL, k * PIXELS_IN_CELL));
				}

			}
		}
	}

	public void draw(Graphics g) {
		for (AbstractObjectOfField item : list) {
			item.draw(g);
		}
	}

	public int getEagleX() {
		return eagle.getX();
	}

	public void setEagleX(int eagleX) {
		this.eagleX = eagleX;
	}

	public int getEagleY() {
		return eagle.getY();
	}

	private void setEagleY(int eagleY) {
		this.eagleY = eagleY;
	}

	public synchronized Eagle getEagle() {
		return eagle;
	}
	
}
