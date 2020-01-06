package lawt.graphics.geometry;

import java.awt.Color;
import java.awt.Graphics2D;

import lawt.engine.Entity;
import lawt.engine.Util;

public class Circle extends Entity{
	
	private double radius;
	private boolean drawOutline;
	
	public Circle(double x, double y, double radius, Color col) {
		super(x, y, col);
		setRadius(radius);
		setDrawOutline(false);
	}

	@Override
	public void update() {}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(col);
		if(drawOutline){
			g.drawOval((int)(x-radius), (int)(y-radius), (int)(radius*2), (int)(radius*2));
		}else{
			g.fillOval((int)(x-radius), (int)(y-radius), (int)(radius*2), (int)(radius*2));
		}
	}
	
	public boolean isOnScreen() {
		  return Util.inRectangle(
				  x, y,
				  currentWindow.getCameraX()-radius,
				  currentWindow.getCameraY()-radius,
				  currentWindow.getCameraX() + currentWindow.getWidth()/currentWindow.getScaleFactor() + radius,
				  currentWindow.getCameraY() + currentWindow.getHeight()/currentWindow.getScaleFactor() + radius
				  );
	  }

	public double getRadius() { return radius; }

	public void setRadius(double radius) { this.radius = radius; }
	
	public void setDrawOutline(boolean b){ drawOutline = b; }
}
