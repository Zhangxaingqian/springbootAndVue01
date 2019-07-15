package cn.myweb01.money01.mapper;

import cn.myweb01.money01.pojo.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface FavoriteMapper {
    Favorite isFavoriteByJob1Id(@Param("job1Id") Integer job1Id, @Param("uid") Integer uid);

    void addFavorite(@Param("job1Id") Integer job1Id, @Param("uid") Integer uid);

    int queryFavoriteCount(@Param("uid") Integer uid);

    List<Favorite> queryFavoriteByPage(@Param("firstResult") int firstResult, @Param("pageSize") int pageSize, @Param("uid") Integer uid);

    List<Favorite> queryFavoriteList(@Param("uid") Integer uid);
}
