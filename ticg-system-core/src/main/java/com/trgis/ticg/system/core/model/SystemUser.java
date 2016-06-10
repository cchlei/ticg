package com.trgis.ticg.system.core.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.trgis.ticg.core.model.Organization;

/**
 * 系统超级管理员用户列表
 * 
 * @author Alice
 *
 */
@Entity
@Table(name = "ticg_sys_user")
public class SystemUser {

	/**
	 * 主键
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String username;

	@Column
	private String salt;

	@Column
	private String password;
	
	/**
	 * 系统超级管理员角色
	 */
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "sysrole")
	private SystemRole sysrole;
	/**
	 * 无实际意义，方便查询，service层save用户时需级联修改此字段
	 */
	@Column
	private String rolename;	
	/**
	 * 所属组织机构（系统超管没有组织机构）
	 */
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "organization")
	private Organization organization;
	/**
	 * 无实际意义，方便查询，service层save用户时需级联修改此字段
	 */
	@Column
	private String organizationname;	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		// salt genrate by auto
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SystemRole getSysrole() {
		return sysrole;
	}

	public void setSysrole(SystemRole sysrole) {
		this.sysrole = sysrole;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public SystemUser() {
		super();
	}

	public SystemUser(String username, String salt, String password, SystemRole sysrole, Organization organization) {
		super();
		this.username = username;
		this.salt = salt;
		this.password = password;
		this.sysrole = sysrole;
		this.organization = organization;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}
}
