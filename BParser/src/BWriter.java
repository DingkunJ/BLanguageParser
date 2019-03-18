import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import java.awt.TextArea;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Panel;
import java.awt.Color;

public class BWriter {

	private JFrame frmB;
	TextArea textArea;
	private Panel panel;
	private JButton btnNewButton;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BWriter window = new BWriter();
					window.frmB.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BWriter() {
		initialize();
	}
	
	

	public JFrame getFrmB() {
		return frmB;
	}

	public void setFrmB(JFrame frmB) {
		this.frmB = frmB;
	}

	public TextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}

	public Panel getPanel() {
		return panel;
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
	}

	public JButton getBtnNewButton() {
		return btnNewButton;
	}

	public void setBtnNewButton(JButton btnNewButton) {
		this.btnNewButton = btnNewButton;
	}

	public JButton getBtnNewButton_1() {
		return btnNewButton_1;
	}

	public void setBtnNewButton_1(JButton btnNewButton_1) {
		this.btnNewButton_1 = btnNewButton_1;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmB = new JFrame();
		frmB.setIconImage(Toolkit.getDefaultToolkit().getImage(
				BWriter.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Bold-Black.png")));
		frmB.setTitle("B语言编辑器");
		frmB.setBounds(100, 100, 450, 300);
		frmB.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textArea = new TextArea();
		frmB.getContentPane().add(textArea, BorderLayout.CENTER);

		panel = new Panel();
		panel.setBackground(new Color(0, 128, 128));
		frmB.getContentPane().add(panel, BorderLayout.SOUTH);

		btnNewButton_1 = new JButton("打开B文件");
		btnNewButton_1.setIcon(new ImageIcon(BWriter.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		btnNewButton_1.setBackground(new Color(0, 128, 0));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// b后缀文件过滤器
				FileFilter bFileFilter = new BFileFilter("b", "B文件（*.b）");
				// txt后缀文件过滤器
				FileFilter txtFileFilter = new BFileFilter("txt", "TXT文件（*.txt）");
				JFileChooser jFileChooser = new JFileChooser();
				// 给jfilechoser添加b文件过滤器
				jFileChooser.addChoosableFileFilter(bFileFilter);
				// 给jfilechooser添加TXT文件过滤器
				jFileChooser.addChoosableFileFilter(txtFileFilter);
				// 打开文件选择对话框
				jFileChooser.showOpenDialog(null);
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
				BufferedReader bufferedReader = null;
				try {
					bufferedReader = new BufferedReader(new FileReader(checkFile));
					String readerString = null;
					String code = "";
					while ((readerString = bufferedReader.readLine()) != null) {
						code += readerString + "\n";
					}
					textArea.setText(code);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				} finally {
					if (bufferedReader != null) {
						try {
							bufferedReader.close();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "文件关闭失败");
						}
					} else {
						JOptionPane.showMessageDialog(null, "文件打开失败");
					}
				}
			}
		});
		panel.add(btnNewButton_1);

		btnNewButton = new JButton("保存B文件");
		btnNewButton.setIcon(new ImageIcon(BWriter.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		btnNewButton.setBackground(new Color(0, 128, 0));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				BFileFilter bFileFilter = new BFileFilter(".b", "B文件（*.b）");
				// 给jfilechoser添加b文件过滤器
				jFileChooser.addChoosableFileFilter(bFileFilter);
				// 给jfilechooser添加TXT文件过滤器

				// jFileChooser.addChoosableFileFilter(txtFileFilter);
				int fileresult = jFileChooser.showSaveDialog(null);

				if (fileresult == JFileChooser.APPROVE_OPTION) {
					// 获取选择的文件后缀
					BFileFilter bFileFilter2 = (BFileFilter) jFileChooser.getFileFilter();
					String fileExtension = bFileFilter2.getFileExtension();
					// 获取选择的文件
					File file = jFileChooser.getSelectedFile();
					File saveFile = null;
					// 判断文件后缀是否满足
					if (file.getAbsolutePath().toUpperCase().endsWith(fileExtension.toUpperCase())) {
						saveFile = file;
					} else {
						saveFile = new File(file.getAbsolutePath() + fileExtension);
					}
					// 保存文件
					FileWriter fileWriter = null;
					try {
						fileWriter = new FileWriter(saveFile);
						fileWriter.write(textArea.getText());
						// fileWriter.close();
						// JOptionPane.showMessageDialog(null, "文件保存成功");
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage());
					} finally {
						if (fileWriter != null) {
							try {
								fileWriter.close();
								JOptionPane.showMessageDialog(null, "文件保存成功");
							} catch (Exception e3) {
								JOptionPane.showMessageDialog(null, "文件关闭失败");
							}
						} else {
							JOptionPane.showMessageDialog(null, "文件保存失败");
						}
					}
				}
			}

		});
		panel.add(btnNewButton);
	}

}
