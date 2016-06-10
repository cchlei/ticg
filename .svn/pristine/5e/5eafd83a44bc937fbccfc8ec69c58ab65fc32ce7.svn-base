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

/**
 * 各子系统菜单实体
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Entity
@Table(name = "ticg_menu")
public class Menu implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 名称
     */
    @Column(name="name",length=20)
    private String name;

    /**
     * 编码
     */
    @Column(name="code",length=20)
    private String code;
    
    /**
     * 操作地址
     */
    @Column(name="url",length=30)
    private String url;
    
    /**
     * 所属子系统
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subsystem")
    private Subsystem subsystem;
    

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Subsystem getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(Subsystem subsystem) {
		this.subsystem = subsystem;
	}

	public Menu() {
		super();
	}

    public Menu(String name, String code, String url, Subsystem subsystem) {
		super();
		this.name = name;
		this.code = code;
		this.url = url;
		this.subsystem = subsystem;
	}
}