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
 * 角色操作关系表——角色授权
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Entity
@Table(name="ticg_role_authc")
public class RoleAuthc implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "role")
	private Role role;
	
	@ManyToOne
	@JoinColumn(name = "authc")
	private Authc authc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Authc getAuthc() {
		return authc;
	}

	public void setAuthc(Authc authc) {
		this.authc = authc;
	}

	public RoleAuthc() {
		super();
	}

	public RoleAuthc(Role role, Authc authc) {
		super();
		this.role = role;
		this.authc = authc;
	}

}
