package main;

import java.awt.Point;
import java.util.List;

public class ETCItem extends InventoryItem {
	public MapEffect amountLabel;
	
	public ETCItem(int Slot, int type, int num, int invtType) {
		super(Slot, num, type, invtType);
		ItemConst.itemSlots.get(Slot).set(1, this);
		ItemConst.itemSlots.get(Slot).set(0, objType);
		amountLabel = new StringEffect(String.valueOf(num),new Point(ItemConst.invTypeRects.get(Slot).get(1)+30-(5*String.valueOf(num).length()),ItemConst.invTypeRects.get(Slot).get(2)+37), -1, 0);
	}
	
}
