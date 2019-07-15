package JunitTest.elasticsearch;

import JunitTest.MyUnitTest;
import cn.myweb01.money01.mapper.JobInfo1Mapper;
import cn.myweb01.money01.mapper.elasticsearch.JobRepository;
import cn.myweb01.money01.pojo.JobInfo1;
import cn.myweb01.money01.pojo.elasticsearch.JobSearch;
import cn.myweb01.money01.service.impl.elasticsearch.JobSearchService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class CreateIndex01 extends MyUnitTest {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private JobInfo1Mapper jobInfo1Mapper;
    @Autowired
    private JobSearchService jobSearchService;

    @Test
    public void createIndex(){
        // 创建索引库，以及映射
        this.elasticsearchTemplate.createIndex(JobSearch.class);
        this.elasticsearchTemplate.putMapping(JobSearch.class);
        //查询所有的jobInfo数据
        List<JobInfo1> jobInfo1s = jobInfo1Mapper.selectAll();
        //遍历,转化为jobSearch对象
        List<JobSearch> jobSearches = jobInfo1s.stream().map(jobInfo1 -> jobSearchService.createJobSearch(jobInfo1)).collect(Collectors.toList());
        //将数据存入到索引中
        this.jobRepository.saveAll(jobSearches);
    }
}
