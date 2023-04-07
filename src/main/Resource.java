package main;

import java.util.Arrays;

public class Resource extends MapObj {
		public int frame = 0, HP, resourceType, state = 0, frameBound = 0;
		public final int IDLE = 0, DEAD = 4;
		
		public Resource(int key, int type, int cordx, int cordy) {
			super(key, type, cordx, cordy);
			resourceType = MapConst.objProps.get(type).get(2);
			frameBound = MapConst.objProps.get(type).get(1);
			HP = MapConst.objProps.get(type).get(3);
		}
		
		public void CheckFrameBound() {
			if (state == DEAD) {
				frameBound -= 5;
				if (frameBound < 6) {
					Mapa.mapObjsList.remove(this);
					Mapa.interMapObj.remove(this);
					MapConst.objLoca.remove(this.objKey);
				}
			}
		}
		
		public void ChangeHp(int change) {
			HP -= change;
			if (HP <= 0) {
				state = DEAD;
				Mapa.clickedObj = null;
				Player.mapObjInter = Player.IDLE;
				Player.playerCollideObjType = Player.IDLE;
				int dropKey = AuxiliaryCalc.GetFreeMapObjectKey(MapConst.objLoca);
				int dropType = AuxiliaryCalc.GetDropId(objType);
				int amount = 1;
				if (objType == 23) {
					amount = 2;
				}
				
				MapConst.objLoca.put(dropKey, Arrays.asList(dropType, objRect.x, objRect.y, amount, 2));//{key, {item type, xcord, ycord, amount, dropping}}
			}
		}
}
