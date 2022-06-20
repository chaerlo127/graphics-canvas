package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable {
	// attribute 속성: 객체가 가진 특정한 값 -> 어떤 관점
	private static final long serialVersionUID = 1L;
	private static final int THICKER = 20;
	private static final int THINNER = 1;
	private static float[] DASH = new float[]{5,5,5,5};;
	private int round;
	private  float[] dash;
	
	//graphics attribute;
	private boolean bSelected;
	private boolean isColored;
	private boolean bfillColored;
	private boolean bthickered;
	private Color color;
	private Color fillColor;
	
	// component
	public Shape shape;
	private AffineTransform affineTransform;
	private TAnchors anchors;
	private Image image;
	
	//working	

	// constructor
	public TShape() {
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity(); //항등원: 초기화 곱해도 원래의 값이 나오도록
		
		this.anchors = new TAnchors();
		
		this.bSelected = false;
		this.bfillColored = false;
		this.bthickered = false;
		this.round = 0;
		this.dash = null;
		this.image = null;
		this.color = Color.white;
	}
	
	public abstract TShape clone();
//	public void initialize() {}
	
	//setters and getters :속성 변경 값
	
	
	public boolean isColored() {return this.isColored;}
	public void setColored(boolean isColored) {this.isColored = isColored;}
	
	public boolean isFilled() {return this.bfillColored;}
	public void setFilled(boolean bfillColored) {this.bfillColored = bfillColored;}
	
	public boolean isThicked() {return this.bthickered;}
	public void setThicked(boolean bThickered) {this.bthickered = bThickered;}
	
	
	public boolean isSelected() {return this.bSelected;}
	public void setSelected(boolean bSelected) {this.bSelected = bSelected;}
	
	public Color setColor() { return this.color;}
	public void getColor(Color color) {this.color = color;}
	public Color setFillColor() { return this.fillColor;}
	public void getFillColor(Color fillColor) {this.fillColor = fillColor;}
	public Rectangle getShapeBound() {return this.shape.getBounds();}
	public void setImage(Image img) {this.image = img;}
	public Image getImage() {return this.image;}
	
	public EAnchors getSelectedAnchor() {return this.anchors.getSelectedAnchor();}
	
	public AffineTransform getAffineTransform() {return affineTransform;}
	public TAnchors getAnchors() {return anchors;}
	public Shape getShape() {return this.shape;}
	public void getRound() {
		if(this.round == 1)	this.round = 0;
		else this.round = 1;
	}

	public void getMiter() {
		if(this.dash != null) this.dash = null; 
		else this.dash = DASH; 
	}
	public float[] getDash() {return this.dash;}
	public void setDash(float[] dash) {	this.dash = dash;}


	
	// method: 메소드, 객체의 특징
	public boolean contains(int x, int y) { // 상태
		if (isSelected()) {
			if (this.anchors.contains(x, y)) {return true;}
		}
		Shape trnasformedShape = this.shape.getBounds();
		if (trnasformedShape.contains(x, y)) {
			this.anchors.setSelectedAnchor(EAnchors.eMove);
			return true;
		}
		return false;
	}

	public void draw(Graphics2D graphics2D) {
		// 임시 도형
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		
		if(isColored()) {
			graphics2D.setColor(this.fillColor);
			if(isFilled()) {
				graphics2D.fill(transformedShape);
			}
			graphics2D.setColor(this.color);
		}else {
			graphics2D.setColor(Color.black);
		}
		
		if (this.isThicked()) {
			graphics2D.setStroke(new BasicStroke(THICKER, 0, this.round, 1.0f, dash, 0));
		}else {
			graphics2D.setStroke(new BasicStroke(THINNER, 0, this.round, 1.0f, dash, 0));
		}
		graphics2D.draw(transformedShape);
		if(isSelected()) {
			this.anchors.draw(graphics2D, transformedShape.getBounds());
		}
	}

	
	public abstract void prepareDrawing(int x, int y);
	public abstract void keepDrawing(int x, int y);
	public void addPoint(int x, int y) {}
}