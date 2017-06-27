package com.springframework.security.entity;

import java.util.Date;  
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;  
import javax.persistence.Entity;  
import javax.persistence.FetchType;  
import javax.persistence.GeneratedValue;  
import javax.persistence.GenerationType;  
import javax.persistence.Id;  
import javax.persistence.JoinColumn;  
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;  
import javax.persistence.Table;  
//½ÇÉ«±í  
@Entity  
@Table(name="sys_role")  
public class SysRole {  
    @Id  
    @GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="id",length=10)  
    private int id;  
    
    private String roleName;
	private Integer isValid = 1;
	private String comments;
	private String roleType;
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<SysUser> users;  
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "sys_role_permis", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "perm_id") })  
	private Set<SysPermission> permissions;
    public int getId() {  
        return id;  
    }  
    public void setId(int id) {  
        this.id = id;  
    }  
    @Column(name = "role_name")
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "is_valid")
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	
	@Column(name = "comments")
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}	  
	
	@Column(name = "ROLE_TYPE")
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
    

	public Set<SysUser> getUsers() {
		return users;
	}
	public void setUsers(Set<SysUser> users) {
		this.users = users;
	}
	
	
	public Set<SysPermission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<SysPermission> permissions) {
		this.permissions = permissions;
	}
      
}  
