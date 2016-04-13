package logic;

import java.io.Serializable;

import objects.AbstractTank;

public class GameplayRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7403026897028265591L;

	private ActionsPair actionsPairDefender;
	private ActionsPair actionsPairAgressor;
	private ActionsPair actionsPairKiller;

	public ActionsPair getActionsPairDefender() {
		return actionsPairDefender;
	}
	public void setActionsPairDefender(ActionsPair actionsPairDefender) {
		this.actionsPairDefender = actionsPairDefender;
	}
	public ActionsPair getActionsPairAgressor() {
		return actionsPairAgressor;
	}
	public void setActionsPairAgressor(ActionsPair actionsPairAgressor) {
		this.actionsPairAgressor = actionsPairAgressor;
	}
	public ActionsPair getActionsPairKiller() {
		return actionsPairKiller;
	}
	public void setActionsPairKiller(ActionsPair actionsPairKiller) {
		this.actionsPairKiller = actionsPairKiller;
	}
}
