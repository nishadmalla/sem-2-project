package Audio;

import java.util.Random;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioPlayer {
    public static int MENU_1 = 0;
	public static int LEVEL_1 = 1;
	public static int LEVEL_2 = 2;

	public static int DIE = 0;
	public static int JUMP = 1;
	public static int GAMEOVER = 2;
	public static int LVL_COMPLETED = 3;
	public static int ATTACK_ONE = 4;
	public static int ATTACK_TWO = 5;
	public static int ATTACK_THREE = 6;
	public static int PEARL = 7;
	public static int CRABBY= 8;

	private Clip[] songs, effects;
	private int currentSongId;
	private float volume = 1f;
	private boolean songMute, effectMute;
	private Random rand = new Random();

	public AudioPlayer() {
		loadSongs();
		loadEffects();
		playSong(MENU_1);
	}

	private void loadSongs() {
		String[] names = { "menu", "level1", "level2" };
		songs = new Clip[names.length];
		for (int i = 0; i < songs.length; i++)
			songs[i] = getClip(names[i]);
	}

	private void loadEffects() {
		String[] effectNames = { "die", "jump", "gameover", "lvlcompleted", "attack1", "attack2", "attack3","pearl","crabby"};
		effects = new Clip[effectNames.length];
		for (int i = 0; i < effects.length; i++) {
			Clip c = getClip(effectNames[i]);
			if (c != null) {
				effects[i] = c;
			} else {
				System.err.println("Error: Clip object is null for effect " + effectNames[i]);
			}
		}
	}

}
