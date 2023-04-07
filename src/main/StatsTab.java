package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class StatsTab {
	public static Map<Integer, List<Integer>> statsRects = new HashMap <Integer, List<Integer>>();
	public static Map<Integer, BufferedImage> statsImgs = new HashMap<Integer, BufferedImage>();
	public Map<Integer, ArrayList<BufferedImage>> ButtonStatsImgs = new HashMap<Integer, ArrayList<BufferedImage>>();
	final public List<String> buttonPaths = Arrays.asList("/statsimgs/nostatsbutton.png","/statsimgs/statsbutton.png");
	public static Map<Integer, MapEffect> mapEff = new HashMap<Integer, MapEffect>();
	
	public StatsTab() {
		Dimension size = GamePanel.size;
		int x = (size.width-200)/2 ,y = size.height-574+187;
		statsRects.put(0, Arrays.asList(x+29,y+21,10,10));
		statsRects.put(1, Arrays.asList(x+29,y+41,10,10));
		statsRects.put(2, Arrays.asList(x+29,y+62,10,10));
		statsRects.put(3, Arrays.asList(x+29,y+83,10,10));
		mapEff.put(0, new StringEffect(String.valueOf(Player.level), new Point((size.width-200)/2 + 110,size.height-454) , 2, 0)) ;
		mapEff.put(1, new StringEffect(String.valueOf(Player.Hp)+"/"+String.valueOf(Player.maxHp), new Point((size.width-200)/2 + 75,size.height-430) , 2, 0)) ;
		mapEff.put(2, new StringEffect(String.valueOf(Player.Mp)+"/"+String.valueOf(Player.maxMp), new Point((size.width-200)/2 + 75,size.height-406) , 2, 0)) ;
		mapEff.put(3, new StringEffect(String.valueOf(Player.Exp)+"/"+String.valueOf(Player.lvlUpExp), new Point((size.width-200)/2 + 80,size.height-383) , 2, 0)) ;
		mapEff.put(4, new StringEffect(String.valueOf(Player.baseStrength)+'+'+String.valueOf(Player.addStrength), new Point((size.width-200)/2 + 123,size.height-357) , 2, 0)) ;
		mapEff.put(5, new StringEffect(String.valueOf(Player.baseDefense)+'+'+String.valueOf(Player.addDefense), new Point((size.width-200)/2 + 110,size.height-336) , 2, 0)) ;
		mapEff.put(6, new StringEffect(String.valueOf(Player.baseAgility)+'+'+String.valueOf(Player.addAgility), new Point((size.width-200)/2 + 108,size.height-315) , 2, 0)) ;
		mapEff.put(7, new StringEffect(String.valueOf(Player.baseLuck)+'+'+String.valueOf(Player.addLuck), new Point((size.width-200)/2 + 90,size.height-294) , 2, 0)) ;
		mapEff.put(8, new StringEffect(String.valueOf(Player.ablilityPoints), new Point((size.width-200)/2 + 96,size.height-249) , 2, 0)) ;
	}
	
	public void UpdateStatsTab() {
		UpdatePlayerStatsStrings();
	}
	
	public void Draw(Graphics g) {
		Color color = new Color(78,29,162);
		Font font = new Font("Showcard Gothic", Font.BOLD, 13);
		Map<Integer, List<Integer>> RectsSkill = new HashMap <Integer, List<Integer>>(statsRects);
		Map<Integer, MapEffect> effMap = new HashMap<Integer, MapEffect>(mapEff);
		StringEffect strEff;
		Graphics2D g2;
		for (int rectKey : RectsSkill.keySet()) {
			g.drawImage(ButtonStatsImgs.get(0).get((Player.ablilityPoints > 0) ? 1 :  0), RectsSkill.get(rectKey).get(0), RectsSkill.get(rectKey).get(1), null);	
		}
		g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.setFont(font);
		for (MapEffect mEff : effMap.values()) {
			strEff = (StringEffect) mEff;
			if (effMap.get(8) == mEff) {
				g2.setColor(Color.BLACK);
			}
			g2.drawString(strEff.label, strEff.point.x, strEff.point.y);
		}

	}
	private void UpdatePlayerStatsStrings() {
		Map<Integer, MapEffect> effMap = new HashMap<Integer, MapEffect>(mapEff);
		Dimension size = GamePanel.size;
		int x = (size.width-200)/2 ,y = size.height-574+187;
		StringEffect strEeff;
		for (int effKey : effMap.keySet()) {
			switch (effKey) {
				case 0:
					strEeff = (StringEffect)effMap.get(effKey);
					strEeff.label = String.valueOf(Player.level);
					strEeff.point.x = (size.width-200)/2 + 110;
					break;
				case 1:
					strEeff = (StringEffect)effMap.get(effKey);
					strEeff.label = String.valueOf(Player.Hp)+"/"+String.valueOf(Player.maxHp);
					strEeff.point.x = (size.width-200)/2 + 75;
					break;
				case 2:
					strEeff = (StringEffect)effMap.get(effKey);
					strEeff.label = String.valueOf(Player.Mp)+"/"+String.valueOf(Player.maxMp);
					strEeff.point.x = (size.width-200)/2 + 75;
					break;
				case 3:
					strEeff = (StringEffect)effMap.get(effKey);
					strEeff.label = String.valueOf(Player.Exp)+"/"+String.valueOf(Player.lvlUpExp);
					strEeff.point.x = (size.width-200)/2 + 80;
					break;
				case 4:
					strEeff = (StringEffect)effMap.get(effKey);
					strEeff.label = String.valueOf(Player.baseStrength)+'+'+String.valueOf(Player.addStrength);
					strEeff.point.x = (size.width-200)/2 + 123;
					break;
				case 5:
					strEeff = (StringEffect)effMap.get(effKey);
					strEeff.label = String.valueOf(Player.baseDefense)+'+'+String.valueOf(Player.addDefense);
					strEeff.point.x = (size.width-200)/2 + 110;
					break;
				case 6:
					strEeff = (StringEffect)effMap.get(effKey);
					strEeff.label = String.valueOf(Player.baseAgility)+'+'+String.valueOf(Player.addAgility);
					strEeff.point.x = (size.width-200)/2 + 108;
					break;
				case 7:
					strEeff = (StringEffect)effMap.get(effKey);
					strEeff.label = String.valueOf(Player.baseLuck)+'+'+String.valueOf(Player.addLuck);
					strEeff.point.x = (size.width-200)/2 + 90;
					break;
				case 8:
					strEeff = (StringEffect)effMap.get(effKey);
					strEeff.label = String.valueOf(Player.ablilityPoints);
					strEeff.point.x = (size.width-200)/2 + 96;
					break;
			}
		}
		
	}
	
	public void importImage() {
		InputStream is;
		int i = 1;
		ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();
		for (String loca : buttonPaths) {
			is = getClass().getResourceAsStream(loca);
			try {
				imgs.add(ImageIO.read(is));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i % 2 == 0) {
				ButtonStatsImgs.put((i/2)-1, new ArrayList<BufferedImage>(imgs));
				imgs.clear();
			}
			i++;
		} 
	}
}
