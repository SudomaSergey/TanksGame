package logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import objects.AbstractObjectOfField;
import objects.AbstractTank;

public class AI {

	private ActionField af;

	public AI(ActionField af) {
		this.af = af;
	}
	
	public AbstractTank locateNearestTargetTo(AbstractTank tank){
		
		HashMap<Integer, AbstractTank> map = new HashMap<Integer, AbstractTank>();
		
		for(AbstractTank possibleNearestTank : AbstractTank.getTankList()){
			if(possibleNearestTank.getTankID() != tank.getTankID() && possibleNearestTank.getIsDestroyed() == false){				
				int stepsX = Math.abs(getXdifference(possibleNearestTank, tank));
				int stepsY = Math.abs(getYdifference(possibleNearestTank, tank));				
				int stepsTotal =  stepsX + stepsY;
				map.put(stepsTotal, possibleNearestTank);
			}
		}
		Integer minSteps = Collections.min(map.keySet());
		return map.get(minSteps);
	}
	
	private synchronized int getXdifference(AbstractTank tank, AbstractObjectOfField obj){
		String objQuadrant = af.getQuadrant(obj.getX(), obj.getY());
		String tankQuadrant = af.getQuadrant(tank.getX(), tank.getY());
		int objX = af.parseX(objQuadrant) * BattleField.PIXELS_IN_CELL;
		int tankX = af.parseX(tankQuadrant) * BattleField.PIXELS_IN_CELL;
		int xDifference = (objX - tankX) / BattleField.PIXELS_IN_CELL;
		return xDifference;
	}
	
	private synchronized int getYdifference(AbstractTank tank, AbstractObjectOfField obj){
		String objQuadrant = af.getQuadrant(obj.getX(), obj.getY());
		String tankQuadrant = af.getQuadrant(tank.getX(), tank.getY());
		int objY = af.parseY(objQuadrant) * BattleField.PIXELS_IN_CELL;		
		int tankY = af.parseY(tankQuadrant) * BattleField.PIXELS_IN_CELL;		
		int yDifference = (objY - tankY) / BattleField.PIXELS_IN_CELL;
		return yDifference;
	}

	public synchronized ActionsPair destroyTheTarget(AbstractTank tank, AbstractObjectOfField obj) {
		
		int xDifference = getXdifference(tank, obj);
		int yDifference = getYdifference(tank, obj);	
		
		ActionsPair emptyActionsPair = new ActionsPair(Actions.NONE, Direction.DEFAULT);
		
		if(tank.getIsDestroyed() == true){
			return emptyActionsPair;
		}

		if (obj.getIsDestroyed() == false) {
			
			if (xDifference > 0 && yDifference == 0){	
				return new ActionsPair(Actions.FIRE, Direction.RIGHT);
			}
			
			if (xDifference < 0 && yDifference == 0){
				return new ActionsPair(Actions.FIRE, Direction.LEFT);
			}
			
			if (yDifference < 0 && xDifference == 0){
				return new ActionsPair(Actions.FIRE, Direction.UP);
			}
			
			if (yDifference > 0 && xDifference == 0){
				return new ActionsPair(Actions.FIRE, Direction.DOWN);
			}

			if (xDifference > 0) {
				if (af.isAvailableForMove(tank, Direction.RIGHT)) {
					return new ActionsPair(Actions.MOVE, Direction.RIGHT);
				} else {
					//return new ActionsPair(Actions.FIRE, Direction.RIGHT);
					return obtainRndAction();
				}
			}

			if (xDifference < 0) {
				if (af.isAvailableForMove(tank, Direction.LEFT)) {
					return new ActionsPair(Actions.MOVE, Direction.LEFT);
				} else {
					//return new ActionsPair(Actions.FIRE, Direction.LEFT);
					return obtainRndAction();
				}
			}

			if (yDifference < 0) {
				if (af.isAvailableForMove(tank, Direction.UP)) {
					return new ActionsPair(Actions.MOVE, Direction.UP);
				} else {
					//return new ActionsPair(Actions.FIRE, Direction.UP);
					return obtainRndAction();
				}
			}

			if (yDifference > 0) {
				if (af.isAvailableForMove(tank, Direction.DOWN)) {
					return new ActionsPair(Actions.MOVE, Direction.DOWN);
				} else {
					//return new ActionsPair(Actions.FIRE, Direction.DOWN);
					return obtainRndAction();
				}
			}
		}
		return emptyActionsPair;
	}

	private ActionsPair obtainRndAction() {
		Random rnd = new Random();
		Actions[] tempActions = Actions.values();
		Direction[] tempDirections = Direction.values();
		Actions act = tempActions[rnd.nextInt(3)];
		Direction dir = tempDirections[rnd.nextInt(4)];
		return new ActionsPair(act, dir);
	}
}
