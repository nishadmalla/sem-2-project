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
    private void initImg() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initImg'");
    }
    private void initButtons() {
        int menuX=(int)(330*Game.SCALE);
        int nextX=(int)(445* Game.SCALE);
        int y=(int)(195* Game.SCALE);
        next= new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu= new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }
}
