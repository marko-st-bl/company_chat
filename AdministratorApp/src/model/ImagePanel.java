package model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	
	private static final long serialVersionUID = 5795776406260004331L;
	
	private  BufferedImage img;
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1280, 720);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(img != null)
			g.drawImage(img, 0, 0, this);
	}


	public BufferedImage getImg() {
		return img;
	}


	public void setImg(BufferedImage img) {
		this.img = img;
	}
	
	

}