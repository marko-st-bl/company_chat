package service;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

public class PrintScreenUtil {
	
	private static  final Logger LOGGER=LoggerFactory.getLogger(PrintScreenUtil.class.getName());
	
	public static BufferedImage printScreen() {
		Rectangle rectangle = new Rectangle(
			      Toolkit.getDefaultToolkit().getScreenSize());
			    Robot robot;
			    BufferedImage img=null;
				try {
					robot = new Robot();
					img = robot.createScreenCapture(rectangle);
				} catch (AWTException e) {
					LOGGER.log(Level.SEVERE, e.getMessage(),e);
				}
			    return img;
	}
	
	public static BufferedImage compressImg(BufferedImage img) {
		BufferedImage compressed=Scalr.resize(img,Method.BALANCED, 1280,720);
		return compressed;
	}

}
