package com.dongdongwuliu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dongdongwuliu.domain.dto.TbCarDTO;
import com.dongdongwuliu.mapper.TbCarMapper;
import com.dongdongwuliu.pojo.TbCar;
import com.dongdongwuliu.service.TbCarService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Deacription TODO
 * @Author wkk
 * @Date 2021/1/30 14:42
 * @Version 1.0
 **/
@Service
public class TbCarServiceImpl implements TbCarService {
    @Resource
    private TbCarMapper carMapper;

    //增加汽车信息
    @Override
    public void insertInfoCar(TbCarDTO tbCarDTO) {
        TbCar tbCar = new TbCar();
        BeanUtils.copyProperties(tbCarDTO,tbCar);
        carMapper.insert(tbCar);
    }
    //根据id或者是车牌号删除车辆信息
    @Override
    public void deleteByIdOrCarNumber(TbCarDTO tbCarDTO) {
        TbCar tbCar = new TbCar();
        BeanUtils.copyProperties(tbCarDTO,tbCar);
        //汽车id
        Integer carId = tbCar.getCarId();
        //车牌号
        String carNumber = tbCar.getCarNumber();
        if (carId != null && !carId.equals("") && carId >0 ){
            carMapper.deleteById(carId);
        }
        if (carNumber != null && !carNumber.equals("")){
            Map<String,Object> map = new HashMap<>();
            map.put("car_number",carNumber);
            carMapper.deleteByMap(map);
        }
    }
    ////根据汽车id或者汽车车牌号进行修改
    @Override
    public void updateByIdOrCarNumber(TbCarDTO tbCarDTO) {
        TbCar tbCar = new TbCar();
        BeanUtils.copyProperties(tbCarDTO,tbCar);
        carMapper.updateById(tbCar);
    }
    //查询全部并分页
    @Override
    public Map<String, Object> selectPage(TbCarDTO tbCarDTO,Integer pageNumber, Integer pageSize) {
        TbCar tbCar = new TbCar();
        BeanUtils.copyProperties(tbCarDTO,tbCar);

        Integer carId = tbCar.getCarId();
        String carNumber = tbCar.getCarNumber();

        Page<TbCar> tbCarDTOPage = new Page<>(pageNumber,pageSize);
        QueryWrapper<TbCar> queryWrapper = new QueryWrapper<>();
        //根据条件查询并分页
        IPage<TbCar> tbCarIPage = null;
        //根据汽车id查询
        if (carId != null && !carId.equals("")){
            queryWrapper.eq("car_id",carId);
            tbCarIPage = carMapper.selectPage(tbCarDTOPage, queryWrapper);
        }
        //根据车牌号查询
        if (carNumber != null && !carNumber.equals("")){
            queryWrapper.eq("car_number",carNumber);
            tbCarIPage = carMapper.selectPage(tbCarDTOPage, queryWrapper);
        }
        //没有条件查询全部
        if (carId == null && carNumber.equals("")){
            tbCarIPage = carMapper.selectPage(tbCarDTOPage, null);
        }

        List<TbCar> list = tbCarIPage.getRecords();
        long total = tbCarIPage.getTotal();
        long pages = tbCarIPage.getPages();
        long current = tbCarIPage.getCurrent();
        long size = tbCarIPage.getSize();
        Map<String, Object> map = new HashMap<>();
        map.put("rows", list);
        map.put("total", total);
        map.put("pageNumber", current);
        map.put("pageSize", size);
        map.put("pages", pages);
        return map;
    }

    //根据汽车id或者汽车车牌号进行查询
    @Override
    public TbCarDTO selectByIdOrCarNumber(TbCarDTO tbCarDTO) {
        //汽车id
        Integer carId = tbCarDTO.getCarId();
        //车牌号
        String carNumber = tbCarDTO.getCarNumber();


        TbCar car = null;
        if (carId != null && !carId.equals("") && carId >0 ){
            car = carMapper.selectById(carId);
        }
        if (carNumber != null && !carNumber.equals("")){
            Map<String,Object> map = new HashMap<>();
            map.put("car_number",carNumber);
            List<TbCar> list = carMapper.selectByMap(map);
            car = list.get(0);
        }
        BeanUtils.copyProperties(car,tbCarDTO);
        return tbCarDTO;
    }

    //可调度车辆查询
    @Override
    public List<TbCar> carControlSelect(Integer status, Integer siteId) {
        QueryWrapper<TbCar> queryWrapper = new QueryWrapper<>();
        //查询车辆未绑定站点id  或者 为空闲状态的
        queryWrapper.eq("status",status).or().eq("site_id",siteId);
        List<TbCar> list = carMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public List<TbCarDTO> selectBySiteId(TbCarDTO tbCarDTO) {
        //站点id
        Integer siteId = tbCarDTO.getSiteId();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("site_id",siteId);
        List<TbCarDTO> list = carMapper.selectList(queryWrapper);
        return list;
    }
}