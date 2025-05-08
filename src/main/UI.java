package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font arial_40, arial_80B; 
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int dialogueOption = 0;//dialogue 
    public int maxOptions = 3;
    public StringBuilder inputText = new StringBuilder();//input
    public int maxInputLength = 9;
    public boolean isDeposit = true;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
	}
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		//title state
		if(gp.gameState == gp.titleState) {
			drawTitleScreen(); 
		}
		//play state
		if(gp.gameState == gp.playState) {
			//play state stuff
		}
		//pause state
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		//dialogue state
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
		//input state
		if(gp.gameState == gp.inputState) {
            drawInputScreen();
        }
	}
	public void drawTitleScreen() {
		//background
		g2.setColor(new Color(137, 207, 240));//blue
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		//title name
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
		String text = "Credit-Cat";
		int x = getXforCenterText(text); 
		int y = gp.tileSize*3;
		// Shadow
		g2.setColor(Color.black);
		g2.drawString(text, x+5, y+5);
		//main color
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		//Cat
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize*2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		//menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		
		text = "NEW GAME";
		x = getXforCenterText(text);
		y += gp.tileSize*3.5;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "LOAD GAME";
		x = getXforCenterText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "QUIT";
		x = getXforCenterText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize, y);
		}
	}
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F)); 
		String text = "||";
		int x = getXforCenterText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	public void drawDialogueScreen() {
		//window
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		String[] lines = currentDialogue.split("\n"); 
        for(int i = 0; i < lines.length; i++) {
            if(i > 0 && lines[i].startsWith(String.valueOf(i)+")")) {
                // This is an option line
                if(dialogueOption == i-1) {
                    g2.drawString(">", x-30, y);
                }
            }
            g2.drawString(lines[i], x, y); 
            y += 40;
        } 
	}
	public void drawInputScreen() {
		// Draw input window
        int x = gp.tileSize * 3;
        int y = gp.tileSize * 5;
        int width = gp.tileSize * 10;
        int height = gp.tileSize * 3;
        drawSubWindow(x, y, width, height);
        
        // Draw text
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        String prompt = isDeposit ? "Deposit Amount: $" : "Withdraw Amount: $";
        String text = prompt + inputText.toString();
        int textX = x + gp.tileSize;
        int textY = y + gp.tileSize; 
        g2.drawString(text, textX, textY);
        
        // Blinking cursor
        if((gp.gameLoopCount / 15) % 2 == 0) { // Blink every 15 frames
            int cursorX = textX + g2.getFontMetrics().stringWidth(text);
            g2.drawString("_", cursorX, textY);
        }
	}
	public void drawSubWindow(int x, int y, int width, int height) {
		//black window
		Color c = new Color(0,0,0,210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		//white border
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
	public int getXforCenterText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}





