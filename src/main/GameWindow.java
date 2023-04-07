package main;

import javax.swing.JFrame;


public class GameWindow {
	public JFrame jframe;
	
	public GameWindow(GamePanel gamePanel) {
		jframe = new JFrame();
		jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
		jframe.add(gamePanel);
		jframe.setResizable(false);
		jframe.pack();
		jframe.setVisible(true);
		jframe.setLocationRelativeTo(null);

		//jframe.addWindowFocusListener(new WindowFocusListener() {}
	}
}
