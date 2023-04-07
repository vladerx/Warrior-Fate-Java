package main;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

abstract class MapHelper {
	
	public static void CheckMouseClickInRect(Point point, Map<Integer, List<Integer>> TypeRects, int mode) {
		Rectangle rect = new Rectangle();
		int targetSlotType = 0;
		for(int rectKey : TypeRects.keySet()) { // search for inv rects that collide with point
			if (mode == 2 || mode == 3 || mode == 4 || mode == 5 || mode == 6) {
				rect.x = TypeRects.get(rectKey).get(0);
				rect.y = TypeRects.get(rectKey).get(1);
				rect.width = TypeRects.get(rectKey).get(2);
				rect.height = TypeRects.get(rectKey).get(3);
			} else {
				rect.x = TypeRects.get(rectKey).get(1);
				rect.y = TypeRects.get(rectKey).get(2);
				rect.width = TypeRects.get(rectKey).get(3);
				rect.height =TypeRects.get(rectKey).get(4);
				targetSlotType = TypeRects.get(rectKey).get(0);
			}
			if (rect.contains(point)) {
				//System.out.println(rectKey);
				if (mode == 0 || mode == 1) {
					ExcuteInventorySlotAction(rectKey, targetSlotType, rect);
				} else if (mode == 2) {
					ExecuteSkillSlotAction(rectKey,rect);
				} else if (mode == 3) {
					ExecuteAbilityButtonAction(rectKey);
				} else if (mode == 4 && rectKey != 0) {
					ExecuteNPCDialogueButtonAction(rectKey);
				} else if (mode == 5) {
					ExecuteQuestTabAction(rectKey);
				} else if (mode == 6) {
					ExecuteMenuButtonAction(rectKey);
				}
				break;
			}
		}
	}
	
	private static void ExecuteMenuButtonAction(int buttonKey) {
		switch (buttonKey) {
			case 0:
				ArrayList<String> fileLocs = new ArrayList<String>() {{
					add("data.vla");
					add("inv.vla");
					add("quest.vla");
					add("skill.vla");
					add("mapobject.vla");
				}};
				for (String loca : fileLocs) {
				      File file = new File(loca);
				      if (file.exists()) {
				    	  file.delete();
				      }
				}
				Player.LoadPlayerStats();
				Inventory.LoadInventoryItems();
				QuestTab.LoadQuests();
				SkillTab.LoadSkills();
				Mapa.LoadMapObjects();
				Player.SavePlayerStats();
				Inventory.SaveInventoryItems();
				QuestTab.SaveQuests();
				SkillTab.SaveSkills();
				Mapa.SaveMapObjects();
				Menu.state = Menu.GAMEON;
				break;
			case 1:
				Player.LoadPlayerStats();
				Inventory.LoadInventoryItems();
				QuestTab.LoadQuests();
				SkillTab.LoadSkills();
				Mapa.LoadMapObjects();
				Menu.state = Menu.GAMEON;
				break;
			case 2:
				System.exit(0);
				break;
		}
		
	}

	private static void ExecuteQuestTabAction(int rectKey) {
		if (rectKey == 5 && QuestTab.page > 0) {
			QuestTab.pageChanged = true;
			QuestTab.page--;
		} else if (rectKey == 6 && QuestTab.page < QuestTab.pages) {
			QuestTab.pageChanged = true;
			QuestTab.page++;
		}
	}

