package com.neusoft.spring.service;

import com.neusoft.spring.model.User;


/**
 * The Interface UserService.
 */
public interface UserService {

    /**
     * Gets the user by name.
     *
     * @param username the user name
     * @return the user by name
     */
    public User getUserByName(String username);
}