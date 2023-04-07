package main;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillConst {
	public static final int ACTIVE = 0, PASSIVE = 1, BUFF= 2, PROJECTILE = 3;
	
	//{skill type,start value, value inc, mpcon, mpcon inc, max level, current level, cd, duration}
	final public static Map<Integer, List<Integer>> skillProps = new HashMap <Integer, List<Integer>>() {{
		put(0, Arrays.asList(BUFF, 2, 1, 5, 2, 10, 0, 40, 20)); // hp recovery
		put(1, Arrays.asList(BUFF, 1, 1, 6, 3, 20, 0, 70, 50)); // attack up 
		put(2, Arrays.asList(PROJECTILE, 30, 10, 10, 2, 20, 0, 0, 0)); // throw shuriken
		put(3, Arrays.asList(BUFF, 2, 1, 5, 2, 10, 0, 60, 50)); // defense
		put(4, Arrays.asList(ACTIVE, 80, 3, 5, 3, 20, 0, 10, 0)); // power strike 
		put(5, Arrays.asList(PASSIVE, 2, 2, 0, 0, 10, 0, 0, 0)); // accuracy up
	}};
	
	final public static Map<Integer, List<Object>> skillSlots = new HashMap <Integer, List<Object>>() {{
		put(0, Arrays.asList(-1, null));// {skill type, skill}
		put(1, Arrays.asList(-1, null));
		put(2, Arrays.asList(-1, null));
		put(3, Arrays.asList(-1, null));
	}};
	final public static Map<Integer, List<Integer>> skillEffectsSize = new HashMap <Integer, List<Integer>>() {{
		put(4, Arrays.asList(15, 31));// {skill type, skill}
	}};
	
	public static Map<Integer, List<Integer>> skillRects = new HashMap <Integer, List<Integer>>();
	final public static List<String> buttonPaths = Arrays.asList("/skillbuttons/noskilllevelup.png","/skillbuttons/skilllevelup.png");
	final public static List<String> paths = Arrays.asList("/skills/0.png","/skills/1.png","/skills/2.png","/skills/3.png","/skills/4.png","/skills/5.png");
	final public static List<String> buffPaths = Arrays.asList("/skillbuffs/0.png","/skillbuffs/1.png","/skillbuffs/3.png");
	final public static List<String> activePaths = Arrays.asList("/skillactives/4.png");
	
	public static void InitializeSkillTabRects() {
		String txt = "";
		Dimension size = GamePanel.size;
		int x = (size.width-500)/2 + 505 ,y = size.height-80;
		skillRects.put(0, Arrays.asList(x+22,y+15,50,50));
		txt = "A";
		Mapa.skillEff.put(0, (MapEffect)new StringEffect (txt,new Point(x+22+1+(String.valueOf(txt).length()),y+29),2, 0));
		skillRects.put(1, Arrays.asList(x+76,y+15,50,50));
		txt = "S";
		Mapa.skillEff.put(1, (MapEffect)new StringEffect (txt,new Point(x+76+1+(String.valueOf(txt).length()),y+29),2, 0));
		skillRects.put(2, Arrays.asList(x+130,y+15,50,50));
		txt = "D";
		Mapa.skillEff.put(2, (MapEffect)new StringEffect (txt,new Point(x+130+1+(String.valueOf(txt).length()),y+29),2, 0));
		skillRects.put(3, Arrays.asList(x+184,y+15,50,50));
		txt = "F";
		Mapa.skillEff.put(3, (MapEffect)new StringEffect (txt,new Point(x+184+1+(String.valueOf(txt).length()),y+29),2, 0));
		
		x = (size.width-500)/2 + 455;
		y = size.height-188;
		skillRects.put(4, Arrays.asList(x+18,y+17,50,50));
		txt = String.valueOf(skillProps.get(0).get(6));
		SkillTab.mapEff.put(4, (MapEffect)new StringEffect (txt,new Point(x+19+(String.valueOf(txt).length()),y+29),2, 0));
		skillRects.put(5, Arrays.asList(x+72,y+17,50,50));
		txt = String.valueOf(skillProps.get(1).get(6));
		SkillTab.mapEff.put(5, (MapEffect)new StringEffect (txt,new Point(x+73+(String.valueOf(txt).length()),y+29),2, 0));
		skillRects.put(6, Arrays.asList(x+126,y+17,50,50));
		txt = String.valueOf(skillProps.get(2).get(6));
		SkillTab.mapEff.put(6, (MapEffect)new StringEffect (txt,new Point(x+127+(String.valueOf(txt).length()),y+29),2, 0));
		skillRects.put(7, Arrays.asList(x+180,y+17,50,50));
		txt = String.valueOf(skillProps.get(3).get(6));
		SkillTab.mapEff.put(7, (MapEffect)new StringEffect (txt,new Point(x+181+(String.valueOf(txt).length()),y+29),2, 0));
		skillRects.put(8, Arrays.asList(x+234,y+17,50,50));
		txt = String.valueOf(skillProps.get(4).get(6));
		SkillTab.mapEff.put(8, (MapEffect)new StringEffect (txt,new Point(x+235+(String.valueOf(txt).length()),y+29),2, 0));
		skillRects.put(9, Arrays.asList(x+288,y+17,50,50));
		txt = String.valueOf(skillProps.get(5).get(6));
		SkillTab.mapEff.put(9, (MapEffect)new StringEffect (txt,new Point(x+289+(String.valueOf(txt).length()),y+29),2, 0));
		
		skillRects.put(10, Arrays.asList(x+38,y+7,10,10,0));
		skillRects.put(11, Arrays.asList(x+92,y+7,10,10,0));
		skillRects.put(12, Arrays.asList(x+146,y+7,10,10,0));
		skillRects.put(13, Arrays.asList(x+200,y+7,10,10,0));
		skillRects.put(14, Arrays.asList(x+254,y+7,10,10,0));
		skillRects.put(15, Arrays.asList(x+308,y+7,10,10,0));
		SkillTab.skillPointsString = (MapEffect)new StringEffect ("0",new Point(x+177-(3*"0".length()),y+88),2, 0);
	}
}
