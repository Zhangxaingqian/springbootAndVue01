package cn.myweb01.money01.controller;

import cn.myweb01.money01.pojo.PageBean;
import cn.myweb01.money01.pojo.ResultInfo;
import cn.myweb01.money01.pojo.Route;
import cn.myweb01.money01.service.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("route")
public class RouteController {

    @Autowired
    private IRouteService routeService;

    @RequestMapping("routeCareChoose")
    @ResponseBody
    public ResultInfo routeCareChoose(){

        ResultInfo resultInfo = null;

        try {
            Map<String,List<Route>> map = routeService.routeCareChoose();
            resultInfo = new ResultInfo(true,map,null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,"服务器忙，请稍后再试！");
        }
        return  resultInfo;
    }

    @RequestMapping("queryPageBean")
    @ResponseBody
    public ResultInfo queryPageBean(@RequestParam(value = "cid",required = false)Integer cid,
                                    @RequestParam(value = "curPage",defaultValue = "1")Integer curPage,
                                    @RequestParam(value = "rname",required = false)String rname){

        ResultInfo resultInfo = null;

        try {
            PageBean pageBean = routeService.queryPageBean(cid,curPage,rname);
            resultInfo = new ResultInfo(true,pageBean,null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,"服务器忙，请稍后再试！");
        }
        return resultInfo;
    }

    @RequestMapping("queryRouteByRid")
    @ResponseBody
    public ResultInfo queryRouteByRid(@RequestParam("rid")Integer rid){

        ResultInfo resultInfo = null;

        try {
            Route route = routeService.queryRouteByRid(rid);
            resultInfo = new ResultInfo(true,route,null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,"服务器忙，请稍后再试！");
        }
        return resultInfo;
    }
}
