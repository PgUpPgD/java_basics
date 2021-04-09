package com.example.excel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.excel.config.R;
import com.example.excel.dao.JobInfoDao;
import com.example.excel.entity.JobInfo;
import com.example.excel.service.JobInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class JobInfoServiceImpl extends ServiceImpl<JobInfoDao, JobInfo> implements JobInfoService{

    public R insertInfo(JobInfo jobInfo){
        JobInfo info = this.getBaseMapper().selectOne(Wrappers.<JobInfo>query().lambda()
                .eq(JobInfo::getUrl, jobInfo.getUrl())
                .eq(JobInfo::getTime, jobInfo.getTime()));
        if (StringUtils.isEmpty(info)){
            Boolean b = this.saveOrUpdate(jobInfo, Wrappers.<JobInfo>query().lambda()
                    .eq(JobInfo::getUrl, jobInfo.getUrl()));
            if (b){
                System.out.println("存入成功");
                return R.data(b);
            }
        }
        System.out.println("数据已存在或更新失败");
        return R.error();  //数据已存在或更新失败
    }

    public List<JobInfo> findJobInfo(JobInfo jobInfo) {
        List<JobInfo> list = this.getBaseMapper().selectList(Wrappers.<JobInfo>query().lambda().eq(JobInfo::getUrl, jobInfo.getUrl()));
        return list;
    }
}
