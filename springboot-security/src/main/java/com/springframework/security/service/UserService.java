package com.springframework.security.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.springframework.security.dao.UserRepository;
import com.springframework.security.entity.SysUser;
import com.springframework.security.support.Appctx;

@Service("suserService")
public class UserService {
	@Autowired
	private UserRepository userRepository;
	

	public SysUser findByName(String userName) {
		return userRepository.findByName(userName);
	}
	
	public List<SysUser> findAll() {
		return userRepository.findAll();
	}
	
	public SysUser update(SysUser user){
		return userRepository.save(user);
	};
	
	public SysUser findById(int id) {
		return userRepository.findOne(id);
	}
	
	public SysUser create(SysUser user){
		return userRepository.save(user);
	};
	
}
