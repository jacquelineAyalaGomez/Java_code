package main;

import entity.NPC_CatTeller;
import object_s.OBJ_chest;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	public void setObject() {
		gp.obj[0] = new OBJ_chest(gp);
		gp.obj[0].worldX = 26 * gp.tileSize;
		gp.obj[0].worldY = 20 * gp.tileSize;
		
	}
	public void setNPC() {
		gp.npc[0] = new NPC_CatTeller(gp);
		gp.npc[0].worldX = gp.tileSize*40;
		gp.npc[0].worldY = gp.tileSize*40;
	}
}
