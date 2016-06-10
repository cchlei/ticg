package com.trgis.ticg.system.core.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 系统超级管理角色及权限关系表
 * 
 * @author Alice
 *
 * 2016年5月18日
 */
@Entity
@Table(name = "ticg_sys_role_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rpid;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "sysrole")
	private SystemRole sysrole;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "sysmenu")
	private SystemMenu sysmenu;

	public Long getRpid() {
		return rpid;
	}

	public void setRpid(Long rpid) {
		this.rpid = rpid;
	}

	public SystemRole getSysrole() {
		return sysrole;
	}

	public void setSysrole(SystemRole sysrole) {
		this.sysrole = sysrole;
	}

	public SystemMenu getSysmenu() {
		return sysmenu;
	}

	public void setSysmenu(SystemMenu sysmenu) {
		this.sysmenu = sysmenu;
	}

	public SystemRoleMenu() {
		super();
	}

	public SystemRoleMenu(SystemRole sysrole, SystemMenu sysmenu) {
		super();
		this.sysrole = sysrole;
		this.sysmenu = sysmenu;
	}

}