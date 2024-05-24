package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;

public class LevelManager {

	private Game game;
	private BufferedImage[] levelSprite;
	private Level levelOne;
	private ArrayList<Level> levels;
	private int lvlIndex=0;

	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levels=new ArrayList<>();
		BuildAllLevels();
	}
	public void loadNextLevel(){
		lvlIndex++;
		if(lvlIndex>=levels.size()){
			lvlIndex=0;
			System.out.println("nO MORE LEVELS! Game Completed!");
			Gamestate.state=Gamestate.MENU;
		}
		Level newLevel=levels.get(lvlIndex);
		game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
		game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
	}

	public void draw(Graphics g, int lvlOffset) {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
			for (int i = 0; i < levelOne.getLevelData()[0].length; i++) {
				int index = levelOne.getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Game.TILES_SIZE * i - lvlOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}

	public void update() {

	}

	public Level getCurrentLevel() {
		return levelOne;
	}

}
