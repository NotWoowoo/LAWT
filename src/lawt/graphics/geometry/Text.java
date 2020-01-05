package lawt.graphics.geometry;

import java.awt.Color;
import java.awt.Graphics2D;

import lawt.engine.Entity;

public class Text extends Entity{
	
	private String txt;
	
	public Text(double x, double y, Color col, String text) {
		super(x, y, col);
		txt = text;
	}
	
	public Text(double x, double y, Color col) {
		super(x, y, col);
		txt = "";
	}

	@Override
	public void update() {}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(col);
		g.drawString(txt, (float)x, (float)y);
	}
	
	public void setString(String text) {
		txt = text;
	}
	
	public String getString() {
		return txt;
	}
}
