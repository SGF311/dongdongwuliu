package com.dongdongwuliu.service;

import java.util.List;

public interface PersonRoleService {
    List<Integer> getInFoByUidGetRid(Integer uid);

    void delete(Integer uid);

    void insertByUIdAndRid(Integer uid, Integer[] roles);
}
