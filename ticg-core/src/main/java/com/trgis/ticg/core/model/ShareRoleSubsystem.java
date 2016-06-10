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
 * 共享角色注册子系统关系表
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Entity
@Table(name = "ticg_share_role_subsystem")
public class ShareRoleSubsystem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 共享角色
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sharerole")
    private ShareRole sharerole;
    
    /**
     * 关联注册子系统
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ss_register")
    private SubSystemRegister ssRegister;
    
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

	public SubSystemRegister getSsRegister() {
		return ssRegister;
	}

	public void setSsRegister(SubSystemRegister ssRegister) {
		this.ssRegister = ssRegister;
	}

	public ShareRoleSubsystem() {
		super();
	}

	public ShareRoleSubsystem(ShareRole sharerole, SubSystemRegister ssRegister) {
		super();
		this.sharerole = sharerole;
		this.ssRegister = ssRegister;
	}

}