package com.example.ivt_mng_sys.entity;

import lombok.Data;

//权限
@Data
public class AuthorityInfo {
	private String authorityID;
	private String menuID;
	private String description;
	private String authorityName;
}
