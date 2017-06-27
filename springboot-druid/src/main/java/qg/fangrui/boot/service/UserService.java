package qg.fangrui.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qg.fangrui.boot.dao.UserDao;
import qg.fangrui.boot.model.User;

/**
 * Created by zggdczfr on 2017/1/23.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userMapper;

    public int add(User user) {
        return userMapper.insertUser(user);
    }
    
    public User findById(Integer id) {
        return userMapper.findById(id);
    }
    
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
