package main;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class MapItem extends MapObj{

	public int frame = 0, state, dropAdvance = 0;
	public final int IDLE = 0, LOOTED = 1, DROPPING = 2;
	public ArrayList <Integer> pixels = new ArrayList <Integer>();
	
	public MapItem(int key, int type, int cordx, int cordy, int status) {
		super(key, type, cordx, cordy);
		state = status;
		MapConst.objLoca.get(key).set(4, 0);
		pixels = AuxiliaryCalc.GetImgPixels(Mapa.mapItemImgs.get(type));
	}
	
	public boolean MoveToPlayer(Inventory invt) {
		Point playerMid = new Point((Player.playerRect.x + Player.playerRect.width)/2,(Player.playerRect.y + Player.playerRect.height)/2);
		Point itemMid = new Point((this.objRect.x + this.objRect.width - Mapa.mapPoint.x)/2,(this.objRect.y + this.objRect.height - Mapa.mapPoint.y)/2);
		if (state == LOOTED) {
			if (playerMid.x != itemMid.x) {
				this.objRect.x -= (2*(itemMid.x-playerMid.x)/Math.abs(itemMid.x-playerMid.x));
			}
			if (playerMid.y != itemMid.y) {
				this.objRect.y -= (2*(itemMid.y-playerMid.y)/Math.abs(itemMid.y-playerMid.y));
			}
		}
		Rectangle rect = new Rectangle(this.objRect.x-Mapa.mapPoint.x,this.objRect.y-Mapa.mapPoint.y, this.objRect.width,this.objRect.height);
		if (Player.playerRect.intersects(rect)) {
			Mapa.mapObjsList.remove(this);
			Mapa.interMapObj.remove(this);
			Mapa.itemMapObj.remove(this);
			MapConst.objLoca.remove(this.objKey);
			return true;
		}
		return false;
	}
	
	public void CreateNewInventoryItem(int freeSlot, int num) {
		if (this.objType != 1000) {
			if (ItemConst.itemProps.get(this.objType).get(0) == Equip.class) {
				new Equip(freeSlot, this.objType, num, (int)ItemConst.itemProps.get(this.objType).get(1), (int)ItemConst.itemProps.get(this.objType).get(2), (int)ItemConst.itemProps.get(this.objType).get(3) ,(int)ItemConst.itemProps.get(this.objType).get(4),(int)ItemConst.itemProps.get(this.objType).get(5),(int) ItemConst.itemProps.get(objType).get(6));
			} else if (ItemConst.itemProps.get(this.objType).get(0) == Useable.class) {
				new Useable(freeSlot, this.objType, num,(int)ItemConst.itemProps.get(this.objType).get(1));
			} else if (ItemConst.itemProps.get(this.objType).get(0) == ETCItem.class) {
				new ETCItem(freeSlot, this.objType, num,(int)ItemConst.itemProps.get(this.objType).get(1));
			}
		}
	}
	
	public void MoveDroppingItem() {
		dropAdvance += 1;
		if (dropAdvance >= 20 && dropAdvance < 40) {
			objRect.y += 5;
		} else if (dropAdvance < 20) {
			objRect.y -= 5;
		} else if (dropAdvance >= 40) {
			state = 0;
		}
	}
	
}
