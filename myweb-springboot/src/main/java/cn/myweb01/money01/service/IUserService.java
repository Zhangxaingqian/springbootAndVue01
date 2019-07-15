package cn.myweb01.money01.service;

import cn.myweb01.money01.pojo.User;

import java.util.List;

public interface IUserService {
    void register(User user) throws Exception;

    Boolean active(String code);

    User login(User user) throws Exception;

    User queryUserByUid(Integer uid);

    Boolean insertUser(User user);

    Boolean updateUser(User user);

    Boolean deleteUserByUid(Integer uid);

    //查询所有的用户
    List<User> queryAllUsers();

}
