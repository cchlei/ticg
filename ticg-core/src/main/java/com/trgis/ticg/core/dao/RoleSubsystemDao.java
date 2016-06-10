package com.trgis.ticg.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.RoleSubsystem;
import com.trgis.ticg.core.model.SubSystemRegister;
import com.trgis.ticg.core.model.Subsystem;

/**
 * 角色与注册子系统关系DAO
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Repository
public interface RoleSubsystemDao extends JpaRepository<RoleSubsystem, Long>,JpaSpecificationExecutor<RoleSubsystem>{

	@Query("from RoleSubsystem where ssRegister.subsystem = ?1")
	List<RoleSubsystem> findBySubsystem(Subsystem subsystem);

	@Modifying
	@Query("delete from RoleSubsystem where role=?1")
	void deleteByRole(Role role);
		
	List<RoleSubsystem> findByRole(Role role);

	@Modifying
	@Query(nativeQuery=true,value="delete from ticg_role_subsystem where role in (?1)")
	void deleteByRoles(Long[] ids);
	/**
	 * @Description: 根据注册子系统删除级联关系=角色注册子系统表
	 * @author liuyan
	 * @date 2016年5月27日 下午2:43:41
	 * @param role
	 */
	@Modifying
	@Query("delete from RoleSubsystem where ssRegister in (?1)")
	void deleteBySsRegister(List<SubSystemRegister> sysReg);
	@Modifying
	@Query("from RoleSubsystem where ssRegister= ?1")
	List<RoleSubsystem> findBySubSystemRegister(SubSystemRegister subSystemRegister);
}
