package menus;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import frames.DrawingPanel;
import global.Constants.EEditMenu;

public class EditMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private DrawingPanel drawingPanel;
	
	public EditMenu(String title) {
		super(title); // jmenu에게 함수를 호출해달라고 부르는 것
		ActionHandler actionHandler = new ActionHandler();
		for(EEditMenu eMenuItem : EEditMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getLabel());
			menuItem.setToolTipText(eMenuItem.getToolTip());
			menuItem.setAccelerator(KeyStroke.getKeyStroke(eMenuItem.getShortcut(), Event.CTRL_MASK));
			menuItem.setActionCommand(eMenuItem.name());
			menuItem.addActionListener(actionHandler);
			add(menuItem);
		}
		
	}
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	public void initialize() {
	}
	
	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(EEditMenu.eUndo.name())) {
				drawingPanel.undo();
			}else if(e.getActionCommand().equals(EEditMenu.eRedo.name())){
				drawingPanel.redo();
			}else if(e.getActionCommand().equals(EEditMenu.eCut.name())) {
				drawingPanel.cut();
			}else if(e.getActionCommand().equals(EEditMenu.eCopy.name())) {
				drawingPanel.copy();
			}else if(e.getActionCommand().equals(EEditMenu.ePaste.name())) {
				drawingPanel.paste();
			}
		}
	}
}
