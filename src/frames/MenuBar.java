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
		this.fileMenu = new FileMenu("����(F)");
		this.fileMenu.setMnemonic(KeyEvent.VK_F);
		this.add(this.fileMenu); // ���
		
		this.editMenu = new EditMenu("����(E)");
		this.editMenu.setMnemonic(KeyEvent.VK_E);
		this.add(this.editMenu); // ���
		
		this.colorMenu = new ColorMenu("�׷��� �Ӽ�(G)");
		this.colorMenu.setMnemonic(KeyEvent.VK_G);
		this.add(this.colorMenu);
		
		
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel; // ����� �г��� �ּҸ� ����
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
