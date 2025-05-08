package entity;

import main.KeyHandler;
import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity{

  KeyHandler keyH;
  public final int screenX;
  public final int screenY;
  public int money;
  

  public Player(GamePanel gp, main.KeyHandler keyH){
	  //calling constructor of super class
	  super(gp); 
	  
      this.keyH = keyH;
      money = 0;
      //returns halfway point of screen
      screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
      screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
      
      //collision area for character 
      solidArea = new Rectangle(8,16,32,32);
      
      //collision area for object
      solidAreaDefaultX = solidArea.x;
      solidAreaDefaultY = solidArea.y;
      
      setDefaultValues();
      getPlayerImage();
  }

  public void setDefaultValues(){
      worldX = gp.tileSize * 23;
      worldY = gp.tileSize * 21;
      speed = 4;
      direction = "down";
  }

  public void getPlayerImage(){
      up1 = setup("/player/cat_up1");
      up2 = setup("/player/cat_up2");
      down1 = setup("/player/cat_down1");
      down2 = setup("/player/cat_down2");
      left1 = setup("/player/cat_left1");
      left2 = setup("/player/cat_left2");
      right1 = setup("/player/cat_right1");
      right2 = setup("/player/cat_right2");
  }

  public void update(){
	  //checks what direction player is going
	  if(keyH.upPress == true || keyH.downPress == true 
			  || keyH.leftPress == true || keyH.rightPress == true) {	  
      if(keyH.upPress == true){
          direction = "up";
      }else if(keyH.downPress == true){
          direction = "down";
      }else if(keyH.leftPress == true){
          direction = "left";
      }else if(keyH.rightPress == true){
          direction = "right";
      } 
      
      //check tile collision
      collisionOn = false;
      gp.check.checkTile(this);
      
      //check obj collision
      int objIndex = gp.check.checkObject(this, true);
      pickUpObject(objIndex);
      
      //check npc collision
      int npcIndex = gp.check.checkEntity(this, gp.npc);
      interactNPC(npcIndex);
      
      //if collision is false player can move
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
      
      spriteCounter++;
      // Changes character number to look "animated"
      if(spriteCounter > 12){ // how fast/slow you want PNG to change.
    	  if(spriteNum == 1){
    		  spriteNum = 2;
    	  }else if(spriteNum == 2){
    		  spriteNum = 1;
    	  }
    	  spriteCounter = 0;
      }
	  }
  }
  public void pickUpObject(int i) {
	  if(i != 999) {
		  
	  }
  }
  public void interactNPC(int i) {
	  if(i != 999) {
		  if(gp.keyH.ePress == true) {
	            gp.gameState = gp.dialogueState;
	            gp.currentNpcIndex = i; // Store which NPC we're talking to
	            gp.npc[i].speak();
	            gp.ui.dialogueOption = 0; // Reset selection
	        }
	  }
	  gp.keyH.ePress = false;
  }
  public void draw(Graphics2D g2){

      BufferedImage image = null;
      // Changes character image to look "animated"
      switch(direction){
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
        	  if(spriteNum == 2) {
                  image = down2;
        	  }
              break;
          case "left":
        	  if(spriteNum == 1) {
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
      g2.drawImage(image, screenX, screenY, null);

  }
}