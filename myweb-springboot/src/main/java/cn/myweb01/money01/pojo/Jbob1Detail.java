package cn.myweb01.money01.pojo;

import java.io.Serializable;

public class Jbob1Detail implements Serializable {
    private int dJob1Id;//详细要求的id
    private int job1Id;//外键
    private String job1Detail;//详细要求

    public Jbob1Detail() {
    }

    public Jbob1Detail(int dJob1Id, int job1Id, String job1Detail) {
        this.dJob1Id = dJob1Id;
        this.job1Id = job1Id;
        this.job1Detail = job1Detail;
    }

    public int getdJob1Id() {
        return dJob1Id;
    }

    public void setdJob1Id(int dJob1Id) {
        this.dJob1Id = dJob1Id;
    }

    public int getJob1Id() {
        return job1Id;
    }

    public void setJob1Id(int job1Id) {
        this.job1Id = job1Id;
    }

    public String getJob1Detail() {
        return job1Detail;
    }

    public void setJob1Detail(String job1Detail) {
        this.job1Detail = job1Detail;
    }
}
