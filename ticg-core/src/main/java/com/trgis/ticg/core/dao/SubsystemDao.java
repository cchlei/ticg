package com.trgis.ticg.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.Subsystem;

@Repository
public interface SubsystemDao extends JpaRepository<Subsystem, Long> ,JpaSpecificationExecutor<Subsystem>{

	Subsystem findByName(String value);

	Subsystem findByCode(String value);
	
	@Query(nativeQuery=true,value="select * from ticg_subsystem where id not in (select subsystem from ticg_subsystem_register where organization=?1)")
	public List<Subsystem> findUnRegistedSubsystems(Long organization);
}

