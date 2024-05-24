package utilz;

import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.io.File;

import javax.imageio.ImageIO;

import entities.Crabby;
import main.Game;

import static utilz.Constants.EnemyConstants.CRABBY;


public class LoadSave {

	public static final String PLAYER_ATLAS = "/res/player_sprites.png";
	public static final String LEVEL_ATLAS = "/res/outside_sprites.png";
	public static final String MENU_BUTTONS = "/res/button_atlas.png";
	public static final String MENU_BACKGROUND = "/res/menu_background.png";
	public static final String PAUSE_BACKGROUND = "/res/pause_menu.png";
	public static final String SOUND_BUTTONS = "/res/sound_button.png";
	public static final String URM_BUTTONS = "/res/urm_buttons.png";
	public static final String VOLUME_BUTTONS = "/res/volume_buttons.png";
	public static final String MENU_BACKGROUND_IMG = "/res/background_menu.png";
	public static final String PLAYING_BG_IMG = "/res/playing_bg_img.png";
	public static final String BIG_CLOUDS = "/res/big_clouds.png";
	public static final String SMALL_CLOUDS = "/res/small_clouds.png";
	public static final String CRABBY_SPRITE = "/res/crabby_sprite.png";
	public static final String STATUS_BAR = "/res/health_power_bar.png";
	public static final String COMPLETED_IMG = "/res/completed_sprite.png";

	public static BufferedImage GetSpriteAtlas(String fileName){
		BufferedImage img=null;
		InputStream is = LoadSave.class.getResourceAsStream(fileName);
		try {
			if (is != null) {
				return ImageIO.read(is);
			} else {
				throw new IOException("Resource not found: " + fileName);
			}
		} catch (IOException e) {
			e.printStackTrace(); // Consider logging the exception instead
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	public static BufferedImage[]GetAllLevels(){
		URL url= LoadSave.class.getResource("/lvls");
		File file = null;

		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		File[] files= file.listFiles();
		File[] filesSorted= new File[files.length];

		for(int i=0;i<filesSorted.length;i++)
			for(int j=0;j<files.length;j++)
				if(files[j].getName().equals(""+(i+1)+".png"))
				filesSorted[i]=files[j];
		// for(File f: files)
		// 	System.out.println("file:"+f.getName());
		// 	for(File f: filesSorted)
		// 	System.out.println("file sorted:"+f.getName());

		BufferedImage[] imgs= new BufferedImage[filesSorted.length];

		for(int i=0;i<imgs.length;i++)	
		try {
			imgs[i]=ImageIO.read(filesSorted[i]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imgs;
	}
	
	}