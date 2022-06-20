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
	//constructor, 생성될 때 호출되는 함수
	public MainFrame() {
		this.setSize(600, 600); // 너비, 길이
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ActionHandler actionHandler = new ActionHandler();
		WindowHandler windowHandler = new WindowHandler();
		this.addWindowListener(windowHandler);
		BorderLayout layoutManager = new BorderLayout();
		this.setLayout(layoutManager); // 왼쪽 위부터
		
		
		this.menuBar = new MenuBar();
		this.setJMenuBar(this.menuBar);
		
		this.button = new JButton("새로운 캔버스 생성");
		this.toolBar = new ToolBar();
		this.toolBar.add(this.button);
		this.add(this.toolBar, BorderLayout.NORTH);
		
		this.canvas = new CanvasGroup();
		this.add(canvas, BorderLayout.CENTER);
		
		drawingPanel = new DrawingPanel();
		this.canvas.add("first canvas", drawingPanel);
//		canvas.add(drawingPanel, BorderLayout.CENTER);
		

		this.toolBar.associate(this.drawingPanel); // 서로 연결을 해라
		this.menuBar.associate(this.drawingPanel); // 서로 연결을 해라
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
			toolBar.associate(drawingPanel1); // 서로 연결을 해라
			menuBar.associate(drawingPanel1); // 서로 연결을 해라
			drawingPanel = drawingPanel1;
		}
		
	}

}
