package com.dongdongwuliu.service;

import com.dongdongwuliu.domain.dto.MenuDTO;
import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.pojo.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {
    List<Map<String, Object>> getMenu(TbPersonDTO person);

    List<Map<String, Object>> getZtree();

    void insertInto(MenuDTO menuDTO);

    Menu getInfoById(Integer mid);

    void update(MenuDTO menuDTO);

    void delete(Integer id);

    List<Map<String, Object>> getZtreeInfoChecked(Integer rid);
}
