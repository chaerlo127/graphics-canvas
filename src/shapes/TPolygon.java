package shapes;

import java.awt.Polygon;

public class TPolygon extends TShape{
	// attribute �Ӽ�: ��ü�� ���� Ư���� �� -> � ����
	private static final long serialVersionUID = 1L;

	
	// constructor
	public TPolygon() {
		this.shape = new Polygon();
	}
	
	@Override
	public TShape clone() {
		return new TPolygon();
	}
	
	//setters and getters :�Ӽ� ���� ��
	@Override
	public void prepareDrawing(int x, int y) {
		this.addPoint(x, y);
		this.addPoint(x, y);
	}

	//method
	public void addPoint(int x, int y) {
		Polygon polygon = (Polygon) this.shape; // �ٿ� ĳ����
		polygon.addPoint(x, y);
	}
	
	@Override
	public void keepDrawing(int x, int y) {
		Polygon polygon = (Polygon) this.shape; // �ٿ� ĳ����
		polygon.xpoints[polygon.npoints-1] = x;
		polygon.ypoints[polygon.npoints-1] = y;		
	}



}