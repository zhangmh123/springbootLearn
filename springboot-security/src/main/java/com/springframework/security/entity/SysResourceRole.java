package com.springframework.security.entity;

import java.util.Date;  

import javax.persistence.Column;  
import javax.persistence.Entity;  
import javax.persistence.FetchType;  
import javax.persistence.GeneratedValue;  
import javax.persistence.GenerationType;  
import javax.persistence.Id;  
import javax.persistence.JoinColumn;  
import javax.persistence.ManyToOne;  
import javax.persistence.Table;  
@Entity  
@Table(name="s_resource_role")  
public class SysResourceRole {  
        @Id  
        @GeneratedValue(strategy=GenerationType.IDENTITY)  
        @Column (name="id",length=10)  
        private int id;  
          
        @Column(name="roleId",length=50)  
        private String roleId; //��ɫID  
          
        @Column(name="resourceId",length=50)  
        private String resourceId;//��ԴID  
          
        @Column(name="updateTime")  
        private Date updateTime;//����ʱ��  
  
        public int getId() {  
            return id;  
        }  
  
        public void setId(int id) {  
            this.id = id;  
        }  
  
        public String getRoleId() {  
            return roleId;  
        }  
  
        public void setRoleId(String roleId) {  
            this.roleId = roleId;  
        }  
  
        public String getResourceId() {  
            return resourceId;  
        }  
  
        public void setResourceId(String resourceId) {  
            this.resourceId = resourceId;  
        }  
  
        public Date getUpdateTime() {  
            return updateTime;  
        }  
  
        public void setUpdateTime(Date updateTime) {  
            this.updateTime = updateTime;  
        }  
  
          
}  
