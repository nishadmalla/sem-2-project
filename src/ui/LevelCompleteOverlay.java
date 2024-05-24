package ui;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;
public class LevelCompleteOverlay {
    private Playing playing;
    private UrmButton menu,next;
    private BufferedImage img;
    private int bgX,bgY,bgW,bgH;

    public LevelCompleteOverlay(Playing playing){
        this.playing=playing;
        initImg();
        initButtons();
        
    }
}
