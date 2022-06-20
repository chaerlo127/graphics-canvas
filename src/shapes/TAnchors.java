package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class TAnchors implements Serializable{
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 10;
	private final int HEIGHT = 10;
	
	public enum EAnchors{eNW, eWW, eSW, eSS, eSE, eEE, eNE, eNN, eRR, eMove}
	
	private Ellipse2D anchors[];
	private EAnchors eSelectedAnchor;
	//getters and setters
	public EAnchors getSelectedAnchor() {return this.eSelectedAnchor;}
	public void setSelectedAnchor(EAnchors eSelectedAnchor) {this.eSelectedAnchor = eSelectedAnchor;}
	private EAnchors eResizeAnchor;
	
	public EAnchors getResizeAnchor() {return this.eResizeAnchor;}
	public void setResizeAnchor(EAnchors eResizeAnchor) {this.eResizeAnchor = eResizeAnchor;}
	// consturctor
	public TAnchors() {
		this.anchors = new Ellipse2D[EAnchors.values().length-1];
		for(int i = 0; i < EAnchors.values().length-1 ; i++) {
			this.anchors[i] = new Ellipse2D.Double();
		}
	}
	
	// method
	public boolean contains(int x, int y) {
		for(int i = 0; i < EAnchors.values().length-1 ; i++) {
			if(this.anchors[i].contains(x, y)) { // 9개 앵커한테 물어본 것임
				this.eSelectedAnchor = EAnchors.values()[i];
				return true;
			}
		}
		return false;
	}
	
	public void draw(Graphics2D graphics2D, Rectangle boundingRectangle) {
		for(int i = 0; i < EAnchors.values().length-1 ; i++) {
			EAnchors anchor = EAnchors.values()[i];
			int x = boundingRectangle.x;
			int y = boundingRectangle.y;
			int w = boundingRectangle.width;
			int h = boundingRectangle.height;
			switch (anchor) {
				case eNW:									break;
				case eWW:					y = y+ h/2;		break;
				case eSW:					y = y + h;		break;
				case eSS:	x = x + w/2;	y = y + h;		break;
				case eSE:	x = x + w;		y = y + h;		break;
				case eEE:	x = x + w;		y = y + h/2;	break;
				case eNE:	x = x + w;						break;
				case eNN:	x = x + w/2;					break;
				case eRR:	x = x + w/2;	y = y - 40;	break;
				default:
			}
			x = x - this.WIDTH/2;
			y = y - this.HEIGHT/2;
			this.anchors[anchor.ordinal()].setFrame(x, y, this.WIDTH, this.HEIGHT);
			graphics2D.setColor(Color.LIGHT_GRAY);
			graphics2D.fill(this.anchors[anchor.ordinal()]);
			graphics2D.setColor(Color.black); // 변경되는 색과 상관 없이 앵커 색상은 항상 black
			graphics2D.setStroke(new BasicStroke(1));
			graphics2D.draw(this.anchors[anchor.ordinal()]);
		}
	}
	
	public Point2D getResizeAnchorPoint() {
		eResizeAnchor = null;
		switch (this.eSelectedAnchor) {
			case eNW: 	eResizeAnchor = EAnchors.eSE;		break;
			case eWW:	eResizeAnchor = EAnchors.eEE;		break;
			case eSW:	eResizeAnchor = EAnchors.eNE;		break;
			case eSS:	eResizeAnchor = EAnchors.eNN;		break;
			case eSE:	eResizeAnchor = EAnchors.eNW;		break;
			case eEE:	eResizeAnchor = EAnchors.eWW;		break;
			case eNE:	eResizeAnchor = EAnchors.eSW;		break;
			case eNN:	eResizeAnchor = EAnchors.eSS;		break;
			default: break;
		}
		double cx = this.anchors[eResizeAnchor.ordinal()].getCenterX();
		double cy = this.anchors[eResizeAnchor.ordinal()].getCenterY();
		return new Point2D.Double(cx, cy);
	}
}
