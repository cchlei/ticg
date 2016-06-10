package com.trgis.ticg.system.core.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.Transient;

import com.trgis.ticg.core.util.EnumUtil;

/**
 * 系统超级管理员权限表
 * 
 * @author Alice
 *
 * 2016年5月18日
 */
@Entity
@Table(name = "ticg_sys_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemMenu implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	@Column
	private String menuname;
	
	/**
	 * 初始化时是否展开
	 */
	@Column
	private boolean isClosed = false;
	
	/**
	 * 权限链接地址，点击以后跳转的页面或方法路径
	 */
	@Column
	private String url;
	
	/**
	 * 添加了一个属性，不入库，新增角色时临时使用
	 */
	@Transient
    private String isSelected = "";
    
    /**
	 * 状态
	 */
	@Column
	private Integer delflage = EnumUtil.DELFLAG.NOMAL.getValue();

    
    
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "parent")
	private SystemMenu parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	@OrderBy("id")
	private List<SystemMenu> menus = new ArrayList<SystemMenu>(0);

	@Override
    public int hashCode() {
        return super.hashCode();
    }
	
    @Override
    public boolean equals(Object o) {
        if (o instanceof SystemMenu) {
            SystemMenu item = (SystemMenu) o;
            if (item.getId() == null && this.getId() == null) {
                return true;
            }
            if (this.getId() != null
                    && this.getId().equals(item.getId())) {
                return true;
            }
        }
        return false;
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}

	public Integer getDelflage() {
		return delflage;
	}

	public void setDelflage(Integer delflage) {
		this.delflage = delflage;
	}

	public SystemMenu getParent() {
		return parent;
	}

	public void setParent(SystemMenu parent) {
		this.parent = parent;
	}

	public List<SystemMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<SystemMenu> menus) {
		this.menus = menus;
	}

    public SystemMenu() {
		super();
	}

	public SystemMenu(String menuname, String url, SystemMenu parent) {
        super();
        this.menuname = menuname;
        this.url = url;
        this.parent = parent;
    }
}