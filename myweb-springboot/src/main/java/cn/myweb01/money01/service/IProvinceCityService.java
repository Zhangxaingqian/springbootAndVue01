package cn.myweb01.money01.service;

public interface IProvinceCityService {
    //根据省份名称返回城市列表
    String getCitys(String provinceCode);
    //根据城市获取地区
   String getAreaByCity(String cityCode);
    //获取所有的省份
    String getAllProvince();
    //根据地区代码获取地区名称
    String getAreaNameByCode(String areaCode);
}
