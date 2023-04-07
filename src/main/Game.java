package main;

import java.awt.Graphics;

public class Game implements Runnable{

	private Thread gameThread;
	private GamePanel gamePanel;
	private Inventory inventory;
	private SkillTab skilltab;
	private StatsTab statstab;
	private NPCDialogue npcDia;
	private QuestTab questTab;
	private Mapa gameMap;
	private Player player;
	private Menu menu;
	final int FPS_SET = 120;
	final int UPS_SET = 200;
	
	public Game () {
		player = new Player();
		gameMap = new Mapa();
		skilltab = new SkillTab();
		inventory = new Inventory();
		statstab = new StatsTab();
		npcDia = new NPCDialogue();
		questTab = new QuestTab();
		menu = new Menu();
		gameMap.importImage();
		gameMap.InitMapObj();
		
		gamePanel = new GamePanel(this);
		ItemConst.InitializeInventoryRects();
		SkillConst.InitializeSkillTabRects();
		
		inventory.importImage();
		skilltab.importImage();
		statstab.importImage();
		npcDia.importImage();
		questTab.LoadButtonImg();
		menu.importImage();
		
		new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void Render (Graphics g, Player player) {
		if (menu.state == menu.GAMEON) {
			gameMap.Draw(g, player, gamePanel);
			switch(Mapa.state) {
				case Mapa.INVENTORY:
					inventory.Draw(g);
					break;
				case Mapa.SKILLS:
					skilltab.Draw(g);
					break;
				case Mapa.STATS:
					statstab.Draw(g);
					break;
				case Mapa.DIALOGUE:
					npcDia.Draw(g);
					break;
				case Mapa.QUEST:
					questTab.Draw(g);
					break;
			}
		} else {
			menu.Draw(g);
		}
	}
	
	public Player GetPlayer() {
		return player;
	}
	
	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
		
		long previousTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while (true) {
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if (deltaU >= 1) {
				if (menu.state == menu.GAMEON) {
					gameMap.Update(player, inventory, skilltab, statstab, npcDia, questTab);
				} else {
					menu.UpdateMenu();
				}
				updates++;
				deltaU--;
			}
			
			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				//System.out.println("FPS: "+ frames +"| UPS: "+ updates);
				frames = 0;
				updates = 0;
			}		
		}
		
	}
}
