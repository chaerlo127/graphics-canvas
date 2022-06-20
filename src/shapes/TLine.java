package shapes;

import java.awt.geom.Line2D;

public class TLine extends TShape {
	// attribute �Ӽ�: ��ü�� ���� Ư���� �� -> � ����
	private static final long serialVersionUID = 1L;

	
	public TLine() {
		this.shape = new Line2D.Double();
	}

	@Override
	public TShape clone() {
		return new TLine();
	}
	
	//setters and getters :�Ӽ� ���� ��
	@Override
	public void  prepareDrawing(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(x, y, x, y);
	}

	// method: �޼ҵ�, ��ü�� Ư¡
	@Override
	public void keepDrawing(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(line.getX1(), line.getY1(), x, y);
	}

	



}
