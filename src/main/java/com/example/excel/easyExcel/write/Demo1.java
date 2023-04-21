package com.example.excel.easyExcel.write;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Demo1 {
    @ExcelProperty("*客户名称")
    private String name;

    @ExcelProperty("*客户类型")
    private String customerType;

    @ExcelProperty("*客户来源")
    private String customerSource;

    @ExcelProperty("*客户手机号码")
    private String mobilePhone;

    @ExcelProperty("*客户经理手机号码")
    private String mobile;

    @ExcelProperty("尊称")
    private String callType;

    @ExcelProperty("所属网格")
    private String gridsId;

    @ExcelProperty("身份证号码")
    private String idCard;

    @ExcelProperty("详细地址")
    private String contactAddressDetail;
}
