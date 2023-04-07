package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapConst {
	public static final int MINE = 2, LUMBER = 3;
	public int freeIndex = 208;
	
	final public static Map<Integer, List<Integer>> objProps = new HashMap <Integer, List<Integer>>() {{ //{width, height, type, HP}
		put(0, Arrays.asList(150,150));
		put(1, Arrays.asList(150,18));
		put(2, Arrays.asList(18,150));
		put(3, Arrays.asList(18,150));
		put(4, Arrays.asList(50,50));
		put(5, Arrays.asList(50,50));
		put(6, Arrays.asList(59,38));
		put(7, Arrays.asList(150,200));
		put(8, Arrays.asList(150,200));
		put(9, Arrays.asList(90,50));
		put(10, Arrays.asList(100,100));
		put(11, Arrays.asList(25,25));
		put(12, Arrays.asList(25,25));
		put(13, Arrays.asList(150,100));
		put(14, Arrays.asList(200,140));
		put(20, Arrays.asList(25,25, MINE, 10));
		put(21, Arrays.asList(25,25, MINE, 10));
		put(22, Arrays.asList(15,25, LUMBER, 10));
		put(23, Arrays.asList(50,24, LUMBER, 20));
		put(24, Arrays.asList(25,25, MINE, 15));
		put(50, Arrays.asList(50, 50));//{width, height}
		put(51, Arrays.asList(50, 50));//{width, height}
		put(52, Arrays.asList(50, 50));//{width, height}
		put(53, Arrays.asList(50, 50));//{width, height}
		put(54, Arrays.asList(38, 50));//{width, height}
		put(55, Arrays.asList(50, 50));//{width, height}
		put(56, Arrays.asList(25, 50));//{width, height}
		put(57, Arrays.asList(28, 50));//{width, height}
		put(100, Arrays.asList(50, 50, 0, 1, 200, 2, 3));//{width, height,projectile dmg, lvl, HP, damage, exp}
		put(101, Arrays.asList(50, 50, 4, 2, 300, 5, 5));//{width, height,projectile dmg, lvl, HP, damage, exp}
		put(102, Arrays.asList(50, 50, 0, 3, 500, 8, 10));//{width, height,projectile dmg, lvl, HP, damage, exp}
		put(103, Arrays.asList(50, 50, 8, 3, 400, 7, 14));//{width, height,projectile dmg, lvl, HP, damage, exp}
		put(104, Arrays.asList(50, 20, 0, 4, 800, 4, 15));//{width, height,projectile dmg, lvl, HP, damage, exp}
		put(105, Arrays.asList(50, 50, 15, 5, 900, 10, 20));//{width, height,projectile dmg, lvl, HP, damage, exp}
		put(106, Arrays.asList(30, 50, 0, 6, 1200, 15, 30));//{width, height,projectile dmg, lvl, HP, damage, exp}
		put(1000, Arrays.asList(15, 15));//{width, height}
		put(1001, Arrays.asList(15, 29));//{width, height}
		put(1002, Arrays.asList(13, 29));//{width, height}
		put(1003, Arrays.asList(24, 30));//{width, height}
		put(1004, Arrays.asList(30, 16));//{width, height}
		put(1005, Arrays.asList(24, 30));//{width, height}
		put(1006, Arrays.asList(30, 25));//{width, height}
		put(1007, Arrays.asList(30, 20));//{width, height}
		put(1008, Arrays.asList(15, 15));//{width, height}
		put(1009, Arrays.asList(15, 15));//{width, height}
		put(1010, Arrays.asList(30, 30));//{width, height}
		put(1011, Arrays.asList(20, 20));//{width, height}
		put(1012, Arrays.asList(17, 16));//{width, height}
		put(1013, Arrays.asList(19, 10));//{width, height}
		put(1014, Arrays.asList(30, 25));//{width, height}
		put(1015, Arrays.asList(20, 20));//{width, height}
		put(1016, Arrays.asList(15, 20));//{width, height}
		put(1017, Arrays.asList(20, 20));//{width, height}
		put(1018, Arrays.asList(15, 15));//{width, height}
		put(1019, Arrays.asList(20, 28));//{width, height}
	}};

	final public static Map<Integer, List<Integer>> projectileProps = new HashMap <Integer, List<Integer>>() {{// 0 type MapObj superclass
		put(2, Arrays.asList(20, 20)); // {width, height}
		put(101, Arrays.asList(20, 20));
		put(103, Arrays.asList(20, 20)); 
		put(105, Arrays.asList(20, 20)); 
	}};
	
	final public static Map<Integer, Object> objClassTypes = new HashMap <Integer, Object>() {{
		put(0, MapObj.class); // 0 type MapObj superclass
		put(1,  MapObj.class);
		put(2,  MapObj.class);
		put(3,  MapObj.class);
		put(4,  MapObj.class);
		put(5,  MapObj.class);
		put(6,  MapObj.class);
		put(7,  MapObj.class);
		put(8,  MapObj.class);
		put(9,  MapObj.class);
		put(10,  MapObj.class);
		put(11,  MapObj.class);
		put(12,  MapObj.class);
		put(13,  MapObj.class);
		put(14,  MapObj.class);
		put(20, Resource.class);
		put(21, Resource.class);
		put(22, Resource.class);
		put(23, Resource.class);
		put(24, Resource.class);
		put(50, NPC.class);
		put(51, NPC.class);
		put(52, NPC.class);
		put(53, NPC.class);
		put(54, NPC.class);
		put(55, NPC.class);
		put(56, NPC.class);
		put(57, NPC.class);
		put(100, Enemy.class);
		put(101, Enemy.class);
		put(102, Enemy.class);
		put(103, Enemy.class);
		put(104, Enemy.class);
		put(105, Enemy.class);
		put(106, Enemy.class);
		put(1000, MapItem.class);
		put(1001, MapItem.class);
		put(1002, MapItem.class);
		put(1003, MapItem.class);
		put(1004, MapItem.class);
		put(1005, MapItem.class);
		put(1006, MapItem.class);
		put(1007, MapItem.class);
		put(1008, MapItem.class);
		put(1009, MapItem.class);
		put(1010, MapItem.class);
		put(1011, MapItem.class);
		put(1012, MapItem.class);
		put(1013, MapItem.class);
		put(1014, MapItem.class);
		put(1015, MapItem.class);
		put(1016, MapItem.class);
		put(1017, MapItem.class);
		put(1018, MapItem.class);
		put(1019, MapItem.class);
	}};
	
	public static Map<Integer, List<Integer>> interfaceLoca = new HashMap <Integer, List<Integer>>();
	public static Map<Integer, List<Integer>> invTypeRects = new HashMap <Integer, List<Integer>>();
	
	final public static List<String> paths = Arrays.asList("/mapimgs/field.png","/mapimgs/0.png","/mapimgs/1.png","/mapimgs/2.png",
			"/mapimgs/3.png", "/mapimgs/4.png", "/mapimgs/5.png","/mapimgs/6.png","/mapimgs/7.png","/mapimgs/8.png","/mapimgs/9.png","/mapimgs/10.png","/mapimgs/11.png","/mapimgs/12.png",
			"/mapimgs/13.png","/mapimgs/14.png");
	final public static List<String> resourcePaths = Arrays.asList("/resources/20.png", "/resources/21.png","/resources/22.png","/resources/23.png","/resources/24.png");
	
	final public static List<String> enemyPaths = Arrays.asList("/enemies/100.png","/enemies/101.png","/enemies/102.png","/enemies/103.png","/enemies/104.png","/enemies/105.png","/enemies/106.png");
	final public static List<String> interfacePaths = Arrays.asList("/interfaceimgs/heart.png","/interfaceimgs/bar.png","/interfaceimgs/hpbar.png","/interfaceimgs/mana.png","/interfaceimgs/mpbar.png","/interfaceimgs/exp.png","/interfaceimgs/expbar.png","/interfaceimgs/usebar.png","/interfaceimgs/inventory.png","/interfaceimgs/skillbar.png","/interfaceimgs/skilltab.png","/interfaceimgs/statstab.png","/interfaceimgs/queststab.png");
	final public static List<String> npcPaths = Arrays.asList("/npcs/50.png","/npcs/51.png","/npcs/52.png","/npcs/53.png","/npcs/54.png","/npcs/55.png","/npcs/56.png","/npcs/57.png");
	
	final public static Map<Integer, String> projectPaths = new HashMap <Integer, String>(){{
		put(2,"/projectile/2.png");
		put(101,"/projectile/101.png");
		put(103,"/projectile/103.png");
		put(105,"/projectile/105.png");
	}};

	final public static Map<Integer, String> itempaths = new HashMap <Integer, String>() {{
			put(1000,"/mapitems/map1000.png");
			put(1001,"/mapitems/map1001.png");
			put(1002,"/mapitems/map1002.png");
			put(1003,"/mapitems/map1003.png");
			put(1004,"/mapitems/map1004.png");
			put(1005,"/mapitems/map1005.png");
			put(1006,"/mapitems/map1006.png");
			put(1007,"/mapitems/map1007.png");
			put(1008,"/mapitems/map1008.png");
			put(1009,"/mapitems/map1009.png");
			put(1010,"/mapitems/map1010.png");
			put(1011,"/mapitems/map1011.png");
			put(1012,"/mapitems/map1012.png");
			put(1013,"/mapitems/map1013.png");
			put(1014,"/mapitems/map1014.png");
			put(1015,"/mapitems/map1015.png");
			put(1016,"/mapitems/map1016.png");
			put(1017,"/mapitems/map1017.png");
			put(1018,"/mapitems/map1018.png");
			put(1019,"/mapitems/map1019.png");
	}};
	
	public static Map<Integer, List<Integer>> objLoca = new HashMap <Integer, List<Integer>>() {{ //key {type, cordx, cory, amount}
		put(0, Arrays.asList(1,150,135) );
		put(1, Arrays.asList(2,135,150) );
		put(2, Arrays.asList(2,135,300) );
		put(3, Arrays.asList(3,290,150) );
		put(4, Arrays.asList(3,290,300) );
		put(5, Arrays.asList(2,135,450) );
		put(6, Arrays.asList(2,135,600) );
		put(7, Arrays.asList(3,290,450) );
		put(8, Arrays.asList(3,290,600) );
		put(9, Arrays.asList(1,290,740) );
		put(10, Arrays.asList(2,135,750) );
		put(11, Arrays.asList(2,135,900) );
		put(12, Arrays.asList(1,440,740) );
		put(13, Arrays.asList(1,590,740) );
		put(14, Arrays.asList(1,740,740) );
		put(15, Arrays.asList(1,890,740) );
		put(16, Arrays.asList(1,1040,740) );
		put(17, Arrays.asList(1,1190,740) );
		put(18, Arrays.asList(1,1340,740) );
		put(19, Arrays.asList(1,1490,740) );
		put(20, Arrays.asList(3,1640,300) );
		put(21, Arrays.asList(3,1640,450) );
		put(22, Arrays.asList(3,1640,600) );
		put(23, Arrays.asList(1,1650,285) );
		put(24, Arrays.asList(1,1800,285) );
		put(25, Arrays.asList(1,1950,285) );
		put(26, Arrays.asList(1,2100,285) );
		put(27, Arrays.asList(1,2250,285) );
		put(28, Arrays.asList(1,2400,285) );
		put(29, Arrays.asList(1,2550,285) );
		put(30, Arrays.asList(1,2700,285) );
		put(31, Arrays.asList(1,2850,285) );
		put(32, Arrays.asList(2,2995,295) );
		put(33, Arrays.asList(2,2995,445) );
		put(34, Arrays.asList(2,135,1050) );
		put(35, Arrays.asList(2,135,1200) );
		put(36, Arrays.asList(2,135,1350) );
		put(37, Arrays.asList(2,135,1500) );
		put(38, Arrays.asList(1,145,1650) );
		put(39, Arrays.asList(1,295,1650) );
		put(40, Arrays.asList(1,445,1650) );
		put(41, Arrays.asList(1,595,1650) );
		put(42, Arrays.asList(1,745,1650) );
		put(43, Arrays.asList(1,895,1650) );
		put(44, Arrays.asList(1,1045,1650) );
		put(45, Arrays.asList(1,1195,1650) );
		put(46, Arrays.asList(1,1345,1650) );
		put(47, Arrays.asList(1,1495,1650) );
		put(48, Arrays.asList(1,1645,1650) );
		put(49, Arrays.asList(1,1795,1650) );
		put(50, Arrays.asList(1,1945,1650) );
		put(51, Arrays.asList(3,2100,1660) );
		put(52, Arrays.asList(3,2100,1810) );
		put(53, Arrays.asList(3,2100,1960) );
		put(54, Arrays.asList(3,2100,2110) );
		put(55, Arrays.asList(1,1945,2250) );
		put(56, Arrays.asList(1,1795,2250) );
		put(57, Arrays.asList(1,1645,2250) );
		put(58, Arrays.asList(1,1495,2250) );
		put(59, Arrays.asList(1,1345,2250) );
		put(60, Arrays.asList(1,1195,2250) );
		put(61, Arrays.asList(1,1045,2250) );
		put(62, Arrays.asList(1,895,2250) );
		put(63, Arrays.asList(1,745,2250) );
		put(64, Arrays.asList(1,595,2250) );
		put(65, Arrays.asList(1,445,2250) );
		put(66, Arrays.asList(1,295,2250) );
		put(67, Arrays.asList(2,2995,595) );
		put(68, Arrays.asList(2,2995,745) );
		put(69, Arrays.asList(2,2995,895) );
		put(70, Arrays.asList(2,2995,1045) );
		put(71, Arrays.asList(2,2995,1195) );
		put(72, Arrays.asList(2,2995,1345) );
		put(73, Arrays.asList(2,2995,1495) );
		put(74, Arrays.asList(2,2995,1645) );
		put(75, Arrays.asList(2,2995,1795) );
		put(76, Arrays.asList(2,2995,1945) );
		put(77, Arrays.asList(2,2995,2095) );
		put(78, Arrays.asList(2,2995,2245) );
		put(79, Arrays.asList(2,2995,2395) );
		put(80, Arrays.asList(2,2995,2545) );
		put(81, Arrays.asList(2,2995,2695) );
		put(82, Arrays.asList(2,2995,2845) );
		put(83, Arrays.asList(2,2995,2995) );
		put(84, Arrays.asList(1,2855,3145) );
		put(85, Arrays.asList(1,2705,3145) );
		put(86, Arrays.asList(1,2555,3145) );
		put(87, Arrays.asList(1,2405,3145) );
		put(88, Arrays.asList(1,2255,3145) );
		put(89, Arrays.asList(1,2105,3145) );
		put(90, Arrays.asList(1,1955,3145) );
		put(91, Arrays.asList(1,1805,3145) );
		put(92, Arrays.asList(1,1655,3145) );
		put(93, Arrays.asList(1,1505,3145) );
		put(94, Arrays.asList(1,1355,3145) );
		put(95, Arrays.asList(1,1205,3145) );
		put(96, Arrays.asList(1,1055,3145) );
		put(97, Arrays.asList(1,905,3145) );
		put(98, Arrays.asList(2,895,3160) );
		put(99, Arrays.asList(2,895,3310) );
		put(100, Arrays.asList(2,895,3460) );
		put(101, Arrays.asList(1,905,3610) );
		put(102, Arrays.asList(1,1055,3610) );
		put(103, Arrays.asList(1,1205,3610) );
		put(104, Arrays.asList(1,1355,3610) );
		put(105, Arrays.asList(1,1505,3610) );
		put(106, Arrays.asList(1,1655,3610) );
		put(107, Arrays.asList(1,1805,3610) );
		put(108, Arrays.asList(1,1955,3610) );
		put(109, Arrays.asList(1,2105,3610) );
		put(110, Arrays.asList(1,2255,3610) );
		put(111, Arrays.asList(1,2405,3610) );
		put(112, Arrays.asList(1,2555,3610) );
		put(113, Arrays.asList(1,2705,3610) );
		put(114, Arrays.asList(1,2855,3610) );
		put(115, Arrays.asList(1,3005,3610) );
		put(116, Arrays.asList(3,3155,3620) );
		put(117, Arrays.asList(3,3155,3770) );
		put(118, Arrays.asList(3,3155,3920) );
		put(119, Arrays.asList(3,3155,4070) );
		put(120, Arrays.asList(3,3155,4220) );
		put(121, Arrays.asList(3,3155,4370) );
		put(122, Arrays.asList(3,3155,4520) );
		put(123, Arrays.asList(3,3155,4670) );
		put(124, Arrays.asList(3,3155,4820) );
		put(125, Arrays.asList(3,3155,4970) );
		put(126, Arrays.asList(3,3155,5120) );
		put(127, Arrays.asList(3,3155,5270) );
		put(128, Arrays.asList(3,3155,5420) );
		put(129, Arrays.asList(3,3155,5570) );
		put(130, Arrays.asList(1,3005,5700) );
		put(131, Arrays.asList(1,2855,5700) );
		put(132, Arrays.asList(1,2705,5700) );
		put(133, Arrays.asList(1,2555,5700) );
		put(134, Arrays.asList(1,2405,5700) );
		put(135, Arrays.asList(1,2255,5700) );
		put(136, Arrays.asList(1,2105,5700) );
		put(137, Arrays.asList(1,1955,5700) );
		put(138, Arrays.asList(1,1805,5700) );
		put(139, Arrays.asList(1,1655,5700) );
		put(140, Arrays.asList(1,1505,5700) );
		put(141, Arrays.asList(1,1355,5700) );
		put(142, Arrays.asList(1,1205,5700) );
		put(143, Arrays.asList(1,1055,5700) );
		put(144, Arrays.asList(1,905,5700) );
		put(145, Arrays.asList(1,755,5700) );
		put(146, Arrays.asList(1,605,5700) );
		put(147, Arrays.asList(1,455,5700) );
		put(148, Arrays.asList(1,305,5700) );
		put(149, Arrays.asList(3,295,5560) );
		put(150, Arrays.asList(3,295,5410) );
		put(151, Arrays.asList(3,295,5260) );
		put(152, Arrays.asList(3,295,5110) );
		put(153, Arrays.asList(3,295,4960) );
		put(154, Arrays.asList(3,295,4810) );
		put(155, Arrays.asList(3,295,4660) );
		put(156, Arrays.asList(3,295,4510) );
		put(157, Arrays.asList(3,295,4360) );
		put(158, Arrays.asList(3,295,4210) );
		put(159, Arrays.asList(3,295,4060) );
		put(160, Arrays.asList(3,295,3910) );
		put(161, Arrays.asList(3,295,3760) );
		put(162, Arrays.asList(3,295,3610) );
		put(163, Arrays.asList(3,295,3460) );
		put(164, Arrays.asList(3,295,3310) );
		put(165, Arrays.asList(3,295,3160) );
		put(166, Arrays.asList(3,295,3010) );
		put(167, Arrays.asList(3,295,2860) );
		put(168, Arrays.asList(3,295,2710) );
		put(169, Arrays.asList(3,295,2560) );
		put(170, Arrays.asList(3,295,2410) );
		put(171, Arrays.asList(3,295,2260) );//frame end
		put(172, Arrays.asList(1,1000,4255) );
		put(173, Arrays.asList(1,1150,4255) );
		put(174, Arrays.asList(2,980,4270) );
		put(175, Arrays.asList(3,1305,4270) );
		put(176, Arrays.asList(2,980,4420) );
		put(177, Arrays.asList(3,1305,4420) );
		put(178, Arrays.asList(2,980,4570) );
		put(179, Arrays.asList(3,1305,4570) );
		put(180, Arrays.asList(2,980,4720) );
		put(181, Arrays.asList(3,1305,4720) );
		put(182, Arrays.asList(2,980,4870) );
		put(183, Arrays.asList(3,1305,4870) );
		put(184, Arrays.asList(1,1000,5010) );
		put(185, Arrays.asList(1,1150,5010) );
		put(186, Arrays.asList(1,2340,3895) );
		put(187, Arrays.asList(1,2490,3895) );
		put(188, Arrays.asList(2,2320,3910) );
		put(189, Arrays.asList(3,2640,3910) );
		put(190, Arrays.asList(2,2320,4060) );
		put(191, Arrays.asList(3,2640,4060) );
		put(192, Arrays.asList(2,2320,4210) );
		put(193, Arrays.asList(3,2640,4210) );
		put(194, Arrays.asList(1,2340,4355) );
		put(195, Arrays.asList(1,2490,4355) );
		put(196, Arrays.asList(1,1940,5170) );
		put(197, Arrays.asList(1,2090,5170) );
		put(198, Arrays.asList(1,2240,5170) );
		put(199, Arrays.asList(1,1940,5480) );
		put(200, Arrays.asList(1,2090,5480) );
		put(201, Arrays.asList(1,2240,5480) );
		put(202, Arrays.asList(3,1925,5185) );
		put(203, Arrays.asList(3,1925,5335) );
		put(204, Arrays.asList(3,2390,5185) );
		put(205, Arrays.asList(3,2390,5335) );// end of fences
		put(207, Arrays.asList(7,1850,3630) );
		put(208, Arrays.asList(51,1790,3750) );
		put(209, Arrays.asList(9,1803,500) );
		put(210, Arrays.asList(9,1800,410) );
		put(211, Arrays.asList(9,1873,460) );
		put(212, Arrays.asList(9,1732,460) );
		put(213, Arrays.asList(52,1822,460) );
		put(214, Arrays.asList(10,300,1300) );
		put(215, Arrays.asList(54,329,1365) );
		put(216, Arrays.asList(53,2820,1905) );
		put(217, Arrays.asList(11,700,1000) );
		put(218, Arrays.asList(12,900,1300) );
		put(219, Arrays.asList(4,1200,1100) );
		put(220, Arrays.asList(0,1500,1000) );
		put(221, Arrays.asList(0,1100,1300) );
		put(222, Arrays.asList(0,600,1100) );
		put(223, Arrays.asList(6,300,1000) );
		put(224, Arrays.asList(11,1500,1200) );
		put(225, Arrays.asList(9,1400,1300) );
		put(226, Arrays.asList(0,2100,600) );
		put(227, Arrays.asList(0,2100,1400) );
		put(228, Arrays.asList(0,1900,1200) );
		put(228, Arrays.asList(5,2500,500) );
		put(229, Arrays.asList(0,2700,350) );
		put(230, Arrays.asList(11,2800,600) );
		put(231, Arrays.asList(6,2600,900) );
		put(232, Arrays.asList(6,2100,1200) );
		put(233, Arrays.asList(0,2200,1000) );
		put(234, Arrays.asList(4,2400,1600) );
		put(235, Arrays.asList(0,2600,1450) );
		put(236, Arrays.asList(0,2700,1100) );
		put(237, Arrays.asList(0,2500,1800) );
		put(238, Arrays.asList(12,2300,2000) );
		put(239, Arrays.asList(0,2400,2100) );
		put(240, Arrays.asList(0,2600,2400) );
		put(241, Arrays.asList(5,2700,2100) );
		put(242, Arrays.asList(0,2290,2720) );
		put(243, Arrays.asList(0,2290,2480) );
		put(244, Arrays.asList(0,2090,2720) );
		put(245, Arrays.asList(0,2090,2480) );
		put(246, Arrays.asList(0,1890,2720) );
		put(247, Arrays.asList(0,1890,2480) );
		put(248, Arrays.asList(0,1690,2720) );
		put(249, Arrays.asList(0,1690,2480) );
		put(250, Arrays.asList(0,1490,2720) );
		put(251, Arrays.asList(0,1490,2480) );
		put(252, Arrays.asList(0,1250,2720) );
		put(253, Arrays.asList(0,1250,2480) );
		put(254, Arrays.asList(0,1000,2720) );
		put(255, Arrays.asList(0,1000,2480) );
		put(256, Arrays.asList(0,750,2720) );
		put(257, Arrays.asList(0,750,2480) );
		put(258, Arrays.asList(50,1020,2870) );
		put(259, Arrays.asList(11,1020,2920) );
		put(260, Arrays.asList(12,1080,2900) );
		put(261, Arrays.asList(11,950,2890) );
		put(262, Arrays.asList(5,1490,2340) );
		put(263, Arrays.asList(9,750,2890) );
		put(264, Arrays.asList(6,650,2390) );
		put(265, Arrays.asList(12,2450,2390) );
		put(266, Arrays.asList(6,2650,2900) );
		put(267, Arrays.asList(0,700,3100) );
		put(268, Arrays.asList(0,400,3400) );
		put(269, Arrays.asList(11,600,3200) );
		put(270, Arrays.asList(4,700,3600) );
		put(271, Arrays.asList(0,700,3300) );
		put(272, Arrays.asList(12,500,3300) );
		put(273, Arrays.asList(0,700,3800) );
		put(274, Arrays.asList(0,600,4000) );
		put(275, Arrays.asList(0,400,4200) );
		put(276, Arrays.asList(0,600,4300) );
		put(276, Arrays.asList(11,700,4300) );
		put(277, Arrays.asList(6,800,4400) );
		put(278, Arrays.asList(0,600,4600) );
		put(279, Arrays.asList(9,500,4400) );
		put(280, Arrays.asList(0,400,4800) );
		put(281, Arrays.asList(0,600,5000) );
		put(282, Arrays.asList(5,800,4800) );
		put(283, Arrays.asList(8,500,5300) );
		put(284, Arrays.asList(55,550,5480) );
		put(285, Arrays.asList(0,800,5500) );
		put(286, Arrays.asList(0,1100,5300) );
		put(287, Arrays.asList(5,750,5300) );
		put(288, Arrays.asList(11,450,5100) );
		put(289, Arrays.asList(12,500,5250) );
		put(290, Arrays.asList(0,1000,5100) );
		put(291, Arrays.asList(0,1400,5400) );
		put(292, Arrays.asList(0,1700,5100) );
		put(293, Arrays.asList(11,1600,5200) );
		put(294, Arrays.asList(11,1400,5300) );
		put(295, Arrays.asList(9,1708,5475) );
		put(296, Arrays.asList(0,1608,4875) );
		put(297, Arrays.asList(0,1378,4675) );
		put(298, Arrays.asList(0,1678,4575) );
		put(299, Arrays.asList(11,1500,4375) );
		put(300, Arrays.asList(4,1900,4575) );
		put(301, Arrays.asList(6,2200,4375) );
		put(302, Arrays.asList(6,2300,4775) );
		put(303, Arrays.asList(6,2600,4975) );
		put(304, Arrays.asList(0,2000,4200) );
		put(305, Arrays.asList(0,1700,4000) );
		put(306, Arrays.asList(0,1200,4100) );
		put(307, Arrays.asList(0,1400,3800) );
		put(308, Arrays.asList(12,1100,3700) );
		put(309, Arrays.asList(0,1000,3900) );
		put(310, Arrays.asList(0,2400,4975) );
		put(311, Arrays.asList(0,2000,4775) );
		put(312, Arrays.asList(0,2600,4675) );
		put(313, Arrays.asList(5,2800,4475) );
		put(314, Arrays.asList(11,2900,4975) );
		put(315, Arrays.asList(11,2900,4975) );
		put(316, Arrays.asList(0,3000,5100) );
		put(317, Arrays.asList(0,2800,5200) );
		put(318, Arrays.asList(0,2600,5300) );
		put(319, Arrays.asList(0,2600,5300) );
		put(320, Arrays.asList(9,2900,5450) );
		put(321, Arrays.asList(5,2200,5600) );
		put(322, Arrays.asList(0,2900,4300) );
		put(323, Arrays.asList(11,2700,4100) );
		put(324, Arrays.asList(0,2800,4000) );
		put(325, Arrays.asList(4,2900,4575) );
		put(326, Arrays.asList(13,2130,3950) );
		put(327, Arrays.asList(56,2200,4050) );
		put(328, Arrays.asList(14,2930,3800) );
		put(329, Arrays.asList(57,3015,3942) );//end of map objects
	}};
	
	final public static Map<Integer, List<Integer>> enemyLoca = new HashMap <Integer, List<Integer>>() {{
		put(374, Arrays.asList(100,800,1000) );
		put(375, Arrays.asList(100,740,1400) );
		put(376, Arrays.asList(100,1000,1200) );
		put(377, Arrays.asList(100,1400,1500) );
		put(378, Arrays.asList(100,1800,700) );
		put(379, Arrays.asList(101,2500,600) );
		put(380, Arrays.asList(101,2400,1100) );
		put(381, Arrays.asList(101,2300,1500) );
		put(382, Arrays.asList(101,2120,2100) );
		put(383, Arrays.asList(101,2500,2600) );
		put(384, Arrays.asList(102,2200,2900) );
		put(385, Arrays.asList(102,1700,2400) );
		put(386, Arrays.asList(102,1200,2900) );
		put(387, Arrays.asList(102,500,2700) );
		put(388, Arrays.asList(103,600,3500) );
		put(389, Arrays.asList(103,320,3900) );
		put(390, Arrays.asList(103,500,4500) );
		put(391, Arrays.asList(103,700,5200) );
		put(392, Arrays.asList(104,1400,5600) );
		put(393, Arrays.asList(104,2000,5000) );
		put(394, Arrays.asList(104,1600,4500) );
		put(395, Arrays.asList(104,900,3800) );
		put(396, Arrays.asList(105,2200,4700) );
		put(397, Arrays.asList(105,2800,4200) );
		put(398, Arrays.asList(105,2500,3700) );
		put(399, Arrays.asList(106,2800,4800) );
		put(400, Arrays.asList(106,2800,5390) );
		put(401, Arrays.asList(106,2400,5600) );
	}};

}

