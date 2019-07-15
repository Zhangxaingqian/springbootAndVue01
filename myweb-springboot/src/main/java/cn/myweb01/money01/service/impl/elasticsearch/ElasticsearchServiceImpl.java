package cn.myweb01.money01.service.impl.elasticsearch;

import cn.myweb01.money01.mapper.elasticsearch.JobRepository;
import cn.myweb01.money01.pojo.JobInfo1;
import cn.myweb01.money01.pojo.PageBean;
import cn.myweb01.money01.pojo.elasticsearch.JobSearch;
import cn.myweb01.money01.pojo.elasticsearch.SearchRequest;
import cn.myweb01.money01.service.IElasticsearchService;
import cn.myweb01.money01.service.IJobInfo1Service;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ElasticsearchServiceImpl implements IElasticsearchService {


    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private IJobInfo1Service jobInfo1Service;
    @Autowired
    private JobSearchService jobSearchService;


    @Override
    public PageBean queryJobByPage(SearchRequest request) {
        //从对象获取参数
        String jname=request.getKey();
        Integer curPage=request.getCurPage();
        Integer pageSize=request.getPageSize();
        String minPrice=request.getMinPrice();
        String maxPrice=request.getMaxPrice();
        //首先判断jname是否为空,为空不查询
        if(StringUtils.isBlank(jname)){
            return null;
        }
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 对jname进行全文检索查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", jname).operator(Operator.AND));
        // 2、通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle
        queryBuilder.withSourceFilter(new FetchSourceFilter(
                new String[]{"id","jobGrade","jobSite","jobUpdateDate","jobWage"}, null));
        // 3、分页
        // 准备分页参数
        queryBuilder.withPageable(PageRequest.of(curPage - 1, pageSize));
        //范围内查询
        if(null!=minPrice && null!=maxPrice){
            queryBuilder.withFilter(QueryBuilders.
                    rangeQuery("jobWage")
                    .from(minPrice)
                    .to(maxPrice)
                    .includeLower(true)     // 包含上界
                    .includeUpper(true)      // 包含下届
            );
        }else if(null==minPrice && null!=maxPrice){
            queryBuilder.withFilter(QueryBuilders.
                    rangeQuery("jobWage")
                    .from("0")
                    .to(maxPrice)
                    .includeLower(true)     // 包含上界
                    .includeUpper(true)      // 包含下届
            );
        }else if(null!=minPrice && null==maxPrice){
            queryBuilder.withFilter(QueryBuilders.
                    rangeQuery("jobWage")
                    .from(minPrice)
                    .includeLower(true)     // 包含上界
            );
        }
        //4,进行排序,根据时间降序
        queryBuilder.withSort(SortBuilders.fieldSort("jobUpdateDate").order(SortOrder.DESC));
        //返回数据
        Page<JobSearch> search = this.jobRepository.search(queryBuilder.build());

        //封装到pageBean
        PageBean<JobSearch> pageBean = new PageBean<>();
        pageBean.setCount((int)search.getTotalElements());//总记录数
        pageBean.setCurPage(curPage);//当前页的页面
        pageBean.setPageSize(pageSize);//页面大小
        pageBean.setData(search.getContent());//当前页的数据
        return pageBean;
    }

    //更新文档
    public void createIndex(Integer id) throws IOException {
        //根据id查询工作表
        JobInfo1 jobInfo1 = this.jobInfo1Service.selectDetailJobInfo1ById(id);
        //将jobinfo1对象转化为jobsearch对象
        JobSearch jobSearch = this.jobSearchService.createJobSearch(jobInfo1);
        //保存为文档
        this.jobRepository.save(jobSearch);

    }

    //删除文档
    public void deleteIndex(Integer id) {
       this.jobRepository.deleteById((long)id);
    }
}
