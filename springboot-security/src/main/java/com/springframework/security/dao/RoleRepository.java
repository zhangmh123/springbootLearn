package com.springframework.security.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springframework.security.entity.SysRole;


public interface RoleRepository extends JpaRepository<SysRole,Integer>{
}
