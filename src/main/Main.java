package main;

import javax.swing.JFrame;

public class Main {  
public static void main(String[] arg){
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        window.setResizable(false);
        window.setTitle("Credit-Cat");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); 

        window.pack();

        window.setLocationRelativeTo(null); // Displays center of screen
        window.setVisible(true);

        gamePanel.setUpGame();
        gamePanel.startGameThread();

    }
}
