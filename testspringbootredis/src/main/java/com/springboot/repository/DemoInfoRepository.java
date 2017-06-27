package com.springboot.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.bean.DemoInfo;

@Repository
@CacheConfig(cacheNames = "demoInfo")
public interface DemoInfoRepository extends JpaRepository<DemoInfo, Long> {
	// @Query("from DemoInfo u where u.id=:id")
	//DemoInfo findById(Long id);
	    @Cacheable(key = "'DemoInfoId' + #p0")  
	    DemoInfo findById(long id);  
	  
	    /** 
	     * 新增或修改时 
	     */  
	    @CachePut(key = "'DemoInfoId' + #p0.id")  
	    DemoInfo save(DemoInfo demoInfo);  
	  
	    @Transactional  
	    @Modifying  
	    @CacheEvict(key = "'DemoInfoId' + #p0")  
	    int deleteById(long id);  
}