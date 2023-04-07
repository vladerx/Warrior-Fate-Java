package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class Menu {
	final public List<String> menuPaths = Arrays.asList("/menuimgs/menubuttons.png","/menuimgs/menubg.png");
	final public ArrayList<BufferedImage> menuimgs = new ArrayList<BufferedImage>();
	public static Map<Integer, List<Integer>> menuRects = new HashMap <Integer, List<Integer>>();
	public static int imgIndex = 0, state = 0;
	public final static int MENU = 0, GAMEON = 1;
	
	public Menu() {
		Dimension size = GamePanel.size;
		int x = (size.width-200)/2 ,y = 150;
		menuRects.put(0,  Arrays.asList(x,y,200,100));
		menuRects.put(1,  Arrays.asList(x,y+200,200,100));
		menuRects.put(2,  Arrays.asList(x,y+400,200,100));
	}
	
	public void UpdateMenu() {
		boolean found = false;
		Map<Integer, List<Integer>> rects = new HashMap <Integer, List<Integer>>(menuRects);
		Rectangle rect = new Rectangle();
		for (int rectKey : rects.keySet()) {
			rect.x = rects.get(rectKey).get(0);
			rect.y = rects.get(rectKey).get(1);
			rect.width = rects.get(rectKey).get(2);
			rect.height = rects.get(rectKey).get(3);
			if (rect.contains(MouseInputs.mousePoint.x, MouseInputs.mousePoint.y)) {
				imgIndex = (rectKey*2)+1;
				found = true;
			}
		}
		if (!found) {
			imgIndex = 0;
		}
	}
	
	public void Draw(Graphics g) {
		Map<Integer, List<Integer>> rects = new HashMap <Integer, List<Integer>>(menuRects);
		g.drawImage(menuimgs.get(1).getScaledInstance(GamePanel.size.width,GamePanel.size.height, Image.SCALE_SMOOTH),0,0,null);	
		for (int rectKey : rects.keySet()) {
			g.drawImage(menuimgs.get(0).getSubimage(200*((imgIndex % 2 == 0 ? 0 : 1)*(imgIndex/2 == rectKey ? 1 : 0)), 100*rectKey, 200, 100), rects.get(rectKey).get(0), rects.get(rectKey).get(1), null);	
		}
	}
	
	public void importImage() {
		InputStream is;
		for (String loca : menuPaths) {
			is = getClass().getResourceAsStream(loca);
			try {
				menuimgs.add(ImageIO.read(is));
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
	}
}
