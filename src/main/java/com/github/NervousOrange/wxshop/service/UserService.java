package com.github.NervousOrange.wxshop.service;

import com.github.NervousOrange.wxshop.dao.UserDao;
import com.github.NervousOrange.wxshop.entity.User;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDao userDao;


    @Autowired
    public UserService(UserDao userDao, MockSmsCodeService mockSmsCodeService, VerificationCheckService verificationCheckService) {
        this.userDao = userDao;
    }

    public User createUserIfNotExist(String tel) {
        try {
            userDao.createUserIfNotExist(tel);
        } catch (PersistenceException e) {
            e.printStackTrace();
            System.out.println("用户已经存在");
        }
        User user = userDao.loadUserByTel(tel);
        return user;
    }
}
