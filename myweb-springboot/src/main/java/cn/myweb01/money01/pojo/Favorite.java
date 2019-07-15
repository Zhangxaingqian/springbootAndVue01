package cn.myweb01.money01.pojo;

import java.io.Serializable;

/**
 * 收藏实体类
 */
public class Favorite implements Serializable {
    //高级查询，一对一的关系
    private JobInfo1 jobInfo1;//工作表
    private String date;//收藏时间
    private User user;//所属用户

    /**
     * 无参构造方法
     */
    public Favorite() {
    }


    public JobInfo1 getJobInfo1() {
        return jobInfo1;
    }

    public void setJobInfo1(JobInfo1 jobInfo1) {
        this.jobInfo1 = jobInfo1;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    
}
