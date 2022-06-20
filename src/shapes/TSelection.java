package shapes;

import java.awt.Rectangle;

public class TSelection extends TShape {
		// attribute 속성: 객체가 가진 특정한 값 -> 어떤 관점	
		private static final long serialVersionUID = 1L;
		//Rectangle에는 많은 함수를 갖고 있음. shape superclass생성 가능
		// component
		int x, y;
		
		public TSelection() {
			this.shape = new Rectangle();
		}
		@Override
		public TShape clone() {
			return new TSelection();
		}
		
		//setters and getters :속성 변경 값
		@Override
		public void prepareDrawing(int x, int y) {
			Rectangle rectangle = (Rectangle) this.shape;
			rectangle.setBounds(x, y, 0, 0);
			this.x = x;
			this.y = y;
		}
		
		// method: 메소드, 객체의 특징
		@Override
		public void keepDrawing(int x, int y) {
			Rectangle rectangle = (Rectangle) this.shape;
			rectangle.setBounds(Math.min(this.x, x), Math.min(this.y, y), Math.abs(this.x-x), Math.abs(this.y-y));
		}
		
	
	}
