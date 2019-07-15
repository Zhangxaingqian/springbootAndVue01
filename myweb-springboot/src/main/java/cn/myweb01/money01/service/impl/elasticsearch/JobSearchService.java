package cn.myweb01.money01.service.impl.elasticsearch;

import cn.myweb01.money01.pojo.JobInfo1;
import cn.myweb01.money01.pojo.SecondJobCategory;
import cn.myweb01.money01.pojo.elasticsearch.JobSearch;
import cn.myweb01.money01.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* 创建service将工作表查出来的信息存储到索引库中,
*   也就是将查出来的信息转化为Jobserch对象*/
@Service
public class JobSearchService {

    @Autowired
    private ICategoryService categoryService;

    //创建JobSearch对象的方法
    public JobSearch createJobSearch(JobInfo1 jobInfo1){
        JobSearch jobSearch = new JobSearch();
        //根据jobgrade查询对应的中文名称
        Integer jobGrade = jobInfo1.getJobGrade();
        SecondJobCategory secondJobCategory = categoryService.queryJobGradeById(jobGrade);
        //查询详细要求
        String job1Detail = jobInfo1.getJbob1Detail().getJob1Detail();


        //对jobsearch进行赋值
        //主键
        jobSearch.setId(jobInfo1.getJob1Id());
        //全文检索的字段
        jobSearch.setAll(secondJobCategory.getSecondName()+" "+job1Detail);
        //薪水
        jobSearch.setJobWage(jobInfo1.getJobWage());
        //职位等级
        jobSearch.setJobGrade(jobInfo1.getJobGrade());
        //工作地点
        jobSearch.setJobSite(jobInfo1.getJobSite());
//        更新时间
        jobSearch.setJobUpdateDate(jobInfo1.getJobUpdateDate());


        return jobSearch;
    }
}
