package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_CatTeller extends Entity{
	public int dialogueOption = 0;
	public int dialogueStage = 0;
	
	public NPC_CatTeller(GamePanel gp) {
		super(gp);
		
		direction = "left";
		speed = 1;
		
		getImage();
		setDialogue();
	}
	public void getImage() {
		up1 = setup("/npc/up_1");
	    up2 = setup("/npc/up_2");
	    down1 = setup("/npc/down_1");
	    down2 = setup("/npc/down_2");
	    left1 = setup("/npc/left_1");
	    left2 = setup("/npc/left_2");
	    right1 = setup("/npc/right_1");
	    right2 = setup("/npc/right_2");
	}
	public void setDialogue() {
		dialogues[0] = "Teller: How may I help you? \n1) Make Deposit \n2) Make Withdraw \n3) Financial Overview";
        dialogues[1] = "Teller: How much will you deposit?";
        dialogues[2] = "Teller: How much will you withdraw?";
        dialogues[3] = "Teller: Transaction complete!";
        dialogues[4] = "Teller: Insufficient funds!";
	}
	public void setAction() {
		
		actionLockCounter++;
		if(actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100)+1; // 1-100
			
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			actionLockCounter = 0; 
		}
	}
	public void speak() { 
		if(dialogueStage == 0) {
            gp.ui.currentDialogue = dialogues[0];
            dialogueStage = 1;
        } else {
            switch(dialogueOption) {
            	case 0: // Deposit
            		if(dialogueStage == 1) {
            			gp.gameState = gp.inputState;
	                    gp.ui.inputText.setLength(0);
	                    gp.ui.isDeposit = true; // Flag for deposit
	                    dialogueStage = 2;
	                }
	                break;
                
	            case 1: // Withdraw
	                if(dialogueStage == 1) {
	                    gp.gameState = gp.inputState;
	                    gp.ui.inputText.setLength(0);
	                    gp.ui.isDeposit = false; // Flag for withdraw
	                    dialogueStage = 2;
	                }
	                break;
	            case 2: // Financial Overview
	                gp.ui.currentDialogue = "Balance: $" + gp.player.money;
	                break;
	        }
        
	        if(dialogueOption != 2) { // Don't reset for overview
	            dialogueStage = 0;
	        }
        }
	}
	public void handleDeposit(int amount) { 
        // Add your transaction logic here
        gp.player.money += amount; // Example - increase player's money
        
        // Show confirmation
        gp.ui.currentDialogue = dialogues[2] + "\nDeposited: $" + amount + 
                               "\nNew balance: $" + gp.player.money;
        gp.gameState = gp.dialogueState;
        dialogueStage = 0; // Reset dialogue
    }
	public void handleTransaction(int amount, boolean isDeposit) {
        if(isDeposit) {
            gp.player.money += amount;
            gp.ui.currentDialogue = dialogues[3] + "\nDeposited: $" + amount + 
                                   "\nNew balance: $" + gp.player.money;
        } 
        else { // Withdraw
            if(amount <= gp.player.money) {
                gp.player.money -= amount;
                gp.ui.currentDialogue = dialogues[3] + "\nWithdrew: $" + amount + 
                                       "\nNew balance: $" + gp.player.money;
            } else {
                gp.ui.currentDialogue = dialogues[4] + 
                                       "\nYour balance: $" + gp.player.money;
            }
        }
        gp.gameState = gp.dialogueState;
    }
}

