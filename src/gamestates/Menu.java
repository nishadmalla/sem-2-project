package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Main.Game;
import ui.MenuButton;
import utiliz.LoadSave;


public class Menu extends State implements Statemethods{

    private MenuButton[]buttons=new MenuButton[3];
    private BufferedImage backgroundIMG,BackgroundImg_menu;
    private int menuX,menuY,menuWidht,menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        BackgroundImg_menu=LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUD_IMG);
    }

    private void loadBackground() {
       backgroundIMG= LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
       menuWidht=(int)(backgroundIMG.getWidth()*Game.SCALE);
       menuHeight=(int)(backgroundIMG.getHeight()*Game.SCALE);
        menuX=Game.GAME_WIDTH/2 - menuWidht/2;
        menuY=(int)(45*Game.SCALE);
    }

    private void loadButtons() {
        buttons[0]=new MenuButton( Game.GAME_WIDTH/2 , (int)(150*Game.SCALE), 0, Gamestate.PLAYING);
        buttons[1]=new MenuButton( Game.GAME_WIDTH/2 , (int)(220*Game.SCALE), 1, Gamestate.OPTIONS);
        buttons[2]=new MenuButton( Game.GAME_WIDTH/2 , (int)(290*Game.SCALE), 2, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for(MenuButton mb:buttons)
            mb.update();
        
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(BackgroundImg_menu, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(backgroundIMG, menuX, menuY, menuWidht,menuHeight, null);
        for(MenuButton mb:buttons)
         mb.draw(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        

    }
    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb:buttons){
            if(isIn(e, mb)){
                mb.setMousePressed(true);

            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGamestate();
				break;
            }
        }
        resetButtons();
    }

  
	private void resetButtons() {
		for (MenuButton mb : buttons)
			mb.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton mb:buttons){
        mb.setMouseOver(false);
        }
        for(MenuButton mb:buttons){
            if(isIn(e, mb)){
                mb.setMouseOver(true);
                break;
            }
    }
    }
    @Override
    public void keyPressed(KeyEvent e) {
    if(e.getKeyCode()==KeyEvent.VK_ENTER)
        Gamestate.state= Gamestate.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
      
    }

    
    
}
