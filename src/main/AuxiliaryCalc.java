package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AuxiliaryCalc {
	
	public static BufferedImage ChangeImgPixelColor(BufferedImage img, int mode) {
		Color currColor,newColor;
		int pixel;
		for (int i = 0 ; i < img.getWidth() ; i++) {
			for (int j = 0 ; j < img.getHeight() ; j++){
				pixel = img.getRGB(i, j);
				currColor = new Color(pixel, true);
				if (currColor.getAlpha() > 5) {
					if (mode == 0) {
						newColor = new Color( currColor.getRed(),0,0);
					}else {
						newColor = new Color( currColor.getRed()/5,currColor.getGreen()/5,currColor.getBlue()/5);
					}
					img.setRGB(i, j, newColor.getRGB());
				}
			}
		}
		return img;
	}
	
	public static void ChangeImgAlpha(BufferedImage img, int offset) {
		Color currColor,newColor;
		int pixel;
		for (int i = 0 ; i < img.getWidth() ; i++) {
			for (int j = 0 ; j < img.getHeight() ; j++){
				pixel = img.getRGB(i, j);
				currColor = new Color(pixel, true);
				if (currColor.getAlpha() > 5) {
					newColor = new Color( currColor.getRed(),currColor.getGreen(),currColor.getBlue(), offset);
					img.setRGB(i, j, newColor.getRGB());
				}
			}
		}
	}
	
	public static ArrayList<Integer> GetImgPixels(BufferedImage img) {
		ArrayList<Integer> pixels = new ArrayList<Integer>();
		for (int i = 0 ; i < img.getWidth() ; i++) {
			for (int j = 0 ; j < img.getHeight() ; j++){
				pixels.add(img.getRGB(i, j));
			}
		}
		return pixels;
	}
	
	public static void SetImgPixels(BufferedImage img, ArrayList<Integer> pixels) {
		Color newColor;
		int k = 0;
		for (int i = 0 ; i < img.getWidth() ; i++) {
			for (int j = 0 ; j < img.getHeight() ; j++){
				newColor = new Color(pixels.get(k), true);
				img.setRGB(i, j, newColor.getRGB());
				k++;
			}
		}
	}
	
	public static int GetNumOfIntersectedPoints(Point sPoint, Point ePoint, Rectangle r1) {
		int k = 0;
		Point interPoint = new Point();
		if (ePoint.x - sPoint.x == 0) {
			for (int i = sPoint.y; i <= ePoint.y; i++) {
				interPoint.x = sPoint.x;
				interPoint.y = i;
				if (r1.contains(interPoint)) {
					k++;
				}
			}
		} else {
			for (int i = sPoint.x; i <= ePoint.x; i++) {
				interPoint.y = sPoint.y;
				interPoint.x = i;
				if (r1.contains(interPoint)) {
					k++;
				}
			}
		}
		return k;
	}
	
	public static int GetFreeMapObjectKey(Map<Integer, List<Integer>> objMap) {
		for (int i = 403; i < 100000; i++) {
			if (!objMap.containsKey(i)) {
				return i;
			}
		}
		return- 1;
	}
	
	public static int GetDropId(int mobId) {
		Random rand = new Random();
		int ratesSum = 0;
		for (int rate : DropConst.mobRates.get(mobId).values()) {
			ratesSum += rate;
        }
		int rateNum = rand.nextInt(0, ratesSum);
		int sum = 0;
		for (int itemKey : DropConst.mobRates.get(mobId).keySet()) {
			sum += DropConst.mobRates.get(mobId).get(itemKey);
			if (rateNum < sum) {
				return itemKey;
			}
        }
		return -1;
	}
	
	public static int CheckInRangeToFire(Enemy enem) {
		double enemyX = enem.objRect.x - Mapa.mapPoint.x, enemyY = enem.objRect.y - Mapa.mapPoint.y;
		double distance = Math.sqrt(Math.pow((Player.cords[0] + (Player.playerRect.width/2)) - (enemyX + (enem.objRect.width/2)), 2) + Math.pow(Player.cords[1] + (Player.playerRect.height/2) - (enemyY + (enem.objRect.height/2)), 2));
		if (distance <= 300) {
			if ((enemyY + enem.objRect.height) < Player.cords[1]) {
				return 0;
			} else if ((enemyY + enem.objRect.height) >= Player.cords[1] && enemyY < Player.cords[1]) {
				if (Player.cords[0] < enemyX) {
					return 2;
				} else {
					return 1;
				}
				
			} else if (enemyY >= Player.cords[1]) {
				return 3;
			}
		}
		return -1;
	}
	public static double[] CalcProjectileDirection(int[] projCords) {
		double xAdv = Player.cords[0] - (projCords[0]-Mapa.mapPoint.x);
		double yAdv = Player.cords[1] - (projCords[1]-Mapa.mapPoint.y);
		int xSign , ySign;
		if (xAdv >= 1) {
			xSign = 1;
		} else {
			xSign = -1;
		}
		if (yAdv >= 1) {
			ySign = 1;
		} else {
			ySign = -1;
		}
		double advance = xAdv/yAdv;
		if (Math.abs(advance) > 1) {
			return new double[] {xSign, Math.abs(yAdv/xAdv)*ySign};
		} else {
			return new double[] {Math.abs(xAdv/yAdv)*xSign, ySign};
		}
	}
}
