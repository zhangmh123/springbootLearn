package com.springframework.security.entity;

import java.util.List;

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
import javax.persistence.Table;  
@Entity  
@Table(name="s_resource")  
public class SysResource {  
        @Id  
        @GeneratedValue(strategy=GenerationType.IDENTITY)  
        @Column (name="id",length=10)  
        private int id;  
          
        @Column(name="resourceString",length=1000)  
        private String resourceString;//url  
          
        @Column(name="resourceId",length=50)  
        private String resourceId;//资源ID  
          
        @Column(name="remark",length=200)  
        private String remark;//备注  
          
        @Column(name="resourceName",length=400)  
        private String resourceName;//资源名称  
          
        @Column(name="methodName",length=400)  
        private String methodName;//资源所对应的方法名  
          
        @Column(name="methodPath",length=1000)  
        private String methodPath;//资源所对应的包路径  
        @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    	@JoinTable(name = "sys_perms_resc", joinColumns = { @JoinColumn(name = "res_id") }, inverseJoinColumns = { @JoinColumn(name = "perm_id") })
        private List<SysPermission> permissions;
          
        public int getId() {  
            return id;  
        }  
  
        public void setId(int id) {  
            this.id = id;  
        }  
  
        public String getResourceString() {  
            return resourceString;  
        }  
  
        public void setResourceString(String resourceString) {  
            this.resourceString = resourceString;  
        }  
  
        public String getResourceId() {  
            return resourceId;  
        }  
  
        public void setResourceId(String resourceId) {  
            this.resourceId = resourceId;  
        }  
  
        public String getRemark() {  
            return remark;  
        }  
  
        public void setRemark(String remark) {  
            this.remark = remark;  
        }  
  
        public String getResourceName() {  
            return resourceName;  
        }  
  
        public void setResourceName(String resourceName) {  
            this.resourceName = resourceName;  
        }  
  
        public String getMethodName() {  
            return methodName;  
        }  
  
        public void setMethodName(String methodName) {  
            this.methodName = methodName;  
        }  
  
        public String getMethodPath() {  
            return methodPath;  
        }  
  
        public void setMethodPath(String methodPath) {  
            this.methodPath = methodPath;  
        }  
          
       
    	public List<SysPermission> getPermissions() {
    		return permissions;
    	}
    	public void setPermissions(List<SysPermission> permissions) {
    		this.permissions = permissions;
    	}   
}  
