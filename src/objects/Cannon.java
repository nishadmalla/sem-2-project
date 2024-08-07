package objects;

import Audio.AudioPlayer;
import gamestates.Playing;
import main.Game;

public class Cannon extends GameObject {

	private int tileY;

	public Cannon(int x, int y, int objType,Playing playing) {
		super(x, y, objType);
		
		tileY = y / Game.TILES_SIZE;
		initHitbox(40, 26);
		hitbox.x -= (int) (4 * Game.SCALE);
		hitbox.y += (int) (6 * Game.SCALE);
	}

	public void update() {
		if (doAnimation)
			updateAnimationTick();
	}

	public int getTileY() {
		return tileY;
	}

}
