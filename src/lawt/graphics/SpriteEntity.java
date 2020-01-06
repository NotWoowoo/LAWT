package lawt.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lawt.engine.Entity;
import lawt.engine.Util;

public class SpriteEntity extends Entity{
	
	private BufferedImage img;

	public SpriteEntity(double x, double y, BufferedImage sprite) {
		super(x, y, Color.WHITE);
		img = sprite;
	}
	
	public SpriteEntity(double x, double y, String filepath) {
		super(x, y, Color.WHITE);
		File imgFile = new File(filepath);
		try {
			img = ImageIO.read(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void scaleTo(int dWidth, int dHeight) {
		img = Transform.scaleTo(img, dWidth, dHeight);
	}
	
	public void scaleBy(double fWidth, double fHeight) {
		img = Transform.scaleBy(img, fWidth, fHeight);
	}
	
	@Override
	public boolean isOnScreen() {
		if(img != null) {
		  return Util.inRectangle(
				  x, y,
				  currentWindow.getCameraX() - img.getWidth(),
				  currentWindow.getCameraY() - img.getHeight(),
				  currentWindow.getCameraX() + currentWindow.getWidth()/currentWindow.getScaleFactor(),
				  currentWindow.getCameraY() + currentWindow.getHeight()/currentWindow.getScaleFactor());
		}else {
			return false;
		}
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, (int)x, (int)y, null);
	}

}
