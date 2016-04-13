package logic;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import objects.Bullet;

public class KeyboardListener{
	
	private ActionField af;
	private boolean isMoving = false;
	
	public KeyboardListener(ActionField af){
		this.af = af;
		initKeyBindings();
	}
	


	private void initKeyBindings() {
		af.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
		af.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
		af.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
		af.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		af.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "fire");

		//move left
		af.getActionMap().put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	Thread moveLeftThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
		                try {
		                	
		                	if(isMoving){
		                		return;
		                	}
		                	
		                	
		                	isMoving = true;
		                	af.getDefender().setCurrentActionPair(new ActionsPair(Actions.MOVE, Direction.LEFT));
							af.processAction(new ActionsPair(Actions.MOVE, Direction.LEFT), af.getDefender());

		                	isMoving = false;
		                } catch (Exception e1) {
							e1.printStackTrace();
						}						
					}
				});
            	moveLeftThread.start();	
			}
		});
		
		
		//move right
		af.getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	Thread moveRightThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
		                try {
		                	
		                	while(isMoving){
			                	
		                	synchronized (this) {
								this.wait();
		                	}
		                	}
		                	
		                	
		                	isMoving = true;
							af.processAction(new ActionsPair(Actions.MOVE, Direction.RIGHT), af.getDefender());
		                	af.getDefender().setCurrentActionPair(new ActionsPair(Actions.MOVE, Direction.RIGHT));
		                	
		                	isMoving = false;
		                } catch (Exception e1) {
							e1.printStackTrace();
						}						
					}
				});
            	moveRightThread.start();	
			}
		});
		
		
		//move up
		af.getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	Thread moveUpThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
		                try {
		                	
		                	while(isMoving){
			                	
		                	synchronized (this) {
								this.wait();
		                	}
		                	}
		                	
		                	
		                	isMoving = true;
							af.processAction(new ActionsPair(Actions.MOVE, Direction.UP), af.getDefender());
		                	af.getDefender().setCurrentActionPair(new ActionsPair(Actions.MOVE, Direction.UP));
		                	isMoving = false;
		                } catch (Exception e1) {
							e1.printStackTrace();
						}						
					}
				});
            	moveUpThread.start();	
			}
		});
		
		
		//move down
		af.getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	Thread moveDownThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
		                try {
		                	
		                	while(isMoving){			                	
		                		synchronized (this) {
		                			this.wait();
		                		}
		                	}
		                	
		                	
		                	isMoving = true;
							af.processAction(new ActionsPair(Actions.MOVE, Direction.DOWN), af.getDefender());
		                	af.getDefender().setCurrentActionPair(new ActionsPair(Actions.MOVE, Direction.DOWN));
		                	isMoving = false;
		                } catch (Exception e1) {
							e1.printStackTrace();
						}						
					}
				});
            	moveDownThread.start();	
			}
		});
		
		
		//fire
		af.getActionMap().put("fire", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	Thread fireThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
		                try {
		                	
		                	while(isMoving){			                	
		                		synchronized (this) {
		                			this.wait();
		                		}
		                	}		                	
		                	
		                	isMoving = true;
		                	af.processAction(new ActionsPair(Actions.FIRE, af.getDefender().getDirection()), af.getDefender());
		                	af.getDefender().setCurrentActionPair(new ActionsPair(Actions.FIRE, af.getDefender().getDirection()));	                	
		                	isMoving = false;
		                } catch (Exception e1) {
							e1.printStackTrace();
						}						
					}
				});  	
 				fireThread.start();
			}
		});
		
	}
}


