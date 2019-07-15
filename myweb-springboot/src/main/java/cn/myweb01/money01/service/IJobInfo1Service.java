package cn.myweb01.money01.service;

import cn.myweb01.money01.pojo.JobInfo1;
import cn.myweb01.money01.pojo.PageBean;
import cn.myweb01.money01.pojo.SecondJobCategory;

import java.util.Map;

public interface IJobInfo1Service {
    //分页查询工作表1的信息
    PageBean queryJobByPage(Integer curPage, String jname);
    /*
    和详情表联查,获取数据
    * */
    JobInfo1 selectDetailJobInfo1ById(int job1Id);
    /*
    保存招聘信息
    * */
    void saveJobInfo(Map<String, Object> zhaoPinInfo, String userName);

    /*根据用户名查询工作的详情*/
    JobInfo1 queryJobInfoByUserName(String userName);


}
