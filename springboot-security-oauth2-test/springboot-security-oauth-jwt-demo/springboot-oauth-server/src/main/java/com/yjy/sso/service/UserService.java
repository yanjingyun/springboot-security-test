package com.yjy.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yjy.sso.dao.SysUserDao;
import com.yjy.sso.entity.SysUser;

@Service
public class UserService {

    @Autowired
    private SysUserDao sysUserRepository;

    public SysUser getByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }
}
