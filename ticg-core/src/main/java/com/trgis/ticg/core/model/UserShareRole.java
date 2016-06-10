package com.trgis.ticg.core.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author majl
 * @Description 用户共享角色关系
 * @data 2016年5月27日
 */
@Entity
@Table(name="ticg_user_sharerole")
public class UserShareRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	private User user;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="shareroleorg")
    private ShareRoleOrg shareRoleOrg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ShareRoleOrg getShareRoleOrg() {
		return shareRoleOrg;
	}

	public void setShareRoleOrg(ShareRoleOrg shareRoleOrg) {
		this.shareRoleOrg = shareRoleOrg;
	}
	
    
}
