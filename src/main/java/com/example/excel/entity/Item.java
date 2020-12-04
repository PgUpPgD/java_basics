package com.example.excel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("jd_item")
public class Item {
    //主键
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //标准产品单位（商品集合）
    private Long spu;
    //库存量单位（最小品类单元）
    private Long sku;
    //商品标题
    private String title;
    //商品价格
    private Double price;
    //商品图片
    private String pic;
    //商品详情地址
    private String url;
    //创建时间
    private LocalDateTime created;
    //更新时间
    private Date updated;
}
