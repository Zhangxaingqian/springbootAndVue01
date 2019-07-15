package cn.myweb01.money01.service;

import cn.myweb01.money01.pojo.PageBean;
import cn.myweb01.money01.pojo.elasticsearch.SearchRequest;

import java.io.IOException;

public interface IElasticsearchService {
    //分页查询工作表1的信息
    PageBean queryJobByPage(SearchRequest request);
    //根据id创建对应的文档
    void createIndex(Integer id) throws IOException;
    //根据id删除对应的文档
    void deleteIndex(Integer id);
}
