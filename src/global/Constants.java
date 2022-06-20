package global;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TSelection;
import shapes.TShape;
import shapes.TText;

final public class Constants {
	
	// ������ Ÿ��
	public enum ETransformationStyle{
		e2Point,
		eNpoint
	}
	
	// ����
	public enum ETools {
		eSelection("����", new ImageIcon("image/K.jpg"), new ImageIcon("image/CK.jpg"), new TSelection(), "Pick selection", ETransformationStyle.e2Point),
		eRectanngle("�׸�", new ImageIcon("image/R.jpg"), new ImageIcon("image/CR.jpg"), new TRectangle(), "Draw Rectangle",ETransformationStyle.e2Point),
		eOval("���׶��", new ImageIcon("image/O.jpg"),new ImageIcon("image/CO.jpg"),  new TOval(), "Draw Oval in Panel", ETransformationStyle.e2Point),
		eLine("����", new ImageIcon("image/L.jpg"), new ImageIcon("image/CL.jpg"), new TLine(), "Draw Line in Panel", ETransformationStyle.e2Point),
		ePolygon("�ٰ���", new ImageIcon("image/P.jpg"), new ImageIcon("image/CP.jpg"), new TPolygon(), "Draw Polygon in Panel", ETransformationStyle.eNpoint),
		eText("�ؽ�Ʈ", new ImageIcon("image/T.jpg"), new ImageIcon("image/CT.jpg"), new TText(), "Draw TextArea in Panel", ETransformationStyle.e2Point);

		// ������ array, �̸��� ������ �� ����. '������ �ִ� ��ü'�� ����� ��
		// ��ü�� shape�� ���� ����
		private Image image;
		private ImageIcon changeIcon;
		private String label;
		private TShape tool;
		private ImageIcon icon;
		private String toolTip;
		private ETransformationStyle eTransformationStyle;
		private ETools(String label, ImageIcon icon, ImageIcon changeIcon, TShape tool, String toolTip, ETransformationStyle eTransformationStyle) {
			this.label = label;
			this.tool = tool;
			this.icon = icon;
			this.changeIcon = changeIcon;
			this.toolTip = toolTip;
			this.eTransformationStyle = eTransformationStyle;
		}
		
		public ImageIcon getIcon() {
			this.image = this.icon.getImage();
			return new ImageIcon(this.image.getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		}
		public String getlabel() {
			return this.label;
		}
		public String getToolTip() {
			return this.toolTip;
		}

		public TShape newShape() {
			return this.tool.clone();
		}
		
		public ImageIcon getChangeIcon() {
			this.image = this.changeIcon.getImage();
			return new ImageIcon(this.image.getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		}
		
		public ETransformationStyle getETransformationStyle() {
			return this.eTransformationStyle;
		}

	}
	
	
	public enum EFileMenu{
		eNew("���� �����", "new canvas", 'N'),
		eOpen("����", "open the file", 'O'),
		eSave("�����ϱ�", "save this file", 'S'),
		eSaveAs("�ٸ��̸����� ����", "save as, save another file", 'A'),
		eImage("�̹��� �ҷ�����", "open Iamge", 'R'),
		eSearch("������ �̹��� ã��", "open web site for searching image", 'H'),
		ePrint("����Ʈ", "print this file", 'P'),
		eQuit("����", "exit this program", 'Q');
		
		private String toolTip;
		private String label;
		private char shortcut;
		EFileMenu(String label, String toolTip, char shortcut) {
			this.label = label;
			this.toolTip = toolTip;
			this.shortcut = shortcut;
		}
		
		public String getLabel() {
			return this.label;
		}
		public String getToolTip() {
			return this.toolTip;
		}
		public char getShortcut() {
			return this.shortcut;
		}
	}
	
	public enum EEditMenu{
		eUndo("�� ������", "undo", 'Z'),
		eRedo("�� �ķ�", "redo", 'Y'),
		eCut("�ڸ���", "cut the shapes",'X'), 
		eCopy("�����ϱ�", "copy the shapes", 'C'),
		ePaste("�����ֱ�", "paste the shapes", 'V'),
		eGroup("�׷���", "grouping by shapes", 'G'),
		eUngroup("�׷��� ����", "unGrouping by shapes", 'U');
		private String toolTip;
		private String label;
		private char shortcut;
		EEditMenu(String label, String toolTip, char shortcut){
			this.label = label;
			this.toolTip = toolTip;
			this.shortcut = shortcut;
		}
		
		public String getLabel() {
			return this.label;
		}
		public String getToolTip() {
			return this.toolTip;
		}
		public char getShortcut() {
			return this.shortcut;
		}

	}
	
	public enum EColorMenu{
		efiller("���� ä���", "fill selected shape", 'L'),
		eliner("���� �� �׸���", "change color selected shape", 'D'),
		ethicker("�� �β���", "draw Shape thicker", 'T'),
		ethinner("�� ���", "draw Shape thinner", 'K'),
		eRound("�簢�� �ձ۰�", "draw Rectangle Round shape", 'J'),
		eMiter("���� ����", "draw Shape Miter", 'I');
		private String label, toolTip;
		private char shortcut;
		EColorMenu(String label, String toolTip, char shortcut){
			this.label = label;
			this.toolTip = toolTip;
			this.shortcut = shortcut;
		}
		public String getLabel() {
			return this.label;
		}
		public String getToolTip() {
			return this.toolTip;
		}
		public char getShortcut() {
			return this.shortcut;
		}
		
	}
	
	public enum FileDir{
		efileDir(new File(System.getProperty("user.dir")));

		File file;
		private FileDir(File file) {
			this.file = file;
		}
		public File getFileDir() {
			return this.file;
		}
	}

}
