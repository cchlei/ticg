package com.trgis.ticg.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 组织机构注册子系统
 * 
 * @author Zhaolei
 *
 * 2016年5月19日
 */
@Entity
@Table(name="ticg_subsystem_register")

public class SubSystemRegister implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**
	 * 组织机构
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "organization")
	private Organization organization;
	/**
	 * 子系统
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subsystem")
	private Subsystem subsystem;
	/**
	 * 开始使用时间
	 */
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@Column(name="startdate")
	private Date startDate;
	/**
	 * 结束使用时间
	 */
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@Column(name="enddate")
	private Date endDate;
	/**
	 * 状态
	 */
	@Column(name="status")
	private String status;
	/**
	 * 备注
	 */
	@Column(name="remark")
	private String remark;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public Subsystem getSubsystem() {
		return subsystem;
	}
	public void setSubsystem(Subsystem subsystem) {
		this.subsystem = subsystem;
	}

}
