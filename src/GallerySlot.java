import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GallerySlot extends JPanel {
	
	private BufferedImage img;
	int index;
	boolean filled;	
	boolean selected;
	
	private Frame frame;
	private static final int MY_WIDTH = 250, MY_HEIGHT = 250;
	
	public GallerySlot(Frame f){
		this.frame = f;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(MY_WIDTH, MY_HEIGHT);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		try {
			img = ImageIO.read(new File("image.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(img != null){
			ImageIcon icon = new ImageIcon(img);
			Image scaleImage = icon.getImage().getScaledInstance(MY_WIDTH, MY_HEIGHT, Image.SCALE_SMOOTH);
			g.drawImage(scaleImage, 0, 0, null);
		}
	}
	
	public int getIndex(){
		return index;
	}
	
	public boolean isFilled(){
		return filled;
	}
	
	public void setFilled(boolean b){
		filled = b;
	}
	
	public void setImage(BufferedImage i){
		img = i;
	}
	
	public void setSelected(boolean b){
		selected = b;
	}
	
	public boolean isSelected(){
		return selected;
	}
}
