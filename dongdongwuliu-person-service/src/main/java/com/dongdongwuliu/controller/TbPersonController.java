package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.config.IdWorker;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.domain.vo.TbCarVO;
import com.dongdongwuliu.pojo.TbPerson;
import com.dongdongwuliu.pojo.TbRole;
import com.dongdongwuliu.service.PersonRoleService;
import com.dongdongwuliu.service.TbPersonService;
import com.dongdongwuliu.service.TbRoleService;
import com.dongdongwuliu.domain.vo.TbPersonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.dongdongwuliu.shiroutil.ShiroUtils.encryptPassword;
import static com.dongdongwuliu.shiroutil.ShiroUtils.generateSalt;

/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2021/1/29 21:39
 * @Version 1.0
 **/

@RestController
@RequestMapping("tbPerson")
@Api(description = "用户接口")
@RefreshScope //开启自动刷新配置
public class TbPersonController {
    Logger log  = LoggerFactory.getLogger(TbPersonController.class);
    @Autowired
    private TbPersonService tbPersonService;
    @Autowired
    private TbRoleService tbRoleService;
    @Autowired
    private PersonRoleService personRoleService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IdWorker idWorker;

    @ApiOperation(value = "登录方法")
    @ApiImplicitParam(name = "tbPerson", value = "实体类", required = true, paramType = "body", dataTypeClass = TbPerson.class)
    @PostMapping("/login")
    public DataResult selectById(@RequestBody TbPersonVO tbPerson) {
        log.info("/tbPerson/login被调用,传入参数为" + tbPerson);
        //判断密码是否正确
        /**使用shiro编写认证操作
         *
         **/
        //1.获取subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        //2.封装用户数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(tbPerson.getUname(), tbPerson.getUpwd());
        try {
            //密码正确
            //3.执行登录方法
            subject.login(usernamePasswordToken);
            //重定向:跳转的是方法
            log.info("/login调用完成");
            Map<String,Object> map = new HashMap<>();

            //把该用户存入redis中   把生成的id存入了redis中 传给前台 设置30分钟过期时间
            Long id = idWorker.nextId();
            //获取登录的对象
            TbPersonDTO user = (TbPersonDTO)subject.getPrincipal();

            String s = JSONObject.toJSONString(user);
            redisTemplate.opsForValue().set(id.toString(),s,30, TimeUnit.MINUTES);


            map.put("key",id.toString());
            map.put("TbPersonDTO",s);

            return  DataResult.response(ResponseStatusEnum.SUCCESS).setData(map);

        } catch (UnknownAccountException e) {
            log.error("没有该用户");
            //请求转发:跳转的是页面
            return   DataResult.response(ResponseStatusEnum.USER_NOT_FOUND);
        } catch (IncorrectCredentialsException e) {
            log.error("密码错误");
            //密码错误
            return DataResult.response(ResponseStatusEnum.PASSWORD_ERROR);
        }
    }

    //获取用户表的全部
    @GetMapping("findAll")
    @ApiOperation(value = "获取用户表全部数据")
    public DataResult findAll(){
        log.info("正在执行person/findAll方法");
        try {
            List<TbPerson> list = tbPersonService.getInfo();
            log.info("执行person/findAll方法结束，返回值{}",list);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(list);
        } catch (Exception e) {
            log.error("person/findAll方法结束,错误信息:{}",e);
            return  DataResult.response(ResponseStatusEnum.FAIL);
        }
    }


    //增加数据
    @PostMapping("insertInto")
    @ApiOperation(value = "增加用户表数据")
    @ApiImplicitParam(name = "tPerson", value = "实体类", required = true, paramType = "body", dataTypeClass = TbPersonVO.class)
    public DataResult insertInto(@RequestBody  TbPersonVO tPerson, @RequestParam Integer[] roles){
        log.info("正在执行person/insertInto方法,参数{}，{}",tPerson,roles);
        try {
            String  pwd = tPerson.getUpwd();
            //先指定一个长度的盐,可以使用任何的字符串和数字  还也可以使用手机号
            String salt = generateSalt(6);
            //加密后的密码
            String password = encryptPassword("MD5", pwd, salt, 3);
            //给对象赋值  密码
            tPerson.setUpwd(password);
            //盐
            tPerson.setSalt(salt);

            TbPersonDTO tbPersonDTO = new TbPersonDTO();
            BeanUtils.copyProperties(tPerson,tbPersonDTO);
            tbPersonService.insertInto(tbPersonDTO,roles);
            log.info("person/insertInto方法结束");
            return  DataResult.response(ResponseStatusEnum.SUCCESS);
        } catch (Exception e) {
            log.error("person/insertInto方法结束,错误信息:{}",e);
            return  DataResult.response(ResponseStatusEnum.FAIL);
        }
    }



