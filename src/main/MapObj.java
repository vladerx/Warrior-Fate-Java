package main;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapObj {
	public int objType, objKey;  // (type)
	public Rectangle objRect;

	public  MapObj (int key ,int type, int cordx, int cordy) {
		objRect = new Rectangle(cordx, cordy , MapConst.objProps.get(type).get(0), MapConst.objProps.get(type).get(1));
		objKey = key;
		objType = type;
	}
	
}
