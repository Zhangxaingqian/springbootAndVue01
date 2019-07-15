package cn.myweb01.money01.exception;

/**
 * 密码错误异常
 */
public class PasswordErrorException extends Exception {
    public PasswordErrorException(){

    }
    public PasswordErrorException(String errorMsg){
        super(errorMsg);
    }
}
