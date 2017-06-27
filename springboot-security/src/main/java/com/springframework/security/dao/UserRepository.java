package com.springframework.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springframework.security.entity.SysUser;

public interface UserRepository extends JpaRepository<SysUser,Integer>{
	@Query(" select u from SysUser u where u.name=?1 and password=?2")
	SysUser login(String username,String password);
	
	@Query(" select u from SysUser u where u.name=?1 ")
	SysUser findByName(String username);
	
}
