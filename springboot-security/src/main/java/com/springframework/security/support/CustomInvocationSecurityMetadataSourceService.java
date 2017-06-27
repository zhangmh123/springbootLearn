package com.springframework.security.support;

import java.util.ArrayList;  
import java.util.Collection;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Map.Entry;  
import java.util.Set;  
  
import javax.annotation.PostConstruct;  
  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.security.access.ConfigAttribute;  
import org.springframework.security.access.SecurityConfig;  
import org.springframework.security.web.FilterInvocation;  
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;  
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;  
import org.springframework.security.web.util.matcher.RequestMatcher;  
import org.springframework.stereotype.Service;  

import com.springframework.security.service.PermissionService;
import com.springframework.security.service.SResourceService;
import com.springframework.security.service.SRoleService;
  
  
/** 
 * ����ĵĵط��������ṩĳ����Դ��Ӧ��Ȩ�޶��壬��getAttributes�������صĽ���� �����ڳ�ʼ��ʱ��Ӧ��ȡ��������Դ�����Ӧ��ɫ�Ķ��塣 
 *  
 */  
@Service  
public class CustomInvocationSecurityMetadataSourceService implements  
        FilterInvocationSecurityMetadataSource {  
	 private static final Logger log = LoggerFactory.getLogger(CustomInvocationSecurityMetadataSourceService.class);  
    @Autowired  
    private SResourceService sResourceService;  
      
    //@Autowired  
    //private SRoleService sRoleService;  
    
    @Autowired  
    private PermissionService permissionService;
      
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;  
  
//    public CustomInvocationSecurityMetadataSourceService(SResourceService sres,SRoleService sR) { 
//        this.sResourceService = sres; 
//        this.sRoleService = sR; 
//        loadResourceDefine(); 
//    }  
    
    public CustomInvocationSecurityMetadataSourceService(SResourceService sres,PermissionService perm) { 
        this.sResourceService = sres; 
        this.permissionService = perm; 
        loadResourceDefine(); 
    }  
    @PostConstruct//<span style="font-family: Helvetica, Tahoma, Arial, sans-serif; font-size: 14px; line-height: 25.1875px;">  </span><span style="font-family: Helvetica, Tahoma, Arial, sans-serif; line-height: 25.1875px;"><span style="font-size:10px;">��@PostConstruct���εķ������ڷ���������Servle��ʱ�����У�����ֻ�ᱻ������ִ��һ�Ρ�PostConstruct�ڹ��캯��֮��ִ��,init()����֮ǰִ�С�</span></span></span>  
    private void loadResourceDefine() { // <span style="color:#33cc00;"> //һ��Ҫ����<span style="font-family: Arial, Helvetica, sans-serif;">@PostConstructע��</span></span>  
    //<span style="color:#33cc00;">// ��Web����������ʱ����ȡϵͳ�е�����Ȩ�ޡ�</span>  
    	log.info("loadResourceDefine===========================begin");  
    /*	List<Map<String,Object>> list =sRoleService.findAll();  
        List<String> query = new ArrayList<String>();  
        if(list!=null && list.size()>0) {  
            for(Map<String,Object> sr :list){  
                //String name = sr.get("name")    
                Object value = sr.get("role_name");  
                String name = String.valueOf(value);  
                query.add(name);  
            }  
        } */ 
    	
    	List<Map<String,Object>> list =permissionService.findAll();  
        List<String> query = new ArrayList<String>();  
        if(list!=null && list.size()>0) {  
            for(Map<String,Object> sr :list){  
                //String name = sr.get("name")    
                Object value = sr.get("id");  
                String name = String.valueOf(value);  
                query.add(name);  
            }  
        }
       // log.info("query==========================="+query);  
        //<span style="color:#33cc00;">/* 
        // * Ӧ������ԴΪkey�� Ȩ��Ϊvalue�� ��Դͨ��Ϊurl�� Ȩ�޾�����Щ��ROLE_Ϊǰ׺�Ľ�ɫ�� һ����Դ�����ɶ��Ȩ�������ʡ� 
        // * sparta 
       //  */</span>  
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();  
       // System.out.println("resourceMap1:"+resourceMap);  
        for (String auth : query) {  
            ConfigAttribute ca = new SecurityConfig(auth);  
            List<String> query1 = new ArrayList<String>();  
            //List<Map<String, Object>>  list1 = sResourceService.findByRoleName(auth);  
            List<Map<String, Object>>  list1 = sResourceService.findByPermissionId(auth);  
          //  System.out.println("list1:"+list1);  
            if(list1!=null && list1.size()>0) {  
                for(Map<String, Object> map :list1){  
                    Object value = map.get("resource_string");  
                    String url = String.valueOf(value);  
                    query1.add(url);  
                }  
            }  
           // System.out.println("query1:"+query1);  
            for (String res : query1) {  
                String url = res;  
                  
               // <span style="color:#33cc00;">/* 
               //  * �ж���Դ�ļ���Ȩ�޵Ķ�Ӧ��ϵ������Ѿ�������ص���Դurl����Ҫͨ����urlΪkey��ȡ��Ȩ�޼��ϣ���Ȩ�����ӵ�Ȩ�޼����С� 
               //  * sparta 
               //  */</span>  
                if (resourceMap.containsKey(url)) {  
  
                    Collection<ConfigAttribute> value = resourceMap.get(url);  
                    value.add(ca);  
                    resourceMap.put(url, value);  
                } else {  
                    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();  
                    atts.add(ca);  
                    resourceMap.put(url, atts);  
                }  
  
            }  
        }  
        log.info("resourceMap1:"+resourceMap);  
  
    }  
  
   
    public Collection<ConfigAttribute> getAllConfigAttributes() {  
         return new ArrayList<ConfigAttribute>();  
    }  
//<span style="color:#33cc00;">  
    // ����URL���ҵ���ص�Ȩ�����á�</span>  
 
    public Collection<ConfigAttribute> getAttributes(Object object)  
            throws IllegalArgumentException {  
    	//log.info("getAttributes ���û������url:"+object);  
       // <span style="color:#33cc00;">// object ��һ��URL�����û������url��</span>  
        FilterInvocation filterInvocation = (FilterInvocation) object;  
        // object ��һ��URL�����û������url��
        String url = filterInvocation.getRequestUrl();
        log.info("���û������ԭʼurl:" + url);
        log.info("filterInvocation.getHttpRequest():" + filterInvocation.getHttpRequest());
        int firstQuestionMarkIndex = url.indexOf("?");

        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }
        //log.info("resourceMap2:"+resourceMap);  
        log.info("�������url:" + url);
        if (resourceMap == null) {  
            loadResourceDefine();  
        }  
        Iterator<String> ite = resourceMap.keySet().iterator();  
        while (ite.hasNext()) {  
            String resURL = ite.next();  
            if (url.equals(resURL)) {
                return resourceMap.get(resURL);
            }
//             RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);  
//             if(requestMatcher.matches(filterInvocation.getHttpRequest())) {  
//                return resourceMap.get(resURL);  
//            }  
        }  
       
        return null;  
    }  
    
    public boolean supports(Class<?> arg0) {  
  
        return true;  
    }  
  
}  