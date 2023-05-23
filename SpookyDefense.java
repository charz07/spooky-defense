/*
Charlotte Zhou
Game Project (Spooky Defense)
A Halloween-themed strategy game
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*; 
import java.awt.BorderLayout;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;

public class SpookyDefense extends JFrame
{
	public SpookyDefense() //to make the jframe
	{
		super("Spooky Defense");
		setSize(800, 550);    
		setDefaultCloseOperation(EXIT_ON_CLOSE);   
		setLocation(400,50);
		setResizable(true);
		Game pc = new Game();   
		setContentPane( pc );
		setVisible(true);
	}
	public static void main(String[]args)  //main to run constructor
	{
		SpookyDefense sd = new SpookyDefense();
	}
}

class Game extends JPanel //running the game
{
	JPanel panel, homey, btnPanel, playIt, howToPlay, playPanel, winPanel, losePanel, readyPanel, charPanel, char1Panel, char2Panel, char3Panel;
	int back;
	CardLayout cardLayout;
	int [][] containTower;
	int [][] hoverTower;
	boolean towerBuild [];
	int coins, lives;
	JLabel coinCount;
	JButton boulder, cactus;
	int levelNum;
	int [] slimeX;
	int [] slimeY;
	int slime;
	int [] wormX;
	int [] wormY;
	int worms;
	int [] catX;
	int [] catY;
	int cats;
	Timer slimeTimer;
	Timer wormTimer;
	Timer catTimer;
	Timer playerTimer;
	Rectangle [][] rect;
	Color buttPurple;
	int skeleX, skeleX2;
	boolean forward, forward2;
	int count, count2;
	Timer gifTimer, skeleTimer, readyTimer;
	GifTimer gt; 
	SkeleMover sk;
	int [] slimeHp;
	int [] wormHp;
	int [] catHp;
	int slimeMax, wormMax, playerMax,catMax;
	long secondsDisplay, minutes;
	int [] sDirec;
	int [] wDirec;
	int [] cDirec;
	boolean passBeg;
	int [] canGo;
	int dead;
	boolean boulderShowDesc, cactiShowDesc;
	int bannerNumber, bannerX;
	int playerDirec, playerX, playerY;
	int playerHp;
	long pause;
	Timer timey;
	boolean [][] melee;
	boolean showIndicator;
	long startLightning;
	long dmgCD, towerdmgCD, coinCD;
	Clip clip;
	int instructionCard;
	JTextArea intro, traps, enemies, constructs;
	JLabel infoEx;
	int charShow;
	int wormBubble;
	long enemyTowerHurtCD;
	JLabel slimeEx, wormEx, catEx, boulderEx, cactusEx, trapEx;
	long bloodCD;
	boolean blood;
	public Game() //constructor to initialize variables and run the main
	{
		readyPanel = new JPanel();
		readyPanel.setOpaque(false);
		count = 0;
		count2 = 0;
		skeleX = 0;
		skeleX2 = 300;
		bannerNumber = 0;
		bannerX = -800;
		forward = true;
		forward2 = true;
		passBeg = false;
		playerX = 650;
		playerY = 190;
		charShow = 1;
		startLightning = System.currentTimeMillis();
		gt = new GifTimer();
		gifTimer = new Timer(10, gt);
		sk = new SkeleMover();
		skeleTimer = new Timer(20, sk);
		runIt();
	}
	public void paintComponent(Graphics g) //paint component for the background, sfx, constructs, monsters, grid, etc 
	{
		if(back == 0)
		{
			Image homeback = new ImageIcon("spookhome.gif").getImage();
			if(count == 0)
			{
				gifTimer.start();
				count++;
			}
			g.drawImage(homeback,0,0,800,550,null);
			Image lightning = new ImageIcon("lightning.gif").getImage();
			if(System.currentTimeMillis()-startLightning>=8000)
			{
				g.drawImage(lightning, 0, 0, 800, 550, null);
			}
			if(System.currentTimeMillis()-startLightning>=8400 && System.currentTimeMillis()-startLightning<=8410)
			{
				lightningStrike();
			}
			if(System.currentTimeMillis()-startLightning>=9000)
				startLightning = System.currentTimeMillis();
		}
		else if (back == 1)
		{
			if(levelNum==1)
			{
				Image tree = new ImageIcon("forestback.jpg").getImage();
				g.drawImage(tree,0,0,800,550,null);
			}
			else if(levelNum == 2)
			{
				Image forest = new ImageIcon("treeback.jpg").getImage();
				g.drawImage(forest,0,0,800,550,null);
			}
			else if(levelNum == 3)
			{
				Image ruin = new ImageIcon("ruinback.jpg").getImage();
				g.drawImage(ruin,0,0,800,550,null);
			}
		}
		else if (back == 2)
		{
			Image levelbg = new ImageIcon("levelback.jpeg").getImage();
			Image skele = new ImageIcon("skeleton.gif").getImage();
			Image skele2 = new ImageIcon("skeleton2.gif").getImage();
			if(count2 == 0)
			{
				skeleTimer.start();
				count2++;
			}
			g.drawImage(levelbg,0,0,800,550,null);
			if(forward==true)
				g.drawImage(skele,skeleX, 400, 70, 90, null);
			else if (forward==false)
				g.drawImage(skele2,skeleX, 400, 70, 90, null);
			if (forward2==true)
				g.drawImage(skele,skeleX2, 400, 70, 90, null);
			else if (forward2==false)
				g.drawImage(skele2,skeleX2, 400, 70, 90, null);
		}
		else if (back == 3)
		{
			Image instrucbg = new ImageIcon("howtoback.jpeg").getImage();
			g.drawImage(instrucbg,0,0,800,550,null);
		}
		else if (back == 4)
		{
			Image bloodtop = new ImageIcon("blooddrop.gif").getImage();
			Image defeatbg = new ImageIcon("defeatBack.jpg").getImage();
			Image blood1 = new ImageIcon("blood1.png").getImage();
			Image blood2 = new ImageIcon("blood2.png").getImage();
			gifTimer.start();
			g.drawImage(defeatbg,0,0,800,550,null);
			g.drawImage(bloodtop, 0, 0, 400, 400, null);
			g.drawImage(bloodtop, 380, 0, 400, 400, null);
			g.drawImage(blood1, 0, 0, 400, 400, null);
			g.drawImage(blood2, 200, 200, 300, 300, null);
			g.drawImage(blood2, 500, 100, 400, 400, null);
		}
		else if (back == 5)
		{
			Image victorybg = new ImageIcon("victory.jpg").getImage();
			Image stars = new ImageIcon("stars.gif").getImage();
			gifTimer.start();
			g.drawImage(victorybg,0,0,800,550,null);
			g.drawImage(stars,50,0,300,200,null);
			g.drawImage(stars,450,0,300,200,null);
		}
		Image towerA = new ImageIcon("boulder.png").getImage();
		Image towerB = new ImageIcon("cacti.png").getImage();
		Image treestump = new ImageIcon("treestump.png").getImage();
		Image fireball = new ImageIcon("fireball.gif").getImage();
		Image spikyTrap = new ImageIcon("trap.gif").getImage();
		Image char1Run = new ImageIcon("char1.gif").getImage();
		Image char1Run2 = new ImageIcon("char1back.gif").getImage();
		Image char1Stop = new ImageIcon("char1stop.png").getImage();
		Image char1Stop2 = new ImageIcon("char1stopback.png").getImage();
		Image char2Run = new ImageIcon("char2.gif").getImage();
		Image char2Run2 = new ImageIcon("char2back.gif").getImage();
		Image char2Stop = new ImageIcon("char2stop.png").getImage();
		Image char2Stop2 = new ImageIcon("char2stopback.png").getImage();
		Image char3Run = new ImageIcon("char3.gif").getImage();
		Image char3Run2 = new ImageIcon("char3back.gif").getImage();
		Image char3Stop = new ImageIcon("char3stop.png").getImage();
		Image char3Stop2 = new ImageIcon("char3stopback.png").getImage();
		if(levelNum != 0)
		{
			for(int i=0; i<7; i++)
			{
				for (int j=0; j<13; j++)
				{
					Color color = new Color(255,255,255, 75);
					g.setColor(color);
					g.drawRect(60+(j*50), 40+(i*50), 50, 50);
					Color color2 = new Color(255,255,255, 40);
					g.setColor(color2);
					g.fillRect(60+(j*50), 40+(i*50), 50, 50);
					if(containTower[i][j]==1 || (hoverTower[i][j]==1 && melee[i][j] == true))
					{
						g.drawImage(towerA, 60+(j*50), 40+(i*50), 50, 45, null);
						hoverTower[i][j] = 100;
					}
					else if(containTower[i][j]==2 || (hoverTower[i][j]==2  && melee[i][j] == true))
					{
						g.drawImage(towerB, 60+(j*50), 40+(i*50), 50, 50, null);
						hoverTower[i][j] = 100;
					}
					else if(containTower[i][j]==100)
					{
						g.drawImage(spikyTrap, 62+(j*50), 37+(i*50), 45, 45, null);
					}
					else if(containTower[i][j]==3)
					{
						g.drawImage(treestump, 62+(j*50), 37+(i*50), 45, 45, null);
					}
					if(melee[i][j] == true && showIndicator == true && containTower[i][j] == 0)
					{
						g.setColor(color);
						g.fillRect(60+(j*50), 40+(i*50), 50, 50);
					}
					
					for(int k=0; k<slime; k++)
					{
						canGo = new int[]{0, 0, 0, 0};
						
						if(slimeX[k]>=60 && passBeg==false)
							passBeg = true;
						if(slimeX[k]>=660)
							canGo[0] = 1;
						if(slimeY[k]>=340)
							canGo[2] = 1;
						if(slimeX[k]<=60 && passBeg==true)
							canGo[1] = 1;
						if(slimeY[k]<=40)
							canGo[3] = 1;
						if(containTower[i][j]!=0 && containTower[i][j]!=100)
						{
							if(rect[i][j].contains(slimeX[k]+50, slimeY[k]+25))
								canGo[0]=1;
							if(rect[i][j].contains(slimeX[k], slimeY[k]+25))
								canGo[1]=1;
							if(rect[i][j].contains(slimeX[k]+25, slimeY[k]+40))
								canGo[2]=1;
							if(rect[i][j].contains(slimeX[k]+25, slimeY[k]))
								canGo[3]=1;
						}
						while(canGo[sDirec[k]]!=0)
						{
							if(canGo[sDirec[k]]==2)
							{
								if(slimeHp[k]>=0)
								{
									slimeHp[k]-=20;
									if(slimeHp[k]<=0)
									{
										dead--;
										if(dead==0)
										{
											back = 5;
											levelNum = 0;
											clip.stop();
											victoryMusic();
											cardLayout.show(panel, "link5");
											timerStop();
											if(slime>0)
												slime = 0;
											if(worms>0)
												worms = 0;
											if(cats>0)
												cats = 0;
											timey.stop();
											repaint();
										}
									}
								}
							}
							sDirec[k] = (int)(Math.random()*4+0);
						}
					}
					for(int k=0; k<worms; k++)
					{
						canGo = new int[]{0, 0, 0, 0};
						
						if(wormX[k]>=60 && passBeg==false)
							passBeg = true;
						if(wormX[k]>=660)
							canGo[0] = 1;
						if(wormY[k]>=340)
							canGo[2] = 1;
						if(wormX[k]<=60 && passBeg==true)
							canGo[1] = 1;
						if(wormY[k]<=40)
							canGo[3] = 1;
						if(containTower[i][j]==2)
						{
							if(rect[i][j].contains(wormX[k]+50, wormY[k]+25))
								canGo[0]=2;
							if(rect[i][j].contains(wormX[k], wormY[k]+25))
								canGo[1]=2;
							if(rect[i][j].contains(wormX[k]+25, wormY[k]+50))
								canGo[2]=2;
							if(rect[i][j].contains(wormX[k]+25, wormY[k]))
								canGo[3]=2;
						}
						else if(containTower[i][j]!=0 && containTower[i][j]!=100)
						{
							if(rect[i][j].contains(wormX[k]+50, wormY[k]+25))
								canGo[0]=1;
							if(rect[i][j].contains(wormX[k], wormY[k]+25))
								canGo[1]=1;
							if(rect[i][j].contains(wormX[k]+25, wormY[k]+50))
								canGo[2]=1;
							if(rect[i][j].contains(wormX[k]+25, wormY[k]))
								canGo[3]=1;
						}
						while(canGo[wDirec[k]]!=0)
						{
							if(canGo[wDirec[k]]==2)
							{
								if(wormHp[k]>=0)
								{
									if(System.currentTimeMillis()-enemyTowerHurtCD>=500)
									{
										if(wormBubble>0)
											wormBubble--;
										else
											wormHp[k]-=50;
										enemyTowerHurtCD = System.currentTimeMillis();
									}
									if(wormHp[k]<=0)
									{
										dead--;
										if(dead==0)
										{
											back = 5;
											levelNum = 0;
											clip.stop();
											victoryMusic();
											cardLayout.show(panel, "link5");
											timerStop();
											if(slime>0)
												slime = 0;
											if(worms>0)
												worms = 0;
											if(cats>0)
												cats = 0;
											timey.stop();
											repaint();
										}
									}
								}
							}
							wDirec[k] = (int)(Math.random()*4+0);
						}
					}
					for(int k=0; k<cats; k++)
					{
						canGo = new int[]{0, 0, 0, 0};
						
						if(catX[k]>=60 && passBeg==false)
							passBeg = true;
						if(catX[k]>=660)
							canGo[0] = 1;
						if(catY[k]>=340)
							canGo[2] = 1;
						if(catX[k]<=60 && passBeg==true)
							canGo[1] = 1;
						if(catY[k]<=40)
							canGo[3] = 1;
						if(containTower[i][j]==2)
						{
							if(rect[i][j].contains(catX[k]+50, catY[k]+25))
								canGo[0]=2;
							if(rect[i][j].contains(catX[k], catY[k]+25))
								canGo[1]=2;
							if(rect[i][j].contains(catX[k]+25, catY[k]+50))
								canGo[2]=2;
							if(rect[i][j].contains(catX[k]+25, catY[k]))
								canGo[3]=2;
						}
						else if(containTower[i][j]!=0 && containTower[i][j]!=100)
						{
							if(rect[i][j].contains(catX[k]+50, catY[k]+25))
								canGo[0]=1;
							if(rect[i][j].contains(catX[k], catY[k]+25))
								canGo[1]=1;
							if(rect[i][j].contains(catX[k]+25, catY[k]+50))
								canGo[2]=1;
							if(rect[i][j].contains(catX[k]+25, catY[k]))
								canGo[3]=1;
						}
						while(canGo[cDirec[k]]!=0)
						{
							if(canGo[cDirec[k]]==2)
							{
								if(catHp[k]>=0)
								{
									if(System.currentTimeMillis()-enemyTowerHurtCD>=500)
									{
										catHp[k]-=50;
										enemyTowerHurtCD = System.currentTimeMillis();
									}
									if(catHp[k]<=0)
									{
										dead--;
										if(dead==0)
										{
											back = 5;
											levelNum = 0;
											clip.stop();
											victoryMusic();
											cardLayout.show(panel, "link5");
											timerStop();
											if(slime>0)
												slime = 0;
											if(worms>0)
												worms = 0;
											if(cats>0)
												worms = 0;
											timey.stop();
											repaint();
										}
									}
								}
							}
							cDirec[k] = (int)(Math.random()*4+0);
						}
					}
				}
			}
			if(playerHp>0)
			{
				if(charShow == 1)
				{
					if(playerDirec==0)
						g.drawImage(char1Stop2, playerX, playerY, 50, 50, null);
					else if(playerDirec==-1)
						g.drawImage(char1Stop, playerX, playerY, 50, 50, null);
					else if(playerDirec!=4)
						g.drawImage(char1Run2, playerX, playerY, 50, 50, null);
					else if (playerDirec==4)
						g.drawImage(char1Run, playerX, playerY, 50, 50, null);
				}
				else if(charShow == 2)
				{
					if(playerDirec==0)
						g.drawImage(char2Stop2, playerX, playerY, 50, 50, null);
					else if(playerDirec==-1)
						g.drawImage(char2Stop, playerX, playerY, 50, 50, null);
					else if(playerDirec!=4)
						g.drawImage(char2Run2, playerX, playerY, 50, 50, null);
					else if (playerDirec==4)
						g.drawImage(char2Run, playerX, playerY, 50, 50, null);
				}
				else if(charShow == 3)
				{
					if(playerDirec==0)
						g.drawImage(char3Stop2, playerX, playerY, 50, 50, null);
					else if(playerDirec==-1)
						g.drawImage(char3Stop, playerX, playerY, 50, 50, null);
					else if(playerDirec!=4)
						g.drawImage(char3Run2, playerX, playerY, 50, 50, null);
					else if (playerDirec==4)
						g.drawImage(char3Run, playerX, playerY, 50, 50, null);
				}
				if(blood == true)
				{
					Image bloody = new ImageIcon("bloodsplattergif.gif").getImage();
					g.drawImage(bloody,playerX, playerY, 40, 40, null);
					if(System.currentTimeMillis()-bloodCD>=500)
						blood =false;
				}
				g.setColor(Color.GRAY);
				g.fillRect(playerX, playerY-2, 40, 3);
				g.setColor(Color.GREEN);
				g.fillRect(playerX, playerY-2, (int)(40*((double)playerHp/playerMax)),3);
			}
			if(back == 6)
			{
				if(levelNum == 1)
				{
					Image level1ready = new ImageIcon("readylevel1.png").getImage();
					g.drawImage(level1ready,0,-1,787,515,null);
				}
				else if(levelNum == 2)
				{
					Image level2ready = new ImageIcon("readylevel2.png").getImage();
					g.drawImage(level2ready,0,0,787,513,null);
				}
				else if(levelNum == 3)
				{
					Image level3ready = new ImageIcon("readylevel3.png").getImage();
					g.drawImage(level3ready,1,0,785,513,null);
				}
				g.setColor(Color.BLACK);
				g.fillRect(bannerX, 200, 800, 100);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				if(bannerNumber == 0)
					g.drawString("Ready...", bannerX+350, 255);
				else if(bannerNumber == 1)
					g.drawString("Set...", bannerX+355, 255);
				else if(bannerNumber == 2)
					g.drawString("Go!", bannerX+370, 255);
			}
			if(slime>0)
			{
				for(int i=0; i<slime; i++)
				{
				if(slimeHp[i]>0)
					{
						Image slimey = new ImageIcon("slime.gif").getImage();
						Image slimey2 = new ImageIcon("backslime.gif").getImage();
						if(sDirec[i]!=1)
							g.drawImage(slimey, slimeX[i], slimeY[i], 50, 40, null);
						else if(sDirec[i]==1)
							g.drawImage(slimey2, slimeX[i], slimeY[i], 50, 40, null);
						g.setColor(Color.GRAY);
						g.fillRect(slimeX[i], slimeY[i], 40, 3);
						g.setColor(Color.RED);
						g.fillRect(slimeX[i], slimeY[i], (int)(40*((double)slimeHp[i]/slimeMax)),3);
					}
					else 
					{
						slimeTimer.stop();
						coins+=50;
					}
				}
			}
			if(worms>0)
			{
				for(int i=0; i<worms; i++)
				{
					if(wormHp[i]>0)
					{
						Image wormey = new ImageIcon("fireworm.gif").getImage();
						Image wormey2= new ImageIcon("firewormback.gif").getImage();
						Image bubble = new ImageIcon("bubble.png").getImage();
						if(wDirec[i]!=1)
							g.drawImage(wormey, wormX[i], wormY[i], 50, 50, null);
						else if(wDirec[i]==1)
							g.drawImage(wormey2, wormX[i], wormY[i], 50, 50, null);
						if(wormBubble>0)
							g.drawImage(bubble, wormX[i]-10, wormY[i]-3, 70, 65, null);
						g.setColor(Color.GRAY);
						g.fillRect(wormX[i]+5, wormY[i]-2, 40, 3);
						if(wormBubble<=0)
						{
							g.setColor(Color.RED);
							g.fillRect(wormX[i]+5, wormY[i]-2, (int)(40*((double)wormHp[i]/wormMax)),3);
						}
						else
						{
							g.setColor(Color.CYAN);
							g.fillRect(wormX[i]+5, wormY[i]-2, (int)(40*((double)wormBubble/2)),3);
						}
					}
					else
					{
						wormTimer.stop();
						coins+=50;
					}
				}
			}	
			if(cats>0)
			{
				for(int i=0; i<cats; i++)
				{
					if(catHp[i]>0)
					{
						Image catright = new ImageIcon("catwalkright.gif").getImage();
						Image catleft = new ImageIcon("catwalkleft.gif").getImage();
						Image catback = new ImageIcon("catwalkback.gif").getImage();
						Image catforward = new ImageIcon("catwalkforward.gif").getImage();
						if(cDirec[i]==0)
							g.drawImage(catright, catX[i], catY[i], 50, 50, null);
						else if(cDirec[i]==1)
							g.drawImage(catleft, catX[i], catY[i], 50, 50, null);
						else if(cDirec[i]==2)
							g.drawImage(catforward, catX[i], catY[i], 50, 50, null);
						else if(cDirec[i]==3)
							g.drawImage(catback, catX[i], catY[i], 50, 50, null);
						g.setColor(Color.GRAY);
						g.fillRect(catX[i], catY[i], 40, 3);
						g.setColor(Color.RED);
						g.fillRect(catX[i], catY[i], (int)(40*((double)catHp[i]/catMax)),3);
					}
					else
					{
						if(catHp[i]!=-1)
						{
							catX[i] = -10;
							catY[i] = -10;
							coins+=50;
							catHp[i]=-1;
						}
					}
				}
			}	
			if(boulderShowDesc && levelNum ==1)
			{
				g.setColor(Color.WHITE);
				g.fillRect(200, 415, 120, 50);
				g.fillPolygon(new int[]{320,330,320}, new int[]{430, 440, 450}, 3);
				g.setColor(Color.BLACK);
				g.setFont(new Font("Monospaced", Font.PLAIN, 8));
				g.drawString("Cost: 50 coins", 205, 425);
				g.drawString("An ordinary rock. Place", 205, 435);
				g.drawString("to block enemy movement", 205, 445);
				g.drawString("in a single direction.", 205, 455);
			}
			if(boulderShowDesc && (levelNum ==2 || levelNum==3))
			{
				g.setColor(Color.WHITE);
				g.fillRect(100, 410, 120, 60);
				g.fillPolygon(new int[]{220,230,220}, new int[]{430, 440, 450}, 3);
				g.setColor(Color.BLACK);
				g.setFont(new Font("Monospaced", Font.PLAIN, 8));
				g.drawString("Cost: 50 coins", 105, 420);
				g.drawString("An ordinary rock. Place", 105, 430);
				g.drawString("to block enemy movement", 105, 440);
				g.drawString("in a single direction.", 105, 450);
			}
			if(cactiShowDesc && (levelNum ==2 || levelNum==3))
			{
				g.setColor(Color.WHITE);
				g.fillRect(520, 410, 120, 60);
				g.fillPolygon(new int[]{520,510,520}, new int[]{430, 440, 450}, 3);
				g.setColor(Color.BLACK);
				g.setFont(new Font("Monospaced", Font.PLAIN, 8));
				g.drawString("Cost: 100 coins", 525, 420);
				g.drawString("A spiky cacti. Place", 525, 430);
				g.drawString("to block enemy movement", 525, 440);
				g.drawString("and deal damage when hit.", 525, 450);
				g.drawString("Can hurt player.", 525, 460);
			}
		}
		if(back!=0 && count!=0)
		{
			gifTimer.stop();
			count = 0;
		}
		if(back!=2 && count2!=0)
		{
			skeleTimer.stop();
			count2 = 0;
		}
	}
	class SkeleMover implements ActionListener  //moves the skeleton in the level select screen
	{
		public void actionPerformed(ActionEvent e)
		{
			if(skeleX>=800)
				forward = false;
			if(skeleX<=0)
				forward = true;
			if(skeleX2>=800)
				forward2 = false;
			if(skeleX2<=0)
				forward2 = true;
			if(forward == true)
				skeleX+=1;
			else if (forward ==false)
				skeleX-=1;
			if(forward2 == true)
				skeleX2+=1;
			else if (forward2 == false)
				skeleX2-=1;
			repaint();
		}
	}
	class GifTimer implements ActionListener //makes the homepage gif for background move
	{
		public void actionPerformed(ActionEvent e)
		{
			repaint();
		}
	}
	public void runIt() //main
	{
		back = 0;
		levelNum = 0;
		cardLayout = new CardLayout();
		panel = new JPanel();
		homey = new JPanel();
		playIt = new JPanel();  
		howToPlay = new JPanel();
		panel.setPreferredSize(new Dimension(800,500));
		panel.setOpaque(false);
		homey.setOpaque(false);
		playIt.setOpaque(false);
		howToPlay.setOpaque(false);
		howToPlay.setLayout(null);
		charPanel = new JPanel();
		charPanel.setLayout(null);
		charPanel.setOpaque(false);
		
		rect = new Rectangle [7][13];
		containTower = new int [7][13];
		hoverTower = new int[7][13];
		melee = new boolean[7][13];
		for(int i =0; i<7; i++)
		{
			for (int j=0; j<13; j++)
			{
				rect[i][j] = new Rectangle(60+(j*50), 40+(i*50), 50, 50);
				containTower[i][j] = 0;
				hoverTower[i][j] = 100;
			}
		}

		buttPurple = new Color(65,9,133);
		homePage();
		levelPage();
		howToPage();
		losingPage();
		winningPage();
		charSelectPage();
		
		panel.setLayout(cardLayout);
		cardLayout.show(panel, "link1");
		panel.add(homey, "link1");
		panel.add(playIt, "link2");
		panel.add(howToPlay, "link3");
		panel.add(winPanel, "link5");
		panel.add(losePanel, "link6");
		panel.add(readyPanel, "link7");
		panel.add(charPanel, "link8");
		
		backMusic();
		add(panel);
	}
	public void homePage() // adds elements to homepage
	{
		ImageIcon titleImg = new ImageIcon("title.png");
		JLabel title = new JLabel();
		title.setIcon(titleImg);
		JLabel spacing1 = new JLabel();
		spacing1.setPreferredSize(new Dimension(200, 10));
		JPanel movePanel = new JPanel();
		movePanel.setPreferredSize(new Dimension(200, 200));
		
		Icon playButt = new ImageIcon("playButt.png");
		JButton play = new JButton(playButt);
		play.setOpaque(false);
		play.setContentAreaFilled(false); 
		JLabel spacing2 = new JLabel();
		spacing2.setPreferredSize(new Dimension(200, 10));
		Icon howButt = new ImageIcon("howToButt.png");
		JButton howto = new JButton(howButt);
		howto.setOpaque(false);
		howto.setContentAreaFilled(false); 
		PlayButton pb = new PlayButton();
		play.addActionListener(pb);
		HowButton hb = new HowButton();
		howto.addActionListener(hb);
		play.setPreferredSize(new Dimension(200, 75));
		howto.setPreferredSize(new Dimension(200, 75));
		movePanel.setOpaque(false);
		
		movePanel.add(spacing1);
		movePanel.add(play);
		movePanel.add(spacing2);
		movePanel.add(howto);
		homey.add(title);
		homey.add(movePanel);
	}
	class PlayButton implements ActionListener  //action handler for play button
	{
		public void actionPerformed(ActionEvent e)
		{
			back = 2;
			buttonClick();
			clip.stop();
			levelMusic();
			cardLayout.show(panel,"link2");
			repaint();
		}
	}
	class HowButton implements ActionListener  // action handler for how to button
	{
		public void actionPerformed(ActionEvent e)
		{
			back = 3;
			buttonClick();
			clip.stop();
			howToMusic();
			cardLayout.show(panel,"link3");
			repaint();
		}
	}
	public void levelPage() // adds elements to level select page
	{
		JLabel playTitle = new JLabel();
		ImageIcon playImg = new ImageIcon("playword.png");
		playTitle.setIcon(playImg);
		JLabel format1 = new JLabel();
		format1.setPreferredSize(new Dimension(450,75));
		JLabel format2 = new JLabel();
		format2.setPreferredSize(new Dimension(700,30));

		ImageIcon backImg = new ImageIcon("backbutton.png");
		JButton backButt1 = new JButton(backImg);
		BackButton bb = new BackButton();
		backButt1.addActionListener(bb);
		backButt1.setPreferredSize(new Dimension(150, 50));
		backButt1.setOpaque(false);
		backButt1.setContentAreaFilled(false); 
		
		ImageIcon lvl1pic = new ImageIcon("level1butt.png");
		ImageIcon lvl2pic = new ImageIcon("level2butt.png");
		ImageIcon lvl3pic = new ImageIcon("level3butt.png");
		JButton lvl1 = new JButton(lvl1pic);
		JButton lvl2 = new JButton(lvl2pic);
		JButton lvl3 = new JButton(lvl3pic);
		Level1Button l1 = new Level1Button();
		Level2Button l2 = new Level2Button();
		Level3Button l3 = new Level3Button();
		lvl1.setOpaque(false);
		lvl1.setContentAreaFilled(false); 
		lvl2.setOpaque(false);
		lvl2.setContentAreaFilled(false); 
		lvl3.setOpaque(false);
		lvl3.setContentAreaFilled(false); 
		lvl1.addActionListener(l1);
		lvl2.addActionListener(l2);
		lvl3.addActionListener(l3);
		lvl1.setPreferredSize(new Dimension(200, 199));
		lvl2.setPreferredSize(new Dimension(200, 199));
		lvl3.setPreferredSize(new Dimension(200, 199));
		ImageIcon charPic = new ImageIcon("charselectbutt.png");
		JButton charSelect = new JButton(charPic);
		charSelect.setPreferredSize(new Dimension(420, 75));
		charSelect.setOpaque(false);
		charSelect.setContentAreaFilled(false);
		CharButton cb = new CharButton();
		charSelect.addActionListener(cb);
		
		playIt.add(playTitle);
		playIt.add(format1);
		playIt.add(backButt1);
		playIt.add(format2);
		playIt.add(lvl1);
		playIt.add(lvl2);
		playIt.add(lvl3);
		playIt.add(charSelect);
	}
	class Level1Button implements ActionListener  //action handler for the button for level 1
	{
		public void actionPerformed(ActionEvent e)
		{
			back = 6;
			buttonClick();
			cardLayout.show(panel,"link7");
			clip.stop();
			readySetGoSound();
			levelNum = 1;
			readySetGoPage();
			repaint();
		}
	}
	class Level2Button implements ActionListener //action handler for the button for level 2
	{
		public void actionPerformed(ActionEvent e)
		{
			back = 6;
			buttonClick();
			cardLayout.show(panel,"link7");
			clip.stop();
			readySetGoSound();
			levelNum = 2;
			readySetGoPage();
			repaint();
		}
	}
	class Level3Button implements ActionListener  //action handler for the button for level 3
	{
		public void actionPerformed(ActionEvent e)
		{
			back = 6;
			buttonClick();
			cardLayout.show(panel,"link7");
			clip.stop();
			readySetGoSound();
			levelNum = 3;
			readySetGoPage();
			repaint();
		}
	}
	class CharButton implements ActionListener   //action handler for the button for the character selection page
	{
		public void actionPerformed(ActionEvent e)
		{
			buttonClick();
			cardLayout.show(panel, "link8");
		}
	}
	public void charSelectPage()  //adds stuff to the character select panel
	{
		char1Panel = new JPanel();
		char1Panel.setLayout(null);
		char1Panel.setBounds(120, 120, 550, 320);
		char1Panel.setBackground(Color.BLACK);
		char2Panel = new JPanel();
		char2Panel.setLayout(null);
		char2Panel.setBounds(120, 120, 550, 320);
		char2Panel.setBackground(Color.BLACK);
		char3Panel = new JPanel();
		char3Panel.setLayout(null);
		char3Panel.setBounds(120, 120, 550, 320);
		char3Panel.setBackground(Color.BLACK);
		
		ImageIcon char1 = new ImageIcon("char1stop.png");
		ImageIcon char2 = new ImageIcon("char2stop.png");
		ImageIcon char3 = new ImageIcon("char3stop.png");
		
		JLabel char1Pic = new JLabel(char1);
		char1Pic.setBounds(50, 50, 50, 50);
		JTextArea char1Info = new JTextArea("Adventurer:\nA hardy adventurer who happened to\nstumble upon these woods.\n\nAgility: Low\nHP: High");
		char1Info.setFont(new Font("Monospaced", Font.PLAIN, 14));
		char1Info.setForeground(Color.WHITE);
		char1Info.setOpaque(false);
		char1Info.setBounds(125, 50, 400, 200);
		JLabel char2Pic = new JLabel(char2);
		char2Pic.setBounds(50, 50, 50, 50);
		JTextArea char2Info = new JTextArea("Red Riding Hood:\nA small girl that may seem harmless at\nfirst sight, but her speed is unmatched.\n\nAgility: High\nHP: Low");
		char2Info.setFont(new Font("Monospaced", Font.PLAIN, 14));
		char2Info.setForeground(Color.WHITE);
		char2Info.setOpaque(false);
		char2Info.setBounds(125, 50, 400, 200);
		JLabel char3Pic = new JLabel(char3);
		char3Pic.setBounds(50, 50, 50, 50);
		JTextArea char3Info = new JTextArea("Ninja:\nA highly skilled and meticulously trained\nninja, master of all arts.\n\nAgility: Medium\nHP: Medium");
		char3Info.setFont(new Font("Monospaced", Font.PLAIN, 14));
		char3Info.setForeground(Color.WHITE);
		char3Info.setOpaque(false);
		char3Info.setBounds(125, 50, 400, 200);
		ImageIcon left = new ImageIcon("arrowleft.png");
		ImageIcon right = new ImageIcon("arrowright.png");
		JButton arrowNext = new JButton(right);
		JButton arrowBack = new JButton(left); 
		arrowBack.setBounds(30, 220, 70, 86);
		arrowNext.setBounds(700, 220, 70, 86);
		CharNext cnext = new CharNext();
		CharBack cback = new CharBack();
		arrowNext.addActionListener(cnext);
		arrowBack.addActionListener(cback);
		ImageIcon backPic = new ImageIcon("backbutton.png");
		JButton back = new JButton(backPic);
		back.setBounds(625, 25, 150, 50);
		BackToLevel btl = new BackToLevel();
		back.addActionListener(btl);
		
		charPanel.add(arrowNext);
		charPanel.add(arrowBack);
		char1Panel.add(char1Pic);
		char2Panel.add(char2Pic);	
		char3Panel.add(char3Pic);
		char1Panel.add(char1Info);
		char2Panel.add(char2Info);
		char3Panel.add(char3Info);		
		charPanel.add(char1Panel);
		charPanel.add(back);
	}
	class CharNext implements ActionListener  //action handler for the button on right side of character select
	{
		public void actionPerformed(ActionEvent e)
		{
			buttonClick();
			if(charShow == 1)
			{
				charPanel.remove(char1Panel);
				charPanel.add(char2Panel);
				charShow++;
				charPanel.repaint();
			}
			else if(charShow == 2)
			{
				charPanel.remove(char2Panel);
				charPanel.add(char3Panel);
				charShow++;
				charPanel.repaint();
			}
			else if(charShow == 3)
			{
				charPanel.remove(char3Panel);
				charPanel.add(char1Panel);
				charShow = 1;
				charPanel.repaint();
			}
		}
	}
	class CharBack implements ActionListener //action handler for the button on left side of character select
	{
		public void actionPerformed(ActionEvent e)
		{
			buttonClick();
			if(charShow == 1)
			{
				charPanel.remove(char1Panel);
				charPanel.add(char3Panel);
				charShow = 3;
				charPanel.repaint();
			}
			else if(charShow == 2)
			{
				charPanel.remove(char2Panel);
				charPanel.add(char1Panel);
				charShow--;
				charPanel.repaint();
			}
			else if(charShow == 3)
			{
				charPanel.remove(char3Panel);
				charPanel.add(char2Panel);
				charShow--;
				charPanel.repaint();
			}
		}
	}
	class BackToLevel implements ActionListener  //action handler for back button in the character select page
	{
		public void actionPerformed(ActionEvent e)
		{
			buttonClick();
			cardLayout.show(panel, "link2");
		}
	}
	public void howToPage ()  // adds elements to how to page
	{
		JLabel instrucTitle = new JLabel();
		ImageIcon instrucImg = new ImageIcon("howtoword.png");
		instrucTitle.setIcon(instrucImg);
		instrucTitle.setBounds(20, 5, 396, 90);
		intro  = new JTextArea("It is October 31, the night of Halloween.\n\nMonsters are wreaking havoc across the world, and it is your\njob to stop them! Use constructs and traps and your knowledge\nof combat to defeat these monsters!");
		intro.setBounds(120, 120, 550, 330);
		traps = new JTextArea("Traps:\n\nThere will be a set number of traps at the start of each level.\nUsing constructs, you must make the monster walk into these\ntraps. If an enemy walks into a trap, it will die immediately\nregardless of current HP if unshielded.\n In Level 3, the trap will move locations when a certain amount\nof time has elapsed, so be vigilant.");
		traps.setBounds(120, 120, 550, 200);
		constructs = new JTextArea("Constructs:\n\nConstructs are used to manuveur enemies.\nClick the corresponding button at the bottom of the playscreen\nand click any box shown in the indicator to place a construct.\nYou need coins to purchase towers, which you can earn over time\nor by killing enemies. Different constructs have different\nattributes and prices, so choose your constructs wisely.\nMonsters cannot pass through constructs, so place them\nstrategically.");
		constructs.setBounds(120, 120, 550, 220);
		enemies = new JTextArea("Enemies:\n\nDifferent types of enemies have varying levels of HP, speed,\nand damage. You can find information about the monsters in each\nlevel by clicking the information button in the bottom left\ncorner of each level.\nBe careful, monsters can deal damage to you if you get too\nclose. Some constructs may be more effective towards some types\nof enemies.");
		enemies.setBounds(120, 120, 550, 200);
		ImageIcon infoButton = new ImageIcon("infobutton.png");
		infoEx = new JLabel(infoButton);
		infoEx.setOpaque(false);
		infoEx.setBounds(150,200, 20, 20);
		intro.setFont(new Font("Monospaced", Font.PLAIN, 14));
		traps.setFont(new Font("Monospaced", Font.PLAIN, 14));
		constructs.setFont(new Font("Monospaced", Font.PLAIN, 14));
		enemies.setFont(new Font("Monospaced", Font.PLAIN, 14));
		intro.setForeground(Color.WHITE);
		traps.setForeground(Color.WHITE);
		constructs.setForeground(Color.WHITE);
		enemies.setForeground(Color.WHITE);
		intro.setBackground(Color.BLACK);
		traps.setBackground(Color.BLACK);
		constructs.setBackground(Color.BLACK);
		enemies.setBackground(Color.BLACK);
		
		ImageIcon slimePic = new ImageIcon("slime.gif");
		ImageIcon wormPic = new ImageIcon("fireworm.gif");
		ImageIcon catPic = new ImageIcon("catwalkright.gif");
		 slimeEx = new JLabel(slimePic);
		 wormEx = new JLabel(wormPic);
		 catEx = new JLabel(catPic);
		slimeEx.setBackground(Color.BLACK);
		slimeEx.setOpaque(true);
		slimeEx.setBounds(120, 300, 183, 150);
		wormEx.setOpaque(true);
		wormEx.setBackground(Color.BLACK);
		wormEx.setBounds(303, 300, 183, 150);
		catEx.setOpaque(true);
		catEx.setBackground(Color.BLACK);
		catEx.setBounds(486, 300, 184, 150);
		ImageIcon boulderPic = new ImageIcon("boulder.png");
		ImageIcon cactusPic = new ImageIcon("cacti.png");
		boulderEx = new JLabel(boulderPic);
		cactusEx = new JLabel(cactusPic);
		boulderEx.setBackground(Color.BLACK);
		boulderEx.setOpaque(true);
		boulderEx.setBounds(120, 300, 275, 150);
		cactusEx.setBackground(Color.BLACK);
		cactusEx.setOpaque(true);
		cactusEx.setBounds(395, 300, 275, 150);
		ImageIcon trapPic = new ImageIcon("trap.gif");
		trapEx = new JLabel(trapPic);
		trapEx.setBackground(Color.BLACK);
		trapEx.setOpaque(true);
		trapEx.setBounds(120, 300, 550, 150);
		
		ImageIcon back = new ImageIcon("backbutton.png");
		JButton backButt2 = new JButton(back);
		BackButton bb = new BackButton();
		backButt2.addActionListener(bb);
		backButt2.setBounds(620, 20, 150, 50);
		backButt2.setPreferredSize(new Dimension(150, 50));
		ImageIcon left = new ImageIcon("arrowleft.png");
		ImageIcon right = new ImageIcon("arrowright.png");
		JButton arrowRight = new JButton(right);
		JButton arrowLeft = new JButton(left);
		arrowLeft.setBounds(30, 220, 70, 86);
		arrowRight.setBounds(700, 220, 70, 86);
		ButtonNext bnext = new ButtonNext();
		ButtonBack bback = new ButtonBack();
		arrowRight.addActionListener(bnext);
		arrowLeft.addActionListener(bback);
		
		howToPlay.add(instrucTitle);
		howToPlay.add(backButt2);
		howToPlay.add(arrowLeft);
		howToPlay.add(arrowRight);
		howToPlay.add(intro);
	}
	class ButtonNext implements ActionListener  //  action handler for the button on right side of how to play page
	{
		public void actionPerformed(ActionEvent e)
		{
			buttonClick();
			if(instructionCard==0)
			{
				howToPlay.remove(intro);
				instructionCard++;
				howToPlay.add(traps);
				howToPlay.add(trapEx);
				howToPlay.repaint();
			}
			else if(instructionCard==1)
			{
				howToPlay.remove(traps);
				howToPlay.remove(trapEx);
				instructionCard++;
				howToPlay.add(constructs);
				howToPlay.add(boulderEx);
				howToPlay.add(cactusEx);
				howToPlay.repaint();
			}
			else if(instructionCard==2)
			{
				howToPlay.remove(constructs);
				howToPlay.remove(boulderEx);
				howToPlay.remove(cactusEx);
				instructionCard++;
				howToPlay.add(enemies);
				howToPlay.add(slimeEx);
				howToPlay.add(wormEx);
				howToPlay.add(catEx);
				howToPlay.repaint();
			}
			else if(instructionCard==3)
			{
				howToPlay.remove(enemies);
				howToPlay.remove(slimeEx);
				howToPlay.remove(wormEx);
				howToPlay.remove(catEx);
				instructionCard=0;
				howToPlay.add(intro);
				howToPlay.repaint();
			}
		}
	}
	class ButtonBack implements ActionListener   //  action handler for the button on left side of how to play page
	{
		public void actionPerformed(ActionEvent e)
		{
			buttonClick();
			if(instructionCard==0)
			{
				howToPlay.remove(intro);
				instructionCard = 3;
				howToPlay.add(enemies);
				howToPlay.add(slimeEx);
				howToPlay.add(wormEx);
				howToPlay.add(catEx);
				howToPlay.repaint();
			}
			else if(instructionCard==1)
			{
				howToPlay.remove(traps);
				howToPlay.remove(trapEx);
				instructionCard--;
				howToPlay.add(intro);
				howToPlay.repaint();
			}
			else if(instructionCard==2)
			{
				howToPlay.remove(constructs);
				howToPlay.remove(boulderEx);
				howToPlay.remove(cactusEx);
				instructionCard--;
				howToPlay.add(traps);
				howToPlay.add(trapEx);
				howToPlay.repaint();
			}
			else if(instructionCard==3)
			{
				howToPlay.remove(enemies);
				howToPlay.remove(slimeEx);
				howToPlay.remove(wormEx);
				howToPlay.remove(catEx);
				instructionCard--;
				howToPlay.add(constructs);
				howToPlay.add(boulderEx);
				howToPlay.add(cactusEx);
				howToPlay.repaint();
			}
		}
	}
	class BackButton implements ActionListener //handles back buttons in play and how to play pages and links back to home
	{
		public void actionPerformed(ActionEvent e)
		{
			back = 0;
			buttonClick();
			cardLayout.show(panel, "link1");
			clip.stop();
			backMusic();
		}
	}
	public void losingPage()  // adds elements to defeat page
	{
		losePanel = new JPanel();
		losePanel.setOpaque(false);
		losePanel.setLayout(null);
		ImageIcon quitPic = new ImageIcon("quitbutton.png");
		JButton quit = new JButton(quitPic);
		quit.setBounds(325, 200, 150, 67);
		QuitButton qb = new QuitButton();
		quit.addActionListener(qb);
		ImageIcon startPic = new ImageIcon("startoverbutt.png");
		JButton startOver = new JButton(startPic);
		startOver.setBounds(325, 300, 150, 67);
		StartButton sb = new StartButton();
		startOver.addActionListener(sb);
		
		losePanel.add(quit);
		losePanel.add(startOver);
	}
	public void winningPage ()  // adds elements to victory page
	{
		winPanel = new JPanel();
		winPanel.setOpaque(false);
		winPanel.setLayout(null);
		ImageIcon quitPic = new ImageIcon("quitbutton.png");
		JButton quit1 = new JButton(quitPic);
		quit1.setBounds(325, 200, 150, 67);
		QuitButton qb = new QuitButton();
		quit1.addActionListener(qb);
		ImageIcon startPic = new ImageIcon("startoverbutt.png");
		JButton startOver = new JButton(startPic);
		startOver.setBounds(325, 300, 150, 67);
		StartButton sb = new StartButton();
		startOver.addActionListener(sb);
		
		winPanel.add(quit1);
		winPanel.add(startOver);
	}
	class QuitButton implements ActionListener  // action handler for quit button on the winning/losing panel
	{
		public void actionPerformed(ActionEvent e)
		{
			cardLayout.show(panel, "link2");
			buttonClick();
			back = 2;
			levelNum = 0;
			playerX = 650;
			playerY = 190;
			playerTimer.stop();
			gifTimer.stop();
			timey.stop();
			showIndicator = false;
			if(slime>0)
				slimeTimer.stop();
			if(worms>0)
				wormTimer.stop();
			if(cats>0)
				catTimer.stop();
			for(int i=0; i<7; i++)
			{
				for (int j=0; j<13; j++)
				{
					containTower[i][j] = 0;
				}
			}
			if(slime>0)
				slime = 0;
			if(worms>0)
				worms = 0;
			if(cats>0)
				cats = 0;
			clip.stop();
			levelMusic();
			repaint();
		}
	}
	class StartButton implements ActionListener // action handler for start over button on defeat/victory screens
	{
		public void actionPerformed(ActionEvent e)
		{
			buttonClick();
			playerX = 650;
			playerY = 190;
			playerTimer.stop();
			gifTimer.stop();
			showIndicator = false;
			if(slime>0)
				slimeTimer.stop();
			if(worms>0)
				wormTimer.stop();
			if(cats>0)
				catTimer.stop();
			for(int i=0; i<7; i++)
			{
				for (int j=0; j<13; j++)
				{
					containTower[i][j] = 0;
				}
			}
			if(slime>0)
				slime = 0;
			if(worms>0)
				worms = 0;
			if(cats>0)
				cats = 0;
			back = 6;
			buttonClick();
			cardLayout.show(panel,"link7");
			clip.stop();
			readySetGoSound();
			levelNum = 1;
			readySetGoPage(); 
			repaint();
		}
	}
	public void readySetGoPage ()  // timer for ready set go animation
	{
		ReadyAnimate ra = new ReadyAnimate();
		readyTimer = new Timer(1, ra);
		readyTimer.start();
	}
	class ReadyAnimate implements ActionListener  // animates the ready set go 
	{
		public void actionPerformed(ActionEvent e)
		{
			if(bannerX!=0)
				bannerX+=20;
			else if(bannerX==0)
			{
				if(pause==0)
					pause = System.currentTimeMillis();
				if(System.currentTimeMillis()-pause>=1000)
				{
					bannerX= -800;
					bannerNumber++;
					pause = 0;
				}
			}
			if(bannerNumber==3 )
			{
				readyTimer.stop();
				back = 1;
				bannerNumber = 0;
				bannerX = -800;
				Level ll = new Level();
				cardLayout.show(panel, "link4");
				battleMusic();
			}
			repaint();
			requestFocus();
		}
	}
	public void backMusic() //play homepage music
	{
		File file = new File("Sounds/toshalloween.wav");
		try
		{	
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.loop(clip.LOOP_CONTINUOUSLY);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
		
	}
	public void levelMusic() //play level select music
	{
		File file = new File("levelmusic.wav");
		try
		{	
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.loop(clip.LOOP_CONTINUOUSLY);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	public void howToMusic()  // play how to play  music
	{
		File file = new File("howtomusic.wav");
		try
		{	
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.loop(clip.LOOP_CONTINUOUSLY);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	public void battleMusic()  // play battle music
	{
		File file = new File("battlemusic.wav");
		try
		{	
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.loop(clip.LOOP_CONTINUOUSLY);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	public void defeatMusic()  // play defeat music
	{
		File file = new File("defeatmusic.wav");
		try
		{	
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.loop(clip.LOOP_CONTINUOUSLY);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	public void victoryMusic()  // play victory music
	{
		File file = new File("victorymusic.wav");
		try
		{	
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.loop(clip.LOOP_CONTINUOUSLY);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	public void readySetGoSound()  //play ready set go sound
	{
		File file = new File("readysetgosfx.wav");
		try
		{	
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	public void buttonClick()  //play button click sound
	{
		try
		{	
			File buttonSound = new File("click.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(buttonSound);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	public void lightningStrike()  //play lightning strike sound
	{
		try
		{	
			File file = new File("lightningsfx.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	public void hurtSound()  // play one of two hurt sounds
	{
		int sound = (int)(Math.random()*2+0);
		File file;
		if(sound==0)
			file = new File("hurt1.wav");
		else
			file = new File("hurt2.wav");
		try
		{	
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	public void deathScream()  // play death scream upon defeat
	{
		try
		{	
			File scream = new File("death.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(scream);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	public void cactusSound() // play cacti placing sound
	{
		try
		{	
			File scream = new File("cactusSound.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(scream);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	public void bloodSplat()  // cooldown for blood splatter vfx when you get hurt
	{
		if(System.currentTimeMillis()-bloodCD>=300)
		{
			blood = true;
			bloodCD = System.currentTimeMillis();
			repaint();
		}
	}
	public void timerStart()  //starts timer
	{
		playerTimer.start();
		for(int i=0; i<slime; i++)
		{
			if(slime>0)
			{
				slimeTimer.start();
			}
		}
		for(int i=0; i<worms; i++)
		{
			if(worms>0)
			{
				wormTimer.start();
			}
		}
		for(int i=0; i<cats; i++)
		{
			if(cats>0)
			{
				catTimer.start();
			}
		}
	}
	public void timerStop()  //stops timers
	{ 
		playerTimer.stop();
		if(slime>0)
		{
			slimeTimer.stop();
		}
		if(worms>0)
		{
			wormTimer.stop();
		}
		if(cats>0)
		{
			catTimer.stop();
		}
	}
	class Level implements MouseListener, MouseMotionListener, KeyListener  //runs levels of the game
	{
		JLabel formlvl1, formlvl2, showTimer, levelIndicator;
		JButton backButt3, enemyInfo;
		JPanel enemyInfoBox;
		long startTime;
		long timeAllowed;
		boolean moved;
		public Level() //constructor for running "runner" method
		{
			runner();
			addMouseListener(this);
			addMouseMotionListener(this);
			addKeyListener(this);
		}
		public void runner() //runner for the class level
		{
			playPanel = new JPanel();
			playPanel.setOpaque(false);
			playPanel.setLayout(null);
			playPanel.setPreferredSize(new Dimension(800, 500));
			playPanel.setFocusable(true);
			
			if(levelNum==1)
				level1();
			else if(levelNum==2)
				level2();
			else if(levelNum==3)
				level3();
				
			levelIndicator = new JLabel("Level "+levelNum);
			levelIndicator.setOpaque(true);
			levelIndicator.setBackground(Color.WHITE);
			levelIndicator.setFont(new Font("Monospaced", Font.PLAIN, 15));
			levelIndicator.setBounds(670, 460, 100, 25);
			levelIndicator.setHorizontalAlignment(JLabel.CENTER);
			
			towerBuild = new boolean[2];
			ImageIcon quitButton = new ImageIcon("quitbutton2.png");
			backButt3 = new JButton(quitButton);
			backButt3.setBounds(30, 5, 114, 25);
			QuitButton qb = new QuitButton();
			backButt3.addActionListener(qb);
			Color greengrass = new Color(47,133,61);
			
			coinCount = new JLabel("Coins: "+coins);
			coinCount.setOpaque(true);
			coinCount.setBackground(Color.YELLOW);
			coinCount.setBounds(670, 5, 100, 25);
			coinCount.setHorizontalAlignment(JLabel.CENTER);
			coinCount.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
			showTimer = new JLabel();
			showTimer.setBounds(350, 5, 100, 25);
			showTimer.setHorizontalAlignment(JLabel.CENTER);
			showTimer.setOpaque(true);

			ImageIcon info = new ImageIcon("infobutton.png");
			enemyInfo = new JButton(info);
			enemyInfo.setOpaque(false);
			enemyInfo.setContentAreaFilled(false); 
			enemyInfo.setFocusPainted(false); 
			enemyInfo.setBorderPainted(false);
			enemyInfo.setLayout(null);
			enemyInfo.setBounds(30, 450, 30, 30);
			InfoButton ib = new InfoButton();
			enemyInfo.addActionListener(ib);
			
			hoverDescriptions();
			
			playPanel.add(backButt3);
			playPanel.add(showTimer);
			playPanel.add(coinCount);
			playPanel.add(enemyInfo);
			playPanel.add(levelIndicator);
			if(levelNum ==1)
				playPanel.add(boulder);
			else if(levelNum==2 || levelNum==3)
			{
				playPanel.add(boulder);
				playPanel.add(cactus);
			}
			
			panel.add(playPanel, "link4");
		}
		public void level1() //variables and info for level 1
		{
			timeAllowed = 120000;
			startTime = System.currentTimeMillis();
			coins = 300;
			if(charShow == 1)
				playerHp = 150;
			else if(charShow == 2)
				playerHp = 80;
			else if(charShow == 3)
				playerHp = 120;
			playerMax = playerHp;
			slime = 1;
			dead = 1;
			slimeHp = new int[slime];
			slimeMax = 100;
			sDirec = new int[slime];
			slimeX = new int[slime];
			slimeY = new int[slime];
			SlimeMover sm = new SlimeMover();
			slimeTimer = new Timer(20, sm);
			for(int i=0; i<slime; i++)
			{
				slimeX[i] = (i*-15);
				slimeY[i] = 190;
				slimeHp[i] = 100;
				sDirec[i] = 0;
			}
			ImageIcon boulderButt = new ImageIcon("boulderButton.png");
			boulder = new JButton(boulderButt);
			boulder.setOpaque(false);
			boulder.setContentAreaFilled(false); 
			boulder.setFocusPainted(false); 
			boulder.setBorderPainted(false);
			BoulderMaker bm = new BoulderMaker();
			boulder.addActionListener(bm);
			boulder.setBounds(340, 390, 125, 82);
			containTower[5][11] = 100;
			TimeHandler tm = new TimeHandler();
			timey = new Timer(5, tm);
			timey.start();
			PlayerMover pm  = new PlayerMover();
			playerTimer = new Timer(10, pm);
			playerTimer.start();
			slimeTimer.start();
		}
		public void level2()  // adds values for level 2
		{
			timeAllowed = 60000;
			startTime = System.currentTimeMillis();
			coins = 300;
			if(charShow == 1)
				playerHp = 150;
			else if(charShow == 2)
				playerHp = 80;
			else if(charShow == 3)
				playerHp = 120;
			playerMax = playerHp;
			wormBubble = 2;
			worms = 1;
			dead = 1;
			wormHp = new int[worms];
			wormMax = 200;
			wDirec = new int[worms];
			wormX = new int[worms];
			wormY = new int[worms];
			WormMover wm = new WormMover();
			wormTimer = new Timer(40, wm);
			for(int i=0; i<worms; i++)
			{
				wormX[i] = (i*-30)-100;
				wormY[i] = 190;
				wormHp[i] = 200;
				wDirec[i] = 0;
			}
			ImageIcon boulderButt = new ImageIcon("boulderButton.png");
			boulder = new JButton(boulderButt);
			boulder.setOpaque(false);
			boulder.setContentAreaFilled(false); 
			boulder.setFocusPainted(false); 
			boulder.setBorderPainted(false);
			BoulderMaker bm = new BoulderMaker();
			boulder.addActionListener(bm);
			boulder.setBounds(250, 390, 125, 82);
			ImageIcon cactiPic = new ImageIcon("cactibutton.png");
			cactus = new JButton(cactiPic);
			cactus.setOpaque(false);
			cactus.setContentAreaFilled(false); 
			cactus.setFocusPainted(false); 
			cactus.setBorderPainted(false);
			CactusMaker cm = new CactusMaker();
			cactus.addActionListener(cm);
			cactus.setBounds(400, 390, 125, 82);
			containTower[5][11] = 100;
			TimeHandler tm = new TimeHandler();
			timey = new Timer(5, tm);
			timey.start();
			PlayerMover pm = new PlayerMover();
			playerTimer = new Timer(10, pm);
			playerTimer.start();
			wormTimer.start();
		}
		public void level3()  // adds values for level 2
		{
			timeAllowed = 120000;
			startTime = System.currentTimeMillis();
			coins = 600;
			if(charShow == 1)
				playerHp = 150;
			else if(charShow == 2)
				playerHp = 80;
			else if(charShow == 3)
				playerHp = 120;
			playerMax = playerHp;
			cats = 5;
			dead = 5;
			catHp = new int[cats];
			catMax = 200;
			cDirec = new int[cats];
			catX = new int[cats];
			catY = new int[cats];
			CatMover catm = new CatMover();
			catTimer = new Timer(40, catm);
			for(int i=0; i<cats; i++)
			{
				catX[i] = (i*-30)-100;
				catY[i] = 190;
				catHp[i] = 200;
				cDirec[i] = 0;
			}
			ImageIcon boulderButt = new ImageIcon("boulderButton.png");
			boulder = new JButton(boulderButt);
			boulder.setOpaque(false);
			boulder.setContentAreaFilled(false); 
			boulder.setFocusPainted(false); 
			boulder.setBorderPainted(false);
			BoulderMaker bm = new BoulderMaker();
			boulder.addActionListener(bm);
			boulder.setBounds(250, 390, 125, 82);
			ImageIcon cactiPic = new ImageIcon("cactibutton.png");
			cactus = new JButton(cactiPic);
			cactus.setOpaque(false);
			cactus.setContentAreaFilled(false); 
			cactus.setFocusPainted(false); 
			cactus.setBorderPainted(false);
			CactusMaker cm = new CactusMaker();
			cactus.addActionListener(cm);
			cactus.setBounds(400, 390, 125, 82);
			containTower[1][5] = 100;
			containTower[4][9] = 3;
			containTower[2][5] = 3;
			containTower[1][10] = 3;
			containTower[5][2] = 3;
			containTower[1][1] = 3;
			containTower[6][7] = 3;
			TimeHandler tm = new TimeHandler();
			timey = new Timer(5, tm);
			timey.start();
			PlayerMover pm = new PlayerMover();
			playerTimer = new Timer(10, pm);
			playerTimer.start();
			catTimer.start();
		}
		class BoulderMaker implements ActionListener  //add boulders 
		{
			public void actionPerformed(ActionEvent e)
			{
				if(towerBuild[0] == true)
				{
					towerBuild[0]=false;
					showIndicator = false;
					timerStart();
					requestFocus();
				}
				else if (towerBuild[0] ==false)
				{
					if (coins>=50)
					{
						towerBuild[0]=true;
						towerBuild[1] = false;
						showIndicator = true;
						timerStop();
						requestFocus();
						repaint();
					}
				}
			}
		}
		class CactusMaker implements ActionListener  // adds cacti
		{
			public void actionPerformed(ActionEvent e)
			{
				if(towerBuild[1] == true)
				{
					towerBuild[0]=false;
					showIndicator = false;
					timerStart();
					requestFocus();
				}
				else if (towerBuild[1] ==false)
				{
					if (coins>=100)
					{
						towerBuild[1]=true;
						towerBuild[0] = false;
						showIndicator = true;
						timerStop();
						requestFocus();
						repaint();
					}
				}
			}
		}
		class InfoButton implements ActionListener  //handles enemy info button 
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonClick();
				enemyInfoBox = new JPanel();
				enemyInfoBox.setBackground(Color.BLACK);
				enemyInfoBox.setBounds(100, 50, 600, 300);
				enemyInfoBox.setLayout(null);
				ImageIcon xbutt = new ImageIcon("xbutton.png");
				JButton xbox = new JButton(xbutt);
				xbox.setBounds(560, 0, 40, 40);
				xbox.setContentAreaFilled(false); 
				xbox.setFocusPainted(false); 
				xbox.setBorderPainted(false);
				xbox.setLayout(null);
				XButton xb = new XButton();
				xbox.addActionListener(xb);
				if(levelNum==1)
				{
					ImageIcon slimeSample = new ImageIcon("slime.gif");
					JLabel slimePic = new JLabel(slimeSample);
					slimePic.setOpaque(false);
					slimePic.setBounds(25, 25, 50, 50);
					JTextArea slimeInfo = new JTextArea("Slime\nAn average slime. Medium agility, low HP, low damage.\nWeaknesses: None\nStrengths: None");
					slimeInfo.setBounds(100, 20, 500, 100);
					slimeInfo.setOpaque(false);
					slimeInfo.setFont(new Font("Monospaced", Font.PLAIN, 15));
					slimeInfo.setForeground(Color.WHITE);
					enemyInfoBox.add(slimePic);
					enemyInfoBox.add(slimeInfo);
				}
				if(levelNum==2)
				{
					ImageIcon wormSample = new ImageIcon("fireworm.gif");
					JLabel wormPic = new JLabel(wormSample);
					wormPic.setOpaque(false);
					wormPic.setBounds(25, 25, 60, 50);
					JTextArea wormInfo = new JTextArea("Worm\nMedium to large sized worm.\nHas a bubble shield that can only be broken\nby contact with a cactus.\nLow agility, high HP, medium damage.\nWeaknesses: Vulnerability to cacti obstacles\nStrengths: None");
					wormInfo.setBounds(100, 20, 500, 200);
					wormInfo.setOpaque(false);
					wormInfo.setFont(new Font("Monospaced", Font.PLAIN, 15));
					wormInfo.setForeground(Color.WHITE);
					enemyInfoBox.add(wormPic);
					enemyInfoBox.add(wormInfo);
				}
				if(levelNum==3)
				{
					ImageIcon catSample = new ImageIcon("catwalkright.gif");
					JLabel catPic = new JLabel(catSample);
					catPic.setOpaque(false);
					catPic.setBounds(25, 25, 60, 50);
					JTextArea catInfo = new JTextArea("Black Cat\nBlack colored feline with sharp claws.\n High agility, medium HP, high damage.\nWeaknesses: None \nStrengths: None");
					catInfo.setBounds(100, 20, 500, 120);
					catInfo.setOpaque(false);
					catInfo.setFont(new Font("Monospaced", Font.PLAIN, 15));
					catInfo.setForeground(Color.WHITE);
					enemyInfoBox.add(catPic);
					enemyInfoBox.add(catInfo);
				}
				enemyInfoBox.add(xbox);
				playPanel.add(enemyInfoBox);
				if(slime>0)
				{
					slimeTimer.stop();
				}
				if(worms>0)
				{
					wormTimer.stop();
				}
				if(cats>0)
				{
					catTimer.stop();
				}
			}
		}
		class XButton implements ActionListener  // action handler for x button in the enemy info popup
		{
			public void actionPerformed(ActionEvent e)
			{
				playPanel.remove(enemyInfoBox);
				buttonClick();
				if(slime>0)
				{
					slimeTimer.start();
				}
				if(worms>0)
				{
					wormTimer.start();
				}
				if(cats>0)
				{
					catTimer.start();
				}
				requestFocus();
			}
		}
		
		public void mousePressed (MouseEvent e) {}  // mousePressed method that is unused
		public void mouseReleased (MouseEvent e) {}  // mouseReleased method that is unused
		public void mouseClicked (MouseEvent e)   //builds a construct when you click
		{
			for(int i=0; i<7; i++)
			{
				for(int j=0; j<13; j++)
				{
					if(rect[i][j].contains(e.getX(), e.getY()))
					{
						if(towerBuild[0]==true && containTower[i][j]==0 && melee[i][j]==true)
						{
							containTower[i][j] = 1;
							boulderSound();
							towerBuild[0]=false;
							showIndicator = false;
							coins-=50;
							coinCount.setText("Coins: "+coins);
							timerStart();
							Icon lockedTower1 = new ImageIcon("boulderButtonLock.png");
							if(coins<50)
								boulder.setIcon(lockedTower1);
						}
						else if(towerBuild[1]==true && containTower[i][j]==0 && melee[i][j]==true)
						{
							containTower[i][j] = 2;
							cactusSound();
							towerBuild[1]=false;
							showIndicator = false;
							coins-=100;
							coinCount.setText("Coins: "+coins);
							timerStart();
						}
						if(coins<50)
						{
							ImageIcon lockBoulder = new ImageIcon("boulderButtonLock.png");
							boulder.setIcon(lockBoulder);
						}
						if(coins<100 && levelNum!=1)
						{
							ImageIcon lockCacti = new ImageIcon("cactilockbutton.png");
							cactus.setIcon(lockCacti);
						}
						repaint();
					}
				}
			}
			requestFocus();
		}
		public void mouseEntered (MouseEvent e) {}  // mouseEntered method that is unused
		public void mouseExited (MouseEvent e) {}  // mouseExited method that is unused
		public void mouseDragged (MouseEvent e) {}  //mouseDragged method that is unused
		public void mouseMoved (MouseEvent e)   //when you click to build contrsuct and hover over a square to build, adds effects
		{
			for(int i=0; i<7; i++)
			{
				for(int j=0; j<13; j++)
				{
					if(rect[i][j].contains(e.getX(),e.getY())&& towerBuild[0]==true)
					{
						hoverTower[i][j] = 1;
						repaint();
					}
					else if(rect[i][j].contains(e.getX(),e.getY())&& towerBuild[1]==true)
					{
						hoverTower[i][j] = 2;
						repaint();
					}
				}
			}
		}
		public void keyTyped(KeyEvent e) //enter building mode when '1' is typed
		{
			if(e.getKeyChar()=='1')
			{
				if(levelNum==1)
				{
					if(towerBuild[0] == true)
					{
						towerBuild[0]=false;
						showIndicator = false;
						timerStart();
						requestFocus();
					}
					else if (towerBuild[0] ==false)
					{
						if (coins>=50)
						{
							towerBuild[0]=true;
							towerBuild[1]=false;
							showIndicator = true;
							timerStop();
							requestFocus();
							repaint();
						}
					}
				}
				else if(levelNum==2)
				{
					if(towerBuild[1] == true)
					{
						towerBuild[1]=false;
						showIndicator = false;
						timerStart();
						requestFocus();
					}
					else if (towerBuild[1] ==false)
					{
						if (coins>=50)
						{
							towerBuild[1]=true;
							showIndicator = true;
							timerStop();
							requestFocus();
							repaint();
						}
					}
				}
			}
				
		}
		public void keyPressed (KeyEvent e)  // changes direction of player
		{
			if(e.getKeyChar()=='w')
				playerDirec = 1;
			else if(e.getKeyChar()=='a')
				playerDirec = 2;
			else if(e.getKeyChar()=='s')
				playerDirec = 3;
			else if(e.getKeyChar()=='d')
				playerDirec = 4;
			requestFocus();
			repaint();
		}
		public void keyReleased(KeyEvent e)  // stops movement of player
		{
			if(playerDirec==4)
				playerDirec = -1;
			else
				playerDirec = 0;
			repaint();
		}
		public void boulderSound()
		{
			File file = new File("rocksfx.wav");
			try
			{	
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
				Clip clip = AudioSystem.getClip();
				clip.open(audioStream);
				clip.start();
			}
			catch (Exception e)
			{
				System.err.println(e.getMessage());
			}
		}
		class PlayerMover implements ActionListener //changes direction and moves player based on key input
		{
			public void actionPerformed(ActionEvent e)
			{
				if(playerDirec==1 && playerY-3>=40)
				{
					for(int i=0; i<7; i++)
					{
						for(int j=0; j<13; j++)
						{
							if(rect[i][j].contains(playerX+25, playerY-3) && containTower[i][j]!=3)
							{
								if(charShow == 1)
									playerY-=2;
								else if(charShow == 2)
									playerY-=4;
								else if(charShow == 3)
									playerY-=3;
							}
						}
					}
				}
				else if(playerDirec==2 && playerX-3>=60)
				{
					for(int i=0; i<7; i++)
					{
						for(int j=0; j<13; j++)
						{
							if(rect[i][j].contains(playerX-3, playerY+25) && containTower[i][j]!=3)
							{
								if(charShow == 1)
									playerX-=2;
								else if(charShow == 2)
									playerX-=4;
								else if(charShow == 3)
									playerX-=3;
							}
						}
					}
				}
				else if(playerDirec==3 && playerY+3<=340)
				{
					for(int i=0; i<7; i++)
					{
						for(int j=0; j<13; j++)
						{
							if(rect[i][j].contains(playerX+25, playerY+3+50) && containTower[i][j]!=3)
							{
								if(charShow == 1)
									playerY+=2;
								else if(charShow == 2)
									playerY+=4;
								else if(charShow == 3)
									playerY+=3;
							}
						}
					}
				}
				else if(playerDirec==4 && playerX+3<=660)
				{
					for(int i=0; i<7; i++)
					{
						for(int j=0; j<13; j++)
						{
							if(rect[i][j].contains(playerX+3+50, playerY+25) && containTower[i][j]!=3)
							{
								if(charShow == 1)
									playerX+=2;
								else if(charShow == 2)
									playerX+=4;
								else if(charShow == 3)
									playerX+=3;
							}
						}
					}
				}
				for(int i=0; i<7; i++)
				{
					for(int j=0; j<13; j++)
					{
						melee[i][j] = false;
					}
				}
				meleeDetector();
				enemyDamage();
				towerSelfDamage();
				repaint();
			}
			public void meleeDetector()  //finds the blocks in melee range of the character
			{
				for(int i=0; i<7; i++)
				{
					for(int j=0; j<13; j++)
					{
						if(rect[i][j].contains(playerX+25, playerY+25))
						{
							melee[i][j] = true;
							if(i>0)
								melee[i-1][j] = true;
							if(i<=5)
								melee[i+1][j] = true;
							if(j>0)
								melee[i][j-1] = true;
							if(j<=11)
								melee[i][j+1] = true;
						}
					}
				}
			}
		}
		public void enemyDamage()  // deal damage to player when enemy is contact
		{
			if(slime>0)
			{
				for(int i=0; i<slime; i++)
				{
					Rectangle slimeLoc = new Rectangle(slimeX[i], slimeY[i], 50, 40);
					if(slimeLoc.contains(playerX+25, playerY+25))
					{
						if(System.currentTimeMillis()-dmgCD>=300)
						{
							dmgCD = System.currentTimeMillis();
							playerHp-=10;
							hurtSound();
							bloodSplat();
							repaint();
						}
					}
				}
			}
			if(worms>0)
			{
				for(int i=0; i<worms; i++)
				{
					Rectangle wormLoc = new Rectangle(wormX[i], wormY[i], 50, 40);
					if(wormLoc.contains(playerX+25, playerY+25))
					{
						if(System.currentTimeMillis()-dmgCD>=300)
						{
							dmgCD = System.currentTimeMillis();
							playerHp-=10;
							hurtSound();
							bloodSplat();
							repaint();
						}
					}
				}
			}
			if(cats>0)
			{
				for(int i=0; i<cats; i++)
				{
					Rectangle catLoc = new Rectangle(catX[i], catY[i], 50, 40);
					if(catLoc.contains(playerX+25, playerY+25))
					{
						if(System.currentTimeMillis()-dmgCD>=300)
						{
							dmgCD = System.currentTimeMillis();
							playerHp-=20;
							bloodSplat();
							hurtSound();
							repaint();
						}
					}
				}
			}
			if(playerHp<=0)
			{
				back = 4;
				levelNum = 0;
				cardLayout.show(panel, "link6");
				timerStop();
				if(slime>0)
					slime = 0;
				if(worms>0)
					worms = 0;
				if(cats>0)
					cats = 0;
				timey.stop();
				deathScream();
				clip.stop();
				defeatMusic();
				repaint();
			}
		}
		public void towerSelfDamage()  // deal dmage to player when hit by cacti
		{
			for(int i=0; i<7; i++)
			{
				for(int j=0; j<13; j++)
				{
					if(rect[i][j].contains(playerX+25, playerY+25) && containTower[i][j]==2)
					{
						if(System.currentTimeMillis()-towerdmgCD>=300)
						{
							towerdmgCD = System.currentTimeMillis();
							playerHp-=10;
							bloodSplat();
							hurtSound();
							repaint();
						}
					}
				}
			}
			if(playerHp<=0)
			{
				back = 4;
				levelNum = 0;
				cardLayout.show(panel, "link6");
				timerStop();
				if(slime>0)
					slime = 0;
				if(worms>0)
					worms = 0;
				if(cats>0)
					cats = 0;
				timey.stop();
				clip.stop();
				deathScream();
				defeatMusic();
				repaint();
			}
		}
		class SlimeMover implements ActionListener  //adjusts slime movement and direction
		{
			public void actionPerformed(ActionEvent e) 
			{
				for(int i=0; i<slime; i++)
				{  
					if (sDirec[i] == 0) //right
						slimeX[i]+=1;
					else if (sDirec[i] == 1) //left
						slimeX[i]-=1;
					else if (sDirec[i] == 2) //down
						slimeY[i]+=1;
					else if (sDirec[i] == 3)  //up
						slimeY[i]-=1;
					repaint();
					enemyDamage();
					for(int j=0; j<7; j++)
					{
						for(int k=0; k<13; k++)
						{
							if(containTower[j][k]==100)
							{
								if(rect[j][k].contains(slimeX[i]+25, slimeY[i]+25))
								{
									slimeHp[i]=0;
									dead--;
									if(dead==0)
									{
										back = 5;
										levelNum = 0;
										clip.stop();
										victoryMusic();
										cardLayout.show(panel, "link5");
										timerStop();
										if(slime>0)
											slime = 0;
										if(worms>0)
											worms = 0;
										if(cats>0)
											cats = 0;
										timey.stop();
										repaint();
									}
								}
							}
						}
					}
				}
			}
		}
		class WormMover implements ActionListener  // worm movement and direction
		{
			public void actionPerformed(ActionEvent e) 
			{
				for(int i=0; i<worms; i++)
				{
					if (wDirec[i] == 0) //right
						wormX[i]+=2;
					else if (wDirec[i] == 1) //left
						wormX[i]-=2;
					else if (wDirec[i] == 2) //down
						wormY[i]+=2;
					else if (wDirec[i] == 3)  //up
						wormY[i]-=2;
					repaint();
					enemyDamage();
					for(int j=0; j<7; j++)
					{
						for(int k=0; k<13; k++)
						{
							if(containTower[j][k]==100)
							{
								if(rect[j][k].contains(wormX[i]+25, wormY[i]+25) && wormBubble<=0)
								{
									wormHp[i]=0;
									dead--;
									if(dead==0)
									{
										back = 5;
										levelNum = 0;
										clip.stop();
										victoryMusic();
										cardLayout.show(panel, "link5");
										timerStop();
										if(slime>0)
											slime = 0;
										if(worms>0)
											worms = 0;
										if(cats>0)
											cats = 0;
										timey.stop();
										repaint();
									}
								}
							}
						}
					}
				}
			}
		}
		class CatMover implements ActionListener  // cat movement and direction
		{
			public void actionPerformed(ActionEvent e)
			{
				for(int i=0; i<cats; i++)
				{
					if(catHp[i]>0)
					{
						if (cDirec[i] == 0) //right
							catX[i]+=5;
						else if (cDirec[i] == 1) //left
							catX[i]-=5;
						else if (cDirec[i] == 2) //down
							catY[i]+=5;
						else if (cDirec[i] == 3)  //up
						catY[i]-=5;
					}
					repaint();
					enemyDamage();
					for(int j=0; j<7; j++)
					{
						for(int k=0; k<13; k++)
						{
							if(containTower[j][k]==100)
							{
								if(rect[j][k].contains(catX[i]+25, catY[i]+25))
								{
									catHp[i]=0;
									dead--;
									if(dead==0)
									{
										back = 5;
										levelNum = 0;
										clip.stop();
										victoryMusic();
										cardLayout.show(panel, "link5");
										timerStop();
										if(slime>0)
											slime = 0;
										if(worms>0)
											worms = 0;
										if(cats>0)
											cats = 0;
										timerStop();
										timey.stop();
										clip.stop();
										victoryMusic();
										repaint();
									}
								}
							}
						}
					}
				}
			}
		}
		class TimeHandler implements ActionListener //show time left to clear level
		{
			public void actionPerformed(ActionEvent e)
			{
				long timeLeft = timeAllowed-(System.currentTimeMillis() - startTime);
				long seconds = timeLeft / 1000;
				secondsDisplay = seconds % 60;
				minutes = seconds / 60;
				showTimer.setFont(new Font("Monospaced", Font.PLAIN, 18));
				if(coinCD - timeLeft >= 500 || coinCD - timeLeft <= 0)
				{
					coinCD = timeLeft;
					coins++;
					coinCount.setText("Coins: "+coins);
				}
				if(secondsDisplay>=10)
					showTimer.setText(minutes+":"+secondsDisplay);
				else 
					showTimer.setText(minutes+":0"+secondsDisplay);
				if(levelNum == 3 && minutes == 1 && secondsDisplay == 0 && moved == false)
				{
					containTower[1][5] = 0;
					moved = true;
					containTower[(int)(Math.random()*6)][(int)(Math.random()*12)] = 100;
				}
				if(timeLeft<=0)
				{
					back = 4;
					levelNum = 0;
					cardLayout.show(panel, "link6");
					timerStop();
					if(slime>0)
						slime = 0;
					if(worms>0)
						worms = 0;
					if(cats>0)
						cats = 0;
					clip.stop();
					defeatMusic();
					timey.stop();
					repaint();
				}
			}
		}
		public void hoverDescriptions ()  //show descriptions for constructs when you hover over the button
		{
			if(levelNum == 1 || levelNum == 2 || levelNum==3)
			{
				boulder.addMouseListener(new java.awt.event.MouseAdapter() 
				{
					public void mouseEntered(java.awt.event.MouseEvent evt) 
					{
						boulderShowDesc = true;
						repaint();
					}

					public void mouseExited(java.awt.event.MouseEvent evt) 
					{
						boulderShowDesc = false;
						repaint();
					}
				});
			}
			if(levelNum == 2 || levelNum==3)
			{
				cactus.addMouseListener(new java.awt.event.MouseAdapter() 
				{
					public void mouseEntered(java.awt.event.MouseEvent evt) 
					{
						cactiShowDesc = true;
						repaint();
					}

					public void mouseExited(java.awt.event.MouseEvent evt) 
					{
						cactiShowDesc = false;
						repaint();
					}
				});
			}
		}
	}
}



