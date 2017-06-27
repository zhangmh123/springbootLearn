package com.springframework.security.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.springframework.security.entity.SysRole;
@Service("commonService")
public class CommonService {
	@Resource
	private JdbcTemplate jdbcTemplate;

	public List<SysRole> findRoleByUsername(String username) {
		String sql = "select r.* from s_user as u join user_role as p on u.id = p.user_id join s_role as r on r.id = p.role_id where u.name = ?";
		Object[] params = new Object[] { username };
		List<SysRole> list = this.jdbcTemplate.query(sql, params,new BeanPropertyRowMapper<SysRole>(SysRole.class));
		return list;
	}
}
