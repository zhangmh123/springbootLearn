package com.springframework.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springframework.security.entity.SysPermission;

public interface PermissionRepository extends JpaRepository<SysPermission,Integer>{

}
