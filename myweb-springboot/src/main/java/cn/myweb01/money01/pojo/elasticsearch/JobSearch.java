package cn.myweb01.money01.pojo.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Timestamp;


@Document(indexName = "jobsearch", type = "docs", shards = 5, replicas = 1)
public class JobSearch {
    @Id
    private Integer id; // job1_id
    @Field(type = FieldType.Text)
    private String all; // 所有需要被搜索的信息，包含工作等级,薪水,工作详细要求
    @Field(type = FieldType.Keyword, index = true)//设置为false就不可以通过这个字段搜索
    private Integer jobWage;// 价格
    private String jobSite;// 位置
    private Integer jobGrade;// 职位等级
    private Timestamp jobUpdateDate;// 更新时间

    public String getJobSite() {
        return jobSite;
    }

    public void setJobSite(String jobSite) {
        this.jobSite = jobSite;
    }

    public Integer getJobGrade() {
        return jobGrade;
    }

    public void setJobGrade(Integer jobGrade) {
        this.jobGrade = jobGrade;
    }

    public Timestamp getJobUpdateDate() {
        return jobUpdateDate;
    }

    public void setJobUpdateDate(Timestamp jobUpdateDate) {
        this.jobUpdateDate = jobUpdateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }


    public Integer getJobWage() {
        return jobWage;
    }

    public void setJobWage(Integer jobWage) {
        this.jobWage = jobWage;
    }
}
