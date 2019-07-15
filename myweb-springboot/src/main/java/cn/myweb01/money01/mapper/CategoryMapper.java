package cn.myweb01.money01.mapper;

import cn.myweb01.money01.pojo.FirstJobCategory;
import cn.myweb01.money01.pojo.SecondJobCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    //获取一级分类
    List<FirstJobCategory> queryFirstJobCategoryList();
    //获取二级分类,根据一级id

    List<SecondJobCategory> querySecondJobCategoryById(@Param("firstId") int firstId);
    //根据二级分类的id查询二级分类的名字
    SecondJobCategory queryJobGradeById(@Param("jobGradeId")Integer jobGradeId);
}
