package com.example.excel.controller;

import com.example.excel.interceptor.result.ResponseResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/result")
@ResponseResult
public class ResultController {

    @RequestMapping("{id}")
    public Integer rePath(@PathVariable("id") Integer id){
        return id;
    }

}
