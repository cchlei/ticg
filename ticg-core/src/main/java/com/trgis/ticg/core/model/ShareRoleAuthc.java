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
@Table(name="ticg_share_role_authc")
public class ShareRoleAuthc implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "sharerole")
	private ShareRole sharerole;
	
	@ManyToOne
	@JoinColumn(name = "authc")
	private Authc authc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ShareRole getSharerole() {
		return sharerole;
	}

	public void setSharerole(ShareRole sharerole) {
		this.sharerole = sharerole;
	}

	public Authc getAuthc() {
		return authc;
	}

	public void setAuthc(Authc authc) {
		this.authc = authc;
	}

	public ShareRoleAuthc() {
		super();
	}

	public ShareRoleAuthc(ShareRole sharerole, Authc authc) {
		super();
		this.sharerole = sharerole;
		this.authc = authc;
	}

}
