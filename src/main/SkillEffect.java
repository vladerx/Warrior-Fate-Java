package main;

import java.awt.Point;

public class SkillEffect extends MapEffect {

	public Point point;
	public int Duration = 10, type;
	
	public SkillEffect(Point pnt, int skillType){
		point = pnt;
		type = skillType;
		Mapa.mapEff.add(this);
	}
}
