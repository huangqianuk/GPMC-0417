package com.gpmc.util;

/*
 * 这个方法就我们的app来说应该是既用来下载又用来上传，因为没有服务器，在本地的操作其实就是文件的复制。 
 * 所谓的上传和下载本质上都是将文件从一个目录copy 到另外一个目录。
 * 在上传时source由使用者选择，dest指向目标文件夹（我觉得应该跟topic名相关，xml也应该存在这里）
 * 若路径不存在会自动创建，现在设置的是同名直接覆盖，可以讨论下（文件已经存在会抛出异常，可以处理）
 * 下载时反之，dest的路径由用户指定。 * 
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;

public class FileControl {

	private String uploadFileName = null;
	public String getUploadFileName() { return uploadFileName; }
	private static void copyFile(File source, File dest) throws IOException {
		dest.getParentFile().mkdirs();
		// overwrite existing file, if exists
		CopyOption[] options = new CopyOption[] {
				StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES };
		Files.copy(source.toPath(), dest.toPath(), options);
	}
	
	public boolean uploadFile(String dest){
		JFileChooser fc = new JFileChooser("D:");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnValue = fc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			uploadFileName = fc.getSelectedFile().getName();
			File newfile = new File(dest+fc.getSelectedFile().getName());
			try {
				copyFile(fc.getSelectedFile(), newfile);
				return true;
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean downloadFile(String source){
		JFileChooser fc = new JFileChooser("D:");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = fc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File oldfile = new File(source);
			File newfile = new File(fc.getSelectedFile().getPath()+"//"+oldfile.getName());
			
			System.out.println(oldfile.getPath());
			System.out.println(newfile.getPath());
			try {
				copyFile(oldfile,newfile);
				return true;
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public static void main(String[] args) {
		FileControl c = new FileControl();
		/*
		 * 调用的时候传递String
		 */
		String source = "F://test.txt";// 选中的文件的绝对路径
		String dest = "F://a//";// 要上传的位置，一般来说应该是对应topic的目录
		c.uploadFile(dest);	
		c.downloadFile(source);
	}
}
