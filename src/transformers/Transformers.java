package transformers;

import java.awt.geom.AffineTransform;

import shapes.TAnchors;
import shapes.TShape;

public abstract class Transformers {

	protected TShape shape; // �ڽĵ��� ����� �� �ֵ��� �ؾ��Ѵ�.(protected)
	
	protected AffineTransform affineTransform;
	protected TAnchors anchors;
	
	protected int px, py;

	
	public Transformers(TShape shape) {
		this.shape = shape;
		this.affineTransform = this.shape.getAffineTransform();
		this.anchors = this.shape.getAnchors();
	}
	
	public abstract void prepare(int x, int y);	
	public abstract void keepTransforming(int x, int y);
	public abstract void finalize(int x, int y);
}