	private static void ExecuteNPCDialogueButtonAction(int targetKey) {
		Map<Integer, List<Integer>> locsObj = new HashMap <Integer, List<Integer>>(MapConst.objLoca);
		NPC npcO = (NPC)Mapa.clickedObj;
		if (targetKey == 1 && NPCDialogue.questTarget != -1) {
			if (((int)QuestConst.questsInterface.get(NPCDialogue.questTarget).get(1)) == QuestConst.NOTSTARTED) {
				QuestConst.questsInterface.get(NPCDialogue.questTarget).set(1, QuestConst.INPROGRESS);
			} else if (((int)QuestConst.questsInterface.get(NPCDialogue.questTarget).get(1)) == QuestConst.COMPLETED) {
				Map<Integer, List<Integer>> rewardsList = new HashMap <Integer, List<Integer>>(QuestConst.questRewards);
				QuestConst.questsInterface.get(NPCDialogue.questTarget).set(1, QuestConst.REWARDED);
				List<Integer> rewards = rewardsList.get(NPCDialogue.questTarget);
				Player.ChangePlayerExp(rewards.get(0));
				int xCord = npcO.objRect.x;
				for (int i = 1; i< rewards.size(); i += 2) {
					xCord += MapConst.objProps.get(rewards.get(i)).get(0)+i;
					locsObj.put(AuxiliaryCalc.GetFreeMapObjectKey(locsObj), Arrays.asList(rewards.get(i), xCord, npcO.objRect.y, rewards.get(i+1), 2));
				}
				MapConst.objLoca = new HashMap <Integer, List<Integer>>(locsObj);
			}
			Mapa.state = Mapa.GAMEPLAY;
			npcO.frame = 0;
			NPCDialogue.questTarget = -1;
		} else {
			if (((int)QuestConst.questsInterface.get(NPCDialogue.questTarget).get(1)) == QuestConst.COMPLETED) {
				Map<Integer, List<Integer>> rewardsList = new HashMap <Integer, List<Integer>>(QuestConst.questRewards);
				QuestConst.questsInterface.get(NPCDialogue.questTarget).set(1, QuestConst.REWARDED);
				List<Integer> rewards = rewardsList.get(NPCDialogue.questTarget);
				Player.ChangePlayerExp(rewards.get(0));
				int xCord = npcO.objRect.x;
				for (int i = 1; i< rewards.size(); i += 2) {
					xCord += MapConst.objProps.get(rewards.get(i)).get(0)+i;
					locsObj.put(AuxiliaryCalc.GetFreeMapObjectKey(locsObj), Arrays.asList(rewards.get(i), xCord, npcO.objRect.y, rewards.get(i+1), 2));
				}
				MapConst.objLoca = new HashMap <Integer, List<Integer>>(locsObj);
			}
			Mapa.state = Mapa.GAMEPLAY;
			npcO.frame = 0;
			NPCDialogue.questTarget = -1;
		}
		
	}
	
	public static void CheckClickedMapObject() {
		ArrayList<MapObj> runList = new ArrayList<MapObj>(Mapa.interMapObj);
		for (MapObj mapobj : runList) { //if click on obj get its type
			if (mapobj.objRect.contains(new Point (MouseInputs.mousePoint.x + Mapa.mapPoint.x, MouseInputs.mousePoint.y + Mapa.mapPoint.y))) {
				if (mapobj.getClass() == Resource.class) {
					((Resource) mapobj).frame = 3;
					Mapa.clickedObj = mapobj;
				} else if (mapobj.getClass() == NPC.class)  {
					((NPC) mapobj).frame = 2;
					Mapa.clickedObj = mapobj;
					NPCDialogue.npcPic = Mapa.npcsImgs.get(mapobj.objType).getSubimage(0, 0, 50, 50);
					if (NPCDialogue.UpdateNPCDialogue() != -1) {
						Mapa.state = Mapa.DIALOGUE;
					}
				}
			} else { // reset other frames if clicked anywhere else
				if (mapobj.getClass() == Resource.class) {
					((Resource) mapobj).frame = 0;
				} else if (mapobj.getClass() == NPC.class) {
					((NPC) mapobj).frame = 0;
				}
			}
		}
		runList.clear();
	}
	
	private static void ExecuteSkillSlotAction(int targetKey, Rectangle rect) {
		boolean found = false;
		if (targetKey < 4 && SkillTab.clickedSkill != null) {
			for (int skillKey : SkillConst.skillSlots.keySet()) {
				if ((int)SkillConst.skillSlots.get(skillKey).get(0) == SkillTab.clickedSkill.objType) {
					found = true;
					SkillTab.clickedSkill = null;
					break;
				}
			}
			if(!found) {
				if (SkillConst.skillSlots.get(targetKey).get(1) != null) {
					SkillTab.skillList.remove(SkillConst.skillSlots.get(targetKey).get(1));
				}
	
				SkillConst.skillSlots.get(targetKey).set(0, SkillTab.clickedSkill.objType);
				SkillConst.skillSlots.get(targetKey).set(1, SkillTab.clickedSkill);
				SkillTab.clickedSkill.skillSlot = targetKey;
				SkillTab.skillList.add(SkillTab.clickedSkill);
				SkillTab.clickedSkill = null;
			}
		} else if (targetKey < 4 && SkillTab.clickedSkill == null && SkillConst.skillSlots.get(targetKey).get(1) != null) {
			SkillTab.clickedSkill = (Skill)SkillConst.skillSlots.get(targetKey).get(1);
			SkillTab.skillList.remove(SkillTab.clickedSkill);
			SkillConst.skillSlots.get(targetKey).set(0, -1);
			SkillConst.skillSlots.get(targetKey).set(1, null);
		}
		if (targetKey > 3 && targetKey < 10 && (targetKey - 4 != 5)) {
			if ( SkillConst.skillProps.get(targetKey-4).get(6) != 0) {
				switch(SkillConst.skillProps.get(targetKey-4).get(0)) {
					case SkillConst.ACTIVE:
						SkillTab.clickedSkill = new ActiveSkill(-1, targetKey-4);
						break;
					case SkillConst.BUFF:
						SkillTab.clickedSkill = new BuffSkill(-1, targetKey-4);
						break;
					case SkillConst.PROJECTILE:
						SkillTab.clickedSkill = new ProjectileSkill(-1, targetKey-4);
						break;
				}
			}
		}
		if (Player.skillPoints > 0 && targetKey > 9) {
			int skillLvl = SkillConst.skillProps.get(targetKey-10).get(6);
			SkillConst.skillProps.get(targetKey-10).set(6, ++skillLvl);
			Player.skillPoints -= 1;
			UpdatePlayerSkillStrings();
			if (SkillConst.skillProps.get(targetKey-10).get(0) == SkillConst.PASSIVE) {
				switch(targetKey-10) {
					case 5:
						Player.accuracy = SkillConst.skillProps.get(5).get(1) + (SkillConst.skillProps.get(5).get(2))*SkillConst.skillProps.get(5).get(6);
						break;
				}
			}
			System.out.println(Player.accuracy);
		}
		
	}
	
