package cn.myweb01.money01.pojo;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 职位描述实体类
 */
public class JobInfo1 implements Serializable {

    private Integer job1Id;// 工作表1的id，必输
    private String userName;//用户名
    private Integer jobCategory;
    private Integer jobGrade;//职位等级
    private String jobSite;//位置
    private Timestamp jobCreateDate;//发布日期
    private Timestamp jobUpdateDate;//更新日期
    private Integer jobWage;//日薪
    private String firstName;//联系人的姓氏
    private Integer sex;//联系人的姓氏
    private String phoneNum;//联系人的手机号
    private String provinceCode;//省代码
    private String cityCode;//城市代码
    private String areaCode;//地区代码



    //高级查询的一对一关系
    private Jbob1Detail jbob1Detail;// 详情


    public Timestamp getJobCreateDate() {
        return jobCreateDate;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setJobCreateDate(Timestamp jobCreateDate) {
        this.jobCreateDate = jobCreateDate;
    }

    public Timestamp getJobUpdateDate() {
        return jobUpdateDate;
    }

    public void setJobUpdateDate(Timestamp jobUpdateDate) {
        this.jobUpdateDate = jobUpdateDate;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Jbob1Detail getJbob1Detail() {
        return jbob1Detail;
    }

    public void setJbob1Detail(Jbob1Detail jbob1Detail) {
        this.jbob1Detail = jbob1Detail;
    }

    public Integer getJob1Id() {
        return job1Id;
    }

    public void setJob1Id(Integer job1Id) {
        this.job1Id = job1Id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(Integer jobCategory) {
        this.jobCategory = jobCategory;
    }

    public Integer getJobGrade() {
        return jobGrade;
    }

    public void setJobGrade(Integer jobGrade) {
        this.jobGrade = jobGrade;
    }

    public String getJobSite() {
        return jobSite;
    }

    public void setJobSite(String jobSite) {
        this.jobSite = jobSite;
    }


    public Integer getJobWage() {
        return jobWage;
    }

    public void setJobWage(Integer jobWage) {
        this.jobWage = jobWage;
    }
    /*
    无参构造方法
    * */

    public JobInfo1() {
    }
    /*
    全参构造方法
    * */

}
