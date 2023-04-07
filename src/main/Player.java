package main;

import java.awt.Dimension;
import java.awt.Graphics;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

public class Player {
	public static int[] cords = new int[] {100,100};
	public static Rectangle playerRect = new Rectangle(cords[0], cords[1], 36, 50);
	public int row = 0, aniChange = 1, recoverCounter = 1, x = 0, y = 0, w = 50;
	private int resourceDMG = 1;
	public BufferedImage img, damagedImg;
	public static int line = 0 ,mapObjInter = 0, playerCollideObjType = 0, level = 1, Hp = 100, maxHp = 100, Mp = 100, maxMp = 100, Exp = 0, lvlUpExp = 20, 
			baseStrength = 1, baseDefense = 1, baseAgility = 1, baseLuck = 1, ablilityPoints = 0, baseWeaponAttack = 1, skillPoints = 0,
			accuracy = 0, rangedAttCounter = 0, addStrength = 0, addDefense = 0, addAgility = 0, addLuck = 0, addWeaponAttack = 0, deadCounter = 49;
	public static boolean underAtt = false, rangedUnderAtt = false;
	public static Enemy attTarget;
	public static final int IDLE = 0, ATTACKING = 1, MINNING = 2, CUTTING = 3, DEAD = 4;
	
	public Player() {
		LoadPlayerImg();
	}
	
	public void ChangePlayerAnimation() {
		if (mapObjInter != DEAD) {
			y = line*50;
			SetImgIndex();
			ChangeState();
			if (line == 2) {
				if (row == 9) {
					x = 26*row; w = 34;
				} else if (row >= 10) {
					x = 268+((row-10)*36); w = 36;
				} else {
					x = 26*row; w = 26;
				}
			} else {
				x = 36*row; w = 36;
			}
			playerRect.width = w;
			if (aniChange % 10 == 0) {
				aniChange = 0;
				ChangeAniFrame();
			}
			if (recoverCounter == 300) {
				ChangePlayerHP(1);
			} 
			if (recoverCounter == 600) {
				ChangePlayerMP(1);
				recoverCounter = 0;
			}
			if (rangedUnderAtt) {
				if (rangedAttCounter == 0) {
					rangedUnderAtt = false;
				}
				rangedAttCounter--;
			}
			recoverCounter++;
			aniChange++;
		} else {
			deadCounter--;
			if (deadCounter == 0) {
				deadCounter = 49;
				mapObjInter = IDLE;
			}
		}
	}
	
	private void ChangeAniFrame() {
		if (Player.mapObjInter == IDLE) {
			if (row < 6) {
				row++;
			} else if (row >= 6) {
				row = 0;
			}
		} else if (Player.mapObjInter == ATTACKING) {
			DamageEnemy(false, 0);
			if (row < 7) {
				row = 7;
			} else if(row < 10) {
				row++;
			} else {
				row = 0;
			}
		} else if (Player.mapObjInter == MINNING) {
			Resource objInter = (Resource)Mapa.clickedObj;
			objInter.ChangeHp(resourceDMG);
			if (row < 11 || row == 14) {
				row = 11;
			} else if(row < 14) {
				row++;
			}
			
		} else if (Player.mapObjInter == CUTTING) {
			Resource objInter = (Resource)Mapa.clickedObj;
			objInter.ChangeHp(resourceDMG);
			if (row < 15 || row == 18) {
				row = 15;
			} else if(row < 18) {
				row++;
			}
		}
		
	}
	private void SetImgIndex() {
		if (Player.mapObjInter == IDLE) {
			if(KeyInputs.direc[1] == -1) {
				if (line == 3) {
					cords[0] += 10;
				}
				line = 0;
			}
			if(KeyInputs.direc[1] == 1) {
				if (line == 3) {
					cords[0] += 10;
				}
				line = 1;
			}
			if (KeyInputs.direc[0] == -1) {
				if (line == 3) {
					cords[0] += 10;
				}
				line = 2;
			} 
			if(KeyInputs.direc[0] == 1) {
				if (line == 0 || line == 1 || line == 2) {
					cords[0] -= 10;
				}
				line = 3;
			}
			if (KeyInputs.direc[0] == 0 && KeyInputs.direc[1] == 0 ) {
				row = 0;
			}
		}
	}
	
