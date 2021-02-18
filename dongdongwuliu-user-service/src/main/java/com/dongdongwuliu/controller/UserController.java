package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.config.IdWorker;
import com.dongdongwuliu.constant.VerificationCodeContstant;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.domain.dto.TbUserDTO;
import com.dongdongwuliu.domain.vo.TbCarVO;
import com.dongdongwuliu.domain.vo.TbPersonVO;
import com.dongdongwuliu.domain.vo.TbUserVO;
import com.dongdongwuliu.pojo.TbUser;
import com.dongdongwuliu.service.UserService;
import com.dongdongwuliu.shiroutil.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.dongdongwuliu.shiroutil.ShiroUtils.encryptPassword;
import static com.dongdongwuliu.shiroutil.ShiroUtils.generateSalt;

@RestController
@RequestMapping("user")
@Api(description = "用户登录注册接口")
@RefreshScope //开启自动刷新配置
public class UserController {
    Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IdWorker idWorker;
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "tbCarVO", value = "实体类", required = true, paramType = "body", dataTypeClass = TbCarVO.class),
//            @ApiImplicitParam(name = "pageNum", value = "页数", required = true, paramType = "query", dataTypeClass = Integer.class),
//            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataTypeClass = Integer.class)
//    })

    //登录
    @GetMapping("login")
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, paramType = "query", dataTypeClass = String.class),
    })
    public DataResult login(@RequestParam("username") String username, @RequestParam("password") String password) {

        log.info("/user/login被调用,传入参数为账号{}，密码{}", username, password);
        //判断密码是否正确
        /**使用shiro编写认证操作
         *
         **/
        //1.获取subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        //2.封装用户数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        try {
            //密码正确
            //3.执行登录方法
            subject.login(usernamePasswordToken);
            //重定向:跳转的是方法
            log.info("/login调用完成");
            Map<String, Object> map = new HashMap<>();

//            //把该用户存入redis中   把生成的id存入了redis中 传给前台 设置30分钟过期时间
            Long id = idWorker.nextId();
//            //获取登录的对象
            TbUserDTO user = (TbUserDTO) subject.getPrincipal();
//
            String s = JSONObject.toJSONString(user);
            redisTemplate.opsForValue().set(id.toString(),s,30, TimeUnit.MINUTES);


            map.put("Userkey",id.toString());
            map.put("user", s);
        //修改最后一次登录时间
            user.setLastLoginTime(new Date());
            user.setUpdated(new Date());
            userService.update(user);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(map);

        } catch (UnknownAccountException e) {
            log.error("没有该用户");
            //请求转发:跳转的是页面
            return DataResult.response(ResponseStatusEnum.USER_NOT_FOUND).setData("没有该用户");
        } catch (IncorrectCredentialsException e) {
            log.error("密码错误");
            //密码错误
            return DataResult.response(ResponseStatusEnum.PASSWORD_ERROR).setData("密码错误");
        }
    }

    //是否注册
    @GetMapping("registered")
    @ApiOperation(value = "查看用户是否注册")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataTypeClass = String.class)
    public DataResult registered(@RequestParam("username") String username) {
        log.info("/user/registered被调用,传入参数为账号{}", username);
        try {
            TbUserDTO infoByName = userService.getInfoByName(username);
            if (infoByName.getId() != null) {
                return  DataResult.response(ResponseStatusEnum.SUCCESS).setData(1);
            }

            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(0);
        } catch (Exception e) {
            log.error("/user/registered被调用结束 错误参数：{}",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }

    }

    //注册
    @PostMapping("register")
    @ApiOperation(value = "注册")
    @ApiImplicitParam(name = "tbUserVO", value = "用户名", required = true, paramType = "body", dataTypeClass = TbUserVO.class)
    public DataResult register(@RequestBody TbUserVO tbUserVO) {
        log.info("/user/register被调用,传入参数为{}", tbUserVO);
        try {
            //获取redis中的验证码 判断验证码是否 正确
            if (StringUtils.isBlank(tbUserVO.getCode())) {
                return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR).setData("请输入验证码");

            }
            String o1 = (String) redisTemplate.opsForValue().get(VerificationCodeContstant.VERIFICATION_CODE + tbUserVO.getPhone());
            if(o1.equals("")){
                return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR).setData("验证码失效");
            }


            if (!o1.equalsIgnoreCase(tbUserVO.getCode())) {
                return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR).setData("验证码不正确");
            }

            //先指定一个长度的盐,可以使用任何的字符串和数字  还也可以使用手机号
            String salt = generateSalt(6);
            //加密后的密码
            String password = encryptPassword("MD5", tbUserVO.getPassword(), salt, 3);
            tbUserVO.setPassword(password);
            tbUserVO.setSalt(salt);
            TbUserDTO tbUserDTO = new TbUserDTO();
            BeanUtils.copyProperties(tbUserVO,tbUserDTO);
            userService.insert(tbUserDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        } catch (BeansException e) {
            log.error("/user/register被调结束,错误参数{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //修改个人信息
    @PutMapping("update")
    @ApiOperation(value = "查看用户是否注册")
    @ApiImplicitParam(name = "tbUserVO", value = "用户名", required = true, paramType = "body", dataTypeClass = TbUserVO.class)
    public DataResult update(@RequestBody TbUserVO tbUserVO) {
        log.info("/user/update被调用,传入参数为{}", tbUserVO);
        try {
            TbUserDTO tbUserDTO = new TbUserDTO();
            BeanUtils.copyProperties(tbUserVO,tbUserDTO);
            userService.update(tbUserDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        } catch (BeansException e) {
            log.error("/user/update被调用结束,错误参数{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //根据id查询数据
    @GetMapping("toUpdate/{key}")
    @ApiOperation(value = "根据id查询数据")
    @ApiImplicitParam(name = "key", value = "用户id", required = true, paramType = "path", dataTypeClass = String.class)
    public DataResult toupdate(@PathVariable("key")String key) {
        log.info("/user/toUpdate被调用,传入参数为：{}", key);
        TbUserVO tbUserVO = new TbUserVO();
        try {
            String s = (String)redisTemplate.opsForValue().get(key);
            TbUser tbUser = JSONObject.parseObject(s, TbUser.class);
            if(tbUser == null){
                return DataResult.response(ResponseStatusEnum.FAIL);
            }
            TbUserDTO infoByName = userService.selectById(tbUser.getId());
            BeanUtils.copyProperties(infoByName,tbUserVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbUserVO);
        } catch (Exception e) {
            log.error("/user/toUpdate被调用结束 错误参数：{}",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }

    }


    //退出登录
    @GetMapping("outLog/{key}")
    @ApiOperation(value = "根据redis中的id 获取当前的登录对象退出登录")
    @ApiImplicitParam(name = "key", value = "redis的id", required = true, paramType = "path", dataTypeClass = String.class)
    public  DataResult outLog(@PathVariable("key") String key){
        log.info("user/outLog方法,参数为:{}",key);
        try {
            //清除key
            redisTemplate.delete(key);
            //shiro 退出
            Subject lvSubject= SecurityUtils.getSubject();
            lvSubject.logout();

        } catch (Exception e) {
            log.error("user/outLog方法结束,错误信息:{}",e);
            return  DataResult.response(ResponseStatusEnum.FAIL);
        }
        log.info("user/outLog方法结束");
        return DataResult.response(ResponseStatusEnum.SUCCESS);
    }
    // changePhone
    @GetMapping("changePhone")
    @ApiOperation(value = "查看用户是否注册手机号")
    @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataTypeClass = String.class)
    public DataResult changePhone(@RequestParam("phone") String phone) {
        log.info("/user/registered被调用,传入参数为账号{}", phone);
        try {
            TbUserDTO infoByPhone = userService.getInfoByPhone(phone);
            if (infoByPhone.getId() != null) {
                return  DataResult.response(ResponseStatusEnum.SUCCESS).setData(1);
            }

            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(0);
        } catch (Exception e) {
            log.error("/user/registered被调用结束 错误参数：{}",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }

    }
}
