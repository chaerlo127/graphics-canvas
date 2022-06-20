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
		ButtonGroup buttonGroup = new ButtonGroup(); // 언제나 하나만 눌러져 있어야 함.
		ActionHandler actionHandler = new ActionHandler();
		
		for(ETools etool: ETools.values()){
			JRadioButton drawingTool = new JRadioButton(etool.getIcon()); 
			drawingTool.setActionCommand(etool.name());
			drawingTool.setSelectedIcon(etool.getChangeIcon());
			drawingTool.setToolTipText(etool.getToolTip());
			drawingTool.addActionListener(actionHandler);
			add(drawingTool); // array에 자식이 저장된다. 
			buttonGroup.add(drawingTool); // 복수로 버튼이 눌리지 않도록 해주는 코드
		}
	}
	public void initialize() {
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel; // 드로잉 패널의 주소를 저장
		JRadioButton defaultButton = (JRadioButton) this.getComponent(ETools.eSelection.ordinal());
		defaultButton.doClick();
	}

	
	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// 찾아와서 etools에서 객체를 찾아서 쓸 수 있음.
//			if(drawingPanel!= null) {
				drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));
//			}
		}
	}



}
