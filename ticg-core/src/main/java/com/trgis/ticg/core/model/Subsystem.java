package com.trgis.ticg.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trgis.ticg.core.util.EnumUtil;
import com.trgis.ticg.core.util.EnumUtil.STATUS;

/**
 * ClassName subsystem
 * @Description: 子系统实体类
 * @author liuyan
 * @date 2016年4月9日下午10:22:26
 */
@Entity
@Table(name="ticg_subsystem")
public class Subsystem implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	/**
	 * 子系统名称
	 */
	@Column(name="name",length=20)
	private String name;
	
	/**
	 * 子系统编码（子系统英文名）
	 */
	@Column(name="code",length=50)
	private String code;
	
	/**
	 * 数据库标识（数据库的简称）
	 */
	@Column(name="database",length=20)
	private String database;
	/**
	 * 子系统的访问地址
	 */
	@Column(name="url",length=255)
	private String url;

	/**
	 * 描述信息
	 */
	@Column(name="description",length=100)
	private String description;
	/**
	 * 注册时间
	 */
	@Column(name = "createtime" )
	@JsonFormat(pattern="yyyy年MM月dd日  HH:mm:ss",timezone="GMT+8")
	private Date createTime = new Date();
	/**
	 * 是否启用
	 */
	@Column(name="status",length=4)
	private Integer status =EnumUtil.STATUS.YQY.getValue();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
    
	public Subsystem(Long id) {
		super();
		this.id = id;
	}
	public Subsystem() {
		super();
	}
	
}