	public void ChangeState() {
		if (KeyInputs.attackPressed) {
			switch (Player.playerCollideObjType) {
				case 0:
					mapObjInter = IDLE;
					break;
				case 1:
					mapObjInter = ATTACKING;
					break;
				case 2:
					mapObjInter = MINNING;
					break;
				case 3:
					mapObjInter = CUTTING;
					break;
			}
		} else {
			Player.mapObjInter = Player.IDLE;
			if (Player.attTarget != null) {
				Player.attTarget.underAtt = false;
			}
		}
	}
	
	public static void ChangePlayerHP(int Amount) {
		Player.Hp += Amount;
		if (Player.Hp > Player.maxHp) {
			Player.Hp = Player.maxHp;
		} else if (Player.Hp <= 0) {
			Player.Hp = 0;
			Mapa.mapPoint.x = 0;
			Mapa.mapPoint.y = 400;
			Player.cords[0] = (GamePanel.size.width-36)/2;
			Player.cords[1] = (GamePanel.size.height-50)/2;
			if (attTarget != null) {
				attTarget.state = attTarget.IDLE;
			}
			mapObjInter = DEAD;
		}
	} 
	public static void ChangePlayerExp(int Amount) {
		Player.Exp += Amount;
		if (Player.Exp >= Player.lvlUpExp) {
			UpdatePlayerMaxStats();
		}
	}
	public static void ChangePlayerMP(int Amount) {
		Player.Mp += Amount;
		if (Player.Mp > Player.maxMp) {
			Player.Mp = Player.maxMp;
		}
	} 
	
	public static void DamageEnemy(boolean activeSkill, double skillValue) {
		Random randint = new Random();
		int stringType = 0;
		double damage = 0;
		if (!activeSkill) {
			int attChance = randint.nextInt(0,100);
			if ((attChance <= (84 + (accuracy/2)))) {
				int Crit = randint.nextInt(0,100);
				if (Crit <= (5 + ((baseLuck+addLuck)/4))) {
					damage = randint.nextInt(10*(baseStrength+baseWeaponAttack+baseStrength+baseWeaponAttack), (baseStrength+baseWeaponAttack+baseStrength+baseWeaponAttack)*30);
					stringType = 2;
				} else {
					damage = randint.nextInt(2*(baseStrength+baseWeaponAttack+baseStrength+baseWeaponAttack), (baseStrength+baseWeaponAttack+baseStrength+baseWeaponAttack)*5);
					stringType = 1;
				}
			}
		} else { // active skill damage
			damage =  (1+(skillValue/100))*randint.nextInt(10*(baseStrength+baseWeaponAttack+baseStrength+baseWeaponAttack), (baseStrength+baseWeaponAttack+baseStrength+baseWeaponAttack)*30);
			stringType = 3;
		}
		attTarget.underAtt = true;
		attTarget.ChangeHp((int)damage, stringType);
	}
	
	public void Draw(Graphics g) {
		if (mapObjInter != DEAD) {
			if (underAtt || rangedUnderAtt) {
				g.drawImage(damagedImg.getSubimage(x, y, w, 50), cords[0], cords[1], null);
			} else {
				g.drawImage(img.getSubimage(x, y, w, 50), cords[0], cords[1], null);
			}
		} else {
			g.drawImage(img.getSubimage(x, y, w, 50-deadCounter), cords[0], cords[1], null);
		}
	}
	
