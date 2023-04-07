package main;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class KeyInputs implements KeyListener {

	public static int[] direc = new int[] {0,0};
	public static boolean attackPressed = false;

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Map<Integer, List<Integer>> locaInter = new HashMap <Integer, List<Integer>>(MapConst.interfaceLoca);
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			direc[1] = -1;
			break;
		case KeyEvent.VK_UP:
			direc[1] = 1;
			break;
		case KeyEvent.VK_RIGHT:
			direc[0] = -1;
			break;
		case KeyEvent.VK_LEFT:
			direc[0] = 1;
			break;
		case KeyEvent.VK_I:
			if (Mapa.state != Mapa.DIALOGUE) {
				if (Mapa.state == Mapa.INVENTORY) {
					Mapa.state = Mapa.GAMEPLAY;
					locaInter.remove(10);
					MapEffect.coinLabel = null;
				} else {
					Mapa.state = Mapa.INVENTORY;
					Dimension size = GamePanel.size;
					new StringEffect("0",new Point(GamePanel.size.width/2-3,GamePanel.size.height/2+122), 1, 0);
					locaInter = RemoveTab(locaInter);
					locaInter.put(10, Arrays.asList(8, (size.width-500)/2, size.height-531));
				}
			}
			break;
		case KeyEvent.VK_BACK_QUOTE:
			MapHelper.UseUseableItem(54);
			break;
		case KeyEvent.VK_1:
			MapHelper.UseUseableItem(55);
			break;
		case KeyEvent.VK_2:
			MapHelper.UseUseableItem(56);
			break;
		case KeyEvent.VK_3:
			MapHelper.UseUseableItem(57);
			break;
		case KeyEvent.VK_4:
			MapHelper.UseUseableItem(58);
			break;
		case KeyEvent.VK_5:
			MapHelper.UseUseableItem(59);
			break;
		case KeyEvent.VK_6:
			MapHelper.UseUseableItem(60);
			break;
		case KeyEvent.VK_7:
			MapHelper.UseUseableItem(61);
			break;
		case KeyEvent.VK_8:
			MapHelper.UseUseableItem(62);
			break;
		case KeyEvent.VK_9:
			MapHelper.UseUseableItem(63);
			break;
		case KeyEvent.VK_0:
			MapHelper.UseUseableItem(64);
			break;
		case KeyEvent.VK_MINUS:
			MapHelper.UseUseableItem(65);
			break;
		case KeyEvent.VK_ESCAPE:
			locaInter = RemoveTab(locaInter);
			if (Mapa.state == Mapa.DIALOGUE) {
				NPC npcO = (NPC)Mapa.clickedObj;
				npcO.frame = 0;
				NPCDialogue.questTarget = -1;
			}
			Mapa.state = Mapa.GAMEPLAY;
			break;
		case KeyEvent.VK_K:
			if (Mapa.state != Mapa.DIALOGUE) {
				if (Mapa.state == Mapa.SKILLS) {
					Mapa.state = Mapa.GAMEPLAY;
					locaInter.remove(12);
				} else {
					Mapa.state = Mapa.SKILLS;
					Dimension size = GamePanel.size;
					locaInter = RemoveTab(locaInter);
					locaInter.put(12, Arrays.asList(10, (size.width-500)/2 + 455, size.height-188));
				}
			}
			break;
		case KeyEvent.VK_O:
			if (Mapa.state != Mapa.DIALOGUE) {
				if (Mapa.state == Mapa.STATS) {
					Mapa.state = Mapa.GAMEPLAY;
					locaInter.remove(13);
				} else {
					Mapa.state = Mapa.STATS;
					Dimension size = GamePanel.size;
					locaInter = RemoveTab(locaInter);
					locaInter.put(13, Arrays.asList(11, (size.width-200)/2, size.height-574));
				}
			}
			break;
		case KeyEvent.VK_Z:
			switch(Mapa.state) {
				case Mapa.GAMEPLAY:
					int[] result;
					MapItem mItem;
					ArrayList<MapObj> runList = new ArrayList<MapObj>(Mapa.interMapObj);
					for (MapObj mapobj : runList) { //if click on obj get its type
						if (mapobj.getClass() == MapItem.class) {
							mItem = (MapItem)mapobj;
							if (mItem.state != mItem.LOOTED) {
								if (mItem.objType == 1000) {
									Inventory.coins += MapConst.objLoca.get(mItem.objKey).get(3);
									mItem.state = mItem.LOOTED;
									continue;
								}
								result = MapHelper.ModifyInventorySlots(mItem.objType, mItem.objKey);
								if (result[0] != -1) {
									mItem.state = mItem.LOOTED;
									if (result[0] != -2) {
										mItem.CreateNewInventoryItem(result[0],result[1]);
									}
								} else {
									System.out.println("no free slots");
								}
								((MapItem) mapobj).frame = 3;
							}
						}
					}
					runList.clear();
					break;
			}
			break;
		case KeyEvent.VK_X:
			switch(Mapa.state) {
				case Mapa.GAMEPLAY:
					attackPressed = true;
					break;
			}
			break;
		case KeyEvent.VK_A:
			MapHelper.SetSkillAction(0);
			break;
		case KeyEvent.VK_S:
			MapHelper.SetSkillAction(1);
			break;
		case KeyEvent.VK_D:
			MapHelper.SetSkillAction(2);
			break;
		case KeyEvent.VK_F:
			MapHelper.SetSkillAction(3);
			break;
		case KeyEvent.VK_T:
			Player.SavePlayerStats();
			Inventory.SaveInventoryItems();
			QuestTab.SaveQuests();
			SkillTab.SaveSkills();
			Mapa.SaveMapObjects();
			break;
		case KeyEvent.VK_Q:
			if (Mapa.state != Mapa.DIALOGUE) {
				if (Mapa.state == Mapa.QUEST) {
					Mapa.state = Mapa.GAMEPLAY;
					locaInter.remove(14);
				} else {
					Mapa.state = Mapa.QUEST;
					Dimension size = GamePanel.size;
					locaInter = RemoveTab(locaInter);
					locaInter.put(14, Arrays.asList(12, (size.width-800)/2, (size.height-500)/2));
				}
			}
			break;
			
		}
		MapConst.interfaceLoca = new HashMap <Integer, List<Integer>>(locaInter);
	}


	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			direc[1] = 0;
			break;
		case KeyEvent.VK_UP:
			direc[1] = 0;
			break;
		case KeyEvent.VK_RIGHT:
			direc[0] = 0;
			break;
		case KeyEvent.VK_LEFT:
			direc[0] = 0;
			break;
		case KeyEvent.VK_X:
			switch(Mapa.state) {
				case Mapa.GAMEPLAY:
					attackPressed = false;
					break;
			}
			break;
			
		}	
		
	}
	
	public Map<Integer, List<Integer>> RemoveTab(Map<Integer, List<Integer>> locas) {
		ArrayList<Integer> keyRemove = new ArrayList<Integer>();
		for (int key : locas.keySet()) {
			if (key == 10 || key == 12 || key == 13|| key == 14) {
				keyRemove.add(key);
			}
		}
		for(int key : keyRemove) {
			locas.remove(key);
		}
		return locas;
	}
	
}
