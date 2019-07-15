package cn.myweb01.money01.exception;

/**
 * 密码错误异常
 */
public class UserNameOrPasswordErrorException extends Exception {
    public UserNameOrPasswordErrorException(){

    }
    public UserNameOrPasswordErrorException(String errorMsg){
        super(errorMsg);
    }
}
