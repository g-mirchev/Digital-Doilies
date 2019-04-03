
public class Line {
	private String lineColor;
	private int lineWidth;
	private int linePX, linePY, lineNX, lineNY;
	private boolean reflect;
	
	
	public Line(int pX, int pY, int nX, int nY, int lW, String lC, boolean rf){
		linePX = pX;
		linePY = pY;
		lineNX = nX;
		lineNY = nY;
		lineWidth = lW;
		lineColor = lC;
		reflect = rf;
		
	}
		
	public int getPX(){
		return linePX;
	}
	
	public int getPY(){
		return linePY;
	}
	
	public int getNX(){
		return lineNX;
	}
	
	public int getNY(){
		return lineNY;
	}
	
	public String getLineColor(){
		return lineColor;
	}
	
	public int getLineWidth(){
		return lineWidth;
	}
	
	public boolean getLineReflect(){
		return reflect;
	}
}
