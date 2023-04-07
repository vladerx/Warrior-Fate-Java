package main;

public class BuffSkill extends Skill{
	public int cooldown = 0, duration, active = 0, tick = 160;
	
	public BuffSkill(int Slot, int type) {
		super(Slot,type);
		duration = SkillConst.skillProps.get(type).get(8);
	}
	
	public Boolean TickBuff() {
		if (tick % 40 == 0) {
			cooldown--;
			if (active == 1) {
				if (duration != 0) {
					switch (objType) {
						case 0:
							Player.ChangePlayerHP(value);
							if (duration == SkillConst.skillProps.get(objType).get(8)) {
								Player.ChangePlayerMP(-mpcon);
							}
							break;
						case 1:
							if (duration == SkillConst.skillProps.get(objType).get(8)) {
								Player.addWeaponAttack += value;
								Player.ChangePlayerMP(-mpcon);
							}
							break;
						case 3:
							if (duration == SkillConst.skillProps.get(objType).get(8)) {
								Player.addDefense += value;
								Player.ChangePlayerMP(-mpcon);
							}
							break;
					}
					duration--;
				} else if (duration == 0) {
					active = 0;
					switch (objType) {
					case 1:
						Player.addWeaponAttack -= value;
						break;
					case 3:
						Player.addDefense -= value;	
						break;
					}
				}
			}
			if (duration == 0 && cooldown == 0) {
				duration = SkillConst.skillProps.get(objType).get(8);
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
