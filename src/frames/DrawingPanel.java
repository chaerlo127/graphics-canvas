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
	// 겍체 고유의 특징
	private static final long serialVersionUID = 1L;

	
	// component
	// 자식들, 부품 ( 구성하고 있는, 독립적으로 존재하는 일부)
	private BufferedImage bufferdImage;
	private Graphics2D graphics2DBufferedImage; // 그림 도구, 안에 그림 판까지 가지고 다님. 
	private Vector<TShape> shapes;
	private Vector<TShape> removeShapes;
	// associated attribute
	// 다른 componet가 연결하는 값들 toolbar가 전달하는 값
	private ETools selectedTool;
	private TShape currentShape;
	private TShape selectedShape;
	private TShape editShape;
	private Transformers transformer;
	private boolean bUpdated;
	private Image img;
	
	// working variable
	// 프로그램을 돌리기 위해 필요한 메모리 공간
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
		
		// backgorund 색상 변경
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
		this.repaint(); // 그림을 다시 그려준다.
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
	// jpanel 그리는 도중에 이를 호출한다.
	public void paint(Graphics graphics) {
		super.paint(graphics); 
		//버퍼 이미지를 그린다.
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
			this.removeShapes.clear(); // redo, undo 하기위해 만든 vector 내부 값 삭제
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
//		graphics2DBufferedImage.setXORMode(this.getBackground()); // exclusive or로 그린 부분은 지우고, 그리지 않은 부분은 남겨둠.
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
			this.shapes.add(this.currentShape); // 벡터에 저장
			this.selectedShape = this.currentShape;
			// 항상 같이 다녀야 함.
			
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
			if (eDrawingState == EDrawingstate.eIdle) { // 아무것도 안하는 상태에서 원점을 잡아라
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
