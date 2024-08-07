package ui;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.URMButtons.*;
import static utilz.Constants.UI.VolumeButtons.*;

import gamestates.Gamestate;
public class AudioOptions {
     
    private VolumeButton volumeButton;
    
	private SoundButton musicButton, sfxButton;
    private Game game; 

	public AudioOptions(Game game){
		this.game= game;
        createSoundButtons();
        createVolumeButton();
    }

    private void createSoundButtons() {
		int soundX = (int) (450 * Game.SCALE);
		int musicY = (int) (140 * Game.SCALE);
		int sfxY = (int) (186 * Game.SCALE);
		musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
	}
    	private void createVolumeButton() {
		int vX = (int) (309 * Game.SCALE);
		int vY = (int) (278 * Game.SCALE);
		volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
	}
    public void update(){
        musicButton.update();
		sfxButton.update();

        volumeButton.update();
    }
    public void draw (Graphics g){
        
		// Sound buttons
        musicButton.draw(g);
		sfxButton.draw(g);
        
        // Volume Button
		volumeButton.draw(g);
    }
    public void mouseDragged(MouseEvent e) {
		if (volumeButton.isMousePressed()) {
			float valueBefore =volumeButton.getFloatValue();
			volumeButton.changeX(e.getX());
			float valueAfter = volumeButton.getFloatValue();
			if(valueBefore !=valueAfter)
			game.getAudioPlayer().setVolume(valueAfter);
		}
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, musicButton))
			musicButton.setMousePressed(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMousePressed(true);
	
		else if (isIn(e, volumeButton))
			volumeButton.setMousePressed(true);
	}

}
