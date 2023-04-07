package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.ListIterator;

abstract class MapEffect {
	
	public static MapEffect coinLabel;
	public static int stringIndex = 1;
	
	public static ArrayList<MapEffect> ChangeEffectProps(ArrayList<MapEffect> Effs) {
		StringEffect strEff;
		SkillEffect sklEff;
		ArrayList<MapEffect> mEffs = new ArrayList<MapEffect>(Effs);
		for (ListIterator<MapEffect> iter = mEffs.listIterator(); iter.hasNext();) {
			MapEffect mEff = iter.next();
			if (mEff.getClass() == StringEffect.class) {
				strEff = (StringEffect) mEff;
				strEff.Duration--;
				strEff.point.y -= 15;
				if (strEff.Duration == 0 || strEff.point.y == 0) {
					iter.remove();
				}
			} else if (mEff.getClass() == SkillEffect.class) {
				sklEff = (SkillEffect)mEff;
				sklEff.Duration--;
				if (sklEff.Duration == 0) {
					iter.remove();
				}
			}
		}
		return (new ArrayList<MapEffect>(mEffs));
	}
	
	public static void DrawMapEffects(Graphics g, ArrayList<MapEffect> MapEffs) {
		Font font = new Font("Rockwell", Font.BOLD, 14);
		Graphics2D g2 = (Graphics2D) g;
		Font fontOld = g2.getFont();
		g2.setFont(font);
		StringEffect strEff;
		SkillEffect sklEff;
		int i;
		ArrayList<MapEffect> mEffs = new ArrayList<MapEffect>(MapEffs);
		for (MapEffect mEff : mEffs) {
			if (mEff.getClass() == StringEffect.class) {
				strEff = (StringEffect) mEff;
				switch(strEff.type) {
					case 0:
						g2.setColor(Color.BLACK);
						break;
					case 1:
						g2.setColor(Color.RED);
						break;
					case 2:
						g2.setColor(Color.CYAN);
						break;
					case 3:
						g2.setColor(Color.YELLOW);
						break;
				}
				g2.drawString(strEff.label, strEff.point.x, strEff.point.y);
			} else if (mEff.getClass() == SkillEffect.class) {
				sklEff = (SkillEffect) mEff;
				if (sklEff.Duration % 3 == 0) {
					i = 0;
				} else if (sklEff.Duration % 2 == 0) {
					i = 1;
				} else{
					i = 2;
				}
				sklEff.point.x = Player.cords[0] + ((Player.playerRect.width-SkillConst.skillEffectsSize.get(sklEff.type).get(0))/2);
				sklEff.point.y = Player.cords[1] - SkillConst.skillEffectsSize.get(sklEff.type).get(1) - 5;
				g.drawImage(SkillTab.skillActivesImgs.get(sklEff.type).getSubimage(i*SkillConst.skillEffectsSize.get(sklEff.type).get(0), 0, SkillConst.skillEffectsSize.get(sklEff.type).get(0), SkillConst.skillEffectsSize.get(sklEff.type).get(1)), sklEff.point.x, sklEff.point.y, null);
			}
		}
		g2.setFont(fontOld);
	}
	
	public static void LevelUpEffect() {
		ArrayList<MapEffect> effect = new ArrayList<MapEffect>(Mapa.levelUpEffect);
		ArrayList<MapEffect> toRemove = new ArrayList<MapEffect>();
		ArrayList<MapEffect> toAdd = new ArrayList<MapEffect>();
		String lvlString = "Level Up!!!";
		if (effect.size() != 0) {
			Dimension size = GamePanel.size;
			StringEffect lvlUp;
			if (stringIndex < 12) {
				for(MapEffect mEff : effect) {
					lvlUp = (StringEffect)mEff;
					if (lvlUp.cycle == 1) {
						continue;
					}
					lvlUp.point.y += 10;
					if (lvlUp.point.y >= size.height/2) {
						lvlUp.cycle = 1;
						if (stringIndex < 11) {
							toAdd.add(new StringEffect(String.valueOf(lvlString.charAt(stringIndex)),new Point((size.width-400+(100*stringIndex))/2,0),4, 0));
						}
						stringIndex++;
					}
				}
			} else {
				for(MapEffect mEff : effect) {
					lvlUp = (StringEffect)mEff;
					if (lvlUp.cycle == 2) {
						continue;
					}
					lvlUp.point.y += 10;
					if (lvlUp.point.y >= size.height) {
						lvlUp.cycle = 2;
						toRemove.add(mEff);
					}
				}
			}
		}
		for (MapEffect effKey : toRemove) {
			effect.remove(effKey);
		}
		for (MapEffect effKey : toAdd) {
			effect.add(effKey);
		}
		Mapa.levelUpEffect = new ArrayList<MapEffect>(effect);
	}
}
