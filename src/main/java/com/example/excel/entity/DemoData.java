package com.example.excel.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DemoData {
    private String string;
    private Date date;
    private Double doubleDate;

    public DemoData(){}
    public DemoData(String string, Date date, Double doubleDate) {
        this.string = string;
        this.date = date;
        this.doubleDate = doubleDate;
    }

}
