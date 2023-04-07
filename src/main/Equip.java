package main;

public class Equip extends InventoryItem {
	
	public int attack, defense, agility, luck, upgradeSlots;
	
	public Equip(int Slot, int objType, int num, int equipType, int attk, int defs, int agil, int lck, int  upgSlots) {
		super(Slot, num, objType, equipType);
		ItemConst.itemSlots.get(Slot).set(1, this);
		ItemConst.itemSlots.get(Slot).set(0, objType);
		upgradeSlots = upgSlots;
		attack = attk;
		defense = defs;
		agility = agil;
		luck = lck;
	}
	
}
