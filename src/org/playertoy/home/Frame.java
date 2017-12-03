package org.playertoy.home;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Frame extends JFrame {
	
	// Serial UID
	private static final long serialVersionUID = -2895355959111543897L;

	// Game Title
	public static String TITLE = "ClickThePic";
	
	// Boundaries
	public static int WIDTH = 636;
	public static int HEIGHT = 639;
	
	public static int GRIDSIZE = 10;
	
	// Creating A Frame
	public Frame() {
		// Panel Setup
		Panel panel = new Panel(GRIDSIZE);
		
		// Frame Setup
		setBounds(0, 0, WIDTH, HEIGHT);
		setAutoRequestFocus(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		// Setting Title and Icon
		setTitle(TITLE + " | LudumDare40");
		ClassLoader classLoader = getClass().getClassLoader();
		URL iconURL = classLoader.getResource("sprites\\icons\\WindowIcon.png");
		if(iconURL != null) {
			ImageIcon icon = new ImageIcon();
			setIconImage(icon.getImage());
		}
		// Set the Panel
		setContentPane(panel);
		
		// Make MUSIC \_(~.~)_/
		try {
			Clip music = AudioSystem.getClip();
			music.open(AudioSystem.getAudioInputStream(classLoader.getResource("music\\background.mid")));
			music.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (NullPointerException | LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			JOptionPane.showMessageDialog(null, "I can't load the music. Please check for Resources", 
					"No Music", JOptionPane.ERROR_MESSAGE);
		}
		
		// Make the Frame Visible
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Frame();
	}
	
}
