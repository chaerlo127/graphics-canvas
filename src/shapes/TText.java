package shapes;

import java.awt.Rectangle;
import java.awt.TextArea;

public class TText extends TShape{

	private static final long serialVersionUID = 1L;
	private TextArea textArea;
	private int x, y;
	
	public TText() {
		this.shape =  new Rectangle();
		this.textArea = new TextArea(5, 10);
		
	}
	@Override
	public TShape clone() {
		return new TText();
	}
	public TextArea getText() {return this.textArea;}

	@Override
	public void prepareDrawing(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setBounds(x, y, 0, 0);
		this.textArea.setLocation(x, y);
		this.x = x;
		this.y = y;
	}

	@Override
	public void keepDrawing(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setBounds(Math.min(this.x, x), Math.min(this.y, y), Math.abs(this.x-x), Math.abs(this.y-y));
		this.textArea.setBounds(rectangle);
	}

}
