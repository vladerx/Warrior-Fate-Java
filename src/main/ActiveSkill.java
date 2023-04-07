package main;

import java.awt.Point;

public class ActiveSkill extends Skill{
	public int cooldown = 0, tick = 160;
	
	public ActiveSkill(int Slot, int type) {
		super(Slot,type);
	}
	
	public Boolean TickActive() {
		if (tick % 40 == 0) {
			if (cooldown == SkillConst.skillProps.get(objType).get(7)) {
				switch (objType) {
					case 4:
						if (Player.attTarget == null) {
							cooldown = 0;
							return true;
						}
						Player.ChangePlayerMP(-mpcon);
						Player.DamageEnemy(true, value);
						new SkillEffect(new Point(0,0), 4);
						break;
				}
			}
			cooldown--;
			if (cooldown == 0) {
				return true;
			}
		}
		tick--;
		if (tick == 0) {
			tick = 160;
		}
		return false;
	}
}
