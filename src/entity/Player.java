package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	public int hasMushroom = 0;
	public int hasChestKey = 0;
	public int chestLevel = 2;
	
	public Player(GamePanel gp, KeyHandler keyH) {
	
	this.gp = gp;
	this.keyH = keyH;
	
	screenX = gp.screenWidth / 2 - (gp.tileSize/2);
	screenY = gp.screenHeight / 2 - (gp.tileSize/2);
	
	//PLAYER COLLIDER
	solidArea = new Rectangle(); //or add the attributes in the brackets: new Rectangle(8, 16, 32, 32)
	solidArea.x = 8;
	solidArea.y = 16;
	solidAreaDefaultX = solidArea.x;
	solidAreaDefaultY = solidArea.y;
	solidArea.width = 32;
	solidArea.height = 32;
	
	
	setDefaultValues();
	getPlayerImage();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}
	
	//ANIMATION FRAMES
	public void getPlayerImage() { //method to associate each image with direction movement
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right2.png"));
			
			//System.out.println(up1);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//update 60 frames per second
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
		
			if(keyH.upPressed == true) {
				direction = "up";
				
			}
			else if(keyH.downPressed == true) {
				direction = "down";
				
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
				
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
				
			}
		
			//CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);

			//CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			//IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == false) {
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			 // Sprite player animation
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
	}
	
		public void pickUpObject(int i) {
			if(i != 999) {
				String objectName = gp.obj[i].name;
				
				switch(objectName) {
				case "Key":
					gp.playSE(1);
					hasKey++;
					gp.obj[i] = null;
					gp.ui.showMessage("You got a door key!");
					break;
				case "Key Chest":
					gp.playSE(1);
					hasChestKey++;
					gp.obj[i] = null;
					gp.ui.showMessage("You got a chest key!");
					break;
				case "Door":
						if(hasKey > 0) {
						gp.playSE(3);
						gp.obj[i] = null;
						hasKey--;
						gp.ui.showMessage("You opened the door!");
						} 
						else {
							gp.ui.showMessage("You need a key!");
						}
					break;
				case "Chest":
					if(hasChestKey > 0) {
						gp.playSE(6);
						gp.obj[i] = null;
						hasChestKey--;
						gp.ui.showMessage("You opened the chest!");
						chestLevel--;
						if(chestLevel == 0) {
							gp.ui.gameFinished = true;
							gp.stopMusic();
						} 
					}
					else {
						gp.ui.showMessage("You need a key!");
					}
					break;
					
				case "Speedy boots":
					gp.playSE(4);
					if(hasMushroom > 0) {
						speed += 4;
					} 
					else {
						speed +=2;
					}
					gp.obj[i] = null;
					gp.ui.showMessage("You got speedy boots!");
					break;
					
				case "Slow Mushroom":
					gp.playSE(2);
					hasMushroom++;
					speed -= 2;
					gp.obj[i] = null;
					gp.ui.showMessage("You got a sloppy mushroom!");
					break;
				}
			}
		}
	
		//ANIMATION SWITCHES ACCORDING TO DIRECTION
		public void draw(Graphics2D g2) {
			//g2.setColor(Color.white);
			//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
			
			BufferedImage image = null;
			
			switch(direction) {
			case "up":
				if(spriteNum == 1) {
					image = up1;
				}
				if(spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if(spriteNum == 1) {
					image = down1;
				}
				if(spriteNum ==2) {
					image = down2;
				}
				break;
			case "left":
				if(spriteNum ==1) {
					image = left1;
				}
				if(spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if(spriteNum == 1) {
					image = right1;
				}
				if(spriteNum == 2) {
					image = right2;
				}
				break;
			}
			
			g2.drawImage(image,  screenX,  screenY,  gp.tileSize, gp.tileSize, null);
		}
}