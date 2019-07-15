package cn.myweb01.money01.controller;

import cn.myweb01.money01.pojo.PageBean;
import cn.myweb01.money01.pojo.ResultInfo;
import cn.myweb01.money01.pojo.User;
import cn.myweb01.money01.service.IFavoriteService;
import cn.myweb01.money01.service.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("favorite")
public class FavoriteController {

    @Autowired
    private IRouteService routeService;

    @Autowired
    private IFavoriteService favoriteService;

    @RequestMapping("isFavoriteByJob1Id")
    @ResponseBody
    public ResultInfo isFavoriteByRid(@RequestParam("job1Id")Integer job1Id, HttpSession session){

        ResultInfo resultInfo = null;

        try {
            User user = (User) session.getAttribute("user");
            if(user == null){
                resultInfo = new ResultInfo(true,false,null);
            }else{
                //查询登陆用户是否已经收藏了该路线
                Boolean flag = favoriteService.isFavoriteByJob1Id(job1Id,user.getUid());
                //是否显示收藏
                resultInfo = new ResultInfo(true,flag,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,"服务器忙，请稍后再试！");
        }

        return resultInfo;
    }

    @RequestMapping("addFavorite")
    @ResponseBody
    public ResultInfo addFavorite(@RequestParam("job1Id")Integer job1Id, HttpSession session){

        ResultInfo resultInfo = null;

        User user = (User) session.getAttribute("user");

        try {
            if (user==null){
                resultInfo = new ResultInfo(true,0,null);
            }else{
                //添加收藏
                favoriteService.addFavorite(job1Id,user.getUid());
                //查询最新收藏数量
                int count = routeService.queryRouteFavoriteNum(job1Id);
                resultInfo = new ResultInfo(true,count,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,"服务器忙，请稍后再试！");
        }
        return  resultInfo;
    }

    @RequestMapping("findFavoriteByPage")
    @ResponseBody
    public ResultInfo findFavoriteByPage(@RequestParam(value = "curPage",defaultValue = "1")Integer curPage, HttpSession session){
        ResultInfo resultInfo = null;

        User user = (User) session.getAttribute("user");

        try {
            PageBean pageBean = favoriteService.queryFavoriteByPage(curPage,user.getUid());
            resultInfo = new ResultInfo(true,pageBean,null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,"服务器忙，请稍后再试！");
        }


        return resultInfo;
    }
}
