package cn.myweb01.money01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class HelloController {

    @GetMapping("hello")
    public String test(){
        return "hello ssm";
    }
}
