package JunitTest.test01;

import JunitTest.MyUnitTest;
import cn.myweb01.money01.mapper.CategoryMapper;
import cn.myweb01.money01.mapper.IProvinceCityMapper;
import cn.myweb01.money01.pojo.FirstJobCategory;
import cn.myweb01.money01.pojo.ProvinceCity.Province;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Test02 extends MyUnitTest {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private IProvinceCityMapper provinceCityMapper;

    @Test
    public void test(){
        List<FirstJobCategory> firstJobCategories = categoryMapper.queryFirstJobCategoryList();
        List<Province> allProvince = provinceCityMapper.getAllProvince();
        System.out.println("firstJobCategories = " + firstJobCategories);
    }
}
