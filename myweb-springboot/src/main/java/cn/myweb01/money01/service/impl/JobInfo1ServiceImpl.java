package cn.myweb01.money01.service.impl;

import cn.myweb01.money01.mapper.JobInfo1Mapper;
import cn.myweb01.money01.pojo.Jbob1Detail;
import cn.myweb01.money01.pojo.JobInfo1;
import cn.myweb01.money01.pojo.PageBean;
import cn.myweb01.money01.pojo.SecondJobCategory;
import cn.myweb01.money01.service.IJobInfo1Service;
import cn.myweb01.money01.service.IProvinceCityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT)
public class JobInfo1ServiceImpl implements IJobInfo1Service {
    private final static Logger log= LoggerFactory.getLogger(JobInfo1ServiceImpl.class);

    @Autowired
    private JobInfo1Mapper jobInfo1Mapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private IProvinceCityService provinceCityService;

    /*分页查询工作列表,并且模糊查询*/
    @Override
    public PageBean queryJobByPage(Integer curPage, String jname) {
        //定义页面大小，limit x , y中的y的值
        int pageSize = 5;
        //定义查询数据的起始索引号,limit x , y中的x的值
        int firstResult = (curPage-1)*pageSize;

        //查询总记录数
        int count = jobInfo1Mapper.queryJobInfo1Count(jname);
        log.info("总记录数:"+count);
        //查询分页数据
        List<Map<String,Object>> list = jobInfo1Mapper.selectJobInfo1ByPage(firstResult,pageSize,jname);

        //封装到pageBean
        PageBean<Map<String,Object>> pageBean = new PageBean<>();
        pageBean.setCount(count);//总记录数
        pageBean.setCurPage(curPage);//当前页的页面
        pageBean.setPageSize(pageSize);//页面大小
        pageBean.setData(list);//当前页的数据

        return pageBean;
    }
     /*
    和详情表联查,获取数据
    * */
    @Override
    public JobInfo1 selectDetailJobInfo1ById(int job1Id) {

        JobInfo1 jobInfo1=jobInfo1Mapper.selectDetailJobInfo1ById(job1Id);

        return jobInfo1;
    }
    /*
    * 保存招聘信息*/
    @Transactional
    @Override
    public void saveJobInfo(Map<String, Object> zhaoPinInfo,String userName) {
        //将map中的数据取出来,封装到不同的对象中
        log.info("将表单的数据封装为java对象");
        Integer jobCategory=null!=(String)zhaoPinInfo.get("jobCategory")?Integer.parseInt((String)zhaoPinInfo.get("jobCategory")):null;
        Integer jobGrade=null!=(String)zhaoPinInfo.get("jobGrade")?Integer.parseInt((String)zhaoPinInfo.get("jobGrade")):null;
        String job1Detail=(String)zhaoPinInfo.get("jobDetail");
        String firstName=(String)zhaoPinInfo.get("firstName");
        Integer sex=null!=(String)zhaoPinInfo.get("sex")?Integer.parseInt((String)zhaoPinInfo.get("sex")):null;
        String phoneNum=(String)zhaoPinInfo.get("phoneNum");
        String provinceCode=(String)zhaoPinInfo.get("province");
        String cityCode=(String)zhaoPinInfo.get("city");
        String areaCode=(String)zhaoPinInfo.get("area");
        Integer jobWage=null!=(String)zhaoPinInfo.get("jobWage")?Integer.parseInt((String)zhaoPinInfo.get("jobWage")):null;

        //获取地区名称,赋值给jobsite
        String areaName = this.provinceCityService.getAreaNameByCode(areaCode);
        //封装jobInfo1对象
        JobInfo1 jobInfo1 = new JobInfo1();
        jobInfo1.setUserName(userName);
        jobInfo1.setJobCategory(jobCategory);
        jobInfo1.setJobGrade(jobGrade);
        jobInfo1.setFirstName(firstName);
        jobInfo1.setSex(sex);
        jobInfo1.setPhoneNum(phoneNum);
        jobInfo1.setAreaCode(areaCode);
        jobInfo1.setProvinceCode(provinceCode);
        jobInfo1.setCityCode(cityCode);
        jobInfo1.setJobWage(jobWage);
        jobInfo1.setJobSite(areaName);
        //封装jobDetail对象
        Jbob1Detail jbob1Detail = new Jbob1Detail();
        jbob1Detail.setJob1Detail(job1Detail);
        //分别保存这两个对象信息
        //如果工作表已经存在这个用户就更新,不存在就插入
        log.info("根据用户名查询id,如果存在就更新,不存在就插入");
        Integer jobId=jobInfo1Mapper.queryJobIdByUserName(userName);
        if(jobId!=null){
            jobInfo1.setJob1Id(jobId);
            jobInfo1.setJobUpdateDate(new Timestamp(System.currentTimeMillis()));
            log.info("更新工作表的数据");
            jobInfo1Mapper.updateJobInfo(jobInfo1);

            log.info("更新工作表详情表的数据");
            jobInfo1Mapper.updateJobInfoDetail(jobInfo1,jbob1Detail);
            //给es发送消息
            sendMessage(jobId, "update");
        }else{
            jobInfo1.setJobCreateDate(new Timestamp(System.currentTimeMillis()));
            log.info("保存工作表的数据");
            jobInfo1Mapper.saveJobInfo(jobInfo1);
            log.info("保存工作表详情表的数据");
            jobInfo1Mapper.saveJobInfoDetail(jobInfo1,jbob1Detail);
            //给es发送消息
            sendMessage(jobInfo1.getJob1Id(), "insert");
        }

    }

    /*根据用户名查询工作的信息*/
    @Override
    public JobInfo1 queryJobInfoByUserName(String userName) {
        //根据用户名查出对应的工作id,再根据id查出详情
        Integer jobId=jobInfo1Mapper.queryJobIdByUserName(userName);
        //根据id查出工作信息
        if(null==jobId){
            log.info("用户还没有工作招聘信息");
            return null;
        }else{
            JobInfo1 jobInfo1=jobInfo1Mapper.selectDetailJobInfo1ById(jobId);
            return jobInfo1;
        }

    }

    /*当工作表发生更新获取添加的时候,发送消息给es服务,进行对应的更改*/
    private void sendMessage(Integer id, String type){
        // 发送消息
        HashMap<Object, Object> map = new HashMap<>();
        map.put("id", id.toString());
        JSONObject jsonObject=new JSONObject();
        try {
            String s = new ObjectMapper().writeValueAsString(map);
            this.amqpTemplate.convertAndSend("job." + type, s);
        } catch (Exception e) {
            log.error("{}更改工作消息发送异常，工作id：{}", type, id, e);
        }
    }


}
