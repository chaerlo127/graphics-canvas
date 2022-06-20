package shapes;

import java.awt.Polygon;

public class TPolygon extends TShape{
	// attribute 속성: 객체가 가진 특정한 값 -> 어떤 관점
	private static final long serialVersionUID = 1L;

	
	// constructor
	public TPolygon() {
		this.shape = new Polygon();
	}
	
	@Override
	public TShape clone() {
		return new TPolygon();
	}
	
	//setters and getters :속성 변경 값
	@Override
	public void prepareDrawing(int x, int y) {
		this.addPoint(x, y);
		this.addPoint(x, y);
	}

	//method
	public void addPoint(int x, int y) {
		Polygon polygon = (Polygon) this.shape; // 다운 캐스팅
		polygon.addPoint(x, y);
	}
	
	@Override
	public void keepDrawing(int x, int y) {
		Polygon polygon = (Polygon) this.shape; // 다운 캐스팅
		polygon.xpoints[polygon.npoints-1] = x;
		polygon.ypoints[polygon.npoints-1] = y;		
	}



}