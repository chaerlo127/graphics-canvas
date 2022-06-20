package shapes;

import java.awt.geom.Line2D;

public class TLine extends TShape {
	// attribute 속성: 객체가 가진 특정한 값 -> 어떤 관점
	private static final long serialVersionUID = 1L;

	
	public TLine() {
		this.shape = new Line2D.Double();
	}

	@Override
	public TShape clone() {
		return new TLine();
	}
	
	//setters and getters :속성 변경 값
	@Override
	public void  prepareDrawing(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(x, y, x, y);
	}

	// method: 메소드, 객체의 특징
	@Override
	public void keepDrawing(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(line.getX1(), line.getY1(), x, y);
	}

	



}
