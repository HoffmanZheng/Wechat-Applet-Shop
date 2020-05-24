package com.github.NervousOrange.wxshop.dao;

import com.github.NervousOrange.wxshop.generated.User;
import com.github.NervousOrange.wxshop.generated.UserExample;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {
    private final SqlSessionFactory sqlSessionFactory;

    @Autowired
    public UserDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void createUserIfNotExist(String tel) {
        try(SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            User user = new User();
            user.setTel(tel);
            sqlSession.insert("com.github.NervousOrange.wxshop.generated.UserMapper.insert", user);
        }
    }

    public User loadUserByTel(String tel) {
        try(SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserExample example = new UserExample();
            example.createCriteria().andTelEqualTo(tel);
            return sqlSession.selectOne("com.github.NervousOrange.wxshop.generated.UserMapper.selectByExample", example);
        }
    }
}
