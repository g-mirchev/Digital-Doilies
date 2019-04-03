import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Frame extends JFrame {
	
	private boolean drag;
	private int numSectors = 12;
	private String color = "Red";
	private boolean isReflect = true;
	private int penSize;
	private boolean visibleLines = true;
	private ArrayList<Line> line;
	private ArrayList<ArrayList<Line>> sequence;
	private ArrayList<ArrayList<ArrayList<Line>>> drawing;
	private ArrayList<GallerySlot> slots;
	private int currentSlot = 0;
	private final int capacity = 12;
	private int selectedSlot = -1;
	
	public void go(){
		/*
		 * Set the title to Doily Drawer create the container
		 * setting the main Layout to Grid 1 row 2 columns
		 * 1st column is for the display 2nd is for the gallery
		 * set size and default close operation
		 */
		this.setTitle("Doily Drawer");
		Container p = this.getContentPane();
		//p.setLayout(new GridLayout(1,2));
		p.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		/*
		 * creating the panel to hold the display
		 * and the panel for the buttons that would
		 * interact with the display and their layouts
		 */
		JPanel display = new JPanel();
		display.setSize(700,700);
		display.setBackground(Color.GRAY);
		display.setLayout(new FlowLayout());
		JPanel controls = new JPanel();
		JPanel displayControlls = new JPanel();
		displayControlls.setLayout(new BorderLayout());
		
		/*
		 * creating the gallery panel
		 * and it's button panel to hold the
		 * delete button, also setting their layouts
		 */
		JPanel gallery = new JPanel();
		gallery.setLayout(new GridLayout(3,4));
		gallery.setBackground(Color.GRAY);
		JPanel pdelete = new JPanel();
		JPanel galleryDelete = new JPanel();
		galleryDelete.setLayout(new BorderLayout());
		
		/*
		 * these are sub panels that all go to the
		 * button panel they are used to help
		 * layout the buttons in a more aesthetic way
		 */
		JPanel colorSize = new JPanel();
		colorSize.setLayout(new GridLayout(2,2));
		JPanel sectorsNum = new JPanel();
		sectorsNum.setLayout(new BorderLayout());
		JPanel subSectorsNum = new JPanel();
		JPanel sectorLinesReflect = new JPanel();
		sectorLinesReflect.setLayout(new GridLayout(2,1));
		
		/*
		 * the clear button
		 */
		JButton clear = new JButton("Clrear");
		
		/*
		 * I used combo box for the pen colours
		 * and size and put appropriate labels
		 */
		JLabel lColor = new JLabel("Color:");
		String[] colorNames = new String[]{"Red", "Blue", "Green", "Yellow", "Cyan", "Pink", "Magenta", "Black", "White"};
		JComboBox<String> colors = new JComboBox<>(colorNames);
		
		JLabel lSize = new JLabel("Size:");
		Integer[] sizeValues = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
		JComboBox<Integer> size = new JComboBox<>(sizeValues);

		/* 
		 * this is used to regenerate the display with the
		 * given number of sectors
		 */
		JLabel lSectors = new JLabel("Sectors:");
		JTextField sectors = new JTextField(3);
		JButton generate = new JButton("Generate");
		
		/*
		 * check boxes for the visibility of lines
		 * or if the reflect option will be on or off
		 */
		JCheckBox hideLines = new JCheckBox("Hide lines");
		JCheckBox stopReflect = new JCheckBox("Stop reflect");
		
		/*
		 * undo button to delete the last thing drawn
		 */
		JButton undo = new JButton("Undo");
		
		/*
		 * a button to save the current doily to gallery
		 */
		JButton save = new JButton("Save to Gallery");
		
		/*
		 * the delete button for the gallery
		 */
		JButton delete = new JButton("Delete");

		/*
		 * adding all the components to the respective sub panels
		 */
		
	    Display myDisplay = new Display(this);
	     
		line = new ArrayList<Line>();
		sequence = new ArrayList<ArrayList<Line>>();
		drawing = new ArrayList<ArrayList<ArrayList<Line>>>();
		
		class myPencil implements MouseMotionListener, MouseListener {
			Graphics2D drawGraph;
			int nX, nY, pX, pY;
			
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(drag == true)
					return;
		
				drag = true;		
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				nX = e.getX();
				nY = e.getY();
				if(drag == false)
					return;
					
				if (nX < 0)                     
			        nX = 0;                    
			    if (nX > myDisplay.getWidth())   
			    	nX = myDisplay.getWidth();
			         
			    if (nY < 0)                          
			    	nY = 0;                         
			    if (nY > myDisplay.getHeight())       
			    	nY = myDisplay.getHeight();
				
				drawGraph = (Graphics2D) myDisplay.getGraphics();
				
				drawGraph.setStroke(new BasicStroke(penSize));
				
				switch (color) {
				
		        case "Red":
		        	drawGraph.setColor(Color.RED);
		           break;
		        case "Blue":
		        	drawGraph.setColor(Color.BLUE);
		           break;
		        case "Green":
		        	drawGraph.setColor(Color.GREEN);
		           break;
		        case "Yellow":
		        	drawGraph.setColor(Color.YELLOW);
		           break;
		        case "Cyan":
		        	drawGraph.setColor(Color.CYAN);
		            break;
		        case "Pink":
		        	drawGraph.setColor(Color.PINK);
		           break;	
		        case "Magenta":
		        	drawGraph.setColor(Color.MAGENTA);
		           break;
		        case "Black":
		        	drawGraph.setColor(Color.BLACK);
		           break;
		        case "White":
		        	drawGraph.setColor(Color.WHITE);
		           break;
				}
				if(isReflect == true){
					for(int i = 0; i < getSectors(); i++){
						e.translatePoint(e.getComponent().getLocation().x - nX, e.getComponent().getLocation().y - nY);
						drawGraph.rotate(getRotationAngle(), myDisplay.getWidth()/2, myDisplay.getHeight()/2);
						drawGraph.drawLine(pX, pY, nX, nY);
						drawGraph.drawLine(pX, myDisplay.getHeight()-pY, nX, myDisplay.getHeight()-nY);
					}
				}
				else{
					for(int i = 0; i < getSectors(); i++){
						e.translatePoint(e.getComponent().getLocation().x - nX, e.getComponent().getLocation().y - nY);
						drawGraph.rotate(getRotationAngle(), myDisplay.getWidth()/2, myDisplay.getHeight()/2);
						drawGraph.drawLine(pX, pY, nX, nY);
					}
				}
				line.add(new Line(pX, pY, nX, nY, getPenSize(), getColor(), getReflect()));
				pX = nX;
				pY = nY;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(drag == false)
					return;
				drag = false;
				sequence.add((ArrayList<Line>) line.clone());
				line.clear();
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				pX = e.getX();
				pY = e.getY();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		}
	
		myDisplay.addMouseMotionListener(new myPencil());
		myDisplay.addMouseListener(new myPencil());
		
		class Slot extends JPanel {
			
			boolean selected;
			@Override
			protected void paintComponent(Graphics g){
				
				if(selected == true){
					setUndecorated(true);
					getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.YELLOW));
				}
			}
			
			public void setSelected(boolean b){
				selected = b;
			}
			
			public boolean isSelected(){
				return selected;
			}
		}
		
		slots = new ArrayList<GallerySlot>();
		ArrayList<Slot> bSlots = new ArrayList<Slot>();
		
		
		for(int i = 0; i <capacity; i++){
			slots.add(new GallerySlot(this));
			bSlots.add(new Slot());
			bSlots.get(i).setLayout(new FlowLayout());
			bSlots.get(i).add(slots.get(i));
			gallery.add(bSlots.get(i));
		}
		
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sequence.clear();
				myDisplay.repaint();
			}
		});
		
		colors.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setColor(colors.getSelectedItem().toString());
			}	
		});
		
		size.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setPenSize(Integer.parseInt(size.getSelectedItem().toString()));
			}	
		});
		
		hideLines.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					setVisibleLines(false);
				}
				else{
					setVisibleLines(true);
				}
				myDisplay.repaint();
			}
		});
		
		stopReflect.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					setReflect(false);
				}
				else{
					setReflect(true);
				}
			}
		});
		
		undo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sequence.remove(sequence.get(sequence.size() -1 ));
				myDisplay.repaint();
			}
		});
		
		generate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(sectors.getText().equals("")){
					return;
				}
				
				int numSec = Integer.parseInt(sectors.getText());
				
				if(numSec < 2 || numSec > 360){
					return;
				}
				sectors.setText("");
				setSectors(numSec);
				myDisplay.repaint();
				
			}	
		});
		
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				for(currentSlot = 0; currentSlot < capacity; currentSlot++){
					if(slots.get(currentSlot).isFilled() == false){
						BufferedImage img = new BufferedImage(myDisplay.getWidth(),myDisplay.getHeight(),BufferedImage.TYPE_INT_RGB);
						Graphics2D g = img.createGraphics();
						myDisplay.print(g);
						try {
							ImageIO.write(img,"png",new File("image.png"));
							
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						slots.get(currentSlot).repaint();
						slots.get(currentSlot).setFilled(true);
						break;
					}
				}
			}
		});
		
		delete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				for(currentSlot = 0; currentSlot < capacity; currentSlot++){
					if(slots.get(currentSlot).isSelected() == true){
						Graphics2D g = (Graphics2D) slots.get(currentSlot).getGraphics();
						g.fillRect(0, 0, slots.get(currentSlot).getWidth(), slots.get(currentSlot).getHeight());
						slots.get(currentSlot).setSelected(false);
					}
				}
			}
		});
		
		class SlotSelect implements MouseListener {

			GallerySlot slot;
			
			public SlotSelect(GallerySlot s){
				slot = s;
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				for(currentSlot = 0; currentSlot < capacity; currentSlot++){
					slots.get(currentSlot).setSelected(false);
				}
				slot.setSelected(true);
				slot.setFilled(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		for(currentSlot = 0; currentSlot < capacity; currentSlot++){
			slots.get(currentSlot).addMouseListener(new SlotSelect(slots.get(currentSlot)));
		}
		
		display.add(myDisplay);
		
		colorSize.add(lColor);
		colorSize.add(colors);
		colorSize.add(lSize);
		colorSize.add(size);
		
		subSectorsNum.add(lSectors);
		subSectorsNum.add(sectors);
		sectorsNum.add(subSectorsNum);
		sectorsNum.add(generate, BorderLayout.SOUTH);
		
		sectorLinesReflect.add(hideLines);
		sectorLinesReflect.add(stopReflect);
		
		/* 
		 * adding the components and sub panels to the main controls panel
		 */
		controls.add(clear);
		controls.add(colorSize);
		controls.add(sectorsNum);
		controls.add(sectorLinesReflect);
		controls.add(undo);
		controls.add(save);
		
		/*
		 * adding the delete button to it's panel
		 */
		pdelete.add(delete);
			
		/*
		 * adding the controls panel and display to one panel which 
		 * will be the 1st column of the main grid
		 */
		displayControlls.add(display);
		displayControlls.add(controls, BorderLayout.SOUTH);
		
		/*
		 * adding the gallery and delete button to the other panel
		 * which will be the second column of the main grid
		 */
		galleryDelete.add(gallery);
		galleryDelete.add(pdelete, BorderLayout.SOUTH);
		
		/*
		 * adding the two columns to the grid
		 */
		p.add(displayControlls, BorderLayout.WEST);
		p.add(galleryDelete);
		
		/*
		 * make the window visible and pack
		 */
		
		this.setResizable(false);
		this.setVisible(true);
		this.pack();
	}	
	
	/*
	 * setter and getter for the number of lines
	 */
	private void setSectors(int s){
		numSectors = s;
	}

	public int getSectors(){
		return numSectors;
	}
	
	/*
	 * setter and getter for the drag boolean
	 */
	public void setDrag(boolean d){
		drag = d;
	}
	
	public boolean getDrag(){
		return drag;
	}
	
	/*
	 * setter and getter for the reflect boolean
	 */
	private void setReflect(boolean r){
		isReflect = r;
	}
	
	public boolean getReflect(){
		return isReflect;
	}
	
	/*
	 * setter and getter for the color
	 */
	private void setColor(String c){
		color = c;
	}
	
	public String getColor(){
		return color;
	}
	
	/*
	 * setter and getter for the pen size
	 */
	private void setPenSize(int s){
		penSize = s;
	}
	
	public int getPenSize(){
		return penSize;
	}
	
	/*
	 * setter and getter for the line visibility boolean
	 */
	private void setVisibleLines(boolean v){
		visibleLines = v;
	}

	public boolean getVisibleLines(){
		return visibleLines;
	}
	
	public double getRotationAngle(){
		return Math.toRadians(360)/getSectors();
	}
	
	public ArrayList<ArrayList<Line>> getSequence(){
		return sequence;
	}
}
