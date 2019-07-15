package cn.myweb01.money01.service.impl;

import cn.myweb01.money01.mapper.FavoriteMapper;
import cn.myweb01.money01.mapper.RouteMapper;
import cn.myweb01.money01.pojo.Favorite;
import cn.myweb01.money01.pojo.PageBean;
import cn.myweb01.money01.service.IFavoriteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT)
public class FavoriteServiceImpl implements IFavoriteService {

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public Boolean isFavoriteByJob1Id(Integer job1Id, Integer uid) {

        Favorite favorite = favoriteMapper.isFavoriteByJob1Id(job1Id,uid);

        if (favorite==null){
            //如果是null，表示没有收藏
            return false;
        }

        return true;
    }

    @Override
    public void addFavorite(Integer job1Id, Integer uid) {

        //新增收藏
        favoriteMapper.addFavorite(job1Id,uid);

        //更新收藏数量
        //routeMapper.updateRouteCount(job1Id);

    }

    /*@Override
    public PageBean queryFavoriteByPage(Integer curPage, Integer uid) {

        //定义页面大小，limit x , y 中的y的值
        int pageSize = 4;
        //定义查询记录的起始值，limit x , y 中的x的值。
        int firstResult = (curPage-1)*pageSize;

        //查询总记录数
        int count = favoriteMapper.queryFavoriteCount(uid);

        //查询分页数据
        List<Favorite> list = favoriteMapper.queryFavoriteByPage(firstResult,pageSize,uid);


        //封装数据到pageBean中
        PageBean<Favorite> pageBean = new PageBean<>();

        //总记录数
        pageBean.setCount(count);
        //页面大小
        pageBean.setPageSize(pageSize);
        //当前页页码
        pageBean.setCurPage(curPage);
        //分页数据
        pageBean.setData(list);

        return pageBean;
    }*/


    @Override
    public PageBean queryFavoriteByPage(Integer curPage, Integer uid) {

        //定义页面大小，limit x , y 中的y的值
        int pageSize = 4;

        PageHelper.startPage(curPage, pageSize);

        //查询数据
        List<Favorite> list = favoriteMapper.queryFavoriteList(uid);

        PageInfo page = new PageInfo(list);


        //封装数据到pageBean中
        PageBean<Favorite> pageBean = new PageBean<>();

        //总记录数
        pageBean.setCount((int) page.getTotal());
        //页面大小
        pageBean.setPageSize(pageSize);
        //当前页页码
        pageBean.setCurPage(curPage);
        //分页数据
        pageBean.setData(page.getList());

        return pageBean;
    }
}