	public static void ExecuteAbilityButtonAction (int buttonKey) {
		if (Player.ablilityPoints > 0) {
			switch(buttonKey) {
				case 0:
					Player.baseStrength += 1;
					break;
				case 1:
					Player.baseDefense += 1;
					break;
				case 2:
					Player.baseAgility += 1;
					break;
				case 3:
					Player.baseLuck += 1;
					break;
			}
			Player.ablilityPoints -= 1;
		}
	}

	private static void UpdatePlayerSkillStrings() {
		Map<Integer, MapEffect> effMap = new HashMap<Integer, MapEffect>(SkillTab.mapEff);
		Dimension size = GamePanel.size;
		int x = (size.width-500)/2 + 455;
		StringEffect strEeff;
		for (int effKey : effMap.keySet()) {
			if (effKey > 3 && effKey < 10) {
				strEeff = (StringEffect)effMap.get(effKey);
				strEeff.label = String.valueOf(SkillConst.skillProps.get(effKey-4).get(6));
				switch (effKey) {
					case 4:
						strEeff.point.x = (x+19+(String.valueOf(strEeff.label).length()));
						break;
					case 5:
						strEeff.point.x = (x+73+(String.valueOf(strEeff.label).length()));
						break;
					case 6:
						strEeff.point.x = (x+127+(String.valueOf(strEeff.label).length()));
						break;
					case 7:
						strEeff.point.x = (x+181+(String.valueOf(strEeff.label).length()));
						break;
					case 8:
						strEeff.point.x = (x+235+(String.valueOf(strEeff.label).length()));
						break;
					case 9:
						strEeff.point.x = (x+289+(String.valueOf(strEeff.label).length()));
						break;
				}
			}
		}
		x = (size.width-500)/2 + 455;
		StringEffect mEff = (StringEffect)SkillTab.skillPointsString;
		mEff.label = String.valueOf(Player.skillPoints);
		mEff.point.x = (x+177-(3*String.valueOf(Player.skillPoints).length())); 
	}
	public static void SwitchSlotAndMouse(int targetSlot, InventoryItem targetObj, InventoryItem mouseObj, Rectangle targetRect) {
		InventoryItem invItem;
		InventoryItem tempItem;
		Useable useItem;
		ETCItem etcItem;
		BufferedImage img;
		ArrayList<MapEffect> upEffs = new ArrayList<MapEffect>(Inventory.upgraderEffects);
		UpdatePlayerEquipStats(targetObj, mouseObj, targetSlot);
		if (mouseObj != null && targetObj!= null) { // if mouse slot and inv slot arnt empty
			if (targetObj.objType == mouseObj.objType && targetObj.getClass() != Equip.class) {//check if same type of item and not equip type
				CheckAndChangeAmounts(mouseObj, targetObj, targetSlot); // adjust items/amounts
			} else {
				// creating new label in the target slot
				if (mouseObj.getClass() == Useable.class) { 
					useItem = (Useable)mouseObj;
					useItem.amountLabel = new StringEffect(String.valueOf(mouseObj.amount), new Point(ItemConst.invTypeRects.get(targetSlot).get(1)+30-(5*String.valueOf(mouseObj.amount).length()),ItemConst.invTypeRects.get(targetSlot).get(2)+37),-1, 0);;
				} else if (mouseObj.getClass() == ETCItem.class) {
					etcItem = (ETCItem)mouseObj;
					etcItem.amountLabel = new StringEffect(String.valueOf(mouseObj.amount), new Point(ItemConst.invTypeRects.get(targetSlot).get(1)+30-(5*String.valueOf(mouseObj.amount).length()),ItemConst.invTypeRects.get(targetSlot).get(2)+37),-1, 0);;
				} else if (mouseObj.getClass() == Equip.class && ItemConst.invTypeRects.get(targetSlot).get(0) != ItemConst.FREE) {
					if (!CheckStatsReq(mouseObj.objType)) {
						return;
					}
				}
				if (targetSlot == 66) {
					upEffs.clear();
					ArrayList<Integer> reqs = new ArrayList<Integer>(ItemConst.upgradeReqs.get(mouseObj.objType));
					for (int i=0, k = 4; i<reqs.size();i+=2, k++) {
						upEffs.add(new StringEffect("X "+String.valueOf(reqs.get(i+1)), Inventory.locPoints.get(k), 2, 0));
					}
				}
				//removing label from inv slot
				if (targetObj.getClass() == Useable.class) {
					useItem = (Useable)targetObj;
					useItem.amountLabel = null;
				} else if (targetObj.getClass() == ETCItem.class) {
					etcItem = (ETCItem)targetObj;
					etcItem.amountLabel = null;
				}
				//switching places between mouse slot and inv slot
				invItem = targetObj;
				tempItem = mouseObj;
				Inventory.clickedobj = invItem;
				img = Inventory.invImgs.get(tempItem.objType-1001);
				AuxiliaryCalc.SetImgPixels(img, tempItem.pixels);
				tempItem.invRect.x = targetRect.x+((targetRect.width-img.getWidth())/2);
				tempItem.invRect.y = targetRect.y+((targetRect.height-img.getHeight())/2);
				ItemConst.itemSlots.get(targetSlot).set(1, tempItem);
				ItemConst.itemSlots.get(targetSlot).set(0, tempItem.objType);
			}
		} else if (mouseObj == null && targetObj!= null) {
			//removing label from target slot
			if (targetObj.getClass() == Useable.class) {
				useItem = (Useable)targetObj;
				useItem.amountLabel = null;
			} else if (targetObj.getClass() == ETCItem.class) {
				etcItem = (ETCItem)targetObj;
				etcItem.amountLabel = null;
			}
			if (targetSlot == 66) {
				upEffs.clear();
			}
			//moving obj to mouse slot and removing from inv slot
			Inventory.clickedobj = targetObj;
			ItemConst.itemSlots.replace(targetSlot, Arrays.asList(0, null));
		} else if (mouseObj != null && targetObj == null) {
			// creating new label in target slot
			if (mouseObj.getClass() == Useable.class) { 
				useItem = (Useable)mouseObj;
				useItem.amountLabel = new StringEffect(String.valueOf(mouseObj.amount), new Point(ItemConst.invTypeRects.get(targetSlot).get(1)+30-(5*String.valueOf(mouseObj.amount).length()),ItemConst.invTypeRects.get(targetSlot).get(2)+37),-1, 0);
			} else if (mouseObj.getClass() == ETCItem.class) {
				etcItem = (ETCItem)mouseObj;
				etcItem.amountLabel = new StringEffect(String.valueOf(mouseObj.amount), new Point(ItemConst.invTypeRects.get(targetSlot).get(1)+30-(5*String.valueOf(mouseObj.amount).length()),ItemConst.invTypeRects.get(targetSlot).get(2)+37),-1, 0);
			} else if (mouseObj.getClass() == Equip.class && ItemConst.invTypeRects.get(targetSlot).get(0) != ItemConst.FREE) {
				if (!CheckStatsReq(mouseObj.objType)) {
					return;
				}
			}
			if (targetSlot == 66) {
				ArrayList<Integer> reqs = new ArrayList<Integer>(ItemConst.upgradeReqs.get(mouseObj.objType));
				for (int i=0, k = 4; i<reqs.size();i+=2, k++) {
					upEffs.add(new StringEffect("X "+String.valueOf(reqs.get(i+1)), Inventory.locPoints.get(k), 2, 0));
				}
			}
			//moving obj from mouse slot to inv slot
			tempItem = mouseObj;
			img = Inventory.invImgs.get(tempItem.objType-1001);
			AuxiliaryCalc.SetImgPixels(img, tempItem.pixels);
			tempItem.invRect.x = targetRect.x+((targetRect.width-img.getWidth())/2);
			tempItem.invRect.y = targetRect.y+((targetRect.height-img.getHeight())/2);
			ItemConst.itemSlots.get(targetSlot).set(1, tempItem);
			ItemConst.itemSlots.get(targetSlot).set(0, tempItem.objType);
			Inventory.clickedobj = null;
		}
		Inventory.upgraderEffects = new ArrayList<MapEffect>(upEffs);
	}
	
