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
		Long h = System.currentTimeMillis();// 获得当前时间的毫秒数
		String str = h.toString();// 转化为字符串
		int i = str.length();
		int j = i - 7;// 用来取此字符串的末尾7位数，因为前面的数是年份什么的基本不变，我们只用后面的7位
		String body = str.substring(j, j + 3);// 取此字符串的末尾7位数的前3位
		String tail = str.substring(j + 3, i);// 取此字符串的末尾7位数的后4位
		// 将26位字母做成数组
		String[] arr = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "g", "k",
				"l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
				"x", "y", "z" };
		String[] target = new String[3];
		// 将字母数组随机取3个字母组成一个字符串，一共组成3个字符串放到目标数组target中
		for (int s = 0; s < 3; s++) {
			Random random = new Random();
			int a = random.nextInt(arr.length);
			int b = random.nextInt(arr.length);
			int c = random.nextInt(arr.length);
			target[s] = arr[a] + arr[b] + arr[c];
		}
		return target[0] + body + target[1] + tail + target[2];// 将3个字母段与两个数字段组合输出随机ID
	}

//	public static void main(String[] args) {
//		UniqueId u = new UniqueId();
//		System.out.println(u.getId());
//	}
}