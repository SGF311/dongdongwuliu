package com.dongdongwuliu.service;

public interface RoleMenuService {
    void insertByRidAndResId(Integer rid, Integer[] ids);

    void deleteByRid(Integer rid);
}
