package cn.myweb01.money01.mapper;

import cn.myweb01.money01.pojo.Route;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RouteMapper {
    List<Route> queryPopularityRouteList();

    List<Route> queryNewsRouteList();

    List<Route> queryThemesRouteList();

    int queryRouteCount(@Param("cid") Integer cid, @Param("rname") String rname);

    List<Route> queryRouteListPage(@Param("cid") Integer cid, @Param("firstResult") int firstResult, @Param("pageSize") int pageSize, @Param("rname") String rname);

    Route queryRouteByRid(@Param("rid") Integer rid);

    void updateRouteCount(@Param("job1Id") Integer rid);

    int queryRouteFavoriteNum(@Param("rid") Integer rid);
}
