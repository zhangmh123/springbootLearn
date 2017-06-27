package com.springframework.security.entity;

import java.util.Date;  
import java.util.HashSet;  
import java.util.Set;  
  
import javax.persistence.CascadeType;
import javax.persistence.Column;  
import javax.persistence.Entity;  
import javax.persistence.FetchType;  
import javax.persistence.GeneratedValue;  
import javax.persistence.GenerationType;  
import javax.persistence.Id;  
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;  
import javax.persistence.Table;  
import javax.persistence.Temporal;  
import javax.persistence.TemporalType;  
    @Entity  
    @Table(name = "sys_user")//code11  
    public class SysUser implements java.io.Serializable {  
          
        @Id  
        @GeneratedValue(strategy = GenerationType.IDENTITY)  
        @Column(name = "id", unique = true, nullable = false)  
        private Integer id;  
        @Column(name = "name", length = 120)  
        private String name; //�û���  
        @Column(name = "email", length = 50)  
        private String email;//�û�����  
        @Column(name = "password", length = 120)  
        private String password;//�û�����  
        @Temporal(TemporalType.DATE)  
        @Column(name = "dob", length = 10)  
        private Date dob;//ʱ��  
          
        //@OneToMany(fetch = FetchType.LAZY, mappedBy = "SUser")  
        @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
        private Set<SysRole> SysRoles = new HashSet<SysRole>(0);// ����Ӧ�Ľ�ɫ����  
  
        public SysUser() {  
        }  
  
        public SysUser(String name, String email, String password, Date dob, Set<SysRole> SysRoles) {  
            this.name = name;  
            this.email = email;  
            this.password = password;  
            this.dob = dob;  
            this.SysRoles = SysRoles;  
        }  
  
          
        public Integer getId() {  
            return this.id;  
        }  
  
        public void setId(Integer id) {  
            this.id = id;  
        }  
  
          
        public String getName() {  
            return this.name;  
        }  
  
        public void setName(String name) {  
            this.name = name;  
        }  
  
        public String getEmail() {  
            return this.email;  
        }  
  
        public void setEmail(String email) {  
            this.email = email;  
        }  
  
        public String getPassword() {  
            return this.password;  
        }  
  
        public void setPassword(String password) {  
            this.password = password;  
        }  
  
          
        public Date getDob() {  
            return this.dob;  
        }  
  
        public void setDob(Date dob) {  
            this.dob = dob;  
        }  
    	
    	 public Set<SysRole> getSysRoles() {  
            return this.SysRoles;  
        } 
    	public void setSRoles(Set<SysRole> SysRoles) {  
            this.SysRoles = SysRoles;  
        } 
       // @OneToMany(fetch = FetchType.EAGER, mappedBy = "SUser")  
     /*   public Set<SysRole> getSysRoles() {  
            return this.SysRoles;  
        }  
  
        public void setSRoles(Set<SysRole> SysRoles) {  
            this.SysRoles = SysRoles;  
        }  */
  
}  