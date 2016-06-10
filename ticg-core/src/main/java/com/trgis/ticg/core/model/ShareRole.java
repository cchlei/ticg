package com.trgis.ticg.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trgis.ticg.core.util.BeanUtil;
/**
 * 共享角色实体
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Entity
@Table(name = "ticg_share_role")
public class ShareRole implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="rolename",length=20)
    private String rolename;
    
    /**
     * 所属组织机构
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization")
    private Organization organization;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
    public String getOrgname() {
    	String ret = "";
    	if(BeanUtil.isNotEmpty(this.organization)){
    		ret = this.organization.getOrgnazationName();
    	}
    	return ret;
    }
}