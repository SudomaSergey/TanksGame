package logic;

import java.io.Serializable;

public class ActionsPair implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 210001918430964502L;
	private Actions action;
	private Direction direction;
	
	public ActionsPair(Actions actions, Direction direction){
		this.action = actions;
		this.direction = direction;
	}
	
	
	public Actions getAction() {
		return action;
	}

	public void setAction(Actions action) {
		this.action = action;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}	

}
