package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class NPCDialogue {

	public static ArrayList<BufferedImage> dialogueImgs = new ArrayList<BufferedImage>();
	public static BufferedImage npcPic;
	public static int questTarget = -1;
	public static Map<Integer, List<Integer>> dialogueRects = new HashMap <Integer, List<Integer>>();
	final public List<String> imgsPaths = Arrays.asList("/dialogue/chat.png","/dialogue/buttonaccpt.png","/dialogue/buttonaccptchng.png","/dialogue/buttonclose.png","/dialogue/buttonclosechng.png");
	
	public NPCDialogue() {
		int x = (GamePanel.size.width-400)/2 ,y = (GamePanel.size.height-400)/2;
		dialogueRects.put(0, Arrays.asList(x+159,y+20,80,75));
		dialogueRects.put(1, Arrays.asList(x+89,y+320,70,40,0));
		dialogueRects.put(2, Arrays.asList(x+229,y+320,70,40,0));
	}
	
	public void Draw(Graphics g) {
		int x = (GamePanel.size.width-400)/2 ,y = (GamePanel.size.height-400)/2 + 125;
		Map<Integer, List<Integer>> RectsSkill = new HashMap <Integer, List<Integer>>(dialogueRects);
		Map<Integer, List<Object>> quests = new HashMap <Integer, List<Object>>(QuestConst.questsInterface);
		g.drawImage(dialogueImgs.get(0), (GamePanel.size.width-400)/2, (GamePanel.size.height-400)/2, null);	
		g.drawImage(npcPic, RectsSkill.get(0).get(0)+((80-npcPic.getWidth())/2), RectsSkill.get(0).get(1)+((75-npcPic.getHeight())/2), null);
		int k = 1;
		for (int i  = 1; i< RectsSkill.size(); i++, k += 2) {
			g.drawImage(dialogueImgs.get(k+RectsSkill.get(i).get(4)), RectsSkill.get(i).get(0), RectsSkill.get(i).get(1), null);	
		}
		Graphics2D g2 = (Graphics2D) g;
		Font oldFont = g2.getFont();
		g2.setColor(Color.BLACK);
		if (questTarget != -1) {
			int questStatus = ((int)quests.get(questTarget).get(1));
			if (questStatus == QuestConst.COMPLETED) {
				y += 30;
				g2.setFont(new Font("Rockwell", Font.BOLD, 15));
				g2.drawString("Quest Completed! Here is Your Reward", x+50, y);
			} else if (questStatus == QuestConst.NOTSTARTED) {
				int fontSize = 20;
				g2.setFont(new Font("Rockwell", Font.BOLD, fontSize));
				String txt = (String)quests.get(questTarget).get(3);
				g2.drawString(txt, x+((350-(txt.length()*(fontSize-10)))/2), y);
				fontSize = 15;
				y += 30;
				g2.setFont(new Font("Rockwell", Font.BOLD, fontSize));
				for (int i = 4; i < quests.get(questTarget).size();i++, y += 20) {
					g2.drawString((String)quests.get(questTarget).get(i), x+50, y);
				}
			}
		}
		g2.setFont(oldFont);

	}
	
	public static int UpdateNPCDialogue() {
		Map<Integer, List<Object>> quests = new HashMap <Integer, List<Object>>(QuestConst.questsInterface);
		Map<Integer, List<Integer>> Reqs = new HashMap <Integer, List<Integer>>(QuestConst.questReqs);
		for (int questId : quests.keySet()) {
			 if (((int)quests.get(questId).get(0)) == ((NPC)Mapa.clickedObj).objType && ((int)quests.get(questId).get(1)) == QuestConst.INPROGRESS) {
				 if (MapHelper.CheckSlotsItems(Reqs.get(questId))) {
					 quests.get(questId).set(1, QuestConst.COMPLETED);
					 questTarget = questId;
					 return questTarget;
				 }
			} else if (((int)quests.get(questId).get(0)) == ((NPC)Mapa.clickedObj).objType && ((int)quests.get(questId).get(1)) == QuestConst.NOTSTARTED && ((int)quests.get(questId).get(2)) <= Player.level) {
				questTarget = questId;
				return questTarget;
			}
		}
		return questTarget;
	}
	
	public void UpdateImgPosition (){
		Map<Integer, List<Integer>> RectsSkill = new HashMap <Integer, List<Integer>>(dialogueRects);
		Rectangle rect = new Rectangle();
		for (int i = 1; i < RectsSkill.size(); i++) {
			rect.x = RectsSkill.get(i).get(0);
			rect.y = RectsSkill.get(i).get(1);
			rect.width = RectsSkill.get(i).get(2);
			rect.height = RectsSkill.get(i).get(3);
			if (rect.contains(MouseInputs.mousePoint)) {
				RectsSkill.get(i).set(4, 1);
			} else {
				RectsSkill.get(i).set(4, 0);
			}
		}
		dialogueRects = new HashMap <Integer, List<Integer>>(RectsSkill);
	}
	
	public void importImage() {
		InputStream is;
		for (String loca : imgsPaths) {
			is = getClass().getResourceAsStream(loca);
			try {
				dialogueImgs.add(ImageIO.read(is));
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
	}
}
