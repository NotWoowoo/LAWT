package lawt.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Transform {
	
	public static BufferedImage scale(BufferedImage sbi, int dWidth, int dHeight, double fWidth, double fHeight) {
	    BufferedImage dbi = null;
	    if(sbi != null) {
	        dbi = new BufferedImage(dWidth, dHeight, sbi.getType());
	        Graphics2D g = dbi.createGraphics();
	        AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
	        g.drawRenderedImage(sbi, at);
	    }
	    return dbi;
	}
	
	public static BufferedImage scaleTo(BufferedImage sbi, int dWidth, int dHeight) {
	    BufferedImage dbi = null;
	    if(sbi != null) {
	        dbi = new BufferedImage(dWidth, dHeight, sbi.getType());
	        Graphics2D g = dbi.createGraphics();
	        AffineTransform at = AffineTransform.getScaleInstance(dWidth/sbi.getWidth(), dHeight/sbi.getHeight());
	        g.drawRenderedImage(sbi, at);
	    }
	    return dbi;
	}
	
	public static BufferedImage scaleBy(BufferedImage sbi, double fWidth, double fHeight) {
	    BufferedImage dbi = null;
	    if(sbi != null) {
	        dbi = new BufferedImage((int)(sbi.getWidth()*fWidth), (int)(sbi.getHeight()*fHeight), sbi.getType());
	        Graphics2D g = dbi.createGraphics();
	        AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
	        g.drawRenderedImage(sbi, at);
	    }
	    return dbi;
	}
	
}
