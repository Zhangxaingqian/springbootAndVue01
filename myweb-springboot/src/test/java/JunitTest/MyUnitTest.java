package JunitTest;

import cn.myweb01.money01.MyWeb01;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//classes是启动类的名字
/*
* 建立一个测试的父类,加上各种注解,其他单元测试直接继承这个类即可*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyWeb01.class)
public class MyUnitTest {
    
    @Before
    public void before(){
        System.out.println("测试的before方法执行");
    }
    @After
    public void after(){
        System.out.println("测试的after方法执行");
    }

}
