package org.playertoy.home;

import java.awt.Color;
import java.awt.Graphics;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;

public class Button extends JButton implements MouseListener {

	private static final long serialVersionUID = 1L;
	
	private Color background = new Color(100, 100, 100, 255);
	private Color hitColor = new Color(0, 0, 0, 0);
	private Color changingColor = new Color(0, 0, 200, 180);
	
	private boolean active = false;
	private boolean changing = false;
	private Panel root;
	
	public Button(Panel root) {
		super();
		super.setContentAreaFilled(false);
		super.addMouseListener(this);
		super.setFocusable(false);
		super.setVerifyInputWhenFocusTarget(false);
		setRoot(root);
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(hitColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
	
	@Override
    public void setContentAreaFilled(boolean b) {}
	
	@Override
	public Color getBackground() {
		if(isChanging()) {
			return changingColor;
		} else if(isActive()) {
			return hitColor;
		} else {
			return background;
		}
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
		revalidate();
		repaint();
	}
	
	public boolean isChanging() {
		return changing;
	}
	
	public void setChanging(boolean changing) {
		this.changing = changing;
		revalidate();
		repaint();
	}
	
	public Panel getRoot() {
		return root;
	}
	
	public void setRoot(Panel root) {
		this.root = root;
	}
	
	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	ScheduledFuture<?> future;
	
	public void changing() {
		future = executor.schedule(new Runnable() {
			
			@Override
			public void run() {
				setChanging(true);
				future = executor.schedule(new Runnable() {
					
					@Override
					public void run() {
						setChanging(false);
						setActive(false);
					}
					
				}, 14 - 3 * (getRoot().getTurnedCount() / 100) - getRoot().getDifficulty(), TimeUnit.SECONDS);
			}
			
		}, (new Random().nextInt(18) + 20) / getRoot().getDifficulty(), TimeUnit.SECONDS);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!isActive()) {
			setActive(true);
			getRoot().checkForWin();
			changing();
		} else if(isChanging()) {
			if(future != null && !future.isCancelled()) {
				future.cancel(true);
			}
			setChanging(false);
			changing();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
}
