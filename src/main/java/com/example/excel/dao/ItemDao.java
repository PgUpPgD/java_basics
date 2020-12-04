package com.example.excel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.excel.entity.Item;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDao extends BaseMapper<Item> {
}
