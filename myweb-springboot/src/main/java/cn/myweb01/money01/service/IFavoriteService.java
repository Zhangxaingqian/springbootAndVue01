package cn.myweb01.money01.service;

import cn.myweb01.money01.pojo.PageBean;

public interface IFavoriteService {
    Boolean isFavoriteByJob1Id(Integer job1Id, Integer uid);

    void addFavorite(Integer rid, Integer uid);

    PageBean queryFavoriteByPage(Integer curPage, Integer uid);
}
