package com.github.NervousOrange.wxshop.service;

import com.github.NervousOrange.wxshop.dao.UserDao;
import com.github.NervousOrange.wxshop.generated.User;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUserIfNotExist(String tel) {
        try {
            userDao.createUserIfNotExist(tel);
        } catch (PersistenceException e) {
            logger.info("用户已经存在，无需重复创建，tel: [{}]", tel);
        }
    }

    public Optional<User> getUserByTel(String tel) {
        return Optional.ofNullable(userDao.loadUserByTel(tel));
    }
}
