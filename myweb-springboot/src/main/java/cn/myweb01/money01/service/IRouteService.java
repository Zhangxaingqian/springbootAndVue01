package cn.myweb01.money01.service;

import cn.myweb01.money01.pojo.PageBean;
import cn.myweb01.money01.pojo.Route;

import java.util.List;
import java.util.Map;

public interface IRouteService {
    Map<String,List<Route>> routeCareChoose();

    PageBean queryPageBean(Integer cid, Integer curPage, String rname);

    Route queryRouteByRid(Integer rid);

    int queryRouteFavoriteNum(Integer rid);
}