	private static void CheckAndChangeAmounts(InventoryItem mouseObj, InventoryItem targetObj, int targetSlot) {
		Useable useItem;
		ETCItem etcItem;
		StringEffect strEff;
		if (targetObj.amount + mouseObj.amount > 999) {
			mouseObj.amount = targetObj.amount + mouseObj.amount - 999;
			targetObj.amount = 999;
		} else {
			targetObj.amount += mouseObj.amount;
			Inventory.invItems.remove(mouseObj);
			Inventory.clickedobj = null;
		}
		if (targetObj.getClass() == Useable.class) {
			useItem = (Useable)targetObj;
			strEff = (StringEffect)useItem.amountLabel;
			strEff.label = String.valueOf(targetObj.amount);
			strEff.point.x = ItemConst.invTypeRects.get(targetSlot).get(1)+30-(5*strEff.label.length());
			strEff.point.y = ItemConst.invTypeRects.get(targetSlot).get(2)+37;
		} else if (targetObj.getClass() == ETCItem.class) {
			etcItem = (ETCItem)targetObj;
			strEff = (StringEffect)etcItem.amountLabel;
			strEff.label = String.valueOf(targetObj.amount);
			strEff.point.x = ItemConst.invTypeRects.get(targetSlot).get(1)+30-(5*strEff.label.length());
			strEff.point.y = ItemConst.invTypeRects.get(targetSlot).get(2)+37;
		}
	}

