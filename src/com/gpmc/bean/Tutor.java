package com.gpmc.bean;

import com.gpmc.interf.TutorInterface;
import com.gpmc.util.FileControl;

public class Tutor extends User implements TutorInterface {
	public Tutor() {
		super();
	}

	@Override
	public void postMaterial(String dest) {
		FileControl fileController = new FileControl();
		fileController.uploadFile(dest);
	}
	
	

}
