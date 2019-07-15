package cn.myweb01.money01.mapper.elasticsearch;

import cn.myweb01.money01.pojo.elasticsearch.JobSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface JobRepository extends ElasticsearchRepository<JobSearch,Long> {
}
