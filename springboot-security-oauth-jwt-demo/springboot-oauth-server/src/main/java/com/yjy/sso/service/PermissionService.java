package com.yjy.sso.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yjy.sso.dao.SysPermissionDao;
import com.yjy.sso.dao.SysUserDao;
import com.yjy.sso.entity.SysPermission;
import com.yjy.sso.entity.SysRole;
import com.yjy.sso.entity.SysUser;

@Service
public class PermissionService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysPermissionDao sysPermissionDao;

    @Transactional
    public List<SysPermission> findByUserId(Integer userId) {
    	SysUser sysUser = sysUserDao.findById(userId).get();
    	List<SysRole> sysRoles = sysUser.getSysRoles();
    	List<Integer> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
    	return sysPermissionDao.findByIds(roleIds);
    }
}