	public void LoadPlayerImg() {
		InputStream is = getClass().getResourceAsStream("/playerimgs/playerimg.png");;
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream ir = getClass().getResourceAsStream("/playerimgs/playerimg.png");
		try {
			damagedImg = ImageIO.read(ir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ir.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		damagedImg = AuxiliaryCalc.ChangeImgPixelColor(damagedImg, 0);
	}
	public void UpdatePlayerAblility(int mode, int change) {
		switch(mode) {
			case 0:
				baseStrength += change;
				break;
			case 1:
				baseDefense += change;
				break;
			case 2:
				baseAgility += change;
				break;
			case 3:
				baseLuck += change;
				break;
		}
	}
	public static void UpdatePlayerMaxStats() {
		Dimension size = GamePanel.size;
		Mapa.levelUpEffect.add(new StringEffect("L",new Point((size.width-400)/2,0),2, 0));
		MapEffect.stringIndex = 1;
		Random rand = new Random();
		level++;
		maxHp += rand.nextInt(maxHp/10, maxHp/5);
		maxMp += rand.nextInt(maxMp/20, maxMp/10);
		lvlUpExp = 25 + (5*level*level*level);
		ablilityPoints += 3;
		skillPoints += 2; 
		int x = (size.width-500)/2 + 455;
		StringEffect mEff = (StringEffect)SkillTab.skillPointsString;
		mEff.label = String.valueOf(Player.skillPoints);
		mEff.point.x = (x+177-(3*String.valueOf(Player.skillPoints).length())); 
	}
	public static void SavePlayerStats() {
		FileWriter myWriter;
		try {
	      File file = new File("data.vla");
	      if (file.createNewFile()) {
	    	  System.out.println("File created: " + file.getName());
	      } else {
	    	  System.out.println("File already exists.");
	      }
	      myWriter = new FileWriter("data.vla");
    	  myWriter.write(String.valueOf(level));
    	  myWriter.write(System.getProperty( "line.separator" ));
    	  myWriter.write(String.valueOf(Hp));
    	  myWriter.write(System.getProperty( "line.separator" ));
    	  myWriter.write(String.valueOf(maxHp));
    	  myWriter.write(System.getProperty( "line.separator" ));
    	  myWriter.write(String.valueOf(Mp));
    	  myWriter.write(System.getProperty( "line.separator" ));
    	  myWriter.write(String.valueOf(maxMp));
    	  myWriter.write(System.getProperty( "line.separator" ));
    	  myWriter.write(String.valueOf(Exp));
    	  myWriter.write(System.getProperty( "line.separator" ));
    	  myWriter.write(String.valueOf(lvlUpExp));
    	  myWriter.write(System.getProperty( "line.separator" ));
    	  myWriter.write(String.valueOf(baseStrength));
    	  myWriter.write(System.getProperty( "line.separator" ));
    	  myWriter.write(String.valueOf(baseDefense));
    	  myWriter.write(System.getProperty( "line.separator" ));
    	  myWriter.write(String.valueOf(baseAgility));
    	  myWriter.write(System.getProperty( "line.separator" ));
    	  myWriter.write(String.valueOf(baseLuck));
    	  myWriter.write(System.getProperty( "line.separator" ));
    	  myWriter.write(String.valueOf(ablilityPoints));
    	  myWriter.write(System.getProperty( "line.separator" ));
    	  myWriter.write(String.valueOf(baseWeaponAttack));
    	  myWriter.write(System.getProperty( "line.separator" ));
    	  myWriter.write(String.valueOf(skillPoints));
    	  myWriter.write(System.getProperty( "line.separator" ));
	      myWriter.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	public static void LoadPlayerStats() {
		BufferedReader reader;
		try {
	      File file = new File("data.vla");
	      if (file.createNewFile()) {
	    	  System.out.println("File created: " + file.getName());
	      } else {
	    	  System.out.println("File already exists.");
	    	  reader = new BufferedReader(new FileReader("data.vla"));
		      level = Integer.parseInt(reader.readLine());
		      Hp = Integer.parseInt(reader.readLine()); 
		      maxHp = Integer.parseInt(reader.readLine()); 
		      Mp = Integer.parseInt(reader.readLine()); 
		      maxMp = Integer.parseInt(reader.readLine()); 
		      Exp = Integer.parseInt(reader.readLine()); 
		      lvlUpExp = Integer.parseInt(reader.readLine()); 
		      baseStrength = Integer.parseInt(reader.readLine()); 
		      baseDefense = Integer.parseInt(reader.readLine()); 
		      baseAgility = Integer.parseInt(reader.readLine()); 
		      baseLuck = Integer.parseInt(reader.readLine()); 
		      ablilityPoints = Integer.parseInt(reader.readLine()); 
		      baseWeaponAttack = Integer.parseInt(reader.readLine()); 
		      skillPoints = Integer.parseInt(reader.readLine()); 
		      reader.close();
	      }
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}
