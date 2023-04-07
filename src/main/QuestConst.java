package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestConst {
	public static final int NOTSTARTED = 0, INPROGRESS = 1, COMPLETED = 2, REWARDED = 3;
	final public static Map<Integer, List<Object>> questsInterface = new HashMap <Integer, List<Object>>() {{//{npc Id, status,level req, quest name, text, text}
		put(0, Arrays.asList(54, NOTSTARTED, 1 , "Lumber For New Fence", "Please bring me 5 stacks of lumber."));
		put(1, Arrays.asList(50, NOTSTARTED, 2 , "Iron Ores to Smelth a Plate", "Please bring me 3 iron ores."));
		put(2, Arrays.asList(51, NOTSTARTED, 1 , "A Rock To Build a Cave", "Please bring me 5 Rocks."));
		put(3, Arrays.asList(51, NOTSTARTED, 2 , "Bronze Ores Smelth a Plate", "Please bring me 3 bronze ores."));
	}};
	
	final public static Map<Integer, List<Integer>> questReqs = new HashMap <Integer, List<Integer>>() {{
		put(0, Arrays.asList(1004, 5)); //{item id, amount...,item id, amount} MAX 4
		put(1, Arrays.asList(1008, 3)); //{item id, amount...,item id, amount}
		put(2, Arrays.asList(1012, 5)); //{item id, amount...,item id, amount}
		put(3, Arrays.asList(1009, 3)); //{item id, amount...,item id, amount}
	}};
	final public static Map<Integer, List<Integer>> questRewards = new HashMap <Integer, List<Integer>>() {{
		put(0, Arrays.asList(20 ,1000, 50, 1003, 5));//{exp, item id, amount...,item id, amount}
		put(1, Arrays.asList(30 ,1000, 100, 1005, 5));//{exp, item id, amount...,item id, amount}
		put(2, Arrays.asList(20 ,1000, 100, 1003, 5));//{exp, item id, amount...,item id, amount}
		put(3, Arrays.asList(30 ,1000, 100, 1005, 5));//{exp, item id, amount...,item id, amount}
	}};
	
}
