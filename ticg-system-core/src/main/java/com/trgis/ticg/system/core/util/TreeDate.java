package com.trgis.ticg.system.core.util;

import java.util.List;

/**
 * 为了构造左侧菜单JSON权限树
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
public class TreeDate{
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 是否展开
	 */
	private boolean open= false;
	
	/**
	 * 点击后连接地址
	 */
	private String href;
	
	/**
	 * 子集
	 */
	private List<TreeDate> children;
	
	/**
	 * 是否可折叠
	 */
	private boolean collapse = true;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<TreeDate> getChildren() {
		return children;
	}

	public void setChildren(List<TreeDate> children) {
		this.children = children;
	}

	public boolean isCollapse() {
		return collapse;
	}

	public void setCollapse(boolean collapse) {
		this.collapse = collapse;
	}

}
