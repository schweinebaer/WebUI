package imageMap;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Rectangle;

public class Form {
	private Rectangle rec;
	private String href;
	private int type;
	private Color c;
	private Polygon p;
	private double ratioWidth;
	private double ratioHeight;
	private double ratioX;
	private double ratioY;
	
	public Form(int x, int y, int width, int height){
		rec = new Rectangle(x, y, width, height);	
		href = "";
}

	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}

	
	public String getHref() {
		return href;
	}
	
	
	public void setC(Color color) {
		this.c = color;	
	}
	public Color getC() {
		return c;
	}

	
	public void setR(Rectangle r) {
		this.rec = r;
		
	}
	public Rectangle getR() {
		return rec;
	}

	
	public void setP(Polygon p) {
		this.p = p;	
	}
	public Polygon getP() {
		return p;
	}

	
	public void setRatioWidth(double ratioWidth) {
		this.ratioWidth = ratioWidth;
	}
	public double getRatioHeight() {
		return ratioHeight;
	}
	

	public void setRatioHeight(double ratioHeight) {
		this.ratioHeight = ratioHeight;
	}
	public double getRatioWidth() {
		return ratioWidth;
	}

	
	public double getRatioX() {
		return ratioX;
	}
	public void setRatioX(double ratioX) {
		this.ratioX = ratioX;	
	}

	public double getRatioY() {
		return ratioY;
	}
	public void setRatioY(double ratioY) {
		this.ratioY = ratioY;
		
	}






	
	
}
