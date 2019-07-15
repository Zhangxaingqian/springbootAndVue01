package cn.myweb01.money01.service.impl;

import cn.myweb01.money01.exception.UserExistsException;
import cn.myweb01.money01.exception.UserNameOrPasswordErrorException;
import cn.myweb01.money01.exception.UserNoActiveException;
import cn.myweb01.money01.mapper.NewUserMapper;
import cn.myweb01.money01.pojo.User;
import cn.myweb01.money01.service.IUserService;
import cn.myweb01.money01.utils.Md5Util;
import cn.myweb01.money01.utils.UuidUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT)
public class UserServiceImpl implements IUserService {
    private final static Logger log= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    //private UserMapper userMapper;
    private NewUserMapper userMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;


    @Override
    public void register(User user) throws Exception {
        //通过用户名称查询用户
        //User queryUser = userMapper.queryUserByUserName(user.getUsername());

        User record = new User();
        record.setUsername(user.getUsername());

        User queryUser = userMapper.selectOne(record);


        //判断用户是否存在,如果用户已存在，抛出用户存在的异常
        if(queryUser!=null){
            throw  new UserExistsException("用户名已存在！");
        }
        log.info("密码Md5加密");
        //密码加密
        user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
        //设置激活状态为未激活
        user.setStatus("0");
        user.setSendEmailFlag("0");//默认未发送邮件
        //设置激活码
        user.setCode(UuidUtil.getUuid());

        //执行注册
        //userMapper.register(user);
        userMapper.insertSelective(user);
        log.info("发送消息给rabbitMq,让其发送短信");
        log.info("发送邮件给用户激活:"+user.getEmail());

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sendEmail", "Y");
        map.put("email", user.getEmail());
        map.put("code", user.getCode());
        map.put("userName", user.getUsername());

        JSONObject jsonObject=new JSONObject();
        String s = new ObjectMapper().writeValueAsString(map);

        sendMessage("myweb.email",s);

       /* 这是confirm确认的机制
       rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback(){
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {

            }
        });*/
        //根据key发送到对应的队列
        //rabbitTemplate.convertAndSend("que_email_key", map);

        //发送邮件给注册用户，让其进行账号的激活
        //5f8850ffda66466b95ac75c7f4ad110d
        /*try {
            MailUtil.sendMail(user.getEmail(),"<h1>激活测试</h1><a href='http://120.55.56.15/user/active?code="+user.getCode()+"'>用户激活</a>");
        } catch (Exception e) {
            //一定要抛出这个异常,才能被spring的aop捕获
            log.info("激活邮件发送失败:");
            e.printStackTrace();
            throw new RuntimeException();
        }*/
    }

    @Override
    public Boolean active(String code) {

       // int count = userMapper.active(code);

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code",code);

        User user = new User();
        user.setStatus("1");

        int count = userMapper.updateByExampleSelective(user, example);


        return count==1;
    }

    @Override
    public User login(User user) throws Exception {

        //对密码进行加密
        user.setPassword(Md5Util.encodeByMd5(user.getPassword()));

        //通过用户名和密码查询用户
        //User queryUser =  userMapper.login(user);

        /*User record = new User();
        record.setUsername(user.getUsername());
        record.setPassword(user.getPassword());*/

        User queryUser = userMapper.selectOne(user);


        //判断用户是否为null,如果是null,表示用户名或者密码错误，提示错误信息
        if (queryUser==null){
            throw new UserNameOrPasswordErrorException("用户名或者密码错误！");
        }

        //判断用户是否是激活状态
        if (!queryUser.getStatus().equals("1")){
            throw  new UserNoActiveException("账户未激活!");
        }

        return queryUser;
    }

    @Override
    public User queryUserByUid(Integer uid) {


        return userMapper.selectByPrimaryKey(uid);
    }

    @Override
    public Boolean insertUser(User user) {
        int count = userMapper.insertSelective(user);
        return count==1;
    }

    @Override
    public Boolean updateUser(User user) {
        int count = userMapper.updateByPrimaryKeySelective(user);
        return count==1;
    }

    @Override
    public Boolean deleteUserByUid(Integer uid) {

        int count = userMapper.deleteByPrimaryKey(uid);
        return count==1;
    }
    /*
    * 封装的发送rabbitMq消息的方法
    * */
    private void sendMessage(String type,String map){
        // 发送消息
        try {
            this.amqpTemplate.convertAndSend(type,map);
        } catch (Exception e) {
            log.error("{}邮件消息发送异常", map, e);
        }
    }

    /*查询所有的用户*/
    @Override
    public List<User> queryAllUsers() {
        return this.userMapper.selectAll();
    }
}