	private static boolean CheckStatsReq(int objType) {
		List<Object> objList =  ItemConst.itemProps.get(objType);
		if (((int)objList.get(7)) > Player.level) {
			return false;
		}
		if (((int)objList.get(8)) > Player.baseStrength) {
			return false;
		}
		if (((int)objList.get(9)) > Player.baseDefense) {
			return false;
		}
		if (((int)objList.get(10)) > Player.baseAgility) {
			return false;
		}
		if (((int)objList.get(11)) > Player.baseLuck) {
			return false;
		}
		return true;
	}

	public static void ExcuteInventoryButtonAction(int buttonSlot) {
		switch(buttonSlot) {
			case 67: //removing items that dragged to trash
				if (Inventory.clickedobj != null) {
					ArrayList<InventoryItem> Items = new ArrayList<InventoryItem>(Inventory.invItems);
					Items.remove(Inventory.clickedobj);
					Inventory.clickedobj = null;
					Inventory.invItems = new ArrayList<InventoryItem>(Items);
				}
				break;
			case 68: //adding stats and removing upgrade slot
				InventoryItem invItem;
				ETCItem etcItem;
				Useable useItem;
				Equip equip;
				if (ItemConst.itemSlots.get(66).get(1) != null && ((Equip)ItemConst.itemSlots.get(66).get(1)).upgradeSlots > 0) {
					Map<Integer, Integer> slotsAmount = new HashMap <Integer, Integer>();
					Map<Integer, List<Object>> slotsItem = new HashMap <Integer, List<Object>>(ItemConst.itemSlots);
					ArrayList<Integer> reqs = new ArrayList<Integer>(ItemConst.upgradeReqs.get(((InventoryItem)ItemConst.itemSlots.get(66).get(1)).objType));
					for (int i = 0; i < reqs.size(); i+=2) {
						for (int j = 6 ; j < 54; j++) {
							if (reqs.get(i) != 1000 && slotsItem.get(j).get(1) != null) {
								if ((int)slotsItem.get(j).get(0) == reqs.get(i) && ((InventoryItem)slotsItem.get(j).get(1)).amount >= reqs.get(i+1) ) {
									slotsAmount.put(j, ((InventoryItem)slotsItem.get(j).get(1)).amount - reqs.get(i+1));
								}
							} else if (reqs.get(i) == 1000) {
								if ( Inventory.coins  >= reqs.get(i+1)) {
									slotsAmount.put(reqs.get(i), Inventory.coins - reqs.get(i+1));
								}
							}
						}
					}
					if (reqs.size()/2 == slotsAmount.size()) {
						for (int slot : slotsAmount.keySet()) {
							if (slot != 1000) {
								invItem = (InventoryItem)slotsItem.get(slot).get(1);
								invItem.amount = slotsAmount.get(slot);
								if (invItem.amount == 0) {
									slotsItem.get(slot).set(0, 0);
									slotsItem.get(slot).set(1, null);
									Inventory.invItems.remove(invItem);
								} else {
									if (invItem.getClass() == ETCItem.class) {
										etcItem = (ETCItem)invItem;
										((StringEffect)etcItem.amountLabel).label = String.valueOf(slotsAmount.get(slot));
									} else if (invItem.getClass() == Useable.class) {
										useItem = (Useable)invItem;
										((StringEffect)useItem.amountLabel).label = String.valueOf(slotsAmount.get(slot));
									}
								}
							} else {
								Inventory.coins = slotsAmount.get(slot);
							}
						}
						//add stats to item
						if (slotsItem.get(66).get(1).getClass() == Equip.class) {
							Random rand = new Random();
							equip = (Equip)slotsItem.get(66).get(1);
							for (int i = 2; i < 6; i++) {
								if ((int)ItemConst.itemProps.get(equip.objType).get(i) > 0) {
									switch(i) {
										case 2:
											equip.attack += rand.nextInt(1,4);
											break;
										case 3:
											equip.defense += rand.nextInt(1,4);;
											break;
										case 4:
											equip.agility += rand.nextInt(1,4);;
											break;
										case 5:
											equip.luck += rand.nextInt(1,4);;
											break;
									}
								}
							}
							equip.upgradeSlots-= 1;
						}
					}
				}
				break;
		}
	}
	
