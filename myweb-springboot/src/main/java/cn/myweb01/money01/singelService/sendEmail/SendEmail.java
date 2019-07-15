package cn.myweb01.money01.singelService.sendEmail;

import cn.myweb01.money01.mapper.UserMapper;
import cn.myweb01.money01.utils.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendEmail  {
    @Autowired
    private UserMapper userMapper;

    private final static Logger log= LoggerFactory.getLogger(SendEmail.class);
    public void sendEmail(String email, String userName,String code) throws Exception {
          MailUtil.sendMail(email,"<h1>激活测试</h1><a href='http://120.55.56.15/user/active?code="+code+"'>用户激活</a>");
          //将数据库的send_email_flag设置为1
          userMapper.updateUserEmailFlagByUserName(userName);
    }
}

