package cn.myweb01.money01.service.impl;

import cn.myweb01.money01.mapper.IProvinceCityMapper;
import cn.myweb01.money01.pojo.ProvinceCity.Area;
import cn.myweb01.money01.pojo.ProvinceCity.City;
import cn.myweb01.money01.pojo.ProvinceCity.Province;
import cn.myweb01.money01.service.IProvinceCityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT)
public class ProvinceCityServiceImpl implements IProvinceCityService {
    private final static Logger log= LoggerFactory.getLogger(ProvinceCityServiceImpl.class);

    @Autowired
    private IProvinceCityMapper provinceCityMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    /*根据省份名称获取城市列表*/
    @Override
    public String getCitys(String provinceCode) {
        //使用缓存
        String jsonData = (String) redisTemplate.opsForHash().get("city",provinceCode);
        log.info("redis中的二级分类的缓存city为:{}"+jsonData);
        //如果缓存中没有数据，就去数据库查询，然后再放入缓存中
        if (StringUtils.isBlank(jsonData)){
            log.info("没有缓存,从mysql数据库中进行查询");
            //查询所有的省份
            List<Province> lists = provinceCityMapper.getAllProvince();
            //将Province对象转化为integer对象,jdk8的新特性
            List<String> ids = lists.stream().map(job -> job.getCode()).collect(Collectors.toList());
            //遍历id,根据id查询二级分类信息,将信息存入map中
            HashMap<String,String> map = new HashMap<>();
            ids.forEach(id->{
                List<City> seconds = provinceCityMapper.getCitys(id);
                String listStr = null;
                try {
                    listStr = new ObjectMapper().writeValueAsString(seconds);
                } catch (JsonProcessingException e) {
                    log.info("列表转化为字符串异常");
                    e.printStackTrace();
                }
                map.put(id, listStr);
            });
            log.info("将数据存入redis缓存中");
            //将map存入redis的hash数据结构中
            redisTemplate.opsForHash().putAll("city", map);
            redisTemplate.expire("city", 60*60, TimeUnit.SECONDS);
            jsonData = (String) redisTemplate.opsForHash().get("city",provinceCode);
        }

        return jsonData;

    }
    /*根据城市获取地区*/
    @Override
    public String getAreaByCity(String cityCode) {
        String jsonData=null;
        //先通过city获取省份
        City cityPro =provinceCityMapper.getProvinceByCityCode(cityCode);
        //使用缓存
        String cityData = (String) redisTemplate.opsForHash().get("area",cityPro.getProvincecode());
        HashMap cityMap=null;
        List jsonList=null;
        if(null!=cityData){
            try {
                //cityMap = new ObjectMapper().readValue(cityData, Map.class);
                cityMap = new ObjectMapper().readValue(cityData, HashMap.class);
                //jsonData=(String) cityMap.get(cityCode);
                jsonList=(List) cityMap.get(cityCode);
                jsonData= new ObjectMapper().writeValueAsString(jsonList);
                log.info("redis中的缓存area为:{}"+jsonData);
            } catch (IOException e) {
                log.info("字符串转化为map对象异常");
                e.printStackTrace();
            }
        }
        //如果缓存中没有数据，就去数据库查询，然后再放入缓存中
        if (StringUtils.isBlank(jsonData)){
            log.info("没有缓存,从mysql数据库中进行查询");
            //查询所有的省份
            List<Province> lists = provinceCityMapper.getAllProvince();
            //将Province对象转化为integer对象,jdk8的新特性
            List<String> ids = lists.stream().map(job -> job.getCode()).collect(Collectors.toList());
            //遍历id,根据id查询二级分类信息,将信息存入map中
            HashMap<String,String> map = new HashMap<>();
            ids.forEach(id->{
                //根据省份查询所有的城市
                List<City> seconds = provinceCityMapper.getCitys(id);
                //将城市list转化为城市id的list
                List<String> citys = seconds.stream().map(city -> city.getCode()).collect(Collectors.toList());
                //遍历citys,根据city查询所有的地区
                Map<String,Object> secondMap=new HashMap<>();
                citys.forEach(city->{
                    List<Area> areas=provinceCityMapper.getAreaByCity(city);
                    secondMap.put(city,areas);
                });
                String listStr = null;
                try {
                    listStr = new ObjectMapper().writeValueAsString(secondMap);
                } catch (JsonProcessingException e) {
                    log.info("列表转化为字符串异常");
                    e.printStackTrace();
                }
                map.put(id, listStr);
            });
            log.info("将数据存入redis缓存中");
            //将map存入redis的hash数据结构中
            redisTemplate.opsForHash().putAll("area", map);
            redisTemplate.expire("city", 60*60, TimeUnit.SECONDS);
             cityData = (String) redisTemplate.opsForHash().get("area",cityPro.getProvincecode());
            try {
                cityMap = new ObjectMapper().readValue(cityData, HashMap.class);
                jsonList=(List) cityMap.get(cityCode);
                jsonData= new ObjectMapper().writeValueAsString(jsonList);
            } catch (IOException e) {
                log.info("字符串转化为map对象异常");
                e.printStackTrace();
            }
        }

        return jsonData;
    }
    /*获取所有的省份*/
    @Override
    public String getAllProvince() {
        //使用redis的缓存
        String jsonData = (String) redisTemplate.opsForValue().get("province");
        log.info("redis中的一级分类的缓存province为:"+jsonData);
        //如果缓存中没有数据，就去数据库查询，然后再放入缓存中
        if (StringUtils.isBlank(jsonData)){
            log.info("没有缓存,从mysql数据库中进行查询");
            List<Province> list = provinceCityMapper.getAllProvince();

            //使用工具将list转换为String
            try {
                jsonData = new ObjectMapper().writeValueAsString(list);
            } catch (JsonProcessingException e) {
                log.info("对象转化为json对象异常");
                e.printStackTrace();

            }
            log.info("将数据存入redis缓存中");
            redisTemplate.opsForValue().set("province",jsonData,60*60, TimeUnit.SECONDS);

        }

        return jsonData;
    }

    @Override
    public String getAreaNameByCode(String areaCode) {
        return this.provinceCityMapper.getAreaNameByCode(areaCode).getName();
    }
}
