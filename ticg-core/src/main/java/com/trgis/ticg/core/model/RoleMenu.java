package com.trgis.ticg.core.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 角色菜单关系表
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Entity
@Table(name="ticg_role_menu")
public class RoleMenu implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "menu")
	private Menu menu;
	
	@ManyToOne
	@JoinColumn(name = "role")
	private Role role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public RoleMenu() {
		super();
	}

	public RoleMenu(Menu menu, Role role) {
		super();
		this.menu = menu;
		this.role = role;
	}

}
