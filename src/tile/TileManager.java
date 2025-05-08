package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile tile[];
	public int mapTileNum[] [];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
	 	
		tile = new Tile[40];
		mapTileNum = new int[gp.maxWorldCol] [gp.maxWorldRow]; // will hold maps.txt
		
		getTileImage();
		loadMap("/maps/map_test_large50.txt");
	}
	
	public void getTileImage() {
			
		setup(0, "dirt", false);
		setup(1, "grass", false);
		setup(2, "water", true);
		setup(3, "sand", false);
		setup(4, "tree", true);
		//house
		setup(5, "floor", false);
		setup(6, "wood_left", true);
		setup(7, "wood_right", true);
		setup(8, "wood", true);
		//path
		setup(9, "path_LR", false);
		setup(10, "path_down", false);
		setup(11, "path_up", false);
		setup(12, "path_left", false);
		setup(13, "path_right", false);
		setup(14, "path_left_up_corner", false);
		setup(15, "path_left_down_corner", false);
		setup(16, "path_right_up_corner", false);
		setup(17, "path_right_down_corner", false);
		setup(18, "path_UD", false);
		//shor line 
		setup(19, "sand_water_down_corner", false);
		setup(20, "sand_water_down_cornerR", false);
		setup(21, "sand_water_down", false);
		setup(22, "sand_water_left", false);
		setup(23, "sand_water_right", false);
		setup(24, "sand_water_up_corner", false);
		setup(25, "sand_water_up_cornerR", false);
		setup(26, "sand_water_up", false);
		//Sand, dirt, grass line
		setup(27, "SDG_down", false);
		setup(28, "SDG_left_down_corner", false);
		setup(29, "SDG_left_up_corner", false);
		setup(30, "SDG_left", false);
		setup(31, "SDG_right_down_corner", false);
		setup(32, "SDG_right_up_corner", false);
		setup(33, "SDG_right", false);
		setup(34, "SDG_up", false);
			
		setup(35, "tree_rock", true); 
		setup(36, "concrete_floor", true);
		setup(37, "concrete", false);
	}
	
	public void setup(int index, String imagePath, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+ imagePath +".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine(); // read a line of text
				
				while(col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col] [row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		}catch(Exception e) {
			
		}
	}
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol] [worldRow]; // access tile type
			//what tile is being drawn
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			//where on the screen is the tile being drawn
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
				
			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
}






