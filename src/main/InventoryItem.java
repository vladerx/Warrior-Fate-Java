package main;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

abstract class InventoryItem {
	public final int Head = 0, TOP = 1, BOTTOM = 2, LEG = 3, ACCESSORY = 4, WEAPON = 5, USEABLE = 6, FREE = 7;
	public int invSlot, amount, objType, invType;
	public ArrayList <Integer> pixels = new ArrayList <Integer>();
	public Rectangle invRect;
	
	public InventoryItem(int Slot, int num, int type, int invtType) {
		ArrayList<InventoryItem> itemsInv = new ArrayList<InventoryItem>(Inventory.invItems);
		invSlot = Slot;
		amount = num;
		objType = type;
		invType = invtType;
		pixels = AuxiliaryCalc.GetImgPixels(Inventory.invImgs.get(type-1001));
		Rectangle SlotRect = new Rectangle (ItemConst.invTypeRects.get(Slot).get(1),ItemConst.invTypeRects.get(Slot).get(2),ItemConst.invTypeRects.get(Slot).get(3),ItemConst.invTypeRects.get(Slot).get(4));
		Dimension imgDim = new Dimension (Inventory.invImgs.get(type-1001).getWidth(), Inventory.invImgs.get(type-1001).getHeight());
		invRect = new Rectangle(SlotRect.x+((SlotRect.width-imgDim.width)/2),SlotRect.y+((SlotRect.height-imgDim.height)/2),imgDim.width,imgDim.height);
		itemsInv.add(this);
		Inventory.invItems = new ArrayList<InventoryItem>(itemsInv);
	}
}
