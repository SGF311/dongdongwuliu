package com.dongdongwuliu.feign.fallback;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.MenuDTO;
import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.domain.dto.TbRoleDTO;
import com.dongdongwuliu.domain.vo.TbPersonVO;
import com.dongdongwuliu.domain.vo.TbRoleVO;
import com.dongdongwuliu.feign.TbPersonServiceFeign;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

//**创建feign的实现类**  做降级处理
//在网络请求时，可能会出现异常请求，如果还想再异常情况下使系统可用，那么就需要容错处理，
// 比如:网络请求超时时给用户提示“稍后重试”或使用本地快照数据等等。
@Component
public class TbPersonServiceImplFallBack implements TbPersonServiceFeign {
    @Override
    public DataResult login(TbPersonVO tbPersonVO) {

        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }
//------------------------------------------权限表---------------------------------------------
    @Override
    public DataResult<List<Map<String, Object>>> getMenu(String key) {

        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult getUid(String key) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult<List<Map<String, Object>>> getZtree() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult insertInto(MenuDTO menu) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult<MenuDTO> getInfoById(Integer mid) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult update(MenuDTO menuDTO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult delete(Integer id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult<List<Map<String, Object>>> getZtreeInfoChecked(Integer rid) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }






//-----------------------------用户表----------------------------------------------------
    @Override
    public DataResult<List<TbPersonDTO>> getInfo() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult insertIntoPerson(TbPersonVO tPerson, Integer[] role) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult<Map<String, Object>> toupdate(Integer uid) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult updatePerson(TbPersonVO tPerson, Integer[] role) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult deletePerson(Integer uid) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult outLog(String key) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult<List<TbPersonVO>> selectByType(Integer type,Integer sid) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }
    //--------------------------------------角色表-------------------------------------------

    @Override
    public DataResult<List<TbRoleDTO>> getInfoRoles() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult insertInTo(TbRoleDTO role, Integer[] ids) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult<TbRoleVO> getInfoRoleById(Integer rid) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult updateRole(TbRoleDTO role, Integer[] ids) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }

    @Override
    public DataResult del(Integer id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,太挤了");
    }
}
