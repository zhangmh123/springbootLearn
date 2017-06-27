package com.springboot.mongo.common;

import java.util.List;

/** 
 * ��ҳ 
 * @author zhengcy 
 * 
 * @param<T> 
 */  
public class PageModel<T>{  
    //�����   
   private List<T> datas;   
   //��ѯ��¼��   
   private int rowCount;   
   //ÿҳ����������   
   private int pageSize=20;  
   //�ڼ�ҳ   
   private int pageNo=1;  
   //����������  
   private int skip=0;   
   /** 
     * ��ҳ�� 
     * @return 
     */   
   public int getTotalPages(){   
        return(rowCount+pageSize-1)/pageSize;   
   }  
     
   public List<T>getDatas() {  
      return datas;  
   }  
   public void setDatas(List<T> datas) {  
      this.datas = datas;  
   }  
   public int getRowCount() {  
      return rowCount;  
   }  
   public void setRowCount(int rowCount) {  
      this.rowCount = rowCount;  
   }  
   public int getPageSize() {  
      return pageSize;  
   }  
   public void setPageSize(int pageSize) {  
      this.pageSize = pageSize;  
   }  
   public int getSkip() {  
      skip=(pageNo-1)*pageSize;  
      return skip;  
   }  
   public void setSkip(int skip) {  
      this.skip = skip;  
   }  
   
   public int getPageNo() {  
      return pageNo;  
   }  
   
   public void setPageNo(int pageNo) {  
      this.pageNo = pageNo;  
   }   
    
     
     
}  