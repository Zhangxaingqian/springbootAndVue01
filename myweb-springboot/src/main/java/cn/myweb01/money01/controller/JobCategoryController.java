package cn.myweb01.money01.controller;

import cn.myweb01.money01.pojo.ResultInfo;
import cn.myweb01.money01.pojo.SecondJobCategory;
import cn.myweb01.money01.service.ICategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("jobCategory")
public class JobCategoryController {
    private final static Logger log= LoggerFactory.getLogger(JobCategoryController.class);

    @Autowired
    private ICategoryService categoryService;

    /*
    * 获取一级分类的内容*/
    @RequestMapping("queryFirstJobCategoryList")
    @ResponseBody
    public String queryFirstJobCategoryList(){
        log.info("查询工作一级分类开始:");
        String jsonData = null;
        try {
            jsonData = categoryService.queryFirstJobCategoryList();

        } catch (Exception e) {
            log.info("一级分类查询异常"+e);
            ResultInfo resultInfo = new ResultInfo(false, null, "服务器忙，请稍后再试！");
            //将resultInfo转换为String
            try {
                //将java对象转化为json对象
                jsonData = new ObjectMapper().writeValueAsString(resultInfo);
            } catch (JsonProcessingException e1) {
                log.info("对象转化为json对象异常"+e1);
            }

        }
        log.info("查询工作一级分类结束:");
        return jsonData;

    }
    /*
     * 根据一级分类的id获取二级分类的内容*/
    @RequestMapping("querySecondJobCategoryListById")
    @ResponseBody
    public String querySecondJobCategoryListById(@RequestParam(value = "firstId", required = true)Integer firstId){
        log.info("查询工作二级分类开始,一级分类的id为:"+firstId);
        String jsonData = null;
        try {
            jsonData = categoryService.querySecondJobCategoryListById(firstId);

        } catch (Exception e) {
            log.info("二级分类查询异常"+e);
            ResultInfo resultInfo = new ResultInfo(false, null, "服务器忙，请稍后再试！");
            //以下的过程感觉没用
            //将resultInfo转换为String
            try {
                //将java对象转化为json字符串
                jsonData = new ObjectMapper().writeValueAsString(resultInfo);
            } catch (JsonProcessingException e1) {
                log.info("对象转化为json对象异常"+e1);
            }

        }
        log.info("查询工作二级分类结束:");
        return jsonData;

    }

    /*
     * 根据二级分类的id获取二级分类的内容*/
    @RequestMapping("getJobGradeById")
    @ResponseBody
    public ResultInfo getJobGradeById(@RequestParam(value = "jobGradeId")Integer jobGradeId){
        log.info("根据二级分类的id获取二级分类的内容开始,二级分类的id为:"+jobGradeId);
        ResultInfo resultInfo = null;
        try {
            SecondJobCategory secondJobCategory = categoryService.queryJobGradeById(jobGradeId);
            resultInfo = new ResultInfo(true, secondJobCategory, null);
        } catch (Exception e) {
            log.info("根据二级分类的id获取二级分类的内容查询异常"+e);
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器忙，请稍后再试！");

        }
        log.info("根据二级分类的id获取二级分类的内容结束:");
        return resultInfo;

    }
}