	public static int[] ModifyInventorySlots(int itemType, int objKey) {
		ETCItem etcItem;
		Useable useItem;
		StringEffect strEff;
		if (itemType != 1000) { // if coin just return
			Map<Integer, Integer> slotsAmount = new HashMap<Integer, Integer>();
			int amountLeft = MapConst.objLoca.get(objKey).get(3);
			int slotAmount = 0;
			boolean done = false;
			if (ItemConst.itemProps.get(itemType).get(0) == ETCItem.class || ItemConst.itemProps.get(itemType).get(0) == Useable.class) {
				for (int i = 6; i < 54; i++) { // search for same item type
					if ((int)ItemConst.itemSlots.get(i).get(0) == itemType) {
						slotAmount = ((InventoryItem)ItemConst.itemSlots.get(i).get(1)).amount;
						if ((amountLeft + slotAmount) > 999) {
							slotsAmount.put(i, 999); // fill slots with 999
							amountLeft -= (999 - slotAmount); // decrease the amount left
						} else {
							slotsAmount.put(i, amountLeft + slotAmount); //add the remainder to the last space
							done = true;
							break;
						}
					}
				}
				if (done) { //sort the amount between all slots
					for (int slotKey : slotsAmount.keySet()) {
						Object obj = ItemConst.itemSlots.get(slotKey).get(1);
						if (obj.getClass() == ETCItem.class) {
							etcItem = (ETCItem)obj;
							etcItem.amount = slotsAmount.get(slotKey);
							strEff = (StringEffect)etcItem.amountLabel;
							strEff.label = String.valueOf(slotsAmount.get(slotKey));
							strEff.point.x = ItemConst.invTypeRects.get(slotKey).get(1)+30-(5*String.valueOf(slotsAmount.get(slotKey)).length());
							strEff.point.y = ItemConst.invTypeRects.get(slotKey).get(2)+37;
						} else if(obj.getClass() == Useable.class) {
							useItem = (Useable) obj;
							useItem.amount = slotsAmount.get(slotKey);
							strEff = (StringEffect)useItem.amountLabel;
							strEff.label = String.valueOf(slotsAmount.get(slotKey));
							strEff.point.x = ItemConst.invTypeRects.get(slotKey).get(1)+30-(5*String.valueOf(slotsAmount.get(slotKey)).length());
							strEff.point.y = ItemConst.invTypeRects.get(slotKey).get(2)+37;
						}
						ItemConst.itemSlots.get(slotKey).set(0, itemType);
						ItemConst.itemSlots.get(slotKey).set(1, obj);
					}
					return new int[] {-2, -1};
				} else { //check if there is a space for the remainder else return no free slot
					for (int i = 6; i < 54; i++) {
						if ((int)ItemConst.itemSlots.get(i).get(0) == 0) {
							for (int keySlot : slotsAmount.keySet()) { //add all saved values for slots
								Object obj = ItemConst.itemSlots.get(keySlot).get(1);
								if (obj.getClass() == ETCItem.class) {
									etcItem = (ETCItem)obj;
									etcItem.amount = slotsAmount.get(keySlot);
									strEff = (StringEffect)etcItem.amountLabel;
									strEff.label = String.valueOf(slotsAmount.get(keySlot));
									strEff.point.x = ItemConst.invTypeRects.get(keySlot).get(1)+30-(5*String.valueOf(slotsAmount.get(keySlot)).length());
									strEff.point.y = ItemConst.invTypeRects.get(keySlot).get(2)+37;
								} else if(obj.getClass() == Useable.class) {
									useItem = (Useable) obj;
									useItem.amount = slotsAmount.get(keySlot);
									strEff = (StringEffect)useItem.amountLabel;
									strEff.label = String.valueOf(slotsAmount.get(keySlot));
									strEff.point.x = ItemConst.invTypeRects.get(keySlot).get(1)+30-(5*String.valueOf(slotsAmount.get(keySlot)).length());
									strEff.point.y = ItemConst.invTypeRects.get(keySlot).get(2)+37;
								}
								ItemConst.itemSlots.get(keySlot).set(0, itemType);
								ItemConst.itemSlots.get(keySlot).set(1, obj);
							}
							return new int[] {i, amountLeft};
						}
					}
				}
			} else {
				for (int slotKey : ItemConst.itemSlots.keySet()) {
					if ((int)ItemConst.itemSlots.get(slotKey).get(0) == 0 && slotKey > 5 && slotKey < 54) {
						return new int[] {slotKey, -1};
					}
				}
			}
			
		}
		return new int[] {-1, -1};
	}
	
