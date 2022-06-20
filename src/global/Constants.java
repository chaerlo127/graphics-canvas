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
	
	// 도구의 타입
	public enum ETransformationStyle{
		e2Point,
		eNpoint
	}
	
	// 서수
	public enum ETools {
		eSelection("선택", new ImageIcon("image/K.jpg"), new ImageIcon("image/CK.jpg"), new TSelection(), "Pick selection", ETransformationStyle.e2Point),
		eRectanngle("네모", new ImageIcon("image/R.jpg"), new ImageIcon("image/CR.jpg"), new TRectangle(), "Draw Rectangle",ETransformationStyle.e2Point),
		eOval("동그라미", new ImageIcon("image/O.jpg"),new ImageIcon("image/CO.jpg"),  new TOval(), "Draw Oval in Panel", ETransformationStyle.e2Point),
		eLine("라인", new ImageIcon("image/L.jpg"), new ImageIcon("image/CL.jpg"), new TLine(), "Draw Line in Panel", ETransformationStyle.e2Point),
		ePolygon("다각형", new ImageIcon("image/P.jpg"), new ImageIcon("image/CP.jpg"), new TPolygon(), "Draw Polygon in Panel", ETransformationStyle.eNpoint),
		eText("텍스트", new ImageIcon("image/T.jpg"), new ImageIcon("image/CT.jpg"), new TText(), "Draw TextArea in Panel", ETransformationStyle.e2Point);

		// 일종의 array, 이름을 지어줄 수 있음. '순서가 있는 객체'를 만드는 것
		// 객체에 shape을 만들어서 저장
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
		eNew("새로 만들기", "new canvas", 'N'),
		eOpen("열기", "open the file", 'O'),
		eSave("저장하기", "save this file", 'S'),
		eSaveAs("다른이름으로 저장", "save as, save another file", 'A'),
		eImage("이미지 불러오기", "open Iamge", 'R'),
		eSearch("웹에서 이미지 찾기", "open web site for searching image", 'H'),
		ePrint("프린트", "print this file", 'P'),
		eQuit("종료", "exit this program", 'Q');
		
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
		eUndo("이 전으로", "undo", 'Z'),
		eRedo("이 후로", "redo", 'Y'),
		eCut("자르기", "cut the shapes",'X'), 
		eCopy("복사하기", "copy the shapes", 'C'),
		ePaste("븥여넣기", "paste the shapes", 'V'),
		eGroup("그룹핑", "grouping by shapes", 'G'),
		eUngroup("그룹핑 해제", "unGrouping by shapes", 'U');
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
		efiller("도형 채우기", "fill selected shape", 'L'),
		eliner("도형 선 그리기", "change color selected shape", 'D'),
		ethicker("선 두껍게", "draw Shape thicker", 'T'),
		ethinner("선 얇게", "draw Shape thinner", 'K'),
		eRound("사각형 둥글게", "draw Rectangle Round shape", 'J'),
		eMiter("도형 점선", "draw Shape Miter", 'I');
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
