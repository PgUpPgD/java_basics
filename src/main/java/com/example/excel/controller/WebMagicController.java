package com.example.excel.controller;

import com.example.excel.config.R;
import com.example.excel.entity.JobInfo;
import com.example.excel.service.impl.JobInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("magic")
public class WebMagicController {

    @Autowired
    private JobInfoServiceImpl serviceImpl;

    @RequestMapping("insertInfo")
    public R insertInfo(JobInfo jobInfo){
        return serviceImpl.insertInfo(jobInfo);
    }
}
