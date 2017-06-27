package com.springframework.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springframework.security.entity.SysResource;

public interface ResourceRepository extends JpaRepository<SysResource,Integer>{

}
