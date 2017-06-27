package com.springframework.security.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionService {
	@Resource
	private JdbcTemplate jdbcTemplate;
	public List<Map<String,Object>> findAll(){
		  String sql = "select * from  sys_permission";	
		  return jdbcTemplate.queryForList(sql);
	};
}
