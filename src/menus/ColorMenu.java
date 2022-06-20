package menus;

import java.awt.Color;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import frames.DrawingPanel;
import global.Constants.EColorMenu;

public class ColorMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private DrawingPanel drawingPanel;
	
	public ColorMenu(String title) {
		super(title); // jmenu에게 함수를 호출해달라고 부르는 것
		ActionHandler actionHandler = new ActionHandler();
		for(EColorMenu eMenuItem : EColorMenu.values()) {
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
			if (e.getActionCommand().equals(EColorMenu.efiller.name())) {
				Color color = JColorChooser.showDialog(null, EColorMenu.efiller.getToolTip(), getBackground());
				if(color != null){
					drawingPanel.fill(color);
				}
			}else if(e.getActionCommand().equals(EColorMenu.eliner.name())){
				Color color = JColorChooser.showDialog(null, EColorMenu.eliner.getToolTip(), getBackground());
				if(color != null){
					drawingPanel.getChooseColor(color);
				}
			}else if(e.getActionCommand().equals(EColorMenu.ethicker.name())){
				drawingPanel.getThickness();
			}else if(e.getActionCommand().equals(EColorMenu.ethinner.name())) {
				drawingPanel.getThinner();
			}else if(e.getActionCommand().equals(EColorMenu.eRound.name())) {
				drawingPanel.getRound();
			}else if(e.getActionCommand().equals(EColorMenu.eMiter.name())) {
				drawingPanel.getMiter();
			}
			
		}
	}
}
