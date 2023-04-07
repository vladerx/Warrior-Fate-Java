package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class QuestTab {
	public static Map<Integer, List<Integer>> questRects = new HashMap <Integer, List<Integer>>();
	public static BufferedImage questButtonImg;
	public static  Map<Integer, MapEffect> tabEff =  new HashMap <Integer, MapEffect>();
	public static int buttonHover = 0, page = 0, pages = 0, onGoingQuests = 0;
	public static boolean pageChanged = false;
	
	public QuestTab() {
		Dimension size = GamePanel.size;
		int x = (size.width-800)/2+133 ,y = (size.height-500)/2+24;
		questRects.put(0, Arrays.asList(x,y,542,50,-1));
		questRects.put(1, Arrays.asList(x,y+50,542,50,-1));
		questRects.put(2, Arrays.asList(x,y+100,542,50,-1));
		questRects.put(3, Arrays.asList(x,y+150,542,50,-1));
		questRects.put(4, Arrays.asList(x,y+200,542,50,-1));
		questRects.put(5, Arrays.asList(x-50,y+222,50,50));
		questRects.put(6, Arrays.asList(x+542,y+222,50,50));
		questRects.put(7, Arrays.asList(x-85,y+334,80,75,-1));
		questRects.put(8, Arrays.asList(x+50,y+310,50,50,-1));
		questRects.put(9, Arrays.asList(x+50,y+380,50,50,-1));
		questRects.put(10, Arrays.asList(x+320,y+310,50,50,-1));
		questRects.put(11, Arrays.asList(x+320,y+380,50,50,-1));
		Map<Integer, List<Integer>> qRects = new HashMap <Integer, List<Integer>>(questRects);
		Map<Integer, MapEffect> tEff =  new HashMap <Integer, MapEffect>(tabEff);
		UpdateQuestsStatus(qRects, tEff);
	}
	
	public void Draw(Graphics g) {
		Map<Integer, List<Integer>> rects = new HashMap <Integer, List<Integer>>(questRects);
		Map<Integer, MapEffect> tEff =  new HashMap <Integer, MapEffect>(tabEff);
		Graphics2D g2 = (Graphics2D) g;
		Font oldFont = g2.getFont();
		int width, height;
		StringEffect strEff;
		g2.fillRect((GamePanel.size.width - 400)/2, (GamePanel.size.height -500)/2-80, 400, 75);
		Color oldColor = g2.getColor();
		g2.setColor(new Color(78, 29, 162));
		g2.fillRect((GamePanel.size.width - 392)/2, (GamePanel.size.height -500)/2-76, 392, 67);
		g2.setColor(new Color(255, 202, 24));
		g2.fillRect((GamePanel.size.width - 384)/2, (GamePanel.size.height -500)/2-72, 384, 59);
		g2.setColor(oldColor);
		g2.setFont(new Font("Rockwell", Font.BOLD, 30));
		g2.drawString("Quests", (GamePanel.size.width - ("Quests".length()*17))/2, (GamePanel.size.height -500)/2-35);
		g2.setFont(new Font("Rockwell", Font.BOLD, 20));
		for (int rectkey : rects.keySet()) {
			if (rectkey == 5) {
				g.drawImage(questButtonImg.getSubimage((buttonHover < 2 ? buttonHover*50 :  0), 0, 50, 50), rects.get(rectkey).get(0), rects.get(rectkey).get(1), null);
			} else if (rectkey == 6){
				g.drawImage(questButtonImg.getSubimage((buttonHover == 2 ? 150 :  100), 0, 50, 50), rects.get(rectkey).get(0), rects.get(rectkey).get(1), null);
			} else if (rectkey == 7 && rects.get(rectkey).get(4) != -1) {
				width = MapConst.objProps.get(rects.get(7).get(4)).get(0);
				height = MapConst.objProps.get(rects.get(7).get(4)).get(1);		
				g.drawImage(Mapa.npcsImgs.get(rects.get(7).get(4)).getSubimage(0, 0, width, height), rects.get(rectkey).get(0)+((80-width)/2), rects.get(rectkey).get(1)+((75-height)/2), null);
			} else if (rects.get(rectkey).get(4) != -1 && rectkey > 4) {
				width = MapConst.objProps.get(rects.get(rectkey).get(4)).get(0);
				height = MapConst.objProps.get(rects.get(rectkey).get(4)).get(1);		
				g.drawImage(Mapa.mapItemImgs.get(rects.get(rectkey).get(4)).getSubimage(0, 0, width, height), rects.get(rectkey).get(0)+((50-width)/2), rects.get(rectkey).get(1)+((50-height)/2), null);
			}
			if (tEff.containsKey(rectkey)) {
				strEff = (StringEffect) tEff.get(rectkey);
				g2.drawString(strEff.label, strEff.point.x, strEff.point.y);
			} 
		}
		g2.setFont(oldFont);
		
	}
	
	public void UpdateQuestTab() {
		Map<Integer, List<Integer>> qRects = new HashMap <Integer, List<Integer>>(questRects);
		Map<Integer, List<Integer>> qReqs = new HashMap <Integer, List<Integer>>(QuestConst.questReqs);
		Map<Integer, MapEffect> tEff =  new HashMap <Integer, MapEffect>(tabEff);
		buttonHover = 0;
		if (pageChanged) {
			pageChanged = false;
			for (int rectKey : qRects.keySet()) {
				if (rectKey != 5 && rectKey != 6) {
					qRects.get(rectKey).set(4, -1);
				}
			}
			tEff.clear();
		}
		tEff = UpdateQuestsStatus(qRects, tEff);
		int questId;
		boolean found = false;
		Rectangle rect = new Rectangle();
		for (int rectKey : qRects.keySet()) {
			rect.x = qRects.get(rectKey).get(0);
			rect.y = qRects.get(rectKey).get(1);
			rect.width = qRects.get(rectKey).get(2);
			rect.height = qRects.get(rectKey).get(3);
			if (rect.contains(MouseInputs.mousePoint.x, MouseInputs.mousePoint.y)) {
				switch (rectKey) {
					case 5:
						buttonHover = 1;
						break;
					case 6:
						buttonHover = 2;
						break;
					default:
						found = true;
						questId = qRects.get(rectKey).get(4);
						if (questId != -1) {
							qRects.get(7).set(4, (int)QuestConst.questsInterface.get(questId).get(0));
							int k = 8;
							String txt;
							for (int i = 0; i < qReqs.get(questId).size(); i+=2, k++) {
								qRects.get(k).set(4, qReqs.get(questId).get(i));
								txt = "X "+String.valueOf(qReqs.get(questId).get(i+1));
								tEff.put(k, new StringEffect(txt ,new Point (qRects.get(k).get(0) + qRects.get(k).get(2) + 10,qRects.get(k).get(1)+30), 2, 0));
							}
							for (int j = k; j < 12; j++) {
								qRects.get(j).set(4, -1);
								if (tEff.containsKey(j)) {
									tEff.remove(j);
								}
							}
						}
						break;
				}
			}
		}
		if (!found) {
			for (int j = 7; j < qRects.size(); j++) {
				qRects.get(j).set(4, -1);
				tEff.remove(j);
			}
		}
		questRects = new HashMap <Integer, List<Integer>>(qRects);
		tabEff =  new HashMap <Integer, MapEffect>(tEff);
	}
	
	public Map<Integer, MapEffect> UpdateQuestsStatus(Map<Integer, List<Integer>> rects, Map<Integer, MapEffect> tEff) {
		Map<Integer, List<Object>> quests = new HashMap <Integer, List<Object>>(QuestConst.questsInterface);
		onGoingQuests = 0;
		int found;
		int freeIndex;
		for (int quest : quests.keySet()) {
			if (((int)quests.get(quest).get(1)) == QuestConst.INPROGRESS) {
				onGoingQuests++;
			}

		}
		pages = (onGoingQuests-1)/5;
		int skipIndexs = 0;
		for (int quest : quests.keySet()) {
			if (((int)quests.get(quest).get(1)) == QuestConst.INPROGRESS) {
				if (skipIndexs < (page*5)) {
					skipIndexs++;
					continue;
				}
				found = CheckAlreadyInList(quest, rects);
				if (found == -1) {
					freeIndex = CheckFreeIndex(rects);
					if (freeIndex != -1) {
						String txt = (String)quests.get(quest).get(3);
						tEff.put(freeIndex, new StringEffect(txt ,new Point (rects.get(freeIndex).get(0) + ((rects.get(freeIndex).get(2)) -(txt.length()*12))/2,rects.get(freeIndex).get(1)+20), 2, 0));
						questRects.get(freeIndex).set(4, quest);
					}
				}
			} else {
				found = CheckAlreadyInList(quest, rects);
				if (found != -1) {
					tEff.remove(found);
					questRects.get(found).set(4, -1);
				}
			}
		}
		
		return new HashMap <Integer, MapEffect>(tEff);
	}
	

	private int CheckAlreadyInList(int questId, Map<Integer, List<Integer>> qRects) {
		for (int i = 0; i < 5; i++) {
			if (qRects.get(i).get(4) == questId) {
				return i;
			}
		}
		return -1;
	}
	private int CheckFreeIndex(Map<Integer, List<Integer>> qRects) {
		for (int i = 0; i < 5; i++) {
			if (qRects.get(i).get(4) == -1) {
				return i;
			}
		}
		return -1;
	}
	
	
	public void LoadButtonImg() {
		InputStream is;
		is = getClass().getResourceAsStream("/inventorybuttons/arrow.png");
		try {
			questButtonImg = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void SaveQuests() {
		Map<Integer, List<Object>> questInter = new HashMap <Integer, List<Object>>(QuestConst.questsInterface);
		FileWriter myWriter;
		try {
	      File file = new File("quest.vla");
	      if (file.createNewFile()) {
	    	  System.out.println("File created: " + file.getName());
	      } else {
	    	  System.out.println("File already exists.");
	      }
	      myWriter = new FileWriter("quest.vla");
	      for (int questKey : questInter.keySet()){
	    	  myWriter.write(String.valueOf(questInter.get(questKey).get(1)));
    		  myWriter.write(System.getProperty( "line.separator" ));
    	  }
	      myWriter.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public static void LoadQuests() {
		BufferedReader reader;
		Map<Integer, List<Object>> questInter = new HashMap <Integer, List<Object>>(QuestConst.questsInterface);
		try {
	      File file = new File("quest.vla");
	      if (file.createNewFile()) {
	    	  System.out.println("File created: " + file.getName());
	      } else {
	    	  System.out.println("File already exists.");
	    	  reader = new BufferedReader(new FileReader("quest.vla"));
	    	  for (int questKey : questInter.keySet()){
	    		  questInter.get(questKey).set(1, Integer.parseInt(reader.readLine()));
	    	  }
		      reader.close();
	      }
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}
