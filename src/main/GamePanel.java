package main;


import java.awt.Dimension;

import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class GamePanel extends JPanel {
	KeyInputs keyInputs = new KeyInputs();
	public static Dimension size = new Dimension(1280,800);
	MouseInputs mouseInputs = new MouseInputs();
	Game game;
	
	public GamePanel(Game game) {
		
		addKeyListener(keyInputs);
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
		setPanelSize();
		this.game = game;
		Player.cords[0] = (size.width-36)/2;
		Player.cords[1] = (size.height-50)/2;
	}
	

	private void setPanelSize() {
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setPreferredSize(size);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.Render(g, game.GetPlayer());
	}

}
