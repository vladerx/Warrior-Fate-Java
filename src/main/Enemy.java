package main;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

public class Enemy extends MapObj{
	public int line = 2, row = 0, HP, aniChange = 0, state = 4, idleCounter = 0, startx, dir = 1
			, frameBound = 0, damage, exp, imgOffset = 6, level, projectileDamage;
	public final int IDLE = 0, MOVING = 1, ATTACKING = 2, DEAD = 3, SPAWNING = 4, RANGEATTACKING = 5;
	public boolean underAtt = false;

	public Enemy(int key, int type, int cordx, int cordy, BufferedImage fullImg) {
		super(key, type, cordx, cordy);
		startx = cordx;
		projectileDamage = MapConst.objProps.get(type).get(2);
		level = MapConst.objProps.get(type).get(3);
		HP = MapConst.objProps.get(type).get(4);
		frameBound = MapConst.objProps.get(type).get(1);
		damage = MapConst.objProps.get(type).get(5);
		exp = MapConst.objProps.get(type).get(6);
	}
	
	public void ChangeEnemyAnimation() {
		double damagePlayer;
		if (aniChange % 20 == 0) {
			if (state == MOVING) {
				if (dir == 1) {
					line = 2;
				} else {
					line = 3;
				}
				if (row < 2) {
					row++;
				} else if (row >= 2) {
					row = 0;
				}
			} else if (state == ATTACKING) {
				Random randint = new Random();
				damagePlayer = randint.nextInt(damage, damage*2) - ((Player.addDefense+Player.baseDefense)/10);
				if (damagePlayer < 1) {
					damagePlayer = 1;
				}
				Player.ChangePlayerHP(-(int)damagePlayer);
				if (line == 2 || line == 3) {
					if (row < 3) {
						row = 3;
					} else if(row < 6) {
						row++;
					} else {
						row = 3;
					}
				} else {
					if (row > 1) {
						row = 0;
					} else {
						row++;
					}
				}
			} else if (state == IDLE) {
				idleCounter++;
				if (idleCounter == 20) {
					idleCounter = 0;
					state = MOVING;
				}
			} else if (state == DEAD) {
				CheckFrameBound();
			} else if(state == SPAWNING) {
				imgOffset += 20;
				if (imgOffset > 220) {
					state = MOVING;
				}
			} else if (state == RANGEATTACKING && aniChange % 300 == 0) {
				Dimension size = new Dimension(MapConst.projectileProps.get(objType).get(0), MapConst.projectileProps.get(objType).get(1));
				Mapa.projectiles.add(new Projectile(new Rectangle(objRect.x + (objRect.width-size.width)/2, objRect.y + (objRect.height-size.height)/2, size.width, size.height), AuxiliaryCalc.CalcProjectileDirection(new int[] {objRect.x, objRect.y}), null, objType));
			}
		}
		if (aniChange % 300 == 0) {
			aniChange = 0;
		}
		aniChange++;
	}
	
	public void CheckFrameBound() {
		frameBound -= 5;
		if (frameBound < 6) {
			Mapa.mapObjsList.remove(this);
			Mapa.enemyObj.remove(this);
			MapConst.objLoca.remove(this.objKey);
		}
	}
	
	public void ChangeHp(int change, int type) {
		HP -= change;
		Random rand = new Random();
		if (change == 0) {
			new StringEffect("Miss", new Point((this.objRect.x-Mapa.mapPoint.x) + (this.objRect.width - "Miss".length())/2, this.objRect.y-Mapa.mapPoint.y - 20), 0, 0);
		} else {
			String str = String.valueOf(change);
			new StringEffect(str, new Point((this.objRect.x-Mapa.mapPoint.x) + (this.objRect.width - str.length())/2, this.objRect.y-Mapa.mapPoint.y - 20), 0, type);
			if (HP <= 0) {
				state = DEAD;
				underAtt = false;
				Player.ChangePlayerExp(exp);
				Player.attTarget = null;
				Player.mapObjInter = Player.IDLE;
				Player.playerCollideObjType = Player.IDLE;
				Player.underAtt = false;
				int dropKey = AuxiliaryCalc.GetFreeMapObjectKey(MapConst.objLoca);
				int dropType = AuxiliaryCalc.GetDropId(objType);
				int amount = 1;
				if (dropKey != -1 && dropType != -1 && dropType != 1000) {
					Object objt = ItemConst.itemProps.get(dropType).get(0);
					if (objt == Useable.class) {
						amount = rand.nextInt(amount, 5);
					}
				} else if (dropType == 1000) {
					amount = rand.nextInt(amount, 10);
				}
				MapConst.objLoca.put(dropKey, Arrays.asList(dropType, objRect.x, objRect.y, amount, 2));//{key, {item type, xcord, ycord, amount, dropping}} //vlad
			}
		}
	}
}
