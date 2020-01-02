package lawt.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lawt.engine.Entity;
import lawt.engine.Util;

public class ImageEntity extends Entity{

	private BufferedImage img;
	private File imgFile;
	
	public ImageEntity(double x, double y, String filename) {
		super(x, y, Color.WHITE);
		imgFile = new File(filename);
		try {
			img = ImageIO.read(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isOnScreen() {
		  return Util.inRectangle(
				  x, y,
				  currentWindow.getCameraX() - img.getWidth(),
				  currentWindow.getCameraY() - img.getHeight(),
				  currentWindow.getCameraX() + currentWindow.getWidth()/currentWindow.getScaleFactor(),
				  currentWindow.getCameraY() + currentWindow.getHeight()/currentWindow.getScaleFactor());
	  }

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, (int)x, (int)y, null);
	}
	
}
