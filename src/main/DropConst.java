package main;

import java.util.HashMap;
import java.util.Map;

public final class DropConst {
	
	final public static Map<Integer,  Map<Integer, Integer>> mobRates = new HashMap <Integer, Map<Integer, Integer>>() {{
		put(100, new HashMap <Integer, Integer>(){{ //{mob}
			put(1000, 300);//{item, drop chance}
			put(1013, 100);
			put(1001, 5);
			put(1003, 50);
			put(1005, 40);
		}});
		put(101, new HashMap <Integer, Integer>(){{ //{mob}
			put(1000, 300);//{item, drop chance}
			put(1003, 50);
			put(1005, 40);
			put(1006, 10);
			put(1012, 100);
		}});
		put(102, new HashMap <Integer, Integer>(){{ //{mob}
			put(1000, 300);//{item, drop chance}
			put(1003, 50);
			put(1005, 40);
			put(1010, 5);
		}});
		put(103, new HashMap <Integer, Integer>(){{ //{mob}
			put(1000, 300);//{item, drop chance}
			put(1003, 50);
			put(1005, 40);
			put(1014, 100);
		}});
		put(104, new HashMap <Integer, Integer>(){{ //{mob}
			put(1000, 300);//{item, drop chance}
			put(1003, 50);
			put(1005, 40);
			put(1015, 100);
			put(1009, 5);
		}});
		put(105, new HashMap <Integer, Integer>(){{ //{mob}
			put(1000, 300);//{item, drop chance}
			put(1003, 50);
			put(1005, 40);
			put(1015, 100);
			put(1008, 5);
		}});
		
		put(106, new HashMap <Integer, Integer>(){{ //{mob}
			put(1000, 300);//{item, drop chance}
			put(1003, 50);
			put(1005, 40);
			put(1017, 100);
			put(1018, 3);
		}});
		
		put(20, new HashMap <Integer, Integer>(){{ 
			put(1008, 1);//{item, drop chance}
		}});
		put(21, new HashMap <Integer, Integer>(){{ 
			put(1009, 1);//{item, drop chance}
		}});
		put(22, new HashMap <Integer, Integer>(){{ 
			put(1004, 1);//{item, drop chance}
		}});
		put(23, new HashMap <Integer, Integer>(){{ 
			put(1004, 1);//{item, drop chance}
		}});
		put(24, new HashMap <Integer, Integer>(){{ 
			put(1018, 1);//{item, drop chance}
		}});
	}};
	
}