	public static void ExcuteInventorySlotAction(int rectKey,int targetSlotType, Rectangle rect) {
		InventoryItem tempItem;
		int sourceItemType = 0;
		if (rectKey < 67) { // only inv slots, trash slots get different actions
			tempItem = null;
			if (ItemConst.itemSlots.get(rectKey).get(1) != null) { // get the obj in target slot otherwise stays null
				tempItem = (InventoryItem)ItemConst.itemSlots.get(rectKey).get(1);
			}
			if (Inventory.clickedobj != null) { // get the obj in mouse slot otherwise stays null
				sourceItemType = Inventory.clickedobj.invType;
			} // move only to same type slots, free, or move item to mouse slot 
			if (sourceItemType == targetSlotType || targetSlotType == ItemConst.FREE || Inventory.clickedobj == null || (rectKey == 66 && sourceItemType < 6)) {
				SwitchSlotAndMouse(rectKey, tempItem, Inventory.clickedobj, rect);
			}
		} else {
			ExcuteInventoryButtonAction(rectKey);
		}
	}
	
	public static void UseUseableItem(int SlotKey) {
		Useable useItem = (Useable) ItemConst.itemSlots.get(SlotKey).get(1);
		if (useItem != null) {
			useItem.amount--;
			StringEffect itemEff = (StringEffect)useItem.amountLabel;
			int labelNum = Integer.parseInt(itemEff.label);
			labelNum--;
			itemEff.label = String.valueOf(labelNum);
			List<Integer> itemProps = ItemConst.useableProps.get(useItem.objType);
			int i = 0;
			for (int prop : itemProps) {
				switch(prop) {
					case ItemConst.HpRecovery:
						Player.ChangePlayerHP((int)ItemConst.itemProps.get(useItem.objType).get(2+i));
						break;
					case ItemConst.MpRecovery:
						Player.ChangePlayerMP((int)ItemConst.itemProps.get(useItem.objType).get(2+i));
						break;
				}
				i++;
			}
			if (useItem.amount<1) {
				ItemConst.itemSlots.get(SlotKey).set(0, 0);
				ItemConst.itemSlots.get(SlotKey).set(1, null);
				Inventory.invItems.remove(useItem);
			}
		}
	}
	
	public static void UpdatePlayerEquipStats(InventoryItem target, InventoryItem source, int targetSlot) {
		if (targetSlot < 6) {
			Equip equp;
			if (target != null) {
				equp = (Equip)target;
				Player.baseWeaponAttack -= equp.attack;
				Player.baseDefense -= equp.defense;
			}
			if (source != null) {
				equp = (Equip)source;
				Player.baseWeaponAttack += equp.attack;
				Player.baseDefense += equp.defense;
			}
		}
	}
	
