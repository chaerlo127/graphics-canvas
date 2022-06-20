package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Constants.ETools;

public class ToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	
	private DrawingPanel drawingPanel;

	public ToolBar() {
		ButtonGroup buttonGroup = new ButtonGroup(); // ������ �ϳ��� ������ �־�� ��.
		ActionHandler actionHandler = new ActionHandler();
		
		for(ETools etool: ETools.values()){
			JRadioButton drawingTool = new JRadioButton(etool.getIcon()); 
			drawingTool.setActionCommand(etool.name());
			drawingTool.setSelectedIcon(etool.getChangeIcon());
			drawingTool.setToolTipText(etool.getToolTip());
			drawingTool.addActionListener(actionHandler);
			add(drawingTool); // array�� �ڽ��� ����ȴ�. 
			buttonGroup.add(drawingTool); // ������ ��ư�� ������ �ʵ��� ���ִ� �ڵ�
		}
	}
	public void initialize() {
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel; // ����� �г��� �ּҸ� ����
		JRadioButton defaultButton = (JRadioButton) this.getComponent(ETools.eSelection.ordinal());
		defaultButton.doClick();
	}

	
	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// ã�ƿͼ� etools���� ��ü�� ã�Ƽ� �� �� ����.
//			if(drawingPanel!= null) {
				drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));
//			}
		}
	}



}
