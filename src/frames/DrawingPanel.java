package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import global.Constants.ETools;
import global.Constants.ETransformationStyle;
import shapes.TAnchors.EAnchors;
import shapes.TRectangle;
import shapes.TSelection;
import shapes.TShape;
import shapes.TText;
import transformers.Drawer;
import transformers.Mover;
import transformers.Resizer;
import transformers.Rotator;
import transformers.Transformers;

public class DrawingPanel extends JPanel implements Printable{
	
	// attributes
	// ��ü ������ Ư¡
	private static final long serialVersionUID = 1L;

	
	// component
	// �ڽĵ�, ��ǰ ( �����ϰ� �ִ�, ���������� �����ϴ� �Ϻ�)
	private BufferedImage bufferdImage;
	private Graphics2D graphics2DBufferedImage; // �׸� ����, �ȿ� �׸� �Ǳ��� ������ �ٴ�. 
	private Vector<TShape> shapes;
	private Vector<TShape> removeShapes;
	// associated attribute
	// �ٸ� componet�� �����ϴ� ���� toolbar�� �����ϴ� ��
	private ETools selectedTool;
	private TShape currentShape;
	private TShape selectedShape;
	private TShape editShape;
	private Transformers transformer;
	private boolean bUpdated;
	private Image img;
	
	// working variable
	// ���α׷��� ������ ���� �ʿ��� �޸� ����
	private enum EDrawingstate {
		eIdle, e2PointTransformation, eNPointDrawing, eTransforming
	}
	private EDrawingstate eDrawingState;

	public DrawingPanel() {
		this.setBackground(Color.white);
		this.eDrawingState = EDrawingstate.eIdle;
		this.bUpdated = false;
		
		this.shapes = new Vector<TShape>();
		this.removeShapes = new Vector<TShape>();
		this.img = null;
		
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler); // button
		this.addMouseMotionListener(mouseHandler); // move
		this.addMouseWheelListener(mouseHandler); // wheel
	}
	public void initialize() {
		this.bufferdImage = (BufferedImage) this.createImage(this.getWidth(), this.getHeight());
		this.graphics2DBufferedImage = (Graphics2D) this.bufferdImage.getGraphics();
		
		// backgorund ���� ����
		this.graphics2DBufferedImage.setColor(Color.white);
		this.graphics2DBufferedImage.fillRect(0, 0, this.getWidth(), this.getHeight());
		
	}
	public boolean isUpdated() {
		return this.bUpdated;
	}
	
	public void setUpdated(boolean bupdated) {
		this.bUpdated = bupdated;
	}
