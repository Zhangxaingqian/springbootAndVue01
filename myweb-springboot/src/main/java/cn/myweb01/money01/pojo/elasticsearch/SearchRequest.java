package cn.myweb01.money01.pojo.elasticsearch;

public class SearchRequest {
    private static final Integer DEFAULT_PAGE = 1;// 默认页
    private static final Integer DEFAULT_SIZE = 5;// 每页大小，不从页面接收，而是固定大小
    private String key;// 搜索条件
    private String minPrice;
    private String maxPrice;
    private Integer curPage;//当前页
    private Integer pageSize;//每页大小


    public Integer getPageSize() {
        return DEFAULT_SIZE;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurPage() {
        if(curPage == null){
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, curPage);
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
