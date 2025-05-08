package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.NPC_CatTeller;
import entity.Player;
import object_s.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

    //Screen Settings
    final int oringinalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = oringinalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxSreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxSreenRow; // 576 pixels
    
    //World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    //public final int worldWidth = tileSize * maxWorldCol;
    //public final int worldHeight = tileSize * maxWorldRow;
 
    int FPS = 60;
    
    //system
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionCheck check = new CollisionCheck(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public int gameLoopCount = 0;
    Thread gameThread; // Creates time in game
    
    // entity & object
    public Player player = new Player(this,keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];
    public int currentNpcIndex = 0;
    
    //game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int inputState = 4;


    public GamePanel(){ 

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // improves game rendering
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    public void setUpGame() {
    	aSetter.setObject();
    	aSetter.setNPC();
    	
    	//playMusic(0);

    	gameState = titleState;
    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }

    //Game Loop
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; // 0.01666.. seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                // Update character position
            	gameLoopCount++;
                update();
                // Draws the screen with updated position
                repaint();
                delta--;
            }
        }
    }

    public void update(){
    	
    	if(gameState == playState) {
    		//player
    		player.update();
    		//npc
    		for(int i = 0; i < npc.length; i++) {
    			if(npc[i] != null) {
    				npc[i].upadte();
    			}
    		}
    	}
        if(gameState == pauseState) {
        	// nothing
        }
        if(gameState == inputState) {
            // Handle number input
        	// Handle number input
            for(int i = 0; i < 10; i++) {
                if(keyH.numPress[i] && ui.inputText.length() < ui.maxInputLength) {
                    ui.inputText.append(i);
                    keyH.numPress[i] = false;
                }
            }
            
            // Handle backspace
            if(keyH.backspacePress && ui.inputText.length() > 0) {
                ui.inputText.deleteCharAt(ui.inputText.length()-1);
                keyH.backspacePress = false;
            }
            // Handle enter
            if(keyH.enterPress && ui.inputText.length() > 0) {
            	 try {
                     int amount = Integer.parseInt(ui.inputText.toString());
                     
                     if(amount > 0) {
                         // Process valid positive amount
                         NPC_CatTeller teller = (NPC_CatTeller)npc[currentNpcIndex];
                         teller.handleTransaction(amount, ui.isDeposit);
                         
                         // Reset input for next time
                         ui.inputText.setLength(0);
                     } else {
                         // Handle zero/negative input
                         ui.currentDialogue = "Amount must be positive!";
                         gameState = dialogueState;
                         ui.inputText.setLength(0);
                     }
                 } catch (NumberFormatException e) {
                         // Handle non-numeric input
                         ui.currentDialogue = "Please enter numbers only!";
                         gameState = dialogueState;
                         ui.inputText.setLength(0);
                     }
                keyH.enterPress = false;
            }
        }
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //debug
        long drawStart = 0;
        if(keyH.checkDrawTime == true) {
        	drawStart = System.nanoTime();
        }
        // title screen
        if(gameState == titleState) {
        	ui.draw(g2);
        }
        //others
        else {
        	//tile
            tileM.draw(g2);
            //object
            for(int i = 0; i < obj.length; i++) {
            	if(obj[i] != null) {
            		obj[i].draw(g2, this);
            	}
            }
            //npc
            for(int i = 0; i < npc.length; i++) {
            	if(npc[i] != null) {
            		npc[i].draw(g2);
            	}
            }
            //player
            player.draw(g2);
            //UI
            ui.draw(g2);
        }
        //debug
        if(keyH.checkDrawTime == true) {
        	long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " +passed, 10, 400);
            System.out.println("Draw Time: "+passed);
        }
        
        
        g2.dispose();
    }
    public void playMusic(int i) {
    	music.setFile(i);
    	music.play();
    	music.loop();
    }
    
    public void stopMusic() {
    	music.stop();
    }
    public void playSE(int i) {
    	se.setFile(i);
    	se.play();
    }
}







