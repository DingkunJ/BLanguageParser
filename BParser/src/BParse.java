import java.awt.Component;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;
import java.awt.event.ActionEvent;
import javax.swing.SpringLayout;
import javax.swing.TransferHandler;
import javax.swing.filechooser.FileFilter;
import javax.xml.crypto.Data;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuKeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * @author 徐浩文 b语言解释器的界面
 *
 */
public class BParse {

	private JFrame frame;
	private JTextField textField;
	private JButton button;
	private JButton buttonChooseFile;
	private TextArea textArea;
	private JFileChooser jFileChooser = new JFileChooser();

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

	public JButton getButtonChooseFile() {
		return buttonChooseFile;
	}

	public void setButton_1(JButton buttonChooseFile) {
		this.buttonChooseFile = buttonChooseFile;
	}

	public TextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}

	public JFileChooser getjFileChooser() {
		return jFileChooser;
	}

	public void setjFileChooser(JFileChooser jFileChooser) {
		this.jFileChooser = jFileChooser;
	}

	/**
	 * 创建B语言界面程序
	 */
	public BParse() {
		initialize();
	}

	/**
	 * 初始化B语言解释器的界面.
	 */
	private void initialize() {
		/**
		 * 建立一个Jframe容器，使用绝对定位
		 */
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.inactiveCaption);
		frame.setBackground(new Color(192, 192, 192));
		frame.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(BParse.class.getResource("/com/sun/javafx/scene/web/skin/Bold_16x16_JFX.png")));
		frame.setBounds(100, 100, 473, 341);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("B语言解释器");
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		/**
		 * 文件地址，提示语句
		 */
		JLabel label = new JLabel("文件地址：");
		springLayout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, label, 7, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, label, 82, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(label);

		/**
		 * 用于输入文件地址，或者显示“浏览按钮”加载的文件绝对地址
		 */
		textField = new JTextField();
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Component parent = null;
				// b后缀文件过滤器
				FileFilter bFileFilter = new BFileFilter("b", "B文件（*.b）");
				// txt后缀文件过滤器
				FileFilter txtFileFilter = new BFileFilter("txt", "TXT文件（*.txt）");
				// 给jfilechoser添加b文件过滤器
				jFileChooser.addChoosableFileFilter(bFileFilter);
				// 给jfilechooser添加TXT文件过滤器
				jFileChooser.addChoosableFileFilter(txtFileFilter);
				// 打开文件选择对话框
				jFileChooser.showOpenDialog(parent);
				if (jFileChooser.getSelectedFile() == null) {
					System.out.println("没有选择文件，请选择你要编译的文件");
					JOptionPane.showMessageDialog(null, "没有选择文件，请选择你要编译的文件");
					return;
				}
				if (jFileChooser.getSelectedFile().getAbsolutePath() == null
						|| jFileChooser.getSelectedFile().getAbsolutePath().trim().equals("")) {
					System.out.println("请输入合法的文件地址！");
					JOptionPane.showMessageDialog(null, "请输入合法的文件地址！");
					return;
				}
				File checkFile = new File(jFileChooser.getSelectedFile().getAbsolutePath());
				if (!checkFile.exists()) {
					System.out.println("文件无法打开，请检查你的文件路径！");
					JOptionPane.showMessageDialog(null, "文件无法打开，请检查你的文件路径！");
					return;
				}
				textField.setText(jFileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		label.setLabelFor(textField);
		springLayout.putConstraint(SpringLayout.NORTH, textField, 5, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField, 7, SpringLayout.EAST, label);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		/**
		 * 添加拖拽事件，实现文件拖拽进入文本框得到文件地址
		 */
		textField.setTransferHandler(new TransferHandler() {

			/**
			 * transferhandler函数实现文件拖拽
			 * 该函数参考网址：http://blog.csdn.net/java_faep/article/details/53523401
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean importData(JComponent comp, Transferable t) {
				// TODO Auto-generated method stub
				try {
					Object file = t.getTransferData(DataFlavor.javaFileListFlavor);
					String filePath = file.toString();
					if (filePath.startsWith("[")) {
						filePath = filePath.substring(1);
					}
					if (filePath.endsWith("]")) {
						filePath = filePath.substring(0, filePath.length() - 1);
					}
					textField.setText(filePath);
					return true;
				} catch (Exception e) {
					System.out.println(e.getMessage());
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				return false;
			}

			@Override
			public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
				// TODO Auto-generated method stub
				for (int i = 0; i < transferFlavors.length; i++) {
					if (DataFlavor.javaFileListFlavor.equals(transferFlavors[i])) {
						return true;
					}
				}
				return false;
			}

		});

		/**
		 * 编译按钮，点击后执行编译命令，结果显示在textarea中
		 */
		button = new JButton("编译");
		button.setBackground(new Color(0, 128, 0));
		springLayout.putConstraint(SpringLayout.NORTH, button, 5, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, button, -9, SpringLayout.EAST, frame.getContentPane());
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * 调用构造函数
				 */
				if (textField.getText() == null || textField.getText().trim().equals("")) {
					System.out.println("请输入合法的文件地址！");
					JOptionPane.showMessageDialog(null, "请输入合法的文件地址！");
					return;
				}
				File checkFile = new File(textField.getText());
				if (!checkFile.exists()) {
					System.out.println("文件无法打开，请检查你的文件路径！");
					JOptionPane.showMessageDialog(null, "文件无法打开，请检查你的文件路径！");
					return;
				}
				Compile compile = new Compile();
				compile.scanner(textField.getText());
				Parser parser = new Parser();
				parser.BParser(compile.mtt);
				Date date = new Date();
				String compileinfo = "编译时间：\n";
				SimpleDateFormat dateFormat;
				dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dateFormat.format(date);
				compileinfo += dateFormat.format(date) + "\n";
				textArea.setText(compileinfo + parser.parseresult);
			}
		});
		frame.getContentPane().add(button);

		/**
		 * 打开本地文件，将地址保存在textfield
		 */
		buttonChooseFile = new JButton("浏览文件");
		buttonChooseFile.setBackground(SystemColor.textHighlight);
		springLayout.putConstraint(SpringLayout.WEST, buttonChooseFile, 238, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, buttonChooseFile, -91, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, button, 7, SpringLayout.EAST, buttonChooseFile);
		springLayout.putConstraint(SpringLayout.EAST, textField, -7, SpringLayout.WEST, buttonChooseFile);
		springLayout.putConstraint(SpringLayout.NORTH, buttonChooseFile, 5, SpringLayout.NORTH, frame.getContentPane());
		buttonChooseFile
				.setIcon(new ImageIcon(BParse.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		buttonChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * 浏览文件按钮点击获取文件地址放置在textField中
				 */
				Component parent = null;
				// b后缀文件过滤器
				FileFilter bFileFilter = new BFileFilter("b", "B文件（*.b）");
				// txt后缀文件过滤器
				FileFilter txtFileFilter = new BFileFilter("txt", "TXT文件（*.txt）");
				// 给jfilechoser添加b文件过滤器
				jFileChooser.addChoosableFileFilter(bFileFilter);
				// 给jfilechooser添加TXT文件过滤器
				jFileChooser.addChoosableFileFilter(txtFileFilter);
				// 打开文件选择对话框
				jFileChooser.showOpenDialog(parent);
				if (jFileChooser.getSelectedFile() == null) {
					System.out.println("没有选择文件，请选择你要编译的文件");
					JOptionPane.showMessageDialog(null, "没有选择文件，请选择你要编译的文件");
					return;
				}
				if (jFileChooser.getSelectedFile().getAbsolutePath() == null
						|| jFileChooser.getSelectedFile().getAbsolutePath().trim().equals("")) {
					System.out.println("请输入合法的文件地址！");
					JOptionPane.showMessageDialog(null, "请输入合法的文件地址！");
					return;
				}
				File checkFile = new File(jFileChooser.getSelectedFile().getAbsolutePath());
				if (!checkFile.exists()) {
					System.out.println("文件无法打开，请检查你的文件路径！");
					JOptionPane.showMessageDialog(null, "文件无法打开，请检查你的文件路径！");
					return;
				}
				textField.setText(jFileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		frame.getContentPane().add(buttonChooseFile);

		/**
		 * 编译结果显示文本域
		 */
		textArea = new TextArea();
		textArea.setBackground(Color.LIGHT_GRAY);
		springLayout.putConstraint(SpringLayout.SOUTH, button, -6, SpringLayout.NORTH, textArea);
		springLayout.putConstraint(SpringLayout.SOUTH, buttonChooseFile, -6, SpringLayout.NORTH, textArea);
		springLayout.putConstraint(SpringLayout.SOUTH, textField, -6, SpringLayout.NORTH, textArea);
		springLayout.putConstraint(SpringLayout.SOUTH, label, -6, SpringLayout.NORTH, textArea);
		springLayout.putConstraint(SpringLayout.WEST, textArea, 8, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textArea, 0, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, 0, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 45, SpringLayout.NORTH, frame.getContentPane());
		textArea.setText("编译结果：");
		frame.getContentPane().add(textArea);
	}
}
