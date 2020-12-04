package com.example.excel.webMagic.magic;

import com.example.excel.entity.JobInfo;
import com.example.excel.service.impl.JobInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class JobPipeline implements Pipeline {

    @Autowired
    private JobInfoServiceImpl infoService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        //获取封装好的招聘详情对象
        JobInfo jobInfo = resultItems.get("jobInfo");
        // 判断是否不为空
        if (jobInfo != null) {
            // 如果不为空把数据保存到数据库中
            this.infoService.insertInfo(jobInfo);
        }
    }
}
