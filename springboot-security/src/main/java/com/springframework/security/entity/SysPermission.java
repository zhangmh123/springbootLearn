package com.springframework.security.entity;

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
@Entity  
@Table(name="SYS_PERMISSION")  
public class SysPermission {
	 	@Id  
	    @GeneratedValue(strategy=GenerationType.IDENTITY)  
	    @Column (name="id",length=10)  
	    private int id;  
	 	private String permName;
		private String permCode;
		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name = "PARA_ID",referencedColumnName="id")
		private SysPermission paraId;
		private String paraCode;
		private Integer isValid = 1;
		private String comments;	
		@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
		@JoinTable(name = "sys_role_permis", joinColumns = { @JoinColumn(name = "perm_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
		private Set<SysRole> roles;
		@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
		@JoinTable(name = "sys_perms_resc", joinColumns = { @JoinColumn(name = "perm_id") }, inverseJoinColumns = { @JoinColumn(name = "res_id") })
		private Set<SysResource> resources;

		@Column(name = "PERM_NAME")
		public String getPermName() {
			return permName;
		}
		public void setPermName(String permName) {
			this.permName = permName;
		}

		@Column(name = "PERM_CODE")
		public String getPermCode() {
			return permCode;
		}
		public void setPermCode(String permCode) {
			this.permCode = permCode;
		}

		
		public SysPermission getParaId() {
			return paraId;
		}
		public void setParaId(SysPermission paraId) {
			this.paraId = paraId;
		}

		@Column(name = "PARA_CODE")
		public String getParaCode() {
			return paraCode;
		}
		public void setParaCode(String paraCode) {
			this.paraCode = paraCode;
		}

		@Column(name = "IS_VALID")
		public Integer getIsValid() {
			return isValid;
		}
		public void setIsValid(Integer isValid) {
			this.isValid = isValid;
		}

		@Column(name = "COMMENTS")
		public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}

		
		public Set<SysRole> getRoles() {
			return roles;
		}
		public void setRoles(Set<SysRole> roles) {
			this.roles = roles;
		}
		
		
		public Set<SysResource> getResources() {
			return resources;
		}
		public void setResources(Set<SysResource> resources) {
			this.resources = resources;
		}
		
		
	 	
}
