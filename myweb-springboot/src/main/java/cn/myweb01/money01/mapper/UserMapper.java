package cn.myweb01.money01.mapper;

import cn.myweb01.money01.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User queryUserByUserName(@Param("username") String username);

    void register(User user);

    int active(@Param("code") String code);

    User login(User user);
    //根据用户名更新用户的的信息,发送邮件标志设为1
    void updateUserEmailFlagByUserName(@Param("username") String username);

}
