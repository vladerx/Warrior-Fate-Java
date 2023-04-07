package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.imageio.ImageIO;

public class SkillTab {
	public static ArrayList<Skill> skillList = new ArrayList<Skill>();
	public static ArrayList<BuffSkill> skillBuffs = new ArrayList<BuffSkill>();
	public static ArrayList<ActiveSkill> skillActives = new ArrayList<ActiveSkill>();
	public static Skill clickedSkill;
	public static Map<Integer, BufferedImage> skillImgs = new HashMap<Integer, BufferedImage>();
	private Map<Integer, ArrayList<BufferedImage>> skillButtonImgs = new HashMap<Integer, ArrayList<BufferedImage>>();
	public static Map<Integer, BufferedImage> skillActivesImgs = new HashMap<Integer, BufferedImage>();
	public static Map<Integer, BufferedImage> skillBuffsImgs = new HashMap<Integer, BufferedImage>();
	public static Map<Integer, MapEffect> mapEff = new HashMap<Integer, MapEffect>();
	public static MapEffect skillPointsString;
	
	public void Draw(Graphics g) {
		Map<Integer, List<Integer>> RectsSkill = new HashMap <Integer, List<Integer>>(SkillConst.skillRects);
		Map<Integer, MapEffect> effMap = new HashMap<Integer, MapEffect>(mapEff);
		StringEffect strEff;
		Graphics2D g2;
		for (int rectKey : RectsSkill.keySet()) {
			if (rectKey > 9) {
				g.drawImage(skillButtonImgs.get(0).get((Player.skillPoints>0) ? 1 :  0), RectsSkill.get(rectKey).get(0), RectsSkill.get(rectKey).get(1), null);
			} else {
				g.drawImage(skillImgs.get(rectKey-4), RectsSkill.get(rectKey).get(0), RectsSkill.get(rectKey).get(1), null);
			}	
		}
		g2 = (Graphics2D) g;
		for (MapEffect mEff : effMap.values()) {
			g2.setColor(Color.BLACK);
			strEff = (StringEffect) mEff;
			g2.drawString(strEff.label, strEff.point.x, strEff.point.y);
		}
		strEff = (StringEffect) skillPointsString;
		g2.drawString(strEff.label, strEff.point.x, strEff.point.y);
	}
	
	public void UpdateSkillTick() {
		ArrayList<BuffSkill> buffs = new ArrayList<BuffSkill>(skillBuffs);
		ArrayList<ActiveSkill> actis = new ArrayList<ActiveSkill>(skillActives);
		BuffSkill buff;
		ActiveSkill acti;
		for (ListIterator<BuffSkill> buffIter = buffs.listIterator(); buffIter.hasNext();) {
			buff  = buffIter.next();
			if (buff.TickBuff()) {
				buffIter.remove();
			}
		}
		for (ListIterator<ActiveSkill> actiIter = actis.listIterator(); actiIter.hasNext();) {
			acti  = actiIter.next();
			if (acti.TickActive()) {
				actiIter.remove();
			}
		}
		skillBuffs =  new ArrayList<BuffSkill>(buffs);
		skillActives = new ArrayList<ActiveSkill>(actis);
	}
	
	public void importImage() {
		InputStream is;
		int i = 0;
		for (String loca : SkillConst.paths) {
			is = getClass().getResourceAsStream(loca);
			try {
				skillImgs.put(i,ImageIO.read(is));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		i = 1;
		ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();
		for (String loca : SkillConst.buttonPaths) {
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
				skillButtonImgs.put((i/2)-1, new ArrayList<BufferedImage>(imgs));
				imgs.clear();
			}
			i++;
		}
		i = 0;
		int j = 0;
		int skillType;
		for (int skillKey : SkillConst.skillProps.keySet()) {
			skillType = SkillConst.skillProps.get(skillKey).get(0);
			if (skillType == SkillConst.BUFF) {
				is = getClass().getResourceAsStream(SkillConst.buffPaths.get(i));
				try {
					skillBuffsImgs.put(skillKey,ImageIO.read(is));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			} else if (skillType == SkillConst.ACTIVE){
				is = getClass().getResourceAsStream(SkillConst.activePaths.get(j));
				try {
					skillActivesImgs.put(skillKey,ImageIO.read(is));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				j++;
			}
		}
	}
	
	public static void SaveSkills() {
		Map<Integer, List<Integer>> propsSkill = new HashMap <Integer, List<Integer>>(SkillConst.skillProps);
		FileWriter myWriter;
		try {
	      File file = new File("skill.vla");
	      if (file.createNewFile()) {
	    	  System.out.println("File created: " + file.getName());
	      } else {
	    	  System.out.println("File already exists.");
	      }
	      myWriter = new FileWriter("skill.vla");
	      for (int i = 0; i < propsSkill.size(); i++){
	    	  myWriter.write(String.valueOf(propsSkill.get(i).get(6)));
    		  myWriter.write(System.getProperty( "line.separator" ));
    	  }
	      myWriter.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public static void LoadSkills() {
		BufferedReader reader;
		String value;
		StringEffect strEff;
		int x = (GamePanel.size.width-500)/2 + 455;
		Map<Integer, List<Integer>> propsSkill = new HashMap <Integer, List<Integer>>(SkillConst.skillProps);
		try {
	      File file = new File("skill.vla");
	      if (file.createNewFile()) {
	    	  System.out.println("File created: " + file.getName());
	      } else {
	    	  System.out.println("File already exists.");
	    	  reader = new BufferedReader(new FileReader("skill.vla"));
	    	  for (int skillKey : propsSkill.keySet()){
	    		  value = reader.readLine();
	    		  propsSkill.get(skillKey).set(6, Integer.parseInt(value));
	    		  ((StringEffect)mapEff.get(skillKey+4)).label = value;
	    	  }
	    	  strEff = (StringEffect)skillPointsString;
	    	  strEff.label = String.valueOf(Player.skillPoints);
	    	  strEff.point.x = x+177-(3*strEff.label.length());
		      reader.close();
	      }
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}
