package main;

import java.awt.Point;

public class StringEffect extends MapEffect {
	String label;
	Point point;
	int Duration = 10, cycle = 0;
	int type = 0;
	
	public StringEffect(String strg, Point pnt, int state, int strType) {
		switch (state) {
			case 0:
				Mapa.mapEff.add(this);
				type = strType;
				break;
			case 1:
				MapEffect.coinLabel = this;
				break;
		}
		label = strg;
		point = pnt;
	}
	
}
