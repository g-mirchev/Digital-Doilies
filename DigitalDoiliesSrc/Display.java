
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Display extends JPanel {
	
	private Frame frame;
	private static final int MY_WIDTH = 700, MY_HEIGHT = 700;
	
	public Display(Frame f){
		this.frame = f;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(MY_WIDTH, MY_HEIGHT);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight()); 
		g2d.setColor(Color.WHITE);
		
		/*
		 * Draw a number of lines equal to the specified by the user. The
		 * default is 12. This method uses the number of sectors as the top
		 * bound of the enhanced for loop, and calculates the angle to rotate
		 * by dividing 360 by the number of sectors needed. The method draws a
		 * starting line and rotates it to create the required number of sectors.
		 */
		if(frame.getVisibleLines() == true){
			for(int i = 0; i < frame.getSectors(); i++){	
				g2d.rotate(frame.getRotationAngle(), this.getWidth()/2, this.getHeight()/2);
				g2d.drawLine(this.getWidth()/2, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
			}
		}
		
		for(int i = 0; i < frame.getSequence().size(); i++){
			for(int k = 0; k < frame.getSequence().get(i).size(); k++){	
				
				switch (frame.getSequence().get(i).get(k).getLineColor().toString()) {
				
		        case "Red":
		        	g2d.setColor(Color.RED);
		           break;
		        case "Blue":
		        	g2d.setColor(Color.BLUE);
		           break;
		        case "Green":
		        	g2d.setColor(Color.GREEN);
		           break;
		        case "Yellow":
		        	g2d.setColor(Color.YELLOW);
		           break;
		        case "Cyan":
		        	g2d.setColor(Color.CYAN);
		            break;
		        case "Pink":
		        	g2d.setColor(Color.PINK);
		           break;	
		        case "Magenta":
		        	g2d.setColor(Color.MAGENTA);
		           break;
		        case "Black":
		        	g2d.setColor(Color.BLACK);
		           break;
		        case "White":
		        	g2d.setColor(Color.WHITE);
		           break;
				}
				
				g2d.setStroke(new BasicStroke(frame.getSequence().get(i).get(k).getLineWidth()));

				if(frame.getSequence().get(i).get(k).getLineReflect() == true){
					for(int j = 0; j < frame.getSectors(); j++){
						g2d.rotate(frame.getRotationAngle(), getWidth()/2, getHeight()/2);
						g2d.drawLine(frame.getSequence().get(i).get(k).getPX(), frame.getSequence().get(i).get(k).getPY(), frame.getSequence().get(i).get(k).getNX(), frame.getSequence().get(i).get(k).getNY());
						g2d.drawLine(frame.getSequence().get(i).get(k).getPX(), getHeight()-frame.getSequence().get(i).get(k).getPY(), frame.getSequence().get(i).get(k).getNX(), getHeight()-frame.getSequence().get(i).get(k).getNY());
					}
				}
				else{
					for(int j = 0; j < frame.getSectors(); j++){
						g2d.rotate(frame.getRotationAngle(), getWidth()/2, getHeight()/2);
						g2d.drawLine(frame.getSequence().get(i).get(k).getPX(), frame.getSequence().get(i).get(k).getPY(), frame.getSequence().get(i).get(k).getNX(), frame.getSequence().get(i).get(k).getNY());
					}
				}
			}
		}
	}
}
