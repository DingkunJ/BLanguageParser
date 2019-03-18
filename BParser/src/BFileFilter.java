import java.io.File;

import javax.swing.filechooser.FileFilter;

/*
 * 徐浩文
 * 文件后缀过滤
 * 参考网址：http://blog.csdn.net/bailyzheng/article/details/8437648
 */
public class BFileFilter extends FileFilter {
	// 文件后缀
	private String fileExtension;
	// 描述文字
	private String description;

	public BFileFilter() {
		// TODO Auto-generated constructor stub
	}

	public BFileFilter(String fileExtension, String description) {
		super();
		// 设置文件后缀
		this.fileExtension = fileExtension;
		// 设置文件后缀描述
		this.description = description;
	}

	/**
	 * 用于选择过滤的文件，除了指定文件其余文件不显示
	 */
	@Override
	public boolean accept(File pathname) {
		// TODO Auto-generated method stub
		if (pathname.isDirectory())
			return true;
		String fileName = pathname.getName();
		if (fileName.toUpperCase().endsWith(this.fileExtension.toUpperCase()))
			return true;
		return false;
	}

	// 返回文件后缀
	public String getFileExtension() {
		return fileExtension;
	}

	// 设置文件后缀
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	// 获取文件后缀描述
	public String getDescription() {
		return description;
	}

	// 设置文件后缀描述
	public void setDescription(String description) {
		this.description = description;
	}

}
