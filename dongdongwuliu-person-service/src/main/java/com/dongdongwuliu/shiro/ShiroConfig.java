package com.dongdongwuliu.shiro;


import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Deacription TODO
 * @Author Lenovo
 * @Date 2020/12/7 15:02
 * @Version 1.0
 **/
@Configuration
public class ShiroConfig {
    //创建shiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Autowired DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro内置过滤器
        /** shiro内置过滤器,可以实现权限相关的拦截器
         * 常用的过滤器:
         *   anon:无需认证就可以访问
         *   authc:必须认证才可以访问
         *   user:如果使用rememberMe的功能可以直接访问
         *   perms:该资源必须得到资源权限才可以访问
         *   role:该资源必须得到角色权限才可以访问
         *
         **/
        //使用LinkedHashMap原因:保证我们的一个放过拦截的顺序和我们定义的时候一直  保证存取顺序
        Map<String,String> map = new LinkedHashMap<>();
        //设置可以直接访问的路径
        map.put("/**","anon");
//        map.put("/tbPerson/login","anon");//登录方法

        //权限设置
       // map.put("/user/add","perms[myadd]");
        //退出登录  一定写在authc之上
//        map.put("/auth/logout", "logout");

        //设置不可以直接访问的路径
//        map.put("/**","authc");

        //设置拦截之后应该跳转的方法
       // shiroFilterFactoryBean.setLoginUrl("/user/tologin");
        //未授权跳转的页面
       // shiroFilterFactoryBean.setSuccessUrl("/user/error");

        //通过过滤器的map集合 调用方法
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //创建DefaultWebSecurityManager
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Autowired UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }
    //创建realm
    @Bean
    public UserRealm getUserRealm(@Autowired HashedCredentialsMatcher hashedCredentialsMatcher){
        UserRealm userRealm = new UserRealm();
        //设置加密方式
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }
    //这是解密
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //1.指定加密的方式 散列算法:MD4,MD5,SHA-1,SHA-256....
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数 和 存进数据库的加密次数一致
        credentialsMatcher.setHashIterations(3);
        //设置编码 true:加密用的hex编码  使用较多  false:使用的是base64编码
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }
}
