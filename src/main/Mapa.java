package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class Mapa {
	public static Point mapPoint = new Point(0,400);
	
	public static ArrayList<MapObj> mapObjsList = new ArrayList<MapObj>();
	public static Object clickedObj;
	
	public static ArrayList<MapObj> interMapObj = new ArrayList<MapObj>();
	public static ArrayList<MapItem> itemMapObj = new ArrayList<MapItem>();
	public static ArrayList<Enemy> enemyObj = new ArrayList<Enemy>();
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public static ArrayList<NPC> npcObj = new ArrayList<NPC>();
	
	private ArrayList<BufferedImage> img = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> interfaceImg = new ArrayList<BufferedImage>();
	
	private ArrayList<BufferedImage> resourceImg = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> resourceImgDead = new ArrayList<BufferedImage>();
	
	private ArrayList<BufferedImage> enemyImg = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> enemyImgDMG = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> enemyImgDead = new ArrayList<BufferedImage>();
	
	public static Map<Integer, BufferedImage> mapItemImgs = new HashMap<Integer, BufferedImage>();
	public static Map<Integer,BufferedImage> projectileImg = new HashMap<Integer,BufferedImage>();
	public static Map<Integer,BufferedImage> npcsImgs = new HashMap<Integer,BufferedImage>();
	
	public static ArrayList<MapEffect> mapEff = new ArrayList<MapEffect>();
	public static Map<Integer, MapEffect> skillEff = new HashMap<Integer, MapEffect>();
	public static Map<Integer, MapEffect> barStrings = new HashMap<Integer, MapEffect>();
	public static Map<Integer, Color> colorStrings = new HashMap<Integer, Color>();
	public static ArrayList<MapEffect> levelUpEffect = new ArrayList<MapEffect>();
	
	
	public static int aniTick = 0, respawnTimer = 1000, state = 0;
	public static final int GAMEPLAY = 0, INVENTORY = 1, SKILLS = 2, STATS = 3, DIALOGUE = 4, QUEST = 5;

	
	public void ModifyObjOnMap() {
		Map<Integer, List<Integer>> locaObj = new HashMap <Integer, List<Integer>>(MapConst.objLoca);
		ArrayList<MapObj> mObjList = new ArrayList<MapObj>(mapObjsList);
		List<Integer> objLocInts;
		Object objType;
		boolean found;
		for (int objKey : locaObj.keySet()) { // search default location to see entered the panel
			found = false;
			if (((locaObj.get(objKey).get(1) + MapConst.objProps.get(locaObj.get(objKey).get(0)).get(0)) >= mapPoint.x && locaObj.get(objKey).get(1) <= (mapPoint.x + GamePanel.size.width)) && ((locaObj.get(objKey).get(2) + MapConst.objProps.get(locaObj.get(objKey).get(0)).get(1)) >= mapPoint.y && locaObj.get(objKey).get(2) <= (mapPoint.y + GamePanel.size.height))) {
				for (MapObj mapObj : mObjList) { // check by key if obj already exists on panel
					if (mapObj.objKey == objKey) {
						found = true;
						break;
					}
				}
			}
			if (!found) {
				objType = MapConst.objClassTypes.get(locaObj.get(objKey).get(0));
				objLocInts = locaObj.get(objKey);
				if (objType == MapObj.class) {
					mObjList.add(new MapObj(objKey,objLocInts.get(0), objLocInts.get(1), objLocInts.get(2)));
				} else if (objType == Resource.class) {
					Resource mapObj1 = new Resource(objKey, objLocInts.get(0),objLocInts.get(1), objLocInts.get(2));
					mObjList.add(mapObj1);
					interMapObj.add(mapObj1);
				} else if (objType == Enemy.class) {
					Enemy mapObj1 = new Enemy(objKey, objLocInts.get(0), objLocInts.get(1), objLocInts.get(2),enemyImg.get(objLocInts.get(0)-100));
					mObjList.add(mapObj1);
					enemyObj.add(mapObj1);
				} else if (objType == MapItem.class) {
					MapItem mapObj1 = new MapItem(objKey, objLocInts.get(0),objLocInts.get(1), objLocInts.get(2), objLocInts.get(4));
					mObjList.add(mapObj1);
					interMapObj.add(mapObj1);
					itemMapObj.add(mapObj1);
				} else if (objType == NPC.class) {
					NPC mapObj1 = new NPC(objKey,objLocInts.get(0), objLocInts.get(1), objLocInts.get(2));
					mObjList.add(mapObj1);
					interMapObj.add(mapObj1);
					npcObj.add(mapObj1);
				}
			}
			if (respawnTimer == 0) {
				for (int enemyKey : MapConst.enemyLoca.keySet()) {
					if (!locaObj.containsKey(enemyKey)) {
						MapConst.objLoca.put(enemyKey, Arrays.asList(MapConst.enemyLoca.get(enemyKey).get(0),MapConst.enemyLoca.get(enemyKey).get(1),MapConst.enemyLoca.get(enemyKey).get(2)));
					}
				}
				respawnTimer = 1000;
			}
		}
		for (ListIterator<MapObj> iter = mObjList.listIterator(); iter.hasNext();) {
			MapObj mapObj = iter.next();
			if ((mapObj.objRect.x + mapObj.objRect.width) < mapPoint.x || mapObj.objRect.x > (mapPoint.x + GamePanel.size.width) || (mapObj.objRect.y + mapObj.objRect.height) < mapPoint.y || mapObj.objRect.y > (mapPoint.y + GamePanel.size.height)) {
				if (mapObj.getClass() == MapObj.class) {
					iter.remove();
				} else if (mapObj.getClass() == Resource.class) {
					iter.remove();
					interMapObj.remove(mapObj);
				} else if (mapObj.getClass() == Enemy.class) {
					iter.remove();
					enemyObj.remove(mapObj);
				} else if (mapObj.getClass() == MapItem.class) {
					iter.remove();
					interMapObj.remove(mapObj);
					itemMapObj.remove(mapObj);
				} else if (mapObj.getClass() == NPC.class) {
					iter.remove();
					interMapObj.remove(mapObj);
					npcObj.remove(mapObj);
				}
				
			}
		}
		mapObjsList = new ArrayList<>(mObjList);
		respawnTimer--;
	}
	
	public void Update(Player player, Inventory invt, SkillTab skilltab, StatsTab statsTab, NPCDialogue npcDia, QuestTab questTab) {
		switch (state) {
			case GAMEPLAY:
				player.ChangePlayerAnimation();
				if (Player.mapObjInter == Player.IDLE) {
					UpdatePos(player, invt);
				}
				if (enemyObj.size() != 0) {
					UpdateEnemyPos();
					CheckPlayerEnemyCollide(player);
				}
				UpdateProjectilePos();
				ChangeMapObjAniFrame();
				invt.UpdateImgPosition();
				UpdatePlayerStatsStrings();
				skilltab.UpdateSkillTick();
				if (itemMapObj.size() != 0) {
					UpdateMapItemObj(invt);
				}
				break;
			case INVENTORY:
				invt.UpdateImgPosition();
				break;
			case STATS:
				statsTab.UpdateStatsTab();
				break;
			case DIALOGUE:
				npcDia.UpdateImgPosition();
				break;
			case QUEST:
				questTab.UpdateQuestTab();;
				break;
			}
	}

	private void UpdateMapItemObj(Inventory invt) {
		MapItem mapObj;
		ArrayList<MapObj> mObjList  = new ArrayList<MapObj>(mapObjsList);
		ArrayList<MapObj> iObjList = new ArrayList<MapObj>(interMapObj);
		ArrayList<MapItem> itemObj = new ArrayList<MapItem>(itemMapObj);
		for (ListIterator<MapItem> iter = itemObj.listIterator(); iter.hasNext();) {
			mapObj = iter.next();
			if (mapObj.state == mapObj.LOOTED) {
				if(mapObj.MoveToPlayer(invt)) {
					iter.remove();
					mObjList.remove(mapObj);
					iObjList.remove(mapObj);
				}
			}
			if (mapObj.state == mapObj.DROPPING) {
				mapObj.MoveDroppingItem();
			}
		}
		mapObjsList = new ArrayList<MapObj>(mObjList);
		interMapObj = new ArrayList<MapObj>(iObjList);
	}

	private void UpdateProjectilePos() {
		ArrayList<Projectile> projs  = new ArrayList<Projectile>(projectiles);
		Projectile project;
		for (ListIterator<Projectile> proj = projs.listIterator(); proj.hasNext();) {
			project = proj.next();
			if (project.projectileSkill != null) {
				if (project.MoveProjectile(enemyObj)) {
					proj.remove();
				}
			} else {
				if (project.MoveProjectile()) {
					proj.remove();
				}
			}
		}
		projectiles = new ArrayList<Projectile>(projs);
	}

	private void UpdatePlayerStatsStrings() {
		StringEffect strEeff;
		for (int effKey : barStrings.keySet()) {
			switch (effKey) {
				case 0:
					strEeff = (StringEffect)barStrings.get(effKey);
					strEeff.label = String.valueOf(Player.Hp)+"/"+String.valueOf(Player.maxHp);
					strEeff.point.x = 160-(2*strEeff.label.length());
					break;
				case 1:
					strEeff = (StringEffect)barStrings.get(effKey);
					strEeff.label = String.valueOf(Player.Mp)+"/"+String.valueOf(Player.maxMp);
					strEeff.point.x = 160-(2*strEeff.label.length());
					break;
				case 2:
					strEeff = (StringEffect)barStrings.get(effKey);
					strEeff.label = String.valueOf(Player.Exp)+"/"+String.valueOf(Player.lvlUpExp);
					strEeff.point.x = 160-(2*strEeff.label.length());
					break;
			}
		}
		
	}

	private void UpdatePos(Player ply, Inventory invt) {
		int[] direc = KeyInputs.direc;
		CheckCollideWithClickedObj(ply, direc, invt); //check if player is near a resource
		int[] advance = {0,0};
		if ((mapPoint.x + GamePanel.size.width) < 3600 && mapPoint.x > 0) { // check map boundaries
			mapPoint.x -= direc[0];
			advance[0] -= direc[0];
		} else if (((mapPoint.x + GamePanel.size.width) == 3600 || mapPoint.x == 0)) {
			if (Player.cords[0] == (GamePanel.size.width-36)/2) {
				if ((((mapPoint.x + GamePanel.size.width) == 3600) && (direc[0] != -1)) || (mapPoint.x == 0 && (direc[0] != 1))) {
					mapPoint.x -= direc[0];
					advance[0] -= direc[0];
				} else {
					MovePlayer(direc, 0, ply);  // if on the edge just move the player
				}
			} else {
				MovePlayer(direc, 0, ply);
			}
		}
		if ((mapPoint.y + GamePanel.size.height) < 6000 && mapPoint.y > 0) {
			mapPoint.y -= direc[1];
			advance[1] -= direc[1];
		} else if ((mapPoint.y + GamePanel.size.height) == 6000 || mapPoint.y == 0) {
			if (Player.cords[1] == (GamePanel.size.height-50)/2) {
				if ((((mapPoint.y + GamePanel.size.height) == 6000) && (direc[1] != -1)) || (mapPoint.y == 0 && (direc[1] != 1))) {
					mapPoint.y -= direc[1];
					advance[1] -= direc[1];
				} else {
					MovePlayer(direc, 1, ply);
				}
			} else {
				MovePlayer(direc, 1, ply);
			}
		}
		if (MoveBackIfCollides(advance, ply)) { // check player collision with map objs 
			mapPoint.x -= advance[0];                  // if collides move map back 
			mapPoint.y -= advance[1];
			Player.playerRect.x = Player.cords[0];
			Player.playerRect.y = Player.cords[1];
		}
		ModifyObjOnMap(); //re-add objs
		
	}

	private boolean MoveBackIfCollides (int[] advnct, Player ply) {
		Player.playerRect.x = Player.cords[0];
		Player.playerRect.y = Player.cords[1];
		Rectangle modifRect = new Rectangle();
		for (MapObj mapObj : mapObjsList) {
			if (!(mapObj.getClass() == Enemy.class && ((Enemy)mapObj).state == ((Enemy)mapObj).DEAD) && mapObj.getClass() != MapItem.class) {
				modifRect.x =  mapObj.objRect.x - mapPoint.x;
				modifRect.y =  mapObj.objRect.y - mapPoint.y;
				modifRect.width = mapObj.objRect.width;
				modifRect.height = mapObj.objRect.height;
				if (Player.playerRect.intersects(modifRect)) { //check collision with map obj
					if (mapObj.getClass() == Resource.class && ((Resource)mapObj).state != ((Resource)mapObj).DEAD) {
						clickedObj = mapObj;
					} else {
						clickedObj = null;
					}
					return true;
				} // remove if obj is outside of panel boundaries
			}
			
		}
		return false;
	}
	
	public void Draw(Graphics g, Player player, GamePanel gamepanel) {
		Color oldColor = g.getColor();
		if (Player.mapObjInter != Player.DEAD) {
			Resource mapObje;
			Enemy eneObje;
			MapItem itemObj;
			NPC npcO;
			BufferedImage imgEnemy;
			ArrayList<MapObj> runList = new ArrayList<MapObj>(mapObjsList);
			g.drawImage(img.get(0).getSubimage(mapPoint.x, mapPoint.y, GamePanel.size.width, GamePanel.size.height), 0, 0, null);
			for (MapObj mapObj : runList) {
				if (mapObj.getClass() == Resource.class) {
					mapObje = (Resource)mapObj;
					if (mapObje.state == mapObje.DEAD) {
						g.drawImage(resourceImgDead.get(mapObj.objType-20).getSubimage(0, 0, mapObje.objRect.width, mapObje.frameBound), mapObje.objRect.x-mapPoint.x, mapObje.objRect.y-mapPoint.y, null);
					} else {
						g.drawImage(resourceImg.get(mapObj.objType-20).getSubimage(mapObj.objRect.width*mapObje.frame, 0, mapObj.objRect.width, mapObj.objRect.height), mapObj.objRect.x-mapPoint.x, mapObj.objRect.y-mapPoint.y, null);
					}
				} else if (mapObj.getClass() == Enemy.class){
					eneObje = (Enemy)mapObj;
					if (eneObje.underAtt) {
						g.drawImage(enemyImgDMG.get(eneObje.objType-100).getSubimage(eneObje.objRect.width*eneObje.row, eneObje.objRect.height*eneObje.line, eneObje.objRect.width, eneObje.objRect.height), eneObje.objRect.x-mapPoint.x, eneObje.objRect.y-mapPoint.y, null);
					} else if (eneObje.state == eneObje.DEAD) {
						g.drawImage(enemyImgDead.get(eneObje.objType-100).getSubimage(0, 0, eneObje.objRect.width, eneObje.frameBound), eneObje.objRect.x-mapPoint.x, eneObje.objRect.y-mapPoint.y, null);
					}else if (eneObje.state == eneObje.SPAWNING) {
						imgEnemy = enemyImg.get(eneObje.objType-100).getSubimage(eneObje.objRect.width*eneObje.row, eneObje.objRect.height*eneObje.line, eneObje.objRect.width, eneObje.objRect.height);
						AuxiliaryCalc.ChangeImgAlpha(imgEnemy, eneObje.imgOffset);
						g.drawImage(imgEnemy, eneObje.objRect.x-mapPoint.x, eneObje.objRect.y-mapPoint.y, null);
						AuxiliaryCalc.ChangeImgAlpha(imgEnemy, 255);
					} else {
						g.drawImage(enemyImg.get(eneObje.objType-100).getSubimage(eneObje.objRect.width*eneObje.row, eneObje.objRect.height*eneObje.line, eneObje.objRect.width, eneObje.objRect.height), eneObje.objRect.x-mapPoint.x, eneObje.objRect.y-mapPoint.y, null);
					}
					
				} else if (mapObj.getClass() == MapItem.class) {
					itemObj = (MapItem)mapObj;
					imgEnemy = mapItemImgs.get(itemObj.objType).getSubimage(itemObj.objRect.width*itemObj.frame, 0, itemObj.objRect.width, itemObj.objRect.height);
					if (itemObj.state == itemObj.DROPPING) {
						AuxiliaryCalc.ChangeImgAlpha(imgEnemy, (itemObj.dropAdvance*6)+15);
						g.drawImage(imgEnemy, itemObj.objRect.x-mapPoint.x, itemObj.objRect.y-mapPoint.y, null);
						AuxiliaryCalc.SetImgPixels(imgEnemy, itemObj.pixels);
					} else {
						g.drawImage(imgEnemy, itemObj.objRect.x-mapPoint.x, itemObj.objRect.y-mapPoint.y, null);
					}
				} else if (mapObj.getClass() == NPC.class) {
					npcO = (NPC)mapObj;
					g.drawImage(npcsImgs.get(npcO.objType).getSubimage(npcO.objRect.width*npcO.frame, 0, npcO.objRect.width, npcO.objRect.height), npcO.objRect.x-mapPoint.x, npcO.objRect.y-mapPoint.y, null);
				} else {
					g.drawImage(img.get(1+ mapObj.objType), mapObj.objRect.x-mapPoint.x, mapObj.objRect.y-mapPoint.y, null);
				}
				
			}
			player.Draw(g);
			MapEffect.DrawMapEffects(g, mapEff);
			DrawProjectile(g);
			drawInterface(g);
			DrawUseableBarItems(g);
			DrawSkills(g);
			runList.clear();
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, GamePanel.size.width, GamePanel.size.height);
		}
		g.setColor(oldColor);
	}
	
	private void DrawProjectile(Graphics g) {
		ArrayList<Projectile> projs = new ArrayList<Projectile>(projectiles);
		for (Projectile proj : projs) {
			g.drawImage(projectileImg.get(proj.objType), (int)proj.xCord-mapPoint.x, (int)proj.yCord-mapPoint.y, null);
		}
		
	}

	private void DrawSkills(Graphics g) {
		Color colorGreen = new Color(196, 255, 14);
		Color colorRed = new Color(255, 100, 100);
		int redHeight;
		BuffSkill buffSkill;
		ActiveSkill activeSkill;
		Graphics2D g2 = (Graphics2D) g;;
		StringEffect strEff;
		Dimension pSize = GamePanel.size;
		ArrayList<Skill> skills = new ArrayList<Skill>(SkillTab.skillList);
		ArrayList<BuffSkill> buffs = new ArrayList<BuffSkill>(SkillTab.skillBuffs);
		Map<Integer, MapEffect> sEffs = new HashMap<Integer, MapEffect>(skillEff);
		for (Skill skillObj : skills) {
			if (skillObj.getClass() == BuffSkill.class) {
				buffSkill = (BuffSkill)skillObj;
				redHeight = (int)(50*(double)buffSkill.cooldown/(double)SkillConst.skillProps.get(buffSkill.objType).get(7));
				g.setColor(colorRed);
				g.fillRect(SkillConst.skillRects.get(skillObj.skillSlot).get(0), SkillConst.skillRects.get(skillObj.skillSlot).get(1)+(50-redHeight), SkillConst.skillRects.get(skillObj.skillSlot).get(2), redHeight);
				g.setColor(colorGreen);
				g.fillRect(SkillConst.skillRects.get(skillObj.skillSlot).get(0), SkillConst.skillRects.get(skillObj.skillSlot).get(1), SkillConst.skillRects.get(skillObj.skillSlot).get(2), (50-redHeight));
			} else if (skillObj.getClass() == ActiveSkill.class) {
				activeSkill = (ActiveSkill)skillObj;
				redHeight = (int)(50*(double)activeSkill.cooldown/(double)SkillConst.skillProps.get(activeSkill.objType).get(7));
				g.setColor(colorRed);
				g.fillRect(SkillConst.skillRects.get(skillObj.skillSlot).get(0), SkillConst.skillRects.get(skillObj.skillSlot).get(1)+(50-redHeight), SkillConst.skillRects.get(skillObj.skillSlot).get(2), redHeight);
				g.setColor(colorGreen);
				g.fillRect(SkillConst.skillRects.get(skillObj.skillSlot).get(0), SkillConst.skillRects.get(skillObj.skillSlot).get(1), SkillConst.skillRects.get(skillObj.skillSlot).get(2), (50-redHeight));
			}
			g.drawImage(SkillTab.skillImgs.get(skillObj.objType), SkillConst.skillRects.get(skillObj.skillSlot).get(0), SkillConst.skillRects.get(skillObj.skillSlot).get(1), null);
		}
		if (SkillTab.clickedSkill != null) {
			g.drawImage(SkillTab.skillImgs.get(SkillTab.clickedSkill.objType), MouseInputs.mousePoint.x - 25, MouseInputs.mousePoint.y - 25, null);
		}
		for (MapEffect sEff : sEffs.values()) {
			g2.setColor(Color.BLACK);
			strEff = (StringEffect) sEff;
			g2.drawString(strEff.label, strEff.point.x, strEff.point.y);
		}
		for (int i = 0; i < buffs.size();i++) {
			if (buffs.get(i).active == 1) {
				g.drawImage(SkillTab.skillBuffsImgs.get(buffs.get(i).objType), pSize.width-(32*(1+i)), 2, null);
			}
		}
	}

	private void DrawUseableBarItems(Graphics g) {
		Map<Integer, List<Integer>> invTRects = new HashMap <Integer, List<Integer>>(MapConst.invTypeRects);
		Object item;
		ETCItem etcItem;
		Useable useItem;
		BufferedImage img;
		Graphics2D g2;
		if (Inventory.invItems.size() != 0) {
			for (int itemKey : invTRects.keySet()) {
				if ((int) ItemConst.itemSlots.get(itemKey).get(0) != 0) {
					item = ItemConst.itemSlots.get(itemKey).get(1);
					img = Inventory.invImgs.get(((InventoryItem)item).objType-1001);
					g.drawImage(img, ((InventoryItem)item).invRect.x, ((InventoryItem)item).invRect.y, null);
					if (item.getClass() == ETCItem.class) {
						etcItem = (ETCItem)item;
						g2 = (Graphics2D) g;
						g2.setColor(Color.BLACK);
						StringEffect strEff = (StringEffect) etcItem.amountLabel;
						g2.drawString(strEff.label, strEff.point.x, strEff.point.y);
					} else if (item.getClass() == Useable.class) {
						useItem = (Useable)item;
						g2 = (Graphics2D) g;
						g2.setColor(Color.BLACK);
						StringEffect strEff = (StringEffect) useItem.amountLabel;
						g2.drawString(strEff.label, strEff.point.x, strEff.point.y);
					}
				}
			}
			if (Inventory.clickedobj != null) {
				img = Inventory.invImgs.get(Inventory.clickedobj.objType-1001);
				AuxiliaryCalc.ChangeImgAlpha(img, 100);
				g.drawImage(img, Inventory.clickedobj.invRect.x, Inventory.clickedobj.invRect.y, null);
				AuxiliaryCalc.SetImgPixels(img, Inventory.clickedobj.pixels);
			}
		}
		
	}

	public void MovePlayer(int[] direc, int dir, Player ply) {
		Player.cords[dir] -= direc[dir];
		Player.playerRect.x = Player.cords[0];
		Player.playerRect.y = Player.cords[1];
		if (Player.cords[0] < 0 || Player.cords[0] > (GamePanel.size.width-36) || Player.cords[1] < 0 || Player.cords[1] > (GamePanel.size.height-50)) {
			Player.cords[dir] += direc[dir];
		}
		Rectangle modifRect = new Rectangle();
		for (MapObj mapObj : mapObjsList) {
			if (!(mapObj.getClass() == Enemy.class && ((Enemy)mapObj).state == ((Enemy)mapObj).DEAD) && mapObj.getClass() != MapItem.class) {
				modifRect.x =  mapObj.objRect.x - mapPoint.x;
				modifRect.y =  mapObj.objRect.y -mapPoint.y;
				modifRect.width = mapObj.objRect.width;
				modifRect.height = mapObj.objRect.height;
				if (Player.playerRect.intersects(modifRect)) {
					if (mapObj.getClass() == Resource.class && ((Resource)mapObj).state != ((Resource)mapObj).DEAD) {
						clickedObj = mapObj;
					} else {
						clickedObj = null;
					}
					Player.cords[dir] += direc[dir];
					break;
				}
			}
		}
		Player.playerRect.x = Player.cords[0];
		Player.playerRect.y = Player.cords[1];
	}
	
	private void ChangeMapObjAniFrame() {
		Resource resObj;
		MapItem itemObj;
		NPC npcO;
		ArrayList<MapObj> runList = new ArrayList<MapObj>(interMapObj);
		if (aniTick % 50 == 0) {
			for (MapObj mapobj : runList) {
				if (mapobj.getClass() == Resource.class) {
					resObj = (Resource) mapobj;
					resObj.CheckFrameBound();
					if (resObj.state != resObj.DEAD) {
						if (resObj.frame != 3) {
							if (resObj.objRect.contains(new Point (MouseInputs.mousePoint.x + mapPoint.x, MouseInputs.mousePoint.y + mapPoint.y))) {
								if (resObj.frame < 2) {
									resObj.frame++;
								} else {
									resObj.frame = 1;
								}
							} else {
								resObj.frame = 0;
							}
						}
					}
				} else if (mapobj.getClass() == MapItem.class)  {
					itemObj = (MapItem) mapobj;
					if (itemObj.frame != 3) {
						if (itemObj.objRect.contains(MouseInputs.mousePoint.x + mapPoint.x, MouseInputs.mousePoint.y + mapPoint.y)) {
							if (itemObj.frame < 2) {
								itemObj.frame++;
							} else {
								itemObj.frame = 1;
							}
						} else {
							itemObj.frame = 0;
						}
					}
				} else if (mapobj.getClass() == NPC.class)  {
					npcO = (NPC) mapobj;
					if (npcO.frame != 2) {
						if (npcO.objRect.contains(MouseInputs.mousePoint.x + mapPoint.x, MouseInputs.mousePoint.y + mapPoint.y)) {
							npcO.frame = 1;
						} else {
							npcO.frame = 0;
						}
					}
				}
			}
			aniTick = 0;
		}
		if (aniTick % 10 == 0) {
			mapEff = MapEffect.ChangeEffectProps(mapEff);
		}
		MapEffect.LevelUpEffect();
		aniTick += 1;
		runList.clear();
	}
	
	public void CheckCollideWithClickedObj(Player player, int[] dir,Inventory invt) {
		if (clickedObj != null && clickedObj.getClass() == Resource.class) {
			Resource objClicked = (Resource)clickedObj;
			Rectangle tempRect = new Rectangle(Player.playerRect.x-5, Player.playerRect.y-5, Player.playerRect.width+10, Player.playerRect.height+10);
			Rectangle modifyRect = new Rectangle(objClicked.objRect.x -  mapPoint.x, objClicked.objRect.y -  mapPoint.y, objClicked.objRect.width, objClicked.objRect.height);
			if (tempRect.intersects(modifyRect)) {    // if player collides anywhere near 
				if (dir[0] == 0 && dir[1] == 0) {     // only if player is stationary
					Player.line = ChangeIntersectSide(modifyRect, tempRect);  // change animation of activity based on the intersecting side
				}
				Player.playerCollideObjType = objClicked.resourceType;
			} else if (Player.playerCollideObjType != 0) {
				Player.playerCollideObjType = 0;
			}
		}
	}
	
	public int ChangeIntersectSide(Rectangle r1, Rectangle r2) {
		int[] interNums = {0,0,0,0};
		interNums[0] = AuxiliaryCalc.GetNumOfIntersectedPoints(new Point(r2.x, r2.y+r2.height), new Point(r2.x+r2.width, r2.y+r2.height), r1);// line = 0
		interNums[1] = AuxiliaryCalc.GetNumOfIntersectedPoints(new Point(r2.x, r2.y), new Point(r2.x+r2.width, r2.y), r1);// line = 1
		interNums[2] = AuxiliaryCalc.GetNumOfIntersectedPoints(new Point(r2.x+r2.width, r2.y), new Point(r2.x+r2.width, r2.y+r2.height), r1);// line = 2
		interNums[3] = AuxiliaryCalc.GetNumOfIntersectedPoints(new Point(r2.x, r2.y), new Point(r2.x, r2.y+r2.height), r1);// line = 3
		int saveIndex = 0;
		for (int i=1; i < interNums.length ; i++) {
			if (interNums[i] > interNums[i-1]) {
				saveIndex = i;
			}
		}
		return saveIndex;
    }
	
	public void UpdateEnemyPos() {
		ArrayList<Enemy> objEnemy = new ArrayList<Enemy>(enemyObj);
		for (Enemy enemi :  objEnemy) {
			if (enemi.state == enemi.MOVING) {
				enemi.objRect.x += enemi.dir;
				if ((enemi.objRect.x - enemi.startx) >= 200 ) {
					enemi.dir = -1;
					enemi.state = enemi.IDLE;
				} else if ((enemi.objRect.x - enemi.startx) <= 0 ) {
					enemi.dir = 1;
					enemi.state = enemi.IDLE;
				}
			}
			if (enemi.projectileDamage > 0 && (enemi.state == enemi.MOVING || enemi.state == enemi.IDLE || enemi.state == enemi.RANGEATTACKING)) {
				int change = AuxiliaryCalc.CheckInRangeToFire(enemi);
				if (change != -1) {
					enemi.line = 4;
					enemi.row = change;
					enemi.state = enemi.RANGEATTACKING;
				} else {
					enemi.state = enemi.MOVING;
				}
			}
			enemi.ChangeEnemyAnimation();
		}
	}
	
	private void CheckPlayerEnemyCollide(Player player) {
		ArrayList<Enemy> objsEnemy = new ArrayList<Enemy>(enemyObj);
		ArrayList<Enemy> attTargets = new ArrayList<Enemy>();
		boolean underAttack = false;
		Rectangle modifyRect;              //extend rect
		Rectangle tempRect = new Rectangle(Player.playerRect.x - 5, Player.playerRect.y - 5, Player.playerRect.width + 10, Player.playerRect.height + 10);
		for (Enemy objEnemy: objsEnemy) {
			if (objEnemy.state != objEnemy.DEAD && objEnemy.state != objEnemy.SPAWNING) { // ignore dead enemies
				modifyRect = new Rectangle(objEnemy.objRect.x -  mapPoint.x, objEnemy.objRect.y -  mapPoint.y, objEnemy.objRect.width, objEnemy.objRect.height);
				if (tempRect.intersects(modifyRect)) {    // check if extended rect collides
					objEnemy.state = objEnemy.ATTACKING;
					attTargets.add(objEnemy);
					underAttack = true;    // set player under att to change img
					int line = objEnemy.line;
					objEnemy.line = ChangeIntersectSide(tempRect, modifyRect); // get the right animation frame
					if (objEnemy.line < 2 && line >= 2) {
						objEnemy.row = 0;
					} else if (objEnemy.line >= 2 && line < 2) {
						objEnemy.row = 3;
					}
				} else if (objEnemy.state == objEnemy.ATTACKING) { // if specific enemy isnt collide with player set to moving
					objEnemy.state = objEnemy.MOVING;
				}
			}
		}
		if (!underAttack) { // if not under attack set player to idle and target to none;
			Player.underAtt = false;
			if (Player.playerCollideObjType == Player.ATTACKING ) {
				Player.playerCollideObjType = Player.IDLE;
			}
			Player.attTarget = null;
		} else {   // if under attack set player to attacking and get a target for him;
			boolean targetFound = false; 
			Player.underAtt = true;
			Player.playerCollideObjType = Player.ATTACKING; // enable mouse inputs as attacker
			if (Player.attTarget != null) {
				for(Enemy eneObj : attTargets) {
					if (eneObj == Player.attTarget) {
						targetFound = true;
						break;
					}
				}
				if (!targetFound) {
					Player.attTarget = attTargets.get(0);
				}
			} else {
				Player.attTarget = attTargets.get(0);
			}
		}
		objsEnemy.clear();
		attTargets.clear();
	}
	public void importImage() {
		InputStream is;
		BufferedImage img1;
		for (String loca : MapConst.paths) {
			is = getClass().getResourceAsStream(loca);
			try {
				img.add(ImageIO.read(is));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int index = 0;
		for (String loca : MapConst.enemyPaths) {
			is = getClass().getResourceAsStream(loca);
			try {
				img1 =  ImageIO.read(is);
				enemyImg.add(img1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			is = getClass().getResourceAsStream(loca);
			try {
				img1 =  ImageIO.read(is);
				enemyImgDMG.add(AuxiliaryCalc.ChangeImgPixelColor(img1, 0));
				enemyImgDead.add(AuxiliaryCalc.ChangeImgPixelColor(img1.getSubimage(0, 0, MapConst.objProps.get(100+index).get(0), MapConst.objProps.get(100+index).get(1)),1));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			index++;
		}
		index = 0;
		for (String loca : MapConst.resourcePaths) {
			is = getClass().getResourceAsStream(loca);
			try {
				img1 =  ImageIO.read(is);
				resourceImg.add(img1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			is = getClass().getResourceAsStream(loca);
			try {
				img1 =  ImageIO.read(is);
				resourceImgDead.add(AuxiliaryCalc.ChangeImgPixelColor(img1.getSubimage(0, 0, MapConst.objProps.get(20+index).get(0), MapConst.objProps.get(20+index).get(1)),1));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			index++;
		}
		for (String loca : MapConst.interfacePaths) {
			is = getClass().getResourceAsStream(loca);
			try {
				img1 =  ImageIO.read(is);
				interfaceImg.add(img1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int locaKey : MapConst.itempaths.keySet()) {
			is = getClass().getResourceAsStream(MapConst.itempaths.get(locaKey));
			try {
				mapItemImgs.put(locaKey,ImageIO.read(is));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int locaKey : MapConst.projectPaths.keySet()) {
			is = getClass().getResourceAsStream(MapConst.projectPaths.get(locaKey));
			try {
				projectileImg.put(locaKey,ImageIO.read(is));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int i = 0;
		for (String loca : MapConst.npcPaths) {
			is = getClass().getResourceAsStream(loca);
			try {
				npcsImgs.put(50+i,ImageIO.read(is));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		
	}
	
	public void InitMapObj() {
		List<Integer> objLocInts;
		Object objType;
		MapObj mapObj;
		Resource resObj;
		Enemy eneObj;
		MapItem itemObj;
		NPC npcO;
		for (int objKey : MapConst.objLoca.keySet()) {
			objLocInts = MapConst.objLoca.get(objKey);
			if (((objLocInts.get(1) + MapConst.objProps.get(objLocInts.get(0)).get(0)) >= mapPoint.x && objLocInts.get(1) <= (mapPoint.x + GamePanel.size.width)) && ((objLocInts.get(2) + MapConst.objProps.get(objLocInts.get(0)).get(1)) >= mapPoint.y && objLocInts.get(2) <= (mapPoint.y + GamePanel.size.height))) {
				objType = MapConst.objClassTypes.get(objLocInts.get(0));
				if (objType == MapObj.class) {
					mapObj = new MapObj(objKey, objLocInts.get(0), objLocInts.get(1), objLocInts.get(2));
					mapObjsList.add(mapObj);
				} else if (objType == Resource.class) {
					resObj = new Resource(objKey, objLocInts.get(0), objLocInts.get(1), objLocInts.get(2));
					mapObjsList.add(resObj);
					interMapObj.add(resObj);
				} else if (objType == Enemy.class) {
					eneObj = new Enemy(objKey, objLocInts.get(0), objLocInts.get(1), objLocInts.get(2),enemyImg.get(objLocInts.get(0)-100));
					mapObjsList.add(eneObj);
					enemyObj.add(eneObj);
				} else if (objType == MapItem.class) {
					itemObj = new MapItem(objKey, objLocInts.get(0), objLocInts.get(1), objLocInts.get(2), objLocInts.get(4));
					mapObjsList.add(itemObj);
					interMapObj.add(itemObj);
					itemMapObj.add(itemObj);
				}else if (objType == MapItem.class) {
					npcO = new NPC(objKey, objLocInts.get(0), objLocInts.get(1), objLocInts.get(2));
					mapObjsList.add(npcO);
					interMapObj.add(npcO);
					npcObj.add(npcO);
				}
			}
		}
		Dimension size = GamePanel.size;
		MapConst.interfaceLoca.put(0, Arrays.asList(0, 1, size.height-232));
		MapConst.interfaceLoca.put(1, Arrays.asList(1, 78, size.height-222));
		MapConst.interfaceLoca.put(2, Arrays.asList(2, 85, size.height-214));
		MapConst.interfaceLoca.put(3, Arrays.asList(3, 1, size.height-154));
		MapConst.interfaceLoca.put(4, Arrays.asList(1, 78, size.height-143));
		MapConst.interfaceLoca.put(5, Arrays.asList(4, 85, size.height-135));
		MapConst.interfaceLoca.put(6, Arrays.asList(5, 1, size.height-76));
		MapConst.interfaceLoca.put(7, Arrays.asList(1, 78, size.height-65));
		MapConst.interfaceLoca.put(8, Arrays.asList(6, 85, size.height-57));
		MapConst.interfaceLoca.put(9, Arrays.asList(7, (size.width-500)/2, size.height-61));
		MapConst.interfaceLoca.put(11, Arrays.asList(9, (size.width-500)/2 + 505, size.height-80));
		String txt;
		txt = String.valueOf(Player.Hp)+"/"+String.valueOf(Player.maxHp);
		barStrings.put(0,new StringEffect(txt, new Point(160-(2*txt.length()),size.height-188), 3, 0));
		colorStrings.put(0, Color.PINK);
		txt = String.valueOf(Player.Mp)+"/"+String.valueOf(Player.maxMp);
		barStrings.put(1,new StringEffect(txt, new Point(160-(2*txt.length()),size.height-108), 3, 0));
		colorStrings.put(1, new Color(148, 222, 255));
		txt = String.valueOf(Player.Exp)+"/"+String.valueOf(Player.lvlUpExp);
		barStrings.put(2,new StringEffect(txt, new Point(160-(2*txt.length()),size.height-32), 3, 0));
		colorStrings.put(2, new Color(255, 249, 141));
	}
	
	public void drawInterface(Graphics g) {
		Map<Integer, List<Integer>> locaInter = new HashMap <Integer, List<Integer>>(MapConst.interfaceLoca);
		Map<Integer, MapEffect> stringBar = new  HashMap<Integer, MapEffect>(barStrings);
		int i = 0;
		double barSize;
		for (List<Integer> interKey : locaInter.values()) {
			if (i == 2) {
				barSize = ((double)Player.Hp/Player.maxHp)*196;
				if (barSize > 0) {
					g.drawImage(interfaceImg.get(interKey.get(0)).getSubimage(0, 0, (int)barSize, 39),interKey.get(1),interKey.get(2),null);
				}
			} else if (i == 5) {
				barSize = ((double)Player.Mp/Player.maxMp)*196;
				if (barSize > 0) {
					g.drawImage(interfaceImg.get(interKey.get(0)).getSubimage(0, 0, (int)barSize, 39),interKey.get(1),interKey.get(2),null);
				}
			} else if (i == 8) {
				barSize = ((double)Player.Exp/Player.lvlUpExp)*196;
				if (barSize > 0) {
					g.drawImage(interfaceImg.get(interKey.get(0)).getSubimage(0, 0, (int)barSize, 39),interKey.get(1),interKey.get(2),null);
				}
			} else {
				g.drawImage(interfaceImg.get(interKey.get(0)),interKey.get(1),interKey.get(2),null);
			}
			i++;
		}
		Font font = new Font("Forte", Font.BOLD, 20);
		Graphics2D g2;
		g2 = (Graphics2D) g;
		Font oldFont = g2.getFont();
		g2.setFont(font);
		StringEffect strEff;
		for (int effKey : stringBar.keySet()) {
			g2.setColor(colorStrings.get(effKey));
			strEff = (StringEffect) stringBar.get(effKey);
			g2.drawString(strEff.label, strEff.point.x, strEff.point.y);
		}
		if (levelUpEffect.size() != 0) {
			Font font1 = new Font("Ravie", Font.BOLD, 50);
			g2.setFont(font1);
			for (MapEffect mEff : levelUpEffect) {
				strEff = (StringEffect) mEff;
				g2.setColor(new Color(20,29+(MapEffect.stringIndex*10),62+(MapEffect.stringIndex*10)));
				g2.drawString(strEff.label, strEff.point.x, strEff.point.y);
			}
		}
		g2.setFont(oldFont);
	}
	
	public static void SaveMapObjects() {
		Map<Integer, List<Integer>> locObj = new HashMap <Integer, List<Integer>>(MapConst.objLoca);
		FileWriter myWriter;
		try {
		      File file = new File("mapobject.vla");
		      if (file.createNewFile()) {
		    	  System.out.println("File created: " + file.getName());
		      } else {
		    	  System.out.println("File already exists.");
		      }
		      myWriter = new FileWriter("mapobject.vla");
		      for (int objKey : locObj.keySet()){
		    	  if (locObj.get(objKey).size() == 3) {
		    		  myWriter.write(String.valueOf(objKey)+','+String.valueOf(locObj.get(objKey).get(0))+','+String.valueOf(locObj.get(objKey).get(1))+','+String.valueOf(locObj.get(objKey).get(2))+',');
		    		  myWriter.write(System.getProperty( "line.separator" ));
		    	  }
	    	  }
		      myWriter.close();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
	}
	
	public static void LoadMapObjects() {
		BufferedReader reader;
		ArrayList<MapEffect> upEffs = new ArrayList<MapEffect>(Inventory.upgraderEffects);
		try {
	      File file = new File("mapobject.vla");
	      if (file.createNewFile()) {
	    	  System.out.println("File created: " + file.getName());
	    	  reader = new BufferedReader(new FileReader("mapobjectori.vla"));
	      } else {
	    	  System.out.println("File already exists.");
	    	  reader = new BufferedReader(new FileReader("mapobject.vla"));
	      }
	      int chrCounter = 0, key = 0, objId = 0, xcord = 0, ycord = 0;
    	  String asmbString = "";
    	  while (true) {
    		  chrCounter = 0;
    		  String txtLine = reader.readLine();
    		  if (txtLine == null) {
    			  break;
    		  }
    		  for (char chr : txtLine.toCharArray()) {
    			  if (chr == ',') {
    				  chrCounter++;
    				  switch (chrCounter) {
    				  	case 1:
    				  		key = Integer.parseInt(asmbString);
    				  		break;
    				  	case 2:
    				  		objId = Integer.parseInt(asmbString);
    				  		break;
    				  	case 3:
    				  		xcord = Integer.parseInt(asmbString);
    				  		break;
    				  	case 4:
    				  		ycord = Integer.parseInt(asmbString);
    				  		break;
    				  }
    				  asmbString = "";
    			  } else {
    				  asmbString += chr;
    			  }
    		  }
    		  MapConst.objLoca.put(key, Arrays.asList(objId,xcord,ycord));
    	  }
	      reader.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		System.out.println(MapConst.objLoca.size());
		Inventory.upgraderEffects  = new ArrayList<MapEffect>(upEffs);
	}
}
