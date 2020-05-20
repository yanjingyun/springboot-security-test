package com.yjy.sso.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yjy.sso.entity.SysRole;

public interface SysRoleDao extends JpaSpecificationExecutor<SysRole>, JpaRepository<SysRole, Integer> {
	
}
