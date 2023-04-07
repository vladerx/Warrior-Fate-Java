package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemConst {
	public static final int Head = 0, TOP = 1, BOTTOM = 2, LEG = 3, ACCESSORY = 4, WEAPON = 5, USEABLE = 6, ETC = 7, FREE = 8,
			TRASH = 9, UPGRADER = 10, ACTION = 11, HpRecovery = 12, MpRecovery = 13;
	
	// {class type, slot type, att, defense, agility, luck, slots, req level, req strength, req defense, req agility, req luck}
	final public static Map<Integer, List<Object>> itemProps = new HashMap <Integer, List<Object>>() {{
		put(1001, Arrays.asList(Equip.class, WEAPON, 6, 0, 0, 0, 5, 2, 0, 0, 0, 0)); 
		put(1002, Arrays.asList(Equip.class, WEAPON, 4, 0, 0, 0, 5, 1, 0, 0, 0, 0));
		put(1003, Arrays.asList(Useable.class, USEABLE, 30));
		put(1004, Arrays.asList(ETCItem.class, ETC));
		put(1005, Arrays.asList(Useable.class, USEABLE, 30));
		put(1006, Arrays.asList(Equip.class, TOP, 0, 5, 0, 0, 5, 0, 0, 0, 0, 0));
		put(1007, Arrays.asList(Equip.class, BOTTOM, 0, 4, 0, 0, 5, 2, 0, 0, 0, 0));
		put(1008, Arrays.asList(ETCItem.class, ETC));
		put(1009, Arrays.asList(ETCItem.class, ETC));
		put(1010, Arrays.asList(Equip.class, ACCESSORY, 5, 5, 0, 0, 5, 0, 0, 0, 0, 0));
		put(1011, Arrays.asList(Equip.class, BOTTOM, 0, 8, 0, 0, 5, 0, 0, 0, 0, 0));
		put(1012, Arrays.asList(ETCItem.class, ETC));
		put(1013, Arrays.asList(ETCItem.class, ETC));
		put(1014, Arrays.asList(ETCItem.class, ETC));
		put(1015, Arrays.asList(ETCItem.class, ETC));
		put(1016, Arrays.asList(ETCItem.class, ETC));
		put(1017, Arrays.asList(ETCItem.class, ETC));
		put(1018, Arrays.asList(ETCItem.class, ETC));
		put(1019, Arrays.asList(ETCItem.class, ETC));
	}};
	
	final public static Map<Integer, List<Integer>> useableProps = new HashMap <Integer, List<Integer>>() {{
		put(1003, Arrays.asList(HpRecovery));
		put(1005, Arrays.asList(MpRecovery));
	}};
	
	final public static Map<Integer, List<Integer>> upgradeReqs = new HashMap <Integer, List<Integer>>() {{
		put(1001, Arrays.asList(1000, 100, 1008, 3, 1009, 2, 1004, 5));
		put(1002, Arrays.asList(1000, 80, 1008, 2, 1009, 2, 1004, 4));
		put(1006, Arrays.asList(1000, 120, 1008, 3, 1009, 3));
		put(1007, Arrays.asList(1000, 120, 1008, 3, 1009, 3));
		put(1010, Arrays.asList(1000, 200, 1008, 3, 1009, 3, 1018 ,2));//add gold ore
		put(1011, Arrays.asList(1000, 150, 1008, 4, 1009, 4, 1018 ,1));
	}};
	
	final public static List<String> buttonPaths = Arrays.asList("/inventorybuttons/trash.png","/inventorybuttons/trashopen.png","/inventorybuttons/molletleft.png","/inventorybuttons/molletright.png");
	final public static List<String> paths = Arrays.asList("/items/1001.png","/items/1002.png","/items/1003.png","/items/1004.png","/items/1005.png","/items/1006.png","/items/1007.png"
			,"/items/1008.png","/items/1009.png","/items/1010.png","/items/1011.png","/items/1012.png","/items/1013.png","/items/1014.png","/items/1015.png","/items/1016.png","/items/1017.png"
			,"/items/1018.png","/items/1019.png");
	
	
	public static Map<Integer, List<Object>> itemSlots = new HashMap <Integer, List<Object>>() {{
		put(0, Arrays.asList(0, null));// {item type, InventoryItem}}
		put(1, Arrays.asList(0, null));// {item type, InventoryItem}
		put(2, Arrays.asList(0, null));// {item type, InventoryItem}
		put(3, Arrays.asList(0, null));// {item type, InventoryItem}
		put(4, Arrays.asList(0, null));// {item type, InventoryItem}
		put(5, Arrays.asList(0, null));// {item type, InventoryItem}
		put(6, Arrays.asList(0, null));// {item type, InventoryItem}
		put(7, Arrays.asList(0, null));// {item type, InventoryItem}
		put(8, Arrays.asList(0, null));// {item type, InventoryItem}
		put(9, Arrays.asList(0, null));// {item type, InventoryItem}
		put(10, Arrays.asList(0, null));// {item type, InventoryItem}
		put(11, Arrays.asList(0, null));// {item type, InventoryItem}
		put(12, Arrays.asList(0, null));// {item type, InventoryItem}
		put(13, Arrays.asList(0, null));// {item type, InventoryItem}
		put(14, Arrays.asList(0, null));// {item type, InventoryItem}
		put(15, Arrays.asList(0, null));// {item type, InventoryItem}
		put(16, Arrays.asList(0, null));// {item type, InventoryItem}
		put(17, Arrays.asList(0, null));// {item type, InventoryItem}
		put(18, Arrays.asList(0, null));// {item type, InventoryItem}
		put(19, Arrays.asList(0, null));// {item type, InventoryItem}
		put(20, Arrays.asList(0, null));// {item type, InventoryItem}
		put(21, Arrays.asList(0, null));// {item type, InventoryItem}
		put(22, Arrays.asList(0, null));// {item type, InventoryItem}
		put(23, Arrays.asList(0, null));// {item type, InventoryItem}
		put(24, Arrays.asList(0, null));// {item type, InventoryItem}
		put(25, Arrays.asList(0, null));// {item type, InventoryItem}
		put(26, Arrays.asList(0, null));// {item type, InventoryItem}
		put(27, Arrays.asList(0, null));// {item type, InventoryItem}
		put(28, Arrays.asList(0, null));// {item type, InventoryItem}
		put(29, Arrays.asList(0, null));// {item type, InventoryItem}
		put(30, Arrays.asList(0, null));// {item type, InventoryItem}
		put(31, Arrays.asList(0, null));// {item type, InventoryItem}
		put(32, Arrays.asList(0, null));// {item type, InventoryItem}
		put(33, Arrays.asList(0, null));// {item type, InventoryItem}
		put(34, Arrays.asList(0, null));// {item type, InventoryItem}
		put(35, Arrays.asList(0, null));// {item type, InventoryItem}
		put(36, Arrays.asList(0, null));// {item type, InventoryItem}
		put(37, Arrays.asList(0, null));// {item type, InventoryItem}
		put(38, Arrays.asList(0, null));// {item type, InventoryItem}
		put(39, Arrays.asList(0, null));// {item type, InventoryItem}
		put(40, Arrays.asList(0, null));// {item type, InventoryItem}
		put(41, Arrays.asList(0, null));// {item type, InventoryItem}
		put(42, Arrays.asList(0, null));// {item type, InventoryItem}
		put(43, Arrays.asList(0, null));// {item type, InventoryItem}
		put(44, Arrays.asList(0, null));// {item type, InventoryItem}
		put(45, Arrays.asList(0, null));// {item type, InventoryItem}
		put(46, Arrays.asList(0, null));// {item type, InventoryItem}
		put(47, Arrays.asList(0, null));// {item type, InventoryItem}
		put(48, Arrays.asList(0, null));// {item type, InventoryItem}
		put(49, Arrays.asList(0, null));// {item type, InventoryItem}
		put(50, Arrays.asList(0, null));// {item type, InventoryItem}
		put(51, Arrays.asList(0, null));// {item type, InventoryItem}
		put(52, Arrays.asList(0, null));// {item type, InventoryItem}
		put(53, Arrays.asList(0, null));// {item type, InventoryItem}
		put(54, Arrays.asList(0, null));// {item type, InventoryItem}
		put(55, Arrays.asList(0, null));// {item type, InventoryItem}
		put(56, Arrays.asList(0, null));// {item type, InventoryItem}
		put(57, Arrays.asList(0, null));// {item type, InventoryItem}
		put(58, Arrays.asList(0, null));// {item type, InventoryItem}
		put(59, Arrays.asList(0, null));// {item type, InventoryItem}
		put(60, Arrays.asList(0, null));// {item type, InventoryItem}
		put(61, Arrays.asList(0, null));// {item type, InventoryItem}
		put(62, Arrays.asList(0, null));// {item type, InventoryItem}
		put(63, Arrays.asList(0, null));// {item type, InventoryItem}
		put(64, Arrays.asList(0, null));// {item type, InventoryItem}
		put(65, Arrays.asList(0, null));// {item type, InventoryItem}
		put(66, Arrays.asList(0, null));// {item type, InventoryItem}
	}};
	
public static Map<Integer, List<Integer>> invTypeRects = new HashMap <Integer, List<Integer>>();
	
	public static void InitializeInventoryRects() {
		int x = (GamePanel.size.width-200)/2, y = GamePanel.size.height-531;
		invTypeRects.put(0, Arrays.asList(Head,x+72,y+15,54,53));
		invTypeRects.put(1, Arrays.asList(TOP,x+72,y+70,54,53));
		invTypeRects.put(2, Arrays.asList(BOTTOM,x+72,y+125,54,53));
		invTypeRects.put(3, Arrays.asList(LEG,x+72,y+180,54,53));//Armour equips
		invTypeRects.put(4, Arrays.asList(ACCESSORY,x+12,y+107,54,53));//left hand equip
		invTypeRects.put(5, Arrays.asList(WEAPON,x+130,y+107,54,53));//right hand equip
		
		x = (GamePanel.size.width-500)/2 + 22; //inventory
		y = GamePanel.size.height-243;
		int z = 6;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
				invTypeRects.put(z++, Arrays.asList(FREE,x+(38*j),y+(41*i),38,41));//6-53
			}
		}
		x = (GamePanel.size.width-500)/2 + 21; //useable
		y = GamePanel.size.height-51;
		for (int i = 0; i < 12; i++) {
			invTypeRects.put(z, Arrays.asList(USEABLE,x+(38*i),y,38,40));//54-65
			MapConst.invTypeRects.put(z, Arrays.asList(USEABLE,x+(38*i),y,38,40));//54-65
			z++;
		}
		x = (GamePanel.size.width-200)/2; 
		y = GamePanel.size.height-531;
		invTypeRects.put(z, Arrays.asList(UPGRADER,x-119,y+21,38,41));//66
		z++;
		invTypeRects.put(z, Arrays.asList(TRASH,x+145,y+175,25,44));//67
		z++;
		invTypeRects.put(z, Arrays.asList(ACTION,x+25,y+185,25,35));//68
	}
}
