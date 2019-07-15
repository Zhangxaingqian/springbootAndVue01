package cn.myweb01.money01.mapper;

import cn.myweb01.money01.pojo.ProvinceCity.Area;
import cn.myweb01.money01.pojo.ProvinceCity.City;
import cn.myweb01.money01.pojo.ProvinceCity.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProvinceCityMapper {
    //根据省份获取城市
    List<City> getCitys(@Param("provinceCode") String provinceCode);
    //根据城市获取地区
    List<Area> getAreaByCity(@Param("cityCode") String cityCode);
    //获取所有的省份
    List<Province> getAllProvince();
    //通过城市找到对应的省份
    City getProvinceByCityCode(@Param("cityCode") String cityCode);

    //根据地区的代码获取地区的名称
    Area getAreaNameByCode(@Param("areaCode")String areaCode);
}
