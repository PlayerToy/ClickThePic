package org.playertoy.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Panel extends JPanel {

	// Serial UID
	private static final long serialVersionUID = -2197084562553392446L;

	private Button[] buttons;
	
	private long startTime;
	private int gridSize;
	
	private ImageIcon background;
	
	private int difficulty = 0;	
	private int state = 0;
	
	public Panel(int gridSize) {
		this.gridSize = gridSize;
		
		URL backgroundImage = getClass().getResource("/sprites/backgrounds/background_" + new Random().nextInt(3) + ".jpg");
		if(backgroundImage == null) {
			JOptionPane.showMessageDialog(null, "I cannot load the background images. Please check for Resoures", 
					"Resources Missing", JOptionPane.ERROR_MESSAGE);
		} else {
			this.background = new ImageIcon(backgroundImage);
		}
		setBackground(Color.BLACK);
		setSize(Frame.WIDTH + 4, Frame.HEIGHT + 2);
		createStartPanel();
		
	}
	
	public void checkForWin() {
		for(int i = 0; i < buttons.length; i++) {
			if(!buttons[i].isActive()) {
				return;
			}
		}
		
		createWinPanel();
		
	}
	
	public JLabel createLabel(String text, boolean big) {
		JLabel label = new JLabel(text);
		if(big) {
			label.setText(text.toUpperCase());
			label.setFont(new Font("Monospaced", Font.PLAIN, 24));
			label.setBorder(new EmptyBorder(200, 0, 0, 0));
		} else {
			label.setFont(new Font("Monospaced", Font.PLAIN, 18));
		}
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		return label;
	}
	
	public void createStartPanel() {
		
		removeAll();
		revalidate();
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		setBackground(Color.LIGHT_GRAY);
		
		add(createLabel("welcome to ClickThePic", true));
		add(createLabel("rules to play this game:", false));
		add(createLabel("click on all gray plates to see a picture.", false));
		add(createLabel("your objective is that you can see the complete image.", false));
		add(createLabel("sometimes an image part will lit up blue,", false));
		add(createLabel("then you have 10 to 14 seconds to click on it again", false));
		add(createLabel("or they will turn back to gray", false));
		
		add(createLabel(" ", false));
		add(createLabel("Have a nice Click", false));
		add(createLabel(" ", false));
		
		String[] difficulties = {"Easy", "Normal", "Hard", "LudumDARE"};
		
		JComboBox<String> difficultySelect = new JComboBox<>(difficulties);
		difficultySelect.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
		difficultySelect.setMaximumSize( difficultySelect.getPreferredSize() );
		add(difficultySelect);
		
		add(createLabel(" ", false));
		
		JButton start = new JButton("START");
		start.setAlignmentX(JButton.CENTER_ALIGNMENT);
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				difficulty = difficultySelect.getSelectedIndex();
				createGamePanel();
			}
		});
		add(start);
		
		state = 0;
		
	}
	
	public void createWinPanel() {
		
		removeAll();
		revalidate();
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		setBackground(Color.LIGHT_GRAY);
		
		long timeInMillis = System.currentTimeMillis() - startTime;
		long seconds = (timeInMillis / 1000) % 60;
		long minutes = (timeInMillis / (1000 * 60)) % 60;
		long hours = (timeInMillis / (1000 * 60 * 60)) % 24;
		
		add(createLabel("You have WON, \nIt took you " + hours + ":" + minutes + ":" + seconds, true));
		add(createLabel(" ", false));
		
		String[] difficulties = {"Easy", "Normal", "Hard", "LudumDARE"};
		
		JComboBox<String> difficultySelect = new JComboBox<>(difficulties);
		difficultySelect.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
		difficultySelect.setMaximumSize( difficultySelect.getPreferredSize() );
		add(difficultySelect);
		
		add(createLabel(" ", false));
		
		JButton restart = new JButton("RESTART");
		restart.setAlignmentX(JButton.CENTER_ALIGNMENT);
		restart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				difficulty = difficultySelect.getSelectedIndex();
				createGamePanel();
			}
		});
		add(restart);
		
		state = 2;
		
	}
	
	public int getTurnedCount() {
		int turned = 0;
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].isActive()) {
				turned += 1;
			}
		}
		return turned;
	}
	
	public void createGamePanel() {
		
		removeAll();
		revalidate();
		
		setBackground(Color.GRAY);
		GridLayout layout = new GridLayout(gridSize, gridSize, 0, 0);
		setLayout(layout);
		
		// Button Setup
		buttons = new Button[gridSize * gridSize];
		
		for(int y = 0; y < gridSize; y++) {
			for(int x = 0; x < gridSize; x++) {
				Button button = new Button(this);
				// Look and Feel
				button.setBackground(Color.LIGHT_GRAY);
				button.setBorder(new LineBorder(Color.GRAY));
				button.setFocusable(false);
				button.setFocusPainted(false);
				button.setForeground(Color.GRAY);
				buttons[x + y * gridSize] = button;
				add(button);
			}
		}
		
		startTime = System.currentTimeMillis();
		state = 1;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	@Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if(state == 1 && background != null) {
	    	g.drawImage(background.getImage(), 0, 0, 640, 640, 0, 0, 640, 640, null);
	    }
	}
	
}
