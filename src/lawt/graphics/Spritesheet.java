package lawt.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	private BufferedImage sheet;
	private File sheetFile;
	private BufferedImage[][] sprites;
	
	public Spritesheet(String filename, int spriteSize){
		sheetFile = new File(filename);
		try {
			sheet = ImageIO.read(sheetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true){
			if(sheet != null){
				BufferedImage[][] newArr = new BufferedImage[sheet.getWidth()/spriteSize][sheet.getHeight()/spriteSize];
				for(int y = 0; y < sheet.getHeight()/spriteSize; y++){
					for(int x = 0; x < sheet.getWidth()/spriteSize; x++){
						newArr[x][y] = sheet.getSubimage(x*spriteSize, y*spriteSize,spriteSize, spriteSize);
					}
				}
				sprites = newArr;
				
				break;
			}
		}
	}
	
	public BufferedImage getSprite(int collumn, int row){
		--row;
		--collumn;
		return sprites[collumn][row];
	}
}
