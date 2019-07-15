package cn.myweb01.money01.mapper;

import cn.myweb01.money01.pojo.Jbob1Detail;
import cn.myweb01.money01.pojo.JobInfo1;
import cn.myweb01.money01.pojo.SecondJobCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface JobInfo1Mapper {
    //分页查询工作表1的信息
    List<Map<String,Object>> selectJobInfo1ByPage(@Param("firstResult") int firstResult, @Param("pageSize") int pageSize, @Param("jname") String jname);
    //表1的总条数
    //@Select("select count(*) from tab_jobinfo1")
    int queryJobInfo1Count(@Param("jname") String jname);
    /*
    和详情表联查,获取数据
    * */
    JobInfo1 selectDetailJobInfo1ById(@Param("job1Id") int job1Id);

    //保存工作表的信息
    void saveJobInfo(JobInfo1 jobInfo1);
    //保存工作详情表的信息
    void saveJobInfoDetail(@Param("jobInfo1") JobInfo1 jobInfo1, @Param("job1Detail") Jbob1Detail job1Detail);
    //根据用户名查出工作表的id
    Integer queryJobIdByUserName(@Param("userName") String userName);
    //更新工作表的信息
    void updateJobInfo(JobInfo1 jobInfo1);
    //更新工作详情表的信息
    void updateJobInfoDetail(@Param("jobInfo1") JobInfo1 jobInfo1, @Param("job1Detail") Jbob1Detail job1Detail);

    //查询工作表一和详情表的所有信息,返回jobInfo对象
    List<JobInfo1> selectAll();

}
