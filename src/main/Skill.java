package main;

abstract class Skill {
	public int skillSlot, objType, level, maxlevel, startValue, incValue, mpcon, mpconInc,
	value;
	
	public Skill(int Slot, int type) {
		skillSlot = Slot;
		objType  = type;
		startValue = SkillConst.skillProps.get(type).get(1);
		incValue = SkillConst.skillProps.get(type).get(2);
		mpcon = SkillConst.skillProps.get(type).get(3);
		mpconInc = SkillConst.skillProps.get(type).get(4);
		maxlevel = SkillConst.skillProps.get(type).get(5);
		level = SkillConst.skillProps.get(type).get(6);
		mpcon += (mpconInc*level);
		value = startValue+(incValue*level);
	}
	
}
