package com.trgis.ticg.core.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.trgis.ticg.core.util.EnumUtil;

/**
 * 操作实体
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Entity
@Table(name="ticg_authc")
public class Authc implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**
	 * 操作名称
	 */
	@Column(name="name",length=20)
	private String name;
	
	/**
	 * 英文名称
	 */
	@Column(name="code",length=20)
	private String code;
	
	/**
	 * 状态 YQY(1, "已启用"), WQY(0, "未启用");
	 */
	@Column(name="status",length=2)
	private Integer status = EnumUtil.STATUS.WQY.getValue();
	
	/**
	 * 备注
	 */
	@Column(name="note",length=255)
	private String note;
	
	/**
	 * 菜单资源
	 */
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "menu")
	private Menu menu;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Authc() {
		super();
	}

	public Authc(String name, String code, Integer status, String note, Menu menu) {
		super();
		this.name = name;
		this.code = code;
		this.status = status;
		this.note = note;
		this.menu = menu;
	}

}
