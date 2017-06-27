package com.springframework.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springframework.security.entity.SysResourceRole;

public interface ResourceRoleRepository extends JpaRepository<SysResourceRole,Integer>{

}
