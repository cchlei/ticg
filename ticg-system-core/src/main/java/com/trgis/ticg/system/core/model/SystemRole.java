package com.trgis.ticg.system.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trgis.ticg.core.util.EnumUtil;
import com.trgis.ticg.core.util.EnumUtil.STATUS;

/**
 * 系统超级管理员角色
 * 
 * @author Alice
 *
 * 2016年5月18日
 */
@Entity
@Table(name = "ticg_sys_role")
public class SystemRole implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="rolename",length=20)
    private String rolename;
    
    @Column(name="remarks",length=200)
    private String remarks;
    
    @Column
    private Integer status = EnumUtil.STATUS.YQY.getValue();
    
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
    public String getStatusStr() {
        String ret = "";
		for (STATUS status : STATUS.values()) {
			if (status.getValue() == this.status) {
				ret = status.getName();
			}
		}
        return ret;
    }
}