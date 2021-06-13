package com.github.NervousOrange.wxshop.service;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

// 在某个区域内进行验权 鉴权
public class ShiroRealm extends AuthorizingRealm {

    private final VerificationCheckService verificationCheckService;

    @Autowired
    public ShiroRealm(VerificationCheckService verificationCheckService) {
        this.verificationCheckService = verificationCheckService;
        // 匹配身份信息的原则 token 用户提交的身份信息  info 用户真正的身份信息
        this.setCredentialsMatcher((token, info) ->
                new String((char[]) token.getCredentials()).equals(info.getCredentials()));
    }


    @Override
    // 验权：验证某个人的权限
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 鉴权：验证某个人的身份
     *
     * @param token 用户的 token 身份信息
     * @return 这个用户正确的身份信息
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String tel = (String) token.getPrincipal();
        String correctCode = verificationCheckService.getCredentials(tel);
        return new SimpleAuthenticationInfo(tel, correctCode, getName());
    }
}
