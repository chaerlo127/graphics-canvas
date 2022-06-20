package shapes;

import java.awt.geom.Ellipse2D;

public class TOval extends TShape {
	// attribute 속성: 객체가 가진 특정한 값 -> 어떤 관점
	private static final long serialVersionUID = 1L;
	private int x,y;

	//constructor
	public TOval() {
		this.shape = new Ellipse2D.Double();
	}

	@Override
	public TShape clone() {
		return new TOval();
	}

	//setters and getters :속성 변경 값
	@Override
	public void prepareDrawing(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(x, y, 0, 0);
		this.x = x;
		this.y = y;
	}

	//setters and getters :속성 변경 값
	public void keepDrawing(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
//		ellipse.setFrame(ellipse.getX(). ellipse.getY(), x-ellipse.getX(), y-ellipse.getY());
		ellipse.setFrame(Math.min(this.x, x), Math.min(this.y, y), Math.abs(this.x-x), Math.abs(this.y-y));
	}

}