package com.yjy.system.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yjy.security.service.MyPasswordEncoder;
import com.yjy.system.entity.User;
import com.yjy.system.enums.UseStatus;
import com.yjy.system.exception.UserNameAlreadyExistException;
import com.yjy.system.repository.UserRepository;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private MyPasswordEncoder myPasswordEncoder;

    public void saveUser(Map<String, String> registerUser) {
        Optional<User> optionalUser = userRepository.findByUsername(registerUser.get("username"));
        if (optionalUser.isPresent()) {
            throw new UserNameAlreadyExistException("该用户名已存在！");
        }
        User user = new User();
        user.setUsername(registerUser.get("username"));
        user.setPassword(myPasswordEncoder.encode(registerUser.get("password")));
        user.setRoles("DEV,PM");
        user.setStatus(UseStatus.CAN_USE);
        userRepository.save(user);
    }

    public User findUserByUserName(String name) {
        return userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("不存在该用户名" + name));
    }

    public void deleteUserByUserName(String name) {
        userRepository.deleteByUsername(name);
    }


    public Page<User> getAllUser(int pageNum, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNum, pageSize));
    }
}
