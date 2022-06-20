package frames;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTabbedPane;

public class CanvasGroup extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	
	public void addMouse(MainFrame main) {
		JTabbedPane pane = this;
		this.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e) {
				DrawingPanel drawingPanel = (DrawingPanel) pane.getSelectedComponent();
				main.menuBar.associate(drawingPanel);
				main.toolBar.associate(drawingPanel);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
		});
	}
}
