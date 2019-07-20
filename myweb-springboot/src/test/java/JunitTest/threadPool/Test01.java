package JunitTest.threadPool;

import JunitTest.MyUnitTest;
import cn.myweb01.money01.pojo.SecondJobCategory;
import cn.myweb01.money01.service.ICategoryService;
import cn.myweb01.money01.utils.ContexThredPool;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/*测试线程池的使用*/
public class Test01 extends MyUnitTest {
    @Autowired
    private ICategoryService categoryService;

    @Test
    public void show() throws ExecutionException, InterruptedException {
        //引入线程池
        ThreadPoolExecutor threadPool = ContexThredPool.INSTANCE.getThreadPool();
        //使用线程池执行任务
        Future<List<SecondJobCategory>> result = threadPool.submit(new Callable<List<SecondJobCategory>>() {
            @Override
            public List<SecondJobCategory> call() throws Exception {
                List<SecondJobCategory> allJobGrade = categoryService.getAllJobGrade();
                return allJobGrade;
            }
        });
        List<SecondJobCategory> secondJobCategories = result.get();
        secondJobCategories.forEach(s->System.out.println(s.getSecondName()));
        List<String> collect = secondJobCategories.stream().map(s -> s.getSecondName()).collect(Collectors.toList());

        //不能返回数据
        /*threadPool.execute(new Runnable() {
            @Override
            public void run() {
                List<SecondJobCategory> allJobGrade = categoryService.getAllJobGrade();
            }
        });*/

    }
}
