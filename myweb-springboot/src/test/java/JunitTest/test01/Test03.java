package JunitTest.test01;

import JunitTest.MyUnitTest;
import cn.myweb01.money01.mapper.JobInfo1Mapper;
import cn.myweb01.money01.pojo.JobInfo1;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Test03 extends MyUnitTest {

    @Autowired
    private JobInfo1Mapper jobInfo1Mapper;
    @Test
    public void test(){
        List<JobInfo1> jobInfo1s = jobInfo1Mapper.selectAll();
        jobInfo1s.forEach(jobInfo1 -> System.out.println("jobInfo1 = " + jobInfo1.getJobGrade()));
    }
}
