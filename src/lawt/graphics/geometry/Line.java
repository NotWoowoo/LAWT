package lawt.graphics.geometry;

import java.awt.Color;
import java.awt.Graphics2D;

import lawt.engine.Entity;
import lawt.engine.Util;

public class Line extends Entity{
	
	private double x2;
	private double y2;
	
	public Line(double x1, double y1, double x2, double y2, Color col) {
		super(x1, y1, col);
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public void update() {
		this.x2 += this.xVel;
	    this.y2 += this.yVel;
	    
	    if (edgeBehavior != EdgeBehavior.NONE && !Util.inRectangle(this.x2, this.y2, 0.0D, 0.0D, this.currentWindow.getWidth(), this.currentWindow.getHeight())) {
	        switch (this.edgeBehavior) {
	          case CLAMP:
	            this.x2 = Util.clamp(this.x2, 0.0D, this.currentWindow.getWidth());
	            this.y2 = Util.clamp(this.y2, 0.0D, this.currentWindow.getHeight());
	            break;
	          
	          case WRAP:
	            this.x2 = Util.wrap(this.x2, 0.0D, this.currentWindow.getWidth());
	            this.y2 = Util.wrap(this.y2, 0.0D, this.currentWindow.getHeight());
	            break;
	            
	          default:
	          	break;
	        } 
	      }
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(col);
		g.drawLine((int)x, (int)y, (int)x2, (int)y2);
	}

}
