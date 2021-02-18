package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.*;
import com.dongdongwuliu.domain.vo.TbAreasVO;
import com.dongdongwuliu.domain.vo.TbCitiesVO;
import com.dongdongwuliu.domain.vo.TbPathVO;
import com.dongdongwuliu.domain.vo.TbSpecialVO;
import com.dongdongwuliu.feign.AddressServiceFeign;
import com.dongdongwuliu.feign.PathServiceFeign;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PathController
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/2/3 0:02
 * @Version 1.0
 **/
@Controller
@RequestMapping("pathController")
@RefreshScope //开启自动刷新配置
@Api(value = "线路接口")
public class PathController {

    @Autowired
    private PathServiceFeign pathServiceFeign;

    @Autowired
    private AddressServiceFeign addressServiceFeign;

    @RequestMapping("show")
    public String show(){
        return "/admin/path/list";
    }

    //调用后台的controller 查询所有数据
    @RequestMapping("selectList")
    @ResponseBody
    public List<TbPathVO> selectList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "2") Integer pageSize){
        DataResult<List<TbPathDTO>> listDataResult = pathServiceFeign.selectList(pageNum, pageSize);
        //将调用后传递过来的结果转换成字符串类型
        List<TbPathDTO> data = listDataResult.getData();
        List<TbPathVO> dtoList = new ArrayList<>();
        for (TbPathDTO datum : data) {
            //获取开始位置省市县id
            String id = datum.getStartProvince();
            //截取省市县id
            String[] split = id.split(",");
            //分别获取省市县id
            Integer pId = Integer.parseInt(split[0]);
            Integer cId = Integer.parseInt(split[1]);
            Integer aId = Integer.parseInt(split[2]);
            // 结束位置
            String end = datum.getEndProvince();
            String[] endList = end.split(",");
            Integer pId1 = Integer.parseInt(endList[0]);
            Integer cId1 = Integer.parseInt(endList[1]);
            Integer aId1 = Integer.parseInt(endList[2]);
            //调用feign的省市县
            String startInfo = addressInfo(pId, cId, aId);
            System.out.println(startInfo);
            String endInfo = addressInfo(pId1, cId1, aId1);
            TbPathVO vo = new TbPathVO();
            vo.setStartProvince(startInfo);// 开始位置省名称
            vo.setEndProvince(endInfo);// 结束位置省名称

//            //站点
//            String site = datum.getSiteId();
//            //根据,分割
//            String[] siteList = site.split(",");
//            //省id
//            Integer pId2 = Integer.parseInt(siteList[0]);
//            //市
//            Integer cId2 = Integer.parseInt(siteList[1]);
//            //县
//            Integer aId2 = Integer.parseInt(siteList[2]);
//            String s1 = cityInfo(pId2);
//            String s2 = cityInfo(cId2);
//            String s3 = cityInfo(aId2);
//            vo.setSiteId(s1 + "-" + s2 + '-' + s3);
            vo.setId(datum.getId());
            dtoList.add(vo);

        }
        return dtoList;
    }

    /***
     * @Author gao jie
     * @Description //TODO
     * @Date 2021/2/4 15:49
     * @Param []
     * @return
     * 跳转线路增加页面
     **/
    @RequestMapping("toAddPathInfo")
    public String toAddPathInfo(Model model){
        // 查询省级站点
        DataResult<List<TbProvincesDTO>> info = addressServiceFeign.getInfo();
        List<TbProvincesDTO> data = info.getData();
        model.addAttribute("pList",data);
        return "admin/path/add";
    }


    /***
     * @Author gao jie
     * @Description //TODO
     * @Date 2021/2/4 15:49
     * @Param []
     * @return
     * 跳转线路增加页面
     **/
    @RequestMapping("toAddSpecialInfo")
    public String toAddSpecialInfo(Model model){
        // 查询省级站点
        DataResult<List<TbProvincesDTO>> info = addressServiceFeign.getInfo();
        List<TbProvincesDTO> data = info.getData();
        model.addAttribute("pList",data);
        return "admin/special/add";
    }

    // 根据省市县id查询名称
    public  String addressInfo(Integer pid,Integer cid,Integer aid){
        String str = "";
        //查询省
        DataResult<List<TbProvincesDTO>> info = addressServiceFeign.getInfo();
        List<TbProvincesDTO> data = info.getData();

        for (TbProvincesDTO datum : data) {
            //获取省编码
            Integer provinceid = Integer.parseInt(datum.getProvinceid());

            if (provinceid.equals(pid)){
                String province = datum.getProvince();
                str += province + "-";
            }
        }
        //根据省id获取市
        DataResult<List<TbCitiesDTO>> citys = addressServiceFeign.getCity(pid);
        List<TbCitiesDTO> data1 = citys.getData();
        for (TbCitiesDTO datum : data1) {
            //获取省编码
            Integer cityid = Integer.parseInt(datum.getCityid());
            if (cityid.equals(cid)){
                String city = datum.getCity();
                str += city + "-";
            }
        }
        DataResult<List<TbAreasDTO>> town = addressServiceFeign.getTown(cid);
        List<TbAreasDTO> data2 = town.getData();
        for (TbAreasDTO datum : data2) {
            //获取省编码
            Integer areaid = Integer.parseInt(datum.getAreaid());
            if (areaid.equals(aid)){
                String area = datum.getArea();
                str += area;
            }
        }
        return str;
    }

    // 根据市级id查询名称
    public String cityInfo(Integer cid){
        DataResult<TbCitiesDTO> data2 = addressServiceFeign.findCityById(cid);
        TbCitiesDTO cit = data2.getData();
       return cit.getCity();
    }


    //增加跳转
    @RequestMapping("toAdd")
    public String toAdd(){
        return "/admin/path/add";
    }

    //增加
    @RequestMapping("insertInfoPath")
    @ResponseBody
    public String insertInfoPath(TbPathVO tbPathVO){
        DataResult dataResult = pathServiceFeign.insertInfoPath(tbPathVO);
        return dataResult.getMessage();
    }


    //回显
     public String selectInfoById(@PathVariable("id") Long id, Model model) {
         DataResult<TbPathVO> tbPathVODataResult = pathServiceFeign.selectInfoById(id);
         model.addAttribute("path",tbPathVODataResult.getData());
         return "/admin/path/update";
     }


     //修改
     @RequestMapping("update")
     @ResponseBody
     public Integer update(TbPathVO tbPathVO) {
         DataResult result = pathServiceFeign.updatePathInfo(tbPathVO);

         if (result.getCode() == 200) {
             return 1;
         }
         return 2;
     }




    //删除
    @RequestMapping("deleteByPath/{id}")
    @ResponseBody
    public Integer deleteByPath(@PathVariable("id")Long id){
        DataResult dataResult = pathServiceFeign.deleteByPath(id);
        if(dataResult.getCode() == 200){
            return 1;
        }else{
            return 2;
        }
    }






    //--------------------------------------------专线表---------------------------------------------------------


    //跳转页面
    @RequestMapping("toShow")
    public String toShow(){
        return "/admin/special/list";
    }


    //获取全部
    @RequestMapping("getInfo")
    @ResponseBody
    public List<TbSpecialVO> getInfo() {
        DataResult<List<TbSpecialVO>> result = pathServiceFeign.getInfo();
        List<TbSpecialVO> list = result.getData();
        List<TbSpecialVO> dtoList = new ArrayList<>();
        for (TbSpecialVO datum : list) {
            Integer id1 = datum.getId();

            //获取省市县id
            String id = datum.getStartProvince();
            String[] split = id.split(",");
            //获取县id
            Integer pId = Integer.parseInt(split[0]);
            Integer cId = Integer.parseInt(split[1]);
            Integer aId = Integer.parseInt(split[2]);
            // 结束位置
            String end = datum.getEndProvince();
            String[] endList = end.split(",");
            //获取省市县
            Integer pId1 = Integer.parseInt(endList[0]);
            Integer cId1 = Integer.parseInt(endList[1]);
            Integer aId1 = Integer.parseInt(endList[2]);
            String startInfo = addressInfo(pId, cId, aId);
            String endInfo = addressInfo(pId1, cId1, aId1);
            TbSpecialVO vo = new TbSpecialVO();
            vo.setId(id1);
            vo.setStartProvince(startInfo);// 开始位置省名称
            vo.setEndProvince(endInfo);// 结束位置省名称
            dtoList.add(vo);

        }
        return dtoList;
    }

    /***
     * @Author gao jie
     * @Description //TODO
     * @Date 2021/2/4 16:47
     * @Param [provinceId]
     * @return
     * 根据省编号查询下属市
     **/
    @RequestMapping("findCityListByProvinceId/{provinceId}")
    @ResponseBody
    public List<TbCitiesVO> findCityListByProvinceId(@PathVariable("provinceId")Integer provinceId){
        DataResult<List<TbCitiesDTO>> data = addressServiceFeign.getCity(provinceId);
        List<TbCitiesDTO> dtoList = data.getData();
        List<TbCitiesVO> voList = new ArrayList<>();
        dtoList.forEach(dto -> {
            TbCitiesVO vo = new TbCitiesVO();
            BeanUtils.copyProperties(dto,vo);
            voList.add(vo);
        });
        return voList;
    }




    /**
     * @Author gao jie
     * @Description //TODO
     * @Date 2021/2/4 18:44
     * @Param [cityid]
     * @return 根据市编号查询下属县
     **/
    @RequestMapping("findTownListByCityid/{cityid}")
    @ResponseBody
    public List<TbAreasVO> findTownListByCityid(@PathVariable("cityid")Integer cityid){
        DataResult<List<TbAreasDTO>> data = addressServiceFeign.getTown(cityid);
        List<TbAreasDTO> dtoList = data.getData();
        List<TbAreasVO> voList = new ArrayList<>();
        dtoList.forEach(dto -> {
            TbAreasVO vo = new TbAreasVO();
            BeanUtils.copyProperties(dto,vo);
            voList.add(vo);
        });
        return voList;
    }


    //增加跳转
    @RequestMapping("toShowAdd")
    public String toShowAdd(){
        return "/admin/special/add";
    }

    //增加
    @RequestMapping("addSpecial")
    @ResponseBody
    public String addSpecial(TbSpecialVO tbSpecialVO){
        DataResult dataResult = pathServiceFeign.addSpecial(tbSpecialVO);
        return dataResult.getMessage();
    }








}

 

