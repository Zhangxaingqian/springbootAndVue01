package cn.myweb01.money01.controller;

import cn.myweb01.money01.pojo.JobInfo1;
import cn.myweb01.money01.pojo.PageBean;
import cn.myweb01.money01.pojo.ResultInfo;
import cn.myweb01.money01.pojo.User;
import cn.myweb01.money01.service.IJobInfo1Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("jobInfo1")
public class JobInfo1Controller {
    private final static Logger log= LoggerFactory.getLogger(JobInfo1Controller.class);
    @Autowired
    private IJobInfo1Service jobInfo1Service;
    /*
     * 分页查询工作表1的信息,并且模糊查询
     * */
    @RequestMapping("queryJobByPage")
    @ResponseBody
    public ResultInfo queryJobByPage(@RequestParam(value = "curPage", defaultValue = "1")Integer curPage,
                                     @RequestParam(value = "jname",required = false)String jname ) {
        log.info("根据当前页和搜索内容查询招聘信息开始,当前页:"+curPage+"搜索内容"+jname);
        ResultInfo resultInfo = null;
        try {
            //查询所有的招聘分页信息
            PageBean pageBean = jobInfo1Service.queryJobByPage(curPage,jname);

            //是否显示收藏
            resultInfo = new ResultInfo(true, pageBean, null);

        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器忙，请稍后再试！");
        }
        log.info("根据当前页和搜索内容查询招聘信息结束");
        return resultInfo;
    }

    /*
   和详情表联查,获取数据
   * */
    @RequestMapping("queryJobDetailById")
    @ResponseBody
    public ResultInfo queryJobInfo1ById(@RequestParam("job1Id")Integer job1Id) {
        log.info("根据id查询工作招聘详情开始,jobid:"+job1Id);
        ResultInfo resultInfo = null;
        try {
            //查询所有的招聘信息
            JobInfo1 jobInfo1 = jobInfo1Service.selectDetailJobInfo1ById(job1Id);

            //封装返回数据
            resultInfo = new ResultInfo(true, jobInfo1, null);

        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器忙，请稍后再试！");
        }
        log.info("根据id查询工作招聘详情结束");
        return resultInfo;
    }

    /*
    * 根据招聘表单的信息,进行保存
    * 保存以前先判断用户是否登录,只有登录状态下才进行保存
    * */
    @RequestMapping("saveInfo")
    @ResponseBody
    public ResultInfo saveInfo(@RequestParam Map<String, Object> zhaoPinInfo, HttpSession session){
        log.info("保存招聘信息开始");
        ResultInfo resultInfo = null;
        try {
            User user = (User) session.getAttribute("user");
            if(null!=user){
                log.info("招聘信息的发布人:"+user.getName());
                String userName=user.getUsername();
                jobInfo1Service.saveJobInfo(zhaoPinInfo,userName);
                resultInfo = new ResultInfo(true,null,null);
            }else{
                log.info("未登录");
                resultInfo = new ResultInfo(false,null,"请先登录");
            }
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,"服务器忙，请稍后再试！");
        }
        return resultInfo;
    }
    /*
     * 根据用户的名字查询工作表内容
     * */
    @RequestMapping("queryJobInfoByUserName")
    @ResponseBody
    public ResultInfo queryJobInfoByUserName(HttpSession session){
        log.info("根据用户名字查询工作内容开始");
        ResultInfo resultInfo = null;
        try {
            User user = (User) session.getAttribute("user");
            if(null!=user){
                log.info("招聘信息的发布人:"+user.getName());
                String userName=user.getUsername();
                JobInfo1 jobInfo1 =jobInfo1Service.queryJobInfoByUserName(userName);
                if(jobInfo1!=null){
                    log.info("登录人已经存在招聘");
                    resultInfo = new ResultInfo(true,jobInfo1,null);
                }else{
                    log.info("登录人还未发布招聘");
                    resultInfo = new ResultInfo(false,null,"未发布招聘");
                }
            }else{
                log.info("未登录");
                resultInfo = new ResultInfo(false,null,"请先登录");
            }
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,"服务器忙，请稍后再试！");
        }
        return resultInfo;
    }

}
