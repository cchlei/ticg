package com.trgis.ticg.system.core.util;

import java.io.Serializable;
/**
 * 创建系统角色时构造Ztree
 * @author Alice
 *
 * 2016年5月25日
 */
@SuppressWarnings("serial")
public class MenuJson implements Serializable {
	Long menuId;
	String menuName;
	String parentId;
	boolean checked=false;
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public MenuJson() {
		super();
	}
	public MenuJson(Long menuId, String menuName, String parentId) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
		this.parentId = parentId;
	}
}
