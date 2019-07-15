package cn.myweb01.money01.service.impl;

import cn.myweb01.money01.mapper.RouteMapper;
import cn.myweb01.money01.pojo.PageBean;
import cn.myweb01.money01.pojo.Route;
import cn.myweb01.money01.service.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service


public class RouteServiceImpl implements IRouteService {

    @Autowired
    private RouteMapper routeMapper;

    @Override
    public Map<String, List<Route>> routeCareChoose() {

        //查询人气旅游
       List<Route> popularityList =  routeMapper.queryPopularityRouteList();
       //查询最新旅游
        List<Route> newsList =  routeMapper.queryNewsRouteList();
        //查询主题旅游
        List<Route> themesList =  routeMapper.queryThemesRouteList();

        //将查询出来的数据封装到map中
        Map<String, List<Route>> map = new HashMap<>();
        map.put("popularity",popularityList);
        map.put("news",newsList);
        map.put("themes",themesList);

        return map;
    }

    @Override
    public PageBean queryPageBean(Integer cid, Integer curPage, String rname) {

        //定义页面大小，limit x , y中的y的值
        int pageSize = 4;
        //定义查询数据的起始索引号,limit x , y中的x的值
        int firstResult = (curPage-1)*pageSize;

        //查询总记录数
        int count = routeMapper.queryRouteCount(cid,rname);

        //查询分页数据
        List<Route> list = routeMapper.queryRouteListPage(cid,firstResult,pageSize,rname);

        //封装到pageBean
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setCount(count);//总记录数
        pageBean.setCurPage(curPage);//当前页的页面
        pageBean.setPageSize(pageSize);//页面大小
        pageBean.setData(list);//当前页的数据

        return pageBean;
    }

    @Override
    public Route queryRouteByRid(Integer rid) {

        return routeMapper.queryRouteByRid(rid);
    }

    @Override
    public int queryRouteFavoriteNum(Integer rid) {
        return routeMapper.queryRouteFavoriteNum(rid);
    }
}
