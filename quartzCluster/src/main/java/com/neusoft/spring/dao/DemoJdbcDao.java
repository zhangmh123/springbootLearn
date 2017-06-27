package com.neusoft.spring.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.neusoft.spring.model.Demo;

@Repository
public class DemoJdbcDao {
	@Resource
	private JdbcTemplate jdbcTemplate;

	/**
	 * 
	 * 通过id获取demo对象.
	 * 
	 * @param id
	 * 
	 * @return
	 */

	public Demo getById(long id) {

		String sql = "select * from t_demo where id=?";

		RowMapper<Demo> rowMapper = new BeanPropertyRowMapper<Demo>(Demo.class);

		return jdbcTemplate.queryForObject(sql, rowMapper, id);

	}
}
