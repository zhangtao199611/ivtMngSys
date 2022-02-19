package com.example.ivt_mng_sys.entity;

import lombok.Data;

@Data
public class ClassInfo {
	private String classID;
	private String className;
	private int classLevel;
	private String superClassID;
	private int status;
	private int showStatus;
	private int sortValue;
}
