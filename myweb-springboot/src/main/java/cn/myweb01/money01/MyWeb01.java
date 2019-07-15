package cn.myweb01.money01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableTransactionManagement//开启事务,需要在service上使用@Transactional
@MapperScan("cn.myweb01.money01.mapper")
public class MyWeb01 {
    public static void main(String[] args) {
        SpringApplication.run(MyWeb01.class, args);
    }
}