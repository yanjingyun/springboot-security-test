package com.yjy.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yjy.sso.dao.SysUserDao;
import com.yjy.sso.entity.SysUser;

/**
 * @author ChengJianSheng
 * @date 2019-02-12
 */
@Service
public class UserService {

    @Autowired
    private SysUserDao sysUserRepository;

    public SysUser getByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }
}
