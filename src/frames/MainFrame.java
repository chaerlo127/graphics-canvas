package frames;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public MenuBar menuBar;
	public ToolBar toolBar;
	private JButton button;
	private DrawingPanel drawingPanel;
	private CanvasGroup canvas;
	//constructor, ������ �� ȣ��Ǵ� �Լ�
	public MainFrame() {
		this.setSize(600, 600); // �ʺ�, ����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ActionHandler actionHandler = new ActionHandler();
		WindowHandler windowHandler = new WindowHandler();
		this.addWindowListener(windowHandler);
		BorderLayout layoutManager = new BorderLayout();
		this.setLayout(layoutManager); // ���� ������
		
		
		this.menuBar = new MenuBar();
		this.setJMenuBar(this.menuBar);
		
		this.button = new JButton("���ο� ĵ���� ����");
		this.toolBar = new ToolBar();
		this.toolBar.add(this.button);
		this.add(this.toolBar, BorderLayout.NORTH);
		
		this.canvas = new CanvasGroup();
		this.add(canvas, BorderLayout.CENTER);
		
		drawingPanel = new DrawingPanel();
		this.canvas.add("first canvas", drawingPanel);
//		canvas.add(drawingPanel, BorderLayout.CENTER);
		

		this.toolBar.associate(this.drawingPanel); // ���� ������ �ض�
		this.menuBar.associate(this.drawingPanel); // ���� ������ �ض�
		this.canvas.addMouse(this);
		button.addActionListener(actionHandler);
	}

	public void initialize() {
		this.toolBar.initialize();
		this.menuBar.initialize();
		this.drawingPanel.initialize();
	}
	
	private class WindowHandler implements WindowListener{
		@Override
		public void windowOpened(WindowEvent e) {}
		@Override
		public void windowClosing(WindowEvent e) {
			menuBar.quit();
		}
		@Override
		public void windowClosed(WindowEvent e) {}
		@Override
		public void windowIconified(WindowEvent e) {}
		@Override
		public void windowDeiconified(WindowEvent e) {}
		@Override
		public void windowActivated(WindowEvent e) {}
		@Override
		public void windowDeactivated(WindowEvent e) {}
		}
	
	private class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			DrawingPanel drawingPanel1 = new DrawingPanel();
			canvas.add("new Canvas", drawingPanel1);
			toolBar.associate(drawingPanel1); // ���� ������ �ض�
			menuBar.associate(drawingPanel1); // ���� ������ �ض�
			drawingPanel = drawingPanel1;
		}
		
	}

}
