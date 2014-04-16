package com.gpmc.util;

/*
 * ������������ǵ�app��˵Ӧ���Ǽ����������������ϴ�����Ϊû�з��������ڱ��صĲ�����ʵ�����ļ��ĸ��ơ� 
 * ��ν���ϴ������ر����϶��ǽ��ļ���һ��Ŀ¼copy ������һ��Ŀ¼��
 * ���ϴ�ʱsource��ʹ����ѡ��destָ��Ŀ���ļ��У��Ҿ���Ӧ�ø�topic����أ�xmlҲӦ�ô������
 * ��·�������ڻ��Զ��������������õ���ͬ��ֱ�Ӹ��ǣ����������£��ļ��Ѿ����ڻ��׳��쳣�����Դ���
 * ����ʱ��֮��dest��·�����û�ָ���� * 
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
		 * ���õ�ʱ�򴫵�String
		 */
		String source = "F://test.txt";// ѡ�е��ļ��ľ���·��
		String dest = "F://a//";// Ҫ�ϴ���λ�ã�һ����˵Ӧ���Ƕ�Ӧtopic��Ŀ¼
		c.uploadFile(dest);	
		c.downloadFile(source);
	}
}
