package transformers;

import shapes.TShape;

public class Rotator extends Transformers{
	private double cx, cy;
	public Rotator(TShape shape) {
		super(shape);
	}

	@Override
	public void prepare(int x, int y) {
		this.cx = this.shape.shape.getBounds().getCenterX();
		this.cy = this.shape.shape.getBounds().getCenterY();
		this.px = x; 
		this.py = y;
	}

	@Override
	public void keepTransforming(int x, int y) {
		 double startAngle = Math.atan2(this.py-this.cy, this.px-this.cx);
		 double endAngle = Math.atan2(y-this.cy, x-this.cx);
	    
	     double theta = endAngle - startAngle;
	        
	        
		this.affineTransform.translate(cx, cy);
		this.affineTransform.rotate(theta, endAngle, startAngle);
		this.affineTransform.translate(-cx, -cy);
		this.px = x;
		this.py = y; 
	}

	@Override
	public void finalize(int x, int y) {
		this.shape.shape = this.affineTransform.createTransformedShape(this.shape.shape);
		this.affineTransform.setToIdentity(); // √ ±‚»≠	
	}
}
