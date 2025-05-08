package main;

import java.awt.event.KeyEvent;
import entity.NPC_CatTeller;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	GamePanel gp;
    public boolean upPress, downPress, leftPress, rightPress, ePress;
    public boolean numPress[] = new boolean[10]; // 0-9 keys
    public boolean backspacePress, enterPress;
    //debug
    boolean checkDrawTime = false;
    
    public KeyHandler(GamePanel gp) {
    	this.gp = gp;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override 
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();
        //title state
        if(gp.gameState == gp.titleState) {
        	 if(code == KeyEvent.VK_W){
                 gp.ui.commandNum--;
                 if(gp.ui.commandNum < 0) {
                	 gp.ui.commandNum = 2;
                 }
             }
             if(code == KeyEvent.VK_S){
            	 gp.ui.commandNum++;
            	 if(gp.ui.commandNum > 2) {
                	 gp.ui.commandNum = 0;
                 }
             }
             if(code == KeyEvent.VK_ENTER) {
            	 if(gp.ui.commandNum == 0) {
            		 gp.gameState = gp.playState;
            		 gp.playMusic(0);
            	 }
            	 if(gp.ui.commandNum == 1) {
            		 //add
            	 }
            	 if(gp.ui.commandNum == 2) {
            		 System.exit(0);
            	 }
             }
        }
        //play state
        if(gp.gameState == gp.playState) {
        	if(code == KeyEvent.VK_W){
                 upPress = true;
            }
            if(code == KeyEvent.VK_S){
                 downPress = true;
            }
            if(code == KeyEvent.VK_A){
                 leftPress = true;
            }
            if(code == KeyEvent.VK_D){
                 rightPress = true;
            }
            if(code == KeyEvent.VK_P){
         		gp.gameState = gp.pauseState;
            }
            if(code == KeyEvent.VK_ENTER){
          		ePress = true;
            }
             
             //debug
             if(code == KeyEvent.VK_T){
                 if(checkDrawTime == false) {
                 	checkDrawTime = true;
                 }else if(checkDrawTime == true) {
                 	checkDrawTime = false; 
                 }
             }
        }
        //pause state
        else if(gp.gameState == gp.pauseState) {
        	if(code == KeyEvent.VK_P){
         		gp.gameState = gp.playState;
             }
        }
        //dialogue state
        else if(gp.gameState == gp.dialogueState) {
            if(code == KeyEvent.VK_W) {
                gp.ui.dialogueOption--;
                if(gp.ui.dialogueOption < 0) {
                    gp.ui.dialogueOption = gp.ui.maxOptions - 1;
                }
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.dialogueOption++;
                if(gp.ui.dialogueOption >= gp.ui.maxOptions) {
                    gp.ui.dialogueOption = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                // Pass selection to NPC
                if(gp.npc[gp.currentNpcIndex] instanceof NPC_CatTeller) {
                    NPC_CatTeller teller = (NPC_CatTeller)gp.npc[gp.currentNpcIndex];
                    teller.dialogueOption = gp.ui.dialogueOption;
                    teller.speak(); // Progress dialogue
                }
            }
            if(code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }// input state
        if(gp.gameState == gp.inputState) {
            // Number keys 0-9
        	if(code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9) {
                numPress[code - KeyEvent.VK_0] = true;
            }
            else if(code >= KeyEvent.VK_NUMPAD0 && code <= KeyEvent.VK_NUMPAD9) {
                numPress[code - KeyEvent.VK_NUMPAD0] = true;
            }
            // Handle other keys
            else if(code == KeyEvent.VK_BACK_SPACE) {
                backspacePress = true;
            }
            else if(code == KeyEvent.VK_ENTER) {
                enterPress = true;
            } 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPress = false;
        }
        if(code == KeyEvent.VK_S){
            downPress = false;
        }
        if(code == KeyEvent.VK_A){
            leftPress = false;
        }
        if(code == KeyEvent.VK_D){
            rightPress = false;
        }
        if(code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9) {
            numPress[code - KeyEvent.VK_0] = false;
        }
        if(code >= KeyEvent.VK_NUMPAD0 && code <= KeyEvent.VK_NUMPAD9) {
            numPress[code - KeyEvent.VK_NUMPAD0] = false;
        }
        if(code == KeyEvent.VK_BACK_SPACE) {
            backspacePress = false;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPress = false;
        }
    }
}
