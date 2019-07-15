package cn.myweb01.money01.exception;

/**
 * 用户未激活异常
 */
public class UserNoActiveException extends Exception {
    public UserNoActiveException(){

    }
    public UserNoActiveException(String errorMsg){
        super(errorMsg);
    }
}
