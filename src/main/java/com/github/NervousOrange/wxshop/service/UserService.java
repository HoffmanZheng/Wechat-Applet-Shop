package com.github.NervousOrange.wxshop.service;

import com.github.NervousOrange.wxshop.dao.UserDao;
import com.github.NervousOrange.wxshop.generated.User;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;


    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUserIfNotExist(String tel) {
        try {
            userDao.createUserIfNotExist(tel);
        } catch (PersistenceException e) {
            System.out.println("用户已经存在");
        }
        return userDao.loadUserByTel(tel);
    }

    public Optional<User> getUserByTel(String tel) {
        return Optional.ofNullable(userDao.loadUserByTel(tel));
    }
}
