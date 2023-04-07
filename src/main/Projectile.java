package main;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Projectile {
	public double[] direction;
	public double xCord, yCord;
	public ProjectileSkill projectileSkill;
	public int objType, width, height;
	
	public Projectile(Rectangle rectangle, double[] dirc, ProjectileSkill projSkill, int type) {
		direction = dirc;
		projectileSkill = projSkill;
		objType = type;
		xCord = rectangle.x;
		yCord = rectangle.y;
		width = rectangle.width;
		height = rectangle.height;
	}
	
	public boolean MoveProjectile(ArrayList<Enemy> enemies) {
		xCord += (3*direction[0]);
		yCord += (3*direction[1]);
		Rectangle rect = new Rectangle((int)xCord, (int)yCord, width,height);
		double damage = ((double)projectileSkill.value)*(1+((double)(Player.baseAgility+Player.addAgility)+((double)(Player.baseStrength+Player.addStrength)/2))/100);
		Dimension mapSize = GamePanel.size;
		if (xCord <= Mapa.mapPoint.x || xCord >= Mapa.mapPoint.x+mapSize.width || yCord <= Mapa.mapPoint.y || yCord >= Mapa.mapPoint.y+mapSize.height) {
			return true;
		}
		for (Enemy enem : enemies) {
			if (enem.objRect.intersects(rect)) {
				enem.ChangeHp((int)damage, 1);
				return true;
			}
		}
		return false;
	}
	
	public boolean MoveProjectile() {
		xCord += direction[0];
		yCord += direction[1];
		Rectangle rect = new Rectangle((int)xCord-Mapa.mapPoint.x, (int)yCord-Mapa.mapPoint.y, width,height);
		int damage = MapConst.objProps.get(objType).get(2);
		Dimension mapSize = GamePanel.size;
		if (xCord <= Mapa.mapPoint.x || xCord >= Mapa.mapPoint.x+mapSize.width || yCord <= Mapa.mapPoint.y || yCord >= Mapa.mapPoint.y+mapSize.height) {
			return true;
		}
		if (Player.playerRect.intersects(rect)) {
			Player.ChangePlayerHP(-damage);
			Player.rangedUnderAtt = true;
			Player.rangedAttCounter = 50;
			return true;
		}
		return false;
	}
	
}
