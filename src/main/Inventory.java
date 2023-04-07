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
import java.util.Map;

import javax.imageio.ImageIO;

public class Inventory {
	
	public static ArrayList<InventoryItem> invItems = new ArrayList<InventoryItem>();
	public static ArrayList<BufferedImage> invImgs = new ArrayList<BufferedImage>();
	private Map<Integer, ArrayList<BufferedImage>> invButtonImgs = new HashMap<Integer, ArrayList<BufferedImage>>();
	public static InventoryItem clickedobj;
	public BufferedImage hoverImage, itemImgPreview;
	public Rectangle itemStatsRect;
	public boolean SlotHovered = false, upgrader = true;
	public int hooverSlot = 0;
	public static int coins = 0;
	public static ArrayList<MapEffect> invStatsEffects = new ArrayList<MapEffect>();
	public static BufferedImage upgraderImg;
	public static ArrayList<MapEffect> upgraderEffects = new ArrayList<MapEffect>();
	public static ArrayList<Point> locPoints = new ArrayList<Point>();
	
	
	public void UpdateImgPosition (){
		Dimension pSize = GamePanel.size;
		Map<Integer, List<Integer>> invRects = new HashMap <Integer, List<Integer>>(ItemConst.invTypeRects);
		if (clickedobj != null) {
			clickedobj.invRect.x = MouseInputs.mousePoint.x - (clickedobj.invRect.width/2);
			clickedobj.invRect.y = MouseInputs.mousePoint.y - (clickedobj.invRect.height/2);
		}
		Rectangle rect = new Rectangle();
		boolean found = false;
		for (int i = 67; i < ItemConst.invTypeRects.size(); i++) {
			rect.x = ItemConst.invTypeRects.get(i).get(1);
			rect.y = ItemConst.invTypeRects.get(i).get(2);
			rect.width = ItemConst.invTypeRects.get(i).get(3);
			rect.height = ItemConst.invTypeRects.get(i).get(4);
			if (rect.contains(MouseInputs.mousePoint)) {
				hooverSlot = i;
				found = true;
			}
		}
		if (!found) {
			hooverSlot = 0;
		}
		found = false;
		Equip equp;
		List<Object> itemProps;
		for (int i = 0; i < 54; i++) {
			rect.x = invRects.get(i).get(1);
			rect.y = invRects.get(i).get(2);
			rect.width = invRects.get(i).get(3);
			rect.height = invRects.get(i).get(4);
			if (rect.contains(MouseInputs.mousePoint.x, MouseInputs.mousePoint.y) && ItemConst.itemSlots.get(i).get(1) != null) {
				if (ItemConst.itemSlots.get(i).get(1).getClass() == Equip.class) {
					equp = (Equip)ItemConst.itemSlots.get(i).get(1);
					itemProps = ItemConst.itemProps.get(equp.objType);
					SlotHovered = true;
					itemImgPreview = invImgs.get(equp.objType-1001);
					invStatsEffects.add(new StringEffect(String.valueOf(equp.attack), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2-122), 2, 0));
					invStatsEffects.add(new StringEffect(String.valueOf(equp.defense), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2-98), 2, 0));
					invStatsEffects.add(new StringEffect(String.valueOf(equp.agility), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2-73), 2, 0));
					invStatsEffects.add(new StringEffect(String.valueOf(equp.luck), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2-48), 2, 0));
					invStatsEffects.add(new StringEffect(String.valueOf(equp.upgradeSlots), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2-23), 2, 0));
					invStatsEffects.add(new StringEffect(String.valueOf(itemProps.get(7)), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2+26), 2, 0));
					invStatsEffects.add(new StringEffect(String.valueOf(itemProps.get(8)), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2+49), 2, 0));
					invStatsEffects.add(new StringEffect(String.valueOf(itemProps.get(9)), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2+74), 2, 0));
					invStatsEffects.add(new StringEffect(String.valueOf(itemProps.get(10)), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2+101), 2, 0));
					invStatsEffects.add(new StringEffect(String.valueOf(itemProps.get(11)), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2+129), 2, 0));
					found = true;
				}
			}
		}
		rect.x = invRects.get(66).get(1);
		rect.y = invRects.get(66).get(2);
		rect.width = invRects.get(66).get(3);
		rect.height = invRects.get(66).get(4);
		if (rect.contains(MouseInputs.mousePoint.x, MouseInputs.mousePoint.y) && ItemConst.itemSlots.get(66).get(1) != null) {
			if (ItemConst.itemSlots.get(66).get(1).getClass() == Equip.class) {
				equp = (Equip)ItemConst.itemSlots.get(66).get(1);
				itemProps = ItemConst.itemProps.get(equp.objType);
				SlotHovered = true;
				itemImgPreview = invImgs.get(equp.objType-1001);
				invStatsEffects.add(new StringEffect(String.valueOf(equp.attack), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2-122), 2, 0));
				invStatsEffects.add(new StringEffect(String.valueOf(equp.defense), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2-98), 2, 0));
				invStatsEffects.add(new StringEffect(String.valueOf(equp.agility), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2-73), 2, 0));
				invStatsEffects.add(new StringEffect(String.valueOf(equp.luck), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2-48), 2, 0));
				invStatsEffects.add(new StringEffect(String.valueOf(equp.upgradeSlots), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2-23), 2, 0));
				invStatsEffects.add(new StringEffect(String.valueOf(itemProps.get(7)), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2+26), 2, 0));
				invStatsEffects.add(new StringEffect(String.valueOf(itemProps.get(8)), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2+49), 2, 0));
				invStatsEffects.add(new StringEffect(String.valueOf(itemProps.get(9)), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2+74), 2, 0));
				invStatsEffects.add(new StringEffect(String.valueOf(itemProps.get(10)), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2+101), 2, 0));
				invStatsEffects.add(new StringEffect(String.valueOf(itemProps.get(11)), new Point((pSize.height-200)/2 + 348, (pSize.width-400)/2+129), 2, 0));
				found = true;
			}
		}
		if (!found) {
			SlotHovered = false;
			itemImgPreview = null;
			invStatsEffects.clear();
		}
		
	}

	
	public void Draw(Graphics g) {
		int x = (GamePanel.size.width-200)/2, y = GamePanel.size.height-531;
		ArrayList<MapEffect> invStrings = new ArrayList<MapEffect>(invStatsEffects);
		ArrayList<InventoryItem> itemsInv = new ArrayList<InventoryItem>(Inventory.invItems);
		double divi;
		BufferedImage img;
		Graphics2D g2;
		g.drawImage(upgraderImg, x-200, y, null);
		for (int imgKey : invButtonImgs.keySet()) {
			divi = (double)hooverSlot/(67+imgKey);
			if (divi > 1) {
				divi = 0;
			}
			g.drawImage(invButtonImgs.get(imgKey).get((int)divi), ItemConst.invTypeRects.get(67+imgKey).get(1), ItemConst.invTypeRects.get(67+imgKey).get(2), null);
		}
		for(InventoryItem invItem : itemsInv) {
			img = Inventory.invImgs.get(invItem.objType-1001);
			if (invItem == clickedobj) {
				AuxiliaryCalc.ChangeImgAlpha(img, 100);
				g.drawImage(img, invItem.invRect.x, invItem.invRect.y, null);
				AuxiliaryCalc.SetImgPixels(img, invItem.pixels);
			} else {
				g.drawImage(img, invItem.invRect.x, invItem.invRect.y, null);
			}
			if (invItem.getClass() == Useable.class) {
				Useable item = (Useable)invItem;
				if (item.amountLabel != null) {
					g2 = (Graphics2D) g;
					g2.setColor(Color.BLACK);
					StringEffect strEff = (StringEffect) item.amountLabel;
					g2.drawString(strEff.label, strEff.point.x, strEff.point.y);
				}
			} else if (invItem.getClass() == ETCItem.class) {
				ETCItem item = (ETCItem)invItem;
				if (item.amountLabel != null) {
					g2 = (Graphics2D) g;
					g2.setColor(Color.BLACK);
					StringEffect strEff = (StringEffect) item.amountLabel;
					g2.drawString(strEff.label, strEff.point.x, strEff.point.y);
				}
			}
		}
		g2 = (Graphics2D) g;
		if (MapEffect.coinLabel != null) {
			g2.setColor(Color.WHITE);
			StringEffect strEff = (StringEffect) MapEffect.coinLabel;
			strEff.label = String.valueOf(coins);
			g2.drawString(strEff.label, strEff.point.x, strEff.point.y);
		}
		Font font = new Font("Impact", Font.BOLD, 12);
		Font oldFont = g2.getFont();
		g2.setColor(Color.BLACK);
		g2.setFont(font);
		StringEffect sEff;
		if (ItemConst.itemSlots.get(66).get(1) != null) {
			InventoryItem invItem = (InventoryItem)ItemConst.itemSlots.get(66).get(1);
			ArrayList<Integer> reqs = new ArrayList<Integer>(ItemConst.upgradeReqs.get(invItem.objType));
			for (int i = 0, k = 4; i<reqs.size(); i+=2, k++) {
				sEff = (StringEffect) upgraderEffects.get(i/2);
				if (reqs.get(i) == 1000) {
					g.drawImage(Mapa.mapItemImgs.get(1000).getSubimage(0, 0, 15, 15), locPoints.get(i/2).x, locPoints.get(i/2).y, null);
				} else {
					g.drawImage(invImgs.get(reqs.get(i)-1001), locPoints.get(i/2).x, locPoints.get(i/2).y, null);
				}
				g2.drawString(sEff.label, sEff.point.x, sEff.point.y);
			}
		}
		if (SlotHovered) {
			if (hoverImage != null) {
				g.drawImage(hoverImage, (GamePanel.size.width-200)/2, (GamePanel.size.height-400)/2, null);
			}
			if (itemImgPreview != null) {
				g.drawImage(itemImgPreview, itemStatsRect.x+(itemStatsRect.width-itemImgPreview.getWidth())/2, itemStatsRect.y+(itemStatsRect.height-itemImgPreview.getHeight())/2, null);

			}
			for (MapEffect mEff: invStrings) {
				sEff = (StringEffect)mEff;
				g2.drawString(sEff.label, sEff.point.x, sEff.point.y);
			}
		}
		g2.setFont(oldFont);
	}
	
	public void importImage() {
		Dimension pSize = GamePanel.size;
		int x = (pSize.width-200)/2, y =pSize.height-531;
		itemStatsRect = new Rectangle((pSize.height-200)/2 + 315, (pSize.width-400)/2-220,50,50);
		locPoints.add(new Point(x-175,y+115));
		locPoints.add(new Point(x-175,y+155));
		locPoints.add(new Point(x-90,y+115));
		locPoints.add(new Point(x-90,y+155));
		locPoints.add(new Point(x-135,y+130));
		locPoints.add(new Point(x-135,y+170));
		locPoints.add(new Point(x-50,y+130));
		locPoints.add(new Point(x-50,y+170));
		InputStream is;
		for (String loca : ItemConst.paths) {
			is = getClass().getResourceAsStream(loca);
			try {
				invImgs.add(ImageIO.read(is));
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
		int i = 1;
		ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();
		for (String loca : ItemConst.buttonPaths) {
			is = getClass().getResourceAsStream(loca);
			try {
				imgs.add(ImageIO.read(is));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i % 2 == 0) {
				invButtonImgs.put((i/2)-1, new ArrayList<BufferedImage>(imgs));
				imgs.clear();
			}
			i++;
		}
		is = getClass().getResourceAsStream("/inventorybuttons/itemstats.png");
		try {
			hoverImage = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		is = getClass().getResourceAsStream("/inventorybuttons/upgrader.png");
		try {
			upgraderImg = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void SaveInventoryItems() {
		Map<Integer, List<Object>> slotItem = new HashMap <Integer, List<Object>>(ItemConst.itemSlots);
		Equip equip;
		Useable usea;
		ETCItem etci;
		FileWriter myWriter;
		try {
	      File file = new File("inv.vla");
	      if (file.createNewFile()) {
	    	  System.out.println("File created: " + file.getName());
	      } else {
	    	  System.out.println("File already exists.");
	      }
	      myWriter = new FileWriter("inv.vla");
	      myWriter.write(String.valueOf(coins));
		  myWriter.write(System.getProperty( "line.separator" ));
	      for (int i = 0; i < slotItem.size(); i++){
	    	  if (slotItem.get(i).get(1) != null) {
	    		  if (slotItem.get(i).get(1).getClass() == Equip.class) {
	    			  equip = (Equip)slotItem.get(i).get(1);
	    			  myWriter.write(String.valueOf(i)+','+String.valueOf(equip.amount)+','+String.valueOf(equip.objType)+','+String.valueOf(equip.invType)+','+String.valueOf(equip.attack)+','+
	    					  String.valueOf(equip.defense)+','+String.valueOf(equip.agility)+','+String.valueOf(equip.luck)+','+String.valueOf(equip.upgradeSlots));
	    		  } else if (slotItem.get(i).get(1).getClass() == Useable.class) {
	    			  usea = (Useable)slotItem.get(i).get(1);
	    			  myWriter.write(String.valueOf(i)+','+String.valueOf(usea.amount)+','+String.valueOf(usea.objType)+','+String.valueOf(usea.invType));
	    		  } else if (slotItem.get(i).get(1).getClass() == ETCItem.class) {
	    			  etci = (ETCItem)slotItem.get(i).get(1);
	    			  myWriter.write(String.valueOf(i)+','+String.valueOf(etci.amount)+','+String.valueOf(etci.objType)+','+String.valueOf(etci.invType));
	    		  }
	    		  myWriter.write(',');
	    		  myWriter.write(System.getProperty( "line.separator" ));
	    	  }
    	  }
	      myWriter.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public static void LoadInventoryItems() {
		BufferedReader reader;
		String txtLine;
		ArrayList<MapEffect> upEffs = new ArrayList<MapEffect>(Inventory.upgraderEffects);
		try {
	      File file = new File("inv.vla");
	      if (file.createNewFile()) {
	    	  System.out.println("File created: " + file.getName());
	      } else {
	    	  System.out.println("File already exists.");
	    	  reader = new BufferedReader(new FileReader("inv.vla"));
	    	  int chrCounter = 0, slot = 0, amount = 0, objType = 0, invType = 0, attack = 0, defense = 0, agility = 0, luck = 0, upSlots = 0;
	    	  String asmbString = "";
	    	  txtLine = reader.readLine();
	    	  if (txtLine == null) {
	    		  reader.close();
    			  return;
    		  }
	    	  coins = Integer.parseInt(txtLine);
	    	  while (true) {
	    		  chrCounter = 0;
	    		  txtLine = reader.readLine();
	    		  if (txtLine == null) {
	    			  break;
	    		  }
	    		  for (char chr : txtLine.toCharArray()) {
	    			  if (chr == ',') {
	    				  chrCounter++;
	    				  if (chrCounter < 5) {
		    				  switch (chrCounter) {
		    				  	case 1:
		    				  		slot = Integer.parseInt(asmbString);
		    				  		break;
		    				  	case 2:
		    				  		amount = Integer.parseInt(asmbString);
		    				  		break;
		    				  	case 3:
		    				  		objType = Integer.parseInt(asmbString);
		    				  		break;
		    				  	case 4:
		    				  		invType = Integer.parseInt(asmbString);
		    				  		break;
		    				  }
	    			  		} else {
	    					  switch (chrCounter) {
		    				  	case 5:
		    				  		attack = Integer.parseInt(asmbString);
		    				  		break;
		    				  	case 6:
		    				  		defense = Integer.parseInt(asmbString);
		    				  		break;
		    				  	case 7:
		    				  		agility = Integer.parseInt(asmbString);
		    				  		break;
		    				  	case 8:
		    				  		luck = Integer.parseInt(asmbString);
		    				  		break;
		    				  	case 9:
		    				  		upSlots = Integer.parseInt(asmbString);
		    				  		break;
		    				  }
	    				  }
	    				  asmbString = "";
	    			  } else {
	    				  asmbString += chr;
	    			  }
	    		  }
	    		  Object obj = ItemConst.itemProps.get(objType).get(0);
	    		  if (obj == Equip.class) {
	    			  ItemConst.itemSlots.get(slot).set(0, objType);
	    			  ItemConst.itemSlots.get(slot).set(1, new Equip(slot, objType, amount, invType, attack, defense ,agility, luck, upSlots));
	    		  } else if (obj == ETCItem.class) {
	    			  ItemConst.itemSlots.get(slot).set(0, objType);
	    			  ItemConst.itemSlots.get(slot).set(1, new ETCItem(slot, objType, amount, invType));
	    		  } else if (obj == Useable.class) {
	    			  ItemConst.itemSlots.get(slot).set(0, objType);
	    			  ItemConst.itemSlots.get(slot).set(1, new Useable(slot, objType, amount, invType));
	    		  }
	    		  if (slot == 66){
	    			  ArrayList<Integer> reqs = new ArrayList<Integer>(ItemConst.upgradeReqs.get(objType));
	    			  for (int i=0, k = 4; i<reqs.size();i+=2, k++) {
							upEffs.add(new StringEffect("X "+String.valueOf(reqs.get(i+1)), Inventory.locPoints.get(k), 2, 0));
	    			  }
	    		  }
	    	  }
		      reader.close();
	      }
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		Inventory.upgraderEffects  = new ArrayList<MapEffect>(upEffs);
	}
}
