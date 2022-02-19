package com.example.ivt_mng_sys.entity;

import lombok.Data;

import java.util.List;

@Data
public class MenuInfo {
	private String menuID;//菜单编号
	private String menuFunctionUrl;//菜单对应功能url
	private String menuILevel;//menuILevel
	private String superMenuID;//
	private String menuName;//菜单级别，1：1级菜单。2：2级菜单
	private String menuExp;//菜单说明
	private String menuLock;//菜单锁

	private List<MenuInfo> submenu;//对于二级菜单集合
	private List<MenuInfo> submenu3;//对于三级菜单集合

	private String sign;//标志（1：有，0：代表没有）
}
