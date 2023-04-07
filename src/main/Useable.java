package main;

import java.awt.Point;
import java.util.List;

public class Useable extends InventoryItem  {
	
	public int HpRecover, MpRecover;
	public MapEffect amountLabel;
	
	public Useable(int Slot, int type, int num, int invType) {
		super(Slot, num, type, invType);
		ItemConst.itemSlots.get(Slot).set(1, this);
		ItemConst.itemSlots.get(Slot).set(0, objType);
		List<Integer> useProps = ItemConst.useableProps.get(type);
		for (int i = 0; i < useProps.size(); i++) {
			switch (useProps.get(i)){
				case ItemConst.HpRecovery:
					HpRecover = (int)ItemConst.itemProps.get(type).get(2+i);
					break;
				case ItemConst.MpRecovery:
					MpRecover = (int)ItemConst.itemProps.get(type).get(2+i);
					break;
			}
		}
		amountLabel = new StringEffect(String.valueOf(num),new Point(ItemConst.invTypeRects.get(Slot).get(1)+30-(5*String.valueOf(num).length()),ItemConst.invTypeRects.get(Slot).get(2)+37), -1, 0);
	}
}
