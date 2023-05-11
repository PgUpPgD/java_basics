package com.example.excel.service;

import com.example.excel.entity.Item;
import com.example.excel.reflect.getWay.API;

import java.util.List;

@API(code = "SP001", description = "商品插入和查找接口")
public interface ItemService {
    //插入
    int save(Item item);

    //查询
    List<Item> select(Item item);
}