////////////////////////////////////////////////////////////////////////////////////
	// file open & save
	public Object getShapes() {return this.shapes;}
	
	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes) {
		this.shapes = (Vector<TShape>) shapes;
		this.repaint(); // �׸��� �ٽ� �׷��ش�.
	}
	
	public void clearShape() {
		this.shapes.clear();
		this.removeShapes.clear();
		repaint();
	}
	
	public void undo() {
		if(this.shapes.isEmpty());
		else {
			this.removeShapes.add(this.shapes.get(this.shapes.size()-1));
			this.shapes.remove(this.shapes.size()-1);
			this.initialize();
			repaint();
		}
		this.setUpdated(true);
	}
	
	public void redo() {
		if(this.removeShapes.isEmpty());
		else {
			this.shapes.add(this.removeShapes.get(this.removeShapes.size()-1));
			this.removeShapes.remove(this.removeShapes.size()-1);
			this.initialize();
			repaint();
		}
		this.setUpdated(true);
	}
	
	
	public void cut() {
		if(this.selectedShape!=null) {
			this.editShape = this.selectedShape;
			this.shapes.remove(this.selectedShape);
		
			this.removeShapes.add(this.editShape);
			this.setUpdated(true);
			this.initialize();
			repaint();
		}
	}

	public void copy() {
		if(this.selectedShape!=null) {
			this.editShape = this.selectedShape.clone();
			this.editShape.shape = this.selectedShape.shape;
			this.editShape.getColor(this.selectedShape.setColor());
			this.editShape.getFillColor(this.selectedShape.setFillColor());
			this.editShape.setColored(this.selectedShape.isColored());
			this.editShape.setFilled(this.selectedShape.isFilled());
			this.editShape.setThicked(this.selectedShape.isThicked());
			this.editShape.setImage(this.selectedShape.getImage());
			this.editShape.setDash(this.selectedShape.getDash());
			this.editShape.getAffineTransform().translate(20, 20);
			this.removeShapes.add(this.editShape);
			this.setUpdated(true);
			
			this.initialize();
			repaint();
		}
	}

	public void paste() {
		if(this.selectedShape!=null) {
			this.removeShapes.remove(this.editShape);
			this.selectedShape.setSelected(false);
			this.selectedShape = this.editShape;
			this.editShape.setSelected(true);
			this.shapes.add(this.editShape);
			this.setUpdated(true);
			
			this.initialize();
			repaint();
		}
	}
	public void image(Image read) {
		if(read != null) {
			this.img = read;
		}
	}
	
	// printable Override method
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex > 0) {
			return Printable.NO_SUCH_PAGE;
		}
		Graphics2D g = (Graphics2D) graphics;
		g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

		for (TShape shape : this.shapes) {
			shape.draw(g);
			if(shape.getImage()!=null) {
				ImageIcon image = new ImageIcon(shape.getImage().getScaledInstance((int) shape.getShape().getBounds().getWidth(), 
						(int)shape.getShape().getBounds().getHeight(), Image.SCALE_SMOOTH));
				shape.setImage(image.getImage());
				g.drawImage(shape.getImage(), (int) shape.getShape().getBounds().getX(),
						(int) shape.getShape().getBounds().getY(), this);
			}
		}
		return Printable.PAGE_EXISTS;
	}
	
	public void setSelectedTool(ETools eTools) {
		this.selectedTool = eTools;
	}
	
	//draw line this selectedShape
	public void getChooseColor(Color color) {
		if(this.selectedShape != null) {
			this.shapes.remove(this.selectedShape);
			this.selectedShape.setColored(true);
			this.selectedShape.getColor(color);
			this.selectedShape.draw((Graphics2D) this.getGraphics());
			
			this.shapes.add(this.selectedShape);
			this.setUpdated(true);
		}
	}
	
	
	// fill this selectedShape
	public void fill(Color color) {
		if(this.selectedShape != null) {
			this.shapes.remove(this.selectedShape);
			this.selectedShape.setColored(true);
			this.selectedShape.setFilled(true);
			this.selectedShape.getFillColor(color);
			this.selectedShape.draw((Graphics2D)this.getGraphics());
			this.shapes.add(this.selectedShape);
			this.setUpdated(true);
		}		
	}
	
	public void getThickness() {
		if(this.selectedShape != null) {
			this.selectedShape.setThicked(true);
			this.selectedShape.draw((Graphics2D)this.getGraphics());
			this.setUpdated(true);
			
		}
	}
	
	public void getThinner() {
		if(this.selectedShape != null) {
			this.selectedShape.setThicked(false);
			this.selectedShape.draw((Graphics2D)this.getGraphics());
			this.setUpdated(true);
			
		}
	}
	
	public void getRound() {
		if(this.selectedShape != null) {
			this.selectedShape.getRound();
			this.selectedShape.draw((Graphics2D)this.getGraphics());
			this.setUpdated(true);
		}
	}
	
	public void getMiter() {
		if(this.selectedShape != null) {
			this.selectedShape.getMiter();
			this.selectedShape.draw((Graphics2D)this.getGraphics());
			this.setUpdated(true);
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	// jpanel �׸��� ���߿� �̸� ȣ���Ѵ�.
	public void paint(Graphics graphics) {
		super.paint(graphics); 
		//���� �̹����� �׸���.
		for(TShape shape : this.shapes) {
			shape.draw(this.graphics2DBufferedImage);
			if(shape.getImage()!=null) {
				ImageIcon image = new ImageIcon(shape.getImage().getScaledInstance((int) shape.getShape().getBounds().getWidth(), 
						(int)shape.getShape().getBounds().getHeight(), Image.SCALE_SMOOTH));
				shape.setImage(image.getImage());
				this.graphics2DBufferedImage.drawImage(shape.getImage(), (int) shape.getShape().getBounds().getX(),
						(int) shape.getShape().getBounds().getY(), this);
			}
		}
		graphics.drawImage(this.bufferdImage, 0, 0, this);
		
	}
	
	private void prepareTransformation(int x, int y) {
		if (selectedTool == ETools.eSelection) { 
			currentShape = onShape(x, y);
			this.removeShapes.clear(); // redo, undo �ϱ����� ���� vector ���� �� ����
			this.shapes.remove(this.currentShape); 
			if (currentShape != null) {
				this.currentShape.setSelected(false);
				if (currentShape.getSelectedAnchor() == EAnchors.eMove) {
					// move
					this.transformer = new Mover(currentShape);
				} else if (currentShape.getSelectedAnchor() == EAnchors.eRR) {
					// rotate
					this.transformer = new Rotator(currentShape);
				} else {
					// resize
					this.transformer = new Resizer(currentShape);
				}
			} else {
				this.currentShape = this.selectedTool.newShape();
				this.transformer = new Drawer(currentShape);
				this.currentShape.getMiter();
			}
		} else { // drawing
			this.currentShape = this.selectedTool.newShape();
			this.transformer = new Drawer(currentShape);
		}
		this.initialize();
		this.repaint();
//		graphics2DBufferedImage.setXORMode(this.getBackground()); 
		this.transformer.prepare(x, y);
		this.currentShape.draw(graphics2DBufferedImage);	
		this.currentShape.setSelected(false);
	}

	private void keepTransformation(int x, int y) {
		// erase
//		graphics2DBufferedImage.setXORMode(this.getBackground()); // exclusive or�� �׸� �κ��� �����, �׸��� ���� �κ��� ���ܵ�.
		this.initialize();
		this.repaint();
//		this.currentShape.draw(graphics2DBufferedImage);
		
		// draw
		this.currentShape.setSelected(false);
		this.transformer.keepTransforming(x, y);
		this.currentShape.draw(graphics2DBufferedImage);
	}

	private void continueTransformation(int x, int y) {
		this.currentShape.addPoint(x, y);
	}

	private void finishTransformation(int x, int y) {
		graphics2DBufferedImage.setXORMode(this.getBackground());
		this.transformer.finalize(x, y);
		
		if (this.selectedShape != null) {
			this.selectedShape.setSelected(false);
		}
		if(this.currentShape instanceof TText) {
			this.add(((TText) this.currentShape).getText());
		}
		if(this.img != null && this.currentShape instanceof TRectangle) {
			ImageIcon image = new ImageIcon(this.img.getScaledInstance((int) this.currentShape.getShape().getBounds().getWidth(), 
					(int)this.currentShape.getShape().getBounds().getHeight(), Image.SCALE_SMOOTH));
			this.img = image.getImage();
			this.getGraphics().drawImage(this.img, (int) this.currentShape.getShape().getBounds().getX(),
					(int) this.currentShape.getShape().getBounds().getY(), this);
			
			this.currentShape.setImage(this.img);
		}
		
		if (!(this.currentShape instanceof TSelection && this.transformer instanceof Drawer)) {
			this.shapes.add(this.currentShape); // ���Ϳ� ����
			this.selectedShape = this.currentShape;
			// �׻� ���� �ٳ�� ��.
			
			this.selectedShape.setSelected(true);
			this.setUpdated(true);
		}
		
		this.initialize();
		this.repaint();
		this.img = null;

	}

	private TShape onShape(int x, int y) {
		for(TShape shape : this.shapes) {
			if(shape.contains(x, y)) {
				return shape;
			}
		}
		return null;
	}

	private void changeSelection(int x, int y) {
		// erase previous selection
		if(this.selectedShape != null) {
			this.selectedShape.setSelected(false);
		}
		
		// draw new selection
		this.selectedShape = this.onShape(x, y);
		if(this.selectedShape!=null) {
			this.selectedShape.setSelected(true);
			this.selectedShape.draw((Graphics2D) this.getGraphics());
		}
		this.initialize();
		repaint();
	}
	
	private void changeCursor(int x, int y) {
		Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
		if(this.selectedTool !=  ETools.eSelection) {
			cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		}		
		this.currentShape = onShape(x, y);
		if(this.currentShape != null) {
			cursor = new Cursor(Cursor.MOVE_CURSOR);
			if(this.currentShape.isSelected()) {
				EAnchors eAnchor = this.currentShape.getSelectedAnchor();
				switch (eAnchor) {
					case eRR :  cursor = new Cursor(Cursor.HAND_CURSOR);      break;
					case eNN :  cursor = new Cursor(Cursor.N_RESIZE_CURSOR);  break;
					case eNW :  cursor = new Cursor(Cursor.NW_RESIZE_CURSOR); break;
					case eSW :  cursor = new Cursor(Cursor.SW_RESIZE_CURSOR); break;
					case eSS :  cursor = new Cursor(Cursor.S_RESIZE_CURSOR);  break;
					case eSE :  cursor = new Cursor(Cursor.SE_RESIZE_CURSOR); break;
					case eEE :  cursor = new Cursor(Cursor.E_RESIZE_CURSOR);  break;
					case eNE :  cursor = new Cursor(Cursor.NE_RESIZE_CURSOR); break;
					case eWW :  cursor = new Cursor(Cursor.W_RESIZE_CURSOR);  break;
					default : break;
				}
			}
		}
		this.setCursor(cursor);
	}

	private class MouseHandler implements MouseInputListener, MouseWheelListener {
		// MouseListner(button), MouseMotionLister(move) : MouseInputListner
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getClickCount() == 1) {
					this.lButtonClicked(e);
				} else if (e.getClickCount() == 2) {
					this.lButtonDoubleClicked(e);
				}
			}
		}

		private void lButtonClicked(MouseEvent e) {
			if (eDrawingState == EDrawingstate.eIdle) { // �ƹ��͵� ���ϴ� ���¿��� ������ ��ƶ�
				changeSelection(e.getX(), e.getY());
				selectedShape = onShape(e.getX(), e.getY());
				if(selectedTool.getETransformationStyle() == ETransformationStyle.eNpoint) {
					prepareTransformation(e.getX(), e.getY());
					eDrawingState = EDrawingstate.eNPointDrawing;
				}else {
					
				}
			} else if (eDrawingState == EDrawingstate.eNPointDrawing) {
				continueTransformation(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingstate.eNPointDrawing) {
				keepTransformation(e.getX(), e.getY());
			} else if(eDrawingState == EDrawingstate.eIdle) {
				changeCursor(e.getX(),e.getY());
			}
		}


		private void lButtonDoubleClicked(MouseEvent e) {
			if (eDrawingState == EDrawingstate.eNPointDrawing) {
				finishTransformation(e.getX(), e.getY());
				eDrawingState = EDrawingstate.eIdle;
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingstate.eIdle) {
				if(selectedTool.getETransformationStyle() == ETransformationStyle.e2Point) {
					prepareTransformation(e.getX(), e.getY());
					eDrawingState = EDrawingstate.e2PointTransformation;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingstate.e2PointTransformation) {
				keepTransformation(e.getX(), e.getY());
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingstate.e2PointTransformation) {
				finishTransformation(e.getX(), e.getY());
				eDrawingState = EDrawingstate.eIdle;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {}

	}
}
