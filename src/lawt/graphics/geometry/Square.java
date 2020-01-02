package lawt.graphics.geometry;

import java.awt.Color;
import java.awt.Graphics2D;

import lawt.engine.Entity;
import lawt.engine.Util;

public class Square extends Entity{
	
	private double size;
	private boolean drawOutline;
	
	public Square(double x, double y, double size, Color col) {
		super(x, y, col);
		setsize(size);
		isOutline(false);
	}

	@Override
	public void update() {}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(col);
		if(drawOutline){
			g.drawRect((int)(x-size), (int)(y-size), (int)(size*2), (int)(size*2));
		}else{
			g.fillRect((int)(x-size), (int)(y-size), (int)(size*2), (int)(size*2));
		}
	}
	
	public boolean isOnScreen() {
		  return Util.inRectangle(
				  x, y,
				  currentWindow.getCameraX()-size*Math.sqrt(2),
				  currentWindow.getCameraY()-size*Math.sqrt(2),
				  currentWindow.getCameraX() + currentWindow.getWidth()/currentWindow.getScaleFactor() + size*Math.sqrt(2),
				  currentWindow.getCameraY() + currentWindow.getHeight()/currentWindow.getScaleFactor() + size*Math.sqrt(2)
				  );
	  }

	public double getsize() { return size; }

	public void setsize(double size) { this.size = size; }
	
	public void isOutline(boolean b){ drawOutline = b; }
}
