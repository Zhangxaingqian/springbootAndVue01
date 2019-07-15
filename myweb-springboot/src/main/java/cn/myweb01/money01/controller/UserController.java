package cn.myweb01.money01.controller;

import cn.myweb01.money01.exception.UserExistsException;
import cn.myweb01.money01.exception.UserNameOrPasswordErrorException;
import cn.myweb01.money01.exception.UserNoActiveException;
import cn.myweb01.money01.pojo.ResultInfo;
import cn.myweb01.money01.pojo.User;
import cn.myweb01.money01.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {
    private final static Logger log= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping("register")
    @ResponseBody
    public ResultInfo register(User user, @RequestParam("check")String check, HttpSession session){
        ResultInfo resultInfo = null;
        log.info(user.getUsername()+"用户注册开始:");
        try {

            String checkCode = (String) session.getAttribute("check");
            if (check.equalsIgnoreCase(checkCode)){
                log.info("条形码正确开始注册");
                //执行注册
                userService.register(user);
                resultInfo = new ResultInfo(true,null,null);
            }else{
                log.info("条形码错误");
                resultInfo = new ResultInfo(false,null,"验证码错误！");
            }

        } catch (UserExistsException e) {
            log.info("用户已存在");
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,e.getMessage());
        }catch (Exception e) {
            log.info("用户注册失败");
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,"服务器忙，请稍后再试！");
        }
        return resultInfo;
    }

    @RequestMapping("active")
    public String active(@RequestParam("code")String code){

        try {
            Boolean flag = userService.active(code);
            if (flag){
                return "redirect:/login.html";
            }else{
                return "redirect:/error/500.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/500.html";
        }

    }

    @RequestMapping("login")
    @ResponseBody
    public ResultInfo login(User user, @RequestParam("check")String check, HttpSession session){

        ResultInfo resultInfo = null;

        try {

            String checkCode = (String) session.getAttribute("check");
            if (check.equalsIgnoreCase(checkCode)){

                User queryUser = userService.login(user);

                //将登陆信息存入session
                session.setAttribute("user",queryUser);

                resultInfo = new ResultInfo(true,null,null);
            }else{
                resultInfo = new ResultInfo(false,null,"验证码错误!");
            }


        }catch (UserNameOrPasswordErrorException e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,e.getMessage());
        } catch (UserNoActiveException e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,"服务器忙，请稍后再试！");
        }

        return  resultInfo;
    }

    @RequestMapping("queryInfoByLoginUser")
    @ResponseBody
    public ResultInfo queryInfoByLoginUser(HttpSession session){

        ResultInfo resultInfo = null;

        User user = (User) session.getAttribute("user");
        if (user!=null){
            resultInfo = new ResultInfo(true,user.getUsername(),null);
        }else{
            resultInfo = new ResultInfo(false,null,null);
        }
        return  resultInfo;
    }

    @RequestMapping("loginOut")
    public String loginOut(HttpSession session){
        session.invalidate();//清空session
        return "redirect:/login.html";
    }
}
