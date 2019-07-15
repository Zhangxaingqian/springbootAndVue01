package cn.myweb01.money01.service.impl;

import cn.myweb01.money01.mapper.CategoryMapper;
import cn.myweb01.money01.pojo.FirstJobCategory;
import cn.myweb01.money01.pojo.SecondJobCategory;
import cn.myweb01.money01.service.ICategoryService;
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

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT)
public class CategoryServiceImpl implements ICategoryService {
    private final static Logger log= LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CategoryMapper categoryMapper;

   /*获取一级名称,使用redisTemplate可以不需要手动释放资源*/
    @Override
    public String queryFirstJobCategoryList() throws JsonProcessingException {

        //使用缓存
        String jsonData = (String) redisTemplate.opsForValue().get("firstJobCategoryList");
        log.info("redis中的一级分类的缓存firstJobCategoryList为:"+jsonData);
        //如果缓存中没有数据，就去数据库查询，然后再放入缓存中
        if (StringUtils.isBlank(jsonData)){
            log.info("没有缓存,从mysql数据库中进行查询");
            List<FirstJobCategory> list = categoryMapper.queryFirstJobCategoryList();

            //使用工具将list转换为String
            jsonData = new ObjectMapper().writeValueAsString(list);
            log.info("将数据存入redis缓存中");
            redisTemplate.opsForValue().set("firstJobCategoryList",jsonData,60*3,TimeUnit.SECONDS);

        }

        return jsonData;
    }
    /*根据一级分类id获取二级分类的内容*/
    @Override
    public String querySecondJobCategoryListById(Integer firstId) throws JsonProcessingException {

        //使用缓存
        String jsonData = (String) redisTemplate.opsForHash().get("secondJobCategoryList",firstId);
        log.info("redis中的二级分类的缓存secondJobCategoryList为:{}"+jsonData);
        //如果缓存中没有数据，就去数据库查询，然后再放入缓存中
        if (StringUtils.isBlank(jsonData)){
            log.info("没有缓存,从mysql数据库中进行查询");
            //查询所有的一级分类的id
            List<FirstJobCategory> lists = categoryMapper.queryFirstJobCategoryList();
            //将firstJobCategory对象转化为integer对象,jdk8的新特性
            List<Integer> ids = lists.stream().map(job -> job.getFirstId()).collect(Collectors.toList());
            //遍历id,根据id查询二级分类信息,将信息存入map中
            HashMap<Integer,String> map = new HashMap<>();
            ids.forEach(id->{
                List<SecondJobCategory> seconds = categoryMapper.querySecondJobCategoryById(id);
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
            redisTemplate.opsForHash().putAll("secondJobCategoryList", map);
            redisTemplate.expire("secondJobCategoryList", 60*3, TimeUnit.SECONDS);
            jsonData = (String) redisTemplate.opsForHash().get("secondJobCategoryList",firstId);
        }

        return jsonData;
    }
    //根据二级分类的id查询二级分类的名字
    @Override
    public SecondJobCategory queryJobGradeById(Integer jobGradeId) {
        return this.categoryMapper.queryJobGradeById(jobGradeId);
    }
}
