package menus;

import java.awt.Desktop;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import frames.DrawingPanel;
import global.Constants.EFileMenu;
import global.Constants.FileDir;

public class FileMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private static final String URL = "https://www.naver.com/";
	private DrawingPanel drawingPanel;
	private File file;


	public FileMenu(String title) {
		super(title); // jmenu���� �Լ��� ȣ���ش޶�� �θ��� ��
		this.file = null; // ���������� �����ִ� ��
		
		ActionHandler actionHandler = new ActionHandler();
		for (EFileMenu eMenuItem : EFileMenu.values()) {
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
	
	private void load(File file) {
		System.out.println("open");
		try {
			FileInputStream fileInputStream;
			fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			Object object = objectInputStream.readObject();
			this.drawingPanel.setShapes(object);
			objectInputStream.close();
			this.drawingPanel.setUpdated(false); // update false
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
//			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "���� ������ ���� �ʽ��ϴ�.", "Warning", JOptionPane.WARNING_MESSAGE);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void store(File file) {
		try {
			FileOutputStream fileOutputStream;
			fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(this.drawingPanel.getShapes());
			objectOutputStream.close();
			this.drawingPanel.setUpdated(false); // update false
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "����Ǿ����ϴ�!", "Save", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	private void isUpdated() {
		if(this.drawingPanel.isUpdated()) {
			int result = JOptionPane.showConfirmDialog(null, "���Ͽ� �����Ͻðڽ��ϱ�?", EFileMenu.eSave.getLabel(),
					JOptionPane.YES_NO_OPTION);
			if (result == 0) {
				if(this.file != null) {
					save();
				}else {
					saveAs();
				}
			}
		}	
	}
	
	private void newPanel() {
		this.isUpdated();
		this.file = null;
		this.drawingPanel.clearShape();
		this.drawingPanel.removeAll();
		this.drawingPanel.initialize();
	}
	
	private void open() {
		this.isUpdated();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(FileDir.efileDir.getFileDir());
		fileChooser.setDialogTitle(EFileMenu.eOpen.getLabel());
		int ret = fileChooser.showOpenDialog(this.drawingPanel);
		if (ret == JFileChooser.APPROVE_OPTION) {
			this.file = fileChooser.getSelectedFile();
			load(this.file);
		}
	}
	
	private void save() {
		
		if (this.file == null) {
			saveAs();
		} else {
			store(this.file);
		}
	}

	private void saveAs() {
		System.out.println("save");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(FileDir.efileDir.getFileDir());
		fileChooser.setDialogTitle(EFileMenu.eSaveAs.getLabel());
		int ret = fileChooser.showSaveDialog(this.drawingPanel);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			this.file = fileChooser.getSelectedFile();
			store(this.file);
		}else {
			int result = JOptionPane.showConfirmDialog(null, "������ ���� �����ðڽ��ϱ�?", EFileMenu.eSave.getLabel(),
					JOptionPane.YES_NO_OPTION);
			if (result == 1) {
				saveAs();
			}
			return;
		}
	}

	public void quit() {
		isUpdated();
		int result = JOptionPane.showConfirmDialog(null, "���α׷��� �����ڽ��ϱ�?", EFileMenu.eSave.getLabel(),
				JOptionPane.YES_NO_OPTION);
		if (result == 0) {
			System.exit(0);
		} else {
			return;
		}
	}

	private void print() {
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		printerJob.setPrintable(this.drawingPanel);
		if (printerJob.printDialog()) {
			try {
				printerJob.print();
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void image() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(FileDir.efileDir.getFileDir());
		fileChooser.setDialogTitle(EFileMenu.eOpen.getLabel());
		int ret = fileChooser.showOpenDialog(this.drawingPanel);
		if (ret == JFileChooser.APPROVE_OPTION) {
			ImageIcon icon = new ImageIcon(fileChooser.getSelectedFile().toPath()+ "");
			this.drawingPanel.image(icon.getImage());
		}

	}
	private void search() {
		try {
			Desktop.getDesktop().browse(new URI(URL));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(EFileMenu.eOpen.name())) {
				open();
			} else if (e.getActionCommand().equals(EFileMenu.eSave.name())) {
				save();
			} else if (e.getActionCommand().equals(EFileMenu.eQuit.name())) {
				quit();
			} else if (e.getActionCommand().equals(EFileMenu.ePrint.name())) {
				print();
			} else if (e.getActionCommand().equals(EFileMenu.eSaveAs.name())) {
				saveAs();
			} else if (e.getActionCommand().equals(EFileMenu.eNew.name())) {
				newPanel();
			} else if(e.getActionCommand().equals(EFileMenu.eImage.name())) {
				image();
			} else if(e.getActionCommand().equals(EFileMenu.eSearch.name())) {
				search();
			}
		}

	}
}