	public static boolean CheckSlotsItems (List<Integer> questReqs) {
		Map<Integer, List<Object>> slots = new HashMap <Integer, List<Object>>(ItemConst.itemSlots);
		ArrayList<InventoryItem> intvs = new ArrayList<InventoryItem>(Inventory.invItems);
		int newAmount;
		for (int slotKey : slots.keySet()) {
			if (slots.get(slotKey).get(1) != null) {
				for (int i=0; i<questReqs.size(); i+=2) {
					if ((((int)slots.get(slotKey).get(0)) == questReqs.get(i) && ((InventoryItem)slots.get(slotKey).get(1)).amount >= questReqs.get(i+1)) || (((InventoryItem)slots.get(slotKey).get(1)).amount == 1) && (questReqs.get(i+1) == 1)) {
						if (((InventoryItem)slots.get(slotKey).get(1)).amount == 1) {
							newAmount = 0;
						} else {
							newAmount = (((InventoryItem)slots.get(slotKey).get(1)).amount) -  questReqs.get(i+1);
						}
						if (newAmount != 0) {
							if (slots.get(slotKey).get(1).getClass() == ETCItem.class) {
								ETCItem itemETC = (ETCItem)slots.get(slotKey).get(1);
								((StringEffect)itemETC.amountLabel).label = String.valueOf(newAmount);
							} else if (slots.get(slotKey).get(1).getClass() == Useable.class) {
								Useable itemUse = (Useable)slots.get(slotKey).get(1);
								((StringEffect)itemUse.amountLabel).label = String.valueOf(newAmount);
							}
							((InventoryItem)slots.get(slotKey).get(1)).amount = newAmount;
						} else {
							intvs.remove(slots.get(slotKey).get(1));
							slots.get(slotKey).set(0, 0);
							slots.get(slotKey).set(1, null);
						}
						Inventory.invItems = new ArrayList<InventoryItem>(intvs);
						ItemConst.itemSlots =  new HashMap <Integer, List<Object>>(slots);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void SetSkillAction(int slot) {
		if (SkillConst.skillSlots.get(slot).get(1) != null) {
			Object skillObj = SkillConst.skillSlots.get(slot).get(1);
			if (((Skill)skillObj).mpcon <= Player.Mp) {
				if (skillObj.getClass() == BuffSkill.class) {
					BuffSkill bSkill = (BuffSkill)skillObj;
					if (bSkill.cooldown == 0 && bSkill.active == 0) {
						bSkill.active = 1;
						bSkill.cooldown = SkillConst.skillProps.get(bSkill.objType).get(7);
						SkillTab.skillBuffs.add(bSkill);
						Player.ChangePlayerMP(-((Skill)skillObj).mpcon);
					}
				} else if (skillObj.getClass() == ActiveSkill.class) {
					if (Player.attTarget != null) {
						ActiveSkill aSkill = (ActiveSkill)skillObj;
						if (aSkill.cooldown == 0) {
							aSkill.cooldown = SkillConst.skillProps.get(aSkill.objType).get(7);
							SkillTab.skillActives.add(aSkill);
							Player.ChangePlayerMP(-((Skill)skillObj).mpcon);
						}
					}
				} else if (skillObj.getClass() == ProjectileSkill.class) {
					Player.ChangePlayerMP(-((Skill)skillObj).mpcon);
					int x = 0, y = 0;
					double [] dirc = {0,0};
					int skillType = ((Skill)skillObj).objType;
					Dimension size = new Dimension(MapConst.projectileProps.get(skillType).get(0), MapConst.projectileProps.get(skillType).get(1));
					switch(Player.line) {
						case 0:
							dirc[0] = 0;
							dirc[1] = 1;
							x = Player.cords[0] + ((Player.playerRect.width-size.width)/2);
							y = Player.cords[1] + Player.playerRect.height + 3;
							break;
						case 1:
							dirc[0] = 0;
							dirc[1] = -1;
							x = Player.cords[0] + ((Player.playerRect.width-size.width)/2);
							y = Player.cords[1]-size.height - 3;
							break;
						case 2:
							dirc[0] = 1;
							dirc[1] = 0;
							x = Player.cords[0] + Player.playerRect.width + 3;
							y = Player.cords[1] + ((Player.playerRect.height-size.height)/2);
							break;
						case 3:
							dirc[0] = -1;
							dirc[1] = 0;
							x = Player.cords[0] - size.height - 3;
							y = Player.cords[1] + ((Player.playerRect.height-size.width)/2);
							break;
					}
					Mapa.projectiles.add(new Projectile(new Rectangle(x + Mapa.mapPoint.x, y + Mapa.mapPoint.y, size.width, size.height), dirc, (ProjectileSkill)skillObj, skillType));
				}
			}
		}
		
	}
	
}
