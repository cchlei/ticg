package com.trgis.ticg.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.SubSystemRegister;
import com.trgis.ticg.core.model.Subsystem;


@Repository
public interface SubsystemRegisterDao extends JpaRepository<SubSystemRegister, Long>,JpaSpecificationExecutor<SubSystemRegister> {

	
	/**
	 * 根据组织机构查找已注册的
	 * @param organization
	 * @return
	 */
	List<SubSystemRegister> findByOrganization(Organization organization);
	/**
	 * @Description: 根据
	 * @author yanpeng
	 * @date 2016年5月26日 下午6:06:46
	 * @param subsystem
	 * @param organization 
	 * @return
	 */
	SubSystemRegister findBySubsystemAndOrganization(Subsystem subsystem, Organization organization);

	/**
	 * @Description: 根据操作删除级联关系
	 * @author liuyan
	 * @date 2016年5月27日 下午2:43:41
	 * @param role
	 */
	@Modifying
	@Query("delete from SubSystemRegister where subsystem = ?1")
	void deleteBySubsystem(Subsystem subsystem);
	/**
	 * @Description:根据操作查询该子系统下的所有注册子系统，便于级联删除角色和注册子系统表
	 * @Author liuyan 
	 * @Date 2016年5月27日 下午4:50:25
	 * @param subsystem
	 * @return
	 */
	List<SubSystemRegister> findBySubsystem(Subsystem subsystem);
}
