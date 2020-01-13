package edu.toronto.csc301;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A class containing simple (and somewhat over-simplified)
 * helper functions to convert a RenderedImage to/from byte[].
 * 
 * Feel free to use these functions (but don't feel obligated).
 */
public class Util {


	
	public static byte[] imageToByteArray(RenderedImage img){
		try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
			ImageIO.write(img, "png", out);
			out.flush();
			return out.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	
	public static RenderedImage byteArrayToImage(byte[] bytes){
		try(ByteArrayInputStream in = new ByteArrayInputStream(bytes)){
			return ImageIO.read(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
