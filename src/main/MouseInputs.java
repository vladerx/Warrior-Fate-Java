package main;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener{

	public static Point mousePoint = new Point(0, 0);
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePoint.x = e.getX();
		mousePoint.y =  e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) { // choose an action if a click based on which menus are selected
			if (Menu.state == Menu.GAMEON) {
				switch(Mapa.state) {
					case Mapa.GAMEPLAY:
						MapHelper.CheckClickedMapObject();
						if (Inventory.invItems.size() != 0) { // if clicked on useable items slots
							MapHelper.CheckMouseClickInRect(mousePoint,MapConst.invTypeRects, 0);
						}
						MapHelper.CheckMouseClickInRect(mousePoint, SkillConst.skillRects, 2);
						break;
					case Mapa.INVENTORY:
						MapHelper.CheckMouseClickInRect(mousePoint,ItemConst.invTypeRects, 1);
						break;
					case Mapa.SKILLS:
						MapHelper.CheckMouseClickInRect(mousePoint, SkillConst.skillRects, 2);
						break;
					case Mapa.STATS:
						MapHelper.CheckMouseClickInRect(mousePoint, StatsTab.statsRects, 3);
						break;
					case Mapa.DIALOGUE:
						MapHelper.CheckMouseClickInRect(mousePoint, NPCDialogue.dialogueRects, 4);
						break;
					case Mapa.QUEST:
						MapHelper.CheckMouseClickInRect(mousePoint, QuestTab.questRects, 5);
						break;
				}
			} else {
				MapHelper.CheckMouseClickInRect(mousePoint, Menu.menuRects, 6);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
