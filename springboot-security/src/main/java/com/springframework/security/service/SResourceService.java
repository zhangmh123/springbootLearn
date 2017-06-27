package com.springframework.security.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.springframework.security.support.Appctx;
@Service("sResourceService")
public class SResourceService {
	@Resource
	private JdbcTemplate dbTemplate;
	
	private Appctx ctx;
	
	public List<Map<String,Object>> findByRoleName(String auth){
	  String sql = "select t2.* from  s_resource_role t1,s_resource t2,sys_role t3 where t1.resource_id = t2.resource_id and t1.role_id=t3.id and t3.role_name='"+auth+"'";	
	  return dbTemplate.queryForList(sql);
	};
	
	public List<Map<String,Object>> findByPermissionId(String permid){
		//JdbcTemplate dbTemplate =(JdbcTemplate)ctx.getObject("jdbcTemplate");
		String sql = "select r.* from  sys_permission p LEFT OUTER JOIN sys_perms_resc pr on p.id = pr.perm_id LEFT OUTER JOIN s_resource r on pr.res_id = r.id where p.id ="+permid;	
		  return dbTemplate.queryForList(sql);
	};
}
