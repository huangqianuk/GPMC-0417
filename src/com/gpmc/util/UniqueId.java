package com.gpmc.util;
/*
 * This class is used to generate unique id(16bits) 
 * If this is not working well we can try to use java.util.UUID
 */
import java.util.Random;

public class UniqueId {	
	@SuppressWarnings("unused")
	private String id;
	
	public String getId() {
		Long h = System.currentTimeMillis();// ��õ�ǰʱ��ĺ�����
		String str = h.toString();// ת��Ϊ�ַ���
		int i = str.length();
		int j = i - 7;// ����ȡ���ַ�����ĩβ7λ������Ϊǰ����������ʲô�Ļ������䣬����ֻ�ú����7λ
		String body = str.substring(j, j + 3);// ȡ���ַ�����ĩβ7λ����ǰ3λ
		String tail = str.substring(j + 3, i);// ȡ���ַ�����ĩβ7λ���ĺ�4λ
		// ��26λ��ĸ��������
		String[] arr = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "g", "k",
				"l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
				"x", "y", "z" };
		String[] target = new String[3];
		// ����ĸ�������ȡ3����ĸ���һ���ַ�����һ�����3���ַ����ŵ�Ŀ������target��
		for (int s = 0; s < 3; s++) {
			Random random = new Random();
			int a = random.nextInt(arr.length);
			int b = random.nextInt(arr.length);
			int c = random.nextInt(arr.length);
			target[s] = arr[a] + arr[b] + arr[c];
		}
		return target[0] + body + target[1] + tail + target[2];// ��3����ĸ�����������ֶ����������ID
	}

//	public static void main(String[] args) {
//		UniqueId u = new UniqueId();
//		System.out.println(u.getId());
//	}
}