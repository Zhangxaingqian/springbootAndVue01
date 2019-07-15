package cn.myweb01.money01.controller.elasticsearch;

import cn.myweb01.money01.pojo.PageBean;
import cn.myweb01.money01.pojo.ResultInfo;
import cn.myweb01.money01.pojo.elasticsearch.SearchRequest;
import cn.myweb01.money01.service.IElasticsearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("es")
public class EsController {
    private final static Logger log= LoggerFactory.getLogger(EsController.class);

        @Autowired
        private IElasticsearchService elasticsearchService;
    /*
    * 搜索工作*/
    @PostMapping("search")
    @ResponseBody
    public ResultInfo search(@RequestBody SearchRequest request) {
        log.info("使用elasticsearch进行搜索开始");
        log.info("根据当前页和搜索内容查询招聘信息开始,当前页:"+request.getCurPage()+"搜索内容"+request.getKey());
        ResultInfo resultInfo = null;
        try {
            System.out.println("kkk");
            //从es的索引库查询所有的招聘分页信息
            PageBean pageBean = elasticsearchService.queryJobByPage(request);

            //查询成功
            resultInfo = new ResultInfo(true, pageBean, null);

        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器忙，请稍后再试！");
        }
        log.info("根据当前页和搜索内容查询招聘信息结束");
        return resultInfo;
    }

}
