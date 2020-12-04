package com.example.excel.service;

import com.example.excel.entity.Item;

import java.util.List;

public interface ItemService {
    //插入
    int save(Item item);

    //查询
    List<Item> select(Item item);
}
