package com.example.excel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.excel.dao.ItemDao;
import com.example.excel.entity.Item;
import com.example.excel.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Override
    public int save(Item item) {
        return itemDao.insert(item);
    }

    @Override
    public List<Item> select(Item item) {
        List<Item> list = itemDao.selectList(Wrappers.query(item));
        return list;
    }
}
