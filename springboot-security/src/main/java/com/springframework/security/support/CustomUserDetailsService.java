package com.springframework.security.support;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;  
import org.springframework.security.core.userdetails.UserDetailsService;  
import org.springframework.security.core.userdetails.UsernameNotFoundException;  
import org.springframework.stereotype.Component;  

import com.springframework.security.MainApplication;
import com.springframework.security.entity.SysPermission;
import com.springframework.security.entity.SysRole;
import com.springframework.security.entity.SysUser;
import com.springframework.security.service.CommonService;
import com.springframework.security.service.SRoleService;
import com.springframework.security.service.UserService;
  
@Component  
public class CustomUserDetailsService implements UserDetailsService {  
	 private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);  
	@Autowired  //业务服务类  
    private UserService suserService;
	//@Autowired  //角色服务类  
	private Appctx ctx;

	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
		 //SysUser对应数据库中的用户表，是最终存储用户和密码的表，可自定义  
        //本例使用SysUser中的name作为用户名:  
		log.info("CustomUserDetailsService===========================begin");  
		log.info("username:"+username);  
        SysUser user = suserService.findByName(username);  
      /*  if(user != null){
        	List<SysRole> roleList = this.findRoleByUsername(username);
        	Set<SysRole> ts = new HashSet<SysRole>();  
            ts.addAll(roleList);  
        	user.setSRoles(ts);
        }*/
        if (user == null) {  
        	log.info("UserName " + username + " not found");  
            throw new UsernameNotFoundException("UserName " + username + " not found");  
        }  
        // SecurityUser实现UserDetails并将SysUser的name映射为username  
        SecurityUser seu = new SecurityUser(user);  
        List<GrantedAuthority> GrantedAuthoritys = new ArrayList<GrantedAuthority>();
        List<Integer> permissions = findPermByUsername( username);
        if (null != permissions && 0 < permissions.size()){
			for (int i=0;i<permissions.size();i++){
				GrantedAuthoritys.add(new SimpleGrantedAuthority(permissions.get(i).toString()));
			}	
		} else {
			GrantedAuthoritys.add(new SimpleGrantedAuthority("this guy has not permission give by jason!!!!,just don't let it error!!!"));
		}
        seu.setGrantedAuthoritys(GrantedAuthoritys);
        return  seu;  
	}  
  
	public List<SysRole> findRoleByUsername(String username) {
		JdbcTemplate dbTemplate =(JdbcTemplate)ctx.getObject("jdbcTemplate");
		String sql = "select r.* from sys_user as u join user_role as p on u.id = p.user_id join sys_role as r on r.id = p.role_id where u.name = ?";
		Object[] params = new Object[] { username };
		List<SysRole> list = dbTemplate.query(sql, params,new BeanPropertyRowMapper<SysRole>(SysRole.class));
		return list;
	}
	
	public List<Integer> findPermByUsername(String username) {
		JdbcTemplate dbTemplate =(JdbcTemplate)ctx.getObject("jdbcTemplate");
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT p.id FROM	SYS_PERMISSION p ");
		sql.append(" LEFT OUTER JOIN sys_role_permis rp on p.id = rp.perm_id ");
		sql.append("    LEFT OUTER JOIN sys_role  r on rp.role_id = r.id ");
		sql.append(" 	LEFT OUTER JOIN user_role ur on ur.role_id = r.id ");
		sql.append(" 	left outer join sys_user u on u.id = ur.user_id where u.name =  ?");
		Object[] params = new Object[] { username };
		List<Map<String, Object>> list = dbTemplate.queryForList(sql.toString(), params);
		List<Integer> perms = new ArrayList<Integer>(list.size());
		for(int i = 0;i < list.size();i++){
			Integer id = (Integer)list.get(i).get("id");
			perms.add(id);
		}
		return perms;
	}
  
}  