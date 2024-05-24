package levels;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Point;
import static utilz.Constants.EnemyConstants.CRABBY;

import java.awt.Color;
import entities.Crabby;

import main.Game;
import utilz.LoadSave;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetPlayerSpawn;
import static utilz.HelpMethods.GetCrabs;
import static utilz.HelpMethods.GetPlayerSpawn;
public class Level {
	
	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Crabby> crabs;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	private int[][] lvlData;

	public Level(BufferedImage img) {
		this.img = img;
		CreateLevelData();
		createEnemies();
		createLvlOffset();
		calPlayerSpawan();
	}

	private void calPlayerSpawan() {
	playerSpawn=GetPlayerSpawn(img);
	}
	public Level(int[][] lvlData) {
		this.lvlData = lvlData;
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}

}
