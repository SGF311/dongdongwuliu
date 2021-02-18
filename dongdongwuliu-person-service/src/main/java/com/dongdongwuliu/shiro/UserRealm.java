package com.dongdongwuliu.shiro;


import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.service.TbPersonService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @Deacription TODO
 * @Author Lenovo
 * @Date 2020/12/7 14:58
 * @Version 1.0
 **/
public class UserRealm  extends AuthorizingRealm {
    @Autowired
    private TbPersonService personService;
    //执行授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //从数据库中查找该用户对于权限的一个权限字段的字符串 或许会有多个是一个  list
        String perms = "myadd";
        List<String> list = Arrays.asList("myadd");
        //将集合放进 方法中
        authorizationInfo.addStringPermissions(list);
        return authorizationInfo;
    }
//执行认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");

        //编写shiro判断逻辑,判断用户名和密码
        // 获取token对象  UsernamePasswordToken实现了HostAuthenticationToken,HostAuthenticationToken继承了authenticationToken
        //属于向下转型  所有要强转一下
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        //获取数据库的用户名和密码
        TbPersonDTO person = personService.getInfoByName(token.getUsername());
        //1.判断用户名是否存在  如果用户不存在  只要return null,shiro底层会抛出一个异常在controller 进行try  catch
        //2.判断密码是否正确 直接调用shiro的SimpleAuthenticationInfo方法 进行判断
        // 共三个参数 第一个 数据库实体类对象 第二个 前端传过来的密码 第三个 通过用户表获取的盐  第四个 一般是用户的真实姓名
        return person ==  null ? null : new SimpleAuthenticationInfo(person,person.getUpwd(), ByteSource.Util.bytes(person.getSalt()),person.getUname());
    }


}
