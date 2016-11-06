package majoolwip.core.fx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	public int width, height;
	public float anchorX, anchorY;
	public ShadowType shadowType = ShadowType.NONE;
	public int[] pixels;
	
	public Image(String path)
	{
		BufferedImage image = null;
		
		try
		{
			image = ImageIO.read(new File("Client/res/" + path));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		width = image.getWidth();
		height = image.getHeight();
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		anchorX = 0.5f;
		anchorY = 0.5f;
		image.flush();
	}
	
	public Image(int w, int h, int[] p)
	{
		this.width = w;
		this.height = h;
		this.pixels = p;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}


	public float getAnchorX() {
		return anchorX;
	}

	public void setAnchorX(float anchorX) {
		this.anchorX = anchorX;
	}

	public float getAnchorY() {
		return anchorY;
	}

	public void setAnchorY(float anchorY) {
		this.anchorY = anchorY;
	}

	public void setAnchorPoint(float x, float y){
		anchorX = x;
		anchorY = y;
	}
}