    //根据id查询数据 并且 获取所有的角色
    @GetMapping("toupdate/{uid}")
    @ApiOperation(value = "根据用户id查询数据 并且 获取所有的角色")
    @ApiImplicitParam(name = "uid", value = "用户id", required = true, paramType = "path", dataTypeClass = Integer.class)
    public  DataResult toupdate(@PathVariable("uid") Integer uid){
        log.info("person/toupdate方法,参数为:{}",uid);
        Map<String,Object>  map = new HashMap<>();
        try {

            //获取uid对应的用户
            TbPerson tbPerson = tbPersonService.getInfoByUid(uid);
//        PersonRoleMapper 通过uid 获取 rid
            List<Integer> roleid = personRoleService.getInFoByUidGetRid(uid);

            //获取所有的角色
            List<TbRole> roles =  tbRoleService.findAll();

            map.put("person",tbPerson);
            map.put("roleid",roleid);
            map.put("roles",roles);
        } catch (Exception e) {
            log.error("person/toupdate方法结束,错误信息:{}",e);
            return  DataResult.response(ResponseStatusEnum.FAIL);
        }
        log.info("person/toupdate方法结束,返回值{}",map);
        return DataResult.response(ResponseStatusEnum.SUCCESS).setData(map);
    }

    //修改用户表
    @PutMapping("update")
    @ApiOperation(value = "根据用户id查询数据 并且 获取所有的角色")
    @ApiImplicitParam(name = "tPerson", value = "实体类", required = true, paramType = "body", dataTypeClass = TbPersonVO.class)
    public DataResult update(@RequestBody  TbPersonVO tPerson, @RequestParam Integer[] role){
        log.info("person/update方法,参数为{},{}",tPerson,role);
        try {
            TbPersonDTO tbPersonDTO = new TbPersonDTO();
            BeanUtils.copyProperties(tPerson,tbPersonDTO);
            tbPersonService.update( tbPersonDTO,role);
            log.info("person/update方法结束,");
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        } catch (Exception e) {
            log.error("person/update方法结束,错误信息:{}",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //删除用户表
    @DeleteMapping("delete/{uid}")
    @ApiOperation(value = "根据用户id查询数据 并且 获取所有的角色")
    @ApiImplicitParam(name = "uid", value = "用户id", required = true, paramType = "path", dataTypeClass = Integer.class)
    public DataResult delete(@PathVariable("uid") Integer uid){
        log.info("person/delete方法,参数{}",uid);
        try {
            tbPersonService.delete(uid);
            log.info("person/delete方法结束");
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        } catch (Exception e) {
            log.error("person/delete方法结束,错误信息:{}",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //根据redis中的key   获取当前的登录对象
    @GetMapping("getUid/{key}")
    @ApiOperation(value = "根据redis中的id 获取当前的登录对象")
    @ApiImplicitParam(name = "key", value = "redis的id", required = true, paramType = "path", dataTypeClass = String.class)
    public  DataResult getUid(@PathVariable("key") String key){
        log.info("person/getUid方法,参数为:{}",key);

        TbPersonVO tbPersonVO = new TbPersonVO();
        try {
            //获取uid对应的用户  这个id是redis中的key
            String s = (String)redisTemplate.opsForValue().get(key);
            TbPersonDTO tbPersonDTO = JSONObject.parseObject(s, TbPersonDTO.class);
            BeanUtils.copyProperties(tbPersonDTO,tbPersonVO);
        } catch (Exception e) {
            log.error("person/getUid方法结束,错误信息:{}",e);
            return  DataResult.response(ResponseStatusEnum.FAIL);
        }
        log.info("person/getUid方法结束,返回值{}",tbPersonVO);
        return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbPersonVO);
    }



    @GetMapping("outLog/{key}")
    @ApiOperation(value = "根据redis中的id 获取当前的登录对象退出登录")
    @ApiImplicitParam(name = "key", value = "redis的id", required = true, paramType = "path", dataTypeClass = String.class)
    public  DataResult outLog(@PathVariable("key") String key){
        log.info("person/outLog方法,参数为:{}",key);
        try {
            //清除key
            redisTemplate.delete(key);
            //shiro 退出
            Subject lvSubject= SecurityUtils.getSubject();
            lvSubject.logout();

        } catch (Exception e) {
            log.error("person/outLog方法结束,错误信息:{}",e);
            return  DataResult.response(ResponseStatusEnum.FAIL);
        }
        log.info("person/outLog方法结束");
        return DataResult.response(ResponseStatusEnum.SUCCESS);
    }


    //根据站点id  type id
    @GetMapping("selectByType/{type}/{sid}")
    @ApiOperation(value = "根据type查询属于sid的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "type", required = true, paramType = "path", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "sid", value = "sid", required = true, paramType = "path", dataTypeClass = Integer.class)
    })
    public  DataResult outLog(@PathVariable("type") Integer type,@PathVariable("sid") Integer sid){
        log.info("person/selectByType方法,参数为:{},和sid:{}",type,sid);
        try {
            List<TbPersonDTO> list = tbPersonService.selectByType(type,sid);
            String s = JSONObject.toJSONString(list);
            List<TbPersonVO> listTbPersonVO = JSONObject.parseObject(s, List.class);
            log.info("person/selectByType方法结束");
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(listTbPersonVO);
        } catch (Exception e) {
            log.error("person/selectByType方法结束,错误信息:{}",e);
            return  DataResult.response(ResponseStatusEnum.FAIL);
        }

    }

}
