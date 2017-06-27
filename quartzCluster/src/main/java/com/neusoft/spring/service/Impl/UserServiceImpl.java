package com.neusoft.spring.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.spring.dao.UserJpaDao;
import com.neusoft.spring.model.User;
import com.neusoft.spring.service.UserService;
/**
 * 
 * @ClassName UserServiceImpl
 * @author abel
 * @date 2016年11月10日
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJpaDao userJpaDao;
    /**
     * 
     * @param UserName
     * @return
     */
	public User getUserByName(String username) {
		 return userJpaDao.findByName(username);
	}

}