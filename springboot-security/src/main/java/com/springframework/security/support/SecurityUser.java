package com.springframework.security.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springframework.security.entity.SysRole;
import com.springframework.security.entity.SysUser;
  
  
public class SecurityUser extends SysUser implements UserDetails {  
    private static final long serialVersionUID = 1L;  
    private List<GrantedAuthority> GrantedAuthoritys = new ArrayList<GrantedAuthority>();       //ÓÃ»§ÊÚÈ¨Âë
    public SecurityUser(SysUser suser) {  
        if(suser != null)  
        {  
            this.setId(suser.getId());  
            this.setName(suser.getName());  
            this.setEmail(suser.getEmail());  
            this.setPassword(suser.getPassword());  
            this.setDob(suser.getDob());  
            this.setSRoles(suser.getSysRoles());
        }         
    }  
      
    @Override  
    public String getPassword() {  
        return super.getPassword();  
    }

	public String getUsername() {
		// TODO Auto-generated method stub
		return super.getName();  
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		 Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();  
		 if(this.getGrantedAuthoritys()!=null && this.getGrantedAuthoritys().size()>0){
			 authorities.addAll(this.getGrantedAuthoritys());
		 }
//	        Set<SysRole> userRoles = this.getSysRoles();  
//	          
//	        if(userRoles != null)  
//	        {  
//	            for (SysRole role : userRoles) {  
//	                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());  
//	                authorities.add(authority);  
//	            }  
//	        }  
	        return authorities;  
	}  
  
	public List<GrantedAuthority> getGrantedAuthoritys() {
		return GrantedAuthoritys;
	}

	public void setGrantedAuthoritys(List<GrantedAuthority> grantedAuthoritys) {
		GrantedAuthoritys = grantedAuthoritys;
	}
}  
