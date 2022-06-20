package frames;

import java.awt.event.KeyEvent;

import javax.swing.JMenuBar;

import menus.ColorMenu;
import menus.EditMenu;
import menus.FileMenu;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private DrawingPanel drawingPanel;
	private FileMenu fileMenu;
	private EditMenu editMenu;
	private ColorMenu colorMenu;
	
	public MenuBar() {
		this.fileMenu = new FileMenu("파일(F)");
		this.fileMenu.setMnemonic(KeyEvent.VK_F);
		this.add(this.fileMenu); // 등록
		
		this.editMenu = new EditMenu("편집(E)");
		this.editMenu.setMnemonic(KeyEvent.VK_E);
		this.add(this.editMenu); // 등록
		
		this.colorMenu = new ColorMenu("그래픽 속성(G)");
		this.colorMenu.setMnemonic(KeyEvent.VK_G);
		this.add(this.colorMenu);
		
		
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel; // 드로잉 패널의 주소를 저장
		this.fileMenu.associate(this.drawingPanel);
		this.editMenu.associate(this.drawingPanel);
		this.colorMenu.associate(this.drawingPanel);
	}

	public void initialize() {
		this.fileMenu.initialize();
		this.editMenu.initialize();
		this.colorMenu.initialize();
	}

	public void quit() {
		this.fileMenu.quit();
	}

}
