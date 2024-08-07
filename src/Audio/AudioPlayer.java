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
    private Clip getClip(String name) {
		System.out.println("Getting clip for " + name);
		URL url = getClass().getResource("/audioa/" + name + ".wav");
		if (url == null) {
			System.err.println("Audio file not found: " + name + ".wav");
			return null;
		}
	
		AudioInputStream audio;
		try {
			audio = AudioSystem.getAudioInputStream(url);
			if (audio == null) {
				System.err.println("Error loading audio file: " + name + ".wav");
				return null;
			}
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;
		} catch (UnsupportedAudioFileException e) {
			System.err.println("Error loading audio file: " + name + ".wav");
			System.err.println(e.getMessage());
			return null;
		} catch (IOException e) {
			System.err.println("Error loading audio file: " + name + ".wav");
			System.err.println(e.getMessage());
			return null;
		} catch (LineUnavailableException e) {
			System.err.println("Error loading audio file: " + name + ".wav");
			System.err.println(e.getMessage());
			return null;
		}
	}
    public void setVolume(float volume) {
		this.volume = volume;
		updateSongVolume();
		updateEffectsVolume();
	}

	private void updateEffectsVolume() {
        for (Clip c : effects) {
			if (c != null) {
				FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
				float range = gainControl.getMaximum() - gainControl.getMinimum();
				float gain = (range * volume) + gainControl.getMinimum();
				gainControl.setValue(gain);
			}
        }
    }

    private void updateSongVolume() {
        FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);
    }

    public void stopSong() {
		if (songs[currentSongId].isActive())
			songs[currentSongId].stop();
	}

	public void setLevelSong(int lvlIndex) {
		if (lvlIndex % 2 == 0)
			playSong(LEVEL_1);
		else
			playSong(LEVEL_2);
	}
    public void playSong(int song) {
		stopSong();

		currentSongId = song;
		updateSongVolume();
		songs[currentSongId].setMicrosecondPosition(0);
		songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);

      }
}
