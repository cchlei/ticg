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
 * 共享角色组织机构关系实体
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Entity
@Table(name = "ticg_share_role_org")
public class ShareRoleOrg implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     
    /**
     * 所属组织机构
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization")
    private Organization organization;
    
    /**
     * 共享角色
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sharerole")
    private ShareRole sharerole;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public ShareRole getSharerole() {
		return sharerole;
	}

	public void setSharerole(ShareRole sharerole) {
		this.sharerole = sharerole;
	}

}