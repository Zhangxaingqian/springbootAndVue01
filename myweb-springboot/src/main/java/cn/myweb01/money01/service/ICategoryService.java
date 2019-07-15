package cn.myweb01.money01.service;

import cn.myweb01.money01.pojo.SecondJobCategory;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ICategoryService {
    //获取一级分类
    String queryFirstJobCategoryList() throws JsonProcessingException;
    //根据一级分类获取二级分类
    String querySecondJobCategoryListById(Integer firstId) throws JsonProcessingException;
    //根据二级分类的id查询二级分类的名字
    SecondJobCategory queryJobGradeById(Integer jobGradeId);
}
