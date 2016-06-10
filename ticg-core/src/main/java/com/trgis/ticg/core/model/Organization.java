package com.trgis.ticg.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trgis.ticg.core.util.EnumUtil;
/**
 * 组织机构
 * 
 * @author Zhaolei
 *
 * 2016年5月19日
 */
@Entity
@Table(name = "ticg_organization")
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	/**
	 * 组织机构名称
	 */
	@Column(name = "orgnazationname")
	private String orgnazationName;
	/**
	 * 上级组织机构
	 */
	@JsonIgnore
	@ManyToOne()//(cascade = CascadeType.DETACH)
	@JoinColumn(name = "parentOrganization")
	private Organization parentOrganization;
	/**
	 * 下级组织机构
	 */
	@OneToMany(mappedBy = "parentOrganization",/*cascade=CascadeType.ALL,*/fetch=FetchType.EAGER)
	@OrderBy("id")
	List<Organization> children = new ArrayList<Organization>();
	/**
	 * 编码
	 */
	@Column(name = "code")
	private String code;
	/**
	 * 地址
	 */
	@Column(name = "address")
	private String address;
	/**
	 * 电话
	 */
	@Column(name = "phone")
	private String phone;
	/**
	 * 邮箱
	 */
	@Column(name = "email")
	private String email;
	/**
	 * 类别
	 */
	@Column(name = "category")
	private String category;
	/**
	 * 说明
	 */
	@Column(name = "instruction")
	private String instruction;
	/**
	 * 日期
	 */
	@JsonFormat(pattern="yyyy/MM/dd",timezone="GMT+8")
	@Column(name = "date")
	private Date date =new Date();
	/**
	 * 联系人
	 */
	@Column(name = "person")
	private String person;
	/**
	 * 状态
	 */
	@Column(name = "status")
	private Integer status = EnumUtil.DELFLAG.NOMAL.getValue();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgnazationName() {
		return orgnazationName;
	}

	public void setOrgnazationName(String orgnazationName) {
		this.orgnazationName = orgnazationName;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Organization getParentOrganization() {
		return parentOrganization;
	}

	public void setParentOrganization(Organization parentOrganization) {
		this.parentOrganization = parentOrganization;
	}

	public List<Organization> getChildren() {
		return children;
	}

	public void setChildren(List<Organization> children) {
		this.children = children;
	}

	public Organization(Long id) {
		super();
		this.id = id;
	}

	public Organization() {
		super();
		// TODO Auto-generated constructor stub
	}

}
