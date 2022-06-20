package shapes;

import java.awt.Rectangle;

public class TSelection extends TShape {
		// attribute �Ӽ�: ��ü�� ���� Ư���� �� -> � ����	
		private static final long serialVersionUID = 1L;
		//Rectangle���� ���� �Լ��� ���� ����. shape superclass���� ����
		// component
		int x, y;
		
		public TSelection() {
			this.shape = new Rectangle();
		}
		@Override
		public TShape clone() {
			return new TSelection();
		}
		
		//setters and getters :�Ӽ� ���� ��
		@Override
		public void prepareDrawing(int x, int y) {
			Rectangle rectangle = (Rectangle) this.shape;
			rectangle.setBounds(x, y, 0, 0);
			this.x = x;
			this.y = y;
		}
		
		// method: �޼ҵ�, ��ü�� Ư¡
		@Override
		public void keepDrawing(int x, int y) {
			Rectangle rectangle = (Rectangle) this.shape;
			rectangle.setBounds(Math.min(this.x, x), Math.min(this.y, y), Math.abs(this.x-x), Math.abs(this.y-y));
		}
		
	
	}
